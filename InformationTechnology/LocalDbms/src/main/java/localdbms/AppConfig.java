package localdbms;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import localdbms.DBMS.Dbms;
import localdbms.DBMS.DbmsImpl;
import localdbms.controller.*;
import localdbms.DBMS.exception.StorageException;
import localdbms.DBMS.table.Table;
import localdbms.service.DatabaseService;
import localdbms.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
        databaseService.setDatabases(FXCollections.observableArrayList());
        databaseService.setDbms(dbms);
        return databaseService;
    }

    @Bean
    @Autowired
    public TableService tableService(DatabaseOverviewController dsController) throws StorageException {
        TableService tableService = new TableService();
        tableService.setDatabases(dsController.getDatabases());
        tableService.setDbIndex(dsController.getSelectedIndex());
        tableService.setTables(FXCollections.observableArrayList());
        return tableService;
    }

    @Bean
    @Autowired
    public DatabaseOverviewController databaseSelectionController(DatabaseService dbService) throws StorageException {
        DatabaseOverviewController controller = new DatabaseOverviewController();
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
    public TableOverviewController tableOverviewController(TableService tableService) throws StorageException {
        TableOverviewController controller = new TableOverviewController();
        ObservableList<Table> tables = FXCollections.observableArrayList();
        controller.setTables(tables);
        controller.setTableSelectedIndex(new SimpleIntegerProperty());
        controller.setTableService(tableService);
        return controller;
    }

    @Bean
    @Autowired
    public CreateTableController createTableController(TableService tableService) throws StorageException {
        CreateTableController controller = new CreateTableController();
        controller.setTableService(tableService);
        return controller;
    }

    @Bean
    @Autowired
    public CreateRowInTableController createRowInTableController(
            TableOverviewController toController, TableService tableService) throws StorageException {
        CreateRowInTableController controller = new CreateRowInTableController();
        controller.setTableIndex(toController.getTableSelectedIndex());
        controller.setTableService(tableService);
        return controller;
    }
}
