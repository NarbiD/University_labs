package С7;

import B7.Paragraph;
import B7.Sentence;
import B7.Word;

import java.util.List;

public class ParagraphService {
    public static Paragraph crossOutSentencesWithoutSameWord(Paragraph text) {
        Paragraph result = new Paragraph();
        for (Sentence s : text.getSentences()) {
            List<Word> words = s.getWords();
            r: for (Sentence s2 : text.getSentences()) {
                if (s.equals(s2)) {
                    continue;
                }
                for (Word w : s2.getWords()) {
                    if (words.contains(w)) {
                        result.add(s);
                        break r;
                    }
                }
            }
        }
        return result;
    }

    public boolean existsSameWords(Sentence s1, Sentence s2) {
        for (Word w: s1.getWords()) {
            if (s2.contains(w)) {
                return true;
            }
        }
        return false;
    }

}
