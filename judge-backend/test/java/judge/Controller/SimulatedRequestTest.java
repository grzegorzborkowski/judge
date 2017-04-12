package judge.Controller;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static judge.TestUtils.JUDGE_SERVER_URL;
import static judge.Utils.TIMEOUT_VALUE_SECONDS;

public class SimulatedRequestTest {
    private static org.apache.log4j.Logger logger = Logger.getLogger(SimulatedRequestTest.class);

    private String HELLO_WORLD_REQUEST_DATA =
            "{\"code\":\"\\n#include <stdio.h>\\nint main (void)\\n{\\n   " +
                    "puts (\\\"Helo, World!\\\");\\n\\n   return 0;\\n\\n}\\n\"}";
    private String COMPILATION_ERROR_REQUEST_DATA =
            "{\"code\":\"\\n#include <stdio.h>\\nint main (void)\\n{\\n   " +
                    "puts (\\\"Helo, World!\\\")\\n\\n   return 0;\\n\\n}\\n\"}";
    private String TIMEOUT_REQUEST_DATA =
            "{\"code\":\"\\n#include <stdio.h>\\nint main (void)\\n{\\n   " +
                    "sleep(100);\\n\\n   return 0;\\n\\n}\\n\"}";

    private String SUCCESS_RESPONSE = "{\"compilationCode\":0,\"runCode\":0}";
    private String COMPILATION_ERROR_RESPONSE = "{\"compilationCode\":-1,\"runCode\":-1}";
    private String TIMEOUT_RESPONSE = "{\"compilationCode\":0,\"runCode\":2}";

    @Test
    public void testSimulatedRequestSuccess() {
        logger.info("test success");
        String requestData = HELLO_WORLD_REQUEST_DATA;
        String pureResponse = sendRequest(requestData);
        Assert.assertEquals(SUCCESS_RESPONSE, pureResponse);
    }
    @Test
    public void testSimulatedRequestFailure() {
        logger.info("test failure");
        String requestData = COMPILATION_ERROR_REQUEST_DATA;
        String pureResponse = sendRequest(requestData);
        Assert.assertEquals(COMPILATION_ERROR_RESPONSE, pureResponse);
    }
    @Test
    public void testSimulatedRequestTimeout() {
        logger.info("test timeout 1");
        String requestData = TIMEOUT_REQUEST_DATA;
        String pureResponse = sendRequest(requestData);
        Assert.assertEquals(TIMEOUT_RESPONSE, pureResponse);
    }
    @Test
    public void testSimulatedRequestTimeout2() {
        logger.info("test timeout 2");
        String requestData = TIMEOUT_REQUEST_DATA;
        String pureResponse = sendRequest(requestData);
        Assert.assertEquals(TIMEOUT_RESPONSE, pureResponse);
    }

    public String sendRequest(String requestData) {
        try {
            String url = JUDGE_SERVER_URL;
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("data", "text/plain");
            con.setDoOutput(true);
            con.setDoInput(true);
            int estimatedReadTimeout = estimateResponseTimeMilliseconds();
            con.setReadTimeout(estimatedReadTimeout);

            // Send response
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(requestData);
            wr.flush();
            wr.close();

            // Get response
            InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuffer response = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();

            return response.toString();
        } catch (java.net.SocketTimeoutException e) {
            return e.toString();
        } catch (java.io.IOException e) {
            return e.toString();
        }
    }

    /**
     * Response time is estimated to be less than program processing time limit plus 5 seconds.
     * After this time test fails.
     */
    private int estimateResponseTimeMilliseconds() {
        return TIMEOUT_VALUE_SECONDS*1000+5000;
    }
}
