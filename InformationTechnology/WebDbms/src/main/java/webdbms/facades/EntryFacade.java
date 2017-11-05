package webdbms.facades;

import webdbms.DBMS.Entry;
import java.util.List;

public class EntryFacade {

    private Entry entry;

    public EntryFacade(Entry entry) {
        this.entry = entry;
    }

    public List<Object> getValues() {
        return entry.getValues();
    }

    public String getFile() throws Exception {
        return entry.getFile();
    }
}
