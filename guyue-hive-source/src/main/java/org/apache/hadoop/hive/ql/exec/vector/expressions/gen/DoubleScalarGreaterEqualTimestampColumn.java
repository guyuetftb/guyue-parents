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
import org.apache.hadoop.hive.ql.exec.vector.TimestampColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.*;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;

/**
 * Generated from template ScalarCompareTimestamp.txt, which covers comparison
 * expressions between a long/double scalar and a column. The boolean output is stored in a
 * separate boolean column.
 */
public class DoubleScalarGreaterEqualTimestampColumn extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum;
  private double value;
  private int outputColumn;

  public DoubleScalarGreaterEqualTimestampColumn(double value, int colNum, int outputColumn) {
    this.colNum = colNum;
    this.value = value;
    this.outputColumn = outputColumn;
  }

  public DoubleScalarGreaterEqualTimestampColumn() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    TimestampColumnVector inputColVector = (TimestampColumnVector) batch.cols[colNum];
    LongColumnVector outputColVector = (LongColumnVector) batch.cols[outputColumn];
    int[] sel = batch.selected;
    boolean[] nullPos = inputColVector.isNull;
    boolean[] outNulls = outputColVector.isNull;
    int n = batch.size;
    long[] outputVector = outputColVector.vector;

    // return immediately if batch is empty
    if (n == 0) {
      return;
    }

    outputColVector.isRepeating = false;
    outputColVector.noNulls = inputColVector.noNulls;
    if (inputColVector.noNulls) {
      if (inputColVector.isRepeating) {
        //All must be selected otherwise size would be zero
        //Repeating property will not change.
        outputVector[0] = value >= inputColVector.getDouble(0) ? 1 : 0;
        outputColVector.isRepeating = true;
      } else if (batch.selectedInUse) {
        for(int j=0; j != n; j++) {
          int i = sel[j];
          outputVector[i] = value >= inputColVector.getDouble(i) ? 1 : 0;
        }
      } else {
        for(int i = 0; i != n; i++) {
          outputVector[i] = value >= inputColVector.getDouble(i) ? 1 : 0;
        }
      }
    } else {
      if (inputColVector.isRepeating) {
        //All must be selected otherwise size would be zero
        //Repeating property will not change.
        if (!nullPos[0]) {
          outputVector[0] = value >= inputColVector.getDouble(0) ? 1 : 0;
          outNulls[0] = false;
        } else {
          outNulls[0] = true;
        }
        outputColVector.isRepeating = true;
      } else if (batch.selectedInUse) {
        for(int j=0; j != n; j++) {
          int i = sel[j];
          if (!nullPos[i]) {
            outputVector[i] = value >= inputColVector.getDouble(i) ? 1 : 0;
            outNulls[i] = false;
          } else {
            //comparison with null is null
            outNulls[i] = true;
          }
        }
      } else {
        System.arraycopy(nullPos, 0, outNulls, 0, n);
        for(int i = 0; i != n; i++) {
          if (!nullPos[i]) {
            outputVector[i] = value >= inputColVector.getDouble(i) ? 1 : 0;
          }
        }
      }
    }
  }

  @Override
  public int getOutputColumn() {
    return outputColumn;
  }

  @Override
  public String getOutputType() {
    return "long";
  }

  @Override
  public String vectorExpressionParameters() {
    return "val " + value + ", col " + + colNum;
  }

  @Override
  public VectorExpressionDescriptor.Descriptor getDescriptor() {
    return (new VectorExpressionDescriptor.Builder())
        .setMode(
            VectorExpressionDescriptor.Mode.PROJECTION)
        .setNumArguments(2)
        .setArgumentTypes(
            VectorExpressionDescriptor.ArgumentType.getType("double"),
            VectorExpressionDescriptor.ArgumentType.getType("timestamp"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.SCALAR,
            VectorExpressionDescriptor.InputExpressionType.COLUMN).build();
  }
}
