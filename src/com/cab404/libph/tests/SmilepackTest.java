package com.cab404.libph.tests;

import com.cab404.libph.requests.SmilepackRequest;
import com.cab404.moonlight.framework.AccessProfile;
import com.cab404.moonlight.util.tests.Test;

public class SmilepackTest extends Test {
    @Override
    public void test(AccessProfile accessProfile) {
        SmilepackRequest request = new SmilepackRequest();
        request.fetch(accessProfile);

        assertNonNull("Смайлопак загружен", request.smilepack);
        assertEquals("Смайл Тамбл", request.smilepack.getLink("hawk-01"), "/smilepack/hawk/01.png");
    }
}
