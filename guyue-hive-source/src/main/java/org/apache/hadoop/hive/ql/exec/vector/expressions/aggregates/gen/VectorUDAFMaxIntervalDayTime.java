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

package org.apache.hadoop.hive.ql.exec.vector.expressions.aggregates.gen;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.vector.expressions.VectorExpression;
import org.apache.hadoop.hive.ql.exec.vector.expressions.aggregates.VectorAggregateExpression;
import org.apache.hadoop.hive.ql.exec.vector.expressions.VectorExpressionWriter;
import org.apache.hadoop.hive.ql.exec.vector.expressions.VectorExpressionWriterFactory;
import org.apache.hadoop.hive.ql.exec.vector.VectorAggregationBufferRow;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.IntervalDayTimeColumnVector;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.plan.AggregationDesc;
import org.apache.hadoop.hive.ql.util.JavaDataModel;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.common.type.HiveIntervalDayTime;

/**
* VectorUDAFMaxIntervalDayTime. Vectorized implementation for MIN/MAX aggregates.
*/
@Description(name = "max",
    value = "_FUNC_(expr) - Returns the maximum value of expr (vectorized, type: interval_day_time)")
public class VectorUDAFMaxIntervalDayTime extends VectorAggregateExpression {

    private static final long serialVersionUID = 1L;

    /**
     * class for storing the current aggregate value.
     */
    static private final class Aggregation implements AggregationBuffer {

      private static final long serialVersionUID = 1L;

      transient private final HiveIntervalDayTime value;

      /**
      * Value is explicitly (re)initialized in reset()
      */
      transient private boolean isNull = true;

      public Aggregation() {
        value = new HiveIntervalDayTime();
      }

      public void checkValue(IntervalDayTimeColumnVector colVector, int index) {
        if (isNull) {
          isNull = false;
          colVector.intervalDayTimeUpdate(this.value, index);
        } else if (colVector.compareTo(this.value, index) < 0) {
          colVector.intervalDayTimeUpdate(this.value, index);
        }
      }

      @Override
      public int getVariableSize() {
        throw new UnsupportedOperationException();
      }

      @Override
      public void reset () {
        isNull = true;
        this.value.set(0, 0);
      }
    }

    private VectorExpression inputExpression;

    @Override
    public VectorExpression inputExpression() {
      return inputExpression;
    }

    private transient VectorExpressionWriter resultWriter;

    public VectorUDAFMaxIntervalDayTime(VectorExpression inputExpression) {
      this();
      this.inputExpression = inputExpression;
    }

    public VectorUDAFMaxIntervalDayTime() {
      super();
    }

    @Override
    public void init(AggregationDesc desc) throws HiveException {
      resultWriter = VectorExpressionWriterFactory.genVectorExpressionWritable(
          desc.getParameters().get(0));
    }

    private Aggregation getCurrentAggregationBuffer(
        VectorAggregationBufferRow[] aggregationBufferSets,
        int aggregrateIndex,
        int row) {
      VectorAggregationBufferRow mySet = aggregationBufferSets[row];
      Aggregation myagg = (Aggregation) mySet.getAggregationBuffer(aggregrateIndex);
      return myagg;
    }

    @Override
    public void aggregateInputSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregrateIndex,
      VectorizedRowBatch batch) throws HiveException {

      int batchSize = batch.size;

      if (batchSize == 0) {
        return;
      }

      inputExpression.evaluate(batch);

      IntervalDayTimeColumnVector inputColVector = (IntervalDayTimeColumnVector)batch.
        cols[this.inputExpression.getOutputColumn()];

      if (inputColVector.noNulls) {
        if (inputColVector.isRepeating) {
          iterateNoNullsRepeatingWithAggregationSelection(
            aggregationBufferSets, aggregrateIndex,
            inputColVector, batchSize);
        } else {
          if (batch.selectedInUse) {
            iterateNoNullsSelectionWithAggregationSelection(
              aggregationBufferSets, aggregrateIndex,
              inputColVector, batch.selected, batchSize);
          } else {
            iterateNoNullsWithAggregationSelection(
              aggregationBufferSets, aggregrateIndex,
              inputColVector, batchSize);
          }
        }
      } else {
        if (inputColVector.isRepeating) {
          if (batch.selectedInUse) {
            iterateHasNullsRepeatingSelectionWithAggregationSelection(
              aggregationBufferSets, aggregrateIndex,
              inputColVector, batchSize, batch.selected, inputColVector.isNull);
          } else {
            iterateHasNullsRepeatingWithAggregationSelection(
              aggregationBufferSets, aggregrateIndex,
              inputColVector, batchSize, inputColVector.isNull);
          }
        } else {
          if (batch.selectedInUse) {
            iterateHasNullsSelectionWithAggregationSelection(
              aggregationBufferSets, aggregrateIndex,
              inputColVector, batchSize, batch.selected, inputColVector.isNull);
          } else {
            iterateHasNullsWithAggregationSelection(
              aggregationBufferSets, aggregrateIndex,
              inputColVector, batchSize, inputColVector.isNull);
          }
        }
      }
    }

    private void iterateNoNullsRepeatingWithAggregationSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregrateIndex,
      IntervalDayTimeColumnVector inputColVector,
      int batchSize) {

      for (int i=0; i < batchSize; ++i) {
        Aggregation myagg = getCurrentAggregationBuffer(
          aggregationBufferSets,
          aggregrateIndex,
          i);
        // Repeating use index 0.
        myagg.checkValue(inputColVector, 0);
      }
    }

    private void iterateNoNullsSelectionWithAggregationSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregrateIndex,
      IntervalDayTimeColumnVector inputColVector,
      int[] selection,
      int batchSize) {

      for (int i=0; i < batchSize; ++i) {
        Aggregation myagg = getCurrentAggregationBuffer(
          aggregationBufferSets,
          aggregrateIndex,
          i);
        myagg.checkValue(inputColVector, selection[i]);
      }
    }

    private void iterateNoNullsWithAggregationSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregrateIndex,
      IntervalDayTimeColumnVector inputColVector,
      int batchSize) {
      for (int i=0; i < batchSize; ++i) {
        Aggregation myagg = getCurrentAggregationBuffer(
          aggregationBufferSets,
          aggregrateIndex,
          i);
        myagg.checkValue(inputColVector, i);
      }
    }

    private void iterateHasNullsRepeatingSelectionWithAggregationSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregrateIndex,
      IntervalDayTimeColumnVector inputColVector,
      int batchSize,
      int[] selection,
      boolean[] isNull) {

      for (int i=0; i < batchSize; ++i) {
        if (!isNull[selection[i]]) {
          Aggregation myagg = getCurrentAggregationBuffer(
            aggregationBufferSets,
            aggregrateIndex,
            i);
          // Repeating use index 0.
          myagg.checkValue(inputColVector, 0);
        }
      }

    }

    private void iterateHasNullsRepeatingWithAggregationSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregrateIndex,
      IntervalDayTimeColumnVector inputColVector,
      int batchSize,
      boolean[] isNull) {

      for (int i=0; i < batchSize; ++i) {
        if (!isNull[i]) {
          Aggregation myagg = getCurrentAggregationBuffer(
            aggregationBufferSets,
            aggregrateIndex,
            i);
          // Repeating use index 0.
          myagg.checkValue(inputColVector, 0);
        }
      }
    }

    private void iterateHasNullsSelectionWithAggregationSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregrateIndex,
      IntervalDayTimeColumnVector inputColVector,
      int batchSize,
      int[] selection,
      boolean[] isNull) {

      for (int j=0; j < batchSize; ++j) {
        int i = selection[j];
        if (!isNull[i]) {
          Aggregation myagg = getCurrentAggregationBuffer(
            aggregationBufferSets,
            aggregrateIndex,
            j);
          myagg.checkValue(inputColVector, i);
        }
      }
   }

    private void iterateHasNullsWithAggregationSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregrateIndex,
      IntervalDayTimeColumnVector inputColVector,
      int batchSize,
      boolean[] isNull) {

      for (int i=0; i < batchSize; ++i) {
        if (!isNull[i]) {
          Aggregation myagg = getCurrentAggregationBuffer(
            aggregationBufferSets,
            aggregrateIndex,
            i);
          myagg.checkValue(inputColVector, i);
        }
      }
   }

    @Override
    public void aggregateInput(AggregationBuffer agg, VectorizedRowBatch batch)
      throws HiveException {

        inputExpression.evaluate(batch);

        IntervalDayTimeColumnVector inputColVector = (IntervalDayTimeColumnVector)batch.
            cols[this.inputExpression.getOutputColumn()];

        int batchSize = batch.size;

        if (batchSize == 0) {
          return;
        }

        Aggregation myagg = (Aggregation)agg;

        if (inputColVector.isRepeating) {
          if (inputColVector.noNulls &&
            (myagg.isNull || (inputColVector.compareTo(myagg.value, 0) < 0))) {
            myagg.isNull = false;
            inputColVector.intervalDayTimeUpdate(myagg.value, 0);
          }
          return;
        }

        if (!batch.selectedInUse && inputColVector.noNulls) {
          iterateNoSelectionNoNulls(myagg, inputColVector, batchSize);
        }
        else if (!batch.selectedInUse) {
          iterateNoSelectionHasNulls(myagg, inputColVector,
            batchSize, inputColVector.isNull);
        }
        else if (inputColVector.noNulls){
          iterateSelectionNoNulls(myagg, inputColVector, batchSize, batch.selected);
        }
        else {
          iterateSelectionHasNulls(myagg, inputColVector,
            batchSize, inputColVector.isNull, batch.selected);
        }
    }

    private void iterateSelectionHasNulls(
        Aggregation myagg,
        IntervalDayTimeColumnVector inputColVector,
        int batchSize,
        boolean[] isNull,
        int[] selected) {

      for (int j=0; j< batchSize; ++j) {
        int i = selected[j];
        if (!isNull[i]) {
          if (myagg.isNull) {
            myagg.isNull = false;
            inputColVector.intervalDayTimeUpdate(myagg.value, i);
          }
          else if (inputColVector.compareTo(myagg.value, i) < 0) {
            inputColVector.intervalDayTimeUpdate(myagg.value, i);
          }
        }
      }
    }

    private void iterateSelectionNoNulls(
        Aggregation myagg,
        IntervalDayTimeColumnVector inputColVector,
        int batchSize,
        int[] selected) {

      if (myagg.isNull) {
        inputColVector.intervalDayTimeUpdate(myagg.value, selected[0]);
        myagg.isNull = false;
      }

      for (int i=0; i< batchSize; ++i) {
        int sel = selected[i];
        if (inputColVector.compareTo(myagg.value, sel) < 0) {
          inputColVector.intervalDayTimeUpdate(myagg.value, sel);
        }
      }
    }

    private void iterateNoSelectionHasNulls(
        Aggregation myagg,
        IntervalDayTimeColumnVector inputColVector,
        int batchSize,
        boolean[] isNull) {

      for(int i=0;i<batchSize;++i) {
        if (!isNull[i]) {
          if (myagg.isNull) {
            inputColVector.intervalDayTimeUpdate(myagg.value, i);
            myagg.isNull = false;
          }
          else if (inputColVector.compareTo(myagg.value, i) < 0) {
            inputColVector.intervalDayTimeUpdate(myagg.value, i);
          }
        }
      }
    }

    private void iterateNoSelectionNoNulls(
        Aggregation myagg,
        IntervalDayTimeColumnVector inputColVector,
        int batchSize) {
      if (myagg.isNull) {
        inputColVector.intervalDayTimeUpdate(myagg.value, 0);
        myagg.isNull = false;
      }

      for (int i=0;i<batchSize;++i) {
        if (inputColVector.compareTo(myagg.value, i) < 0) {
          inputColVector.intervalDayTimeUpdate(myagg.value, i);
        }
      }
    }

    @Override
    public AggregationBuffer getNewAggregationBuffer() throws HiveException {
      return new Aggregation();
    }

    @Override
    public void reset(AggregationBuffer agg) throws HiveException {
      Aggregation myAgg = (Aggregation) agg;
      myAgg.reset();
    }

    @Override
    public Object evaluateOutput(
        AggregationBuffer agg) throws HiveException {
    Aggregation myagg = (Aggregation) agg;
      if (myagg.isNull) {
        return null;
      }
      else {
        return resultWriter.writeValue(myagg.value);
      }
    }

    @Override
    public ObjectInspector getOutputObjectInspector() {
      return resultWriter.getObjectInspector();
    }

    @Override
    public int getAggregationBufferFixedSize() {
    JavaDataModel model = JavaDataModel.get();
    return JavaDataModel.alignUp(
      model.object() +
      model.primitive2(),
      model.memoryAlign());
  }

  public VectorExpression getInputExpression() {
    return inputExpression;
  }

  public void setInputExpression(VectorExpression inputExpression) {
    this.inputExpression = inputExpression;
  }
}

