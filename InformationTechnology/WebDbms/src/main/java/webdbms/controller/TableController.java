package webdbms.controller;

import org.springframework.web.bind.annotation.*;
import webdbms.DBMS.database.Database;
import webdbms.DBMS.datatype.DataType;
import webdbms.DBMS.datatype.constraint.RealConstraint;
import webdbms.DBMS.exception.StorageException;
import webdbms.DBMS.table.Table;
import webdbms.service.DatabaseService;
import webdbms.service.TableService;

import java.util.*;

@RestController
@RequestMapping("/databases/{databaseName}/tables")
public class TableController {

    private DatabaseService databaseService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<Table> getAllTables(@PathVariable String databaseName) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        return new TableService(database).getTables();
    }

    @RequestMapping(value = "/{tableName}", method = RequestMethod.GET)
    public Table getTable(@PathVariable String databaseName, @PathVariable String tableName) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        return new TableService(database).findByName(tableName);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void createTable(@PathVariable String databaseName, @RequestBody Map<String, Object> requestBody) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        String name = requestBody.get("name").toString();
        List<DataType> types = new ArrayList<>();
        ((List<Object>)requestBody.get("types")).forEach(type -> types.add(DataType.valueOf(type.toString())));
        Map<String, Object> jsonConstraint = (Map)requestBody.get("constraint");
        RealConstraint constraint = new RealConstraint((double)jsonConstraint.get("minValue"), (double)jsonConstraint.get("maxValue"));
        List<String> columnNames = new ArrayList<>();
        ((List<Object>)requestBody.get("columnNames")).forEach(colName -> columnNames.add(colName.toString()));
        new TableService(database).createTable(name, types, columnNames, constraint);
    }

    @RequestMapping(value = "/{tableName}", method = RequestMethod.DELETE)
    public void deleteTable(@PathVariable String databaseName, @PathVariable String tableName) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        database.deleteTable(tableName);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{tableName}/addrow", method = RequestMethod.POST)
    public void addRow(@PathVariable String databaseName, @PathVariable String tableName,
                       @RequestBody Map<String, Object> requestBody) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        Table table = new TableService(database).findByName(tableName);
        table.addRow((List<Object>)requestBody.get("values"), null);
        database.save();
    }

    @RequestMapping(value = "/{tableName}/deleterow/{rowNumber}", method = RequestMethod.DELETE)
    public void deleteRow(@PathVariable String databaseName, @PathVariable String tableName,
                       @PathVariable int rowNumber) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        Table table = new TableService(database).findByName(tableName);
        table.deleteRow(rowNumber);
        database.save();
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
}
