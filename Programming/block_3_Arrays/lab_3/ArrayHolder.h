//
// Created by NarbiD on 07.01.2017.
//

#pragma once

#include <fstream>
using namespace std;

class ArrayHolder {
private:
    int MAX_SIZE;
    int currentSize;
    int* array;

    void getParameters(int &k, int &m, int &p, int &s);

public:
    ArrayHolder();
    ArrayHolder(int size);

    void keyboardInput();
    void inputFromFile(ifstream &fin);

    static void printToFile(int array[], int size, ofstream &fout);
    static void printToScreen(int array[], int size);
    void printToScreen();
    int* interchange();

    int getCurrentSize();

    ~ArrayHolder();
};
