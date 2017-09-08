package org.qydata;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jonhn on 2016/11/11.
 */
public class HashHelperTest {
    @Test
    public void md5() throws Exception {
        assertEquals("B398E5A33AFFBF823EB5882098CC17D6", HashHelper.md5("user1"+"user1pass"+"6ZaW8V"+"1476113775105"));
    }



}