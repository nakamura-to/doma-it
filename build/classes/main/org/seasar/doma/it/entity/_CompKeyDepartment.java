package org.seasar.doma.it.entity;

/** */
@javax.annotation.Generated(value = { "Doma", "2.0-beta-2-SNAPSHOT" }, date = "2014-02-08T10:52:16.168+0900")
public final class _CompKeyDepartment extends org.seasar.doma.jdbc.entity.AbstractEntityType<org.seasar.doma.it.entity.CompKeyDepartment> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.0-beta-2-SNAPSHOT");
    }

    private static final _CompKeyDepartment __singleton = new _CompKeyDepartment();

    private static final org.seasar.doma.jdbc.entity.OriginalStatesAccessor<org.seasar.doma.it.entity.CompKeyDepartment> __originalStatesAccessor = new org.seasar.doma.jdbc.entity.OriginalStatesAccessor<>(org.seasar.doma.it.entity.CompKeyDepartment.class, "originalStates");

    /** the departmentId1 */
    public final org.seasar.doma.jdbc.entity.AssignedIdPropertyType<java.lang.Object, org.seasar.doma.it.entity.CompKeyDepartment, java.lang.Integer, Object> $departmentId1 = new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<>(org.seasar.doma.it.entity.CompKeyDepartment.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "departmentId1", "DEPARTMENT_ID1", false);

    /** the departmentId2 */
    public final org.seasar.doma.jdbc.entity.AssignedIdPropertyType<java.lang.Object, org.seasar.doma.it.entity.CompKeyDepartment, java.lang.Integer, Object> $departmentId2 = new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<>(org.seasar.doma.it.entity.CompKeyDepartment.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "departmentId2", "DEPARTMENT_ID2", false);

    /** the departmentNo */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, org.seasar.doma.it.entity.CompKeyDepartment, java.lang.Integer, Object> $departmentNo = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(org.seasar.doma.it.entity.CompKeyDepartment.class, java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "departmentNo", "DEPARTMENT_NO", true, true, false);

    /** the departmentName */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, org.seasar.doma.it.entity.CompKeyDepartment, java.lang.String, Object> $departmentName = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(org.seasar.doma.it.entity.CompKeyDepartment.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "departmentName", "DEPARTMENT_NAME", true, true, false);

    /** the location */
    public final org.seasar.doma.jdbc.entity.DefaultPropertyType<java.lang.Object, org.seasar.doma.it.entity.CompKeyDepartment, java.lang.String, Object> $location = new org.seasar.doma.jdbc.entity.DefaultPropertyType<>(org.seasar.doma.it.entity.CompKeyDepartment.class, java.lang.String.class, java.lang.String.class, () -> new org.seasar.doma.wrapper.StringWrapper(), null, null, "location", "LOCATION", true, true, false);

    /** the version */
    public final org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, org.seasar.doma.it.entity.CompKeyDepartment, java.lang.Integer, Object> $version = new org.seasar.doma.jdbc.entity.VersionPropertyType<>(org.seasar.doma.it.entity.CompKeyDepartment.class,  java.lang.Integer.class, java.lang.Integer.class, () -> new org.seasar.doma.wrapper.IntegerWrapper(), null, null, "version", "VERSION", false);

    private final org.seasar.doma.jdbc.entity.NullEntityListener<org.seasar.doma.it.entity.CompKeyDepartment> __listener;

    private final org.seasar.doma.jdbc.entity.NamingType __namingType;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<org.seasar.doma.it.entity.CompKeyDepartment, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<org.seasar.doma.it.entity.CompKeyDepartment, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<org.seasar.doma.it.entity.CompKeyDepartment, ?>> __entityPropertyTypeMap;

    private _CompKeyDepartment() {
        __listener = new org.seasar.doma.jdbc.entity.NullEntityListener<org.seasar.doma.it.entity.CompKeyDepartment>();
        __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_UPPER_CASE;
        __immutable = false;
        __name = "CompKeyDepartment";
        __catalogName = "";
        __schemaName = "";
        __tableName = "COMP_KEY_DEPARTMENT";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<org.seasar.doma.it.entity.CompKeyDepartment, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<org.seasar.doma.it.entity.CompKeyDepartment, ?>> __list = new java.util.ArrayList<>(6);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<org.seasar.doma.it.entity.CompKeyDepartment, ?>> __map = new java.util.HashMap<>(6);
        __idList.add($departmentId1);
        __list.add($departmentId1);
        __map.put("departmentId1", $departmentId1);
        __idList.add($departmentId2);
        __list.add($departmentId2);
        __map.put("departmentId2", $departmentId2);
        __list.add($departmentNo);
        __map.put("departmentNo", $departmentNo);
        __list.add($departmentName);
        __map.put("departmentName", $departmentName);
        __list.add($location);
        __map.put("location", $location);
        __list.add($version);
        __map.put("version", $version);
        __idPropertyTypes = java.util.Collections.unmodifiableList(__idList);
        __entityPropertyTypes = java.util.Collections.unmodifiableList(__list);
        __entityPropertyTypeMap = java.util.Collections.unmodifiableMap(__map);
    }

    @Override
    public org.seasar.doma.jdbc.entity.NamingType getNamingType() {
        return __namingType;
    }

    @Override
    public boolean isImmutable() {
        return __immutable;
    }

    @Override
    public String getName() {
        return __name;
    }

    @Override
    public String getCatalogName() {
        return __catalogName;
    }

    @Override
    public String getSchemaName() {
        return __schemaName;
    }

    @Override
    public String getTableName() {
        return __tableName;
    }

    @Override
    public boolean isQuoteRequired() {
        return __isQuoteRequired;
    }

    @Override
    public void preInsert(org.seasar.doma.it.entity.CompKeyDepartment entity, org.seasar.doma.jdbc.entity.PreInsertContext<org.seasar.doma.it.entity.CompKeyDepartment> context) {
        __listener.preInsert(entity, context);
    }

    @Override
    public void preUpdate(org.seasar.doma.it.entity.CompKeyDepartment entity, org.seasar.doma.jdbc.entity.PreUpdateContext<org.seasar.doma.it.entity.CompKeyDepartment> context) {
        __listener.preUpdate(entity, context);
    }

    @Override
    public void preDelete(org.seasar.doma.it.entity.CompKeyDepartment entity, org.seasar.doma.jdbc.entity.PreDeleteContext<org.seasar.doma.it.entity.CompKeyDepartment> context) {
        __listener.preDelete(entity, context);
    }

    @Override
    public void postInsert(org.seasar.doma.it.entity.CompKeyDepartment entity, org.seasar.doma.jdbc.entity.PostInsertContext<org.seasar.doma.it.entity.CompKeyDepartment> context) {
        __listener.postInsert(entity, context);
    }

    @Override
    public void postUpdate(org.seasar.doma.it.entity.CompKeyDepartment entity, org.seasar.doma.jdbc.entity.PostUpdateContext<org.seasar.doma.it.entity.CompKeyDepartment> context) {
        __listener.postUpdate(entity, context);
    }

    @Override
    public void postDelete(org.seasar.doma.it.entity.CompKeyDepartment entity, org.seasar.doma.jdbc.entity.PostDeleteContext<org.seasar.doma.it.entity.CompKeyDepartment> context) {
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<org.seasar.doma.it.entity.CompKeyDepartment, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<org.seasar.doma.it.entity.CompKeyDepartment, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<org.seasar.doma.it.entity.CompKeyDepartment, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<java.lang.Object, org.seasar.doma.it.entity.CompKeyDepartment, ?, ?> getGeneratedIdPropertyType() {
        return null;
    }

    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<java.lang.Object, org.seasar.doma.it.entity.CompKeyDepartment, ?, ?> getVersionPropertyType() {
        return $version;
    }

    @Override
    public org.seasar.doma.it.entity.CompKeyDepartment newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<org.seasar.doma.it.entity.CompKeyDepartment, ?>> __args) {
        org.seasar.doma.it.entity.CompKeyDepartment entity = new org.seasar.doma.it.entity.CompKeyDepartment();
        __args.values().forEach(v -> v.save(entity));
        return entity;
    }

    @Override
    public Class<org.seasar.doma.it.entity.CompKeyDepartment> getEntityClass() {
        return org.seasar.doma.it.entity.CompKeyDepartment.class;
    }

    @Override
    public org.seasar.doma.it.entity.CompKeyDepartment getOriginalStates(org.seasar.doma.it.entity.CompKeyDepartment __entity) {
        return __originalStatesAccessor.get(__entity);
    }

    @Override
    public void saveCurrentStates(org.seasar.doma.it.entity.CompKeyDepartment __entity) {
        org.seasar.doma.it.entity.CompKeyDepartment __currentStates = new org.seasar.doma.it.entity.CompKeyDepartment();
        $departmentId1.copy(__currentStates, __entity);
        $departmentId2.copy(__currentStates, __entity);
        $departmentNo.copy(__currentStates, __entity);
        $departmentName.copy(__currentStates, __entity);
        $location.copy(__currentStates, __entity);
        $version.copy(__currentStates, __entity);
        __originalStatesAccessor.set(__entity, __currentStates);
    }

    /**
     * @return the singleton
     */
    public static _CompKeyDepartment getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _CompKeyDepartment newInstance() {
        return new _CompKeyDepartment();
    }

}
