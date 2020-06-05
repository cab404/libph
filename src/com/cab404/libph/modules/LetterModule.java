package com.cab404.libph.modules;

import com.cab404.libph.data.Letter;
import com.cab404.libph.util.LS;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.ModuleImpl;
import com.cab404.moonlight.parser.HTMLTree;
import com.cab404.moonlight.parser.Tag;
import com.cab404.moonlight.util.SU;

/**
 * @author cab404
 */
public class LetterModule extends ModuleImpl<Letter> {

    @Override
    public Letter extractData(HTMLTree page, AccessProfile profile) {
        Letter letter = new Letter();

        letter.title = SU.deEntity(page.xPathStr("article/header/h1").trim());
        letter.text = SU.deEntity(page.xPathStr("article/div&class=*text*").trim());
        letter.date = LS.parseSQLDate(page.xPathFirstTag("article/header/div&class=topic-info-date/time").get("datetime"));

        for (Tag tag : page.xPath("div/header/a&class=user*"))
            letter.recipients.add(SU.deEntity(page.getContents(tag)));

        letter.starter.login = SU.deEntity(page.xPathStr("article/footer/ul/div/a&rel=author"));
        letter.starter.small_icon = page.xPathFirstTag("article/footer/ul/div/a/img").get("src");
        letter.starter.fillImages();

        letter.id = Integer.parseInt(SU.bsub(page.xPathFirstTag("article/footer/ul/li&class=topic-info-favourite/a&id=fav_topic_*").get("id"), "_", ""));

        return letter;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return "div".equals(tag.name) && tag.get("class").equals("talk-read-item");
    }

}
