package com.tdt.kioskws.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hash util
 */
public class HashUtil {

    public static String hash(String althm, String content) throws NoSuchAlgorithmException {

        try {

            MessageDigest md = MessageDigest.getInstance(althm);
            md.update(content.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NullPointerException e) {

            return null;
        }

    }

}
