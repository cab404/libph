package com.cab404.libph.modules;

import com.cab404.libph.data.Profile;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.ModuleImpl;
import com.cab404.moonlight.parser.HTMLTree;
import com.cab404.moonlight.parser.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Sorry for no comments!
 * Created at 12:13 on 13/05/16
 *
 * @author cab404
 */
public class UserListModule extends ModuleImpl<List<Profile>> {
    @Override
    public List<Profile> extractData(HTMLTree htmlTree, AccessProfile accessProfile) {
        ArrayList<Profile> profiles = new ArrayList<>();
        List<Tag> tags = htmlTree.xPath("div&class=user-item");
        for (int i = 0; i < tags.size(); i++) {
            HTMLTree tr = htmlTree.getTree(tags.get(i));
            Profile profile = new Profile();
            profile.login = tr.xPathStr("a/div&class=user-item-username");
            Tag iconTag = tr.xPathUnique("img&class=user-item-avatar");
            if (iconTag != null)
                profile.mid_icon = iconTag.get("src");
            else
                profile.is_system = true;
            profile.fillImages();
            profiles.add(profile);
        }
        return profiles;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return "div".equals(tag.name) && "users-list".equals(tag.get("class"));
    }
}
