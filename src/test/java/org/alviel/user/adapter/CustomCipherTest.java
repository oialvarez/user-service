package org.alviel.user.adapter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomCipherTest {

    @Test
    void givenEncrypt_whenGood_shouldSucceed() {
        CustomCipher cipher = new CustomCipher("abcde67890");
        String encrypted = cipher.encrypt("lala");

        assertThat(encrypted).isEqualTo("IXsvYmexZWc=");
    }
}