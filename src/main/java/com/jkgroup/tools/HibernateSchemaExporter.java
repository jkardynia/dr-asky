package com.jkgroup.tools;

import com.google.common.collect.Lists;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.util.EnumSet;
import java.util.List;

public final class HibernateSchemaExporter {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateSchemaExporter.class);
    private static final String OUTPUT_FILE = "schema.sql";
    private static final String DIALECT = "org.hibernate.dialect.H2Dialect"; // org.hibernate.dialect.MySQL5InnoDBDialect

    private List<String> entityPackages;

    private HibernateSchemaExporter(List<String> entityPackages) {
        this.entityPackages = entityPackages;
    }

    public static void main(String[] args) {
        final List<String> entityPackages = Lists.newArrayList("com.jkgroup.drasky.commuting.entity", "com.jkgroup.drasky.intent.repository");

        HibernateSchemaExporter exporter = new HibernateSchemaExporter(entityPackages);
        exporter.export();
    }

    private void export() {
        SchemaExport export = new SchemaExport();
        export.setOutputFile(OUTPUT_FILE);
        export.setFormat(true);
        export.setDelimiter(";");
        EnumSet<TargetType> types = EnumSet.of(TargetType.SCRIPT);
        Metadata metadata = createMetadataSources().buildMetadata();
        export.execute(types, SchemaExport.Action.CREATE, metadata);
    }

    private MetadataSources createMetadataSources() {
        MetadataSources metadata = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySetting("hibernate.dialect", DIALECT)
                        .build());

        for (String entityPackage : entityPackages) {
            final Reflections reflections = new Reflections(entityPackage);
            for (Class<?> cl : reflections.getTypesAnnotatedWith(MappedSuperclass.class)) {
                metadata.addAnnotatedClass(cl);
                LOG.info(String.format("Mapped = %s", cl.getName()));
            }
            for (Class<?> cl : reflections.getTypesAnnotatedWith(Entity.class)) {
                metadata.addAnnotatedClass(cl);
                LOG.info(String.format("Mapped = %s", cl.getName()));
            }
        }
        return metadata;
    }
}