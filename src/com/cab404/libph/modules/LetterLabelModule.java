package com.cab404.libph.modules;

import com.cab404.libph.data.LetterLabel;
import com.cab404.libph.util.LS;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.ModuleImpl;
import com.cab404.moonlight.parser.HTMLTree;
import com.cab404.moonlight.parser.Tag;
import com.cab404.moonlight.util.SU;
import com.cab404.moonlight.util.U;

/**
 * @author cab404
 */
public class LetterLabelModule extends ModuleImpl<LetterLabel> {

    @Override
    public LetterLabel extractData(HTMLTree page, AccessProfile profile) {
        LetterLabel letter = new LetterLabel();

        for (Tag user : page.xPath("span&class=recipients/a&class=user")) {
            letter.recipients.add(page.getContents(user));
        }

        letter.title = page.xPathStr("a&class=js-title-talk*/span");
        letter.text = page.xPathFirstTag("a&class=js-title-talk*").get("title");

        if(letter.title == null) {
            letter.is_new = true;
            letter.title =  page.xPathStr("a&class=js-title-talk*/strong");
        }

        letter.id = U.parseInt(SU.bsub(page.xPathFirstTag("a&class=js-title-talk*").get("href"), "read/", "/"));

        String comments = null;
        for(Tag span : page.xPath("span")) {
            if (span.get("class") == "") {
                comments = page.getContents(span);
                letter.comments = comments == null ? 0 : U.parseInt(comments);
            }
        }

        letter.date = LS.parseDate(page.xPathStr("span&class=date"));

        if (comments == null) return letter;

        comments = page.xPathStr("span&class=new");
        letter.comments_new = comments == null ? 0 : U.parseInt(comments);

        return letter;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return "div".equals(tag.name) && tag.get("class").equals("talk-item");
    }
}
