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

import java.sql.Timestamp;

import org.apache.hadoop.hive.common.type.HiveIntervalDayTime;
import org.apache.hadoop.hive.ql.exec.vector.expressions.VectorExpression;
import org.apache.hadoop.hive.ql.exec.vector.TimestampColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.*;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;

/**
 * Generated from template FilterColumnCompareScalar.txt, which covers binary comparison
 * expressions between a column and a scalar, however output is not produced in a separate column.
 * The selected vector of the input {@link VectorizedRowBatch} is updated for in-place filtering.
 */
public class FilterTimestampColGreaterTimestampScalar extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum;
  private Timestamp value;

  public FilterTimestampColGreaterTimestampScalar(int colNum, Timestamp value) {
    this.colNum = colNum;
    this.value = value;
  }

  public FilterTimestampColGreaterTimestampScalar() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

     // Input #1 is type timestamp.
    TimestampColumnVector inputColVector1 = (TimestampColumnVector) batch.cols[colNum];

    int[] sel = batch.selected;
    boolean[] nullPos = inputColVector1.isNull;
    int n = batch.size;

    // return immediately if batch is empty
    if (n == 0) {
      return;
    }

    if (inputColVector1.noNulls) {
      if (inputColVector1.isRepeating) {
        //All must be selected otherwise size would be zero
        //Repeating property will not change.
        if (!(inputColVector1.compareTo(0, value) > 0)) {
          //Entire batch is filtered out.
          batch.size = 0;
        }
      } else if (batch.selectedInUse) {
        int newSize = 0;
        for(int j=0; j != n; j++) {
          int i = sel[j];
          if (inputColVector1.compareTo(i, value) > 0) {
            sel[newSize++] = i;
          }
        }
        batch.size = newSize;
      } else {
        int newSize = 0;
        for(int i = 0; i != n; i++) {
          if (inputColVector1.compareTo(i, value) > 0) {
            sel[newSize++] = i;
          }
        }
        if (newSize < n) {
          batch.size = newSize;
          batch.selectedInUse = true;
        }
      }
    } else {
      if (inputColVector1.isRepeating) {
        //All must be selected otherwise size would be zero
        //Repeating property will not change.
        if (!nullPos[0]) {
          if (!(inputColVector1.compareTo(0, value) > 0)) {
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
           if (inputColVector1.compareTo(i, value) > 0) {
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
            if (inputColVector1.compareTo(i, value) > 0) {
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

  @Override
  public String vectorExpressionParameters() {
    return "col " + colNum + ", val " + value.toString();
  }

  @Override
  public VectorExpressionDescriptor.Descriptor getDescriptor() {
    return (new VectorExpressionDescriptor.Builder())
        .setMode(
            VectorExpressionDescriptor.Mode.FILTER)
        .setNumArguments(2)
        .setArgumentTypes(
            VectorExpressionDescriptor.ArgumentType.getType("timestamp"),
            VectorExpressionDescriptor.ArgumentType.getType("timestamp"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN,
            VectorExpressionDescriptor.InputExpressionType.SCALAR).build();
  }
}
