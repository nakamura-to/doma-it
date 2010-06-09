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
package org.seasar.doma.jdbc.builder;

import java.sql.Statement;

import org.seasar.doma.DomaNullPointerException;
import org.seasar.doma.internal.jdbc.command.InsertCommand;
import org.seasar.doma.internal.jdbc.query.SqlInsertQuery;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.JdbcException;
import org.seasar.doma.jdbc.Sql;
import org.seasar.doma.jdbc.UniqueConstraintException;

/**
 * INSERT文を組み立て実行するクラスです。
 * <p>
 * このクラスはスレッドセーフではありません。
 * 
 * <h4>例</h4>
 * <h5>Java</h5>
 * 
 * <pre>
 * InsertBuilder builder = InsertBuilder.newInstance(config);
 * builder.sql(&quot;insert into Emp&quot;);
 * builder.sql(&quot;(name, salary)&quot;);
 * builder.sql(&quot;values (&quot;);
 * builder.param(String.class, &quot;SMITH&quot;).sql(&quot;, &quot;);
 * builder.param(BigDecimal.class, new BigDecimal(1000)).sql(&quot;)&quot;);
 * builder.execute();
 * </pre>
 * 
 * <h5>実行されるSQL</h5>
 * 
 * <pre>
 * insert into Emp
 * (name, salary)
 * values('SMITH', 100)
 * </pre>
 * 
 * 
 * @author taedium
 * @since 1.8.0
 */
public class InsertBuilder {

    private final SqlNodeBuilder builder;

    private final SqlInsertQuery query;

    private final ParameterIndex parameterIndex;

    private InsertBuilder(Config config) {
        this.builder = new SqlNodeBuilder();
        this.query = new SqlInsertQuery();
        this.query.setConfig(config);
        this.query.setCallerClassName(getClass().getName());
        this.parameterIndex = new ParameterIndex();
    }

    private InsertBuilder(SqlNodeBuilder builder, SqlInsertQuery query,
            ParameterIndex parameterIndex) {
        this.builder = builder;
        this.query = query;
        this.parameterIndex = parameterIndex;
    }

    /**
     * ファクトリメソッドです。
     * 
     * @param config
     *            設定
     * @return UPDATE文を組み立てるビルダー
     * @throws DomaNullPointerException
     *             引数が{@code null} の場合
     */
    public static InsertBuilder newInstance(Config config) {
        if (config == null) {
            throw new DomaNullPointerException("config");
        }
        return new InsertBuilder(config);
    }

    /**
     * SQLの断片を追加します。
     * 
     * @param fragment
     *            SQLの断片
     * @return このインスタンス
     * @throws DomaNullPointerException
     *             引数が {@code null} の場合
     */
    public InsertBuilder sql(String fragment) {
        if (fragment == null) {
            throw new DomaNullPointerException("fragment");
        }
        builder.appendSqlWithLineSeparator(fragment);
        return new SubsequentInsertBuilder(builder, query, parameterIndex);
    }

    /**
     * SQLを切り取ります。
     * <p>
     * {@link #sql(String)}で追加したSQLの断片を切り取ります。
     * 
     * @param length
     *            長さ
     * @return このインスタンス
     */
    public InsertBuilder cutBackSql(int length) {
        builder.cutBackSql(length);
        return new SubsequentInsertBuilder(builder, query, parameterIndex);
    }

    /**
     * パラメータを追加します。
     * <p>
     * パラメータの型には、基本型とドメインクラスを指定できます。
     * 
     * @param <P>
     *            パラメータの型
     * @param parameterClass
     *            パラメータのクラス
     * @param parameter
     *            パラメータ
     * @return このインスタンス
     * @throws DomaNullPointerException
     *             {@code parameterClass} が {@code null} の場合
     */
    public <P> InsertBuilder param(Class<P> parameterClass, P parameter) {
        String parameterName = "p" + parameterIndex.getValue();
        builder.appendParameter(parameterName);
        query.addParameter(parameterName, parameterClass, parameter);
        parameterIndex.increment();
        return new SubsequentInsertBuilder(builder, query, parameterIndex);
    }

    /**
     * SQLを実行します。
     * 
     * @return 更新件数
     * @throws UniqueConstraintException
     *             一意制約違反が発生した場合
     * @throws JdbcException
     *             上記以外でJDBCに関する例外が発生した場合
     */
    public int execute() {
        if (query.getMethodName() == null) {
            query.setCallerMethodName("execute");
        }
        query.setSqlNode(builder.build());
        query.prepare();
        InsertCommand command = new InsertCommand(query);
        int result = command.execute();
        query.complete();
        return result;
    }

    /**
     * クエリタイムアウト（秒）を設定します。
     * <p>
     * 指定しない場合、 {@link Config#getQueryTimeout()} が使用されます。
     * 
     * @param queryTimeout
     *            クエリタイムアウト（秒）
     * @see Statement#setQueryTimeout(int)
     */
    public void queryTimeout(int queryTimeout) {
        query.setQueryTimeout(queryTimeout);
    }

    /**
     * 呼び出し元のクラス名です。
     * <p>
     * 指定しない場合このクラスの名前が使用されます。
     * 
     * @param className
     *            呼び出し元のクラス名
     */
    public void callerClassName(String className) {
        query.setCallerClassName(className);
    }

    /**
     * 呼び出し元のメソッド名です。
     * <p>
     * 指定しない場合このSQLを生成するメソッド（{@link #execute()})）の名前が使用されます。
     * 
     * @param methodName
     *            呼び出し元のメソッド名
     */
    public void callerMethodName(String methodName) {
        query.setCallerMethodName(methodName);
    }

    /**
     * 組み立てられたSQLを返します。
     * 
     * @return 組み立てられたSQL
     */
    public Sql<?> getSql() {
        if (query.getMethodName() == null) {
            query.setCallerMethodName("getSql");
        }
        query.setSqlNode(builder.build());
        query.prepare();
        return query.getSql();
    }

    private static class SubsequentInsertBuilder extends InsertBuilder {

        private SubsequentInsertBuilder(SqlNodeBuilder builder,
                SqlInsertQuery query, ParameterIndex parameterIndex) {
            super(builder, query, parameterIndex);
        }

        @Override
        public InsertBuilder sql(String fragment) {
            super.builder.appendSql(fragment);
            return this;
        }

    }
}
