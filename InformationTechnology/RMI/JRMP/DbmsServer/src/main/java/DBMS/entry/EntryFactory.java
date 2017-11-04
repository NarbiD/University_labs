package DBMS.entry;

import common.DataType;
import DBMS.datatype.constraint.RealConstraint;
import DBMS.exception.EntryException;
import org.json.JSONObject;
import java.util.List;

@FunctionalInterface
public interface EntryFactory {
    Entry getEntryFromJson(JSONObject jsonObject, List<DataType> types, RealConstraint constraint) throws EntryException;
}
