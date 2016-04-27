package cmu.procrastination.focuscoding.ws.remote;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtils {

    private CloseableHttpClient client = HttpClients.createDefault();

    /**
     * HTTP GET
     * 
     * @param url
     * @param headers
     * @return CloseableHttpResponse
     */
    public CloseableHttpResponse get(String url, Map<String, String> headers) {
        if (url == null) {
            return null;
        }
        HttpGet get = new HttpGet(url);
        addHeaders(get, headers);
        return visit(get);
    }

    /**
     * HTTP GET Without Handling HTTP redirects automatically
     * 
     * @param url
     * @param headers
     * @return CloseableHttpResponse
     */
    public CloseableHttpResponse getWithoutAutoRedirect(String url,
            Map<String, String> headers) {
        if (url == null) {
            return null;
        }
        HttpGet get = new HttpGet(url);
        addHeaders(get, headers);
        CloseableHttpResponse response = null;
        try {
            client = HttpClients.custom().disableRedirectHandling().build();
            response = visit(get);
            RedirectStrategy rs = DefaultRedirectStrategy.INSTANCE;
            client = HttpClients.custom().setRedirectStrategy(rs).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    /**
     * HTTP POST
     * 
     * @param url
     * @param headers
     * @param params
     * @return CloseableHttpResponse
     */
    public CloseableHttpResponse post(String url, Map<String, String> headers,
            Map<String, String> params) {
        if (url == null) {
            return null;
        }
        HttpPost post = preparePostMethod(url, params);
        addHeaders(post, headers);
        return visit(post);
    }

    /**
     * 抓取网页源码
     * 
     * @param response
     * @return String
     */
    public HttpEntity fetchWebpage(CloseableHttpResponse response) {
        HttpEntity entity = null;
        try {
            entity = response.getEntity();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return entity;
    }

    private void addHeaders(HttpUriRequest request, Map<String, String> headers) {
        if (headers == null) {
            return;
        }
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            request.addHeader(key, headers.get(key));
        }
    }

    private HttpPost preparePostMethod(String url, Map<String, String> params) {
        HttpPost post = new HttpPost(url);
        if (params != null && params.size() > 0) {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            Set<String> keys = params.keySet();
            for (String key : keys) {
                pairs.add(new BasicNameValuePair(key, params.get(key)));
            }
            post.setEntity(new UrlEncodedFormEntity(pairs, Charset.forName("UTF-8")));
        }
        return post;
    }

    private CloseableHttpResponse visit(HttpUriRequest request) {
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }
}
