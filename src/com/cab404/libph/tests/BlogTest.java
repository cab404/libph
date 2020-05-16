package com.cab404.libph.tests;

import com.cab404.libph.data.Blog;
import com.cab404.libph.pages.BlogPage;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.util.tests.Test;

/**
 * @author cab404
 */
public class BlogTest extends Test {

    @Override
    public void test(AccessProfile profile) {
        BlogPage blogPage = new BlogPage(new Blog("Ponyhawks"));
        blogPage.fetch(profile);

        assertEquals("Название", "Полуночники", blogPage.blog.name);
        assertNonNull("Описание", blogPage.blog.about);
        assertEquals("URL", "Ponyhawks", blogPage.blog.url_name);
        assertEquals("Закрытость", false, blogPage.blog.restricted);
        assertEquals("Id", 5, blogPage.blog.id);

    }

}
