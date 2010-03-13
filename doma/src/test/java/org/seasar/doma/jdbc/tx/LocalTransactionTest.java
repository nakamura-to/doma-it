/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.doma.jdbc.tx;

import junit.framework.TestCase;

import org.seasar.doma.internal.jdbc.mock.MockConnection;
import org.seasar.doma.internal.jdbc.mock.MockDataSource;
import org.seasar.doma.jdbc.UtilLoggingJdbcLogger;

/**
 * @author taedium
 * 
 */
public class LocalTransactionTest extends TestCase {

    private final MockConnection connection = new MockConnection();

    private final MockDataSource dataSource = new MockDataSource(connection);

    private final ThreadLocal<LocalTransactionContext> connectionHolder = new ThreadLocal<LocalTransactionContext>();

    private final UtilLoggingJdbcLogger jdbcLogger = new UtilLoggingJdbcLogger();

    private final LocalTransaction transaction = new LocalTransaction(
            dataSource, connectionHolder, jdbcLogger);

    public void testBegin() throws Exception {
        transaction.begin();
        assertTrue(transaction.isActive());
        assertFalse(connection.autoCommit);
        assertEquals(TransactionIsolationLevel.READ_COMMITTED.getLevel(),
                connection.isolationLevel);
    }

    public void testBeginImlicitDefaultTransactionIsolationLevel()
            throws Exception {
        LocalTransaction transaction = new LocalTransaction(dataSource,
                connectionHolder, jdbcLogger,
                TransactionIsolationLevel.SERIALIZABLE);
        transaction.begin();
        assertTrue(transaction.isActive());
        assertFalse(connection.autoCommit);
        assertEquals(TransactionIsolationLevel.SERIALIZABLE.getLevel(),
                connection.isolationLevel);
    }

    public void testBeginWithTransactionIsolationLevel() throws Exception {
        transaction.begin(TransactionIsolationLevel.SERIALIZABLE);
        assertTrue(transaction.isActive());
        assertFalse(connection.autoCommit);
        assertEquals(TransactionIsolationLevel.SERIALIZABLE.getLevel(),
                connection.isolationLevel);
    }

    public void testBegin_alreadyBegun() throws Exception {
        transaction.begin();
        try {
            transaction.begin();
            fail();
        } catch (LocalTransactionAlreadyBegunException expected) {
            System.out.println(expected.getMessage());
        }
    }

    public void testSetSavepoint() throws Exception {
        transaction.begin();
        transaction.setSavepoint("hoge");
        assertTrue(transaction.isActive());
        assertFalse(connection.autoCommit);
        assertTrue(connection.savepointNames.contains("hoge"));
    }

    public void testSetSavepoint_alreadyExists() throws Exception {
        transaction.begin();
        transaction.setSavepoint("hoge");
        try {
            transaction.setSavepoint("hoge");
            fail();
        } catch (SavepointAleadyExistsException expected) {
            System.out.println(expected.getMessage());
        }
    }

    public void testSetSavepoint_notYetBegun() throws Exception {
        try {
            transaction.setSavepoint("hoge");
            fail();
        } catch (LocalTransactionNotYetBegunException expected) {
            System.out.println(expected.getMessage());
        }
    }

    public void testReleaseSavepoint() throws Exception {
        transaction.begin();
        transaction.setSavepoint("hoge");
        transaction.setSavepoint("foo");
        transaction.setSavepoint("bar");
        transaction.releaseSavepoint("foo");
        assertTrue(transaction.isActive());
        assertFalse(connection.autoCommit);
        assertFalse(connection.savepointNames.contains("hoge"));
        assertFalse(connection.savepointNames.contains("foo"));
        assertTrue(connection.savepointNames.contains("bar"));
    }

    public void testReleaseSavepoint_notYetBegun() throws Exception {
        try {
            transaction.releaseSavepoint("hoge");
            fail();
        } catch (LocalTransactionNotYetBegunException expected) {
            System.out.println(expected.getMessage());
        }
    }

    public void testReleaseSavepoint_notFound() throws Exception {
        transaction.begin();
        transaction.setSavepoint("hoge");
        try {
            transaction.releaseSavepoint("foo");
            fail();
        } catch (SavepointNotFoundException expected) {
            System.out.println(expected.getMessage());
        }
    }

    public void testCommit() {
        transaction.begin();
        transaction.commit();
        assertFalse(transaction.isActive());
        assertTrue(connection.autoCommit);
        assertTrue(connection.committed);
    }

    public void testCommit_notYetBegun() throws Exception {
        try {
            transaction.commit();
            fail();
        } catch (LocalTransactionNotYetBegunException expected) {
            System.out.println(expected.getMessage());
        }
    }

    public void testRollback() {
        transaction.begin();
        transaction.rollback();
        assertFalse(transaction.isActive());
        assertTrue(connection.autoCommit);
        assertTrue(connection.rolledback);
    }

    public void testRollback_notYetBegun() throws Exception {
        transaction.rollback();
    }

    public void testRollbackSavepoint() throws Exception {
        transaction.begin();
        transaction.setSavepoint("hoge");
        transaction.rollback("hoge");
        assertTrue(transaction.isActive());
        assertFalse(connection.autoCommit);
        assertFalse(connection.savepointNames.contains("hoge"));
    }

    public void testRollbackSavepoint_notYetBegun() throws Exception {
        try {
            transaction.rollback("hoge");
        } catch (LocalTransactionNotYetBegunException expected) {
            System.out.println(expected.getMessage());
        }
    }

    public void testRollbackSavepoint_notFound() throws Exception {
        transaction.begin();
        transaction.setSavepoint("hoge");
        try {
            transaction.rollback("foo");
            fail();
        } catch (SavepointNotFoundException expected) {
            System.out.println(expected.getMessage());
        }
    }
}
