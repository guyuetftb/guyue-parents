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

import org.apache.hadoop.hive.common.type.HiveIntervalYearMonth;
import org.apache.hadoop.hive.ql.exec.vector.expressions.VectorExpression;
import org.apache.hadoop.hive.ql.exec.vector.TimestampColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.expressions.NullUtil;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;
import org.apache.hadoop.hive.ql.exec.vector.*;
import org.apache.hadoop.hive.ql.util.DateTimeMath;

/**
 * Generated from template TimestampColumnArithmeticIntervalYearMonthScalar.txt, which covers binary arithmetic
 * expressions between a column and a scalar.
 */
public class TimestampColSubtractIntervalYearMonthScalar extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum;
  private HiveIntervalYearMonth value;
  private int outputColumn;
  private DateTimeMath dtm = new DateTimeMath();

  public TimestampColSubtractIntervalYearMonthScalar(int colNum, long value, int outputColumn) {
    this.colNum = colNum;
    this.value = new HiveIntervalYearMonth((int) value);
    this.outputColumn = outputColumn;
  }

  public TimestampColSubtractIntervalYearMonthScalar() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    // Input #1 is type Timestamp.
    TimestampColumnVector inputColVector1 = (TimestampColumnVector) batch.cols[colNum];

    // Output is type Timestamp.
    TimestampColumnVector outputColVector = (TimestampColumnVector) batch.cols[outputColumn];

    int[] sel = batch.selected;
    boolean[] inputIsNull = inputColVector1.isNull;
    boolean[] outputIsNull = outputColVector.isNull;
    outputColVector.noNulls = inputColVector1.noNulls;
    outputColVector.isRepeating = inputColVector1.isRepeating;
    int n = batch.size;

    // return immediately if batch is empty
    if (n == 0) {
      return;
    }

    if (inputColVector1.isRepeating) {
      dtm.subtract(
          inputColVector1.asScratchTimestamp(0), value, outputColVector.getScratchTimestamp());
      outputColVector.setFromScratchTimestamp(0);
      // Even if there are no nulls, we always copy over entry 0. Simplifies code.
      outputIsNull[0] = inputIsNull[0];
    } else if (inputColVector1.noNulls) {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          dtm.subtract(
              inputColVector1.asScratchTimestamp(i), value, outputColVector.getScratchTimestamp());
          outputColVector.setFromScratchTimestamp(i);
        }
      } else {
        for(int i = 0; i != n; i++) {
          dtm.subtract(
              inputColVector1.asScratchTimestamp(i), value, outputColVector.getScratchTimestamp());
          outputColVector.setFromScratchTimestamp(i);
        }
      }
    } else /* there are nulls */ {
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          dtm.subtract(
              inputColVector1.asScratchTimestamp(i), value, outputColVector.getScratchTimestamp());
          outputColVector.setFromScratchTimestamp(i);
          outputIsNull[i] = inputIsNull[i];
        }
      } else {
        for(int i = 0; i != n; i++) {
          dtm.subtract(
              inputColVector1.asScratchTimestamp(i), value, outputColVector.getScratchTimestamp());
          outputColVector.setFromScratchTimestamp(i);
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
    return "timestamp";
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
            VectorExpressionDescriptor.ArgumentType.getType("timestamp"),
            VectorExpressionDescriptor.ArgumentType.getType("interval_year_month"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN,
            VectorExpressionDescriptor.InputExpressionType.SCALAR).build();
  }
}
