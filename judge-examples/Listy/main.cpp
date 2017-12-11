#include <stdio.h>
#include <time.h>
#include <cstdlib> 
#include <ctime> 
#include <string>

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


#define NO_TESTS 100
#define SIZE_OF_LIST 100

typedef struct Leaf
{
	int val;
	Leaf* nextLeaf;
} Leaf;

struct Input
{
    Leaf* head;
};

struct Output
{
    Leaf* evenHead;
    Leaf* oddHead;
};

bool one_is_null(Leaf* a, Leaf* b)
{
	if(a == NULL && b != NULL)
		return true;
	if(a != NULL && b == NULL)
		return true;
	return false;
}

bool compare(Output a, Output b)
{
	Leaf* a_odd = a.oddHead;
	Leaf* a_even = a.evenHead;
	Leaf* b_odd = b.oddHead;
	Leaf* b_even = b.evenHead;

	if(one_is_null(a_odd,b_odd))
		return false;
	while(a_odd->nextLeaf != NULL and b_odd->nextLeaf != NULL)
	{
		if(a_odd->val != b_odd->val)
			return false;
		a_odd = a_odd->nextLeaf;
		b_odd = b_odd->nextLeaf;
	}
	if(one_is_null(a_odd,b_odd))
		return false;

	if(one_is_null(a_even,b_even))
		return false;
	while(a_even->nextLeaf != NULL and b_even->nextLeaf != NULL)
	{
		if(a_even->val != b_even->val)
			return false;
		a_even = a_even->nextLeaf;
		b_even = b_even->nextLeaf;
	}
	if(one_is_null(a_even,b_even))
		return false;
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
		Leaf* current = new Leaf;
		current->val = 0;
		Leaf* head = current;

	    for(int i=0; i<SIZE_OF_LIST-1; i++) 
        {
        	Leaf* newLeaf = new Leaf;
        	newLeaf->val = rand()%100;
        	current->nextLeaf = newLeaf;
        	current = newLeaf;
        }
        current->nextLeaf = NULL;
        testData.testInput[j].head = head;
	}
	return testData;
}

Output teachersFunction(Input input)
{
	Output output;
	Leaf* current = input.head;
	Leaf* oddHead = new Leaf;
	Leaf* evenHead = new Leaf;
	Leaf* currEven = evenHead;
	Leaf* currOdd = oddHead;
	while(current != NULL)
	{
		Leaf* newLeaf = new Leaf;
		newLeaf->val = current->val;
		if(current->val % 2 == 1)
		{
			currOdd->nextLeaf = newLeaf;
			currOdd = newLeaf;
		}
		else
		{
			currEven->nextLeaf = newLeaf;
			currEven = newLeaf;
		}
		current = current->nextLeaf;
	}
	output.evenHead = evenHead->nextLeaf;
	output.oddHead = oddHead->nextLeaf;

	return output;
}

Output studentsFunction(Input input)
{
	Output output;
	Leaf* current = input.head;
	Leaf* oddHead = new Leaf;
	Leaf* evenHead = new Leaf;
	Leaf* currEven = evenHead;
	Leaf* currOdd = oddHead;
	while(current != NULL)
	{
		Leaf* newLeaf = new Leaf;
		newLeaf->val = current->val;
		if(current->val % 2 == 1)
		{
			currOdd->nextLeaf = newLeaf;
			currOdd = newLeaf;
		}
		else
		{
			currEven->nextLeaf = newLeaf;
			currEven = newLeaf;
		}
		current = current->nextLeaf;
	}
	output.evenHead = evenHead->nextLeaf;
	output.oddHead = oddHead->nextLeaf;

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
