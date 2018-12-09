package B7;

import java.util.ArrayList;
import java.util.List;

public class Paragraph {
    private List<Sentence> sentences;

    public Paragraph(String text) {
        this();
        String[] sentences = text.split("(\\.)|(\\.\\.\\.)|!\\s");
        for (String s : sentences) {
            this.sentences.add(new Sentence(s));
        }
    }

    public Paragraph() {
        sentences = new ArrayList<>();
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void add(Sentence sentence) {
        sentences.add(sentence);
    }

    @Override
    public String toString() {
        return "Paragraph{" +
                "sentences=" + sentences +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paragraph paragraph = (Paragraph) o;

        return sentences != null ? sentences.equals(paragraph.sentences) : paragraph.sentences == null;
    }

    @Override
    public int hashCode() {
        return sentences != null ? sentences.hashCode() : 0;
    }
}
