package com.cd.tools.work;

import com.cd.tools.utils.UtilPhoto;
import com.cd.tools.utils.UtilString;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import oracle.sql.BLOB;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author ChuD
 * @date 2019-05-09 10:21
 */
public class DealIdCardPic {

    static Connection connection;
//    static String path = "C:\\Users\\Administrator\\Desktop\\print-采集\\";
//    static String groupNumFile = "C:\\Users\\Administrator\\Desktop\\print-采集\\group_num.txt";
    static String path = "C:\\Users\\Administrator\\Desktop\\print-导入\\";
    static String groupNumFile = "C:\\Users\\Administrator\\Desktop\\print-导入\\group_num.txt";
    static String orgId = "3401";
    static String orgName = "合肥";
    static String photoSource = null;
    static Map<String, String> regionMap = new HashMap();

    static {
        regionMap.put("3401", "合肥");
        regionMap.put("3402", "芜湖");
        regionMap.put("3403", "蚌埠");
        regionMap.put("3404", "淮南");
        regionMap.put("3405", "马鞍山");
        regionMap.put("3406", "淮北");
        regionMap.put("3407", "铜陵");
        regionMap.put("3408", "安庆");
        regionMap.put("3410", "黄山");
        regionMap.put("3411", "滁州");
        regionMap.put("3412", "阜阳");

        regionMap.put("3413", "宿州");
        regionMap.put("3415", "六安");

        regionMap.put("3416", "亳州");

        regionMap.put("3417", "池州");
        regionMap.put("3418", "宣城");


        regionMap.put("3414", "3414");
        regionMap.put("3421", "3421");
        regionMap.put("3422", "3422");
        regionMap.put("3423", "3423");
        regionMap.put("3424", "3424");
        regionMap.put("3425", "3425");
        regionMap.put("3426", "3426");
        regionMap.put("3427", "3427");
        regionMap.put("3428", "3428");
        regionMap.put("3429", "3429");
    }

    @Before
    public void getConnection() throws ClassNotFoundException, SQLException {

        String driver = "oracle.jdbc.OracleDriver";
        /// local test
//        String url = "jdbc:oracle:thin:@192.168.1.240:1530:hdtydb";
//        String user = "ahrxcj";
//        String password = "hdty2018";

        /// test
//        String url = "jdbc:oracle:thin:@172.16.5.72:1530:jmsfzdb";
//        String user = "ahrxcj";
//        String password = "hdty2018";

        /// pro
        String url = "jdbc:oracle:thin:@172.16.5.72:1530:jmsfzdb";
        String user = "rxcjywuser";
        String password = "rxcjyw0018";

        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, password);
    }

    @Test
    public void saveBlobAsJpg() throws Exception {
        Long index = 1000000000L;
//        String sql = "select * from TMP_RXJC_3401_0509 t";
        /*String sql = "select t2.f_add_person, t3.card_num, f_photo,t1.f_yw_primary, t4.f_platform,t4.f_system,t4.f_model from t_ah_rx_photo t1 " +
                " left join t_ah_rxcj  t2 on t1.f_yw_primary = t2.f_id" +
                " left join t_ewt_user t3 on t2.f_add_person = t3.user_id" +
                " left join (select tfpw.f_model,tfpw.f_system,tfpw.f_user_id, tfpw.f_platform from (select max(f_add_time) as add_time, count(*), tpf.f_user_id from t_ah_user_platform tpf" +
                " group by tpf.f_user_id) a1 left join t_ah_user_platform tfpw" +
                " on (a1.add_time = tfpw.f_add_time" +
                " and a1.f_user_id = tfpw.f_user_id)) t4 on t3.user_id = t4.f_user_id" +
                " where  t1.f_add_time >  '20190507000000'" +
                " and t3.card_num like '" + orgId+ "%'";*/
        String sql = "select t.f_gmsfhm, f_photo, t.f_qdcjxh from T_AH_RX_PHOTO t where t.f_add_time >= '20190522131521' and t.f_gmsfhm like '" + orgId + "%' and (t.f_sjly in ('1', '2') or t.f_sjly is null)";
        String sjly = "手机采集";
        if (Objects.equals(photoSource, "2")) {
            sjly = "导入";
            sql = "select t.f_gmsfhm, f_photo, t.f_qdcjxh from T_AH_RX_PHOTO t where t.f_add_time >= '20190522131521' and t.f_gmsfhm like '" + orgId + "%' and t.f_sjly not in ('1', '2')";
        }
        if (Objects.equals(photoSource, "3")) {
            sjly = "人工审核不通过";
            sql = "select t.f_gmsfhm, f_photo, t.f_qdcjxh, t.f_sjly from T_AH_RX_PHOTO t left join t_ah_rxcj t1 on t.f_yw_primary = t1.f_id  where t1.f_pass_state = '3' and t.f_gmsfhm like '" + orgId + "%' and t.f_add_time >= '20190606120000' ";
        }
        if (Objects.equals(photoSource, "4")) {
            sjly = "人工审核通过";
            sql = "select t.f_gmsfhm, f_photo, t.f_qdcjxh, t.f_sjly from T_AH_RX_PHOTO t left join t_ah_rxcj t1 on t.f_yw_primary = t1.f_id  where t1.f_pass_state = '4' and t.f_gmsfhm like '" + orgId + "%' and t.f_add_time >= '20190606120000' ";
        }
        System.out.println("sql： " + sql + "\n");
        FileOutputStream logOut = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\tools-log.txt", true);
        logOut.write((sql +  "\n").getBytes());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            BLOB f_photo = (BLOB) resultSet.getBlob("f_photo");
            byte[] bytes = UtilPhoto.GetBytesByBlob(f_photo);

//            File file1 = new File("C:\\Users\\Administrator\\Desktop\\" + index + ".jpg");
//            FileOutputStream fileOutputStream1 = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\" + index + ".jpg");
//            fileOutputStream1.write(bytes);

//            String f_system = resultSet.getString("F_SYSTEM");
            String f_phone = resultSet.getString("F_QDCJXH") == null? " ": resultSet.getString("F_QDCJXH");
            String F_SJLY = resultSet.getString("F_SJLY");

            String sjlyTmp;
            if (Objects.equals(F_SJLY, "1") || Objects.equals(F_SJLY, "2") || UtilString.isNullOrEmptyString(F_SJLY)) {
                sjlyTmp = sjly + "(手机采集)";
            } else {
                sjlyTmp = sjly + "(导入)";
            }

            String orgPath = path + orgId + File.separator;
            File outFile = new File(orgPath);
            if (!outFile.exists()) {
                outFile.mkdirs();
            }
            String replaceAll = f_phone.replaceAll("Android", "安卓").replaceAll("HUAWEI", "华为").replaceAll(":", " ");
            String jpgName = orgPath + (index + "_" + orgName + "_" + replaceAll + "_" + sjlyTmp + "_.jpg");
            File file = new File(jpgName);
            try (FileOutputStream fileOutputStream = new FileOutputStream(jpgName)) {
                fileOutputStream.write(bytes);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            index ++;
        }
    }

    @Test
    public void saveJpgNameAsTxt() {
        String lineStr = "01^0142756201^陈陕徽^男^汉^1983^4^1^安徽省宿州市埇桥区永镇^号号^^342201198304019311^0142756201^陈陕徽^0142756201^宿州市公安局埇桥分局^2019.03.14-2039.03.14^0142756201^陈陕徽^\n";
        String orgPath = path + File.separator + orgId + File.separator;
        File file = new File(orgPath);
        File[] files = file.listFiles();
        String txtName = path + File.separator + orgId + File.separator  + "100000.txt";
        try (FileOutputStream txtFileOutputStream = new FileOutputStream(txtName, true)) {
            for (int i = 0; i < files.length; i++) {
                File tmpFile = files[i];
                String jpgName = tmpFile.getName();
                String[] split = jpgName.split("_");
                if (split.length == 4) {
                    String index = split[0];
                    String resultLine = lineStr.replaceAll("0142756201", index + "");
                    resultLine = resultLine.replaceAll("陈陕徽", orgName);
                    resultLine = resultLine.replaceAll("安徽省宿州市埇桥区永镇",  split[2]);
                    if (Objects.equals(photoSource, "2")) {
                        resultLine = resultLine.replaceAll("号号", "导入");
                    } else if (Objects.equals(photoSource, "1")) {
                        resultLine = resultLine.replaceAll("号号", "手机采集");
                    } else if (Objects.equals(photoSource, "3")) {
                        resultLine = resultLine.replaceAll("号号", "人工审核不通过");
                    } else if (Objects.equals(photoSource, "4")) {
                        resultLine = resultLine.replaceAll("号号", "人工审核通过");
                    }
                    tmpFile.renameTo(new File(orgPath + index + ".jpg"));
                    txtFileOutputStream.write(resultLine.getBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveJpgNameAsTxt_new() {
        String groupNum = null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(groupNumFile), "utf-8"))) {
            groupNum = bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long groupNumLong = Long.parseLong(groupNum);

        String orgPath = path + File.separator + orgId + File.separator;
        File file = new File(orgPath);
        File[] files = file.listFiles();
        try {
            ArrayList<File> tmpFileList = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                File tmpFile = files[i];
                String jpgName = tmpFile.getName();
                String[] split = jpgName.split("_");
                if (split.length == 5) {
                    tmpFileList.add(tmpFile);
                    if ((i + 1) % 80 == 0 && i != 0) {
                        deal80File(groupNumLong, tmpFileList);
                        groupNumLong++;
                        tmpFileList = new ArrayList();
                    }
                }
            }
            if (tmpFileList.size() > 0) {
                deal80File(groupNumLong, tmpFileList);
                groupNumLong++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(groupNumFile)))) {
            bufferedWriter.write(String.valueOf(buLing8(String.valueOf(groupNumLong))));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void deal80File(Long groupNumLong, List<File> list) {
//        String groupPath = path + File.separator + orgId + File.separator + buLing8(String.valueOf(groupNumLong));
        String groupPath = "C:\\Users\\Administrator\\Desktop\\printout" + File.separator + buLing8(String.valueOf(groupNumLong));
        String lineStr = "01^0142756201^陈陕徽^男^汉^1983^4^1^安徽省宿州市埇桥区永镇^号号^^342201198304019311^0142756201^陈陕徽^0142756201^宿州市公安局埇桥分局^2019.03.14-2039.03.14^0142756201^陈陕徽^\n";
        File groupFile = new File(groupPath);
        if (!groupFile.exists()) {
            groupFile.mkdirs();
        }
        String txtName = groupPath + File.separator  + buLing8(String.valueOf(groupNumLong)) + ".txt";
        try (FileOutputStream txtFileOutputStream = new FileOutputStream(txtName, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(txtFileOutputStream, "UTF-8");
        /*BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)*/) {
            for (int i = 0; i < list.size(); i++) {
                File tmpFile = list.get(i);
                String jpgName = tmpFile.getName();
                String[] split = jpgName.split("_");
                if (split.length == 5) {
                    File dest = new File(groupPath + File.separator + buLing8(String.valueOf(groupNumLong)) + buLing2(String.valueOf(i + 1)) + ".jpg");
                    String index = split[0];
                    String resultLine = lineStr.replaceAll("0142756201", buLing8(String.valueOf(groupNumLong)) + buLing2(String.valueOf(i + 1)) + "");
                    resultLine = resultLine.replaceAll("陈陕徽", orgName);
                    resultLine = resultLine.replaceAll("安徽省宿州市埇桥区永镇", split[2]);
//                    if (Objects.equals(photoSource, "2")) {
                        resultLine = resultLine.replaceAll("号号", split[3]);
//                    } else {
//                        resultLine = resultLine.replaceAll("号号", "手机采集");
//                    }
    //                    tmpFile.renameTo(new File(orgPath + index + ".jpg"));
                    FileUtils.copyFile(tmpFile, dest);
                    txtFileOutputStream.write(resultLine.getBytes());
//                    bufferedWriter.write(resultLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @After
    public void releaseResource() throws SQLException {
        connection.close();
    }

    public static void main(String[] args) throws Exception {
        DealIdCardPic dealIdCardPic = new DealIdCardPic();

        /// saveBlobAsJpg
//        args = new String[]{"4"};
        if (args.length < 1) {
            System.out.println("手机采集 eg: 1");
            System.out.println("导入 eg: 2 ");
            System.out.println("人工审核不通过 eg: 3 ");
            System.out.println("人工审核通过 eg: 4 ");
            return;
        }
        photoSource = args[0];
        dealIdCardPic.getConnection();
        for (int i = 1; i < 30; i++) {
            orgId = "34" + buLing2(String.valueOf(i));
            orgName = String.valueOf(regionMap.get(orgId));
            if (orgName != null) {
                dealIdCardPic.saveBlobAsJpg();
            }
        }
        dealIdCardPic.releaseResource();
        /// saveJpgNameToTxt
/*        for (int i = 1; i < 30; i++) {
            orgId = "34" + buLing2(String.valueOf(i));
            dealIdCardPic.saveJpgNameAsTxt_new();
        }*/

    }

    private static String buLing8(String num) {
        int length = num.length();
        String a = "00000000";
        String substring = a.substring(0, 8 - length);
        return substring + num;
    }

    private static String buLing2(String num) {
        int length = num.length();
        String a = "00";
        String substring = a.substring(0, 2 - length);
        return substring + num;
    }

}
