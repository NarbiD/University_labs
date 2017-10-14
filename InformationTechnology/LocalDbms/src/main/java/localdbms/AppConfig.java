package localdbms;


import localdbms.controller.CreateDatabaseController;
import localdbms.controller.DatabaseSelectionController;
import localdbms.database.*;
import localdbms.database.exception.EntryException;
import localdbms.database.exception.StorageException;
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
        controller.setDbms(dbms);
        return controller;
    }

    @Bean
    @Autowired
    public CreateDatabaseController createDatabaseController(Dbms dbms) throws StorageException {
        CreateDatabaseController controller = new CreateDatabaseController();
        controller.setDbms(dbms);
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
        } catch (EntryException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public TableFactory tableFactory() {
        return this::table;
    }
}
