package com.csci5308.medinteract.utilities;

import org.junit.jupiter.api.Test;
import javax.crypto.spec.SecretKeySpec;
import static org.junit.jupiter.api.Assertions.*;

import java.security.Key;

class PasswordEncodeDecodeTest {

    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = "memo".getBytes();

    @Test
    void testEncrypt() throws Exception {
        assertEquals("l6mPTXO8wGfqRqWavjamBQ==", PasswordEncodeDecode.encrypt("42"));
    }

    @Test
    void testDecrypt() throws Exception {
        assertEquals("", PasswordEncodeDecode.decrypt(""));
    }

    @Test
    public void testGenerateKey() throws Exception {

        // Arrange
        PasswordEncodeDecode passwordEncodeDecode = new PasswordEncodeDecode();

        // Act
        Key key = PasswordEncodeDecode.generateKey();

        // Assert
        assertNotNull(key);
        assertEquals(ALGORITHM, key.getAlgorithm());
//        assertArrayEquals(new SecretKeySpec(keyValue, ALGORITHM), key.getEncoded());
    }
}

