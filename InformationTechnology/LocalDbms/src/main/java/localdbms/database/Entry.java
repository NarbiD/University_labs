package localdbms.database;

import org.json.JSONObject;
import java.awt.image.BufferedImage;
import java.util.List;

public interface Entry {
    List<Object> getValues();

    BufferedImage getImage();

    void setPic(BufferedImage image);

    JSONObject getJson();

    List<DataType> getTypes();
}
