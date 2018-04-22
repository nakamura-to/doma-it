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
package org.seasar.doma.it.other;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLXML;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.seasar.doma.it.Container;
import org.seasar.doma.it.Dbms;
import org.seasar.doma.it.Run;
import org.seasar.doma.it.Sandbox;
import org.seasar.doma.it.dao.ProductDao;
import org.seasar.doma.it.dao.ProductDaoImpl;
import org.seasar.doma.it.entity.Product;

/**
 * @author nakamura-to
 *
 */
@Run(unless = { Dbms.HSQLDB, Dbms.H2, Dbms.MYSQL, Dbms.ORACLE, Dbms.DB2,
        Dbms.SQLSERVER, Dbms.SQLITE })
public class SQLXMLTest {

    @ClassRule
    public static Container container = new Container();

    @Rule
    public Sandbox sandbox = new Sandbox(container);

    @Test
    public void testSelect() throws Exception {
        ProductDao dao = container.get(config -> new ProductDaoImpl(config));

        SQLXML sqlxml = dao.createSQLXML();
        sqlxml.setString("<test>hoge</test>");
        Product product = new Product();
        product.id = 1;
        product.value = sqlxml;
        dao.insert(product);

        product = dao.selectById(1);
        assertNotNull(product);
        assertEquals("<test>hoge</test>", product.value.getString());
    }

}
