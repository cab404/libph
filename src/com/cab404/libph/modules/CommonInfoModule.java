package com.cab404.libph.modules;

import com.cab404.libph.data.CommonInfo;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.ModuleImpl;
import com.cab404.moonlight.parser.HTMLTree;
import com.cab404.moonlight.parser.Tag;
import com.cab404.moonlight.util.U;

/**
 * Окошко юзера сверху страницы.
 *
 * @author cab404
 */
public class CommonInfoModule extends ModuleImpl<CommonInfo> {

    @Override
    public CommonInfo extractData(HTMLTree page, AccessProfile profile) {
        CommonInfo info = new CommonInfo();

        Tag tag = page.xPathFirstTag("div&class=user-menu-trigger/div&class=message-counter");
        if (tag != null) {
            info.new_messages = U.parseInt(page.getContents(tag));
        } else
            info.new_messages = 0;

        info.avatar = page.xPathFirstTag("div&class=user-menu-trigger/img&class=user-menu-avatar").get("src");
        info.username = page.xPathStr("div&class=user-menu/div&class=user-menu-name");

        finish();
        return info;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return "header".equals(tag.name) && "banner".equals(tag.get("role"));
    }


}
