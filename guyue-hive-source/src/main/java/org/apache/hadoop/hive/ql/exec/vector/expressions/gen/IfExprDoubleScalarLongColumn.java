/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.exec.vector.expressions.gen;

import org.apache.hadoop.hive.ql.exec.vector.expressions.VectorExpression;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DoubleColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.expressions.NullUtil;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;

/**
 * Compute IF(expr1, expr2, expr3) for 3 input column expressions.
 * The first is always a boolean (LongColumnVector).
 * The second is a column or non-constant expression result.
 * The third is a constant value.
 */
public class IfExprDoubleScalarLongColumn extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int arg1Column, arg3Column;
  private double arg2Scalar;
  private int outputColumn;

  public IfExprDoubleScalarLongColumn(int arg1Column, double arg2Scalar, int arg3Column,
      int outputColumn) {
    this.arg1Column = arg1Column;
    this.arg2Scalar = arg2Scalar;
    this.arg3Column = arg3Column;
    this.outputColumn = outputColumn;
  }

  public IfExprDoubleScalarLongColumn() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    LongColumnVector arg1ColVector = (LongColumnVector) batch.cols[arg1Column];
    LongColumnVector arg3ColVector = (LongColumnVector) batch.cols[arg3Column];
    DoubleColumnVector outputColVector = (DoubleColumnVector) batch.cols[outputColumn];
    int[] sel = batch.selected;
    boolean[] outputIsNull = outputColVector.isNull;
    outputColVector.noNulls = arg3ColVector.noNulls; // nulls can only come from arg3 column vector
    outputColVector.isRepeating = false; // may override later
    int n = batch.size;
    long[] vector1 = arg1ColVector.vector;
    long[] vector3 = arg3ColVector.vector;
    double[] outputVector = outputColVector.vector;

    // return immediately if batch is empty
    if (n == 0) {
      return;
    }

    if (arg1ColVector.isRepeating) {
      if (vector1[0] == 1) {
        outputColVector.fill(arg2Scalar);
      } else {
        arg3ColVector.copySelected(batch.selectedInUse, sel, n, outputColVector);
      }
      return;
    }

    // Extend any repeating values and noNulls indicator in the inputs to
    // reduce the number of code paths needed below.
    // This could be optimized in the future by having separate paths
    // for when arg3ColVector is repeating or has no nulls.
    arg3ColVector.flatten(batch.selectedInUse, sel, n);

    if (arg1ColVector.noNulls) {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          outputVector[i] = (vector1[i] == 1 ? arg2Scalar : vector3[i]);
        }
      } else {
        for(int i = 0; i != n; i++) {
          outputVector[i] = (vector1[i] == 1 ? arg2Scalar : vector3[i]);
        }
      }
    } else /* there are nulls */ {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          outputVector[i] = (!arg1ColVector.isNull[i] && vector1[i] == 1 ?
              arg2Scalar : vector3[i]);
          outputIsNull[i] = (!arg1ColVector.isNull[i] && vector1[i] == 1 ?
              false : arg3ColVector.isNull[i]);
        }
      } else {
        for(int i = 0; i != n; i++) {
          outputVector[i] = (!arg1ColVector.isNull[i] && vector1[i] == 1 ?
              arg2Scalar : vector3[i]);
          outputIsNull[i] = (!arg1ColVector.isNull[i] && vector1[i] == 1 ?
              false : arg3ColVector.isNull[i]);
        }
      }
    }

    // restore repeating and no nulls indicators
    arg3ColVector.unFlatten();
  }

  @Override
  public int getOutputColumn() {
    return outputColumn;
  }

  @Override
  public String getOutputType() {
    return "double";
  }

  public int getArg1Column() {
    return arg1Column;
  }

  public void setArg1Column(int colNum) {
    this.arg1Column = colNum;
  }

  public int getArg3Column() {
    return arg3Column;
  }

  public void setArg3Column(int colNum) {
    this.arg3Column = colNum;
  }

  public double getArg2Scalar() {
    return arg2Scalar;
  }

  public void setArg2Scalar(double value) {
    this.arg2Scalar = value;
  }

  public void setOutputColumn(int outputColumn) {
    this.outputColumn = outputColumn;
  }

  @Override
  public String vectorExpressionParameters() {
    return "col " + arg1Column + ", val "+ arg2Scalar + ", col "+ arg3Column;
  }

  @Override
  public VectorExpressionDescriptor.Descriptor getDescriptor() {
    return (new VectorExpressionDescriptor.Builder())
        .setMode(
            VectorExpressionDescriptor.Mode.PROJECTION)
        .setNumArguments(3)
        .setArgumentTypes(
            VectorExpressionDescriptor.ArgumentType.getType("long"),
            VectorExpressionDescriptor.ArgumentType.getType("double"),
            VectorExpressionDescriptor.ArgumentType.getType("long"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN,
            VectorExpressionDescriptor.InputExpressionType.SCALAR,
            VectorExpressionDescriptor.InputExpressionType.COLUMN).build();
  }
}
