#include <iostream>
#include "IOArray.h"
#include "Interchange.h"
using namespace std;

int main(){
	int array[MAX_SIZE_OF_THE_ARRAY];
    int currentSizeOfArray = 0;
	string fileName = "";

    switch (selectInputMethod()){
		case 1:
            keyboardInput(array, currentSizeOfArray);
            break;
		case 2:
            randomInput(array, currentSizeOfArray);
            break;
		case 3:
            inputFromFile(array, currentSizeOfArray);
            break;
		default:
            break;
    }

    if (currentSizeOfArray > 0) {
        cout << "Original array:" << endl;
        printArrayToScreen(array, currentSizeOfArray);

        switch (selectOutputMethod()) {
            case 1:
                printArrayToScreen(interchange(array, currentSizeOfArray), currentSizeOfArray);
                break;
            case 2:
                printArrayToFile(interchange(array, currentSizeOfArray), currentSizeOfArray);
                break;
            default:
                break;
        }
    }

    cout << "<<<END OF WORK>>>" << endl;
    return 0;
}
