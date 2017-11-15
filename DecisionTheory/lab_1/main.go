package main

import "fmt"

var names = []string{"S", "J", "M"}
const DIMENSION = 3

func main() {
	var (
		An = calc(scanMatrix())
		Acn = calc(scanMatrix())
		Aon = calc(scanMatrix())
		Apn = calc(scanMatrix())
	)

	for i, name := range names {
		result := An[0]*Acn[i] + An[1]*Aon[i] + An[2]*Apn[i]
		fmt.Printf(name + " = %.3f\n", result)
	}
}

func scanMatrix() [DIMENSION][DIMENSION]float64 {
	var matrix [DIMENSION][DIMENSION]float64
	for i := 0; i < DIMENSION; i++ {
		for j := 0; j < DIMENSION; j++ {
			fmt.Scan(&matrix[i][j])
		}
	}
	return matrix
}

func calc(matrix [DIMENSION][DIMENSION]float64) [DIMENSION]float64 {
	var resultMatrix [DIMENSION][DIMENSION]float64
	var sums = sumOfRowValues(matrix)
	for i := 0; i < DIMENSION; i++ {
		for j := 0; j < DIMENSION; j++ {
			resultMatrix[i][j] = matrix[i][j]/sums[j]
		}
	}
	return calcW(resultMatrix)
}

func sumOfRowValues(matrix [DIMENSION][DIMENSION]float64) [DIMENSION]float64 {
	var sums [DIMENSION]float64
	for j := 0; j < DIMENSION; j++ {
		for i := 0; i < DIMENSION; i++ {
			sums[j] += matrix[i][j]
		}
	}
	return sums
}

func calcW(matrix [DIMENSION][DIMENSION]float64) [DIMENSION]float64 {
	var w [DIMENSION]float64
	for i := 0; i < DIMENSION; i++ {
		for j := 0; j < DIMENSION; j++ {
			w[i] += matrix[i][j]
		}
		w[i] /= DIMENSION
	}
	return w
}
