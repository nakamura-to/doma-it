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
package org.seasar.doma.internal.jdbc.command;

import static org.seasar.doma.internal.Constants.ROWNUMBER_COLUMN_NAME;
import static org.seasar.doma.internal.util.AssertionUtil.assertNotNull;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.seasar.doma.jdbc.JdbcMappingHint;
import org.seasar.doma.jdbc.JdbcMappingVisitor;
import org.seasar.doma.jdbc.MappedPropertyNotFoundException;
import org.seasar.doma.jdbc.ResultMappingException;
import org.seasar.doma.jdbc.Sql;
import org.seasar.doma.jdbc.entity.EntityPropertyType;
import org.seasar.doma.jdbc.entity.EntityType;
import org.seasar.doma.jdbc.entity.NamingType;
import org.seasar.doma.jdbc.entity.PropertyState;
import org.seasar.doma.jdbc.query.Query;

/**
 * エンティティのビルダーです。
 * 
 * @author taedium
 * @since 1.34.0
 * @param <E>
 *            エンティティの型
 */
public class EntityBuilder<E> {

    protected final Query query;

    protected final EntityType<E> entityType;

    protected final boolean resultMappingEnsured;

    protected Map<Integer, EntityPropertyType<E, ?, ?>> indexMap;

    public EntityBuilder(Query query, EntityType<E> entityType,
            boolean resultMappingEnsured) {
        assertNotNull(query, entityType);
        this.query = query;
        this.entityType = entityType;
        this.resultMappingEnsured = resultMappingEnsured;
    }

    public E build(ResultSet resultSet) throws SQLException {
        assertNotNull(resultSet);
        if (indexMap == null) {
            indexMap = createIndexMap(resultSet.getMetaData(), entityType);
        }
        JdbcMappingVisitor jdbcMappingVisitor = query.getConfig().getDialect()
                .getJdbcMappingVisitor();
        Map<String, PropertyState<E, ?>> states = new HashMap<>(indexMap.size());
        for (Map.Entry<Integer, EntityPropertyType<E, ?, ?>> entry : indexMap
                .entrySet()) {
            Integer index = entry.getKey();
            EntityPropertyType<E, ?, ?> propertyType = entry.getValue();
            PropertyState<E, ?> state = propertyType.createState();
            GetValueFunction function = new GetValueFunction(resultSet, index);
            // TODO
            state.getWrapper().accept(jdbcMappingVisitor, function,
                    new JdbcMappingHint() {
                    });
            states.put(propertyType.getName(), state);
        }
        E entity = entityType.newEntity(states);
        if (!entityType.isImmutable()) {
            entityType.saveCurrentStates(entity);
        }
        return entity;
    }

    protected HashMap<Integer, EntityPropertyType<E, ?, ?>> createIndexMap(
            ResultSetMetaData resultSetMeta, EntityType<E> entityType)
            throws SQLException {
        HashMap<Integer, EntityPropertyType<E, ?, ?>> indexMap = new HashMap<>();
        HashMap<String, EntityPropertyType<E, ?, ?>> columnNameMap = createColumnNameMap(entityType);
        Set<EntityPropertyType<E, ?, ?>> unmappedPropertySet = resultMappingEnsured ? new HashSet<>(
                columnNameMap.values()) : Collections.emptySet();
        int count = resultSetMeta.getColumnCount();
        for (int i = 1; i < count + 1; i++) {
            String columnName = resultSetMeta.getColumnLabel(i);
            String lowerCaseColumnName = columnName.toLowerCase();
            EntityPropertyType<E, ?, ?> propertyType = columnNameMap
                    .get(lowerCaseColumnName);
            if (propertyType == null) {
                if (ROWNUMBER_COLUMN_NAME.equals(lowerCaseColumnName)) {
                    continue;
                }
                throwMappedPropertyNotFoundException(columnName);
            }
            unmappedPropertySet.remove(propertyType);
            indexMap.put(i, propertyType);
        }
        if (resultMappingEnsured && !unmappedPropertySet.isEmpty()) {
            throwResultMappingException(unmappedPropertySet);
        }
        return indexMap;
    }

    protected HashMap<String, EntityPropertyType<E, ?, ?>> createColumnNameMap(
            EntityType<E> entityType) {
        List<EntityPropertyType<E, ?, ?>> propertyTypes = entityType
                .getEntityPropertyTypes();
        HashMap<String, EntityPropertyType<E, ?, ?>> result = new HashMap<>(
                propertyTypes.size());
        for (EntityPropertyType<E, ?, ?> propertyType : propertyTypes) {
            String columnName = propertyType.getColumnName();
            result.put(columnName.toLowerCase(), propertyType);
        }
        return result;
    }

    protected void throwMappedPropertyNotFoundException(String columnName) {
        Sql<?> sql = query.getSql();
        NamingType namingType = entityType.getNamingType();
        throw new MappedPropertyNotFoundException(query.getConfig()
                .getExceptionSqlLogType(), columnName,
                namingType.revert(columnName), entityType.getEntityClass()
                        .getName(), sql.getKind(), sql.getRawSql(),
                sql.getFormattedSql(), sql.getSqlFilePath());
    }

    protected void throwResultMappingException(
            Set<EntityPropertyType<E, ?, ?>> unmappedPropertySet) {
        int size = unmappedPropertySet.size();
        List<String> unmappedPropertyNames = new ArrayList<>(size);
        List<String> expectedColumnNames = new ArrayList<>(size);
        for (EntityPropertyType<E, ?, ?> propertyType : unmappedPropertySet) {
            unmappedPropertyNames.add(propertyType.getName());
            expectedColumnNames.add(propertyType.getColumnName());
        }
        Sql<?> sql = query.getSql();
        throw new ResultMappingException(query.getConfig()
                .getExceptionSqlLogType(), entityType.getEntityClass()
                .getName(), unmappedPropertyNames, expectedColumnNames,
                sql.getKind(), sql.getRawSql(), sql.getFormattedSql(),
                sql.getSqlFilePath());
    }

}