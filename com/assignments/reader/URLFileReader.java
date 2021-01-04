package com.assignments.reader;

import java.io.*;
import java.net.URL;

public class URLFileReader {
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.out.println("Usage: java.exe URLFileReader <URL or filepath> <lines>");
			System.out.println("URL: Web address. Format: https://...");
			System.out.println("filepath: Relative or absolute file path. Format: com/assignments/reader/test.txt");
			System.out.println("lines: Prints first n lines if positive, last n lines if negative, all lines by default. Format: integer n");
			return;
		}

		final String file_loc = args[0];

		int file_lines = 0;
		if (args.length > 1) {
			String file_lines_str = args[1];
			try {
				file_lines = Integer.valueOf(file_lines_str);
			} catch (NumberFormatException e) {
				System.out.println("Number of lines must be an integer.");
				return;
			}
		}

		String read_line;
		BufferedReader br = get_br(file_loc);
		if (file_lines == 0) {
			System.out.println("Printing all lines...");
			while ((read_line = br.readLine()) != null) {
				System.out.println(read_line);
			}
		} else if (file_lines > 0) {
			System.out.println("Printing first " + file_lines + " lines...");
			while ((read_line = br.readLine()) != null && file_lines > 0) {
				System.out.println(read_line);
				--file_lines;
			}
		} else {
			int total_lines = get_lines(file_loc);
			int start_line = total_lines + file_lines;
			if (start_line < 0) {
				System.out.println("File only has " + total_lines + " lines.");
				return;
			}
			System.out.println("Printing from line #" + (start_line + 1) + "...");
			for (int i=0; i<start_line; i++) {
				br.readLine();
			}
			while ((read_line = br.readLine()) != null) {
				System.out.println(read_line);
			}
		}
		br.close();
	}

	// select appropriate reader
	public static BufferedReader get_br (String file_str) throws IOException {
		if (file_str.contains("https")) {
			return url_br(file_str);
		}
		return path_br(file_str);
	}
	// reader for url links
	public static BufferedReader url_br (String file_url) throws IOException {
		URL url = new URL(file_url);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		return br;
	}
	// reader for filepath
	public static BufferedReader path_br (String file_path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file_path));
		return br;
	}
	// get total lines of file
	public static int get_lines (String file_str) throws IOException {
		int lines = 0;
		BufferedReader br = get_br(file_str);
		while (br.readLine() != null) {
			lines++;
		}
		return lines;
	}
}
