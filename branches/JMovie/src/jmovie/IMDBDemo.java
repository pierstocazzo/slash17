package jmovie;

import java.io.*;
import java.net.*;
import java.util.*;

public class IMDBDemo {

	public void main(String[] str) {

		URL url = null;
		Scanner sc = null;
		String apiurl = "http://www.deanclatworthy.com/imdb/";
		String moviename = null;
		String dataurl = null;
		String retdata = null;
		InputStream is = null;
		DataInputStream dis = null;

		try {

			// Getting movie name from user
			sc = new Scanner(System.in);
			moviename = sc.nextLine();

			// Check if user has inputted nothing or blank
			if (moviename == null || moviename.equals("")) {
				System.out.println("No movie found");
				System.exit(1);
			}

			// Remove unwanted space from moviename yb trimming it
			moviename = moviename.trim();

			// Replacing white spaces with + sign as white spaces are not
			// allowed in IMDB api
			moviename = moviename.replace(" ", "+");

			// Forming a complete url ready to send (type parameter can be JSON
			// also)
			dataurl = apiurl + "?q=" + moviename + "&type=text";

			System.out.println("Getting data from service");
			System.out.println("########################################");

			url = new URL(dataurl);

			is = url.openStream();
			dis = new DataInputStream(is);

			String details[];
			// Reading data from url
			while ((retdata = dis.readLine()) != null) {
				// Indicates that movie does not exist in IMDB databse
				if (retdata.equals("error|Film not found")) {
					System.out.println("No such movie found");
					break;
				}

				// Replacing | character with # character for spliting
				retdata = retdata.replace("|", "#");

				// Splitting up string by # character and storing output in
				// details array
				details = retdata.split("#");

				// details[0] contains name of detail. e.g title,genre etc
				System.out.print(details[0].toUpperCase() + " -> ");

				// details[1] contains value of detail. e.g The Cave
				System.out.print(details[1]);
				System.out.println();

			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {

				if (dis != null) {
					dis.close();
				}

				if (is != null) {
					is.close();
				}

				if (sc != null) {
					sc.close();
				}
			} catch (Exception e2) {
				;
			}
		}

	}

}
