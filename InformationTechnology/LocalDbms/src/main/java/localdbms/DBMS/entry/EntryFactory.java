package localdbms.DBMS.entry;

import localdbms.DataType;
import localdbms.DBMS.datatype.constraint.RealConstraint;
import localdbms.DBMS.exception.EntryException;
import org.json.JSONObject;
import java.util.List;

@FunctionalInterface
public interface EntryFactory {
    Entry getEntryFromJson(JSONObject jsonObject, List<DataType> types, RealConstraint constraint) throws EntryException;
}
