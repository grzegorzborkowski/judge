package judge.Service.judge;

import judge.Component.JudgeResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AgentService is responsible for communication with external runner.
 */
@Service
@EnableTransactionManagement
class AgentService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(AgentService.class);

    @Value("${connection.externalrunner.url}")
    private String externalRunnerUrl;

    /**
     * Uploads source code file to the external server (external runner).
     * Handles server response (with examination results!) and creates a result map based on the response.
     * Throws an exception when external runner is unreachable (refused connection).
     *
     * @param filename  path to the .c file with generated source code, this file will be uploaded to the runner
     * @return HashMap <result type, result value> with compilation code and run code
     *
     * TODO: Change communication model - enable reading results from response body. Now they are in headers.
     */
    @Async
    JudgeResult uploadFileToExamine(String filename) throws IOException {


        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost(externalRunnerUrl);
        JudgeResult result = null;

        final File file = new File(filename);
        FileBody fileBody = new FileBody(file);

        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        reqEntity.addPart("file", fileBody);
        HttpEntity entity = reqEntity.build();
        httppost.setEntity(entity);
	System.out.println(externalRunnerUrl);

        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        try {
            logger.debug(response.getStatusLine());
            HttpEntity resEntity = response.getEntity();
            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);

            JSONParser parser = new JSONParser();
            JSONObject bodyJson = (JSONObject) parser.parse(body);
            logger.info("Response from external runner: \n" + bodyJson.toString());

            int compilationCode = Integer.parseInt(bodyJson.get("CompilationCode").toString());
            int runCode = Integer.parseInt(bodyJson.get("RunCode").toString());
            int testsTotal = Integer.parseInt(bodyJson.get("TestsTotal").toString());
            int testsPositive = Integer.parseInt(bodyJson.get("TestsPositive").toString());
            float timeTaken = Float.parseFloat(bodyJson.get("TimeTaken").toString());
            String errorCode = prepareErrorMessage(bodyJson.get("ErrorCode").toString());

            result = new JudgeResult(compilationCode, runCode, testsPositive, testsTotal, timeTaken, errorCode);

            if (resEntity != null) {
                logger.info("Response content length: " + resEntity.getContentLength());
            }
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            logger.error("Error while processing server response. Submission result may by wrong.");
            logger.error(e.getMessage());
        } finally {
            EntityUtils.consume(entity);
        }
        return result;
    }

    String prepareErrorMessage(String errorCode) {
        String errorMessageRegex = "(source[_A-z\\d.]*[:\\d]*|.\\/.*)";

        String[] errorCode1 = errorCode.split("u001B\\[");
        String errorCode1Str = String.join(" ", errorCode1);
        String[] errorCode2 = errorCode1Str.split("u001B\\[K");
        String errorCode2Str = String.join(" ", errorCode2);
        String[] errorCodeWithoutFileNames = errorCode2Str.split(errorMessageRegex);
        String errorCodeFinal = String.join(" ", errorCodeWithoutFileNames);
        errorCodeFinal = errorCodeFinal.replace('^', '\n');

        return errorCodeFinal;
    }
}
