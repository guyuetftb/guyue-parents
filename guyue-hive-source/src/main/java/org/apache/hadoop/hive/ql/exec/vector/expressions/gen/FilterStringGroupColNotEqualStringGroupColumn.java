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
import org.apache.hadoop.hive.ql.exec.vector.expressions.StringExpr;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;

/**
 * Filter the rows in a batch by comparing one string column to another. 
 * This code is generated from a template.
 */
public class FilterStringGroupColNotEqualStringGroupColumn extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum1;
  private int colNum2;

  public FilterStringGroupColNotEqualStringGroupColumn(int colNum1, int colNum2) { 
    this.colNum1 = colNum1;
    this.colNum2 = colNum2;
  }

  public FilterStringGroupColNotEqualStringGroupColumn() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    BytesColumnVector inputColVector1 = (BytesColumnVector) batch.cols[colNum1];
    BytesColumnVector inputColVector2 = (BytesColumnVector) batch.cols[colNum2];
    int[] sel = batch.selected;
    boolean[] nullPos1 = inputColVector1.isNull;
    boolean[] nullPos2 = inputColVector2.isNull;
    int n = batch.size;
    byte[][] vector1 = inputColVector1.vector;
    byte[][] vector2 = inputColVector2.vector;
    int[] start1 = inputColVector1.start;
    int[] start2 = inputColVector2.start;
    int[] length1 = inputColVector1.length;
    int[] length2 = inputColVector2.length;
    
    // return immediately if batch is empty
    if (n == 0) {
      return;
    }
    
    // handle case where neither input has nulls
    if (inputColVector1.noNulls && inputColVector2.noNulls) {
      if (inputColVector1.isRepeating && inputColVector2.isRepeating) {
      
        /* Either all must remain selected or all will be eliminated.
         * Repeating property will not change.
         */
        if (!(!StringExpr.equal(vector1[0], start1[0], length1[0], 
                                 vector2[0], start2[0], length2[0]))) {
          batch.size = 0;
        }      
      } else if (inputColVector1.isRepeating) {
        if (batch.selectedInUse) {
          int newSize = 0;
          for(int j = 0; j != n; j++) {
            int i = sel[j];
            if (!StringExpr.equal(vector1[0], start1[0], length1[0], 
                                   vector2[i], start2[i], length2[i])) {
              sel[newSize++] = i;
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!StringExpr.equal(vector1[0], start1[0], length1[0], 
                                   vector2[i], start2[i], length2[i])) {
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
          int newSize = 0;
          for(int j = 0; j != n; j++) {
            int i = sel[j];
            if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                   vector2[0], start2[0], length2[0])) {
              sel[newSize++] = i;
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                   vector2[0], start2[0], length2[0])) {
              sel[newSize++] = i;
            }
          }
          if (newSize < batch.size) {
            batch.size = newSize;
            batch.selectedInUse = true;
          }
        }
      } else if (batch.selectedInUse) {
        int newSize = 0;
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                 vector2[i], start2[i], length2[i])) {
            sel[newSize++] = i;
          }
        }
        batch.size = newSize;
      } else {
        int newSize = 0;
        for(int i = 0; i != n; i++) {
          if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                 vector2[i], start2[i], length2[i])) {
            sel[newSize++] = i;
          }
        }
        if (newSize < batch.size) {
          batch.size = newSize;
          batch.selectedInUse = true;
        }
      }
    
    // handle case where only input 2 has nulls
    } else if (inputColVector1.noNulls) { 
      if (inputColVector1.isRepeating && inputColVector2.isRepeating) {
        if (nullPos2[0] ||
            !(!StringExpr.equal(vector1[0], start1[0], length1[0], 
                               vector2[0], start2[0], length2[0]))) {
          batch.size = 0; 
        } 
      } else if (inputColVector1.isRepeating) {
         
         // no need to check for nulls in input 1
         if (batch.selectedInUse) {
          int newSize = 0;
          for(int j = 0; j != n; j++) {
            int i = sel[j];
            if (!nullPos2[i]) {
              if (!StringExpr.equal(vector1[0], start1[0], length1[0], 
                                     vector2[i], start2[i], length2[i])) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos2[i]) {
              if (!StringExpr.equal(vector1[0], start1[0], length1[0], 
                                     vector2[i], start2[i], length2[i])) {
                sel[newSize++] = i;
              }
            }
          }
          if (newSize < batch.size) {
            batch.size = newSize;
            batch.selectedInUse = true;
          }
        }
      } else if (inputColVector2.isRepeating) {
        if (nullPos2[0]) {
        
          // no values will qualify because every comparison will be with NULL
          batch.size = 0;
          return;
        }
        if (batch.selectedInUse) {
          int newSize = 0;
          for(int j = 0; j != n; j++) {
            int i = sel[j];
            if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                   vector2[0], start2[0], length2[0])) {
              sel[newSize++] = i;
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                   vector2[0], start2[0], length2[0])) {
              sel[newSize++] = i;
            }
          }
          if (newSize < batch.size) {
            batch.size = newSize;
            batch.selectedInUse = true;
          }
        }
      } else { // neither input is repeating
        if (batch.selectedInUse) {
          int newSize = 0;
          for(int j = 0; j != n; j++) {
            int i = sel[j];
            if (!nullPos2[i]) {
              if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                     vector2[i], start2[i], length2[i])) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos2[i]) {
              if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                     vector2[i], start2[i], length2[i])) {
                sel[newSize++] = i;
              }
            }
          }
          if (newSize < batch.size) {
            batch.size = newSize;
            batch.selectedInUse = true;
          }
        }      
      }
      
    // handle case where only input 1 has nulls
    } else if (inputColVector2.noNulls) {
      if (inputColVector1.isRepeating && inputColVector2.isRepeating) {
        if (nullPos1[0] ||
            !(!StringExpr.equal(vector1[0], start1[0], length1[0], 
                               vector2[0], start2[0], length2[0]))) {
          batch.size = 0; 
          return;
        } 
      } else if (inputColVector1.isRepeating) {
        if (nullPos1[0]) {
        
          // if repeating value is null then every comparison will fail so nothing qualifies
          batch.size = 0;
          return; 
        }
        if (batch.selectedInUse) {
          int newSize = 0;
          for(int j = 0; j != n; j++) {
            int i = sel[j];
            if (!StringExpr.equal(vector1[0], start1[0], length1[0], 
                                   vector2[i], start2[i], length2[i])) {
              sel[newSize++] = i;
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!StringExpr.equal(vector1[0], start1[0], length1[0], 
                                   vector2[i], start2[i], length2[i])) {
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
          int newSize = 0;
          for(int j = 0; j != n; j++) {
            int i = sel[j];
            if (!nullPos1[i]) {
              if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                     vector2[0], start2[0], length2[0])) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos1[i]) {
              if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                     vector2[0], start2[0], length2[0])) {
                sel[newSize++] = i;
              }
            }
          }
          if (newSize < batch.size) {
            batch.size = newSize;
            batch.selectedInUse = true;
          }
        }
      } else { // neither input is repeating
         if (batch.selectedInUse) {
          int newSize = 0;
          for(int j = 0; j != n; j++) {
            int i = sel[j];
            if (!nullPos1[i]) {
              if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                     vector2[i], start2[i], length2[i])) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos1[i]) {
              if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                     vector2[i], start2[i], length2[i])) {
                sel[newSize++] = i;
              }
            }
          }
          if (newSize < batch.size) {
            batch.size = newSize;
            batch.selectedInUse = true;
          }
        }      
      }
            
    // handle case where both inputs have nulls
    } else {
      if (inputColVector1.isRepeating && inputColVector2.isRepeating) {
        if (nullPos1[0] || nullPos2[0] ||
            !(!StringExpr.equal(vector1[0], start1[0], length1[0], 
                               vector2[0], start2[0], length2[0]))) {
          batch.size = 0; 
        } 
      } else if (inputColVector1.isRepeating) {
         if (nullPos1[0]) {
           batch.size = 0;
           return;
         }
         if (batch.selectedInUse) {
          int newSize = 0;
          for(int j = 0; j != n; j++) {
            int i = sel[j];
            if (!nullPos2[i]) {
              if (!StringExpr.equal(vector1[0], start1[0], length1[0], 
                                     vector2[i], start2[i], length2[i])) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos2[i]) {
              if (!StringExpr.equal(vector1[0], start1[0], length1[0], 
                                     vector2[i], start2[i], length2[i])) {
                sel[newSize++] = i;
              }
            }
          }
          if (newSize < batch.size) {
            batch.size = newSize;
            batch.selectedInUse = true;
          }
        }
      } else if (inputColVector2.isRepeating) {
        if (nullPos2[0]) {
          batch.size = 0;
          return;
        }
        if (batch.selectedInUse) {
          int newSize = 0;
          for(int j = 0; j != n; j++) {
            int i = sel[j];
            if (!nullPos1[i]) {
              if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                     vector2[0], start2[0], length2[0])) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos1[i]) {
              if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                     vector2[0], start2[0], length2[0])) {
                sel[newSize++] = i;
              }
            }
          }
          if (newSize < batch.size) {
            batch.size = newSize;
            batch.selectedInUse = true;
          }
        }
      } else { // neither input is repeating
         if (batch.selectedInUse) {
          int newSize = 0;
          for(int j = 0; j != n; j++) {
            int i = sel[j];
            if (!nullPos1[i] && !nullPos2[i]) {
              if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                     vector2[i], start2[i], length2[i])) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos1[i] && !nullPos2[i]) {
              if (!StringExpr.equal(vector1[i], start1[i], length1[i], 
                                     vector2[i], start2[i], length2[i])) {
                sel[newSize++] = i;
              }
            }
          }
          if (newSize < batch.size) {
            batch.size = newSize;
            batch.selectedInUse = true;
          }
        }      
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
            VectorExpressionDescriptor.ArgumentType.STRING_FAMILY,
            VectorExpressionDescriptor.ArgumentType.STRING_FAMILY)
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN,
            VectorExpressionDescriptor.InputExpressionType.COLUMN).build();
  }
}
