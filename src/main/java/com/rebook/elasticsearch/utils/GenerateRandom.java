package com.rebook.elasticsearch.utils;

public class GenerateRandom {

  public static int randomNewsId() {
    int min=1;
    int max=20;
    //Generate random int value from 1 to 25
    System.out.println("Random value in int from "+min+" to "+max+ ":");
    int random_int = (int )(Math.random() * max + min);
    System.out.println(random_int);
    return random_int;
  }

}
