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

        entry.commenter = new Profile();
        entry.topic = new Topic();

        entry.comment_id = Integer.parseInt(SU.bsub(page.xPathFirstTag("a&class=stream-topic").get("href"), "#comment", ""));
        entry.commenter.login = page.xPathStr("p/a&class=author");

        entry.topic.comments = Integer.parseInt(SU.removeAllTags(page.xPathStr("span&class=block-item-comments")));
        entry.topic.title = page.xPathStr("a&class=stream-topic");

        return entry;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return "li".equals(tag.name) && "js-title-comment".equals(tag.get("class"));
    }

}
