package com.cab404.libph.modules;

import com.cab404.libph.data.Profile;
import com.cab404.libph.data.TimelineEntry;
import com.cab404.libph.data.Topic;
import com.cab404.libph.util.LS;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.ModuleImpl;
import com.cab404.moonlight.parser.HTMLTree;
import com.cab404.moonlight.parser.Tag;
import com.cab404.moonlight.util.SU;

/**
 * @author cab404
 */
public class TimelineModule extends ModuleImpl<TimelineEntry> {

    @Override
    public TimelineEntry extractData(HTMLTree page, AccessProfile profile) {
        TimelineEntry entry = new TimelineEntry();

        entry.comment_id = Integer.parseInt(SU.bsub(page.xPathFirstTag("a&class=stream-topic").get("href"), "#comment", ""));
        entry.commenter_login = page.xPathStr("a&class=*author");
        entry.topic_title = page.xPathStr("a&class=stream-topic");
        entry.comment_date = LS.parseDate(page.xPathFirstTag("time").get("title"));
        entry.comment_preview = page.get(0).get("title");

        return entry;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return "div".equals(tag.name) && "stream-item js-title-comment".equals(tag.get("class"));
    }
}
