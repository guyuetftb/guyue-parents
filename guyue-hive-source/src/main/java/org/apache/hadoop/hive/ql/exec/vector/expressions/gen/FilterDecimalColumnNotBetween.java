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

import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;
import org.apache.hadoop.hive.common.type.HiveDecimal;
import org.apache.hadoop.hive.ql.exec.vector.DecimalColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.expressions.DecimalUtil;
import org.apache.hadoop.hive.ql.exec.vector.expressions.VectorExpression;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;

/**
 * Generated from template FilterDecimalColumnBetween.txt, which covers [NOT] BETWEEN filter
 * expressions where a column is [NOT] between one scalar and another.
 * Output is not produced in a separate column.  The selected vector of the input
 * {@link VectorizedRowBatch} is updated for in-place filtering.
 */
public class FilterDecimalColumnNotBetween extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum;

  // The comparison is of the form "column BETWEEN leftValue AND rightValue"
  private HiveDecimal leftValue;
  private HiveDecimal rightValue;

  public FilterDecimalColumnNotBetween(int colNum, HiveDecimal leftValue, HiveDecimal rightValue) {
    this.colNum = colNum;
    this.leftValue = leftValue;
    this.rightValue = rightValue;
  }

  public FilterDecimalColumnNotBetween() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    DecimalColumnVector inputColVector = (DecimalColumnVector) batch.cols[colNum];
    int[] sel = batch.selected;
    boolean[] nullPos = inputColVector.isNull;
    int n = batch.size;
    HiveDecimalWritable[] vector = inputColVector.vector;

    // return immediately if batch is empty
    if (n == 0) {
      return;
    }

    if (inputColVector.noNulls) {
      if (inputColVector.isRepeating) {

        // All must be selected otherwise size would be zero.
        // Repeating property will not change.
        if (!(DecimalUtil.compare(vector[0], leftValue) < 0 || DecimalUtil.compare(vector[0], rightValue) > 0)) {

          // Entire batch is filtered out.
          batch.size = 0;
        }
      } else if (batch.selectedInUse) {
        int newSize = 0;
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          if (!(DecimalUtil.compare(leftValue, vector[i]) <= 0 && DecimalUtil.compare(vector[i], rightValue) <= 0)) {
            sel[newSize++] = i;
          }
        }
        batch.size = newSize;
      } else {
        int newSize = 0;
        for(int i = 0; i != n; i++) {
          if (!(DecimalUtil.compare(leftValue, vector[i]) <= 0 && DecimalUtil.compare(vector[i], rightValue) <= 0)) {
            sel[newSize++] = i;
          }
        }
        if (newSize < n) {
          batch.size = newSize;
          batch.selectedInUse = true;
        }
      }
    } else {
      if (inputColVector.isRepeating) {

        // All must be selected otherwise size would be zero.
        // Repeating property will not change.
        if (!nullPos[0]) {
          if (!(DecimalUtil.compare(vector[0], leftValue) < 0 || DecimalUtil.compare(vector[0], rightValue) > 0)) {

            // Entire batch is filtered out.
            batch.size = 0;
          }
        } else {
          batch.size = 0;
        }
      } else if (batch.selectedInUse) {
        int newSize = 0;
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          if (!nullPos[i]) {
            if (!(DecimalUtil.compare(leftValue, vector[i]) <= 0 && DecimalUtil.compare(vector[i], rightValue) <= 0)) {
             sel[newSize++] = i;
            }
          }
        }
        
        // Change the selected vector
        batch.size = newSize;
      } else {
        int newSize = 0;
        for(int i = 0; i != n; i++) {
          if (!nullPos[i]) {
            if (!(DecimalUtil.compare(leftValue, vector[i]) <= 0 && DecimalUtil.compare(vector[i], rightValue) <= 0)) {
              sel[newSize++] = i;
            }
          }
        }
        if (newSize < n) {
          batch.size = newSize;
          batch.selectedInUse = true;
        }
      }
    }
  }

  @Override
  public int getOutputColumn() {
    return -1;
  }

  @Override
  public String getOutputType() {
    return "boolean";
  }

  public HiveDecimal getLeftValue() {
    return leftValue;
  }

  public void setLeftValue(HiveDecimal value) {
    this.leftValue = value;
  }

  public HiveDecimal getRightValue() {
    return rightValue;
  }

  public void setRightValue(HiveDecimal value) {
    this.rightValue = value;
  }

  @Override
  public String vectorExpressionParameters() {
    return "col " + colNum + ", left " + leftValue.toString() + ", right " + rightValue.toString();
  }

  @Override
  public VectorExpressionDescriptor.Descriptor getDescriptor() {
    return (new VectorExpressionDescriptor.Builder())
        .setMode(
            VectorExpressionDescriptor.Mode.FILTER)
        .setNumArguments(3)
        .setArgumentTypes(
            VectorExpressionDescriptor.ArgumentType.getType("decimal"),
            VectorExpressionDescriptor.ArgumentType.getType("decimal"),
            VectorExpressionDescriptor.ArgumentType.getType("decimal"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN,
            VectorExpressionDescriptor.InputExpressionType.SCALAR,
            VectorExpressionDescriptor.InputExpressionType.SCALAR).build();
  }
}
