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