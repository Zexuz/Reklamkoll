package com.reklamkoll.server;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TvChannel {
	private final int SCALE = 12;

	private ScannedPoint scannedp1; // Top Left Corner
	private ScannedPoint scannedp2; // Top Left Corner Check
	private ScannedPoint scannedp3; // Top Right Corner
	private ScannedPoint scannedp4; // Top Right Corner Check
	private ScannedPoint scannedp5; // Bottom Left Corner
	private ScannedPoint scannedp6; // Bottom Left Corner Check
	private ScannedPoint scannedp7; // Bottom Right Corner
	private ScannedPoint scannedp8; // Bottom Right Corner Check
	private ScannedPoint scannedp9; // Middle logo check
	private ScannedPoint scannedp10; // Middle logo middle

	private ArrayList<ScannedPoint> sp = new ArrayList<>();
	private boolean[] checkPoints;

	private String channel;
	
	public TvChannel(String str) {
		channel = str;
		temp();
	}

	private void temp() {
		if (channel == "tv4") {
			scannedp1 = new ScannedPoint(0, 0, 3, 3, 1);
			scannedp2 = new ScannedPoint(6, 6, 3, 3, 2);
			scannedp3 = new ScannedPoint(26, 0, 3, 3, 3);
			scannedp4 = new ScannedPoint(21, 4, 3, 3, 4);
			scannedp5 = new ScannedPoint(0, 26, 3, 3, 5);
			scannedp6 = new ScannedPoint(8, 23, 3, 3, 6);
			scannedp7 = new ScannedPoint(26, 26, 3, 3, 7);
			scannedp8 = new ScannedPoint(21, 22, 3, 3, 8);
			scannedp9 = new ScannedPoint(13, 17, 3, 2, 9);
			scannedp10 = new ScannedPoint(17, 15, 3, 2, 10);
		}

	}

	public void paint(Graphics g) {
		 if (checkPoints[0] == true) g.setColor(Color.red);
				 else
				 g.setColor(Color.green);
				
				 g.fillRect((scannedp1.getX() * SCALE) + 720 + 5,
				 scannedp1.getY() * SCALE, scannedp1.getWidth() * SCALE,
				 scannedp1.getHeight() * SCALE);
				 g.fillRect((scannedp2.getX() * SCALE) + 720 + 5,
				 scannedp2.getY() * SCALE, scannedp2.getWidth() * SCALE,
				 scannedp2.getHeight() * SCALE);
				
				 if (checkPoints[1] == true) g.setColor(Color.red);
				 else
				 g.setColor(Color.green);
				 g.fillRect((scannedp3.getX() * SCALE) + 720 + 5,
				 scannedp3.getY() * SCALE, scannedp3.getWidth() * SCALE,
				 scannedp3.getHeight() * SCALE);
				 g.fillRect((scannedp4.getX() * SCALE) + 720 + 5,
				 scannedp4.getY() * SCALE, scannedp4.getWidth() * SCALE,
				 scannedp4.getHeight() * SCALE);
				
				 if (checkPoints[2] == true) g.setColor(Color.red);
				 else
				 g.setColor(Color.green);
				 g.fillRect((scannedp5.getX() * SCALE) + 720 + 5,
				 scannedp5.getY() * SCALE, scannedp5.getWidth() * SCALE,
				 scannedp5.getHeight() * SCALE);
				 g.fillRect((scannedp6.getX() * SCALE) + 720 + 5,
				 scannedp6.getY() * SCALE, scannedp6.getWidth() * SCALE,
				 scannedp6.getHeight() * SCALE);
				
				 if (checkPoints[3] == true) g.setColor(Color.red);
				 else
				 g.setColor(Color.green);
				 g.fillRect((scannedp7.getX() * SCALE) + 720 + 5,
				 scannedp7.getY() * SCALE, scannedp7.getWidth() * SCALE,
				 scannedp7.getHeight() * SCALE);
				 g.fillRect((scannedp8.getX() * SCALE) + 720 + 5,
				 scannedp8.getY() * SCALE, scannedp8.getWidth() * SCALE,
				 scannedp8.getHeight() * SCALE);
				
				 if (checkPoints[4] == true) g.setColor(Color.red);
				 else
				 g.setColor(Color.green);
				 g.fillRect((scannedp9.getX() * SCALE) + 720 + 5,
				 scannedp9.getY() * SCALE, scannedp9.getWidth() * SCALE,
				 scannedp9.getHeight() * SCALE);
				 g.fillRect((scannedp10.getX() * SCALE) + 720 + 5,
				 scannedp10.getY() * SCALE, scannedp10.getWidth() * SCALE,
				 scannedp10.getHeight() * SCALE);
				
				 if (checkPoints[5] == true) g.setColor(Color.red);
				 else
				 g.setColor(Color.green);
				 g.fillRect((scannedp8.getX() * SCALE) + 720 + 5,
				 scannedp8.getY() * SCALE, scannedp8.getWidth() * SCALE,
				 scannedp8.getHeight() * SCALE);
	}

	public void tick(BufferedImage image, boolean[] bool) {
		scannedp1.setImage(image);
		scannedp2.setImage(image);
		scannedp3.setImage(image);
		scannedp4.setImage(image);
		scannedp5.setImage(image);
		scannedp6.setImage(image);
		scannedp7.setImage(image);
		scannedp8.setImage(image);
		scannedp9.setImage(image);
		scannedp10.setImage(image);
		
		scannedp1.tick(image);
		scannedp2.tick(image);
		scannedp3.tick(image);
		scannedp4.tick(image);
		scannedp5.tick(image);
		scannedp6.tick(image);
		scannedp7.tick(image);
		scannedp8.tick(image);
		scannedp9.tick(image);
		scannedp10.tick(image);

		
		add(scannedp1, 0);
		add(scannedp2, 1);
		add(scannedp3, 2);
		add(scannedp4, 3);
		add(scannedp5, 4);
		add(scannedp6, 5);
		add(scannedp7, 6);
		add(scannedp8, 7);
		add(scannedp9, 8);
		add(scannedp10, 9);
		
		checkPoints = bool;
	}

	public void add(ScannedPoint e, int index) {
		sp.add(index, e);
	}

	public ScannedPoint get(int index) {
		return sp.get(index - 1);
	}

	public String getChannel() {
		return channel;
	}


}
