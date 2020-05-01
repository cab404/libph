package com.cab404.libph.tests;

import com.cab404.libph.pages.MainPage;
import com.cab404.libph.pages.BasePage;
import com.cab404.libph.requests.LoginRequest;
import com.cab404.libph.util.PonyhawksProfile;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.util.tests.Test;

import java.io.*;

/**
 * @author cab404
 */
public class LoginTest extends Test {

    @Override
    public void test(AccessProfile profile) {
        MainPage page = new MainPage();
        page.fetch(profile);

        String login, password;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("credentials.txt")));
            login = reader.readLine();
            password = reader.readLine();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals("Logged in (long form)", true, new LoginRequest(login, password).exec(profile, page).success());
        assertEquals("Logged in (short form)", true, ((PonyhawksProfile)profile).login(login, password));

        AccessProfile copy = PonyhawksProfile.parseString(profile.serialize());
        BasePage test = new BasePage();
        test.fetch(copy);

        assertNonNull("Copied account usage", test.c_inf);
        assertEquals("Username from header", login, test.c_inf.username);

        System.out.println(login);

    }


}
