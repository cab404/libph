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
        String err_img = page.xPathFirstTag("div&class=content-error/img").get("src");

       if(err_img.contains("404")) return PHErr.NOT_FOUND;

        return PHErr.UNKNOWN;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return tag.get("class").equals("content-error");
    }

}
