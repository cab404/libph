package com.cab404.libph.tests;

import com.cab404.libph.pages.BasePage;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.util.tests.Test;

/**
 * @author fiores
 */
public class BasePageTest extends Test {

    @Override
    public void test(AccessProfile profile) {
        BasePage page = new BasePage();
        page.fetch(profile);

        assertNonNull("инфо", page.c_inf);
        assertEquals("никнейм", page.c_inf.username, "ph");
        assertNonNull("аватар", page.c_inf.avatar);
    }
}
