#include <iostream>
#include <string>
#include <set>
#include <fstream>
#include <ctime>
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
	set<string> books;
	books.insert("texts/alice.txt");
	books.insert("texts/constitution.txt");
	books.insert("texts/tom.txt");
	books.insert("texts/wonderland.txt");
	books.insert("texts/ulysses.txt");
	books.insert("texts/hugo.txt");
	books.insert("texts/kjv.txt");
	books.insert("texts/dracula.txt");
	books.insert("texts/whittier.txt");
	books.insert("texts/men.txt");

	
	long st_time = clock();
	for (string book : books)
		for (string word : split(book))
			dict.insert(word);
	/*for (string i : dict)
		cout << i << endl;*/
	cout << "Time: " <<clock() - st_time << endl;
	system("pause");
}