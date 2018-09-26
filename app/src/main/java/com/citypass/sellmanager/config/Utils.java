package com.citypass.sellmanager.config;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 赵涛 on 2018/9/25.
 */
public class Utils {
    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String s = plainText + "Y2l0eXBhc3M=";
            md.update(s.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            String TemStr = buf.toString().toUpperCase();
            Log.d("md5", s + "  " + TemStr);
            return TemStr;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
