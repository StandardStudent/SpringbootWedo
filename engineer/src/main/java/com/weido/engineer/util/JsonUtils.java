package com.weido.engineer.util;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.weido.engineer.pojo.LogonBeanSocket;


public class JsonUtils {
    /*登录信息*/
    public static String getLoginSocket(String from) {
        String toString = null;
        LogonBeanSocket logonBeanSocket = new LogonBeanSocket();
        LogonBeanSocket.LogonBean logonBean =new LogonBeanSocket.LogonBean();
        logonBeanSocket.setWstype("logon");
        logonBeanSocket.setType("logon");
        logonBeanSocket.setTime("2018年8月9日14:51:37");
        logonBeanSocket.setVersion("1.0");
        logonBeanSocket.setFrom(from);
        logonBeanSocket.setFname(from);
        logonBeanSocket.setTo("logon");
        logonBeanSocket.setTname("logon");

        logonBean.setName(from);
        logonBean.setType("add");
        logonBean.setUid(from);
        logonBean.setToken("system");
        logonBeanSocket.setLogon(logonBean);
        try {
            toString = new Gson().toJson(logonBeanSocket);
        }catch (com.google.gson.JsonParseException e){
         e.printStackTrace();
        }
        return toString;
    }
    /*退出信息*/
    public static String getLogoutSocket(String from) {
        String toString = null;
        LogonBeanSocket logonBeanSocket = new LogonBeanSocket();
        LogonBeanSocket.LogonBean logonBean =new LogonBeanSocket.LogonBean();
        logonBeanSocket.setWstype("logout");
        logonBeanSocket.setType("logout");
        logonBeanSocket.setTime("2018年8月9日14:51:37");
        logonBeanSocket.setVersion("1.0");
        logonBeanSocket.setFrom(from);
        logonBeanSocket.setFname(from);
        logonBeanSocket.setTo("logout");
        logonBeanSocket.setTname("logout");

        logonBean.setName(from);
        logonBean.setType("logout");
        logonBean.setUid(from);
        logonBean.setToken("system");
        logonBeanSocket.setLogon(logonBean);
        try {
            toString = new Gson().toJson(logonBeanSocket);
        }catch (JsonParseException e){
            e.printStackTrace();
        }
        return toString;
    }
}
