package webdbms.DBMS.entry;

import org.json.JSONObject;

import java.util.List;

public interface Entry {

    List<Object> getValues();
    String getImage();

    void setImage(String image);
    JSONObject getJson();
}
