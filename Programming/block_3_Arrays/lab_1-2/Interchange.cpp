#include <iostream>
#include "Interchange.h"

using namespace std;

int* interchange(int array[], int size){
    int tempArray[size];

    // interchange the elements from k to m and from p to s
    int k, m, p, s;
    do {
        getParameters(k, m, p, s, size);
    } while (s > size || m > size || k < 1 || m <= k || p < 1 || s <= p);

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

void getParameters(int& k, int& m, int& p, int& s, int maxValue) {
    cout << "Enter parameters k, m, p, s" << endl;
    cout << "k, m, p, s > 0" << endl;
    cout << "k < m  &  p < s" << endl;
    cout << "s, m < " << maxValue << endl;
    cin >> k >> m >> p >> s;
    cin.ignore();
}
