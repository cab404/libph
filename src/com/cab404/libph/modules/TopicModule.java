package com.cab404.libph.modules;

import com.cab404.libph.data.KV;
import com.cab404.libph.data.Profile;
import com.cab404.libph.data.Topic;
import com.cab404.libph.util.LS;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.ModuleImpl;
import com.cab404.moonlight.parser.HTMLTree;
import com.cab404.moonlight.parser.Tag;
import com.cab404.moonlight.util.SU;
import com.cab404.moonlight.util.U;

import java.util.LinkedList;
import java.util.List;

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

    public static List<KV<String, Integer>> parsePollResults(HTMLTree from) {
        List<Tag> keys = from.xPath("li/dl/dd");
        List<Tag> values = from.xPath("li/dl/dt/span");
        List<KV<String, Integer>> results = new LinkedList<>();

        for (int i = 0; i < Math.min(values.size(), keys.size()); i++) {
            String key = from.getContents(keys.get(i));
            String value = from.getContents(values.get(i));
            results.add(new KV<>(
                    SU.removeAllTags(key).trim(),
                    Integer.parseInt(SU.sub(value, "(", ")"))
            ));
        }

        return results;
    }

    @Override
    public Topic extractData(HTMLTree page, AccessProfile profile) {

        Topic label = new Topic();
        label.text = page.xPathStr("div&class=*text").trim();


        label.is_poll = page.get(0).props.get("class").contains("topic-type-question");
        if (label.is_poll) {
            HTMLTree poll = page.getTree(page.xPathUnique("div&class=poll"));

            Tag resultStart = poll.xPathUnique("ul&class=poll-result");
            label.is_pollFinished = resultStart != null;

            if (label.is_pollFinished) {
                label.pollData = parsePollResults(poll.getTree(resultStart));
            } else {
                label.pollData = new LinkedList<>();
                for (Tag tag : poll.xPath("ul&class=poll-vote/li/label")) {
                    label.pollData.add(new KV<>(SU.removeAllTags(poll.getContents(tag)).trim(), -1));
                }
            }

        }


        label.title = SU.deEntity(SU.removeAllTags(page.xPathStr("header/h1")).trim()); // Если header в списках - это ещё и ссылка.
        label.id = U.parseInt(SU.bsub(page.xPathFirstTag("footer/ul/li&class=topic-info-favourite/a").get("id"), "topic_", ""));

        Tag blog = page.xPathFirstTag("header/div&class=topic-info/a");
        label.blog.url_name = blog.get("href").contains("/blog/") ? SU.bsub(blog.get("href"), "/blog/", "/") : null;
        label.blog.name = page.getContents(blog);

        label.date = LS.parseSQLDate(page.xPathFirstTag("header/div&class=topic-info/time").get("datetime"));

        label.author = new Profile();
        label.author.login = page.xPathStr("footer/ul/div/a&rel=author");

        if (page.xPathFirstTag("footer/ul/div/a/img") != null) {
            label.author.small_icon = page.xPathFirstTag("footer/ul/div/a/img").get("src");
            label.author.fillImages();
        } else
            label.author.is_system = true;

        if (mode != Mode.LETTER) {
            for (Tag tag : page.xPath("header/ul&class=topic-tags*/li/a&rel=tag"))
                label.tags.add(page.getContents(tag));
        }

        label.in_favourites = page.getTagByID("fav_topic_*").get("class").contains("active");

        if (mode == Mode.LIST) {
            label.comments = U.parseInt(page.xPathStr("footer/ul/li&class=topic-info-count/a/span"));
            String new_comments = page.xPathStr("footer/ul/li&class=topic-info-count/a/span&class=count");
            label.comments_new = new_comments == null ? 0 : U.parseInt(new_comments);
        }

        return label;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return "article".equals(tag.name);
    }

}
