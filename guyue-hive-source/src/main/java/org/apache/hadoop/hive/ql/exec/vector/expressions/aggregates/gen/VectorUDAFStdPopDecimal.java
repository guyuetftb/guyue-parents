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
import java.util.List;

import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;
import org.apache.hadoop.hive.common.type.HiveDecimal;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.vector.expressions.VectorExpression;
import org.apache.hadoop.hive.ql.exec.vector.expressions.aggregates.VectorAggregateExpression;
import org.apache.hadoop.hive.ql.exec.vector.VectorAggregationBufferRow;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.DecimalColumnVector;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.plan.AggregationDesc;
import org.apache.hadoop.hive.ql.util.JavaDataModel;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

/**
* VectorUDAFStdPopDecimal. Vectorized implementation for VARIANCE aggregates.
*/
@Description(name = "std,stddev,stddev_pop",
    value = "_FUNC_(x) - Returns the standard deviation of a set of numbers (vectorized, decimal)")
public class VectorUDAFStdPopDecimal extends VectorAggregateExpression {

    private static final long serialVersionUID = 1L;

    /**
    /* class for storing the current aggregate value.
    */
    private static final class Aggregation implements AggregationBuffer {

      private static final long serialVersionUID = 1L;

      transient private double sum;
      transient private long count;
      transient private double variance;

      /**
      * Value is explicitly (re)initialized in reset()
      */
      transient private boolean isNull = true;

      public Aggregation() {
      }

      public void init() {
        isNull = false;
        sum = 0f;
        count = 0;
        variance = 0f;
      }

      @Override
      public int getVariableSize() {
        throw new UnsupportedOperationException();
      }

      @Override
      public void reset () {
        isNull = true;
        sum = 0f;
        count = 0;
        variance = 0f;
      }

      public void updateValueWithCheckAndInit(HiveDecimalWritable value, short scale) {
        if (this.isNull) {
          this.init();
        }

        double dval = value.getHiveDecimal().doubleValue();
        this.sum += dval;
        this.count += 1;
        if(this.count > 1) {
           double t = this.count*dval - this.sum;
           this.variance += (t*t) / ((double)this.count*(this.count-1));
        }
      }

      public void updateValueNoCheck(HiveDecimalWritable value, short scale) {
        double dval = value.getHiveDecimal().doubleValue();
        this.sum += dval;
        this.count += 1;
        double t = this.count*dval - this.sum;
        this.variance += (t*t) / ((double)this.count*(this.count-1));
      }

    }

    private VectorExpression inputExpression;

    @Override
    public VectorExpression inputExpression() {
      return inputExpression;
    }

    transient private LongWritable resultCount;
    transient private DoubleWritable resultSum;
    transient private DoubleWritable resultVariance;
    transient private Object[] partialResult;

    transient private ObjectInspector soi;

    public VectorUDAFStdPopDecimal(VectorExpression inputExpression) {
      this();
      this.inputExpression = inputExpression;
    }

    public VectorUDAFStdPopDecimal() {
      super();
      partialResult = new Object[3];
      resultCount = new LongWritable();
      resultSum = new DoubleWritable();
      resultVariance = new DoubleWritable();
      partialResult[0] = resultCount;
      partialResult[1] = resultSum;
      partialResult[2] = resultVariance;
      initPartialResultInspector();
    }

  private void initPartialResultInspector() {
        List<ObjectInspector> foi = new ArrayList<ObjectInspector>();
        foi.add(PrimitiveObjectInspectorFactory.writableLongObjectInspector);
        foi.add(PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);
        foi.add(PrimitiveObjectInspectorFactory.writableDoubleObjectInspector);

        List<String> fname = new ArrayList<String>();
        fname.add("count");
        fname.add("sum");
        fname.add("variance");

        soi = ObjectInspectorFactory.getStandardStructObjectInspector(fname, foi);
    }

    private Aggregation getCurrentAggregationBuffer(
        VectorAggregationBufferRow[] aggregationBufferSets,
        int aggregateIndex,
        int row) {
      VectorAggregationBufferRow mySet = aggregationBufferSets[row];
      Aggregation myagg = (Aggregation) mySet.getAggregationBuffer(aggregateIndex);
      return myagg;
    }


    @Override
    public void aggregateInputSelection(
      VectorAggregationBufferRow[] aggregationBufferSets,
      int aggregateIndex,
      VectorizedRowBatch batch) throws HiveException {

      inputExpression.evaluate(batch);

      DecimalColumnVector inputVector = (DecimalColumnVector)batch.
        cols[this.inputExpression.getOutputColumn()];

      int batchSize = batch.size;

      if (batchSize == 0) {
        return;
      }

      HiveDecimalWritable[] vector = inputVector.vector;

      if (inputVector.isRepeating) {
        if (inputVector.noNulls || !inputVector.isNull[0]) {
          iterateRepeatingNoNullsWithAggregationSelection(
            aggregationBufferSets, aggregateIndex, vector[0], inputVector.scale, batchSize);
        }
      }
      else if (!batch.selectedInUse && inputVector.noNulls) {
        iterateNoSelectionNoNullsWithAggregationSelection(
            aggregationBufferSets, aggregateIndex, vector, inputVector.scale, batchSize);
      }
      else if (!batch.selectedInUse) {
        iterateNoSelectionHasNullsWithAggregationSelection(
            aggregationBufferSets, aggregateIndex, vector, inputVector.scale, 
            batchSize, inputVector.isNull);
      }
      else if (inputVector.noNulls){
        iterateSelectionNoNullsWithAggregationSelection(
            aggregationBufferSets, aggregateIndex, vector, inputVector.scale, 
            batchSize, batch.selected);
      }
      else {
        iterateSelectionHasNullsWithAggregationSelection(
            aggregationBufferSets, aggregateIndex, vector, inputVector.scale, batchSize,
            inputVector.isNull, batch.selected);
      }

    }

    private void  iterateRepeatingNoNullsWithAggregationSelection(
        VectorAggregationBufferRow[] aggregationBufferSets,
        int aggregateIndex,
        HiveDecimalWritable value,
        short scale,
        int batchSize) {

      for (int i=0; i<batchSize; ++i) {
        Aggregation myagg = getCurrentAggregationBuffer(
          aggregationBufferSets,
          aggregateIndex,
          i);
        myagg.updateValueWithCheckAndInit(value, scale);
      }
    }

    private void iterateSelectionHasNullsWithAggregationSelection(
        VectorAggregationBufferRow[] aggregationBufferSets,
        int aggregateIndex,
        HiveDecimalWritable[] vector,
        short scale,
        int batchSize,
        boolean[] isNull,
        int[] selected) {

      for (int j=0; j< batchSize; ++j) {
        Aggregation myagg = getCurrentAggregationBuffer(
          aggregationBufferSets,
          aggregateIndex,
          j);
        int i = selected[j];
        if (!isNull[i]) {
          HiveDecimalWritable value = vector[i];
          myagg.updateValueWithCheckAndInit(value, scale);
        }
      }
    }

    private void iterateSelectionNoNullsWithAggregationSelection(
        VectorAggregationBufferRow[] aggregationBufferSets,
        int aggregateIndex,
        HiveDecimalWritable[] vector,
        short scale,
        int batchSize,
        int[] selected) {

      for (int i=0; i< batchSize; ++i) {
        Aggregation myagg = getCurrentAggregationBuffer(
          aggregationBufferSets,
          aggregateIndex,
          i);
        HiveDecimalWritable value = vector[selected[i]];
        myagg.updateValueWithCheckAndInit(value, scale);
      }
    }

    private void iterateNoSelectionHasNullsWithAggregationSelection(
        VectorAggregationBufferRow[] aggregationBufferSets,
        int aggregateIndex,
        HiveDecimalWritable[] vector,
        short scale,
        int batchSize,
        boolean[] isNull) {

      for(int i=0;i<batchSize;++i) {
        if (!isNull[i]) {
          Aggregation myagg = getCurrentAggregationBuffer(
            aggregationBufferSets,
            aggregateIndex,
          i);
          HiveDecimalWritable value = vector[i];
          myagg.updateValueWithCheckAndInit(value, scale);
        }
      }
    }

    private void iterateNoSelectionNoNullsWithAggregationSelection(
        VectorAggregationBufferRow[] aggregationBufferSets,
        int aggregateIndex,
        HiveDecimalWritable[] vector,
        short scale,
        int batchSize) {

      for (int i=0; i<batchSize; ++i) {
        Aggregation myagg = getCurrentAggregationBuffer(
          aggregationBufferSets,
          aggregateIndex,
          i);
        HiveDecimalWritable value = vector[i];
        myagg.updateValueWithCheckAndInit(value, scale);
      }
    }

    @Override
    public void aggregateInput(AggregationBuffer agg, VectorizedRowBatch batch)
    throws HiveException {

      inputExpression.evaluate(batch);

      DecimalColumnVector inputVector = (DecimalColumnVector)batch.
        cols[this.inputExpression.getOutputColumn()];

      int batchSize = batch.size;

      if (batchSize == 0) {
        return;
      }

      Aggregation myagg = (Aggregation)agg;

      HiveDecimalWritable[] vector = inputVector.vector;

      if (inputVector.isRepeating) {
        if (inputVector.noNulls) {
          iterateRepeatingNoNulls(myagg, vector[0], inputVector.scale, batchSize);
        }
      }
      else if (!batch.selectedInUse && inputVector.noNulls) {
        iterateNoSelectionNoNulls(myagg, vector, inputVector.scale, batchSize);
      }
      else if (!batch.selectedInUse) {
        iterateNoSelectionHasNulls(myagg, vector, inputVector.scale, batchSize, inputVector.isNull);
      }
      else if (inputVector.noNulls){
        iterateSelectionNoNulls(myagg, vector, inputVector.scale, batchSize, batch.selected);
      }
      else {
        iterateSelectionHasNulls(myagg, vector, inputVector.scale, 
          batchSize, inputVector.isNull, batch.selected);
      }
    }

    private void  iterateRepeatingNoNulls(
        Aggregation myagg,
        HiveDecimalWritable value,
        short scale,
        int batchSize) {

      // TODO: conjure a formula w/o iterating
      //

      myagg.updateValueWithCheckAndInit(value, scale);

      // We pulled out i=0 so we can remove the count > 1 check in the loop
      for (int i=1; i<batchSize; ++i) {
        myagg.updateValueNoCheck(value, scale);
      }
    }

    private void iterateSelectionHasNulls(
        Aggregation myagg,
        HiveDecimalWritable[] vector,
        short scale,
        int batchSize,
        boolean[] isNull,
        int[] selected) {

      for (int j=0; j< batchSize; ++j) {
        int i = selected[j];
        if (!isNull[i]) {
          HiveDecimalWritable value = vector[i];
          myagg.updateValueWithCheckAndInit(value, scale);
        }
      }
    }

    private void iterateSelectionNoNulls(
        Aggregation myagg,
        HiveDecimalWritable[] vector,
        short scale,
        int batchSize,
        int[] selected) {

      if (myagg.isNull) {
        myagg.init ();
      }

      HiveDecimalWritable value = vector[selected[0]];
      myagg.updateValueWithCheckAndInit(value, scale);

      // i=0 was pulled out to remove the count > 1 check in the loop
      //
      for (int i=1; i< batchSize; ++i) {
        value = vector[selected[i]];
        myagg.updateValueNoCheck(value, scale);
      }
    }

    private void iterateNoSelectionHasNulls(
        Aggregation myagg,
        HiveDecimalWritable[] vector,
        short scale,
        int batchSize,
        boolean[] isNull) {

      for(int i=0;i<batchSize;++i) {
        if (!isNull[i]) {
          HiveDecimalWritable value = vector[i];
          myagg.updateValueWithCheckAndInit(value, scale);
        }
      }
    }

    private void iterateNoSelectionNoNulls(
        Aggregation myagg,
        HiveDecimalWritable[] vector,
        short scale,
        int batchSize) {

      if (myagg.isNull) {
        myagg.init ();
      }

      HiveDecimalWritable value = vector[0];
      myagg.updateValueWithCheckAndInit(value, scale);

      // i=0 was pulled out to remove count > 1 check
      for (int i=1; i<batchSize; ++i) {
        value = vector[i];
        myagg.updateValueNoCheck(value, scale);
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
        assert(0 < myagg.count);
        resultCount.set(myagg.count);
        resultSum.set(myagg.sum);
        resultVariance.set(myagg.variance);
        return partialResult;
      }
    }
  @Override
    public ObjectInspector getOutputObjectInspector() {
      return soi;
    }

  @Override
  public int getAggregationBufferFixedSize() {
      JavaDataModel model = JavaDataModel.get();
      return JavaDataModel.alignUp(
        model.object() +
        model.primitive2()*3+
        model.primitive1(),
        model.memoryAlign());
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
