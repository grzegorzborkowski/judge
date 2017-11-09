//------- generated part 1 ---------

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

//------- teacher's part 1 ---------
struct Input
{

};

struct Output
{

};

boolean compare(Output a, Output b)
{
	return true;
}

//------- teacher's part 2 ---------

TestData generateTestInput()
{
	TestData testData;
	testData.numberOfTests = 0;
	testData.testInput = new Input[testData.numberOfTests];
	return testData;
}

Output teachersFunction(Input input)
{
	Output Output;
	return Output;
}

//-------- student's part ----------

Output studentsFunction(Input input)
{
	Output Output;
	return Output;
}

//------- generated part 2 ---------
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