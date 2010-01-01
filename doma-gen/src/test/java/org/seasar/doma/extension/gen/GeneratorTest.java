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
package org.seasar.doma.extension.gen;

import java.io.File;

import junit.framework.TestCase;

import org.seasar.doma.extension.gen.dialect.Dialect;
import org.seasar.doma.extension.gen.dialect.PostgresDialect;
import org.seasar.doma.extension.gen.internal.util.ResourceUtil;

/**
 * @author taedium
 * 
 */
public class GeneratorTest extends TestCase {

    private GlobalFactory factory = new GlobalFactory();

    private Dialect dialect = new PostgresDialect();

    private GeneratorStub generator = new GeneratorStub();

    public void testSimpleEntity() throws Exception {
        ColumnMeta id = new ColumnMeta();
        id.setComment("COMMENT for ID");
        id.setName("ID");
        id.setTypeName("integer");
        id.setPrimaryKey(true);
        id.setNullable(false);

        ColumnMeta empName = new ColumnMeta();
        empName.setComment("COMMENT for NAME");
        empName.setName("EMP_NAME");
        empName.setTypeName("varcar");

        ColumnMeta version = new ColumnMeta();
        version.setComment("COMMENT for VERSION");
        version.setName("VERSION");
        version.setTypeName("integer");

        TableMeta tableMeta = new TableMeta();
        tableMeta.setCatalogName("CATALOG");
        tableMeta.setSchemaName("SCHEMA");
        tableMeta.setName("HOGE");
        tableMeta.setComment("COMMENT for HOGE");
        tableMeta.addColumnMeta(id);
        tableMeta.addColumnMeta(empName);
        tableMeta.addColumnMeta(version);

        EntityPropertyClassNameResolver resolver = factory
                .createEntityPropertyClassNameResolver(null);
        EntityPropertyDescFactory entityPropertyDescFactory = factory
                .createEntityPropertyDescFactory(dialect, resolver, "version", null, 100L, 50L, true);
        EntityDescFactory entityDescFactory = factory
                .createEntityDescFactory("example.entity", null, null, entityPropertyDescFactory, NamingType.NONE, false, false, true, true);
        EntityDesc entityDesc = entityDescFactory.createEntityDesc(tableMeta);
        generator.generate(new EntityContext(entityDesc));

        assertEquals(expect(), generator.getResult());
    }

    public void testEntityListener() throws Exception {
        ColumnMeta id = new ColumnMeta();
        id.setComment("COMMENT for ID");
        id.setName("ID");
        id.setTypeName("integer");
        id.setPrimaryKey(true);
        id.setNullable(false);

        TableMeta tableMeta = new TableMeta();
        tableMeta.setCatalogName("CATALOG");
        tableMeta.setSchemaName("SCHEMA");
        tableMeta.setName("HOGE");
        tableMeta.setComment("COMMENT for HOGE");
        tableMeta.addColumnMeta(id);

        EntityPropertyClassNameResolver resolver = factory
                .createEntityPropertyClassNameResolver(null);
        EntityPropertyDescFactory entityPropertyDescFactory = factory
                .createEntityPropertyDescFactory(dialect, resolver, "version", null, null, null, true);
        EntityDescFactory entityDescFactory = factory
                .createEntityDescFactory("example.entity", null, "entity.listener.CommonListener", entityPropertyDescFactory, NamingType.NONE, false, false, true, true);
        EntityDesc entityDesc = entityDescFactory.createEntityDesc(tableMeta);
        generator.generate(new EntityContext(entityDesc));

        assertEquals(expect(), generator.getResult());
    }

    public void testEntitySuperclass() throws Exception {
        ColumnMeta id = new ColumnMeta();
        id.setComment("COMMENT for ID");
        id.setName("ID");
        id.setTypeName("integer");
        id.setPrimaryKey(true);
        id.setNullable(false);

        TableMeta tableMeta = new TableMeta();
        tableMeta.setCatalogName("CATALOG");
        tableMeta.setSchemaName("SCHEMA");
        tableMeta.setName("HOGE");
        tableMeta.setComment("COMMENT for HOGE");
        tableMeta.addColumnMeta(id);

        EntityPropertyClassNameResolver resolver = factory
                .createEntityPropertyClassNameResolver(null);
        EntityPropertyDescFactory entityPropertyDescFactory = factory
                .createEntityPropertyDescFactory(dialect, resolver, "version", null, null, null, true);
        EntityDescFactory entityDescFactory = factory
                .createEntityDescFactory("example.entity", "example.hoge.CommonEntity", null, entityPropertyDescFactory, NamingType.NONE, false, false, true, true);
        EntityDesc entityDesc = entityDescFactory.createEntityDesc(tableMeta);
        generator.generate(new EntityContext(entityDesc));

        assertEquals(expect(), generator.getResult());
    }

    public void testNamingType() throws Exception {
        ColumnMeta id = new ColumnMeta();
        id.setComment("COMMENT for ID");
        id.setName("ID");
        id.setTypeName("integer");
        id.setPrimaryKey(true);
        id.setNullable(false);

        TableMeta tableMeta = new TableMeta();
        tableMeta.setCatalogName("CATALOG");
        tableMeta.setSchemaName("SCHEMA");
        tableMeta.setName("HOGE");
        tableMeta.setComment("COMMENT for HOGE");
        tableMeta.addColumnMeta(id);

        EntityPropertyClassNameResolver resolver = factory
                .createEntityPropertyClassNameResolver(null);
        EntityPropertyDescFactory entityPropertyDescFactory = factory
                .createEntityPropertyDescFactory(dialect, resolver, "version", null, null, null, true);
        EntityDescFactory entityDescFactory = factory
                .createEntityDescFactory("example.entity", null, null, entityPropertyDescFactory, NamingType.SNAKE_UPPER_CASE, false, false, true, true);
        EntityDesc entityDesc = entityDescFactory.createEntityDesc(tableMeta);
        generator.generate(new EntityContext(entityDesc));

        assertEquals(expect(), generator.getResult());
    }

    public void testGenerationType_IDENTITY() throws Exception {
        ColumnMeta id = new ColumnMeta();
        id.setComment("COMMENT for ID");
        id.setName("ID");
        id.setTypeName("integer");
        id.setPrimaryKey(true);
        id.setAutoIncrement(true);
        id.setNullable(false);

        TableMeta tableMeta = new TableMeta();
        tableMeta.setCatalogName("CATALOG");
        tableMeta.setSchemaName("SCHEMA");
        tableMeta.setName("HOGE");
        tableMeta.setComment("COMMENT for HOGE");
        tableMeta.addColumnMeta(id);

        EntityPropertyClassNameResolver resolver = factory
                .createEntityPropertyClassNameResolver(null);
        EntityPropertyDescFactory entityPropertyDescFactory = factory
                .createEntityPropertyDescFactory(dialect, resolver, "version", null, null, null, true);
        EntityDescFactory entityDescFactory = factory
                .createEntityDescFactory("example.entity", null, null, entityPropertyDescFactory, NamingType.NONE, false, false, true, true);
        EntityDesc entityDesc = entityDescFactory.createEntityDesc(tableMeta);
        generator.generate(new EntityContext(entityDesc));

        assertEquals(expect(), generator.getResult());
    }

    public void testGenerationType_SEQUENCE() throws Exception {
        ColumnMeta id = new ColumnMeta();
        id.setComment("COMMENT for ID");
        id.setName("ID");
        id.setTypeName("integer");
        id.setPrimaryKey(true);
        id.setNullable(false);

        TableMeta tableMeta = new TableMeta();
        tableMeta.setCatalogName("CATALOG");
        tableMeta.setSchemaName("SCHEMA");
        tableMeta.setName("HOGE");
        tableMeta.setComment("COMMENT for HOGE");
        tableMeta.addColumnMeta(id);

        EntityPropertyClassNameResolver resolver = factory
                .createEntityPropertyClassNameResolver(null);
        EntityPropertyDescFactory entityPropertyDescFactory = factory
                .createEntityPropertyDescFactory(dialect, resolver, "version", GenerationType.SEQUENCE, 100L, 50L, true);
        EntityDescFactory entityDescFactory = factory
                .createEntityDescFactory("example.entity", null, null, entityPropertyDescFactory, NamingType.NONE, false, false, true, true);
        EntityDesc entityDesc = entityDescFactory.createEntityDesc(tableMeta);
        generator.generate(new EntityContext(entityDesc));

        assertEquals(expect(), generator.getResult());
    }

    public void testGenerationType_TABLE() throws Exception {
        ColumnMeta id = new ColumnMeta();
        id.setComment("COMMENT for ID");
        id.setName("ID");
        id.setTypeName("integer");
        id.setPrimaryKey(true);
        id.setNullable(false);

        TableMeta tableMeta = new TableMeta();
        tableMeta.setCatalogName("CATALOG");
        tableMeta.setSchemaName("SCHEMA");
        tableMeta.setName("HOGE");
        tableMeta.setComment("COMMENT for HOGE");
        tableMeta.addColumnMeta(id);

        EntityPropertyClassNameResolver resolver = factory
                .createEntityPropertyClassNameResolver(null);
        EntityPropertyDescFactory entityPropertyDescFactory = factory
                .createEntityPropertyDescFactory(dialect, resolver, "version", GenerationType.TABLE, 100L, 50L, true);
        EntityDescFactory entityDescFactory = factory
                .createEntityDescFactory("example.entity", null, null, entityPropertyDescFactory, NamingType.NONE, false, false, true, true);
        EntityDesc entityDesc = entityDescFactory.createEntityDesc(tableMeta);
        generator.generate(new EntityContext(entityDesc));

        assertEquals(expect(), generator.getResult());
    }

    public void testCompositeId() throws Exception {
        ColumnMeta id1 = new ColumnMeta();
        id1.setComment("COMMENT for ID");
        id1.setName("ID1");
        id1.setTypeName("integer");
        id1.setPrimaryKey(true);
        id1.setNullable(false);

        ColumnMeta id2 = new ColumnMeta();
        id2.setComment("COMMENT for ID");
        id2.setName("ID2");
        id2.setTypeName("integer");
        id2.setPrimaryKey(true);
        id2.setNullable(false);

        TableMeta tableMeta = new TableMeta();
        tableMeta.setCatalogName("CATALOG");
        tableMeta.setSchemaName("SCHEMA");
        tableMeta.setName("HOGE");
        tableMeta.setComment("COMMENT for HOGE");
        tableMeta.addColumnMeta(id1);
        tableMeta.addColumnMeta(id2);

        EntityPropertyClassNameResolver resolver = factory
                .createEntityPropertyClassNameResolver(null);
        EntityPropertyDescFactory entityPropertyDescFactory = factory
                .createEntityPropertyDescFactory(dialect, resolver, "version", null, null, null, true);
        EntityDescFactory entityDescFactory = factory
                .createEntityDescFactory("example.entity", null, null, entityPropertyDescFactory, NamingType.NONE, false, false, true, true);
        EntityDesc entityDesc = entityDescFactory.createEntityDesc(tableMeta);
        generator.generate(new EntityContext(entityDesc));

        assertEquals(expect(), generator.getResult());
    }

    public void testEntityPropertyClassNameResolver() throws Exception {
        ColumnMeta id = new ColumnMeta();
        id.setComment("COMMENT for ID");
        id.setName("ID");
        id.setTypeName("integer");
        id.setPrimaryKey(true);
        id.setNullable(false);

        ColumnMeta empName = new ColumnMeta();
        empName.setComment("COMMENT for NAME");
        empName.setName("EMP_NAME");
        empName.setTypeName("varcar");

        ColumnMeta xvalue = new ColumnMeta();
        xvalue.setComment("COMMENT for XVAL");
        xvalue.setName("XVAL");
        xvalue.setTypeName("integer");

        TableMeta tableMeta = new TableMeta();
        tableMeta.setCatalogName("CATALOG");
        tableMeta.setSchemaName("SCHEMA");
        tableMeta.setName("HOGE");
        tableMeta.setComment("COMMENT for HOGE");
        tableMeta.addColumnMeta(id);
        tableMeta.addColumnMeta(empName);
        tableMeta.addColumnMeta(xvalue);

        File file = ResourceUtil.getResourceAsFile(getClass().getPackage()
                .getName().replace(".", "/")
                + "/entityPropertyClassNames.properties");
        EntityPropertyClassNameResolver resolver = factory
                .createEntityPropertyClassNameResolver(file);
        EntityPropertyDescFactory entityPropertyDescFactory = factory
                .createEntityPropertyDescFactory(dialect, resolver, "version", null, 100L, 50L, true);
        EntityDescFactory entityDescFactory = factory
                .createEntityDescFactory("example.entity", null, null, entityPropertyDescFactory, NamingType.NONE, false, false, true, true);
        EntityDesc entityDesc = entityDescFactory.createEntityDesc(tableMeta);
        generator.generate(new EntityContext(entityDesc));

        assertEquals(expect(), generator.getResult());
    }

    public void testSimpleDao() throws Exception {
        ColumnMeta id = new ColumnMeta();
        id.setComment("COMMENT for ID");
        id.setName("ID");
        id.setTypeName("integer");
        id.setPrimaryKey(true);
        id.setNullable(false);

        TableMeta tableMeta = new TableMeta();
        tableMeta.setCatalogName("CATALOG");
        tableMeta.setSchemaName("SCHEMA");
        tableMeta.setName("HOGE");
        tableMeta.setComment("COMMENT for HOGE");
        tableMeta.addColumnMeta(id);

        EntityPropertyClassNameResolver resolver = factory
                .createEntityPropertyClassNameResolver(null);
        EntityPropertyDescFactory entityPropertyDescFactory = factory
                .createEntityPropertyDescFactory(dialect, resolver, "version", null, 100L, 50L, true);
        EntityDescFactory entityDescFactory = factory
                .createEntityDescFactory("example.entity", null, null, entityPropertyDescFactory, NamingType.NONE, false, false, true, true);
        EntityDesc entityDesc = entityDescFactory.createEntityDesc(tableMeta);

        DaoDescFactory daoDescFactory = factory
                .createDaoDescFactory("example.dao", "Dao", "dao.config.MyConfig");
        DaoDesc daoDesc = daoDescFactory.createDaoDesc(entityDesc);
        generator.generate(new DaoContext(daoDesc));

        assertEquals(expect(), generator.getResult());
    }

    private String expect() {
        System.out.println(generator.getResult());

        String path = getClass().getName().replace(".", "/") + "_"
                + getName().substring(4) + ".txt";
        return ResourceUtil.getResourceAsString(path);
    }

    private class EntityContext extends GenerationContext {

        public EntityContext(EntityDesc model) {
            super(model, new File("dummy"), "entity.ftl", "UTF-8", true);
        }
    }

    private class DaoContext extends GenerationContext {

        public DaoContext(DaoDesc model) {
            super(model, new File("dummy"), "dao.ftl", "UTF-8", true);
        }
    }
}