package ch.shanehofstetter.pvdimension.net;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class HttpGetRequest {
    static final Logger logger = LogManager.getLogger();
    private static final String REQUEST_METHOD = "GET";
    private static final String USER_AGENT = "Mozilla/5.0";

    /**
     * @param url URL to send the GET-Request to
     * @return The Response to the GET-Request
     * @throws Exception if problem with internet-connection, or url not reachable
     */
    public static String sendGETRequest(String url) throws Exception {

        URL obj = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

        connection.setRequestMethod(REQUEST_METHOD);
        connection.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = connection.getResponseCode();
        logger.debug("\nSending 'GET' request to URL : " + url);
        logger.debug("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine).append("\n");
        }
        in.close();

        logger.debug(response.toString());
        return response.toString();
    }

}
