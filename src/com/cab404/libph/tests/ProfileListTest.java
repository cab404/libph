package com.cab404.libph.tests;

import com.cab404.libph.data.Profile;
import com.cab404.libph.pages.ProfilePage;
import com.cab404.libph.pages.UserListPage;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.util.tests.Test;

import static java.awt.SystemColor.info;

/**
 * @author cab404
 */
public class ProfileListTest extends Test {

    @Override
    public void test(AccessProfile profile) {
        UserListPage page = new UserListPage();
        page.fetch(profile);

        assertLess("Number", 0, page.users.size());

        page = new UserListPage("Ponyhawks");
        page.fetch(profile);

        assertLess("Number", 0, page.users.size());
    }
}
