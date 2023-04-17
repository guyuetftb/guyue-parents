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
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.expressions.NullUtil;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;
import org.apache.hadoop.hive.ql.exec.vector.*;
import org.apache.hadoop.hive.ql.util.DateTimeMath;
import org.apache.hadoop.hive.serde2.io.DateWritable;

/**
 * Generated from template DateColumnArithmeticTimestampScalarBase.txt, a base class
 * which covers binary arithmetic expressions between a date column and a timestamp scalar.
 */
public class DateColSubtractTimestampScalar extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum;
  private Timestamp value;
  private int outputColumn;
  private Timestamp scratchTimestamp1;
  private DateTimeMath dtm = new DateTimeMath();

  public DateColSubtractTimestampScalar(int colNum, Timestamp value, int outputColumn) {
    this.colNum = colNum;
    this.value = value;
    this.outputColumn = outputColumn;
    scratchTimestamp1 = new Timestamp(0);
  }

  public DateColSubtractTimestampScalar() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    // Input #1 is type date (days).  For the math we convert it to a timestamp.
    LongColumnVector inputColVector1 = (LongColumnVector) batch.cols[colNum];

    // Output is type interval_day_time.
    IntervalDayTimeColumnVector outputColVector = (IntervalDayTimeColumnVector) batch.cols[outputColumn];

    int[] sel = batch.selected;
    boolean[] inputIsNull = inputColVector1.isNull;
    boolean[] outputIsNull = outputColVector.isNull;
    outputColVector.noNulls = inputColVector1.noNulls;
    outputColVector.isRepeating = inputColVector1.isRepeating;
    int n = batch.size;
    long[] vector1 = inputColVector1.vector;

    // return immediately if batch is empty
    if (n == 0) {
      return;
    }

    if (inputColVector1.isRepeating) {
      scratchTimestamp1.setTime(DateWritable.daysToMillis((int) vector1[0]));
      dtm.subtract(
          scratchTimestamp1, value, outputColVector.getScratchIntervalDayTime());
      outputColVector.setFromScratchIntervalDayTime(0);
      // Even if there are no nulls, we always copy over entry 0. Simplifies code.
      outputIsNull[0] = inputIsNull[0];
    } else if (inputColVector1.noNulls) {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          scratchTimestamp1.setTime(DateWritable.daysToMillis((int) vector1[i]));
          dtm.subtract(
             scratchTimestamp1, value, outputColVector.getScratchIntervalDayTime());
          outputColVector.setFromScratchIntervalDayTime(i);
        }
      } else {
        for(int i = 0; i != n; i++) {
          scratchTimestamp1.setTime(DateWritable.daysToMillis((int) vector1[i]));
          dtm.subtract(
             scratchTimestamp1, value, outputColVector.getScratchIntervalDayTime());
          outputColVector.setFromScratchIntervalDayTime(i);
        }
      }
    } else /* there are nulls */ {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          scratchTimestamp1.setTime(DateWritable.daysToMillis((int) vector1[i]));
          dtm.subtract(
             scratchTimestamp1, value, outputColVector.getScratchIntervalDayTime());
          outputColVector.setFromScratchIntervalDayTime(i);
          outputIsNull[i] = inputIsNull[i];
        }
      } else {
        for(int i = 0; i != n; i++) {
          scratchTimestamp1.setTime(DateWritable.daysToMillis((int) vector1[i]));
          dtm.subtract(
             scratchTimestamp1, value, outputColVector.getScratchIntervalDayTime());
          outputColVector.setFromScratchIntervalDayTime(i);
        }
        System.arraycopy(inputIsNull, 0, outputIsNull, 0, n);
      }
    }

    NullUtil.setNullOutputEntriesColScalar(outputColVector, batch.selectedInUse, sel, n);
  }

  @Override
  public int getOutputColumn() {
    return outputColumn;
  }

  @Override
  public String getOutputType() {
    return "interval_day_time";
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
            VectorExpressionDescriptor.ArgumentType.getType("date"),
            VectorExpressionDescriptor.ArgumentType.getType("timestamp"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN,
            VectorExpressionDescriptor.InputExpressionType.SCALAR).build();
  }
}
