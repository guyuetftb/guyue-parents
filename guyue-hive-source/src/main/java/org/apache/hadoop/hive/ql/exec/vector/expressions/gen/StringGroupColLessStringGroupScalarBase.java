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
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;

/**
 * This is a generated class to evaluate a < comparison on a vector of strings.
 */
public abstract class StringGroupColLessStringGroupScalarBase extends VectorExpression {

  private static final long serialVersionUID = 1L;

  protected int colNum;
  protected byte[] value;
  protected int outputColumn;

  @Override
  public void evaluate(VectorizedRowBatch batch) {
    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }
    BytesColumnVector inputColVector = (BytesColumnVector) batch.cols[colNum];
    LongColumnVector outputColVector = (LongColumnVector) batch.cols[outputColumn];
    int[] sel = batch.selected;
    boolean[] nullPos = inputColVector.isNull;
    boolean[] outNull = outputColVector.isNull;
    int n = batch.size;
    byte[][] vector = inputColVector.vector;
    int[] length = inputColVector.length;
    int[] start = inputColVector.start;
    long[] outVector = outputColVector.vector;
    

    // return immediately if batch is empty
    if (n == 0) {
      return;
    }
   
    outputColVector.isRepeating = false; 
    if (inputColVector.noNulls) {
      outputColVector.noNulls = true;
      if (inputColVector.isRepeating) {
        outputColVector.isRepeating = true; 
        if (StringExpr.compare(vector[0], start[0], length[0], value, 0, value.length)< 0) {
          outVector[0] = 1;
        } else {
          outVector[0] = 0;
        }
      } else if (batch.selectedInUse) {
        for(int j=0; j != n; j++) {
          int i = sel[j];
          if (StringExpr.compare(vector[i], start[i], length[i], value, 0, value.length)< 0) {
            outVector[i] = 1;
          } else {
            outVector[i] = 0;
          }
        }
      } else {
        for(int i = 0; i != n; i++) {
          if (StringExpr.compare(vector[i], start[i], length[i], value, 0, value.length)< 0) {
            outVector[i] = 1;
          } else {
            outVector[i] = 0;
          }
        }
      }
    } else {
      outputColVector.noNulls = false;
      if (inputColVector.isRepeating) {
        outputColVector.isRepeating = true;
        outNull[0] = nullPos[0];
        if (!nullPos[0]) {
          if (StringExpr.compare(vector[0], start[0], length[0], value, 0, value.length)< 0) {
            outVector[0] = 1;
          } else {
            outVector[0] = 0;
          }
        } 
      } else if (batch.selectedInUse) {
        for(int j=0; j != n; j++) {
          int i = sel[j];
          outNull[i] = nullPos[i];
          if (!nullPos[i]) {
            if (StringExpr.compare(vector[i], start[i], length[i], value, 0, value.length)< 0) {
              outVector[i] = 1;
            } else {
              outVector[i] = 0;
            }
          }
        }
      } else {
        for(int i = 0; i != n; i++) {
          outNull[i] = nullPos[i];
          if (!nullPos[i]) {
            if (StringExpr.compare(vector[i], start[i], length[i], value, 0, value.length)< 0) {
              outVector[i] = 1;
            } else {
              outVector[i] = 0;
            }
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
    return "boolean";
  }
  
  public int getColNum() {
    return colNum;
  }

  public void setColNum(int colNum) {
    this.colNum = colNum;
  }

  public byte[] getValue() {
    return value;
  }

  public void setValue(byte[] value) {
    this.value = value;
  }

  public void setOutputColumn(int outputColumn) {
    this.outputColumn = outputColumn;
  }

  @Override
  public String vectorExpressionParameters() {
    return "col " + colNum + ", val " + displayUtf8Bytes(value);
  }
}
