package judge.Component;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorMessageParser {
    private static org.apache.log4j.Logger logger = Logger.getLogger(ErrorMessageParser.class);

    public static String parseErrorMessage(String errorCode, int lineWhereCustomCodeStarts) {

        // matches source code file name, e.g "source_code20180112_2334560.13831908083413236.c:"
        // and a "./chain: ..." line
        String errorMessageRegex = "(source[_A-z\\d.]*[\\-\\w.]:|.\\/.*(\\s|\\S)*)";

        logger.debug("Custom code starts: " + lineWhereCustomCodeStarts);

        try {
            String[] errorCode1 = errorCode.split("u001B\\[");
            String errorCode1Str = String.join(" ", errorCode1);
            String[] errorCode2 = errorCode1Str.split("u001B\\[K");
            String errorCode2Str = String.join(" ", errorCode2);
            String[] errorCodeWithoutFileNames = errorCode2Str.split(errorMessageRegex);
            String errorCodeWithoutFilenameStr = String.join(" ", errorCodeWithoutFileNames);
            String escapedErrorCode = escapeBashPrompt(errorCodeWithoutFilenameStr);
            String escapeCodeWithCorrectLines = extractAndChangeLineNumbers(escapedErrorCode, lineWhereCustomCodeStarts);

            return escapeCodeWithCorrectLines;
        } catch (Exception e) {
            logger.error("Parsing error message failed. ", e.getCause());
        }
        return "";
    }

    private static String escapeBashPrompt(String text) {
        String escapedText = text
                .replace("[01m","")
                .replace("[K","")
                .replace("[m","")
                .replace("[01;31m","")
                .replace("[01;32m","")
                .replace("[01;35m","");
        return escapedText;
    }

    private static String extractAndChangeLineNumbers(String text, int lineWhereCustomCodeStarts) {

        String lineNumberRegex = "(\\d+)(:)(\\d+)(:)";

        Pattern p = Pattern.compile(lineNumberRegex);
        Matcher m = p.matcher(text);

        String escapedErrorCodeSplited[] = text.split(lineNumberRegex);

        StringBuilder sb = new StringBuilder();
        int i = 0;

        while(m.find()) {
            sb.append(escapedErrorCodeSplited[i]);
            int lineNumber = Integer.parseInt(m.group(1))-lineWhereCustomCodeStarts+1;

            // The if-else statement is to handle a situation when NEW_PROBLEM is being processed
            // and error comes in data structures definition. Then the line number is a negative number,
            // as we don't count structures definition as custom code. Custom code starts with solution code.
            if(lineNumber>0) sb.append("line " + lineNumber + ":");
            else sb.append("In Structures definition:");
            i++;
        }
        sb.append(escapedErrorCodeSplited[i]);

        String escapeCodeWithCorrectLines = sb.toString();
        return escapeCodeWithCorrectLines;
    }
}
