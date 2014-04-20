package com.cab404.libtabun.requests;

import com.cab404.libtabun.data.LivestreetKey;
import com.cab404.libtabun.pages.TabunPage;
import com.cab404.libtabun.util.MessageFactory;
import com.cab404.moonlight.facility.RequestFactory;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.EntrySet;
import com.cab404.moonlight.framework.ShortRequest;
import com.cab404.moonlight.util.SU;
import org.apache.http.client.methods.HttpRequestBase;
import org.json.simple.JSONObject;

import java.util.Map;

/**
 * @author cab404
 */
public abstract class LSRequest extends ShortRequest {
    protected boolean success = false;

    @Override protected void handleResponse(String response) {
        JSONObject jsonObject = MessageFactory.processJSONwithMessage(response);
        success = !(boolean) jsonObject.get("bStateError");
        handle(jsonObject);
    }

    protected boolean isLong() {return false;}
    protected boolean isChunked() {
        return false;
    }

    protected void handle(JSONObject object) {}

    protected abstract void getData(EntrySet<String, String> data);
    protected abstract String getURL(AccessProfile profile);

    private LivestreetKey key;
    @Override protected HttpRequestBase getRequest(AccessProfile profile) {

        EntrySet<String, String> data = new EntrySet<>();
        data.put("security_ls_key", key.key);
        getData(data);

        String url = getURL(profile);

        RequestFactory request = RequestFactory
                .post(url, profile)
                .addReferer(url);

        if (isLong()) {

            request
                    .MultipartRequest(data, isChunked());

        } else {

            StringBuilder request_body = new StringBuilder();
            for (Map.Entry<String, String> e : data)
                request_body
                        .append('&')
                        .append(SU.rl(e.getKey()))
                        .append('=')
                        .append(SU.rl(e.getValue()));

            request
                    .XMLRequest()
                    .setBody(request_body.toString(), isChunked());
        }

        return request.build();
    }

    /**
     * Возвращает bStateError из принятого json-а.
     */
    public boolean success() {
        return success;
    }

    public <T extends LSRequest> T exec(AccessProfile profile, LivestreetKey key) {
        this.key = key;
        super.fetch(profile);
        //noinspection unchecked
        return ((T) this);
    }

    public <T extends LSRequest> T exec(AccessProfile profile, TabunPage page) {
        return exec(profile, page.key);
    }

    @Override protected void fetch(AccessProfile accessProfile) {
        super.fetch(accessProfile);
    }

}
