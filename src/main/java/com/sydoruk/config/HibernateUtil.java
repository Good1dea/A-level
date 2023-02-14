package com.sydoruk.config;

import org.flywaydb.core.Flyway;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public final class HibernateUtil {
    private static EntityManagerFactory managerFactory;

    private HibernateUtil() {
    }

    public static EntityManager getEntityManager() {

       migrationDB();

        return Optional.ofNullable(managerFactory)
                .or(() -> {
                    managerFactory = Persistence.createEntityManagerFactory("persistence");
                    return Optional.of(managerFactory);
                })
                .map(EntityManagerFactory::createEntityManager)
                .orElseThrow();
    }

    private static void migrationDB(){
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/hibernate", "postgres", "root")
                .baselineOnMigrate(true)
                .locations("db/migration")
                .load();
        flyway.migrate();
    }
}