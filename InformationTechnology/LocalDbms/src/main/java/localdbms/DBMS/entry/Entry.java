package localdbms.DBMS.entry;

import localdbms.DataType;
import org.json.JSONObject;
import java.awt.image.BufferedImage;
import java.util.List;

public interface Entry {
    List<Object> getValues();
    BufferedImage getImage();
    void setImage(BufferedImage image);
    JSONObject getJson();
    List<DataType> getTypes();
}
