package com.hrzafer.prizma.feature;



import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class FeatureValueFactoryTest {

    @Test
    public void testCreate() throws Exception {
        Object o = 5.0;
        FeatureValue actual = FeatureValueFactory.create(o);
        assertEquals("5.0", o.toString());

        o = true;
        actual = FeatureValueFactory.create(o);
        assertEquals("true", o.toString());

        o = 123;
        actual = FeatureValueFactory.create(o);
        assertEquals("123", o.toString());

        o = "test";
        actual = FeatureValueFactory.create(o);
        assertEquals("test", o.toString());
    }
}
