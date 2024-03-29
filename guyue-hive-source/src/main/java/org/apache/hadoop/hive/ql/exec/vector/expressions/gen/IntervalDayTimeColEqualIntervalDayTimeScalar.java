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
import org.apache.hadoop.hive.ql.exec.vector.*;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;

/**
 * Generated from template TimestampColumnCompareTimestampScalar.txt, which covers binary comparison
 * expressions between a column and a scalar. The boolean output is stored in a
 * separate boolean column.
 */
public class IntervalDayTimeColEqualIntervalDayTimeScalar extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum;
  private HiveIntervalDayTime value;
  private int outputColumn;

  public IntervalDayTimeColEqualIntervalDayTimeScalar(int colNum, HiveIntervalDayTime value, int outputColumn) {
    this.colNum = colNum;
    this.value = value;
    this.outputColumn = outputColumn;
  }

  public IntervalDayTimeColEqualIntervalDayTimeScalar() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

     // Input #1 is type interval_day_time.
    IntervalDayTimeColumnVector inputColVector1 = (IntervalDayTimeColumnVector) batch.cols[colNum];

    LongColumnVector outputColVector = (LongColumnVector) batch.cols[outputColumn];

    int[] sel = batch.selected;
    boolean[] nullPos = inputColVector1.isNull;
    boolean[] outNulls = outputColVector.isNull;
    int n = batch.size;
    long[] outputVector = outputColVector.vector;

    // return immediately if batch is empty
    if (n == 0) {
      return;
    }

    outputColVector.isRepeating = false;
    outputColVector.noNulls = inputColVector1.noNulls;
    if (inputColVector1.noNulls) {
      if (inputColVector1.isRepeating) {
        //All must be selected otherwise size would be zero
        //Repeating property will not change.
        outputVector[0] = inputColVector1.compareTo(0, value) == 0 ? 1 : 0;
        outputColVector.isRepeating = true;
      } else if (batch.selectedInUse) {
        for(int j=0; j != n; j++) {
          int i = sel[j];
          outputVector[i] = inputColVector1.compareTo(i, value) == 0 ? 1 : 0;
        }
      } else {
        for(int i = 0; i != n; i++) {
          outputVector[i] = inputColVector1.compareTo(i, value) == 0 ? 1 : 0;
        }
      }
    } else {
      if (inputColVector1.isRepeating) {
        //All must be selected otherwise size would be zero
        //Repeating property will not change.
        if (!nullPos[0]) {
          outputVector[0] = inputColVector1.compareTo(0, value) == 0 ? 1 : 0;
          outNulls[0] = false;
        } else {
          outNulls[0] = true;
        }
        outputColVector.isRepeating = true;
      } else if (batch.selectedInUse) {
        for(int j=0; j != n; j++) {
          int i = sel[j];
          if (!nullPos[i]) {
            outputVector[i] = inputColVector1.compareTo(i, value) == 0 ? 1 : 0;
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
            outputVector[i] = inputColVector1.compareTo(i, value) == 0 ? 1 : 0;
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
    return "col " + colNum + ", val " + value.toString();
  }

  @Override
  public VectorExpressionDescriptor.Descriptor getDescriptor() {
    return (new VectorExpressionDescriptor.Builder())
        .setMode(
            VectorExpressionDescriptor.Mode.PROJECTION)
        .setNumArguments(2)
        .setArgumentTypes(
            VectorExpressionDescriptor.ArgumentType.getType("interval_day_time"),
            VectorExpressionDescriptor.ArgumentType.getType("interval_day_time"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN,
            VectorExpressionDescriptor.InputExpressionType.SCALAR).build();
  }
}
