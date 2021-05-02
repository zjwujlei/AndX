package com.profound.andx.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;


public class CoverUtils {

    public static String accountCover(String account){
        String email = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        String phone = "^(?=\\d{11}$)^1(?:3\\d|4[57]|5[^4\\D]|66|7[^249\\D]|8\\d|9[89])\\d{8}$";

        String result = "";
        if (TextUtils.isEmpty(account)){
            return result;
        }

        if (Pattern.compile(email).matcher(account).matches()){
            //邮箱账号
            result =  emailCover(account);
        }else if (Pattern.compile(phone).matcher(account).matches()){
            //大陆手机号
            result = phoneCover(account);
        }else{
            result = commonCover(account);
        }

        return result;
    }


    /**
     * 一般脱敏规则
     */
    public static String commonCover(String account){
        if (account.length()<3){
            return account+"***";
        }

        int remainder = account.length()%3;
        if (remainder == 0){
            return account.substring(0,account.length()/3)+"***"+account.substring(account.length()-account.length()/3,account.length());
        }else{
            return account.substring(0,account.length()/3+1)+"***"+account.substring(account.length()-account.length()/3,account.length());
        }
    }


    public static String emailCover(String account){
        String head = account.substring(0,account.indexOf("@"));
        String end = account.substring(account.indexOf("@"),account.length());
        if (head.length()<3){
            return head+"***"+end;
        }else {
            return head.substring(0,3)+"***"+end;
        }
    }

    public static String phoneCover(String account){
        return account.substring(0,3)+"******"+account.substring(account.length()-2,account.length());
    }

}

