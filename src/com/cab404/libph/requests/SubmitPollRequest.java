package com.cab404.libph.requests;

import com.cab404.libph.data.KV;
import com.cab404.libph.modules.TopicModule;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.EntrySet;
import com.cab404.moonlight.parser.HTMLTree;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by cab404 on 11/9/17.
 */
public class SubmitPollRequest extends LSRequest {

    public int topicId = 0;
    public int answerId = 0;
    public List<KV<String, Integer>> results;

    public SubmitPollRequest(int topicId, int answerId) {
        this.topicId = topicId;
        this.answerId = answerId;
    }

    @Override
    protected void getData(EntrySet<String, String> data) {
        data.put("idTopic", topicId + "");
        data.put("idAnswer", answerId + "");
    }

    @Override
    protected String getURL(AccessProfile profile) {
        return "/ajax/vote/question/";
    }

    @Override
    protected void handle(JSONObject object) {
        super.handle(object);
        if (success) {
            HTMLTree sText = new HTMLTree(object.get("sText").toString());
            results = TopicModule.parsePollResults(sText.getTree(sText.xPathUnique("ul&class=poll-result")));
        }
    }
}
