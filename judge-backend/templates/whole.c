//------- generated part 1 ---------

#include <stdio.h>

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

//------- teacher's part 1 ---------
struct Input
{

};

struct Output
{

};

int compare(Output a, Output b)
{
	return 0;
}

//------- teacher's part 2 ---------

TestData generateTestInput()
{
	TestData testData;
	testData.numberOfTests = 0;
	testData.testInput = NULL;
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
	for(i=0;i<totalTests;i++)
	{
		if(compare(teachersFunction(testData.testInput[i]), studentsFunction(testData.testInput[i])))
			passedTests++;
	}
	fprintf(stderr, "%d %d", passedTests, totalTests);
	return 0;
}