package DBMS.entry;

import common.DataType;
import DBMS.datatype.constraint.RealConstraint;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

@FunctionalInterface
public interface EntryFactory {
    Entry getEntryFromJson(JSONObject jsonObject, List<DataType> types, RealConstraint constraint) throws IOException;
}
