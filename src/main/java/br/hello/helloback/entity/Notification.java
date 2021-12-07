package br.hello.helloback.entity;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.Module.SetupContext;

public class Notification {
    String content;
    String channelName;
    ArrayList<String> usersDomains;

    public Notification() {

    }

    public String getContent() {
        return content;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public ArrayList<String> getUsersDomains() {
        return usersDomains;
    }

    public void setUsersDomains(ArrayList<String> usersDomains) {
        this.usersDomains = usersDomains;
    }

}
