package com.assignments;
import java.io.*;

public class SortString {
    public static void main(String[] argv) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1);
        int n_strings = Integer.parseInt(br.readLine());  // read number of strings
        String [] arr = new String[n_strings];  // create null array with length of n_strings

        // append n_strings strings into null array
        for (int i=0; i<n_strings; i++) {
            String s = br.readLine();
            arr[i] = s;
        }

        // compareToIgnoreCase bubble sort
        String temp = "";
        for (int i=0; i<arr.length; i++) {
            for (int j=1; j<arr.length-i; j++) {
                if (arr[j-1].compareToIgnoreCase(arr[j])>0) {
                    temp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        // print out sorted array
        System.out.println("Sorted Strings:");
        for (String i: arr) {
            System.out.println(i);
        }
    }
}
