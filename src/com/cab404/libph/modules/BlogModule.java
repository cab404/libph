package com.cab404.libph.modules;

import com.cab404.libph.data.Blog;
import com.cab404.libph.util.LS;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.framework.ModuleImpl;
import com.cab404.moonlight.parser.HTMLTree;
import com.cab404.moonlight.parser.Tag;
import com.cab404.moonlight.util.SU;
import com.cab404.moonlight.util.U;

/**
 * @author cab404
 */
public class BlogModule extends ModuleImpl<Blog> {
    Blog blog = new Blog();

    @Override
    public Blog extractData(HTMLTree page, AccessProfile profile) {
        blog.name = page.xPathStr("div&class=blog-mini/button").trim();
        //blog.restricted = page.xPathFirstTag("h2/i") != null;

        blog.about = page.xPathStr("div&class=blog/div&class=blog-inner/div&class=blog-content/p&class=blog-description").trim();
        String t=page.xPathStr("div&class=blog/div&class=blog-inner/div&class=blog-content/ul/li/strong");
        blog.creation_date = LS.parseDate(page.xPathStr("div&class=blog/div&class=blog-inner/div&class=blog-content/ul/li/strong"));
        blog.url_name = SU.sub(page.xPathFirstTag("div&class=blog/div&class=blog-inner/div&class=blog-content/ul/li/span/a").get("href"), "blog/", "/users");

        try {
            blog.id = U.parseInt(SU.bsub(page.xPathFirstTag("div&class=blog/footer/button&id=button-blog-*").get("id"), "-", ""));
        } catch (NullPointerException e) {
            try {
                /* Значит мы имеем дело с дыратором/админом блога. Будем доставать по-иному */
                blog.id = U.parseInt(SU.sub(page.xPathFirstTag("div&class=blog/footer/ul/li&class=blog-info-edit/a").get("href"), "edit/", "/"));
            } catch (NullPointerException ex) {
                /* Значит нифига это не админ, а незалогиненый юзер. Нунафиг. */
                blog.id = -1;
            }
        }

        return blog;
    }

    @Override
    public boolean doYouLikeIt(Tag tag) {
        return ("div".equals(tag.name) && "content-wrapper".equals(tag.get("id")) && "main".equals(tag.get("role")));
    }
}
