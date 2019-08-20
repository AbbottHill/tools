package com.cd;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.cd.tools.file.DirectoryUtil;
//import com.cd.tools.path.PathUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.URL;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//import java.util.Objects;
//import java.util.function.Consumer;
//import java.util.regex.Pattern;

//@Slf4j
public class Main {

//    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static String oldSuffix;
    public static String newSuffix;

//    public static void main(String[] args) {
//        String operations = "operation: override delete 0，rename 1, ";
//        String path = PathUtil.getProjectPath();
//        // debugger: ...\target\classes
//        // jar: 存放jar 的文件夹
//
//        if (args.length < 1) {
//            throw new IllegalArgumentException(String.format("One argument at least, %s", operations));
//        }
//        String argsStr = JSON.toJSONString(args);
//        System.out.println(argsStr);
////        log.info(argsStr);
//
//        String operation = args[0];
//        if (Objects.equals("0", operation)) {
//            File file = new File(path);
//            DirectoryUtil.retrievalFileDeleteFile(file);
//        } else if (Objects.equals("1", operation)) {
//            if (args.length != 3) {
//                throw new IllegalArgumentException("3 arguments required (operation, old suffix, new suffix)");
//            }
//            for (int i = 1; i < args.length; i++) {
//                if (Pattern.matches("[\\W.]+", args[i])) {
//                    throw new IllegalArgumentException(String.format("%s arguments illegal: %s", i, args[i]));
//                }
//            }
//            System.out.println(String.format("oldSuffix: %s, newSuffix: %s", args[0], args[1]));
//            oldSuffix = args[1];
//            newSuffix = args[2];
//
//            System.out.println(path);
//            File file = new File(path);
//            DirectoryUtil.retrievalFile(file);
//        } else {
//            throw new IllegalArgumentException(operations);
//        }
//
//        try (FileOutputStream fileOutputStream = new FileOutputStream(path + "/log.txt", true)) {
//            fileOutputStream.write((LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) +
//            String.format(" args: %s", argsStr)).getBytes());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        System.out.println("select t2.f_add_person, t3.card_num, f_photo,t1.f_yw_primary, t4.f_platform,t4.f_system,t4.f_model from t_ah_rx_photo t1 " +
                " left join t_ah_rxcj  t2 on t1.f_yw_primary = t2.f_id" +
                " left join t_ewt_user t3 on t2.f_add_person = t3.user_id" +
                " left join (select tfpw.f_model,tfpw.f_system,tfpw.f_user_id, tfpw.f_platform from (select max(f_add_time) as add_time, count(*), tpf.f_user_id from t_ah_user_platform tpf" +
                " group by tpf.f_user_id) a1 left join t_ah_user_platform tfpw" +
                " on (a1.add_time = tfpw.f_add_time" +
                " and a1.f_user_id = tfpw.f_user_id)) t4 on t3.user_id = t4.f_user_id" +
                " where  t1.f_add_time >  '20190507000000'" +
                " and t3.card_num like '34%'");
    }

}
