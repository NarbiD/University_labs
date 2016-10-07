package com.company;

/**
 * Created by NarbiD on 07.10.2016.
 */
public class Word {
    private String word;

    private boolean isVowel(char c) {
        c = Character.toLowerCase(c);
        if (c == 'e'  || c == 'y' || c == 'u' || c == 'i' || c == 'o' || c == 'a') return true;
        else return false;
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
        if (vowel < consonant) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isEndOfFile()
    {
        if (word == "0") {
            return true;
        }
        else {
            return false;
        }
    }
    public void printWord()
    {
        System.out.println(word);
    }

    // TODO readNewWord();
}
