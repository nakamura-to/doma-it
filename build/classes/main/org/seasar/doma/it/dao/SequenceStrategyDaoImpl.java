package org.seasar.doma.it.dao;

/** */
@javax.annotation.Generated(value = { "Doma", "2.0-beta-2-SNAPSHOT" }, date = "2014-02-08T10:52:16.452+0900")
 class SequenceStrategyDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements org.seasar.doma.it.dao.SequenceStrategyDao {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.0-beta-2-SNAPSHOT");
    }

    private static final java.lang.reflect.Method __method0 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(org.seasar.doma.it.dao.SequenceStrategyDao.class, "insert", org.seasar.doma.it.entity.SequenceStrategy.class);

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(org.seasar.doma.it.dao.SequenceStrategyDao.class, "insert", java.util.List.class);

    /** */
    public SequenceStrategyDaoImpl() {
        super(new org.seasar.doma.it.ItConfig());
    }

    /**
     * @param connection the connection
     */
    public SequenceStrategyDaoImpl(java.sql.Connection connection) {
        super(new org.seasar.doma.it.ItConfig(), connection);
    }

    /**
     * @param dataSource the dataSource
     */
    public SequenceStrategyDaoImpl(javax.sql.DataSource dataSource) {
        super(new org.seasar.doma.it.ItConfig(), dataSource);
    }

    /**
     * @param config the configuration
     */
    protected SequenceStrategyDaoImpl(org.seasar.doma.jdbc.Config config) {
        super(config);
    }

    /**
     * @param config the configuration
     * @param connection the connection
     */
    protected SequenceStrategyDaoImpl(org.seasar.doma.jdbc.Config config, java.sql.Connection connection) {
        super(config, connection);
    }

    /**
     * @param config the configuration
     * @param dataSource the dataSource
     */
    protected SequenceStrategyDaoImpl(org.seasar.doma.jdbc.Config config, javax.sql.DataSource dataSource) {
        super(config, dataSource);
    }

    @Override
    public int insert(org.seasar.doma.it.entity.SequenceStrategy entity) {
        entering("org.seasar.doma.it.dao.SequenceStrategyDaoImpl", "insert", entity);
        try {
            if (entity == null) {
                throw new org.seasar.doma.DomaNullPointerException("entity");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<org.seasar.doma.it.entity.SequenceStrategy> __query = getQueryImplementors().createAutoInsertQuery(__method0, org.seasar.doma.it.entity._SequenceStrategy.getSingletonInternal());
            __query.setMethod(__method0);
            __query.setConfig(__config);
            __query.setEntity(entity);
            __query.setCallerClassName("org.seasar.doma.it.dao.SequenceStrategyDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setNullExcluded(false);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = getCommandImplementors().createInsertCommand(__method0, __query);
            int __result = __command.execute();
            __query.complete();
            exiting("org.seasar.doma.it.dao.SequenceStrategyDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("org.seasar.doma.it.dao.SequenceStrategyDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int[] insert(java.util.List<org.seasar.doma.it.entity.SequenceStrategy> entities) {
        entering("org.seasar.doma.it.dao.SequenceStrategyDaoImpl", "insert", entities);
        try {
            if (entities == null) {
                throw new org.seasar.doma.DomaNullPointerException("entities");
            }
            org.seasar.doma.jdbc.query.AutoBatchInsertQuery<org.seasar.doma.it.entity.SequenceStrategy> __query = getQueryImplementors().createAutoBatchInsertQuery(__method1, org.seasar.doma.it.entity._SequenceStrategy.getSingletonInternal());
            __query.setMethod(__method1);
            __query.setConfig(__config);
            __query.setEntities(entities);
            __query.setCallerClassName("org.seasar.doma.it.dao.SequenceStrategyDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setBatchSize(-1);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.BatchInsertCommand __command = getCommandImplementors().createBatchInsertCommand(__method1, __query);
            int[] __result = __command.execute();
            __query.complete();
            exiting("org.seasar.doma.it.dao.SequenceStrategyDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            throwing("org.seasar.doma.it.dao.SequenceStrategyDaoImpl", "insert", __e);
            throw __e;
        }
    }

}
