package localdbms;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import localdbms.controller.*;
import localdbms.database.*;
import localdbms.database.exception.EntryException;
import localdbms.database.exception.StorageException;
import localdbms.database.exception.TableException;
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
        return new DbmsImpl();
    }

    @Bean
    @Autowired
    public DatabaseSelectionController databaseSelectionController(Dbms dbms) throws StorageException {
        DatabaseSelectionController controller = new DatabaseSelectionController();
        controller.setSelectedIndex(new SimpleIntegerProperty());
        controller.setDatabases(FXCollections.observableArrayList());
        controller.setDbms(dbms);
        return controller;
    }

    @Bean
    @Autowired
    public CreateDatabaseController createDatabaseController(Dbms dbms, DatabaseSelectionController dsController) throws StorageException {
        CreateDatabaseController controller = new CreateDatabaseController();
        controller.setDbms(dbms);
        controller.setDatabases(dsController.getDatabases());
        return controller;
    }

    @Bean
    @Autowired
    public TableOverviewController tableOverviewController(DatabaseSelectionController dsController) throws StorageException {
        TableOverviewController controller = new TableOverviewController();
        controller.setTables(FXCollections.observableArrayList());
        controller.setDatabases(dsController.getDatabases());
        controller.setDbIndex(dsController.getSelectedIndex());
        controller.setTableSelectedIndex(new SimpleIntegerProperty());
        return controller;
    }

    @Bean
    @Autowired
    public CreateTableController createTableController(TableOverviewController toController) throws StorageException {
        CreateTableController controller = new CreateTableController();
        controller.setTables(toController.getTables());
        controller.setDatabases(toController.getDatabases());
        controller.setDbIndex(toController.getDbIndex());
        return controller;
    }

    @Bean
    @Autowired
    public CreateRowInTableController createRowInTableController(TableOverviewController toController) throws StorageException {
        CreateRowInTableController controller = new CreateRowInTableController();
        controller.setTables(toController.getTables());
        controller.setTableIndex(toController.getTableSelectedIndex());
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
