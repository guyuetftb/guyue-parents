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
import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;

/**
 * Generated from template FilterTimestampColumnCompareColumn.txt, which covers binary comparison
 * filter expressions between two columns. Output is not produced in a separate column.
 * The selected vector of the input {@link VectorizedRowBatch} is updated for in-place filtering.
 */
public class FilterIntervalDayTimeColEqualIntervalDayTimeColumn extends VectorExpression {

  private static final long serialVersionUID = 1L;

  private int colNum1;
  private int colNum2;

  public FilterIntervalDayTimeColEqualIntervalDayTimeColumn(int colNum1, int colNum2) {
    this.colNum1 = colNum1;
    this.colNum2 = colNum2;
  }

  public FilterIntervalDayTimeColEqualIntervalDayTimeColumn() {
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {

    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

     // Input #1 is type interval_day_time.
    IntervalDayTimeColumnVector inputColVector1 = (IntervalDayTimeColumnVector) batch.cols[colNum1];

     // Input #2 is type interval_day_time.
    IntervalDayTimeColumnVector inputColVector2 = (IntervalDayTimeColumnVector) batch.cols[colNum2];

    int[] sel = batch.selected;
    boolean[] nullPos1 = inputColVector1.isNull;
    boolean[] nullPos2 = inputColVector2.isNull;
    int n = batch.size;

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
        if (!(inputColVector1.compareTo(0, inputColVector2, 0) == 0)) {
          batch.size = 0;
        }
      } else if (inputColVector1.isRepeating) {
        if (batch.selectedInUse) {
          int newSize = 0;
          for(int j = 0; j != n; j++) {
            int i = sel[j];
            if (inputColVector1.compareTo(0, inputColVector2, i) == 0) {
              sel[newSize++] = i;
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (inputColVector1.compareTo(0, inputColVector2, i) == 0) {
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
            if (inputColVector1.compareTo(i, inputColVector2, 0) == 0) {
              sel[newSize++] = i;
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (inputColVector1.compareTo(i, inputColVector2, 0) == 0) {
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
          if (inputColVector1.compareTo(i, inputColVector2, i) == 0) {
            sel[newSize++] = i;
          }
        }
        batch.size = newSize;
      } else {
        int newSize = 0;
        for(int i = 0; i != n; i++) {
          if (inputColVector1.compareTo(i, inputColVector2, i) == 0) {
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
            !(inputColVector1.compareTo(0, inputColVector2, 0) == 0)) {
          batch.size = 0;
        }
      } else if (inputColVector1.isRepeating) {

         // no need to check for nulls in input 1
         if (batch.selectedInUse) {
          int newSize = 0;
          for(int j = 0; j != n; j++) {
            int i = sel[j];
            if (!nullPos2[i]) {
              if (inputColVector1.compareTo(0, inputColVector2, i) == 0) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos2[i]) {
              if (inputColVector1.compareTo(0, inputColVector2, i) == 0) {
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
            if (inputColVector1.compareTo(i, inputColVector2, 0) == 0) {
              sel[newSize++] = i;
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (inputColVector1.compareTo(i, inputColVector2, 0) == 0) {
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
              if (inputColVector1.compareTo(i, inputColVector2, i) == 0) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos2[i]) {
              if (inputColVector1.compareTo(i, inputColVector2, i) == 0) {
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
            !(inputColVector1.compareTo(0, inputColVector2, 0) == 0)) {
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
            if (inputColVector1.compareTo(0, inputColVector2, i) == 0) {
              sel[newSize++] = i;
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (inputColVector1.compareTo(0, inputColVector2, i) == 0) {
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
              if (inputColVector1.compareTo(i, inputColVector2, 0) == 0) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos1[i]) {
              if (inputColVector1.compareTo(i, inputColVector2, 0) == 0) {
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
              if (inputColVector1.compareTo(i, inputColVector2, i) == 0) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos1[i]) {
              if (inputColVector1.compareTo(i, inputColVector2, i) == 0) {
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
            !(inputColVector1.compareTo(0, inputColVector2, 0) == 0)) {
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
              if (inputColVector1.compareTo(0, inputColVector2, i) == 0) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos2[i]) {
              if (inputColVector1.compareTo(0, inputColVector2, i) == 0) {
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
              if (inputColVector1.compareTo(i, inputColVector2, 0) == 0) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos1[i]) {
              if (inputColVector1.compareTo(i, inputColVector2, 0) == 0) {
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
              if (inputColVector1.compareTo(i, inputColVector2, i) == 0) {
                sel[newSize++] = i;
              }
            }
          }
          batch.size = newSize;
        } else {
          int newSize = 0;
          for(int i = 0; i != n; i++) {
            if (!nullPos1[i] && !nullPos2[i]) {
              if (inputColVector1.compareTo(i, inputColVector2, i) == 0) {
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
            VectorExpressionDescriptor.ArgumentType.getType("interval_day_time"),
            VectorExpressionDescriptor.ArgumentType.getType("interval_day_time"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN,
            VectorExpressionDescriptor.InputExpressionType.COLUMN).build();
  }
}
