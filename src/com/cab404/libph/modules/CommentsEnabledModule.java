package com.cab404.libph.modules;

import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.ModuleImpl;
import com.cab404.moonlight.parser.HTMLTree;
import com.cab404.moonlight.parser.Tag;
import com.cab404.moonlight.parser.TagMatcher;

/**
 * @author cab404
 */
public class CommentsEnabledModule extends ModuleImpl {
    TagMatcher matcher = new TagMatcher("div&class=reply&id=reply");

    @Override
    public Object extractData(HTMLTree page, AccessProfile profile) {
        return true;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return matcher.matches(tag);
    }
}
