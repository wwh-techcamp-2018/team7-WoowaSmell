package support;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.List;

class RestTemplateWithCookies extends TestRestTemplate {

    private List<String> cookies = null;

    public RestTemplateWithCookies() {
    }

    private synchronized List<String> getCookies() {
        return cookies;
    }

    private synchronized void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }

    public synchronized void resetCoookies() {
        this.cookies = null;
    }

    private void processHeaders(HttpHeaders headers) {
        final List<String> cookies = headers.get("Set-Cookie");
        if (cookies != null && !cookies.isEmpty()) {
            setCookies(cookies);
        }
    }
}