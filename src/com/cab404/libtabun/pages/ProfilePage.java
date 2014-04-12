package com.cab404.libtabun.pages;

import com.cab404.libtabun.data.Profile;
import com.cab404.libtabun.modules.ProfileModule;
import com.cab404.moonlight.util.SU;
import com.cab404.moonlight.framework.ModularBlockParser;

/**
 * @author cab404
 */
public class ProfilePage extends TabunPage {
    public final String username;
    public Profile user_info;

    public ProfilePage(String username) {
        this.username = username;
    }

    @Override public String getURL() {
        return "/profile/" + SU.rl(username);
    }

    @Override protected void bindParsers(ModularBlockParser base) {
        super.bindParsers(base);
        base.bind(new ProfileModule(), BLOCK_USER_INFO);
    }

    @Override public void handle(Object object, int key) {
        super.handle(object, key);
        switch (key) {
            case BLOCK_USER_INFO:
                user_info = (Profile) object;
                break;
        }
    }
}
