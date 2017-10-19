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

	printf("%d ", passedTests);
	printf("%d ", totalTests);
	printf("%f", time_taken);
	return 0;
}