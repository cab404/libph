package com.cab404.libph.requests;

import com.cab404.libph.data.Smilepack;
import com.cab404.moonlight.facility.RequestBuilder;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.ShortRequest;
import com.cab404.moonlight.util.SU;
import org.apache.http.client.methods.HttpRequestBase;

public class SmilepackRequest extends ShortRequest {
    public Smilepack smilepack = new Smilepack();

    @Override
    protected void handleResponse(String response) {
        String[] lines = response.split("\n");
        for (String line : lines) {
            if (!line.startsWith("#"))
                continue;
            String id = SU.sub(line, "#", "{");
            String link = SU.sub(line, "url(", ");");
            smilepack.add(id, link);
        }
    }

    @Override
    protected HttpRequestBase getRequest(AccessProfile profile) {
        String url = "/templates/skin/synio/css/smilepack.css";
        return RequestBuilder.get(url, profile).build();
    }
}
