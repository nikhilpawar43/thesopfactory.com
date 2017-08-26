package com.thesopfactory.thesopmaker.utils;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
Author- Nikhil
Date - Jul 11, 2017
 */

@Component
public class HibernateExporter {
	
	private static final Logger log = LoggerFactory.getLogger(HibernateExporter.class);
	
	private static final String POSTGRESQL_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
	
	private static final String ENTITY_PACKAGE = "com.thesopfactory.thesopmaker.model";
	
	private static final String OUTPUT_FILENAME = "src/main/scripts/thesopmaker.ddl";
	
	public HibernateExporter() {	}
	
	public void createHibernateDDLExportSchema() {
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySetting("hibernate.dialect", POSTGRESQL_DIALECT)
				.build();
		
		MetadataSources metadata = new MetadataSources(serviceRegistry);
		
		final Reflections reflections = new Reflections(ENTITY_PACKAGE);
		
		for (Class<?> cl : reflections.getTypesAnnotatedWith(MappedSuperclass.class)) {
			metadata.addAnnotatedClass(cl);
			log.info("Mapped = " + cl.getName());
		}
		for (Class<?> cl : reflections.getTypesAnnotatedWith(Entity.class)) {
			metadata.addAnnotatedClass(cl);
			log.info("Mapped = " + cl.getName());
		}
		
		SchemaExport schemaExport = new SchemaExport((MetadataImplementor) metadata.buildMetadata());
        schemaExport.setDelimiter(";");
        schemaExport.setOutputFile(OUTPUT_FILENAME);
        schemaExport.setFormat(true);
        schemaExport.execute(true, false, false, false);
		
	}

	public static void main ( String[] args) {
		
		HibernateExporter exporter = new HibernateExporter();
		exporter.createHibernateDDLExportSchema();
	}

}
