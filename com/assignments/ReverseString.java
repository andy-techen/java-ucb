package com.assignments;

import java.io.*;

public class ReverseString {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1);
        String s = br.readLine();
        System.out.println(reverse(s));
    }
    static String reverse(String s) {
        if (s.isEmpty()) {
            return s;   // start putting together reversed string
        } else {
            return reverse(s.substring(1)) + s.charAt(0);
        }
    }
}
