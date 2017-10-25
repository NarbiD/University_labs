#include <iostream>
#include <string>
#include <set>
#include <fstream>
#include <ctime>
#include "mpi.h"
#include <vector>
using namespace std;

void printResults(int numOfWords, int numOfWordsWithoutRepetitions, string bookName) {
	cout << "Nomber of words in " << bookName << " = " << numOfWords << endl;
	cout << "Nomber of words without in repetitions" << bookName << " = " << numOfWordsWithoutRepetitions << endl;
}

set<string> split(string book)
{
	string word = "";
	unsigned count = 0;
	set<string> words;
	ifstream in(book);
	while (!in.eof())
	{
		string segment;
		in >> segment;
		for (unsigned i = 0; i < segment.size(); i++)
		{
			if (isalpha(segment[i]))
				word += segment[i];
			else
				break;
		}
		if (word.size() > 0 && word.size() < 30) {
			words.insert(word);
			count++;
		}
		word = "";
	}
	in.close();
	printResults(count, words.size(), book);
	return words;
}


int main(int argc, char* argv[])
{
	int rank;
	int size;
	set<string> dict;
	vector<string> books;
	books.push_back("texts/alice.txt");
	books.push_back("texts/constitution.txt");
	books.push_back("texts/tom.txt");
	books.push_back("texts/wonderland.txt");
	books.push_back("texts/ulysses.txt");
	books.push_back("texts/hugo.txt");
	books.push_back("texts/kjv.txt");
	books.push_back("texts/dracula.txt");
	books.push_back("texts/whittier.txt");
	books.push_back("texts/men.txt");
	/*books.push_back("texts/alice.txt");
	books.push_back("texts/constitution.txt");
	books.push_back("texts/tom.txt");
	books.push_back("texts/wonderland.txt");
	books.push_back("texts/ulysses.txt");
	books.push_back("texts/hugo.txt");
	books.push_back("texts/kjv.txt");
	books.push_back("texts/dracula.txt");
	books.push_back("texts/whittier.txt");
	books.push_back("texts/men.txt");
*/
	
	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	double st_time = MPI_Wtime();
	for (int i = 0; i < books.size(); i++) {
		for (string word : split(books[i]))
			dict.insert(word);
	}


	MPI_Finalize();
	/*for (string i : dict)
	cout << i << endl;*/
	cout << "Time: " << MPI_Wtime() - st_time << "ms" << endl;
	system("pause");
}