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
import org.apache.hadoop.hive.ql.exec.vector.expressions.NullUtil;
import org.apache.hadoop.hive.ql.exec.vector.*;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;
import org.apache.hadoop.hive.ql.util.DateTimeMath;
import org.apache.hadoop.hive.serde2.io.DateWritable;

/**
 * Generated from template DateColumnArithmeticTimestampColumn.txt, a class
 * which covers binary arithmetic expressions between a date column and timestamp column.
 */
public class DateColSubtractTimestampColumn extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum1;
  private int colNum2;
  private int outputColumn;
  private Timestamp scratchTimestamp1;
  private DateTimeMath dtm = new DateTimeMath();

  public DateColSubtractTimestampColumn(int colNum1, int colNum2, int outputColumn) {
    this.colNum1 = colNum1;
    this.colNum2 = colNum2;
    this.outputColumn = outputColumn;
    scratchTimestamp1 = new Timestamp(0);
  }

  public DateColSubtractTimestampColumn() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    // Input #1 is type Date (days).  For the math we convert it to a timestamp.
    LongColumnVector inputColVector1 = (LongColumnVector) batch.cols[colNum1];

    // Input #2 is type timestamp.
    TimestampColumnVector inputColVector2 = (TimestampColumnVector) batch.cols[colNum2];

    // Output is type interval_day_time.
    IntervalDayTimeColumnVector outputColVector = (IntervalDayTimeColumnVector) batch.cols[outputColumn];

    int[] sel = batch.selected;
    int n = batch.size;
    long[] vector1 = inputColVector1.vector;

    // return immediately if batch is empty
    if (n == 0) {
      return;
    }

    outputColVector.isRepeating =
         inputColVector1.isRepeating && inputColVector2.isRepeating
      || inputColVector1.isRepeating && !inputColVector1.noNulls && inputColVector1.isNull[0]
      || inputColVector2.isRepeating && !inputColVector2.noNulls && inputColVector2.isNull[0];

    // Handle nulls first
    NullUtil.propagateNullsColCol(
      inputColVector1, inputColVector2, outputColVector, sel, n, batch.selectedInUse);

    /* Disregard nulls for processing. In other words,
     * the arithmetic operation is performed even if one or
     * more inputs are null. This is to improve speed by avoiding
     * conditional checks in the inner loop.
     */
    if (inputColVector1.isRepeating && inputColVector2.isRepeating) {
      scratchTimestamp1.setTime(DateWritable.daysToMillis((int) vector1[0]));
      dtm.subtract(
          scratchTimestamp1, inputColVector2.asScratchTimestamp(0), outputColVector.getScratchIntervalDayTime());
      outputColVector.setFromScratchIntervalDayTime(0);
    } else if (inputColVector1.isRepeating) {
      scratchTimestamp1.setTime(DateWritable.daysToMillis((int) vector1[0]));
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          dtm.subtract(
              scratchTimestamp1, inputColVector2.asScratchTimestamp(i), outputColVector.getScratchIntervalDayTime());
          outputColVector.setFromScratchIntervalDayTime(i);
        }
      } else {
        for(int i = 0; i != n; i++) {
          dtm.subtract(
              scratchTimestamp1, inputColVector2.asScratchTimestamp(i), outputColVector.getScratchIntervalDayTime());
          outputColVector.setFromScratchIntervalDayTime(i);
        }
      }
    } else if (inputColVector2.isRepeating) {
      Timestamp value2 = inputColVector2.asScratchTimestamp(0);
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          scratchTimestamp1.setTime(DateWritable.daysToMillis((int) vector1[i]));
          dtm.subtract(
              scratchTimestamp1, value2, outputColVector.getScratchIntervalDayTime());
          outputColVector.setFromScratchIntervalDayTime(i);
         }
      } else {
        for(int i = 0; i != n; i++) {
          scratchTimestamp1.setTime(DateWritable.daysToMillis((int) vector1[i]));
          dtm.subtract(
              scratchTimestamp1, value2, outputColVector.getScratchIntervalDayTime());
          outputColVector.setFromScratchIntervalDayTime(i);
        }
      }
    } else {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          scratchTimestamp1.setTime(DateWritable.daysToMillis((int) vector1[i]));
         dtm.subtract(
              scratchTimestamp1, inputColVector2.asScratchTimestamp(i), outputColVector.getScratchIntervalDayTime());
          outputColVector.setFromScratchIntervalDayTime(i);
        }
      } else {
        for(int i = 0; i != n; i++) {
          scratchTimestamp1.setTime(DateWritable.daysToMillis((int) vector1[i]));
          dtm.subtract(
              scratchTimestamp1, inputColVector2.asScratchTimestamp(i), outputColVector.getScratchIntervalDayTime());
          outputColVector.setFromScratchIntervalDayTime(i);
        }
      }
    }

    /* For the case when the output can have null values, follow
     * the convention that the data values must be 1 for long and
     * NaN for double. This is to prevent possible later zero-divide errors
     * in complex arithmetic expressions like col2 / (col1 - 1)
     * in the case when some col1 entries are null.
     */
    NullUtil.setNullDataEntriesIntervalDayTime(outputColVector, batch.selectedInUse, sel, n);
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
    return "col " + colNum1 + ", col " + + colNum2;
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
            VectorExpressionDescriptor.InputExpressionType.COLUMN).build();
  }
}

