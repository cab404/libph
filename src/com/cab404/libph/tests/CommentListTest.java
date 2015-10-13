package com.cab404.libph.tests;

import com.cab404.libph.modules.CommentModule;
import com.cab404.libph.pages.MainPage;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.ModularBlockParser;
import com.cab404.moonlight.util.tests.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Sorry for no comments!
 * Created at 08:06 on 08/10/15
 *
 * @author cab404
 */
public class CommentListTest extends Test {

    final List comments = new ArrayList();
    @Override
    public void test(AccessProfile profile) {
        new MainPage(){
            @Override
            public String getURL() {
                return "/profile/cab404/created/comments";
            }

            @Override
            protected void bindParsers(ModularBlockParser base) {
                super.bindParsers(base);
                base.bind(new CommentModule(CommentModule.Mode.LIST), BLOCK_COMMENT);
            }

            @Override
            public void handle(Object object, int key) {
                super.handle(object, key);
                if (key == BLOCK_COMMENT)
                    //noinspection unchecked
                    comments.add(object);
            }
        }.fetch(profile);
    }
}
