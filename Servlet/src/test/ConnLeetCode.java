package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

public class ConnLeetCode {

    //static web page address
    public final static String HOME_PAGE_URL = "https://leetcode.com";
    public final static String LOGIN_PAGE_URL = "https://leetcode.com/accounts/login/";

    //Random generated token
    private static String csrftoken = "mBPm2xb578YSqk0WICUObfEYBh7JszZ4";
    private static String phpsessid = "i8hgu33c6cquwgxhmle7ic88wvha1ii6";

    //USERNAME and PASSWORD for testing purpose
    //private String USERNAME;
    //private String PASSWORD;

    /**
     * String of the web content.
     */
    private String pageContent;
    /**
     * Login into LeetCode.
     * @return true or false
     */
    public boolean loginLeetcode(String username, String password) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Referer", HOME_PAGE_URL);
        headers.put("Cookie", "csrftoken=" + csrftoken);
        Map<String, String> params = new HashMap<String, String>();
        params.put("login", username);
        params.put("password", password);
        params.put("csrfmiddlewaretoken", csrftoken);
        CloseableHttpResponse response = HttpUtils.post(LOGIN_PAGE_URL, headers, params);
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
    /**
     * fetch the web content from the page.
     * @param url
     * @return
     */
    public String fetchPage(String url) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Referer", "https://leetcode.com/problemset/algorithms/");
        headers.put("Cookie", "csrftoken=" + csrftoken + ";PHPSESSID=" + phpsessid);
        CloseableHttpResponse response = HttpUtils.getWithoutAutoRedirect(url, headers);
        StringBuilder sb = new StringBuilder();
        try {
            HttpEntity res = HttpUtils.fetchWebpage(response);
            InputStreamReader isr = new InputStreamReader(res.getContent());
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            pageContent = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pageContent;
    }
    /**
     * Get the AC count from the web page.
     * @param page page bufferedReader
     * @return map of key and values
     */
    public int getACCount() {
        //Map<String, Integer> map = new HashMap<String, Integer>();
        int ac_count = 0;
        try {
            String[] lines = pageContent.split(System.lineSeparator());
                for (String line: lines) {
                    System.out.println(line);
                    if (line.contains("ac_submissions:")) {
                        Pattern p = Pattern.compile("[^0-9]");   
                        Matcher match = p.matcher(line);   
                        ac_count = Integer.parseInt(match.replaceAll(""));
                    }
                }
        } catch (Exception e) {
            System.out.println("cannot read AC count");
            e.printStackTrace();
        }
        return ac_count;
    }
}

