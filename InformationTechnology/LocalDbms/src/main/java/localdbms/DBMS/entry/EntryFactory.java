package localdbms.DBMS.entry;

import localdbms.DataType;
import org.json.JSONObject;
import localdbms.DBMS.exception.EntryException;

import java.util.List;


@FunctionalInterface
public interface EntryFactory {
    Entry getEntryFromJson(JSONObject jsonObject, List<DataType> types) throws EntryException;
}
