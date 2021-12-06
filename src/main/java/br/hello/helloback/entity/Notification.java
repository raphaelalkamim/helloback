package br.hello.helloback.entity;

import java.util.ArrayList;

public class Notification {
    String content;
    String channelName;
    ArrayList <String> users;


    Notification() { 

    }

    public String getContent() {
        return content;
    }

    public String getChannelName() {
        return channelName;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    
}
