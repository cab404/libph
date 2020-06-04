package com.cab404.libph.tests;

import com.cab404.libph.pages.LetterPage;
import com.cab404.libph.pages.LetterTablePage;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.util.tests.Test;

/**
 * Если что - постучитесь ко мне на табун, добавлю в письмо и тестовый блог.
 *
 * @author cab404
 */
public class LetterTest extends Test {

    @Override
    public void test(AccessProfile profile) {
        LetterTablePage letters = new LetterTablePage(1);
        letters.fetch(profile);

        assertLess("Есть письма", 0, letters.letters.size());

        LetterPage page = new LetterPage(2135);
        page.fetch(profile);

        assertEquals("Участники", page.header.recipients.toArray(), new String[]{"Fiores", "ph"});
        assertEquals("Название", page.header.title, "Активность");
        assertEquals("Id", page.header.id, 2135);
        assertEquals("Комментарии", page.comments.size(), 1);
        assertEquals("Комментарий", page.comments.get(0).text, "111");
    }

}
