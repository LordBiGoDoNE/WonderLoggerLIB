package wonderlogger.utils;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import wonderlogger.model.vo.PostResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpUtils {

    public static PostResponse sendPost(String pStringURL, String pJson) {
        HttpPost httpPost = new HttpPost(pStringURL);

        try {
            httpPost.setEntity(new StringEntity(pJson));
        } catch (UnsupportedEncodingException e) {
            return new PostResponse(false, e);
        }

        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        return sendPost(httpPost);
    }

    public static PostResponse sendPost(HttpPost pHttpPost) {
        try (CloseableHttpResponse response = HttpClients.createDefault().execute(pHttpPost)) {
            boolean sucess = response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            return new PostResponse(sucess, responseBody);
        } catch (IOException ioEX) {
            return new PostResponse(false, ioEX);
        }
    }

}
