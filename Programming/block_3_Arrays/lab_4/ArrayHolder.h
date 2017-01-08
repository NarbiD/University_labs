#pragma once

using namespace std;

class ArrayHolder {
private:
    int MAX_SIZE;
    int currentSize;
    int* array;

    int* first;
    int* last;
    int maxDifference = 0;

public:
    ArrayHolder();
    ArrayHolder(int size);
    void initialization();
	void randomInput(int size);
    void keyboardInput();
    void inputFromFile(ifstream &fin);
    void printToFile(ofstream &fout);
  
    void findLargestSubsets();

    int getCurrentSize();

    ~ArrayHolder();
};





