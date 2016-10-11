package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.io.IOException;
import java.util.ArrayList;

public class ReaderFromFile {
    private File file;
    private ArrayList<String> wordsList;

    private Charset encoding = Charset.defaultCharset();

    private InputStream in;
    private Reader buffer;
    private Reader reader;
    private boolean isEndOfFile;

    public ReaderFromFile(String fileName) throws IOException {
        file = new File(fileName);
        in = new FileInputStream(file);
        reader = new InputStreamReader(in, encoding);
        buffer = new BufferedReader(reader);
        wordsList = new ArrayList<String>();
        isEndOfFile = false;
    }

    private String getWord()
            throws IOException {
        StringBuilder currentWord = new StringBuilder("");
        int intCharacter;
        while(true) {
            if ((intCharacter = reader.read()) == -1) {
                isEndOfFile = true;
                break;
            }
            if (Character.isLetter((char) intCharacter)) {
                currentWord.append((char) intCharacter);
            } else if (!currentWord.equals("")) break;
            else continue;
        }
        return currentWord.toString();
    }

    public void readWords()
            throws IOException {
        String currentWord;

        do {
            currentWord = getWord();
            if (!wordsList.contains(currentWord)) {
                wordsList.add(currentWord);
            }
        } while (!isEndOfFile);
    }

    public void printWords()
            throws IOException {
        Word current;
        for (String word : wordsList) {
            current = new Word(word);
            if ((current.check())) {
                System.out.println(word);
            }
        }
    }
}
