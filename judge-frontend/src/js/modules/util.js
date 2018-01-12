import React from 'react'

//xport const BACKEND_ADDRESS = "http://ec2-34-210-35-124.us-west-2.compute.amazonaws.com:8080/";
export const BACKEND_ADDRESS = "http://localhost:8080/";
export const JUDGE_ENDPOINT = "judge/submit";
export const ACCOUNT_ENDPOINT = "user/getInfo";
export const ADD_USER_ENDPOINT = "user/add";
export const ADD_STUDENT_ENDPOINT = "user/addOneStudent";
export const ADD_STUDENTS_ENDPOINT = "user/addMultipleStudents";
export const ADD_TEACHER_ENDPOINT = "user/addTeacher";
export const SUBMISSIONS_FOR_USER_ENDPOINT = "submission/getAllForUser";
export const ALL_SUBMISSIONS = "submission/getAll";
// now unused
export const SOLUTION_ENDPOINT = "submission/getAllForProblem?id=";
export const SUBMISSION_PER_ID_ENDPOINT = "submission/getByID?id=";
export const SUBMISSIONS_FOR_PROBLEM_FOR_USER_ENDPOINT = "submission/getForProblemForUser?problemId="
export const PROBLEM_ENDPOINT = "problems/getAll";
export const PROBLEM_CREATOR_ENDPOINT = "problems/add";
export const PROBLEM_ID_ENDPOINT = "problems/getByID?id=";
export const PROBLEM_TEMPLATE_ENDPOINT = "problems/getTemplate";
export const PROBLEM_EDITOR_ENDPOINT = "problems/edit";
export const CATEGORY_ENDPOINT = "categories/getAll";
export const CATEGORY_ADD_ENDPOINT = "categories/add";
export const CATEGORY_REMOVE_ENDPOINT = "categories/remove?id=";
export const PROBLEMS_BY_CATEGORY_ENDPOINT = "problems/getByCategory?name=";
export const PROBLEM_REMOVE_ENDPOINT = "problems/remove?id=";
export const PASSWORD_CHANGE_ENDPOINT = "user/changePassword";
export const CHANGE_OTHER_USER_PASSWORD_ENDPOINT = "user/otherUserPassword";

export const COMPILATION_SUCCESS_CODE = 0;
export const COMPILATION_FAILURE_CODE = 1;
export const RUN_SUCCESS_CODE = 0;
export const RUN_FAILURE_CODE = 1;
export const TIMEOUT_CODE = 2;
export const PROCESSING_ERROR_CODE = -1;

export function getCompilationStatus(code) {
    if(code==COMPILATION_SUCCESS_CODE) return "OK";
    if(code==COMPILATION_FAILURE_CODE) return "Failed";
    return "Unknown";
}

export function getRunStatus(code) {
    if(code==RUN_SUCCESS_CODE) return "Within the time limit";
    if(code==RUN_FAILURE_CODE) return "Error while executing";
    if(code==TIMEOUT_CODE) return "Time limit exceeded";
    return "Unknown";
}

export function getExecutionStatus(compilationCode, runCode) {
    if(compilationCode==COMPILATION_FAILURE_CODE) return "Compilation failed";
    if(compilationCode==COMPILATION_SUCCESS_CODE && runCode==RUN_SUCCESS_CODE) return "OK";
    if(compilationCode==COMPILATION_SUCCESS_CODE && runCode==TIMEOUT_CODE) return "Time limit exceeded";
    if(compilationCode==COMPILATION_SUCCESS_CODE && runCode==RUN_FAILURE_CODE) return "Error while executing";
    return "Unknown";
}

export function getResultIcon(runCode, testsPositive, testsTotal) {
  if (runCode==TIMEOUT_CODE)
    return (<span class="glyphicon glyphicon-time"></span>)
  else if (runCode==RUN_FAILURE_CODE || testsPositive!=testsTotal)
    return (<span class="glyphicon glyphicon-remove"></span>)
  else if (testsPositive==testsTotal && testsTotal>0)
    return (<span class="glyphicon glyphicon-ok"></span>)
  else
    return (<span class="glyphicon glyphicon-question-sign"></span>)
}

/* Styles */
export const ACE_EDITOR_FONT_SIZE = 15;
