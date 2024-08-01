package Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

public class WriteTextUtil {
	
	
	public static void writeInAppendMode(String path, String strFinal) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			try {
				fw = new FileWriter(path, true);
				bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
				out.println(strFinal);
				// out.println("\n");
			} catch (IOException e) {
				System.out.println("Exception found while writing in log file: " + e.getMessage());

			}

		} finally {
			if (bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					System.out.println("Exception found while writing in log file: " + e.getMessage());

				}

			if (fw != null)
				try {
					fw.close();
				} catch (IOException e) {
					System.out.println("Exception found while writing in log file: " + e.getMessage());

				}
		}
	}

	public static void logHeader(String text) {
		try {
			writeInAppendMode(ConstantsPage.fileName, "\t" + text);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void log(String text) {
		try {
			DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("MM/dd/yyyy  hh:mm:ss a");
			writeInAppendMode(ConstantsPage.fileName,
					timeStampPattern.format(java.time.LocalDateTime.now()) + "\t\t" + text);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
