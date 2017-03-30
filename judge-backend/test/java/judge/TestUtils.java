package judge;

public class TestUtils {

    public static final String HELLO_WORLD_SUBMISSION_CODE =
            "#include <stdio.h>\nint main (void)\n{\n   puts (\"Hello, World!\");\n   return 0;\n}";
    public static final String COMPILATION_ERROR_SUBMISSION_CODE =
            "#include <stdio.h>\nint main (void)\n{\n   puts (\"Hello, World!\")\n   return 0;\n}";
    public static final String TIMEOUT_SUBMISSION_CODE =
            "#include <stdio.h>\nint main (void)\n{\n   sleep(1000);\n   return 0;\n}";

    public static final String JUDGE_SERVER_URL = "http://localhost:8080/judge";

}
