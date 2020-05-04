package org.seasar.doma.it.dao;

import java.util.List;
import java.util.Optional;
import org.seasar.doma.Dao;
import org.seasar.doma.it.entity.Department_;
import org.seasar.doma.it.entity.Employee;
import org.seasar.doma.it.entity.Employee_;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.criteria.Entityql;

@Dao
public interface CriteriaDao {

  default List<Employee> selectAll() {
    Employee_ e = new Employee_();
    Department_ d = new Department_();
    return Entityql.from(e)
        .innerJoin(d, on -> on.eq(e.departmentId, d.departmentId))
        .associate(
            e,
            d,
            (employee, department) -> {
              employee.setDepartment(department);
              department.getEmployeeList().add(employee);
            })
        .execute(Config.get(this));
  }

  default Optional<Employee> selectById(Integer id) {
    Employee_ e = new Employee_();
    Department_ d = new Department_();
    List<Employee> list =
        Entityql.from(e)
            .innerJoin(d, on -> on.eq(e.departmentId, d.departmentId))
            .associate(
                e,
                d,
                (employee, department) -> {
                  employee.setDepartment(department);
                  department.getEmployeeList().add(employee);
                })
            .where(c -> c.eq(e.employeeId, id))
            .execute(Config.get(this));
    return list.stream().findFirst();
  }
}
