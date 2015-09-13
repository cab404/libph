package com.cab404.libph.tests;

import com.cab404.libph.pages.TopicPage;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.util.tests.Test;

/**
 * @author cab404
 */
public class TopicTest extends Test {

    @Override
    public void test(AccessProfile profile) {
        TopicPage topicPage;

        topicPage = new TopicPage(7);
        topicPage.fetch(profile);

        // Не применимо
//        assertLessOrEquals("Все комментарии загружены", topicPage.header.comments, topicPage.comments.size());
        assertEquals("Имя блога", topicPage.header.blog.name, "dev");
        assertEquals("URL-имя блога", topicPage.header.blog.url_name, "dev");
        assertEquals("Название поста", topicPage.header.title, "Тестовый топик [Ponyhawks]");

        topicPage = new TopicPage(282);
        topicPage.fetch(profile);

        assertLessOrEquals("Все комментарии загружены", topicPage.header.comments, topicPage.comments.size());
        assertEquals("Имя блога", topicPage.header.blog.name, "Полуночники");
        assertEquals("URL-имя блога", topicPage.header.blog.url_name, "Ponyhawks");
        assertEquals("Название поста", topicPage.header.title, "Двести двадцать первый немного припозднившийся чат Полуночников [Ponyhawks]");
    }

}
