package com.cd.tools.datetime;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ChuD
 * @date 2019-04-28 9:02
 */
public class DateTimeTest {

    @Test
    public void localDateTimeTest() {
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String format1 = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(format);
        System.out.println(format1);
    }

}
