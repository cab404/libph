package com.cab404.libph.tests;

import com.cab404.libph.pages.BasePage;
import com.cab404.libph.requests.UserAutocompleteRequest;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.util.tests.Test;

/**
 * @author cab404
 */
public class UserAutocompleteTest extends Test {


    @Override
    public void test(AccessProfile profile) {
        BasePage basePage = new BasePage();
        basePage.fetch(profile);
        UserAutocompleteRequest req = new UserAutocompleteRequest("Orhide").exec(profile);

        assertEquals("Результаты поиска", req.names.toArray(), new String[]{"Orhideous"});

    }

}
