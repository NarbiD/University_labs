package webdbms.controller;

import org.springframework.web.bind.annotation.*;
import webdbms.DBMS.database.Database;
import webdbms.DBMS.exception.StorageException;
import webdbms.facades.DatabaseFacade;
import webdbms.service.DatabaseService;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/databases")
public class DatabaseController {
    private DatabaseService databaseService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<Database> getAllDatabases() {
        return databaseService.getDatabases();
    }

    @RequestMapping(value = "/{databaseName}", method = RequestMethod.GET)
    public DatabaseFacade getDatabase(@PathVariable String databaseName) throws StorageException {
        return new DatabaseFacade(databaseService.findByName(databaseName));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void createDatabase(@RequestBody Map<String, String> requestBody) throws StorageException {
        databaseService.createDatabase(requestBody.get("name"));
    }

    @RequestMapping(value = "/{databaseName}", method = RequestMethod.DELETE)
    public void deleteDatabase(@PathVariable String databaseName) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        databaseService.deleteDatabase(database);
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
}
