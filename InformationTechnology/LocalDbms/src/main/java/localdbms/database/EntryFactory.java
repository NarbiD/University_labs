package localdbms.database;

import localdbms.database.exception.EntryException;
import org.json.JSONObject;
import java.util.List;

@FunctionalInterface
public interface EntryFactory {
    Entry getEntryFromJson(JSONObject jsonObject, List<DataType> types) throws EntryException;
}
