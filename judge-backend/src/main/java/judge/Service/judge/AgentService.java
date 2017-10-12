package judge.Service.judge;

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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * AgentService is responsible for communication with external runner.
 */
@Service
@EnableTransactionManagement
class AgentService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(AgentService.class);

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
    Map<String, Integer> uploadFileToExamine(String filename) throws IOException {
        Map<String, Integer> result = new HashMap<>();

        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost("http://localhost:8123/submission");

        final File file = new File(filename);
        FileBody fileBody = new FileBody(file);

        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        reqEntity.addPart("file", fileBody);
        HttpEntity entity = reqEntity.build();
        httppost.setEntity(entity);

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
            result.put("compilationCode", compilationCode);
            result.put("runCode", runCode);
            result.put("testsTotal", testsTotal);
            result.put("testsPositive", testsPositive);

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
}
