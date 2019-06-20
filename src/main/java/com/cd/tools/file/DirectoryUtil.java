package com.cd.tools.file;

import com.cd.Main;

import java.io.*;

public class DirectoryUtil {

    /**
     * rename file
     * @param file
     */
    public static void retrievalFile(File file) {
        String path = file.getPath();
        System.out.println(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File tmpFile = files[i];
                retrievalFile(tmpFile);
            }
        } else {
            String name = file.getName();

            String suffix = "." + Main.oldSuffix;
            if (name.endsWith(suffix)) {
                String newPath = path.replace(suffix, "." + Main.newSuffix);
                try (FileOutputStream fileOutputStream = new FileOutputStream(newPath);
                     FileInputStream fileInputStream = new FileInputStream(file)) {
                    byte[] bytes = new byte[fileInputStream.available()];
                    fileInputStream.read(bytes);
                    fileOutputStream.write(bytes);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                file.delete();
            }
        }
    }

    /**
     * delete file by override empty file
     * @param file
     */
    public static void retrievalFileDeleteFile(File file) {
        String path = file.getPath();
        System.out.println(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File tmpFile = files[i];
                retrievalFileDeleteFile(tmpFile);
            }
        } else {
            try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
                byte[] bytes = new byte[0];
                fileOutputStream.write(bytes);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
