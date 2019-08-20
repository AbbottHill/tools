package com.cd.tools.codec;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_512;

/**
 * @author ChuD
 * @date 2019-06-20 10:48
 */
public class ShaTest {

    @Test
    public void md5HexString() throws Exception {
        String hex = DigestUtils.md5Hex("1");
        System.out.println(hex);
    }

    @Test
    public void sha512HexFile() throws Exception {
        File file = new File("C:\\Users\\Administrator\\Desktop\\apache-maven-3.6.1-bin.tar.gz");
        FileInputStream fileInputStream = new FileInputStream(file);
        String hex = DigestUtils.sha512Hex(fileInputStream);
        System.out.println(hex);
    }

    @Test
    public void sha512HexFile_2() throws Exception {
        File file = new File("C:\\Users\\Administrator\\Desktop\\apache-maven-3.6.1-bin.tar.gz");
        String hex = new DigestUtils(SHA_512).digestAsHex(file);
        System.out.println(hex);
    }

}
