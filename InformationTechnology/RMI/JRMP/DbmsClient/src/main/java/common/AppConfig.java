package common;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import DBMS.Dbms;
import java.io.IOException;
import service.DatabaseService;
import service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@Configuration
public class AppConfig {

    @Bean
    public Bootstrap bootstrap() {
        return new Bootstrap();
    }

    @Bean
    public Dbms dbms() throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1");
        Dbms dbms = (Dbms)registry.lookup("dbms");
        return dbms;
    }

    @Bean
    @Autowired
    public DatabaseService databaseService(Dbms dbms) throws IOException {
        DatabaseService databaseService = new DatabaseService();
        databaseService.setDbms(dbms);
        return databaseService;
    }

    @Bean
    @Autowired
    public TableService tableService(controller.DatabaseOverviewController dsController) throws IOException {
        TableService tableService = new TableService();
        tableService.setDatabases(dsController.getDatabases());
        tableService.setDbIndex(dsController.getSelectedIndex());
        tableService.setTables(FXCollections.observableArrayList());
        return tableService;
    }

    @Bean
    @Autowired
    public controller.DatabaseOverviewController databaseSelectionController(DatabaseService dbService) throws IOException {
        controller.DatabaseOverviewController controller = new controller.DatabaseOverviewController();
        controller.setSelectedIndex(new SimpleIntegerProperty());
        controller.setDatabaseService(dbService);
        return controller;
    }

    @Bean
    @Autowired
    public controller.CreateDatabaseController createDatabaseController(DatabaseService databaseService) throws IOException {
        controller.CreateDatabaseController controller = new controller.CreateDatabaseController();
        controller.setDatabaseService(databaseService);
        return controller;
    }

    @Bean
    @Autowired
    public controller.TableOverviewController tableOverviewController(TableService tableService) throws IOException {
        controller.TableOverviewController controller = new controller.TableOverviewController();
        controller.setTableSelectedIndex(new SimpleIntegerProperty());
        controller.setTableService(tableService);
        return controller;
    }

    @Bean
    @Autowired
    public controller.CreateTableController createTableController(TableService tableService) throws IOException {
        controller.CreateTableController controller = new controller.CreateTableController();
        controller.setTableService(tableService);
        return controller;
    }

    @Bean
    @Autowired
    public controller.CreateRowInTableController createRowInTableController(
            controller.TableOverviewController toController, TableService tableService) throws IOException {
        controller.CreateRowInTableController controller = new controller.CreateRowInTableController();
        controller.setTableIndex(toController.getTableSelectedIndex());
        controller.setTableService(tableService);
        return controller;
    }
}
