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
 * Generated from template FilterColumnCompareColumn.txt, which covers binary comparison 
 * expressions between two columns, however output is not produced in a separate column. 
 * The selected vector of the input {@link VectorizedRowBatch} is updated for in-place filtering.
 */
public class FilterLongColGreaterDoubleColumn extends VectorExpression {

  private static final long serialVersionUID = 1L;

  protected int colNum1;
  protected int colNum2;

  public FilterLongColGreaterDoubleColumn(int colNum1, int colNum2) { 
    this.colNum1 = colNum1;
    this.colNum2 = colNum2;
  }

  public FilterLongColGreaterDoubleColumn() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    LongColumnVector inputColVector1 = (LongColumnVector) batch.cols[colNum1];
    DoubleColumnVector inputColVector2 = (DoubleColumnVector) batch.cols[colNum2];
    int[] sel = batch.selected;
    boolean[] nullPos1 = inputColVector1.isNull;
    boolean[] nullPos2 = inputColVector2.isNull;
    int n = batch.size;
    long[] vector1 = inputColVector1.vector;
    double[] vector2 = inputColVector2.vector;
    
    // return immediately if batch is empty
    if (n == 0) {
      return;
    }
    
    // filter rows with NULL on left input
    int newSize;
    newSize = NullUtil.filterNulls(batch.cols[colNum1], batch.selectedInUse, sel, n);
    if (newSize < n) {
      n = batch.size = newSize;
      batch.selectedInUse = true;
    }
    
    // filter rows with NULL on right input
    newSize = NullUtil.filterNulls(batch.cols[colNum2], batch.selectedInUse, sel, n);
    if (newSize < n) {
      n = batch.size = newSize;
      batch.selectedInUse = true;
    }
    
    // All rows with nulls have been filtered out, so just do normal filter for non-null case
    if (n != 0 && inputColVector1.isRepeating && inputColVector2.isRepeating) {
      
      // All must be selected otherwise size would be zero
      // Repeating property will not change.
      if (!(vector1[0] > vector2[0])) {
        batch.size = 0;
      }
    } else if (inputColVector1.isRepeating) {
      if (batch.selectedInUse) {
        newSize = 0;
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          if (vector1[0] > vector2[i]) {
            sel[newSize++] = i;
          }
        }
        batch.size = newSize;
      } else {
        newSize = 0;
        for(int i = 0; i != n; i++) {
          if (vector1[0] > vector2[i]) {
            sel[newSize++] = i;
          }
        }
        if (newSize < batch.size) {
          batch.size = newSize;
          batch.selectedInUse = true;
        }
      }
    } else if (inputColVector2.isRepeating) {
      if (batch.selectedInUse) {
        newSize = 0;
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          if (vector1[i] > vector2[0]) {
            sel[newSize++] = i;
          }
        }
        batch.size = newSize;
      } else {
        newSize = 0;
        for(int i = 0; i != n; i++) {
          if (vector1[i] > vector2[0]) {
            sel[newSize++] = i;
          }
        }
        if (newSize < batch.size) {
          batch.size = newSize;
          batch.selectedInUse = true;
        }
      }
    } else if (batch.selectedInUse) {
      newSize = 0;
      for(int j = 0; j != n; j++) {
        int i = sel[j];
        if (vector1[i] > vector2[i]) {
          sel[newSize++] = i;
        }
      }
      batch.size = newSize;
    } else {
      newSize = 0;
      for(int i = 0; i != n; i++) {
        if (vector1[i] >  vector2[i]) {
          sel[newSize++] = i;
        }
      }
      if (newSize < batch.size) {
        batch.size = newSize;
        batch.selectedInUse = true;
      }
    }
  }

  @Override
  public String getOutputType() {
    return "boolean";
  }

  @Override
  public int getOutputColumn() {
    return -1;
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

  @Override
  public String vectorExpressionParameters() {
    return "col " + colNum1 + ", col " + + colNum2;
  }

  @Override
  public VectorExpressionDescriptor.Descriptor getDescriptor() {
    return (new VectorExpressionDescriptor.Builder())
        .setMode(
            VectorExpressionDescriptor.Mode.FILTER)
        .setNumArguments(2)
        .setArgumentTypes(
            VectorExpressionDescriptor.ArgumentType.getType("long"),
            VectorExpressionDescriptor.ArgumentType.getType("double"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN,
            VectorExpressionDescriptor.InputExpressionType.COLUMN).build();
  }
}
