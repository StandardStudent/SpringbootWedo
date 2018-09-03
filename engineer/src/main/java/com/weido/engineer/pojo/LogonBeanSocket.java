package com.weido.engineer.pojo;

import java.io.Serializable;

public class LogonBeanSocket implements Serializable{

    /**
     * wstype : logon
     * type : logon
     * time : 2018-07-07 05:53:56
     * version : 1.0
     * from : 15701263175
     * fname : 15701263175
     * to : logon
     * tname : logon
     * logon : {"name":"abc","type":"add","uid":"15701263175","token":"system"}
     */

    private String wstype;
    private String type;
    private String time;
    private String version;
    private String from;
    private String fname;
    private String to;
    private String tname;
    private LogonBean logon;

    public String getWstype() {
        return wstype;
    }

    public void setWstype(String wstype) {
        this.wstype = wstype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public LogonBean getLogon() {
        return logon;
    }

    public void setLogon(LogonBean logon) {
        this.logon = logon;
    }

    public static class LogonBean {
        /**
         * name : abc
         * type : logon
         * uid : 15701263175
         * token : system
         */

        private String name;
        private String type;
        private String uid;
        private String token;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
