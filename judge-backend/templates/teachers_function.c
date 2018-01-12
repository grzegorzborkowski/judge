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

	for(int i=0;i<testData.numberOfTests;i++)
    	{
    	    Input in;
    	    // define your input here, e.g.
    	    // in.n = rand();

    	    testData.testInput[i] = in;
    	}

	return testData;
}

Output teachersFunction(Input in)
{
	Output out;
	return out;
}