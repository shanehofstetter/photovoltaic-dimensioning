package ch.shanehofstetter.pvdimension.net;



import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostRequest {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("");
    private static final String REQUEST_METHOD = "POST";

    /**
     * @param url           the url to send the post request to
     * @param urlParameters the parameters to post
     * @return the response to the post request
     * @throws Exception if problems with internet-connection, or url not reachable
     */
    public static String sendPOSTRequest(String url, String urlParameters) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod(REQUEST_METHOD);
        connection.setDoOutput(true);

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(urlParameters);
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        logger.debug("\nSending 'POST' request to URL : " + url);
        logger.debug("Post parameters : " + urlParameters);
        logger.debug("Response Code : " + responseCode);

        BufferedReader inputStream = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = inputStream.readLine()) != null) {
            response.append(inputLine).append("\n");
        }
        inputStream.close();

        logger.debug(response.toString());
        return response.toString();
    }

}