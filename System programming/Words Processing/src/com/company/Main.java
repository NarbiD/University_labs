package com.company;


import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "C:\\eSupport\\q.txt";
        ReaderFromFile reader = new ReaderFromFile(fileName);
        reader.readWords();
        reader.printWords();
    }
}
