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

        Profile profile1 = page.users.get(0);

//        assertEquals("About", info.about, "cab404.r(63)");
//        assertEquals("ID", info.id, 17188);

        assertLess("Number", 0, page.users.size());



//        assertEquals("Телефон", info.get(Profile.ContactType.PHONE).get(0), "0000000");
//        assertEquals("E-mail", info.get(Profile.ContactType.EMAIL).get(0), "exa@mp.le");
//        assertEquals("Twitter", info.get(Profile.ContactType.TWITTER).get(0), "example");
//        assertEquals("ВКонтакте", info.get(Profile.ContactType.VKONTAKTE).get(0), "example");
//        assertEquals("Одноклассники", info.get(Profile.ContactType.ODNOKLASSNIKI).get(0), "example");
//        assertEquals("Сайт", info.get(Profile.ContactType.SITE).get(0), "example.com");
//
//        assertEquals("День рождения", info.get(Profile.UserInfoType.BIRTHDAY), "6 марта 2014");
//        assertEquals("Пол", info.get(Profile.UserInfoType.GENDER), "женский");
//
//        assertEquals("Фото", info.photo, "http://tabun.everypony.ru/templates/skin/synio/images/user_photo_female.png");
    }

}
