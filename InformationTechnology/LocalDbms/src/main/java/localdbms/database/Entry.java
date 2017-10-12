package localdbms.database;

import org.json.JSONObject;
import java.util.List;

public interface Entry {
    List<Object> getValues();
    JSONObject getJson();
}
