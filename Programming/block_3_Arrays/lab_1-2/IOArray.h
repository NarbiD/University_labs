#ifndef BLOCK_3_ARRAYS_IOARRAY_H
#define BLOCK_3_ARRAYS_IOARRAY_H

#define MAX_SIZE_OF_THE_ARRAY 20
#define PATH_TO_RESOURCES "C:\\Users\\NarbiD\\OneDrive\\Programming\\University_labs\\Programming\\block_3_Arrays\\lab_1-2\\resources\\"

int selectInputMethod();
int selectOutputMethod();
void keyboardInput(int array[], int &size);
void randomInput(int array[], int &size);
void inputFromFile(int array[], int &size);
void printArrayToScreen(int array[], int size);
void printArrayToFile(int array[], int size);

#endif //BLOCK_3_ARRAYS_IOARRAY_H