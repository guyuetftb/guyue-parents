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

import java.sql.Date;
import org.apache.hadoop.hive.common.type.HiveIntervalYearMonth;
import org.apache.hadoop.hive.ql.exec.vector.expressions.VectorExpression;
import org.apache.hadoop.hive.ql.exec.vector.expressions.NullUtil;
import org.apache.hadoop.hive.ql.exec.vector.*;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;
import org.apache.hadoop.hive.ql.util.DateTimeMath;
import org.apache.hadoop.hive.serde2.io.DateWritable;

/**
 * Generated from template DateColumnArithmeticIntervalYearMonthColumn.txt, which covers binary arithmetic
 * expressions between date and interval year month columns.
 */
public class DateColAddIntervalYearMonthColumn extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum1;
  private int colNum2;
  private int outputColumn;
  private Date scratchDate1;
  private HiveIntervalYearMonth scratchIntervalYearMonth2;
  private Date outputDate;
  private DateTimeMath dtm = new DateTimeMath();

  public DateColAddIntervalYearMonthColumn(int colNum1, int colNum2, int outputColumn) {
    this.colNum1 = colNum1;
    this.colNum2 = colNum2;
    this.outputColumn = outputColumn;
    scratchDate1 = new Date(0);
    scratchIntervalYearMonth2 = new HiveIntervalYearMonth();
    outputDate = new Date(0);
  }

  public DateColAddIntervalYearMonthColumn() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    // Input #1 is type date.
    LongColumnVector inputColVector1 = (LongColumnVector) batch.cols[colNum1];

    // Input #2 is type interval_year_month.
    LongColumnVector inputColVector2 = (LongColumnVector) batch.cols[colNum2];

    // Output is type date.
    LongColumnVector outputColVector = (LongColumnVector) batch.cols[outputColumn];

    int[] sel = batch.selected;
    int n = batch.size;
    long[] vector1 = inputColVector1.vector;
    long[] vector2 = inputColVector2.vector;
    long[] outputVector = outputColVector.vector;

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
      scratchDate1.setTime(DateWritable.daysToMillis((int) vector1[0]));
      scratchIntervalYearMonth2.set((int) vector2[0]);
      dtm.add(
          scratchDate1, scratchIntervalYearMonth2,  outputDate);
      outputVector[0] = DateWritable.dateToDays(outputDate);
    } else if (inputColVector1.isRepeating) {
      scratchDate1.setTime(DateWritable.daysToMillis((int) vector1[0]));
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          scratchIntervalYearMonth2.set((int) vector2[i]);
          dtm.add(
              scratchDate1, scratchIntervalYearMonth2,  outputDate);
          outputVector[i] = DateWritable.dateToDays(outputDate);
        }
      } else {
        for(int i = 0; i != n; i++) {
          scratchIntervalYearMonth2.set((int) vector2[i]);
          dtm.add(
              scratchDate1, scratchIntervalYearMonth2,  outputDate);
          outputVector[i] = DateWritable.dateToDays(outputDate);
        }
      }
    } else if (inputColVector2.isRepeating) {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          scratchDate1.setTime(DateWritable.daysToMillis((int) vector1[i]));
          scratchIntervalYearMonth2.set((int) vector2[i]);
          dtm.add(
              scratchDate1, scratchIntervalYearMonth2,  outputDate);
          outputVector[i] = DateWritable.dateToDays(outputDate);
        }
      } else {
        for(int i = 0; i != n; i++) {
          scratchDate1.setTime(DateWritable.daysToMillis((int) vector1[i]));
          scratchIntervalYearMonth2.set((int) vector2[i]);
          dtm.add(
              scratchDate1, scratchIntervalYearMonth2,  outputDate);
          outputVector[i] = DateWritable.dateToDays(outputDate);
        }
      }
    } else {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          scratchDate1.setTime(DateWritable.daysToMillis((int) vector1[i]));
          scratchIntervalYearMonth2.set((int) vector2[i]);
          dtm.add(
              scratchDate1, scratchIntervalYearMonth2,  outputDate);
          outputVector[i] = DateWritable.dateToDays(outputDate);
        }
      } else {
        for(int i = 0; i != n; i++) {
          scratchDate1.setTime(DateWritable.daysToMillis((int) vector1[i]));
          scratchIntervalYearMonth2.set((int) vector2[i]);
          dtm.add(
              scratchDate1, scratchIntervalYearMonth2,  outputDate);
          outputVector[i] = DateWritable.dateToDays(outputDate);
        }
      }
    }

    /* For the case when the output can have null values, follow
     * the convention that the data values must be 1 for long and
     * NaN for double. This is to prevent possible later zero-divide errors
     * in complex arithmetic expressions like col2 / (col1 - 1)
     * in the case when some col1 entries are null.
     */
    NullUtil.setNullDataEntriesLong(outputColVector, batch.selectedInUse, sel, n);
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
            VectorExpressionDescriptor.ArgumentType.getType("interval_year_month"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN,
            VectorExpressionDescriptor.InputExpressionType.COLUMN).build();
  }
}

