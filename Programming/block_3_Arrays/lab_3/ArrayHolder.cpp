//
// Created by NarbiD on 07.01.2017.
//

#include <iostream>
#include <time.h>
#include <iomanip>
#include <random>
#include "ArrayHolder.h"

ArrayHolder::ArrayHolder() : currentSize(0), MAX_SIZE(0), array(nullptr) {
}

ArrayHolder::ArrayHolder(int size) {
    array = new int[size];
    srand(unsigned(time(NULL)));
    for (currentSize = 0; currentSize < size; ++currentSize){
        array[currentSize] = rand() % 100;
    }
}

void ArrayHolder::keyboardInput() {
    currentSize = 1;
    MAX_SIZE = 20;
    array = new int[MAX_SIZE];
    while(true) {
        if (currentSize > MAX_SIZE){
            MAX_SIZE *= 2;
            int* newArray;
            newArray = new int [MAX_SIZE];
            for (int i = 0; i < currentSize; ++i)
                newArray[i] = array[i];
            delete[] array;
            array = newArray;
        }
        cout << "Enter element number " << currentSize << ": " ;
        int currentElement;
        cin >> currentElement;
        cin.clear();
        cin.ignore();
        if (currentElement == -1){
            cout << "You entered " << currentSize - 1 << " elements." << endl;
            break;
        }
        if (currentElement < 0 || currentElement > 99) {
            cout << "An element number " << currentSize << " is not correct!" << endl;
        }
        else {
            array[currentSize-1] = currentElement;
            ++currentSize;
        }
        if (cin.eof()) {
            break;
        }
    }
    --currentSize;
}

void ArrayHolder::printToScreen() {
    printToScreen(array, currentSize);
}

void  ArrayHolder::printToFile(int array[], int size, ofstream &fout) {
    for (int i = 0; i < size; ++i) {
        fout << setw(11) << right << array[i] << ' ';
        if (i != 0 && (i + 1) % 5 == 0) {
            fout << endl;
        }
    }
    cout << "Array was printed to file" << endl;
}

void  ArrayHolder::printToScreen(int array[], int size) {

    for (int i = 0; i < size; ++i){
        cout << setw(11) << right << array[i] << ' ';
        if (i != 0 && (i + 1) % 5 == 0) {
            cout << endl;
        }
    }
    cout << endl << endl;
    return;
}

int* ArrayHolder::interchange(){
    int* tempArray = new int[currentSize];

    // interchange the elements from k to m and from p to s
    int k, m, p, s;
    do {
        getParameters(k, m, p, s);
    } while (s > currentSize || m > currentSize || k < 1 || m <= k || p < 1 || s <= p);

    // the array is indexed from zero, but the parameters - from one
    --k; --m; --p; --s;

    // if the segments are in the reverse order
    if (k > p) {
        swap(k, p);
        swap(m, s);
    }

    // swap segments in the array
    if ((k < m && p < s) && (k < p && m < s)){
        int kOld = k;
        for (int i = k; i < p; ++i)
            tempArray[i] = array[i];
        for (int i = p; i < s + 1; ++i, ++k)
            array[k] = array[i];
        for (int i = m + 1; i < p; ++i, ++k)
            array[k] = tempArray[i];
        for (int i = kOld; i < m + 1; ++i, ++k)
            array[k] = tempArray[i];
    }
    return array;
}

void ArrayHolder::getParameters(int& k, int& m, int& p, int& s) {
    cout << "Enter parameters k, m, p, s" << endl;
    cout << "k, m, p, s > 0" << endl;
    cout << "k < m  &  p < s" << endl;
    cout << "s, m < " << currentSize << endl;
    cin >> k >> m >> p >> s;
    cin.ignore();
}

void ArrayHolder::inputFromFile(ifstream &fin) {
    currentSize = 1;
    MAX_SIZE = 20;
    array = new int[MAX_SIZE];
    while(!fin.eof()) {
        if (currentSize > MAX_SIZE){
            MAX_SIZE *= 2;
            int* newArray;
            newArray = new int [MAX_SIZE];
            for (int i = 0; i < currentSize; ++i)
                newArray[i] = array[i];
            delete[] array;
            array = newArray;
        }
        int currentElement;
        fin >> currentElement;
        fin.clear();
        fin.ignore();
        if (currentElement < 0 || currentElement > 99) {
            cout << "An element number " << currentSize << " is not correct!" << endl;
        }
        else {
            array[currentSize-1] = currentElement;
            ++currentSize;
        }
        if (fin.eof())
            break;
    }
    --currentSize;
}

int ArrayHolder::getCurrentSize() {
    return currentSize;
}

ArrayHolder::~ArrayHolder() {
    if (array) delete [] array;
}
