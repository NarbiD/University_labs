package com.company;


import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "";
        Word current = new Word();
        while (!current.isEndOfFile()) {
            if (current.check()) {
                current.printWord();
            }
            // TODO current.readNewWord(fileName);
        }
    }
}
