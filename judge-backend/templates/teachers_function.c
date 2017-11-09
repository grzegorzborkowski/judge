#include<time.h>
#include<stdlib.h>

bool compare(Output a, Output b)
{
    // define your conditions here, e.g.
    // if(a.n == b.n) return true; return false;

	return true;
}

TestData generateTestInput()
{
	TestData testData;
	// specify number of tests to be executed
	testData.numberOfTests = 0;
	testData.testInput = new Input[testData.numberOfTests];

    int i;

	for(i=0;i<testData.numberOfTests;i++)
    	{
    	    Input input;
    	    // define your input here, e.g.
    	    // input.n = rand();

    	    testData.testInput[i] = input;
    	}

	return testData;
}

Output teachersFunction(Input input)
{
	Output output;
	return output;
}