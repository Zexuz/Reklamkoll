package com.reklamkoll.server;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

public class Tv {

	public final static String Svt1 = "1:0:1:9BA:B:56:300000:0:0:0:";
	public final static String Svt2 = "1:0:1:BBE:2C:46:E080000:0:0:0:";
	public final static String Tv3 = "1:0:1:44C:3:56:300000:0:0:0:";
	public final static String Tv4 = "1:0:1:642:1B:46:E080000:0:0:0:";
	public final static String Kanal5 = "1:0:1:F28:17:56:300000:0:0:0:";
	public final static String Tv6 = "1:0:1:488:3:56:300000:0:0:0:";

	private BufferedImage img, cropImg;

	private static int logoStartX = 627;
	private static int logoStartY = 38;
	private static int logoEndX = 29;
	private static int logoEndY = 29;

	int x = 0;

	// private boolean debug;

	private int[] pixels;
	private URL url;
	private Rectangle rec;

	public Tv(URL url) throws IOException {
		System.out.println("Tv started");
		this.url = url;
		rec = new Rectangle(29, 29);

		pixels = new int[29 * 29];

	}

	public int[] getCropImagePixels() {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = 0;
		getCropImage().getRGB(0, 0, cropImg.getWidth(), cropImg.getHeight(), pixels, 0, cropImg.getWidth());
		return pixels;
	}

	public BufferedImage getImage() {
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			System.err.println("Error: Can't read image");
			e.printStackTrace();
		}
		return img;
	}

	public BufferedImage getCropImage() {
		cropImg = cropImage(img, rec);
		return cropImg;
	}

	private BufferedImage cropImage(BufferedImage img, Rectangle rec) {
		return getImage().getSubimage(logoStartX, logoStartY, logoEndX, logoEndY);
	}

//	public void writeFiles() {
//		File file = new File("D:/Reklamkoll/Images/logo/logo" + x + ".png");
//		if (x > 10) x = 0;
//		x++;
//		try {
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//
//			BufferedImage bi = getCropImage();
//			ImageIO.write(bi, "png", file);
//
//			// System.out.println("Done");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public static String changeChannel(String channel) {
		channel = "http://192.168.1.68/web/zap?sRef=" + channel;
		String urlParameters = "";
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(channel);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
