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
    int x; //majątek początkowy
    int l; //liczba lat
    double p; //wzrost w skali roku, np. 1,05
    int n; //liczba kapitalizacji w ciągu roku
};

struct Output
{
    double x; //łączny majątek po l latach
};
#include<time.h>
#include<stdlib.h>
#include<math.h>

bool compare(Output a, Output b)
{
    return a.x == b.x;
}

TestData generateTestInput()
{
	TestData testData;
	testData.numberOfTests = 1000;
	testData.testInput = new Input[testData.numberOfTests];
	srand((unsigned)time(0));
    int i;

	for(i=0;i<testData.numberOfTests;i++)
    	{
    	    Input input;
    	    input.x = 1000 + rand()%10000;
    	    input.l = 3 + rand()%20;
    	    input.p = 1 + (rand()%100)/100;
    	    input.n = 1 + rand()%10;
    	    
    	    testData.testInput[i] = input;
    	}

	return testData;
}

Output teachersFunction(Input input)
{
	Output output;
	output.x = input.x * pow((1 + (input.p-1) / input.n), (input.n * input.l));
	return output;
}
Output studentsFunction(Input input)
{
	Output output;
	double p = input.p - 1;
	output.x = input.x;
	for(int i=0;i<input.l*input.n;i++)
	{
	    output.x *= 1 + (p/input.n);
	}
	return output;
}
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
