package C7;

import B7.Paragraph;
import org.junit.Assert;
import org.junit.Test;
import С7.ParagraphService;

public class C7test {

    @Test
    public void crossOutTest() {
        Assert.assertEquals(new Paragraph("Мама, мыла        раму. Рама мыла маму. Мама мыла лыжи."),
        ParagraphService.crossOutSentencesWithoutSameWord(new Paragraph("Мама, мыла        раму. Рама мыла маму. Мама мыла лыжи. Папа мыл машину.")));
    }
}
