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

import org.apache.hadoop.hive.ql.exec.vector.expressions.VectorExpression;
import org.apache.hadoop.hive.ql.exec.vector.*;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;

/**
 * Generated from template FilterScalarCompareColumn.txt, which covers binary comparison
 * expressions between a scalar and a column, however output is not produced in a separate column.
 * The selected vector of the input {@link VectorizedRowBatch} is updated for in-place filtering.
 */
public class FilterTimestampScalarGreaterEqualDoubleColumn extends org.apache.hadoop.hive.ql.exec.vector.expressions.gen.FilterDoubleScalarGreaterEqualDoubleColumn {

  private static final long serialVersionUID = 1L;

  public FilterTimestampScalarGreaterEqualDoubleColumn(Timestamp value, int colNum) {
    super(TimestampColumnVector.getDouble(value), colNum);
  }

  public FilterTimestampScalarGreaterEqualDoubleColumn() {
    super();
  }

  @Override
  public String vectorExpressionParameters() {
    return "val " + value + ", col " + + colNum;
  }

  @Override
  public VectorExpressionDescriptor.Descriptor getDescriptor() {
    return (new VectorExpressionDescriptor.Builder())
        .setMode(
            VectorExpressionDescriptor.Mode.FILTER)
        .setNumArguments(2)
        .setArgumentTypes(
            VectorExpressionDescriptor.ArgumentType.getType("timestamp"),
            VectorExpressionDescriptor.ArgumentType.getType("double"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.SCALAR,
            VectorExpressionDescriptor.InputExpressionType.COLUMN).build();
  }
}
