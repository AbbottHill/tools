package com.cd.tools.path;

import com.cd.Main;

import java.io.File;
import java.net.URL;

public class PathUtil {
    /**
     * 获取项目所在路径(包括jar)
     *
     * @return
     */
    public static String getProjectPath() {
        URL url = Main.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println("getLocation -> " + url + "; url.getPath ->" + url.getPath());// D:/IdeaWorkSpace/web-trunk/projects/test-tools/target/classes/
        String filePath = null;
        try {
            // 如果路径包含Unicode字符（汉字），那么还需要使用 java.net.URLDecoder.decode(path, “UTF-8″) 方法进行转码
            filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (filePath.endsWith(".jar"))
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        File file = new File(filePath);
        filePath = file.getAbsolutePath();
        return filePath;
    }
}
