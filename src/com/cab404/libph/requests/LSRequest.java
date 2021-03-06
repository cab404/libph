package com.cab404.libph.requests;

import com.cab404.libph.data.LivestreetKey;
import com.cab404.libph.pages.BasePage;
import com.cab404.libph.util.PonyhawksProfile;
import com.cab404.moonlight.facility.RequestBuilder;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.EntrySet;
import com.cab404.moonlight.framework.ShortRequest;
import com.cab404.moonlight.util.SU;
import org.apache.http.client.methods.HttpRequestBase;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;

/**
 * @author cab404
 */
public abstract class LSRequest extends ShortRequest {
    protected AccessProfile profile;

    protected boolean success = false;

    public static final String LS_KEY_ENTRY = "LIVESTREET_SECURITY_KEY", PHPID_ENTRY = "PHPSESSID";
    public static final int LS_KEY_LEN = 32;


    public String msg = null;
    public String title = null;


    @Override
    protected void handleResponse(String response) {
        JSONObject parsed;
        try {
            parsed = (JSONObject) new JSONParser().parse(response);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (profile instanceof PonyhawksProfile)
            ((PonyhawksProfile) profile).getMessageListener().show(parsed);

        success = !(boolean) parsed.get("bStateError");
        msg = (String) parsed.get("sMsg");
        title = (String) parsed.get("sMsgTitle");
        handle(parsed);
    }

    protected boolean isMultipart() {
        return false;
    }

    protected boolean isChunked() {
        return false;
    }

    protected void handle(JSONObject object) {
    }

    protected abstract void getData(EntrySet<String, String> data);

    protected abstract String getURL(AccessProfile profile);

    private LivestreetKey key;

    @Override
    protected HttpRequestBase getRequest(AccessProfile profile) {
        this.profile = profile;

        EntrySet<String, String> data = new EntrySet<>();
        data.put("security_ls_key", key.key);
        getData(data);

        String url = getURL(profile);

        RequestBuilder request = RequestBuilder
                .post(url, profile)
                .addReferer(url);

        if (isMultipart()) {

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


    @Deprecated
    public <T extends LSRequest> T exec(AccessProfile profile, LivestreetKey key) {
        return exec(profile);
    }

    @Deprecated
    public <T extends LSRequest> T exec(AccessProfile profile, BasePage page) {
        return exec(profile);
    }

    /**
     * Just like {@link LSRequest#fetch(AccessProfile)}, but chain-y
     */
    @SuppressWarnings("unchecked")
    public <T extends LSRequest> T exec(AccessProfile profile) {
        fetch(profile);
        return ((T) this);
    }

    @Override
    public void fetch(AccessProfile profile) {
        this.key = new LivestreetKey(profile.cookies.get(LS_KEY_ENTRY));
        super.fetch(profile);
    }

}
