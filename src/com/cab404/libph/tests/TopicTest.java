package com.cab404.libph.tests;

import com.cab404.libph.data.Type;
import com.cab404.libph.pages.TopicPage;
import com.cab404.libph.requests.RefreshCommentsRequest;
import com.cab404.libph.requests.SubmitPollRequest;
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
        assertLessOrEquals("Все комментарии загружены", topicPage.header.comments, topicPage.comments.size());
  //      assertEquals("Время создания", topicPage.header.date.getTimeInMillis(), 1361028648000L);
        assertEquals("Имя блога", topicPage.header.blog.name, "dev");
        assertEquals("URL-имя блога", topicPage.header.blog.url_name, "dev");
        assertEquals("Название поста", topicPage.header.title, "Тестовый топик [Ponyhawks]");
        assertEquals("Автор", topicPage.header.author.login, "System Pony");
        assertEquals("Аватар автора", topicPage.header.author.small_icon, null);

        topicPage = new TopicPage(282);
        topicPage.fetch(profile);

        assertLessOrEquals("Все комментарии загружены", topicPage.header.comments, topicPage.comments.size());
        assertEquals("Имя блога", topicPage.header.blog.name, "Полуночники");
        assertEquals("URL-имя блога", topicPage.header.blog.url_name, "Ponyhawks");
        assertEquals("Название поста", topicPage.header.title, "Двести двадцать первый немного припозднившийся чат Полуночников [Ponyhawks]");
        assertEquals("Автор", topicPage.header.author.login, "Grave_Hunter");
        assertNonNull("Аватар автора", topicPage.header.author.small_icon);

        RefreshCommentsRequest request = new RefreshCommentsRequest(Type.TOPIC, 282, 0);
        request.exec(profile);
        assertLess("Refresh", 0, request.comments.size());

        topicPage = new TopicPage(1516);
        topicPage.fetch(profile);

        assertLessOrEquals("Все комментарии загружены", topicPage.header.comments, topicPage.comments.size());
        assertEquals("Имя блога", topicPage.header.blog.name, "dev");
        assertEquals("URL-имя блога", topicPage.header.blog.url_name, "dev");
        assertEquals("Название поста", topicPage.header.title, "Опрос для пользователей клиента на андроид: какая у вас версия?");
        assertEquals("Автор", topicPage.header.author.login, "Fiores");
        assertEquals("Опрос", topicPage.header.is_poll, true);
        assertEquals("Опрос завершен", topicPage.header.is_pollFinished, true);
        assertEquals("Варианты ответов", topicPage.header.pollData.size(), 3);

        // Polls are hard to test .-.
//        topicPage = new TopicPage(1537);
//        topicPage.fetch(profile);
//        SubmitPollRequest submitPollRequest = new SubmitPollRequest(1537, 0);
//        submitPollRequest.exec(profile);
//        System.out.println(submitPollRequest.results);
//        System.out.println(topicPage.header.pollData);

    }

}
