package webdbms.controller;

import org.springframework.web.bind.annotation.*;
import webdbms.DBMS.datatype.DataType;
import webdbms.DBMS.datatype.constraint.RealConstraint;
import webdbms.DBMS.exception.StorageException;
import webdbms.DBMS.table.Table;
import webdbms.facades.DatabaseFacade;
import webdbms.facades.TableFacade;
import webdbms.service.DatabaseService;
import webdbms.service.TableService;
import webdbms.service.exception.InternalServerException;
import webdbms.service.exception.InvalidRequestBodyException;
import java.util.*;

@RestController
@RequestMapping("/databases/{databaseName}/tables")
public class TableController {

    private DatabaseService databaseService;
    private TableService tableService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<String> getAllTables(@PathVariable String databaseName) {
        try {
            return new DatabaseFacade(databaseService.findByName(databaseName))
                    .getTableNames();
        } catch (StorageException e) {
            throw new InternalServerException(e);
        }
    }

    @RequestMapping(value = "/{tableName}", method = RequestMethod.GET)
    public TableFacade getTable(@PathVariable String databaseName,
                                @PathVariable String tableName) {
        try {
            Table table = tableService.findByName(databaseName, tableName);
            return new TableFacade(table);
        } catch (StorageException e) {
            throw new InternalServerException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void createTable(@PathVariable String databaseName,
                            @RequestBody Map<String, Object> requestBody) {
        try {
            String name = requestBody.get("tableName").toString();
            List<DataType> types = getTypesFromObjects((List<Object>) requestBody.get("columnTypes"));
            RealConstraint constraint = getConstraintFromMap((Map) requestBody.get("realIntervalConstraint"));
            List<String> columnNames = getColumnNamesFromObjects((List<Object>) requestBody.get("columnNames"));
            tableService.createTable(databaseName, name, types, columnNames, constraint);
        } catch (NullPointerException e) {
            throw new InvalidRequestBodyException();
        } catch (StorageException e) {
            throw new InternalServerException(e);
        }
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
                            @PathVariable String tableName) {
        try {
            tableService.deleteTable(databaseName, tableName);
        } catch (StorageException e) {
            throw new InternalServerException(e);
        }
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void setTableService(TableService tableService) {
        this.tableService = tableService;
    }
}
