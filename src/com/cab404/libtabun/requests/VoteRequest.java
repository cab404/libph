package com.cab404.libtabun.requests;

import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.EntrySet;

/**
 * @author cab404
 */
public class VoteRequest extends LSRequest {


    private final int id;
    private final int vote;
    private final String type;

    /**
     * @param type Константа из {@link com.cab404.libtabun.data.Types}
     */
    public VoteRequest(int id, int vote, String type) {
        this.id = id;
        this.vote = vote;
        this.type = type;
    }

    @Override protected void getData(EntrySet<String, String> data) {
        data.put("id" + type, id + "");
        data.put("value", vote + "");
    }

    @Override protected String getURL(AccessProfile profile) {
        return "/ajax/vote/" + type.toLowerCase() + "/";
    }

}