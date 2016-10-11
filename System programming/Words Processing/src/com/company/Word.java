package com.company;

public class Word {
    private String word;

    public Word(String _word) {
        word = _word;
    }

    private boolean isVowel(char c) {
        c = Character.toLowerCase(c);
        return c == 'e' || c == 'y' || c == 'u' || c == 'i' || c == 'o' || c == 'a';
    }

    public boolean check() {
        int vowel = 0, consonant = 0;
        for (int i = 0; i < word.length(); i++) {
            if (isVowel(word.charAt(i))) {
                ++vowel;
            }
            else {
                ++consonant;
            }
        }
        return vowel > consonant;
    }
}