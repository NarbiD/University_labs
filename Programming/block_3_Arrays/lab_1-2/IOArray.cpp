#include <iostream>
#include <iomanip>
#include <fstream>
#include <random>
#include "IOArray.h"

using namespace std;

string getFilePath() {
    string filePath, fileName;
    cout << "Enter the file name:" << endl;
    while (!getline(cin, fileName)) {
        cout << "Read the file named error!" << endl;
        cout << "Try again!" << endl;
    }
    filePath.append(PATH_TO_RESOURCES + fileName);
    return filePath;
}

int selectInputMethod(){
    int selector;

    do {
        cout << "Select input method:" << endl;
        cout << "1. Keyboard input" << endl;
        cout << "2. Randomly input" << endl;
        cout << "3. Input from file" << endl;
        cout << "9. End of work" << endl;
        cin >> selector;
        cin.ignore();
        cout << endl;
    } while (selector == 1 && selector == 2 && selector == 3 && selector == 9);
    return selector;
}

int selectOutputMethod(){
    int selector;

    do {
        cout << "Select output method:" << endl;
        cout << "1. Output to the screen" << endl;
        cout << "2. Output to the file" << endl;
        cout << "9. End of work" << endl;
        cin >> selector;
        cout << endl;
    } while (selector == 1 && selector == 2 && selector == 9);
    return selector;
}

void printArrayToScreen(int array[], int size) {

    for (int i = 0; i < size; ++i){
        cout << setw(11) << right << array[i] << ' ';
        if (i != 0 && (i + 1) % 5 == 0) {
            cout << endl;
        }
    }
    cout << endl << endl;
    return;
}

void printArrayToFile(int array[], int size) {
    ofstream fout;
    string filePath;
    do {
        filePath = getFilePath();
        if (filePath[filePath.size()-1] == '\\') {
            filePath.append("output.txt");
        }
        fout.open(filePath);
    } while (!fout.is_open());

    for (int i = 0; i < size; ++i) {
        fout << setw(11) << right << array[i] << ' ';
        if (i != 0 && (i + 1) % 5 == 0) {
            fout << endl;
        }
    }
    cout << "Array was printed to file" << endl;

    fout.close();
    return;
}

void keyboardInput(int array[], int &size){

    for (int i = 0; i < MAX_SIZE_OF_THE_ARRAY; ++i) {
        int currentElement;
        cout << "Enter element " << size + 1 << '.' << endl;
        cin >> currentElement;
        cin.clear();
        cin.ignore();
        if (currentElement < 0 || currentElement > 99) {
            cout << "An element number " << i << " is not correct!" << endl;
        }
        else {
            array[size] = currentElement;
            ++size;
        }
        if (cin.eof()) {
            break;
        }

    }
    // after this loop variable size = current size of the array
    cin.clear();
    return;
}

void randomInput(int array[], int &size){
    srand(unsigned(time(NULL)));

    do {
        cout << "Enter number of elements to be filled (between 1 and 20)." << endl;
        cin >> size;
    } while(size < 1 || size > 20);

    for (int i = 0; i < size; ++i) {
        array[i] = rand() % 100;
    }
    return;
}

void inputFromFile(int array[], int &size){
    string fileName, filePath;
    fstream fin;

    do {
        filePath = getFilePath();
        if (filePath[filePath.size()-1] == '\\') {
            filePath.append("input.txt");
        }
        fin.open(filePath);
    } while (!fin.is_open());

    for (int i = 0; !fin.eof(), i < MAX_SIZE_OF_THE_ARRAY; ++i) {
        int currentElement;
        fin >> currentElement;
        if (currentElement < 0 || currentElement > 99) {
            cout << "An element number " << i << " is not correct!" << endl;
        }
        else {
            array[size] = currentElement;
            ++size;
        }
    }

    fin.close();
    return;
}
