package com.example.kris.po.BusinessLogicLayer;

/**
 * singleton of user using app
 */
public class BLLSpeedcuber {
    private static BLLSpeedcuber instance = null;

    protected BLLSpeedcuber(){}

    /**
     *
     * @return instance of singleton, if don't exist method generate new class
     */
    public static BLLSpeedcuber getInstance(){
        if(instance == null){
            instance = new BLLSpeedcuber();
        }
        return instance;
    }

    private String login = null;
    private int id = 1;

    /**
     *
     * @param login of user using app
     */
    public void setUser(String login){
        this.login = login;
        id = 1;
    }

    /**
     *
     * @return login of user using app
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @return id of user in database
     */
    public int getId(){
        return id;
    }
}
