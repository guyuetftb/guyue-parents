/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.exec;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.hadoop.hive.ql.metadata.Hive;
import org.apache.hadoop.hive.ql.session.OperationLog;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.tez.client.TezClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TaskRunner implementation.
 **/

public class TaskRunner extends Thread {
    protected Task<? extends Serializable> tsk;
    protected TaskResult                   result;
    protected SessionState                 ss;
    private   OperationLog                 operationLog;
    private static AtomicLong        taskCounter  = new AtomicLong(0);
    private static ThreadLocal<Long> taskRunnerID = new ThreadLocal<Long>() {

        @Override
        protected Long initialValue() {
            return taskCounter.incrementAndGet();
        }
    };

    protected Thread runner;

    private static transient final Logger LOG           = LoggerFactory.getLogger(TaskRunner.class);
    static final                   String LOG_GY_PREFIX = " MY_TEST .... ";
    static final                   String LOG_GY_BEGIN  = " Beginninggggggggggg ";
    static final                   String LOG_GY_END    = " Endingggggggggggggg ";
    UserGroupInformation ug;

    public TaskRunner(Task<? extends Serializable> tsk,
                      TaskResult result) {
        this.tsk = tsk;
        this.result = result;
        ss = SessionState.get();
    }

    public Task<? extends Serializable> getTask() {
        return tsk;
    }

    public TaskResult getTaskResult() {
        return result;
    }

    public Thread getRunner() {
        return runner;
    }

    public boolean isRunning() {
        return result.isRunning();
    }

    @Override
    public void run() {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 run()  ");
        runner = Thread.currentThread();
        try {
            TezClient tc;
            LOG.info(LOG_GY_PREFIX + " \t 1 run() operationLog = " + operationLog);
            OperationLog.setCurrentOperationLog(operationLog);
            SessionState.start(ss);
            LOG.info(LOG_GY_PREFIX + " \t 2 run() operationLog = " + operationLog);
            runSequential();
        } finally {
            try {
                // Call Hive.closeCurrent() that closes the HMS connection, causes
                // HMS connection leaks otherwise.
                Hive.closeCurrent();
            } catch (Exception e) {
                LOG.warn("Exception closing Metastore connection:" + e.getMessage());
            }
            runner = null;
            result.setRunning(false);
            LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 run() ");
        }
    }

    /**
     * Launches a task, and sets its exit value in the result variable.
     */

    public void runSequential() {
        LOG.info(LOG_GY_PREFIX + LOG_GY_BEGIN + " 000 runSequential() ");
        int exitVal = -101;
        try {
            LOG.info(LOG_GY_PREFIX + " \t 11 TaskRunner().runSequential task-name = " + tsk.getClass().getName());
            exitVal = tsk.executeTask();
            LOG.info(LOG_GY_PREFIX + " \t 22 TaskRunner().runSequential task-name = " + tsk.getClass().getName() + ", exitVal = " + exitVal);
        } catch (Throwable t) {
            if (tsk.getException() == null) {
                tsk.setException(t);
            }
            LOG.error("Error in executeTask", t);
        }
        result.setExitVal(exitVal);
        if (tsk.getException() != null) {
            result.setTaskError(tsk.getException());
        }
        LOG.info(LOG_GY_PREFIX + LOG_GY_END + " 999 runSequential() ");
    }

    public static long getTaskRunnerID() {
        return taskRunnerID.get();
    }

    public void setOperationLog(OperationLog operationLog) {
        this.operationLog = operationLog;
    }
}
