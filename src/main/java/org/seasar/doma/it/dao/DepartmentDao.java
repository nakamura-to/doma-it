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
package org.seasar.doma.it.dao;

import java.util.List;
import org.seasar.doma.BatchInsert;
import org.seasar.doma.BatchUpdate;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Sql;
import org.seasar.doma.Update;
import org.seasar.doma.it.entity.Department;
import org.seasar.doma.it.holder.Identity;
import org.seasar.doma.it.holder.Location;

@Dao
public interface DepartmentDao {

  @Select
  Department selectById(Integer departmentId);

  @Insert
  int insert(Department entity);

  @Sql(useFile = true)
  @Insert
  int insertBySqlFile(Department entity);

  @Insert(excludeNull = true)
  int insert_excludeNull(Department entity);

  @Update
  int update(Department entity);

  @Sql(useFile = true)
  @Update
  int updateBySqlFile(Department entity);

  @Sql(useFile = true)
  @Update
  int updateBySqlFileWithPopulate(Department entity);

  @Sql(useFile = true)
  @Update(ignoreVersion = true)
  int updateBySqlFile_ignoreVersion(Department entity);

  @Update(excludeNull = true)
  int update_excludeNull(Department entity);

  @Update(ignoreVersion = true)
  int update_ignoreVersion(Department entity);

  @Update(suppressOptimisticLockException = true)
  int update_suppressOptimisticLockException(Department entity);

  @Sql(useFile = true)
  @Update
  int updateBySqlFile_nonEntity(
      Identity<Department> departmentId,
      Integer departmentNo,
      String departmentName,
      Location<Department> location,
      Integer version);

  @BatchInsert
  int[] insert(List<Department> entity);

  @Sql(useFile = true)
  @BatchInsert
  int[] insertBySqlFile(List<Department> entity);

  @BatchUpdate
  int[] update(List<Department> entity);

  @Sql(useFile = true)
  @BatchUpdate
  int[] updateBySqlFile(List<Department> entity);

  @Sql(useFile = true)
  @BatchUpdate(suppressOptimisticLockException = true)
  int[] updateBySqlFile_suppressOptimisticLockException(List<Department> entity);

  @BatchUpdate(ignoreVersion = true)
  int[] update_ignoreVersion(List<Department> entity);

  @BatchUpdate(suppressOptimisticLockException = true)
  int[] update_suppressOptimisticLockException(List<Department> entity);
}
