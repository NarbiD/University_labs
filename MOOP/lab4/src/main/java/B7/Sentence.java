package B7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sentence {
    List<PartOfSentence> partsOfSentence;
    String[] punctuationMark = {".", ";", ",", "?", "!", "+", "-", "=", "(", ")", "*", "/", "\""};
    String[] space = {"\t", "\n", " "};


    public Sentence(String sentence) {
        this.partsOfSentence = parseSentence(sentence);
    }

    private List<PartOfSentence> parseSentence(String sentence) {
        List<PartOfSentence> partsOfSentence = new ArrayList<>();
        StringBuilder currentPart = new StringBuilder();
        sentence = sentence.trim();
        for (int i = 0; i < sentence.length(); i++){
            String character = ((Character)sentence.charAt(i)).toString();
            if (Character.isLetter(sentence.charAt(i))) {
                currentPart.append(sentence.charAt(i));
            } else {
                if (!currentPart.toString().isEmpty()) {
                    partsOfSentence.add(new Word(currentPart.toString()));
                    currentPart = new StringBuilder();
                }
                if (Arrays.stream(punctuationMark).anyMatch(character::equals)) {
                    partsOfSentence.add(new Char(character));
                } else if(Arrays.stream(space).anyMatch(character::equals)) {
                    if (!PartOfSentence.SPACE.equals(partsOfSentence.get(partsOfSentence.size()-1))) {
                        partsOfSentence.add(PartOfSentence.SPACE);
                    }
                }
            }
        }
        if (!currentPart.toString().isEmpty()) {
            partsOfSentence.add(new Word(currentPart.toString()));
        }
        return partsOfSentence;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        partsOfSentence.forEach(s -> stringBuilder.append(s.toString()));
        return stringBuilder.toString();
    }

    public List<Word> getWords() {
        List<Word> words = new ArrayList<>();
        for (PartOfSentence p : this.partsOfSentence) {
            if(p instanceof Word) {
                words.add((Word)p);
            }
        }
        return words;
    }

    public boolean contains(PartOfSentence partOfSentence) {
        for (PartOfSentence p : this.partsOfSentence) {
            if (p.equals(partOfSentence)) {
                return true;
            }
        }
        return false;
    }
}
