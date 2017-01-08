#include <iostream>
#include <fstream>
#include "ArrayHolder.h"

using namespace std;

#define PATH_TO_RESOURCES "C:\\Users\\NarbiD\\OneDrive\\Programming\\University_labs\\Programming\\block_3_Arrays\\lab_4\\resources\\"

int selectInputMethod();
string getFilePath();
int inputSize();

int main() {
    ArrayHolder* array;

    switch (selectInputMethod()){
        case 1: {
            array = new ArrayHolder();
            array->keyboardInput();
            break;
        }
        case 2: {
            array = new ArrayHolder(inputSize());
            break;
        }
        case 3: {
            array = new ArrayHolder();
            string filePath;
            ifstream fin;

            do {
                cout << "Enter the input file name:" << endl;
                cin.ignore();
                filePath = getFilePath();
                if (filePath[filePath.size() - 1] == '\\') {
                    filePath.append("input.txt");
                }
                fin.open(filePath);
            } while (!fin.is_open());
            array->inputFromFile(fin);
            fin.close();
            break;
        }
        case 9: {
            cout << "Good luck!" << endl;
            return 0;
        }
        default: {
            cout << "<<<WRONG PARAMETER>>>" << endl;
            return 1;
        }
    }
    cout << "Done! Press any key to continue." << endl;
    cin.get();

    if (array->getCurrentSize() > 0) {
        ofstream fout;
        array->findLargestSubsets();
        string filePath;

        do {
            cout << "Enter the output file name:" << endl;
            filePath = getFilePath();
            if (filePath[filePath.size()-1] == '\\') {
                filePath.append("output.txt");
            }
            fout.open(filePath);
        } while (!fout.is_open());

        array->printToFile(fout);
        fout.close();
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


string getFilePath() {
    string filePath, fileName;
    while (!getline(cin, fileName)) {
        cout << "Read the file named error!" << endl;
        cout << "Try again!" << endl;
    }
    filePath.append(PATH_TO_RESOURCES + fileName);
    return filePath;
}

int inputSize() {
    int size;
    do {
        cout << "Enter size > 0: ";
        cin >> size;
        cout << endl;
    } while (size < 1);
	return size;
}