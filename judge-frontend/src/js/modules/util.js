export const BACKEND_ADDRESS = "http://localhost:8080/";
export const JUDGE_ENDPOINT = "judge/submit";
export const ADD_STUDENT_ENDPOINT = "student/add";
export const SUBMISSIONS_FOR_USER_ENDPOINT = "submission/getAllForUser";
export const SOLUTION_ENDPOINT = "submission/getAllForProblem?id=";
export const PROBLEM_ENDPOINT = "problems/getAll";
export const PROBLEM_CREATOR_ENDPOINT = "problems/add";
export const PROBLEM_ID_ENDPOINT = "problems/getByID?id=";
export const PROBLEM_TEMPLATE_ENDPOINT = "problems/getTemplate";
export const PROBLEM_EDITOR_ENDPOINT = "problems/edit";
export const CATEGORY_ENDPOINT = "categories/getAll";
export const PROBLEMS_BY_CATEGORY_ENDPOINT = "problems/getByCategory?name=";

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
    if(code==RUN_SUCCESS_CODE) return "OK";
    if(code==RUN_FAILURE_CODE) return "Failed";
    if(code==TIMEOUT_CODE) return "Time limit exceeded";
    return "Unknown";
}
