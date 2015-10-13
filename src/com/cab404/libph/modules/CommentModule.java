package com.cab404.libph.modules;

import com.cab404.libph.data.Comment;
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
public class CommentModule extends ModuleImpl<Comment> {

    private final Mode type;

    public enum Mode {
        TOPIC, LIST, LETTER
    }

    public CommentModule(Mode type) {
        this.type = type;
    }

    @Override
    public Comment extractData(HTMLTree page, AccessProfile profile) {
        Comment comment = new Comment();

        try {
            comment.text = page.getContents(page.xPathFirstTag("section/div/div&class=*text*")).trim();
        } catch (Exception ex) {
            comment.deleted = true;
        }

        Tag info_block = page.xPathFirstTag("ul&class=comment-info");

        if (info_block == null) {

            comment.deleted = true;

        } else {

            HTMLTree info = page.getTree(info_block);

            comment.id = U.parseInt(SU.split(page.xPathUnique("a&href=*#comment*").get("href"), "#comment").get(1));

            Tag parent = info.xPathFirstTag("li&class=*parent*/a");
            if (parent == null)
                comment.parent = 0;
            else
                comment.parent = U.parseInt(SU.bsub(parent.get("onclick"), ",", ");"));

            // Немножко откостылим логин.
            {
                comment.author.login = SU.sub(info.xPathUnique("a&href=*profile/*/").get("href"), "profile/", "/");
            }
            if (info.xPathFirstTag("li/img&alt=avatar") == null)
                comment.author.is_system = true;
            else {
                comment.author.small_icon = info.xPathFirstTag("li/img&alt=avatar").get("src");
                comment.author.fillImages();
            }

            comment.is_new = page.get(0).get("class").contains("comment-new");

            comment.date = LS.parseSQLDate(info.xPathFirstTag("li/time").get("datetime"));

            if (type != Mode.LETTER) {
                Tag favs = info.xPathFirstTag("li/div&class=favourite*");
                comment.in_favs = favs != null && favs.get("class").contains("active");
            }
        }

        return comment;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return "section".equals(tag.name) && String.valueOf(tag.get("class")).contains("comment");
    }

}
