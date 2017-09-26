package judge.Component;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class TokenValidator {
    private static org.apache.log4j.Logger logger = Logger.getLogger(TokenValidator.class);

    public Boolean validateToken (String token, String givenID) {
        return true;
//        logger.info("Token validation has started.");
//        RestTemplate restTemplate = new RestTemplate();
//        String verifiedID;
//        try {
//            ResponseEntity<JsonNode> response = restTemplate.getForEntity(
//                    "https://graph.facebook.com/me?access_token=" + token,
//                    JsonNode.class);
//            verifiedID = response.getBody().get("id").asText();
//        } catch (RestClientException e) {
//            logger.warn("Verification of the access token and facebookID has failed.", e);
//            return false;
//        }
//        return givenID.equals(verifiedID);
    }
}
