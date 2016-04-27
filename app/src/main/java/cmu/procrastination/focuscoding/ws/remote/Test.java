package cmu.procrastination.focuscoding.ws.remote;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public final static String HOME_PAGE_URL = "https://leetcode.com";
    public final static String LOGIN_PAGE_URL = "https://leetcode.com/accounts/login/";
    private static String csrftoken = "mBPm2xb578YSqk0WICUObfEYBh7JszZ0";
    private static String phpsessid = "i8hgu33c6cquwgxhmle7ic88wvha1ii6";
    private HttpUtils httpUtils = new HttpUtils();
//    public static void main(String[] args) {
//        loginLeetcode();
//        fetchPage("https://leetcode.com/progress/");
//    }
    
    public boolean loginLeetcode() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Referer", HOME_PAGE_URL);
        headers.put("Cookie", "csrftoken=" + csrftoken);
        Map<String, String> params = new HashMap<String, String>();
        params.put("login", "xuxiaoyang626@gmail.com");
        params.put("password", "xiaowuxiong626");
        params.put("csrfmiddlewaretoken", csrftoken);
        CloseableHttpResponse response = httpUtils.post(LOGIN_PAGE_URL, headers, params);
        try {
            if (response.getStatusLine().getStatusCode() == 302) {
                for (Header header : response.getHeaders("Set-Cookie")) {
                    for (HeaderElement element : header.getElements()) {
                        if (element.getName() != null && element.getName().equals("csrftoken")) {
                            csrftoken = element.getValue();
                        } else if (element.getName() != null && element.getName().equals("PHPSESSID")) {
                            phpsessid = element.getValue();
                        }
                    }
                }
                System.out.println("登录成功");
                return true;
            }
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("登录失败");
        return false;
     }
    public String fetchPage(String url) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Referer", "https://leetcode.com/problemset/algorithms/");
        headers.put("Cookie", "csrftoken=" + csrftoken + ";PHPSESSID=" + phpsessid);
        CloseableHttpResponse response = httpUtils.getWithoutAutoRedirect(url, headers);
        String line ="";
        try {
            HttpEntity res = httpUtils.fetchWebpage(response);
            InputStreamReader isr = new InputStreamReader(res.getContent());
            BufferedReader br = new BufferedReader(isr);
            while((line = br.readLine()) != null) {
                if (line.contains("ac_submissions:")) {
                    System.out.println(line);
                }
            }
            return line;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return line;
    }
}

