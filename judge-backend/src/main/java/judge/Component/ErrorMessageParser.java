package judge.Component;

public class ErrorMessageParser {

    public static String parseErrorMessage(String errorCode) {
        String errorMessageRegex = "(source[_A-z\\d.]*[\\-\\w.]*[:\\d]*|.\\/.*)";

        String[] errorCode1 = errorCode.split("u001B\\[");
        String errorCode1Str = String.join(" ", errorCode1);
        String[] errorCode2 = errorCode1Str.split("u001B\\[K");
        String errorCode2Str = String.join(" ", errorCode2);
        String[] errorCodeWithoutFileNames = errorCode2Str.split(errorMessageRegex);
        String errorCodeWithoutFilenameStr = String.join(" ", errorCodeWithoutFileNames);
        String errorCodeFinal = escapeBashPrompt(errorCodeWithoutFilenameStr);
        return errorCodeFinal;
    }

    private static String escapeBashPrompt(String text) {
        String escapedText = text
                .replace("[01m","")
                .replace("[K","")
                .replace("[m","")
                .replace("[01;31m","")
                .replace("[01;32m","")
                .replace("[01;35m","")
                .replace("^","\n");
        return escapedText;
    }
}
