package com.reklamkoll.server;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ScannedPoint{

	private int x, y, width, height, number,temp = 0;
	private int[] pixels;

	public BufferedImage image;

	public ScannedPoint(int x, int y, int width, int height, int number) {
		System.out.println("ScannedPoint  " + number + " started");
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.number = number;

		pixels = new int[width * height];
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	private void cropImage() {
		image = image.getSubimage(x, y, width, height);
	}

	public int[] getPixels() {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = 0;
		pixels = getImage().getRGB(0, 0, width, height, null, 0, width);
		return pixels;
	}

	public void tick(BufferedImage image) {
		setImage(image);
		cropImage();
//			saveImage();
	}

	public int[] getPixelColors() {
		pixels = getPixels();
		Color c;
		int[] res = new int[3];

		for (int i = 0; i < pixels.length; ++i) {
			c = new Color(pixels[i]);
			res[0] += c.getRed();
			res[1] += c.getGreen();
			res[2] += c.getBlue();
		}

		res = check(res);

		res[0] /= 9;
		res[1] /= 9;
		res[2] /= 9;

		// if (number != 10 || number != 11) {
		// System.out.print(number);
		// }
		// System.out.print(" Red : " + res[0]);
		// System.out.print(" Green : " + res[1]);
		// System.out.print(" Blue : " + res[2]);
		//
		// System.out.println();

		return res;
	}

	private int[] check(int[] res) {
		if (number == 10) {
			res[0] += (int) (res[0] * 1.5);
			res[1] += (int) (res[1] * 1.5);
			res[2] += (int) (res[2] * 1.5);
		}
		if (number == 11) {
			res[0] += res[0] / 8;
			res[1] += res[1] / 8;
			res[2] += res[2] / 8;
		}
		return res;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

//	private void saveImage() {
//		File file = new File("D:/Reklamkoll/Images/Scanned Point " + number + "/ScannedPoint" + temp + ".png");
//		if (temp > 10) temp = 0;
//		temp++;
//		try {
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//
//			BufferedImage bi = getImage();
//			ImageIO.write(bi, "png", file);
//
//			// System.out.println("Done");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
