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