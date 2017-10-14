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

	printf("%d ", passedTests);
	printf("%d", totalTests);
	return 0;
}