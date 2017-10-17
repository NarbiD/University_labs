#include <iostream>
#include <string>
#include <set>
#include <fstream>
#include <ctime>
#include <omp.h>
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

int main()
{
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
	books.push_back("texts/men.txt");*/
	
	double st_time = omp_get_wtime();

#pragma omp parallel for num_threads(4)
	for (int i = 0; i < books.size(); i++) {
		for (string word : split(books[i]))
#pragma omp critical 
		{
			dict.insert(word);
		}
	}
	/*for (string i : dict)
	cout << i << endl;*/
	cout << "Time: " << omp_get_wtime() - st_time << endl;
	system("pause");
}