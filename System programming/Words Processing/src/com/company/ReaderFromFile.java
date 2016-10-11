package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class ReaderFromFile {
    private ArrayList<String> wordsList;
    private Reader reader;
    private boolean isEndOfFile;

    public ReaderFromFile(String fileName) throws IOException {
        Charset encoding = Charset.defaultCharset();
        try {
            File file = new File(fileName);
            InputStream in = new FileInputStream(file);
            reader = new InputStreamReader(in, encoding);
        }
        catch(FileNotFoundException ex) {
            throw new FileNotFoundException("Unable to open file '" + fileName + "'");
        }
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
        }
        return currentWord.toString();
    }

    public void readWords()
            throws IOException {
        AtomicReference<String> currentWord = new AtomicReference<String>();

        do {
            currentWord.set(getWord());
            if (!wordsList.contains(currentWord.get())) {
                wordsList.add(currentWord.get());
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