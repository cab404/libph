package com.cab404.libph.modules;

import com.cab404.libph.data.Profile;
import com.cab404.libph.data.Topic;
import com.cab404.libph.util.LS;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.ModuleImpl;
import com.cab404.moonlight.parser.HTMLTree;
import com.cab404.moonlight.parser.Tag;
import com.cab404.moonlight.util.SU;
import com.cab404.moonlight.util.U;

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

    @Override
    public Topic extractData(HTMLTree page, AccessProfile profile) {
        Topic label = new Topic();
        label.text = page.xPathStr("div&class=*text").trim();

        label.title = SU.removeAllTags(page.xPathStr("header/h1")).trim(); // Если header в списках - это ещё и ссылка.
        label.id = U.parseInt(SU.bsub(page.xPathFirstTag("footer/div&class=topic-share").get("id"), "share_", ""));

        Tag blog = page.xPathFirstTag("header/div/a&class=topic-blog*");
        label.blog.url_name = blog.get("href").contains("/blog/") ? SU.bsub(blog.get("href"), "/blog/", "/") : null;
        label.blog.name = page.getContents(blog);

        label.date = LS.parseSQLDate(page.xPathFirstTag("footer/ul/li/time").get("datetime"));

        label.author = new Profile();
        label.author.login = page.xPathStr("footer/ul/li/a&rel=author");

        if (page.xPathFirstTag("footer/ul/li/a/img") != null) {
            label.author.small_icon = page.xPathFirstTag("footer/ul/li/a/img").get("src");
            label.author.fillImages();
        } else
            label.author.is_system = true;

        if (mode != Mode.LETTER) {
            for (Tag tag : page.xPath("footer/ul&class=topic-tags*/li/a&rel=tag"))
                label.tags.add(page.getContents(tag));
        }

        label.in_favourites = page.getTagByID("fav_topic_*").get("class").contains("active");

        if (mode == Mode.LIST) {
            label.comments = U.parseInt(page.xPathStr("footer/ul/li&class=topic-info-comments/a/span"));
            String new_comments = page.xPathStr("footer/ul/li&class=topic-info-comments/a/span&class=count");
            label.comments_new = new_comments == null ? 0 : U.parseInt(new_comments);
        }

        return label;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return "article".equals(tag.name);
    }

}
