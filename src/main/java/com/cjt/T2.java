package com.cjt;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class T2 {

  public static void main(String[] args) {

    String arg0 = "电话: 7155175";
    String phone2 = findPhone2(arg0);
    System.out.println(phone2);
//    String replace = arg0.replace("电话: ", "");
//    System.out.println(replace);

  }

  public static String findPhone(String str) {
    Pattern p = Pattern.compile("\\d+");
    Matcher m = p.matcher(str);
    String result = "";
    if(m.find()){
      result = m.group(0);
    }
    System.out.println(result);
    System.out.println(result.length());
    return result;
  }

  public static String findPhone2(String str) {
    Pattern p = Pattern.compile("\\d+");
    Matcher m = p.matcher(str);
    String phone = "";
    if(m.find()){
      phone = m.group(0);
    }
    if (  phone.length() == 11) {
      return phone;
    } else {
      phone = str.replace("电话: ", "");
      phone = phone.trim();
    }
    return phone;
  }

}