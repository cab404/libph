package com.cab404.libtabun.parts;

import com.cab404.libtabun.*;
import com.cab404.libtabun.facility.HTMLParser;
import com.cab404.libtabun.facility.MessageFactory;
import com.cab404.libtabun.facility.RequestFactory;
import com.cab404.libtabun.facility.ResponseFactory;
import org.json.simple.JSONObject;

/**
 * Ну тут всё ясно.
 *
 * @author cab404
 */
public class Comment extends Part {
    public String author = "", body = "", time = "";
    public int votes, parent = 0;
    public String avatar;
    public boolean MODERASTIA = false;

    public boolean is_new = false;

    public Comment() {
        type = "Comment";
    }

    /**
     * Парсит комментарии с <s>1823 года</s> тега section
     */
    public static class CommentParser implements ResponseFactory.Parser {
        public Comment comment = new Comment();
        int part = 0;
        StringBuilder text = new StringBuilder();

        @Override
        public boolean line(String line) {
            switch (part) {
                case 0:
                    // Находим заголовок
                    if (line.contains("<section id=\"comment_id")) {
                        comment.id = U.parseInt(U.sub(line, "_id_", "\""));
                        text.append(line).append('\n');
                        part++;
                    }
                    break;

                case 1:
                    if (line.contains("</section>")) {
                        text.append(line).append('\n');

                        HTMLParser parser = new HTMLParser(text.toString());

                        // Если комментарий пуст - вероятно, это остов убитого модерастией.
                        try {
                            parser.getTagIndexByProperty("class", "text");
                        } catch (Throwable e) {
                            comment.MODERASTIA = true;
                            return false;
                        }


                        String props = parser.getTagByName("section").props.get("class");
                        comment.is_new = props.contains("comment-new");

                        comment.body = parser.getContents(parser.getTagIndexByProperty("class", "text")).replaceAll("\t", "");
                        // Тут чуточку сложнее.
                        comment.time = parser.getParserForIndex(parser.getTagIndexByProperty("class", "comment-date")).getTagByName("time").props.get("datetime");

                        // Достаём автора и аватарку.

                        HTMLParser author;
                        author = parser.getParserForIndex(parser.getTagIndexByProperty("class", "comment-info"));
                        {
                            comment.author = U.bsub(author.getTagByName("a").props.get("href"), "profile/", "/");
                            comment.avatar = author.getTagByProperty("alt", "avatar").props.get("src");
                        }

                        // Попытка достать род. комментарий:
                        try {
                            HTMLParser comment_parent_goto = parser.getParserForIndex(parser.getTagIndexByProperty("class", "goto goto-comment-parent"));
                            comment.parent = U.parseInt(U.bsub(comment_parent_goto.getTagByName("a").props.get("onclick"), ",", ");"));
                        } catch (Throwable e) {
                            comment.parent = 0;
                        }

//                        comment.votes = U.parseInt(parser.getContents(parser.getTagByProperty("class", "vote-count")).trim());


                        return false;
                    } else this.text.append(line).append("\n");

                    break;
            }

            return true;
        }
    }

    public String edit(User user, Part post, String text) {
        String body = "";
        body += "commentId=" + id;
        body += "&text=" + U.rl(text);
        body += "&security_ls_key=" + post.key;

        String request = ResponseFactory.read(
                user.execute(
                        RequestFactory.post("/ec_ajax/savecomment/")
                                .addReferer(post.key.address)
                                .setBody(body)
                                .XMLRequest()
                                .build()));

        JSONObject object = MessageFactory.processJSONwithMessage(request);


        boolean err = (boolean) object.get("bStateError");

        if (!err)
            this.body = U.drl((String) object.get("sText"));

        return err ? null : this.body;
    }

    private boolean favourites(User user, int type) {
        String body = "";
        body += "&type=" + type;
        body += "&idComment=" + id;
        body += "&security_ls_key=" + key.key;

        String request = ResponseFactory.read(
                user.execute(
                        RequestFactory.post("/ajax/favourite/comment/")
                                .addReferer(key.address)
                                .setBody(body)
                                .XMLRequest()
                                .build()));

        JSONObject object = MessageFactory.processJSONwithMessage(request);

        return (boolean) object.get("bStateError");
    }

    public boolean addToFavourites(User user) {
        return favourites(user, 1);
    }

    public static int getPostNum(User user, int comment_id) {
        return Integer.parseInt(
                U.bsub(
                        user.execute(RequestFactory.get("/comments/" + comment_id).build(), false)
                                .getFirstHeader("Location")
                                .getValue(),
                        "/",
                        ".html")
        );
    }

    public static Comment getByID(User user, int comment_id) {
        Post post = new Post(user, Comment.getPostNum(user, comment_id));
        post.fetchNewComments(user, comment_id - 1);
        return post.getCommentByID(comment_id);
    }

    public boolean removeFromFavourites(User user) {
        return favourites(user, 0);
    }
}
