/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.controllers;

import java.util.HashMap;
import miu.xphotocollage.controllers.util.MIUControllerModel;
import miu.xphotocollage.models.MiuXphcStartupModel;

/**
 *
 * @author Ndadji Maxime
 */
public class MiuXphcStartupController extends MIUControllerModel{

    public MiuXphcStartupController(MiuXphcStartupModel model) {
        super(model);
    }

    public void startupLoading() {
        ((MiuXphcStartupModel)model).startupLoading();
    }
    
    public static String controlUserData(HashMap<String, String> userData){
        if(userData.get("username") == null || userData.get("username").length() == 0)
            return "null_name";
        if(userData.get("login") == null || userData.get("login").length() == 0)
            return "null_login";
        if(userData.get("login").length() < 4)
            return "short_login";
        if(userData.get("login").length() > 50)
            return "long_login";
        if(userData.get("password") == null || userData.get("password").length() == 0)
            return "null_password";
        if(userData.get("usersign") != null && userData.get("usersign").length() > 250)
            return "long_sign";
        if(userData.get("prevpassword") != null && userData.get("prevpassword").length() == 0)
            return "null_password";
        return null;
    }
}
