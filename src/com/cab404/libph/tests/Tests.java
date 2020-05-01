package com.cab404.libph.tests;

import com.cab404.libph.util.PonyhawksProfile;
import com.cab404.moonlight.util.tests.Test;
import com.cab404.moonlight.util.tests.TestLauncher;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author cab404
 */
public class Tests {

    public static void main(String[] args)
            throws FileNotFoundException {
        TestLauncher launcher = new TestLauncher(new PonyhawksProfile());

        ArrayList<Class<? extends Test>> tests = new ArrayList<>();

        tests.add(LoginTest.class);
        //tests.add(ProfileTest.class);
        //tests.add(CommentListTest.class);
        //tests.add(TopicTest.class);
        //tests.add(ErrorTest.class);
		//tests.add(LetterTest.class);
		//tests.add(BlogTest.class);
        //tests.add(StreamTest.class);
		//tests.add(UserAutocompleteTest.class);
		//tests.add(BlogListTest.class);
		tests.add(TimelineTest.class);
		tests.add(ProfileListTest.class);

        launcher.launch(tests);

    }

}
