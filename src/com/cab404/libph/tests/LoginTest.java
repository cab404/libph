package com.cab404.libph.tests;

import com.cab404.libph.pages.MainPage;
import com.cab404.libph.pages.TabunPage;
import com.cab404.libph.requests.LoginRequest;
import com.cab404.libph.util.PonyhawksProfile;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.util.tests.Test;

/**
 * @author cab404
 */
public class LoginTest extends Test {

    @Override
    public void test(AccessProfile profile) {
        profile.cookies.put("CHECK", "0");
        profile.cookies.put("path", "/");
        MainPage page = new MainPage();
        page.fetch(profile);

        String login, password;
        login = requestString("login");
        password = requestPassword("pwd");

        assertEquals("Logged in (long form)", true, new LoginRequest(login, password).exec(profile, page).success());
        assertEquals("Logged in (short form)", true, new PonyhawksProfile().login(login, password));

        AccessProfile copy = PonyhawksProfile.parseString(profile.serialize());
        TabunPage test = new TabunPage();
        test.fetch(copy);

        assertNonNull("Copied account usage", test.c_inf);
        assertEquals("Username from header", login, test.c_inf.username);

        System.out.println(login);

    }


}
