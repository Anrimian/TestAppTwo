package com.testapptwo;

import com.testapptwo.utils.text.CryptoUtils;

import org.junit.Test;

/**
 * Created on 30.01.2017.
 */

public class CryptoTest {

    @Test
    public void testDecoding() {
        String text = "something";
        String key = "key";

        try {
            String encoded = CryptoUtils.encodeString(text, key);
            String decoded = CryptoUtils.decodeString(encoded, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
