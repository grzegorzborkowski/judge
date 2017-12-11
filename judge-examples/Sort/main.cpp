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
};

struct Output
{
    int array[SIZE_OF_ARRAY];
};

bool compare(Output a, Output b)
{
	for(int i=0;i<SIZE_OF_ARRAY;i++)
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
	    for(int i=0; i<SIZE_OF_ARRAY; i++) 
	        testData.testInput[j].array[i] = (rand()%(SIZE_OF_ARRAY*10))+1;     
	}
	return testData;
}

void cp_arr(int arr1[], int arr2[], int n)
{
	for(int i=0;i<n;i++)
		arr2[i] = arr1[i];
}

void swap(int* a, int* b)
{
    int t = *a;
    *a = *b;
    *b = t;
}

int partition (int arr[], int low, int high)
{
    int pivot = arr[high];
    int i = (low - 1);
 
    for (int j = low; j <= high- 1; j++)
    {
        if (arr[j] <= pivot)
        {
            i++;
            swap(&arr[i], &arr[j]);
        }
    }
    swap(&arr[i + 1], &arr[high]);
    return (i + 1);
}

void quickSort(int arr[], int low, int high)
{
    if (low < high)
    {
        int pi = partition(arr, low, high);
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

Output teachersFunction(Input input)
{
	Output output;
	cp_arr(input.array, output.array, SIZE_OF_ARRAY);
    quickSort(output.array, 0, SIZE_OF_ARRAY - 1);
    return output;
}

Output studentsFunction(Input input)
{
	Output output;
	cp_arr(input.array, output.array, SIZE_OF_ARRAY);
    quickSort(output.array, 0, SIZE_OF_ARRAY - 1);
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
