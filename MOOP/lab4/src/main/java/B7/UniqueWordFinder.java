package B7;

import java.util.ArrayList;
import java.util.List;

public class UniqueWordFinder {
    static List<Word> findUniqWordsFromFirstSentence(Paragraph p) {
        List<Word> result = new ArrayList<>();
        if (!p.getSentences().isEmpty()) {
            List<Word> allWords = p.getSentences().get(0).getWords();
            allWords.forEach(word -> {
                for (int i = 1; i < p.getSentences().size(); i++) {
                    if (p.getSentences().get(i).contains(word)) {
                        result.add(word);
                    }
                }
            });
        }
        return result;

    }
}
