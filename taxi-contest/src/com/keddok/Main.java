package com.keddok;

import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        InputStream input = Main.class.getResourceAsStream("/input.txt");
        System.out.println(input);
    }
}
