#include <iostream>
#include <fstream>
#include "ArrayHolder.h"

using namespace std;

#define PATH_TO_RESOURCES "C:\\Users\\NarbiD\\OneDrive\\Programming\\University_labs\\Programming\\block_3_Arrays\\lab_3\\resources\\"

int selectInputMethod();
int selectOutputMethod();
string getFilePath();
int getSize();

int main() {
    ArrayHolder* array;
    ofstream fout;
    ifstream fin;
    string fileName = "";

    switch (selectInputMethod()){
        case 1:
            array = new ArrayHolder();
            array->keyboardInput();
            break;
        case 2:
            array = new ArrayHolder(getSize());
            break;
        case 3:
            array = new ArrayHolder();
            string filePath;
            ifstream fin;
            do {
                filePath = getFilePath();
                if (filePath[filePath.size()-1] == '\\') {
                    filePath.append("input.txt");
                }
                fin.open(filePath);
            } while (!fin.is_open());
            array->inputFromFile(fin);
            fin.close();
            break;
    }

    if (array->getCurrentSize() > 0) {
        cout << "Original array:" << endl;
        array->printToScreen();

        switch (selectOutputMethod()) {
            case 1:
            ArrayHolder::printToScreen(array->interchange(), array->getCurrentSize());
                break;
            case 2:
                string filePath;
                do {
                    filePath = getFilePath();
                    if (filePath[filePath.size()-1] == '\\') {
                        filePath.append("output.txt");
                    }
                    fout.open(filePath);
                } while (!fout.is_open());
                ArrayHolder::printToFile(array->interchange(), array->getCurrentSize(), fout);
                fout.close();
                break;
        }
    }

    cout << "<<<END OF WORK>>>" << endl;
    return 0;
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

string getFilePath() {
    string filePath, fileName;
    cout << "Enter the file name:" << endl;
    cin.ignore();
    while (!getline(cin, fileName)) {
        cout << "Read the file named error!" << endl;
        cout << "Try again!" << endl;
    }
    filePath.append(PATH_TO_RESOURCES + fileName);
    return filePath;
}

int getSize() {
    int size;
    do {
        cout << "Enter size > 0: ";
        cin >> size;
        cout << endl;
    } while (size < 1);
}