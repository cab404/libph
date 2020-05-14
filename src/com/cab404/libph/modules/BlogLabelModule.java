package com.cab404.libph.modules;

import com.cab404.libph.data.Blog;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.ModuleImpl;
import com.cab404.moonlight.parser.HTMLTree;
import com.cab404.moonlight.parser.Tag;
import com.cab404.moonlight.util.SU;
import com.cab404.moonlight.util.U;

/**
 * @author cab404
 */
public class BlogLabelModule extends ModuleImpl<Blog> {

    @Override
    public Blog extractData(HTMLTree page, AccessProfile profile) {
        Blog blog = new Blog();

        blog.name = page.xPathStr("a&class=blog-item-name");
        blog.url_name = SU.bsub(page.xPathFirstTag("a&class=blog-item-name").get("href"), "/blog/", "/");

        blog.readers = U.parseInt(page.xPathStr("div/span&id=blog_user_count*"));

        String id = page.xPathFirstTag("div/span&id=blog_user_count*").get("id");
        id = id.replace("blog_user_count_","");
        blog.id = U.parseInt(id);

        return blog;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return "div".equals(tag.name) && "blog-item".equals(tag.get("class"));
    }

}
