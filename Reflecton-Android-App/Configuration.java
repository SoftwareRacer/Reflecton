package reflecton.reflecton_android_app;

//
// HomeViewController.java
// Reflecton
//
// Created by Marco Hennermann on 19.12.17
// Copyright © 2017 Marco Hennermann. All rights reserved.
//

public class Configuration {

    /*  static String hostName = "jdbc:mysql://db702720703.db.1and1.com";
          static String username = "dbo702720703";
          static String password = "Seng_ma_donn_eh!";
          static String dataBaseName = "db702720703";
          static String tableName = "users";

          static String hostName = "jdbc:mysql://192.168.0.11";
          static String username = "test";
          static String password = "test";
          static String dataBaseName = "androidtest";
          static String tableName = "TEST";*/
    static String LoginScriptURL ="http://heatcontrol.at/androidlogin.php";
    static String SignupScriptURL="http://heatcontrol.at/reg-android.php";
    static String GETDataURL = "http://heatcontrol.at/Getdata.php";
    static String POSTDataURL = "http://heatcontrol.at/PostData.php";


    public static String getGETDataURL() {
        return GETDataURL;
    }

    public static void setGETDataURL(String GETDataURL) {
        Configuration.GETDataURL = GETDataURL;
    }

    public static String getPOSTDataURL() {
        return POSTDataURL;
    }

    public static void setPOSTDataURL(String POSTDataURL) {
        Configuration.POSTDataURL = POSTDataURL;
    }

    //static String LoginScriptURL ="http://192.168.43.143/test/index.php";
    //static String SignupScriptURL="http://192.168.43.143/test/index.php";
    //static String DataScriptURL = "http://192.168.43.143/DataScript";

    public static String getLoginScriptURL()
    {
        return LoginScriptURL;
    }

    public static void setLoginScriptURL(String loginScriptURL)
    {
        LoginScriptURL = loginScriptURL;
    }

    public static String getSignupScriptURL()
    {
        return SignupScriptURL;
    }

    public static void setSignupScriptURL(String signupScriptURL) {SignupScriptURL = signupScriptURL;}
}

