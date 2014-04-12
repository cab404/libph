package com.cab404.libtabun.modules;

import com.cab404.libtabun.data.Topic;
import com.cab404.moonlight.util.SU;
import com.cab404.moonlight.util.U;
import com.cab404.moonlight.parser.HTMLTree;
import com.cab404.moonlight.parser.Tag;
import com.cab404.moonlight.framework.AccessProfile;

/**
 * Парсер заголовков топиков.
 *
 * @author cab404
 */
public class TopicModule extends ModuleImpl<Topic> {

    private Mode mode;

    public enum Mode {
        TOPIC, LIST, LETTER
    }

    public TopicModule(Mode mode) {
        this.mode = mode;
    }

    @Override public Topic extractData(HTMLTree page, AccessProfile profile) {
        Topic label = new Topic();
        label.text = page.xPathStr("div&class=*text").trim();
        label.title = page.xPathStr("header/h1");
        label.id = U.parseInt(SU.bsub(page.xPathFirstTag("footer/p").get("class"), "-", ""));

        Tag blog = page.xPathFirstTag("header/div/a&class=topic-blog");
        label.blog.url_name = SU.bsub(blog.get("href"), "blog/", "/");
        label.blog.name = page.getContents(blog);

        label.date = U.convertDatetime(page.xPathFirstTag("footer/ul/li/time").get("datetime"));

        if (mode != Mode.LETTER) {
            label.votes = page.getContents(page.getTagByID("vote_total_*")).trim();

            String vote_info = page.xPathStr("header/div/div/div&id=vote_area*");
            label.vote_enabled = vote_info.contains("vote-not-self")
                    && vote_info.contains("not-voted")
                    && vote_info.contains("vote-not-expired");

            if (!label.vote_enabled) {
                if (vote_info.contains("voted-up"))
                    label.your_vote = 1;
                if (vote_info.contains("voted-down"))
                    label.your_vote = -1;
                if (vote_info.contains("voted-zero"))
                    label.your_vote = 0;
            }

            for (Tag tag : page.xPath("footer/p/a"))
                label.tags.add(page.getContents(tag));
        }

        label.in_favourites = page.getTagByID("fav_topic_*").get("class").contains("active");

        if (mode == Mode.LIST) {
            label.comments = U.parseInt(page.xPathStr("footer/ul/li&class=topic-info-comments/a/span"));
            label.comments_new = U.parseInt(page.xPathStr("footer/ul/li&class=topic-info-comments/a/span&class=count"));
        }

        return label;
    }

    @Override public boolean doYouLikeIt(Tag tag) {
        return "article".equals(tag.name);
    }

}
