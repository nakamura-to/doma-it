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
package org.seasar.doma.it.auto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.seasar.doma.it.Container;
import org.seasar.doma.it.Dbms;
import org.seasar.doma.it.Run;
import org.seasar.doma.it.Sandbox;
import org.seasar.doma.it.dao.BusinessmanDao;
import org.seasar.doma.it.dao.BusinessmanDaoImpl;
import org.seasar.doma.it.dao.CompKeyDepartmentDao;
import org.seasar.doma.it.dao.CompKeyDepartmentDaoImpl;
import org.seasar.doma.it.dao.DepartmentDao;
import org.seasar.doma.it.dao.DepartmentDaoImpl;
import org.seasar.doma.it.dao.DeptDao;
import org.seasar.doma.it.dao.DeptDaoImpl;
import org.seasar.doma.it.dao.IdentityStrategyDao;
import org.seasar.doma.it.dao.IdentityStrategyDaoImpl;
import org.seasar.doma.it.dao.NoIdDao;
import org.seasar.doma.it.dao.NoIdDaoImpl;
import org.seasar.doma.it.dao.SequenceStrategyDao;
import org.seasar.doma.it.dao.SequenceStrategyDaoImpl;
import org.seasar.doma.it.dao.StaffDao;
import org.seasar.doma.it.dao.StaffDaoImpl;
import org.seasar.doma.it.dao.TableStrategyDao;
import org.seasar.doma.it.dao.TableStrategyDaoImpl;
import org.seasar.doma.it.dao.WorkerDao;
import org.seasar.doma.it.dao.WorkerDaoImpl;
import org.seasar.doma.it.entity.Businessman;
import org.seasar.doma.it.entity.CompKeyDepartment;
import org.seasar.doma.it.entity.Department;
import org.seasar.doma.it.entity.Dept;
import org.seasar.doma.it.entity.IdentityStrategy;
import org.seasar.doma.it.entity.NoId;
import org.seasar.doma.it.entity.SequenceStrategy;
import org.seasar.doma.it.entity.Staff;
import org.seasar.doma.it.entity.StaffInfo;
import org.seasar.doma.it.entity.TableStrategy;
import org.seasar.doma.it.entity.Worker;
import org.seasar.doma.it.holder.Identity;
import org.seasar.doma.it.holder.Salary;
import org.seasar.doma.jdbc.BatchResult;
import org.seasar.doma.jdbc.JdbcException;
import org.seasar.doma.message.Message;

public class AutoBatchInsertTest {

    @ClassRule
    public static Container container = new Container();

    @Rule
    public Sandbox sandbox = new Sandbox(container);

    @Test
    public void test() throws Exception {
        DepartmentDao dao = container.get(config -> new DepartmentDaoImpl(config));
        Department department = new Department();
        department.setDepartmentId(new Identity<>(99));
        department.setDepartmentNo(99);
        department.setDepartmentName("hoge");
        Department department2 = new Department();
        department2.setDepartmentId(new Identity<>(98));
        department2.setDepartmentNo(98);
        department2.setDepartmentName("foo");
        int[] result = dao.insert(Arrays.asList(department, department2));
        assertEquals(2, result.length);
        assertEquals(1, result[0]);
        assertEquals(1, result[1]);
        assertEquals(Integer.valueOf(1), department.getVersion());
        assertEquals(Integer.valueOf(1), department2.getVersion());

        department = dao.selectById(99);
        assertEquals(Integer.valueOf(99),
                department.getDepartmentId().getValue());
        assertEquals(Integer.valueOf(99), department.getDepartmentNo());
        assertEquals("hoge", department.getDepartmentName());
        assertNull(department.getLocation().getValue());
        assertEquals(Integer.valueOf(1), department.getVersion());
        department = dao.selectById(98);
        assertEquals(Integer.valueOf(98),
                department.getDepartmentId().getValue());
        assertEquals(Integer.valueOf(98), department.getDepartmentNo());
        assertEquals("foo", department.getDepartmentName());
        assertNull(department.getLocation().getValue());
        assertEquals(Integer.valueOf(1), department.getVersion());
    }

    @Test
    public void testImmutable() throws Exception {
        DeptDao dao = container.get(config -> new DeptDaoImpl(config));
        Dept dept = new Dept(new Identity<>(99), 99, "hoge", null, null);
        Dept dept2 = new Dept(new Identity<>(98), 98, "foo", null, null);
        BatchResult<Dept> result = dao.insert(Arrays.asList(dept, dept2));
        int[] counts = result.getCounts();
        assertEquals(2, counts.length);
        assertEquals(1, counts[0]);
        assertEquals(1, counts[1]);
        dept = result.getEntities().get(0);
        dept2 = result.getEntities().get(1);
        assertEquals(Integer.valueOf(1), dept.getVersion());
        assertEquals("hoge_preI_postI", dept.getDepartmentName());
        assertEquals(Integer.valueOf(1), dept2.getVersion());
        assertEquals(Integer.valueOf(1), dept.getVersion());
        assertEquals("foo_preI_postI", dept2.getDepartmentName());

        dept = dao.selectById(99);
        assertEquals(Integer.valueOf(99), dept.getDepartmentId().getValue());
        assertEquals(Integer.valueOf(99), dept.getDepartmentNo());
        assertEquals("hoge_preI", dept.getDepartmentName());
        assertNull(dept.getLocation().getValue());
        assertEquals(Integer.valueOf(1), dept.getVersion());
        dept = dao.selectById(98);
        assertEquals(Integer.valueOf(98), dept.getDepartmentId().getValue());
        assertEquals(Integer.valueOf(98), dept.getDepartmentNo());
        assertEquals("foo_preI", dept.getDepartmentName());
        assertNull(dept.getLocation().getValue());
        assertEquals(Integer.valueOf(1), dept.getVersion());
    }

    @Test
    public void testCompositeKey() throws Exception {
        CompKeyDepartmentDao dao = container.get(config -> new CompKeyDepartmentDaoImpl(config));
        CompKeyDepartment department = new CompKeyDepartment();
        department.setDepartmentId1(99);
        department.setDepartmentId2(99);
        department.setDepartmentNo(99);
        department.setDepartmentName("hoge");
        CompKeyDepartment department2 = new CompKeyDepartment();
        department2.setDepartmentId1(98);
        department2.setDepartmentId2(98);
        department2.setDepartmentNo(98);
        department2.setDepartmentName("hoge");
        int[] result = dao.insert(Arrays.asList(department, department2));
        assertEquals(2, result.length);
        assertEquals(1, result[0]);
        assertEquals(1, result[1]);
        assertEquals(Integer.valueOf(1), department.getVersion());
        assertEquals(Integer.valueOf(1), department2.getVersion());

        department = dao.selectById(99, 99);
        assertEquals(Integer.valueOf(99), department.getDepartmentId1());
        assertEquals(Integer.valueOf(99), department.getDepartmentId2());
        assertEquals(Integer.valueOf(99), department.getDepartmentNo());
        assertEquals("hoge", department.getDepartmentName());
        assertNull(department.getLocation());
        assertEquals(Integer.valueOf(1), department.getVersion());
        department = dao.selectById(98, 98);
        assertEquals(Integer.valueOf(98), department.getDepartmentId1());
        assertEquals(Integer.valueOf(98), department.getDepartmentId2());
        assertEquals(Integer.valueOf(98), department.getDepartmentNo());
        assertEquals("hoge", department.getDepartmentName());
        assertNull(department.getLocation());
        assertEquals(Integer.valueOf(1), department.getVersion());
    }

    @Test
    public void testIdNotAssigned() throws Exception {
        DepartmentDao dao = container.get(config -> new DepartmentDaoImpl(config));
        Department department = new Department();
        department.setDepartmentNo(99);
        department.setDepartmentName("hoge");
        Department department2 = new Department();
        department2.setDepartmentNo(98);
        department2.setDepartmentName("hoge");
        try {
            dao.insert(Arrays.asList(department, department2));
            fail();
        } catch (JdbcException expected) {
            assertEquals(Message.DOMA2020, expected.getMessageResource());
        }
    }

    @Test
    @Run(unless = { Dbms.ORACLE })
    public void testId_Identity() throws Exception {
        IdentityStrategyDao dao = container.get(config -> new IdentityStrategyDaoImpl(config));
        for (int i = 0; i < 110; i++) {
            IdentityStrategy entity = new IdentityStrategy();
            IdentityStrategy entity2 = new IdentityStrategy();
            int[] result = dao.insert(Arrays.asList(entity, entity2));
            assertEquals(2, result.length);
            assertEquals(1, result[0]);
            assertEquals(1, result[1]);
            assertNotNull(entity.getId());
            assertNotNull(entity2.getId());
            assertTrue(entity.getId() < entity2.getId());
        }
    }

    @Test
    @Run(unless = { Dbms.MYSQL, Dbms.SQLSERVER, Dbms.SQLITE })
    public void testId_sequence() throws Exception {
        SequenceStrategyDao dao = container.get(config -> new SequenceStrategyDaoImpl(config));
        for (int i = 0; i < 110; i++) {
            SequenceStrategy entity = new SequenceStrategy();
            SequenceStrategy entity2 = new SequenceStrategy();
            int[] result = dao.insert(Arrays.asList(entity, entity2));
            assertEquals(2, result.length);
            assertEquals(1, result[0]);
            assertEquals(1, result[1]);
            assertNotNull(entity.getId());
            assertNotNull(entity2.getId());
            assertTrue(entity.getId() < entity2.getId());
        }
    }

    // it seems that sqlite doesn't support requiresNew transaction
    // so ignore this test case
    @Test
    @Run(unless = { Dbms.SQLITE })
    public void testId_table() throws Exception {
        TableStrategyDao dao = container.get(config -> new TableStrategyDaoImpl(config));
        for (int i = 0; i < 110; i++) {
            TableStrategy entity = new TableStrategy();
            TableStrategy entity2 = new TableStrategy();
            int[] result = dao.insert(Arrays.asList(entity, entity2));
            assertEquals(2, result.length);
            assertEquals(1, result[0]);
            assertEquals(1, result[1]);
            assertNotNull(entity.getId());
            assertNotNull(entity2.getId());
            assertTrue(entity.getId() < entity2.getId());
        }
    }

    @Test
    public void testNoId() throws Exception {
        NoIdDao dao = container.get(config -> new NoIdDaoImpl(config));
        NoId entity = new NoId();
        entity.setValue1(1);
        entity.setValue2(2);
        NoId entity2 = new NoId();
        entity2.setValue1(1);
        entity2.setValue2(2);
        int[] result = dao.insert(Arrays.asList(entity, entity2));
        assertEquals(2, result.length);
        assertEquals(1, result[0]);
        assertEquals(1, result[1]);
    }

    @Test
    public void testOptional() throws Exception {
        WorkerDao dao = container.get(config -> new WorkerDaoImpl(config));
        Worker worker = new Worker();
        worker.employeeId = Optional.of(9998);
        worker.employeeNo = Optional.of(9998);
        Worker worker2 = new Worker();
        worker2.employeeId = Optional.of(9999);
        worker2.employeeNo = Optional.of(9999);
        int[] result = dao.insert(Arrays.asList(worker, worker2));
        assertEquals(2, result.length);
        assertEquals(1, result[0]);
        assertEquals(1, result[1]);
        assertEquals(Integer.valueOf(1), worker.version.get());
        assertEquals(Integer.valueOf(1), worker2.version.get());

        worker = dao.selectById(Optional.of(9998));
        assertEquals(Integer.valueOf(9998), worker.employeeId.get());
        assertEquals(Integer.valueOf(9998), worker.employeeNo.get());
        assertEquals(Integer.valueOf(1), worker.version.get());
        worker = dao.selectById(Optional.of(9999));
        assertEquals(Integer.valueOf(9999), worker.employeeId.get());
        assertEquals(Integer.valueOf(9999), worker.employeeNo.get());
        assertEquals(Integer.valueOf(1), worker.version.get());
    }

    @Test
    public void testOptionalInt() throws Exception {
        BusinessmanDao dao = container.get(config -> new BusinessmanDaoImpl(config));
        Businessman worker = new Businessman();
        worker.employeeId = OptionalInt.of(9998);
        worker.employeeNo = OptionalInt.of(9998);
        Businessman worker2 = new Businessman();
        worker2.employeeId = OptionalInt.of(9999);
        worker2.employeeNo = OptionalInt.of(9999);
        int[] result = dao.insert(Arrays.asList(worker, worker2));
        assertEquals(2, result.length);
        assertEquals(1, result[0]);
        assertEquals(1, result[1]);
        assertEquals(1, worker.version.getAsInt());
        assertEquals(1, worker2.version.getAsInt());

        worker = dao.selectById(OptionalInt.of(9998));
        assertEquals(9998, worker.employeeId.getAsInt());
        assertEquals(9998, worker.employeeNo.getAsInt());
        assertEquals(1, worker.version.getAsInt());
        worker = dao.selectById(OptionalInt.of(9999));
        assertEquals(9999, worker.employeeId.getAsInt());
        assertEquals(9999, worker.employeeNo.getAsInt());
        assertEquals(1, worker.version.getAsInt());
    }

    @Test
    public void testEmbeddable() throws Exception {
        StaffDao dao = container.get(config -> new StaffDaoImpl(config));
        Staff staff = new Staff();
        staff.employeeId = 9998;
        staff.employeeNo = 9998;
        staff.staffInfo = new StaffInfo(Date.valueOf("2016-05-27"),
                new Salary("1234"));
        Staff staff2 = new Staff();
        staff2.employeeId = 9999;
        staff2.employeeNo = 9999;
        staff2.staffInfo = new StaffInfo(Date.valueOf("2016-04-01"),
                new Salary("5678"));
        int[] result = dao.insert(Arrays.asList(staff, staff2));
        assertEquals(1, result[0]);
        assertEquals(1, result[1]);
        assertEquals(1, staff.version.intValue());
        assertEquals(1, staff2.version.intValue());

        staff = dao.selectById(9998);
        assertEquals(Date.valueOf("2016-05-27"), staff.staffInfo.hiredate);
        assertEquals(1234L, staff.staffInfo.salary.getValue().longValue());
        staff = dao.selectById(9999);
        assertEquals(Date.valueOf("2016-04-01"), staff.staffInfo.hiredate);
        assertEquals(5678L, staff.staffInfo.salary.getValue().longValue());
    }
}
