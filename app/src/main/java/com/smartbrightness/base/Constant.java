package com.smartbrightness.base;

import com.smartbrightness.BRApplication;

/**
 * Created by MMM on 2017/9/1.
 */
public class Constant {

    public static String ip = BRApplication.ip;
    public static String port = BRApplication.port;

    public static String URL = "http://" + ip + ":" + port + "/governance_restful/service/";//服务器地址

    public static void setURL(String ipStr, String portStr) {

        URL = "http://" + ipStr + ":" + portStr + "/governance_restful/service/";
    }
}
