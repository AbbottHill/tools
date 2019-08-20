package com.cd.tools.file;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

/**
 * @author ChuD
 * @date 2019-06-19 17:30
 */
public class FileUitl {

    @Test
    public void getFileBase64Str() {
        System.out.println(base64Str("C:\\Users\\Administrator\\Desktop\\1.jpg"));
        System.out.println(base64Str("D:\\!\\desktop-bak\\942faf12cfd5a2528ea80694abe96ab7.jpg"));
    }

    public static String base64Str(String filePath) {
        byte[] bytes = new byte[0];
        try (FileInputStream fileInputStream1 = new FileInputStream(filePath)) {
            bytes = new byte[fileInputStream1.available()];
            fileInputStream1.read(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = Base64.getEncoder().encodeToString(bytes);
//        System.out.println(s);
        return s;
    }
}
