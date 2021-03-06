package com.cab404.libph.pages;

import com.cab404.libph.data.Comment;
import com.cab404.libph.data.Letter;
import com.cab404.libph.modules.CommentModule;
import com.cab404.libph.modules.LetterModule;
import com.cab404.moonlight.framework.ModularBlockParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cab404
 */
public class LetterPage extends BasePage {
    public Letter header;
    public List<Comment> comments;
    private final int id;

    public LetterPage(int id) {
        this.id = id;
        comments = new ArrayList<>();
    }

    @Override
    public String getURL() {
        return "/talk/read/" + id;
    }

    @Override
    protected void bindParsers(ModularBlockParser base) {
        super.bindParsers(base);
        base.bind(new LetterModule(), BLOCK_LETTER_HEADER);
        base.bind(new CommentModule(CommentModule.Mode.LETTER), BLOCK_COMMENT);
    }

    @Override
    public void handle(Object object, int key) {
        switch (key) {
            case BLOCK_LETTER_HEADER:
                header = (Letter) object;
                break;
            case BLOCK_COMMENT:
                comments.add((Comment) object);
                break;
            default:
                super.handle(object, key);
        }

    }

}
