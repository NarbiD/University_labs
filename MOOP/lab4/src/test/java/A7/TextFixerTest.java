package A7;

import A7.TextFixer;
import org.junit.Assert;
import org.junit.Test;

public class TextFixerTest {

    @Test
    public void simpleTest() {
        Assert.assertEquals(TextFixer.fix("RussiaPalandUkraine"), "RussiaPolandUkraine");
    }

    @Test
    public void patternInTheEndTest() {
        Assert.assertEquals(TextFixer.fix("UkrainePa"), "UkrainePo");
    }

    @Test
    public void patternInTheBeginTest() {
        Assert.assertEquals(TextFixer.fix("Paland"), "Poland");
    }

    @Test
    public void fewMatchesTest() {
        Assert.assertEquals(TextFixer.fix("Paland-papkorn-park-silpa-kompat"),
                "Poland-popkorn-pork-silpo-kompot");
    }
}
