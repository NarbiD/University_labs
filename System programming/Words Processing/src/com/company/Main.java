package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            String fileName = "C:\\eSupport\\q.txt";
            ReaderFromFile reader = new ReaderFromFile(fileName);
            reader.readWords();
            reader.printWords();
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}