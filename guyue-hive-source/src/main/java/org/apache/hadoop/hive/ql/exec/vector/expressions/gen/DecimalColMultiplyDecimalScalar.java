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
import org.apache.hadoop.hive.common.type.HiveDecimal;

/**
 * Generated from template ColumnArithmeticScalarDecimal.txt, which covers binary arithmetic 
 * expressions between a column and a scalar.
 */
public class DecimalColMultiplyDecimalScalar extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum;
  private HiveDecimal value;
  private int outputColumn;

  public DecimalColMultiplyDecimalScalar(int colNum, HiveDecimal value, int outputColumn) {
    this.colNum = colNum;
    this.value = value;
    this.outputColumn = outputColumn;
    this.outputType = "decimal";
  }

  public DecimalColMultiplyDecimalScalar() {
    this.outputType = "decimal";
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    DecimalColumnVector inputColVector = (DecimalColumnVector) batch.cols[colNum];
    DecimalColumnVector outputColVector = (DecimalColumnVector) batch.cols[outputColumn];
    int[] sel = batch.selected;
    boolean[] inputIsNull = inputColVector.isNull;
    boolean[] outputIsNull = outputColVector.isNull;
    outputColVector.noNulls = inputColVector.noNulls;
    outputColVector.isRepeating = inputColVector.isRepeating;
    int n = batch.size;
    HiveDecimalWritable[] vector = inputColVector.vector;
    
    // return immediately if batch is empty
    if (n == 0) {
      return;
    }
         
    if (inputColVector.noNulls) {
    
     /* Initialize output vector NULL values to false. This is necessary
      * since the decimal operation may produce a NULL result even for 
      * a non-null input vector value, and convert the output vector
      * to have noNulls = false;
      */
      NullUtil.initOutputNullsToFalse(outputColVector, inputColVector.isRepeating, 
          batch.selectedInUse, sel, n);
    }

    if (inputColVector.isRepeating) {
      if (!inputColVector.noNulls) {
        outputIsNull[0] = inputIsNull[0];
      } 
      
      // The following may override a "false" null setting if an error or overflow occurs.
      DecimalUtil.multiplyChecked(0, vector[0], value, outputColVector);
    } else if (inputColVector.noNulls) {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          DecimalUtil.multiplyChecked(i, vector[i], value, outputColVector);
        }
      } else {
        for(int i = 0; i != n; i++) {
          DecimalUtil.multiplyChecked(i, vector[i], value, outputColVector);
        }
      }
    } else /* there are nulls */ {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          outputIsNull[i] = inputIsNull[i];

          // The following may override a "false" null setting if an error or overflow occurs.
          DecimalUtil.multiplyChecked(i, vector[i], value, outputColVector);
        }
      } else {
        System.arraycopy(inputIsNull, 0, outputIsNull, 0, n);
        for(int i = 0; i != n; i++) {

          // The following may override a "false" null setting if an error or overflow occurs.
          DecimalUtil.multiplyChecked(i, vector[i], value, outputColVector);
        }
      }
    }
    
    /*
     * Null data entries are not set to a special non-zero value because all null math operations
     * are checked, meaning that a zero-divide always results in a null result.
     */
  }

  @Override
  public int getOutputColumn() {
    return outputColumn;
  }

  @Override
  public String vectorExpressionParameters() {
    return "col " + colNum + ", val " + value.toString();
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
            VectorExpressionDescriptor.InputExpressionType.SCALAR).build();
  }
}
