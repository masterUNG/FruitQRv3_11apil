package kiky.beam.lilly.th.ac.rmutk.fruitqr;

import java.lang.ref.SoftReference;

public class Myconstant {

    private  String[] favoriteFruits = {"โปรดเลือกชื่อผลไม้","ส้ม","มะละกอ", "แตงโม","ทุเรียน"};
    private  String[] units = {"กิโลกรัม","ผล","ลัง"};

    private String nameFileSharePreference = "Fruit";

    private String urlAddDetailFramer = "http://www.androidthai.in.th/rmutk/addDetailFramerLilly.php";
    private String urlAddUser = "https://www.androidthai.in.th/rmutk/addDataLilly.php";
    private String urlGetAllData = "https://www.androidthai.in.th/rmutk/getAllDatalilly.php";
    private String urlGetDataWhereQR = "https://www.androidthai.in.th/rmutk/getDetailWhereQRmaster.php";
    private String urlGetUserWhereId = "https://www.androidthai.in.th/rmutk/getUserWhereId.php";
    private String urlGetAllDetail = "https://www.androidthai.in.th/rmutk/getDetail.php";
    private String urlGetDetailWhereIdUser = "https://www.androidthai.in.th/rmutk/getDetailWhereIdUser.php";

    private String urlGetDetailFramerWhereIdRecord = "http://www.androidthai.in.th/rmutk/getDetailFramerWhereIdRecordLilly.php"; //ดูได้แค่ผู้ผลิค
    private String urlGetAllDetailFramer = "http://www.androidthai.in.th/rmutk/getAllDetaiFramerLilly.php"; //Admin ดูได้ทั้งหมด


    public String getUrlGetDetailFramerWhereIdRecord() {
        return urlGetDetailFramerWhereIdRecord;
    }

    public String getUrlGetAllDetailFramer() {
        return urlGetAllDetailFramer;
    }

    public String getUrlAddDetailFramer() {
        return urlAddDetailFramer;
    }

    public String[] getFavoriteFruits() {
        return favoriteFruits;
    }

    public String[] getUnits() {
        return units;
    }

    public String getNameFileSharePreference() {
        return nameFileSharePreference;
    }

    public String getUrlGetDetailWhereIdUser() {
        return urlGetDetailWhereIdUser;
    }

    public String getUrlGetAllDetail() {
        return urlGetAllDetail;
    }

    public String getUrlGetUserWhereId() {
        return urlGetUserWhereId;
    }

    public String getUrlGetDataWhereQR() {
        return urlGetDataWhereQR;
    }

    public String getUrlGetAllData() {
        return urlGetAllData;
    }

    public String getUrlAddUser() {
        return urlAddUser;
    }
}
