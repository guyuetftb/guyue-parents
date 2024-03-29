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

import java.nio.charset.StandardCharsets;

import org.apache.hadoop.hive.ql.exec.vector.expressions.VectorExpression;
import org.apache.hadoop.hive.ql.exec.vector.expressions.StringExpr;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;


/**
 * This is a generated class to evaluate a [NOT] BETWEEN comparison on a vector of strings.
 */
public class FilterStringColumnBetween extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum;
  private byte[] left;
  private byte[] right;

  public FilterStringColumnBetween(int colNum, byte[] left, byte[] right) {
    this.colNum = colNum;
    this.left = left;
    this.right = right;
  }

  public FilterStringColumnBetween() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {
    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }
    BytesColumnVector inputColVector = (BytesColumnVector) batch.cols[colNum];
    int[] sel = batch.selected;
    boolean[] nullPos = inputColVector.isNull;
    int n = batch.size;
    byte[][] vector = inputColVector.vector;
    int[] length = inputColVector.length;
    int[] start = inputColVector.start;
    

    // return immediately if batch is empty
    if (n == 0) {
      return;
    }
    
    if (inputColVector.noNulls) {
      if (inputColVector.isRepeating) {
      
        // All must be selected otherwise size would be zero. Repeating property will not change.
        if ((StringExpr.compare(vector[0], start[0], length[0], left, 0, left.length) < 0
            || StringExpr.compare(right, 0, right.length, vector[0], start[0], length[0]) < 0)) {

          //Entire batch is filtered out.
          batch.size = 0;
        }
      } else if (batch.selectedInUse) {
        int newSize = 0;
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          if ((StringExpr.compare(left, 0, left.length, vector[i], start[i], length[i]) <= 0
              && StringExpr.compare(vector[i], start[i], length[i], right, 0, right.length) <= 0)) {
            sel[newSize++] = i;
          }
        }
        batch.size = newSize;
      } else {
        int newSize = 0;
        for(int i = 0; i != n; i++) {
          if ((StringExpr.compare(left, 0, left.length, vector[i], start[i], length[i]) <= 0
              && StringExpr.compare(vector[i], start[i], length[i], right, 0, right.length) <= 0)) {
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
      
        // All must be selected otherwise size would be zero. Repeating property will not change.
        if (!nullPos[0]) {
          if ((StringExpr.compare(vector[0], start[0], length[0], left, 0, left.length) < 0
            || StringExpr.compare(right, 0, right.length, vector[0], start[0], length[0]) < 0)) {

            //Entire batch is filtered out.
            batch.size = 0;
          }
        } else {
          batch.size = 0;
        }
      } else if (batch.selectedInUse) {
        int newSize = 0;
        for(int j=0; j != n; j++) {
          int i = sel[j];
          if (!nullPos[i]) {
           if ((StringExpr.compare(left, 0, left.length, vector[i], start[i], length[i]) <= 0
              && StringExpr.compare(vector[i], start[i], length[i], right, 0, right.length) <= 0)) {
             sel[newSize++] = i;
           }
          }
        }
        
        //Change the selected vector
        batch.size = newSize;
      } else {
        int newSize = 0;
        for(int i = 0; i != n; i++) {
          if (!nullPos[i]) {
            if ((StringExpr.compare(left, 0, left.length, vector[i], start[i], length[i]) <= 0
              && StringExpr.compare(vector[i], start[i], length[i], right, 0, right.length) <= 0)) {
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
  
  public int getColNum() {
    return colNum;
  }

  public void setColNum(int colNum) {
    this.colNum = colNum;
  }

  public byte[] getLeftValue() {
    return left;
  }

  public void setLeftValue(byte[] value) {
    this.left = value;
  }
  
  public byte[] getRightValue() {
    return right;
  }

  public void setRightValue(byte[] value) {
    this.right = value;
  }

  @Override
  public String vectorExpressionParameters() {
    return "col " + colNum + ", left " + displayUtf8Bytes(left) + ", right " + displayUtf8Bytes(right);
  }

  @Override
  public VectorExpressionDescriptor.Descriptor getDescriptor() {
    return (new VectorExpressionDescriptor.Builder())
        .setMode(
            VectorExpressionDescriptor.Mode.FILTER)
        .setNumArguments(3)
        .setArgumentTypes(
            VectorExpressionDescriptor.ArgumentType.STRING,
            VectorExpressionDescriptor.ArgumentType.STRING,
            VectorExpressionDescriptor.ArgumentType.STRING)
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN,
            VectorExpressionDescriptor.InputExpressionType.SCALAR,
            VectorExpressionDescriptor.InputExpressionType.SCALAR).build();
  }
  
}
