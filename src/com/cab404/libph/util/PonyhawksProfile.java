package com.cab404.libph.util;

import com.cab404.libph.pages.BasePage;
import com.cab404.libph.requests.LoginRequest;
import com.cab404.moonlight.framework.AccessProfile;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.json.simple.JSONObject;

import static com.cab404.libph.requests.LSRequest.LS_KEY_ENTRY;
import static com.cab404.libph.requests.LSRequest.LS_KEY_LEN;

/**
 * @author cab404
 */
public class PonyhawksProfile extends AccessProfile {

    MessageListener messageListener = new MessageListener() {
        @Override
        public void show(JSONObject parsed) {
        }
    };

    private HttpHost host;

    public MessageListener getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public PonyhawksProfile() {
        cookies.put("CHECK", "0");
        cookies.put("path", "/");
        host = new HttpHost("ponyhawks.ru", 443, "https");
    }

    public PonyhawksProfile(String hostname, int port, String scheme) {
        cookies.put("CHECK", "0");
        cookies.put("path", "/");
        host = new HttpHost(hostname, port, scheme);
    }

    @Override
    public HttpHost getHost() {
        return host;
    }

    @Override
    public synchronized void handleCookies(Header[] headers) {
        String ls_key_cached = cookies.get(LS_KEY_ENTRY);
        super.handleCookies(headers);
        String ls_key_new = cookies.get(LS_KEY_ENTRY);

        if (ls_key_new != null)
            if (ls_key_new.length() != LS_KEY_LEN) {
                System.out.println("LSKEY невалиден. SAY HELLO TO THE RANDOM WITH " + ls_key_new);

                if (ls_key_cached == null)
                    cookies.remove(LS_KEY_ENTRY);
                else
                    cookies.put(LS_KEY_ENTRY, ls_key_cached);
            }
    }

    public boolean login(String name, String password) {
        if (!cookies.containsKey(LS_KEY_ENTRY)) {
            BasePage page = new BasePage();
            page.fetch(this);
        }
        return new LoginRequest(name, password).exec(this).success();
    }

    public static PonyhawksProfile parseString(String s) {
        PonyhawksProfile _return = new PonyhawksProfile();
        _return.setUpFromString(s);
        return _return;
    }

    @Override
    public String userAgentName() {
        return "sweetieBot";
    }
}
