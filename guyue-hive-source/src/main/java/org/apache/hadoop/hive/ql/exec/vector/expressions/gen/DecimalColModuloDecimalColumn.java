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
import org.apache.hadoop.hive.ql.exec.vector.DecimalColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.expressions.NullUtil;
import org.apache.hadoop.hive.ql.exec.vector.expressions.DecimalUtil;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;
import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;

/**
 * Generated from template ColumnArithmeticColumnDecimal.txt, which covers binary arithmetic
 * expressions between a column and a scalar.
 */
public class DecimalColModuloDecimalColumn extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum1;
  private int colNum2;
  private int outputColumn;

  public DecimalColModuloDecimalColumn(int colNum1, int colNum2, int outputColumn) {
    this.colNum1 = colNum1;
    this.colNum2 = colNum2;
    this.outputColumn = outputColumn;
    this.outputType = "decimal";
  }

  public DecimalColModuloDecimalColumn() {
    this.outputType = "decimal";
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    DecimalColumnVector inputColVector1 = (DecimalColumnVector) batch.cols[colNum1];
    DecimalColumnVector inputColVector2 = (DecimalColumnVector) batch.cols[colNum2];
    DecimalColumnVector outputColVector = (DecimalColumnVector) batch.cols[outputColumn];
    int[] sel = batch.selected;
    int n = batch.size;
    HiveDecimalWritable[] vector1 = inputColVector1.vector;
    HiveDecimalWritable[] vector2 = inputColVector2.vector;

    // return immediately if batch is empty
    if (n == 0) {
      return;
    }

    outputColVector.isRepeating =
         inputColVector1.isRepeating && inputColVector2.isRepeating
      || inputColVector1.isRepeating && !inputColVector1.noNulls && inputColVector1.isNull[0]
      || inputColVector2.isRepeating && !inputColVector2.noNulls && inputColVector2.isNull[0];

    if (inputColVector1.noNulls && inputColVector2.noNulls) {

     /* Initialize output vector NULL values to false. This is necessary
      * since the decimal operation may produce a NULL result even for
      * a non-null input vector value, and convert the output vector
      * to have noNulls = false;
      */
      NullUtil.initOutputNullsToFalse(outputColVector,
          inputColVector1.isRepeating && inputColVector2.isRepeating,
          batch.selectedInUse, sel, n);
    }

    // Handle nulls first
    NullUtil.propagateNullsColCol(
      inputColVector1, inputColVector2, outputColVector, sel, n, batch.selectedInUse);

    /* Disregard nulls for processing. In other words,
     * the arithmetic operation is performed even if one or
     * more inputs are null. This is to improve speed by avoiding
     * conditional checks in the inner loop.
     */
    if (inputColVector1.isRepeating && inputColVector2.isRepeating) {
      DecimalUtil.moduloChecked(0, vector1[0], vector2[0], outputColVector);
    } else if (inputColVector1.isRepeating) {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          DecimalUtil.moduloChecked(i, vector1[0], vector2[i], outputColVector);
        }
      } else {
        for(int i = 0; i != n; i++) {
          DecimalUtil.moduloChecked(i, vector1[0], vector2[i], outputColVector);
        }
      }
    } else if (inputColVector2.isRepeating) {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          DecimalUtil.moduloChecked(i, vector1[i], vector2[0], outputColVector);
        }
      } else {
        for(int i = 0; i != n; i++) {
          DecimalUtil.moduloChecked(i, vector1[i], vector2[0], outputColVector);
        }
      }
    } else {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          DecimalUtil.moduloChecked(i, vector1[i], vector2[i], outputColVector);
        }
      } else {
        for(int i = 0; i != n; i++) {
          DecimalUtil.moduloChecked(i, vector1[i], vector2[i], outputColVector);
        }
      }
    }
  }

  @Override
  public int getOutputColumn() {
    return outputColumn;
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
            VectorExpressionDescriptor.ArgumentType.getType("decimal"),
            VectorExpressionDescriptor.ArgumentType.getType("decimal"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN,
            VectorExpressionDescriptor.InputExpressionType.COLUMN).build();
  }
}
