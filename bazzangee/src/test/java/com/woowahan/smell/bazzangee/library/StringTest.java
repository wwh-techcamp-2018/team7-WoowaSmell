package com.woowahan.smell.bazzangee.library;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {
    @Test
    public void lastIndexOf() {
        String fileName = "aaa.txt";
        assertThat(fileName.lastIndexOf(".")).isEqualTo(3);
    }

    @Test
    public void substring() {
        String fileName = "aaa.txt";
        assertThat(fileName.substring(fileName.lastIndexOf("."))).isEqualTo(".txt");
    }
}
