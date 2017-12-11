#include <stdio.h>
#include <time.h>


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
struct Input
{
	int n;
};

struct Output
{
	int n;
};

bool compare(Output a, Output b)
{
	return a.n == b.n;
}
#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <stdbool.h>
#define NUMBER_OF_TESTS 10

TestData generateTestInput()
{
	TestData testData;
	testData.numberOfTests = NUMBER_OF_TESTS;
	srand(time(NULL));
	testData.testInput = new Input[NUMBER_OF_TESTS];
	for(int i=0; i<NUMBER_OF_TESTS; i++)
		testData.testInput[i].n = rand()%999 + 1000;
	return testData;
}

Output teachersFunction(Input input)
{
	int n = input.n;
	int sum=0;
	do
	{
	sum = sum + n%10;
	n = n/10;
	}
	while(n!=0);
	Output output;
	output.n = sum;
	return output;
}
Output studentsFunction(Input input){	Output output;	return output;}
int main()
{
	TestData testData = generateTestInput();
	int totalTests = testData.numberOfTests;
	Input inputArr[totalTests];
	for(int i=0; i<totalTests; i++) {
	    inputArr[i] = testData.testInput[i];
	}
	int passedTests = 0;
	int i;
	clock_t t;
    t = clock();

	for(i=0;i<totalTests;i++)
	{
		if(compare(teachersFunction(inputArr[i]), studentsFunction(inputArr[i])))
			passedTests++;
	}

    t = clock() - t;
    float time_taken = ((float)t)/CLOCKS_PER_SEC; // in seconds

	printf("%d %d ", passedTests, totalTests);
	printf("%f", time_taken);
	return 0;
}
