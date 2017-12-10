#include <stdio.h>
#include <time.h>
#include <cstdlib> 
#include <ctime> 

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


#define NO_TESTS 20
#define SIZE_OF_ARRAY 100

struct Input
{
    int array[SIZE_OF_ARRAY];
    int size;
};

struct Output
{
    int array[SIZE_OF_ARRAY];
    int size;
};

bool compare(Output a, Output b)
{
	for(int i=0;i<a.size;i++)
	{
	    if (a.array[i] != b.array[i])
	        return false;
	}
	return true;
}

TestData generateTestInput()
{
	TestData testData;
	testData.numberOfTests = NO_TESTS;
	testData.testInput = new Input[NO_TESTS];
	srand((unsigned)time(0)); 
	for(int j=0;j<NO_TESTS;j++)
	{
		testData.testInput[j].size = SIZE_OF_ARRAY;

	    for(int i=0; i<testData.testInput[j].size; i++) 
	        testData.testInput[j].array[i] = (rand()%(SIZE_OF_ARRAY*10))+1;  
        
	}
	return testData;
}

void cp_arr(int arr1[], int arr2[], int n)
{
	for(int i=0;i<n;i++)
		arr2[i] = arr1[i];
}

void swap(int *xp, int *yp)
{
    int temp = *xp;
    *xp = *yp;
    *yp = temp;
}
 
void bubbleSort(int arr[], int n)
{
   int i, j;
   for (i = 0; i < n-1; i++)      
 
       for (j = 0; j < n-i-1; j++) 
           if (arr[j] > arr[j+1])
              swap(&arr[j], &arr[j+1]);
}

Output teachersFunction(Input input)
{
	Output output;
	output.size = input.size;
	bubbleSort(input.array, input.size);
	cp_arr(input.array, output.array, input.size);
	return output;
}

Output studentsFunction(Input input)
{
	Output output;
	output.size = input.size;
	bubbleSort(input.array, input.size);
	cp_arr(input.array, output.array, input.size);
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
