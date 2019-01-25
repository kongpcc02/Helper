/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.exat.helper.System;

import EXAT_Sec.callSecurityServices_EXAT;
import java.net.*;
 

/**
 *
 * @author Siridet
 */
public class Helper {

    private String systemCode = "RVA";
    private String username;
    private String userDept;
    private int loginStatus;
    private String loginResult;
    private String userId;

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void Login(String username, String password) throws UnknownHostException {
        String ip = "";
        InetAddress thisIp = InetAddress.getLocalHost();
        ip = thisIp.getHostAddress();
        callSecurityServices_EXAT login = new callSecurityServices_EXAT();

        login.call_VerifyLogin(username, password, this.systemCode, ip);
        this.loginStatus = login.ResultCode();
        this.loginResult = login.ResultText();
        System.out.println(this.loginResult = login.ResultText());
        this.userId = login.U_ID();
        this.username = login.U_Prefix() + login.U_Fname() + " " + login.U_Lname();
        this.userDept = login.U_DepartText();
    }

    public static void main(String[] args) {
        Helper s;
        try {
            s = new Helper();

            s.Login("1520055252", "01102529");
            System.out.println(s.getLoginResult());
            System.out.println(s.loginStatus);
            System.out.println(s.userDept);
            System.out.println(s.userId);
            System.out.println(s.username);
        } catch (Exception ex) {
        }
    }
}
