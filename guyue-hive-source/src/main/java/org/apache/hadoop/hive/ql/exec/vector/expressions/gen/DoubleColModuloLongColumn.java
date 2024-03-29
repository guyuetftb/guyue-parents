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
import org.apache.hadoop.hive.ql.exec.vector.expressions.NullUtil;
import org.apache.hadoop.hive.ql.exec.vector.*;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;

/**
 * Generated from template ColumnArithmeticColumn.txt, which covers binary arithmetic
 * expressions between columns.
 */
public class DoubleColModuloLongColumn extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum1;
  private int colNum2;
  private int outputColumn;

  public DoubleColModuloLongColumn(int colNum1, int colNum2, int outputColumn) {
    this.colNum1 = colNum1;
    this.colNum2 = colNum2;
    this.outputColumn = outputColumn;
  }

  public DoubleColModuloLongColumn() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    DoubleColumnVector inputColVector1 = (DoubleColumnVector) batch.cols[colNum1];
    LongColumnVector inputColVector2 = (LongColumnVector) batch.cols[colNum2];
    DoubleColumnVector outputColVector = (DoubleColumnVector) batch.cols[outputColumn];
    int[] sel = batch.selected;
    int n = batch.size;
    double[] vector1 = inputColVector1.vector;
    long[] vector2 = inputColVector2.vector;
    double[] outputVector = outputColVector.vector;

    // return immediately if batch is empty
    if (n == 0) {
      return;
    }

    outputColVector.isRepeating =
         inputColVector1.isRepeating && inputColVector2.isRepeating
      || inputColVector1.isRepeating && !inputColVector1.noNulls && inputColVector1.isNull[0]
      || inputColVector2.isRepeating && !inputColVector2.noNulls && inputColVector2.isNull[0];

    // Handle nulls first
    NullUtil.propagateNullsColCol(
      inputColVector1, inputColVector2, outputColVector, sel, n, batch.selectedInUse);

    /* Disregard nulls for processing. In other words,
     * the arithmetic operation is performed even if one or
     * more inputs are null. This is to improve speed by avoiding
     * conditional checks in the inner loop.
     */
    boolean hasDivBy0 = false;
    if (inputColVector1.isRepeating && inputColVector2.isRepeating) {
      long denom = vector2[0];
      outputVector[0] = vector1[0] % denom;
      hasDivBy0 = hasDivBy0 || (denom == 0);
    } else if (inputColVector1.isRepeating) {
      final double vector1Value = vector1[0];
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          long denom = vector2[i];
          outputVector[i] = vector1Value % denom;
          hasDivBy0 = hasDivBy0 || (denom == 0);
        }
      } else {
        for(int i = 0; i != n; i++) {
          outputVector[i] = vector1Value % vector2[i];
        }

        for(int i = 0; i != n; i++) {
          hasDivBy0 = hasDivBy0 || (vector2[i] == 0);
        }
      }
    } else if (inputColVector2.isRepeating) {
      final long vector2Value = vector2[0];
      if (vector2Value == 0) {
        // Denominator is zero, convert the batch to nulls
        outputColVector.noNulls = false;
        outputColVector.isRepeating = true;
        outputColVector.isNull[0] = true;
      } else if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          outputVector[i] = vector1[i] % vector2Value;
        }
      } else {
        for(int i = 0; i != n; i++) {
          outputVector[i] = vector1[i] % vector2Value;
        }
      }
    } else {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          long denom = vector2[i];
          outputVector[i] = vector1[i] % denom;
          hasDivBy0 = hasDivBy0 || (denom == 0);
        }
      } else {
        for(int i = 0; i != n; i++) {
          outputVector[i] = vector1[i] % vector2[i];
        }

        for(int i = 0; i != n; i++) {
          hasDivBy0 = hasDivBy0 || (vector2[i] == 0);
        }
      }
    }

    /* For the case when the output can have null values, follow
     * the convention that the data values must be 1 for long and
     * NaN for double. This is to prevent possible later zero-divide errors
     * in complex arithmetic expressions like col2 % (col1 - 1)
     * in the case when some col1 entries are null.
     */
    if (!hasDivBy0) {
      NullUtil.setNullDataEntriesDouble(outputColVector, batch.selectedInUse, sel, n);
    } else {
      NullUtil.setNullAndDivBy0DataEntriesDouble(
          outputColVector, batch.selectedInUse, sel, n, inputColVector2);
    }
  }

  @Override
  public int getOutputColumn() {
    return outputColumn;
  }

  @Override
  public String getOutputType() {
    return "double";
  }

  public int getColNum1() {
    return colNum1;
  }

  public void setColNum1(int colNum1) {
    this.colNum1 = colNum1;
  }

  public int getColNum2() {
    return colNum2;
  }

  public void setColNum2(int colNum2) {
    this.colNum2 = colNum2;
  }

  public void setOutputColumn(int outputColumn) {
    this.outputColumn = outputColumn;
  }

  @Override
  public String vectorExpressionParameters() {
    return "col " + colNum1 + ", col " + + colNum2;
  }

  @Override
  public VectorExpressionDescriptor.Descriptor getDescriptor() {
    return (new VectorExpressionDescriptor.Builder())
        .setMode(
            VectorExpressionDescriptor.Mode.PROJECTION)
        .setNumArguments(2)
        .setArgumentTypes(
            VectorExpressionDescriptor.ArgumentType.getType("double"),
            VectorExpressionDescriptor.ArgumentType.getType("long"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN,
            VectorExpressionDescriptor.InputExpressionType.COLUMN).build();
  }
}
