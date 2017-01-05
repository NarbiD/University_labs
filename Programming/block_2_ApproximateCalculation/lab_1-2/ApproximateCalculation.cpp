#include <iostream>
#include <cmath>

#define EPS 1.0e-8
#define MIN_TAB_STEP 1.0e-6

using namespace std;

// calculates using standard library
double f_lib(double x);
// calculated using the formula
double f_sum(double x, unsigned &N_adds);

bool getArgument(double &x);
double getStep();

void process(double x, double h, unsigned N_adds);
bool query(double x);

void printHeadLine();
void printResults(double x, double resultLibrary, double resultOwnCalc, double difference, unsigned N_adds);

int main(){
	double x;
    unsigned N_adds = 0;         //the number of terms in a finite sum
    while (getArgument(x)) {
        process(x, getStep(), N_adds);
    }
	cout << "\n<<END OF WORK>>\n";
	return 0;
}

void process(double x, double tabulationStep, unsigned N_adds){
    double resultLibrary, resultOwnCalc, difference;
    do{
        printHeadLine();
        for (int numOfRow = 0; x < 1-MIN_TAB_STEP && numOfRow < 10; x += tabulationStep, ++numOfRow){
            resultLibrary = f_lib(x);
            resultOwnCalc = f_sum(x, N_adds);
            difference = fabs(resultLibrary - resultOwnCalc);
            printResults(x, resultLibrary, resultOwnCalc, difference, N_adds);
        }
    } while (!query(x));
}

bool getArgument(double &x){
	do{
		cout << "Enter real argument between -1 and 1: " << endl;
        cout << "Enter any non-numeric value for the end of the program." << endl;
		if (!(cin >> x))
            return false;
		if (x >= 1 || x <= -1) {
            cout << "<<WRONG ARGUMENT!>>" << endl;
            cout << "Try again!" << endl;
        }
        else
            break;
	} while (true);
	return true;
}

double getStep(){
    double tabulationStep;
    do{
        cout << "Enter step more than 0: ";
        cin.clear();
        cin.ignore();
    } while (!(cin >> tabulationStep));
    cout << endl;
    if (tabulationStep > 0) {
        return tabulationStep;
    }
    else {
        cout << "You have entered an incorrect value. Tabulation step will be set as default" << endl;
        return MIN_TAB_STEP;
    }
}

double f_lib(double x){
	return sqrt(1 + x);
}

void printHeadLine(){
	cout << "          x     f_lib(x)    f_sum(x)  difference    N_adds" << endl;
}

void printResults(double x, double f_lib, double f_sum, double difference, unsigned N_adds){
    cout << fixed;
	if (x < 0) {
		cout.precision(8);
		cout << x << "  ";
		cout.precision(9);
	}
	else {
		cout.precision(9);
		cout << x << "  ";
	}
	cout<< f_lib << ' ' << f_sum << ' ' << difference;
	if (N_adds<10)
        cout << "         " << N_adds << endl;
	else if (N_adds<100)
        cout << "        " << N_adds << endl;
	else if (N_adds<1000)
        cout << "       " << N_adds << endl;
	else if (N_adds<10000)
        cout << "      " << N_adds << endl;
	else
        cout << "     " << N_adds << endl;
}

double f_sum(double x, unsigned &N_adds){
    // N_adds = 0 at start
	double sum = 0.0;
	double a = -(x*x) / 8.0;
	if (x != 0){
		for (N_adds = 3; fabs(a) > EPS; ++N_adds){
			sum += a;
			a *= 1.5 * x / N_adds - x;
		}
        --N_adds;
	}
	else N_adds = 0;
	return 1 + x/2 + sum;
}

bool query(double x){
    if (x > 1-MIN_TAB_STEP) {
        cout << endl;
        return 1;
    }
    cout << "Would you like to continue?" << endl;
    cout << "Press 'q' to finish work with this x and h or press any another symbol for continue" << endl;
    char character;
    cin >> character;
    if (character == 'q' || character == 'Q')
        return 1;
    else
        return 0;
}