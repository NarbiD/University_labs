package localdbms.DBMS;

import javafx.collections.FXCollections;
import localdbms.DataType;
import localdbms.service.TableService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class TableServiceTest {

    TableService tableService;

    @Before
    public void initTableService() throws Exception {
        tableService = new TableService();
    }

    @Test
    public void searchTest() throws Exception {
        Table t1 = new Table("table", "db", Arrays.asList(DataType.values()),
                Arrays.asList("1", "2", "3", "4"), new IntegerInvlConstraint());
        Entry[] entries = {
                new Entry(Arrays.asList("w", 1, 2, 3.0), t1.getTypes(), t1.getConstraint()),
                new Entry(Arrays.asList("w", 1, 5, 3.0), t1.getTypes(), t1.getConstraint()),
                new Entry(Arrays.asList("w", 3, 2, 3.01), t1.getTypes(), t1.getConstraint()),
                new Entry(Arrays.asList("w", 1, 8, 3.0), t1.getTypes(), t1.getConstraint()),
                new Entry(Arrays.asList("i", 1, 2, 3.0), t1.getTypes(), t1.getConstraint())
        };

        for (Entry entry : entries) {
            t1.addRow(entry.getValues(), "");
        }

        tableService.setTables(FXCollections.observableArrayList(Collections.singletonList(t1)));

        Assert.assertEquals(Collections.singletonList(entries[1]), tableService.search(0, Arrays.asList(null, 1, 5, 3.0)));
        Assert.assertEquals(Arrays.asList(entries[0], entries[2]), tableService.search(0, Arrays.asList("w", null, 2, null)));
        Assert.assertEquals(Collections.emptyList(), tableService.search(0, Arrays.asList("i", 1, 5, 3.0)));
        Assert.assertEquals(Arrays.asList(entries), tableService.search(0, Arrays.asList(null, null, null, null)));
    }
}
