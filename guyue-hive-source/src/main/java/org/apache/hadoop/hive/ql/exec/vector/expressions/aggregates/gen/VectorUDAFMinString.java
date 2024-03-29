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

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.vector.expressions.StringExpr;
import org.apache.hadoop.hive.ql.exec.vector.expressions.VectorExpression;
import org.apache.hadoop.hive.ql.exec.vector.expressions.aggregates.VectorAggregateExpression;
import org.apache.hadoop.hive.ql.exec.vector.VectorAggregationBufferRow;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.plan.AggregationDesc;
import org.apache.hadoop.hive.ql.util.JavaDataModel;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

/**
* VectorUDAFMinString. Vectorized implementation for MIN/MAX aggregates.
*/
@Description(name = "min",
    value = "_FUNC_(expr) - Returns the minimum value of expr (vectorized, type: string)")
public class VectorUDAFMinString extends VectorAggregateExpression {

    private static final long serialVersionUID = 1L;

    /**
     * class for storing the current aggregate value.
     */
    static private final class Aggregation implements AggregationBuffer {

      private static final long serialVersionUID = 1L;

      transient private final static int MIN_BUFFER_SIZE = 16;
      transient private byte[] bytes = new byte[MIN_BUFFER_SIZE];
      transient private int length;

      /**
      * Value is explicitly (re)initialized in reset()
      */
      transient private boolean isNull = true;

      public void checkValue(byte[] bytes, int start, int length) {
        if (isNull) {
          isNull = false;
          assign(bytes, start, length);
        } else if (StringExpr.compare(
                bytes, start, length,
                this.bytes, 0, this.length) < 0) {
          assign(bytes, start, length);
        }
      }

      public void assign(byte[] bytes, int start, int length) {
        // Avoid new allocation if possible
        if (this.bytes.length < length) {
          this.bytes = new byte[length];
        }
        System.arraycopy(bytes, start, this.bytes, 0, length);
        this.length = length;
      }
      @Override
      public int getVariableSize() {
        JavaDataModel model = JavaDataModel.get();
        return model.lengthForByteArrayOfSize(bytes.length);
      }

      @Override
      public void reset () {
        isNull = true;
        length = 0;
      }

    }

    private VectorExpression inputExpression;

    @Override
    public VectorExpression inputExpression() {
      return inputExpression;
    }

    transient private Text result;

    public VectorUDAFMinString(VectorExpression inputExpression) {
      this();
      this.inputExpression = inputExpression;
    }

    public VectorUDAFMinString() {
      super();
      result = new Text();
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

      BytesColumnVector inputColumn = (BytesColumnVector)batch.
        cols[this.inputExpression.getOutputColumn()];

      if (inputColumn.noNulls) {
        if (inputColumn.isRepeating) {
          iterateNoNullsRepeatingWithAggregationSelection(
            aggregationBufferSets, aggregrateIndex,
            inputColumn, batchSize);
        } else {
          if (batch.selectedInUse) {
            iterateNoNullsSelectionWithAggregationSelection(
              aggregationBufferSets, aggregrateIndex,
              inputColumn, batch.selected, batchSize);
          } else {
            iterateNoNullsWithAggregationSelection(
              aggregationBufferSets, aggregrateIndex,
              inputColumn, batchSize);
          }
        }
      } else {
        if (inputColumn.isRepeating) {
          // All nulls, no-op for min/max
        } else {
          if (batch.selectedInUse) {
            iterateHasNullsSelectionWithAggregationSelection(
              aggregationBufferSets, aggregrateIndex,
              inputColumn, batchSize, batch.selected);
          } else {
            iterateHasNullsWithAggregationSelection(
              aggregationBufferSets, aggregrateIndex,
              inputColumn, batchSize);
          }
        }
      }
    }

    private void iterateNoNullsRepeatingWithAggregationSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregrateIndex,
      BytesColumnVector inputColumn,
      int batchSize) {

      byte[] bytes = inputColumn.vector[0];
      int start = inputColumn.start[0];
      int length = inputColumn.length[0];
      for (int i=0; i < batchSize; ++i) {
        Aggregation myagg = getCurrentAggregationBuffer(
          aggregationBufferSets,
          aggregrateIndex,
          i);
        myagg.checkValue(bytes, start, length);
      }
    }

    private void iterateNoNullsSelectionWithAggregationSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregrateIndex,
      BytesColumnVector inputColumn,
      int[] selection,
      int batchSize) {

      for (int i=0; i < batchSize; ++i) {
        int row = selection[i];
        Aggregation myagg = getCurrentAggregationBuffer(
          aggregationBufferSets,
          aggregrateIndex,
          i);
        myagg.checkValue(inputColumn.vector[row],
          inputColumn.start[row],
          inputColumn.length[row]);
      }
    }

    private void iterateNoNullsWithAggregationSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregrateIndex,
      BytesColumnVector inputColumn,
      int batchSize) {
      for (int i=0; i < batchSize; ++i) {
        Aggregation myagg = getCurrentAggregationBuffer(
          aggregationBufferSets,
          aggregrateIndex,
          i);
        myagg.checkValue(inputColumn.vector[i],
          inputColumn.start[i],
          inputColumn.length[i]);
      }
    }

    private void iterateHasNullsSelectionWithAggregationSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregrateIndex,
      BytesColumnVector inputColumn,
      int batchSize,
      int[] selection) {

      for (int i=0; i < batchSize; ++i) {
        int row = selection[i];
        if (!inputColumn.isNull[row]) {
          Aggregation myagg = getCurrentAggregationBuffer(
            aggregationBufferSets,
            aggregrateIndex,
            i);
          myagg.checkValue(inputColumn.vector[row],
            inputColumn.start[row],
            inputColumn.length[row]);
        }
      }
   }

    private void iterateHasNullsWithAggregationSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregrateIndex,
      BytesColumnVector inputColumn,
      int batchSize) {

      for (int i=0; i < batchSize; ++i) {
        if (!inputColumn.isNull[i]) {
          Aggregation myagg = getCurrentAggregationBuffer(
            aggregationBufferSets,
            aggregrateIndex,
            i);
          myagg.checkValue(inputColumn.vector[i],
            inputColumn.start[i],
            inputColumn.length[i]);
        }
      }
   }

    @Override
    public void aggregateInput(AggregationBuffer agg, VectorizedRowBatch batch)
      throws HiveException {

        inputExpression.evaluate(batch);

        BytesColumnVector inputColumn = (BytesColumnVector)batch.
            cols[this.inputExpression.getOutputColumn()];

        int batchSize = batch.size;

        if (batchSize == 0) {
          return;
        }

        Aggregation myagg = (Aggregation)agg;

        if (inputColumn.isRepeating) {
          if (inputColumn.noNulls) {
            myagg.checkValue(inputColumn.vector[0],
              inputColumn.start[0],
              inputColumn.length[0]);
          }
          return;
        }

        if (!batch.selectedInUse && inputColumn.noNulls) {
          iterateNoSelectionNoNulls(myagg, inputColumn, batchSize);
        }
        else if (!batch.selectedInUse) {
          iterateNoSelectionHasNulls(myagg, inputColumn, batchSize);
        }
        else if (inputColumn.noNulls){
          iterateSelectionNoNulls(myagg, inputColumn, batchSize, batch.selected);
        }
        else {
          iterateSelectionHasNulls(myagg, inputColumn, batchSize, batch.selected);
        }
    }

    private void iterateSelectionHasNulls(
        Aggregation myagg,
        BytesColumnVector inputColumn,
        int batchSize,
        int[] selected) {

      for (int j=0; j< batchSize; ++j) {
        int i = selected[j];
        if (!inputColumn.isNull[i]) {
          myagg.checkValue(inputColumn.vector[i],
            inputColumn.start[i],
            inputColumn.length[i]);
        }
      }
    }

    private void iterateSelectionNoNulls(
        Aggregation myagg,
        BytesColumnVector inputColumn,
        int batchSize,
        int[] selected) {

      for (int j=0; j< batchSize; ++j) {
        int i = selected[j];
        myagg.checkValue(inputColumn.vector[i],
          inputColumn.start[i],
          inputColumn.length[i]);
      }
    }

    private void iterateNoSelectionHasNulls(
        Aggregation myagg,
        BytesColumnVector inputColumn,
        int batchSize) {

      for (int i=0; i< batchSize; ++i) {
        if (!inputColumn.isNull[i]) {
          myagg.checkValue(inputColumn.vector[i],
            inputColumn.start[i],
            inputColumn.length[i]);
        }
      }
    }

    private void iterateNoSelectionNoNulls(
        Aggregation myagg,
        BytesColumnVector inputColumn,
        int batchSize) {
      for (int i=0; i< batchSize; ++i) {
        myagg.checkValue(inputColumn.vector[i],
          inputColumn.start[i],
          inputColumn.length[i]);
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
        result.set(myagg.bytes, 0, myagg.length);
        return result;
      }
    }

    @Override
    public ObjectInspector getOutputObjectInspector() {
      return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
    }

    @Override
    public int getAggregationBufferFixedSize() {
      JavaDataModel model = JavaDataModel.get();
      return JavaDataModel.alignUp(
        model.object() +
        model.ref()+
        model.primitive1()*2,
        model.memoryAlign());
    }

    @Override
    public boolean hasVariableSize() {
      return true;
    }

    @Override
    public void init(AggregationDesc desc) throws HiveException {
      // No-op
    }

    public VectorExpression getInputExpression() {
      return inputExpression;
    }

    public void setInputExpression(VectorExpression inputExpression) {
      this.inputExpression = inputExpression;
    }
}

