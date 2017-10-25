package webdbms.controller;

import org.springframework.web.bind.annotation.*;
import webdbms.DBMS.database.Database;
import webdbms.DBMS.datatype.DataType;
import webdbms.DBMS.datatype.constraint.RealConstraint;
import webdbms.DBMS.exception.StorageException;
import webdbms.facades.DatabaseFacade;
import webdbms.facades.TableFacade;
import webdbms.service.DatabaseService;
import webdbms.service.TableService;

import java.util.*;

@RestController
@RequestMapping("/databases/{databaseName}/tables")
public class TableController {

    private DatabaseService databaseService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<String> getAllTables(@PathVariable String databaseName) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        return new DatabaseFacade(database).getTableNames();
    }

    @RequestMapping(value = "/{tableName}", method = RequestMethod.GET)
    public TableFacade getTable(@PathVariable String databaseName,
                                @PathVariable String tableName) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        return new TableFacade(new TableService(database).findByName(tableName));
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void createTable(@PathVariable String databaseName,
                            @RequestBody Map<String, Object> requestBody) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        String name = requestBody.get("name").toString();
        List<DataType> types = getTypesFromObjects((List<Object>)requestBody.get("types"));
        RealConstraint constraint = getConstraintFromMap((Map)requestBody.get("constraint"));
        List<String> columnNames = getColumnNamesFromObjects((List<Object>)requestBody.get("columnNames"));
        new TableService(database).createTable(name, types, columnNames, constraint);
    }

    private List<DataType> getTypesFromObjects(List<Object> objects) {
        List<DataType> types = new ArrayList<>();
        objects.forEach(type -> types.add(DataType.valueOf(type.toString())));
        return types;
    }

    private RealConstraint getConstraintFromMap(Map<String, Object> map) {
        return new RealConstraint((double)map.get("minValue"), (double)map.get("maxValue"));
    }

    private List<String> getColumnNamesFromObjects(List<Object> objects) {
        List<String> names = new ArrayList<>();
        objects.forEach(colName -> names.add(colName.toString()));
        return names;
    }

    @RequestMapping(value = "/{tableName}", method = RequestMethod.DELETE)
    public void deleteTable(@PathVariable String databaseName,
                            @PathVariable String tableName) throws StorageException {
        Database database = databaseService.findByName(databaseName);
        database.deleteTable(tableName);
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
}
