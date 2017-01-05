#include <stdio.h>
#include <Math.h>

#define SQRT2 1.41421356237

double getCont();
void getPoint(double *x, double *y);
int wherePoint(double x, double y, double contour);
void outResults(double x, double y, double a, int whereP, double distPC);
double distPointCont(double contour, double x, double y, int whereP);
void outPoint(double x, double y);
void outCont(double contour);

int main() {
    double contour = getCont();
    if (contour <= 0) {
        printf("<<<WRONG PARAMETER>>>\n");
    }
    else {
        double x, y;
        getPoint(&x, &y);
        int whereP = wherePoint(x, y, contour);
        outResults(x, y, contour, whereP, distPointCont(contour, x, y, whereP));
    }
    return 0;
}

double getCont() {
    double contour;
    printf("Enter the positive real parameter: \n a = ");
    scanf("%lf", &contour);
    printf("\n");
    return contour;
}

void getPoint(double *x, double *y) {
    printf("Enter the positive real coordinates of the point: \n x = ");
    scanf("%lf", x);
    printf("\n y = ");
    scanf("%lf", y);
    printf("\n");
}

int wherePoint(double x, double y, double contour) {
    if (y < 0) {
        x = -x;
        y = -y;
    }
    if (x >= 0) {
        if (x > contour || y > contour)
            return 1;
        else if (x < contour && y < contour)
            return -1;
        else
            return 0;
    }
    else {
        if (y > x + contour)
            return 1;
        else if (y < x + contour)
            return -1;
        else
            return 0;
    }
}
void outResults(double x, double y, double contour, int whereP, double distPC) {
    outPoint(x, y);
    switch (whereP) {
        case 1:
            printf("  OUT_C  ");
            break;
        case 0:
            printf("  ON_C  ");
            break;
        case -1:
            printf("  IN_C  ");
            break;
        default:
            break;
    }
    outCont(contour);
    printf("\nDISTANCE  %f \n", distPC);
}
double distPointCont(double contour, double x, double y, int whereP) {
    if (y < 0) {
        x = -x;
        y = -y;
    }
    if (whereP == 1) {
        if (x >= 0){
            if (x > contour && y < contour)
                return x - contour;
            if (y > contour && x < contour)
                return y - contour;
            else
                return sqrt((x - contour)*(x - contour) + (y - contour)*(y - contour));
        }
        else {
            if (y > contour - x)
                return sqrt(x*x + (y - contour)*(y - contour));
            if (y < -contour - x)
                return sqrt((x + contour)*(x + contour) + y*y);
            else
                return fabs(x - y + contour) / SQRT2;
        }
    }
    else if (whereP == -1) {
        if (y < x) {
            double temp = x;
            x = y;
            y = temp;
        }

        double distanceToNearestAngle = fabs(x - y + contour) / SQRT2;
        if((contour - x <= contour - y) && (contour - x <= distanceToNearestAngle))
            return contour - x;
        else if (contour - y <= distanceToNearestAngle)
            return contour - y;
        else
            return distanceToNearestAngle;
    }
    else
        return 0;
}
void outPoint(double x, double y) {
    printf("(%f; %f)", x, y);

}
void outCont(double contour) {
    printf("[%f]", contour);
}