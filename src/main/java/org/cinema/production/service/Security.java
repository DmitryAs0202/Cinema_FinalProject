package org.cinema.production.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

public class Security {

  public static final String ALGORITHM = "MD5";

  public static String getHashPassword(String password) {
    String hashValue = "";
    try {
      MessageDigest md = MessageDigest.getInstance(ALGORITHM);
      md.update(password.getBytes());
      byte[] dBytes = md.digest();
      hashValue = DatatypeConverter.printHexBinary(dBytes).toLowerCase();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return hashValue;
  }

}
