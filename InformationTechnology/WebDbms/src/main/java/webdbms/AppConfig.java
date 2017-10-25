package webdbms;

import javafx.collections.FXCollections;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import webdbms.DBMS.Dbms;
import webdbms.DBMS.DbmsImpl;
import webdbms.controller.*;
import webdbms.DBMS.exception.StorageException;
import webdbms.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class AppConfig {

    @Bean
    public Bootstrap bootstrap() {
        return new Bootstrap();
    }

    @Bean
    public Dbms dbms() throws StorageException {
        DbmsImpl dbms = new DbmsImpl();
        dbms.setDatabases(FXCollections.observableArrayList());
        dbms.loadDatabaseFromStorage();
        return dbms;
    }

    @Bean
    @Autowired
    public DatabaseService databaseService(Dbms dbms) throws StorageException {
        DatabaseService databaseService = new DatabaseService();
        databaseService.setDatabases(new ArrayList<>());
        databaseService.setDbms(dbms);
        return databaseService;
    }

    @Bean
    @Autowired
    public DatabaseController databaseController(DatabaseService dbService) {
        DatabaseController databaseController = new DatabaseController();
        databaseController.setDatabaseService(dbService);
        return databaseController;
    }

    @Bean
    @Autowired
    public TableController tableController(DatabaseService databaseService) {
        TableController tableController = new TableController();
        tableController.setDatabaseService(databaseService);
        return tableController;
    }

    @Bean
    @Autowired
    public EntryController entryController(DatabaseService databaseService) {
        EntryController entryController = new EntryController();
        entryController.setDatabaseService(databaseService);
        return entryController;
    }
}
