/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.doma.it.sqlfile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.seasar.doma.it.IntegrationTestEnvironment;
import org.seasar.doma.it.dao.DepartmentDao;
import org.seasar.doma.it.dao.DepartmentDaoImpl;
import org.seasar.doma.it.dao.DeptDao;
import org.seasar.doma.it.dao.DeptDaoImpl;
import org.seasar.doma.it.domain.Identity;
import org.seasar.doma.it.entity.Department;
import org.seasar.doma.it.entity.Dept;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.Result;

@ExtendWith(IntegrationTestEnvironment.class)
public class SqlFileInsertTest {

  @Test
  public void test(Config config) throws Exception {
    DepartmentDao dao = new DepartmentDaoImpl(config);
    Department department = new Department();
    department.setDepartmentId(new Identity<Department>(99));
    department.setDepartmentNo(99);
    department.setDepartmentName("hoge");
    int result = dao.insertBySqlFile(department);
    assertEquals(1, result);

    department = dao.selectById(Integer.valueOf(99));
    assertEquals(Integer.valueOf(99), department.getDepartmentId().getValue());
    assertEquals(Integer.valueOf(99), department.getDepartmentNo());
  }

  @Test
  public void testImmutable(Config config) throws Exception {
    DeptDao dao = new DeptDaoImpl(config);
    Dept dept = new Dept(new Identity<Dept>(99), 99, "hoge", null, null);
    Result<Dept> result = dao.insertBySqlFile(dept);
    assertEquals(1, result.getCount());
    dept = result.getEntity();
    assertEquals("hoge_preI_postI", dept.getDepartmentName());

    dept = dao.selectById(Integer.valueOf(99));
    assertEquals(Integer.valueOf(99), dept.getDepartmentId().getValue());
    assertEquals(Integer.valueOf(99), dept.getDepartmentNo());
    assertEquals("hoge_preI", dept.getDepartmentName());
  }
}
