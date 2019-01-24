package com.example.kris.po.BusinessLogicLayer;

public class BLLSpeedcuber {
    private static BLLSpeedcuber instance = null;

    protected BLLSpeedcuber(){}
    public static BLLSpeedcuber getInstance(){
        if(instance == null){
            instance = new BLLSpeedcuber();
        }
        return instance;
    }

    private String login = null;
    private int id = 1;

    public void setUser(String login){
        this.login = login;
        id = 1;
    }

    public String getLogin() {
        return login;
    }

    public int getId(){
        return id;
    }
}
