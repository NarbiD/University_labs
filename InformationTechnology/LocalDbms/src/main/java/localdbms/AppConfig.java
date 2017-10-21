package localdbms;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import localdbms.DBMS.Dbms;
import localdbms.DBMS.DbmsImpl;
import localdbms.controller.*;
import localdbms.DBMS.database.Database;
import localdbms.DBMS.database.DatabaseFactory;
import localdbms.DBMS.database.DatabaseImpl;
import localdbms.DBMS.exception.EntryException;
import localdbms.DBMS.exception.StorageException;
import localdbms.DBMS.exception.TableException;
import localdbms.DBMS.table.Table;
import localdbms.DBMS.table.TableFactory;
import localdbms.DBMS.table.TableImpl;
import localdbms.service.DatabaseService;
import localdbms.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    public Bootstrap bootstrap() {
        return new Bootstrap();
    }

    @Bean
    public Dbms dbms() throws StorageException {
        Dbms dbms = new DbmsImpl();
        dbms.setDatabases(FXCollections.observableArrayList());
        dbms.loadDatabaseFromStorage();
        return dbms;
    }

    @Bean
    @Autowired
    public DatabaseService databaseService(Dbms dbms) throws StorageException {
        DatabaseService databaseService = new DatabaseService();
        databaseService.setDatabases(FXCollections.observableArrayList());
        databaseService.setDbms(dbms);
        return databaseService;
    }

    @Bean
    public TableService tableService(DatabaseSelectionController dsController) throws StorageException {
        TableService tableService = new TableService();
        tableService.setDatabases(FXCollections.observableArrayList());
        tableService.setDatabases(dsController.getDatabases());
        tableService.setDbIndex(dsController.getSelectedIndex());
        return tableService;
    }

    @Bean
    @Autowired
    public DatabaseSelectionController databaseSelectionController(DatabaseService dbService) throws StorageException {
        DatabaseSelectionController controller = new DatabaseSelectionController();
        controller.setSelectedIndex(new SimpleIntegerProperty());
        controller.setDatabaseService(dbService);
        return controller;
    }

    @Bean
    @Autowired
    public CreateDatabaseController createDatabaseController(DatabaseService databaseService) throws StorageException {
        CreateDatabaseController controller = new CreateDatabaseController();
        controller.setDatabaseService(databaseService);
        return controller;
    }

    @Bean
    @Autowired
    public TableOverviewController tableOverviewController(DatabaseSelectionController dsController, TableService tableService) throws StorageException {
        TableOverviewController controller = new TableOverviewController();
        ObservableList<Table> tables = FXCollections.observableArrayList();
        controller.setTables(tables);
        controller.setDatabases(dsController.getDatabases());
        controller.setDbIndex(dsController.getSelectedIndex());
        controller.setTableSelectedIndex(new SimpleIntegerProperty());
        controller.setTableService(tableService);
        return controller;
    }

    @Bean
    @Autowired
    public CreateTableController createTableController(TableOverviewController toController, TableService tableService) throws StorageException {
        CreateTableController controller = new CreateTableController();
        controller.setTables(toController.getTables());
        controller.setDatabases(toController.getDatabases());
        controller.setDbIndex(toController.getDbIndex());
        controller.setTableService(tableService);
        return controller;
    }

    @Bean
    @Autowired
    public CreateRowInTableController createRowInTableController(TableOverviewController toController, TableService tableService) throws StorageException {
        CreateRowInTableController controller = new CreateRowInTableController();
        controller.setTableIndex(toController.getTableSelectedIndex());
        controller.setTableService(tableService);
        return controller;
    }

    @Bean
    @Scope("prototype")
    public Database database() throws StorageException {
        return new DatabaseImpl();
    }

    @Bean
    public DatabaseFactory databaseFactory() {
        return this::database;
    }

    @Bean
    @Scope("prototype")
    public Table table() {
        try {
            return new TableImpl();
        } catch (EntryException | TableException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public TableFactory tableFactory() {
        return this::table;
    }
}
