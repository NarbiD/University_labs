package webdbms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import webdbms.DBMS.DataType;

@RestController
@RequestMapping("/types")
public class TypeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public DataType[] getAllTables() {
        return DataType.values();
    }
}
