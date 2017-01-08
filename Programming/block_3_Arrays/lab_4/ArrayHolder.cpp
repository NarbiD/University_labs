#include <iostream>
#include <random>
#include <time.h>
#include <iomanip>
#include <fstream>
#include "ArrayHolder.h"

using namespace std;
typedef unsigned long long ull;

ArrayHolder::ArrayHolder() : currentSize(0), MAX_SIZE(0), array(nullptr), first(nullptr), last(nullptr) {
}

ArrayHolder::ArrayHolder(int size) {
    array = new int[size];
	randomInput(size);
}

void ArrayHolder::randomInput(int size) {
	srand(unsigned(time(NULL)));
	for (currentSize = 0; currentSize < size; ++currentSize) {
		array[currentSize] = rand() % 100;
	}
}

void ArrayHolder::initialization() {
    MAX_SIZE = 20;
    array = new int[MAX_SIZE];
}

void ArrayHolder::keyboardInput() {
    initialization();
    currentSize = 1;

    do {
        if (currentSize > MAX_SIZE){
            MAX_SIZE *= 2;
            int* newArray;
            newArray = new int [MAX_SIZE];
            for (int i = 0; i < currentSize; ++i) {
                newArray[i] = array[i];
            }
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
    } while (!cin.eof());

    --currentSize;
}

void  ArrayHolder::printToFile(ofstream &fout) {
    fout << "\n Source: \n";

    for (int i = 0; i < currentSize; ++i) {
        fout << setw(11) << right << array[i] << ' ';

        if (i != 0 && (i + 1) % 5 == 0) {
            fout << endl;
        }
    }
    fout << "\n Max subset: \n";
    for (int i = 0, subsetNumber = 0; i < currentSize; ++i) {
        int numberOfDigitsInCurrentRow = 0;

        if (last[i] - first[i] == maxDifference) {
            fout << "\nSubset #" << ++subsetNumber << ':' << endl;

            for (int j = first[i]; j < last[i] + 1; ++j, ++numberOfDigitsInCurrentRow) {
                fout << setw(11) << right << array[j];

                if (numberOfDigitsInCurrentRow != 0 && (numberOfDigitsInCurrentRow + 1) % 5 == 0
                                && numberOfDigitsInCurrentRow != currentSize - 1) {

                    fout << endl;
                }
            }
        }
    }
	cout << "Result was printed to file" << endl;
}


void ArrayHolder::inputFromFile(ifstream &fin) {
    initialization();
    currentSize = 1;

    while(!fin.eof()) {
        if (currentSize > MAX_SIZE){
            MAX_SIZE *= 2;
            int* newArray;
            newArray = new int [MAX_SIZE];
            for (int i = 0; i < currentSize; ++i) {
                newArray[i] = array[i];
            }
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
        else if (!fin.eof()) {
            array[currentSize-1] = currentElement;
            ++currentSize;
        }
    }
    --currentSize;
}

void ArrayHolder::findLargestSubsets() {
    ull sum = 0, mod = 0;
    first = new int[currentSize];
    last = new int[currentSize];

    for (int i = 0; i < currentSize; ++i)
        first[i] = last[i] = 0;

    for (int i = 0; i < currentSize; ++i){
        sum += (ull)array[i];
        mod = sum % (ull)currentSize;

        if (mod == 0) {
            last[mod] = i;
            continue;
        }
        if (first[mod] == 0)
            first[mod] = i + 1;
        last[mod] = i;
    }
    for (int i = 0; i < currentSize; ++i) {
        int difference = last[i] - first[i];
        if (difference > maxDifference) maxDifference = difference;
    }
}

int ArrayHolder::getCurrentSize() {
    return currentSize;
}

ArrayHolder::~ArrayHolder() {
    if (array) delete [] array;
    if (first) delete[] first;
    if (last) delete[] last;
}