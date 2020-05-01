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
        Tag bottom_block = page.xPathFirstTag("div&class=comment-bottom");

        if (info_block == null) {

            comment.deleted = true;
            comment.id = Integer.parseInt(SU.bsub(page.get(0).get("id"), "id_", ""));

        } else {

            HTMLTree info = page.getTree(info_block);
            HTMLTree bottom = page.getTree(bottom_block);

            comment.id = Integer.parseInt(SU.split(info.xPathUnique("a&href=*#comment*").get("href"), "#comment").get(1));

            Tag parent = info.xPathFirstTag("li&class=*parent*/a");
            if (parent == null)
                comment.parent = 0;
            else
                comment.parent = U.parseInt(SU.bsub(parent.get("onclick"), ",", ");"));

            // Немножко откостылим логин.
            {
                comment.author.login = SU.sub(info.xPathUnique("a&href=*profile/*/").get("href"), "profile/", "/");
            }
            if (bottom.xPathFirstTag("div/img&class=comment-avatar") == null)
                comment.author.is_system = true;
            else {
                comment.author.small_icon = bottom.xPathFirstTag("div/img&class=comment-avatar").get("src");
                comment.author.fillImages();
            }

            comment.is_new = page.get(0).get("class").contains("comment-new");

            comment.ip = page.getContents(bottom.xPathFirstTag("div&class=comment-date/a"));
            comment.date = LS.parseSQLDate(bottom.xPathFirstTag("div&class=comment-date/time").get("datetime"));

            if (type != Mode.LETTER) {
                Tag favs = info.xPathFirstTag("li/class=comment-favourite*");
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
