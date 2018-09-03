package com.weido.engineer.pojo;

public class JsonForScreen {
    private String time;
    private int count;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public JsonForScreen(String time, int count) {
        this.time = time;
        this.count = count;
    }
}
