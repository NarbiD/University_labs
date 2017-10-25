#include <iostream>
#include <string>
#include <set>
using namespace std;

set<string> split(string text)
{
	set<string> words;
	string delim(" ");
	size_t prev = 0;
	size_t next;
	size_t delta = delim.length();
	string word;

	while ((next = text.find(delim, prev)) != string::npos)
	{
		const string segment = text.substr(prev, next - prev);

		for (unsigned i = 0; i < segment.size(); i++)
		{
			if (isalpha(segment[i]))
				word += segment[i];
			else
				break;
		}

		if (word.size() > 0 && word.size() < 30)
			words.insert(word);

		prev = next + delta;
		word = "";
	}
	for (char c : text.substr(prev))
	{
		if (isalpha(c))
			word += c;
	}
	words.insert(word);
	return words;
}

int main()
{
	set<string> dict = split("Hello, world! I am Denis. I am 22 years old.");

	for (string i : dict)
		cout << i << endl;

	system("pause");
}