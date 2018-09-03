package com.weido.engineer.pojo;

public class RemindMsgSocketBean {


    /**
     * wstype : info
     * type : HeartBeat
     * time : 2018-08-24 18:43:12
     * version : 1.0
     * from : HeartBeat
     * fname : HeartBeat
     * to : all
     * tname : all
     * info : {"type":"HeartBeat","uid":"all","name":"all","content":"2018-08-24 18:43:12","eid":1,"oid":"4"}
     */

    private String wstype;
    private String type;
    private String time;
    private String version;
    private String from;
    private String fname;
    private String to;
    private String tname;
    private InfoBean info;

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

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * type : HeartBeat
         * uid : all
         * name : all
         * content : 2018-08-24 18:43:12
         * eid : 1
         * oid : 4
         */

        private String type;
        private String uid;
        private String name;
        private String content;
        private int eid;
        private String oid;
        private String service_stationid;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getEid() {
            return eid;
        }

        public void setEid(int eid) {
            this.eid = eid;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getService_stationid() {
            return service_stationid;
        }

        public void setService_stationid(String service_stationid) {
            this.service_stationid = service_stationid;
        }
    }
}
