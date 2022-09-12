package com.mindhub.homebanking.utilities;

public final class CardUtils{
  private CardUtils() {
  }
  public static int getCvvNumber() {
    return (int) ((Math.random() * (999 - 100)) + 100);
  }
  public static String getCardNumber() {
    return (int)((Math.random() * (9999 - 1000)) + 1000)
            + "-" + (int)((Math.random() * (9999 - 1000)) + 1000)
            + "-" + (int)((Math.random() * (9999 - 1000)) + 1000)
            + "-" + (int)((Math.random() * (9999 - 1000)) + 1000);
  }
}
