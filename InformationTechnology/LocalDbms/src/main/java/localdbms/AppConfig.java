package localdbms;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import localdbms.DBMS.Dbms;
import localdbms.controller.*;
import java.lang.Exception;
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
    public Dbms dbms() throws Exception {
        Dbms dbms = new Dbms();
        dbms.setDatabases(FXCollections.observableArrayList());
        dbms.loadDatabaseFromStorage();
        return dbms;
    }

    @Bean
    @Autowired
    public DatabaseService databaseService(Dbms dbms) throws Exception {
        DatabaseService databaseService = new DatabaseService();
        databaseService.setDatabases(FXCollections.observableArrayList());
        databaseService.setDbms(dbms);
        return databaseService;
    }

    @Bean
    @Autowired
    public TableService tableService(DatabaseOverviewController dsController) throws Exception {
        TableService tableService = new TableService();
        tableService.setDatabases(dsController.getDatabases());
        tableService.setDbIndex(dsController.getSelectedIndex());
        tableService.setTables(FXCollections.observableArrayList());
        return tableService;
    }

    @Bean
    @Autowired
    public DatabaseOverviewController databaseSelectionController(DatabaseService dbService) throws Exception {
        DatabaseOverviewController controller = new DatabaseOverviewController();
        controller.setSelectedIndex(new SimpleIntegerProperty());
        controller.setDatabaseService(dbService);
        return controller;
    }

    @Bean
    @Autowired
    public CreateDatabaseController createDatabaseController(DatabaseService databaseService) throws Exception {
        CreateDatabaseController controller = new CreateDatabaseController();
        controller.setDatabaseService(databaseService);
        return controller;
    }

    @Bean
    @Autowired
    public TableOverviewController tableOverviewController(TableService tableService) throws Exception {
        TableOverviewController controller = new TableOverviewController();
        controller.setTableSelectedIndex(new SimpleIntegerProperty());
        controller.setEntrySelectedIndex(new SimpleIntegerProperty());
        controller.setTableService(tableService);
        return controller;
    }

    @Bean
    @Autowired
    public CreateTableController createTableController(TableService tableService) throws Exception {
        CreateTableController controller = new CreateTableController();
        controller.setTableService(tableService);
        return controller;
    }

    @Bean
    @Autowired
    public SearchController searchController(
            TableOverviewController toController, TableService tableService) throws Exception {
        SearchController controller = new SearchController();
        controller.setTableIndex(toController.getTableSelectedIndex());
        controller.setTableService(tableService);
        return controller;
    }

    @Bean
    @Autowired
    public EditRowController editRowController(
            TableOverviewController toController, TableService tableService) throws Exception {
        EditRowController controller = new EditRowController();
        controller.setTableIndex(toController.getTableSelectedIndex());
        controller.setEntryIndex(toController.getEntrySelectedIndex());
        controller.setTableService(tableService);
        return controller;
    }

    @Bean
    @Autowired
    public CreateRowInTableController createRowInTableController(
            TableOverviewController toController, TableService tableService) throws Exception {
        CreateRowInTableController controller = new CreateRowInTableController();
        controller.setTableIndex(toController.getTableSelectedIndex());
        controller.setTableService(tableService);
        return controller;
    }
}
