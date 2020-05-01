package com.cab404.libph.modules;

import com.cab404.libph.data.PHErr;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.ModuleImpl;
import com.cab404.moonlight.parser.HTMLTree;
import com.cab404.moonlight.parser.Tag;

/**
 * @author cab404
 */
public class ErrorModule extends ModuleImpl<PHErr> {

    @Override
    public PHErr extractData(HTMLTree page, AccessProfile profile) {
        String err_msg = page.xPathStr("div&class=content-error/div");

        if (err_msg == null) return null;

        if ("Здесь ничего нет...".equals(err_msg)) return PHErr.NOT_FOUND;

        return PHErr.UNKNOWN;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return tag.get("class").equals("content-error");
    }

}
