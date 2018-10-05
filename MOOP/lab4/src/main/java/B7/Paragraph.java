package B7;

import java.util.ArrayList;
import java.util.List;

public class Paragraph {
    private List<Sentence> sentences;

    public Paragraph(String text) {
        sentences = new ArrayList<>();
        String[] sentences = text.split("(\\.)|(\\.\\.\\.)|!\\s");
        for (String s : sentences) {
            this.sentences.add(new Sentence(s));
        }
    }

    public List<Sentence> getSentences() {
        return sentences;
    }
}
