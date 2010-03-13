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
package org.seasar.doma.jdbc;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.seasar.doma.DomaNullPointerException;

/**
 * 出力先に {@link java.util.logging.Logger} を使用したJDBCロガーです。
 * <p>
 * 
 * @author taedium
 * 
 */
public class UtilLoggingJdbcLogger implements JdbcLogger {

    /** ログレベルです。 */
    protected final Level level;

    /** このインスタンスで使用するロガーです。 */
    protected final Logger logger;

    /**
     * インスタンスを構築します。
     * <p>
     * ログレベルは {@link Level#INFO} になります。
     */
    public UtilLoggingJdbcLogger() {
        this(Level.INFO);
    }

    /**
     * ログレベルを指定してインスタンスを構築します。
     * 
     * @param level
     *            ログレベル
     */
    public UtilLoggingJdbcLogger(Level level) {
        if (level == null) {
            throw new DomaNullPointerException("level");
        }
        this.level = level;
        this.logger = Logger.getLogger(UtilLoggingJdbcLogger.class.getName());
    }

    @Override
    public void logDaoMethodEntering(String callerClassName,
            String callerMethodName, Object... args) {
        if (callerClassName == null) {
            throw new DomaNullPointerException("callerClassName");
        }
        if (callerMethodName == null) {
            throw new DomaNullPointerException("callerMethodName");
        }
        if (args == null) {
            throw new DomaNullPointerException("args");
        }
        logger.logp(level, callerClassName, callerMethodName, "ENTRY");

    }

    @Override
    public void logDaoMethodExiting(String callerClassName,
            String callerMethodName, Object result) {
        if (callerClassName == null) {
            throw new DomaNullPointerException("callerClassName");
        }
        if (callerMethodName == null) {
            throw new DomaNullPointerException("callerMethodName");
        }
        logger.logp(level, callerClassName, callerMethodName, "RETURN {0}",
                result);
    }

    @Override
    public void logSqlExecutionSkipping(String callerClassName,
            String callerMethodName, SqlExecutionSkipCause cause) {
        if (callerClassName == null) {
            throw new DomaNullPointerException("callerClassName");
        }
        if (callerMethodName == null) {
            throw new DomaNullPointerException("callerMethodName");
        }
        if (cause == null) {
            throw new DomaNullPointerException("cause");
        }
        logger.logp(level, callerClassName, callerMethodName, cause.name());
    }

    @Override
    public void logSql(String callerClassName, String callerMethodName,
            Sql<?> sql) {
        if (callerClassName == null) {
            throw new DomaNullPointerException("callerClassName");
        }
        if (callerMethodName == null) {
            throw new DomaNullPointerException("callerMethodName");
        }
        if (sql == null) {
            throw new DomaNullPointerException("sql");
        }
        String message = String.format("SQL log. sqlFilePath=[%s],%n%s", sql
                .getSqlFilePath(), sql.getFormattedSql());
        logger.logp(level, callerClassName, callerMethodName, message);
    }

    @Override
    public void logLocalTransactionBegun(String callerClassName,
            String callerMethodName, String transactionId) {
        if (callerClassName == null) {
            throw new DomaNullPointerException("callerClassName");
        }
        if (callerMethodName == null) {
            throw new DomaNullPointerException("callerMethodName");
        }
        if (transactionId == null) {
            throw new DomaNullPointerException("transactionId");
        }
        logger.logp(level, callerClassName, callerMethodName,
                "Local transaction begun. transactionId=[{0}]", transactionId);
    }

    @Override
    public void logLocalTransactionSavepointCreated(String callerClassName,
            String callerMethodName, String transactionId, String savepointName) {
        if (callerClassName == null) {
            throw new DomaNullPointerException("callerClassName");
        }
        if (callerMethodName == null) {
            throw new DomaNullPointerException("callerMethodName");
        }
        if (transactionId == null) {
            throw new DomaNullPointerException("transactionId");
        }
        if (savepointName == null) {
            throw new DomaNullPointerException("savepointName");
        }
        logger.logp(level, callerClassName, callerMethodName,
                "Local transaction savepoint created. transactionId=[{0}]",
                transactionId);
    }

    @Override
    public void logLocalTransactionSavepointReleased(String callerClassName,
            String callerMethodName, String transactionId, String savepointName) {
        if (callerClassName == null) {
            throw new DomaNullPointerException("callerClassName");
        }
        if (callerMethodName == null) {
            throw new DomaNullPointerException("callerMethodName");
        }
        if (transactionId == null) {
            throw new DomaNullPointerException("transactionId");
        }
        if (savepointName == null) {
            throw new DomaNullPointerException("savepointName");
        }
        logger.logp(level, callerClassName, callerMethodName,
                "Local transaction savepoint released. transactionId=[{0}]",
                transactionId);
    }

    @Override
    public void logLocalTransactionCommitted(String callerClassName,
            String callerMethodName, String transactionId) {
        if (callerClassName == null) {
            throw new DomaNullPointerException("callerClassName");
        }
        if (callerMethodName == null) {
            throw new DomaNullPointerException("callerMethodName");
        }
        if (transactionId == null) {
            throw new DomaNullPointerException("transactionId");
        }
        logger.logp(level, callerClassName, callerMethodName,
                "Local transaction committed. transactionId=[{0}]",
                transactionId);
    }

    @Override
    public void logLocalTransactionRolledback(String callerClassName,
            String callerMethodName, String transactionId) {
        if (callerClassName == null) {
            throw new DomaNullPointerException("callerClassName");
        }
        if (callerMethodName == null) {
            throw new DomaNullPointerException("callerMethodName");
        }
        if (transactionId == null) {
            throw new DomaNullPointerException("transactionId");
        }
        logger.logp(level, callerClassName, callerMethodName,
                "Local transaction rolled back. transactionId=[{0}]",
                transactionId);
    }

    @Override
    public void logLocalTransactionSavepointRolledback(String callerClassName,
            String callerMethodName, String transactionId, String savepointName) {
        if (callerClassName == null) {
            throw new DomaNullPointerException("callerClassName");
        }
        if (callerMethodName == null) {
            throw new DomaNullPointerException("callerMethodName");
        }
        if (transactionId == null) {
            throw new DomaNullPointerException("transactionId");
        }
        if (savepointName == null) {
            throw new DomaNullPointerException("savepointName");
        }
        logger
                .logp(
                        level,
                        callerClassName,
                        callerMethodName,
                        "Local transaction rolled back. transactionId=[{0}] savepointName=[{1}]",
                        new Object[] { transactionId, savepointName });

    }

    @Override
    public void logConnectionClosingFailure(String callerClassName,
            String callerMethodName, SQLException e) {
    }

    @Override
    public void logStatementClosingFailure(String callerClassName,
            String callerMethodName, SQLException e) {
    }

    @Override
    public void logResultSetClosingFailure(String callerClassName,
            String callerMethodName, SQLException e) {
    }

}
