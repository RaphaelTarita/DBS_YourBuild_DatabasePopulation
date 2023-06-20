package com.rtarita.yourbuild;

import com.rtarita.yourbuild.database.DatabaseHandle;
import com.rtarita.yourbuild.generator.DatabasePopulationEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private static final String DEFAULT_DB_URL = "<university oracle db jdbc url>";
    private static final int DEFAULT_SUPERSEED = 69420;
    private static final String VOUCHER_INCREMENT_DISABLE = "VOUCHER_INCREMENT_DISABLE";
    public static final String VOUCHER_INCREMENT_ENABLE = "VOUCHER_INCREMENT_ENABLE";
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("username: ");
        String username = scanner.nextLine();
        System.out.print("password: ");
        String password = scanner.nextLine();
        System.out.print("database JDBC URL (ENTER for default): ");
        String dbUrlTemp = scanner.nextLine();
        System.out.print("super seed (ENTER for default): ");
        String superseedTemp = scanner.nextLine();

        String dbUrl = dbUrlTemp.isBlank() ? DEFAULT_DB_URL : dbUrlTemp;
        int superseed = superseedTemp.isBlank() ? DEFAULT_SUPERSEED : Integer.parseInt(superseedTemp);

        log.info("STARTUP: YourBuild Database population is starting...");
        log.info("Starting with:");
        log.info("username:          " + username);
        log.info("password:          <not shown>");
        log.info("database JDBC URL: " + dbUrl);
        log.info("superseed:         " + superseed);
        log.info("connecting to database...");
        try (DatabaseHandle handle = new DatabaseHandle(
            dbUrl,
            username,
            password
        )) {
            log.info("connection successful!");
            handle.prepareDDLStatement(VOUCHER_INCREMENT_DISABLE, "ALTER TRIGGER VOUCHER_INCREMENT DISABLE");
            handle.prepareDDLStatement(VOUCHER_INCREMENT_ENABLE, "ALTER TRIGGER VOUCHER_INCREMENT ENABLE");

            log.info("disabling trigger 'VOUCHER_INCREMENT' to prevent deadlocks while inserting");
            handle.executeDDL(VOUCHER_INCREMENT_DISABLE);

            log.info("handing over to DatabasePopulationEngine...");
            DatabasePopulationEngine databasePopulationEngine = new DatabasePopulationEngine(
                handle,
                GenerationRequests.getRequests(superseed)
            );
            databasePopulationEngine.populate();

            log.info("re-enabling trigger 'VOUCHER_INCREMENT'");
            handle.executeDDL(VOUCHER_INCREMENT_ENABLE);
            log.info("done. exiting");
        }
    }
}
