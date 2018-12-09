package B7;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class B7test {
    @Test
    public void UniqueWordFinderTest() {
        List<Word> w = new ArrayList<>(1);
        w.add(new Word("мыла"));
        Assert.assertEquals(w,
                UniqueWordFinder.findUniqWordsFromFirstSentence(new Paragraph("Мама, мыла    раму. Рама мыла маму. ")));
    }

}
