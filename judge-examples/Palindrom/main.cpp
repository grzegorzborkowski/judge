#include <stdio.h>
#include <time.h>
#include <cstdlib> 
#include <ctime> 
#include <string>
#include <algorithm>
#include <iostream>


typedef struct Input Input;

typedef struct Output Output;

typedef struct TestData
{
	Input* testInput;
	int numberOfTests;
} TestData;

bool compare(Output a, Output b);
TestData generateTestInput();
Output teachersFunction(Input input);
Output studentsFunction(Input input);


#define NO_TESTS 20

struct Input
{
    std::string text;
};

struct Output
{
    bool isPalindrom;
};

bool compare(Output a, Output b)
{
	return !(a.isPalindrom ^ b.isPalindrom);//not xor
}

std::string RandomString(unsigned int len)
{
   srand(time(0));
   std::string str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   std::string newstr;
   int pos;
   while(newstr.size() != len) {
    pos = ((rand() % (str.size() - 1)));
    newstr += str.substr(pos,1);
   }
   return newstr;
}


TestData generateTestInput()
{
	TestData testData;
	testData.numberOfTests = NO_TESTS;
	testData.testInput = new Input[NO_TESTS];
	srand((unsigned)time(0)); 
	for(int j=0;j<NO_TESTS-3;j++)
	{
		testData.testInput[j].text = rand()%15;
	}
	testData.testInput[NO_TESTS-3].text = "kajak";
	testData.testInput[NO_TESTS-2].text = "radar";
	testData.testInput[NO_TESTS-1].text = "racecar";
	return testData;
}

Output teachersFunction(Input input)
{
	Output output;
	std::string s =input.text;
	if( equal(s.begin(), s.begin() + s.size()/2, s.rbegin()) )
        output.isPalindrom = true;
    else
        output.isPalindrom = false;
	return output;
}

Output studentsFunction(Input input)
{
	Output output;
	if (input.text == std::string(input.text.rbegin(), input.text.rend()))
    	output.isPalindrom = true;
	else
		output.isPalindrom = false;
	return output;
}

int main()
{
	TestData testData = generateTestInput();
	int totalTests = testData.numberOfTests;
	int passedTests = 0;
	int i;
	clock_t t;
    t = clock();

	for(i=0;i<totalTests;i++)
	{
		if(compare(teachersFunction(testData.testInput[i]), studentsFunction(testData.testInput[i])))
			passedTests++;
	}

    t = clock() - t;
    float time_taken = ((float)t)/CLOCKS_PER_SEC; // in seconds

	printf("%d %d ", passedTests, totalTests);
	printf("%f", time_taken);
	return 0;
}