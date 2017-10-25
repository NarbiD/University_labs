package webdbms.controller;

import org.springframework.web.bind.annotation.*;
import webdbms.DBMS.database.Database;
import webdbms.DBMS.exception.StorageException;
import webdbms.DBMS.table.Table;
import webdbms.facades.EntryFacade;
import webdbms.service.DatabaseService;
import webdbms.service.TableService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/databases/{databaseName}/tables/{tableName}")
public class EntryController {

    private DatabaseService databaseService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/addRow", method = RequestMethod.POST)
    public void addRow(@PathVariable String databaseName, @PathVariable String tableName,
                       @RequestBody Map<String, Object> requestBody) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        Table table = new TableService(database).findByName(tableName);
        table.addRow((List<Object>)requestBody.get("values"), null);
        database.save();
    }

    @RequestMapping(value = "/deleteRow/{rowNumber}", method = RequestMethod.DELETE)
    public void deleteRow(@PathVariable String databaseName, @PathVariable String tableName,
                          @PathVariable int rowNumber) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        Table table = new TableService(database).findByName(tableName);
        table.deleteRow(rowNumber);
        database.save();
    }

    @RequestMapping(value = "/getRows", method = RequestMethod.GET)
    public List<EntryFacade> getRows(@PathVariable String databaseName,
                                     @PathVariable String tableName) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        List<EntryFacade> entries = new ArrayList<>();
        new TableService(database).findByName(tableName).getEntries()
                .forEach(entry -> entries.add(new EntryFacade(entry)));
        return entries;
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
}
