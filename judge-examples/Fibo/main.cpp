#include <stdio.h>
#include <time.h>
#include <cstdlib> 
#include <ctime> 

#define NO_TESTS 10

typedef struct Input Input;

typedef struct Output Output;

typedef struct TestData
{
	Input* testInput;
	int numberOfTests;
} TestData;

int compare(Output a, Output b);
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

int compare(Output a, Output b)
{
	return a.n == b.n;
}

TestData generateTestInput()
{
	TestData testData;
	testData.numberOfTests = NO_TESTS;
	testData.testInput = new Input[NO_TESTS];
	int i;
	for (i=0; i<NO_TESTS; i++)
	{
	    testData.testInput[i].n = i;
	}
	return testData;
}

Output teachersFunction(Input input)
{
	Output output;
	if (input.n == 0) 
	{
        output.n = 0;
        return output;
	} 
	int a = 0;
    int b = 1;
    while (input.n-- > 1) 
    {
    	int t = a;
        a = b;
        b += t;
    }
    output.n = b;
    return output;
}

Output studentsFunction(Input input)
{
    Output output;
	if (input.n == 0) 
	{
        output.n = 0;
        return output;
    } 
 	if (input.n == 1) 
 	{
    	output.n = 1;
	    return output;
 	}
	Input a,b;
	a.n = input.n - 1;
	b.n = input.n - 2;
	output.n = studentsFunction(a).n + studentsFunction(b).n;
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