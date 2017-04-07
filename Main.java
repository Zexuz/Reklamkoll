package com.reklamkoll.server;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Main extends JComponent implements Runnable {
	private static final long serialVersionUID = -189255241097382295L;

	// private final static String LOCAL_URL =
	// "http://192.168.1.68/grab?format=jpg&r=720&filename=%2Ftmp%2F1403716388435&v=";
	private final static String GLOBAL_URL = "http://cdn.androidcommunity.com/wp-content/uploads/2012/02/2_mobile_offline-720x420.png";
	private final int SCALE = 12;

	private JFrame frame;
	private Thread thread;
	private Tv tv;
	private ImageRecognition ir;
	private SendPush sendPush;
	private TvChannel tv4;
	private TvChannel tv6;

	// private ScannedPoint scannedp1; // Top Left Corner
	// private ScannedPoint scannedp2; // Top Left Corner Check
	// private ScannedPoint scannedp3; // Top Right Corner
	// private ScannedPoint scannedp4; // Top Right Corner Check
	// private ScannedPoint scannedp5; // Bottom Left Corner
	// private ScannedPoint scannedp6; // Bottom Left Corner Check
	// private ScannedPoint scannedp7; // Bottom Right Corner
	// private ScannedPoint scannedp8; // Bottom Right Corner Check
	// private ScannedPoint scannedp9; // Middle logo check
	// private ScannedPoint scannedp10; // Middle logo middle

//	Downloaded picture
	public static BufferedImage image;
	
//	Logo picture
	public BufferedImage cropImage;
	
//	tv4 logo cord
	private static int logoStartX = 627;
	private static int logoStartY = 38;

	private Rectangle rec;

	private boolean running = false;
	private boolean commer, timeToSend = false;

	private SimpleDateFormat sdf;
	private Date date;

	public Main() throws IOException {
		//init push ability
		sendPush = new SendPush();
		//point url to tv
		URL url = new URL(GLOBAL_URL);
		//init frame
		frame = new JFrame();
		//use url to init tv
		tv = new Tv(url);
		//init IR
		ir = new ImageRecognition();
		//get the latest picture from tv and set the image to that
		image = tv.getImage();
		//crop the image and create a subimage (logo image)
		cropImage = tv.getCropImage();
		//init sdf
		sdf = new SimpleDateFormat("HH:mm:ss");
		//Init date
		date = new Date();
		//init channel tv4
		tv4 = new TvChannel("tv4");

		setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));

		rec = new Rectangle(cropImage.getWidth() + 10, cropImage.getHeight() + 10);
		rec.x = logoStartX - 5;
		rec.y = logoStartY - 5;
	}

	private void tick() {
		date = new Date();
		image = tv.getImage();
		cropImage = tv.getCropImage();
		commer = ir.isCommercial();

//		tv.writeFiles();

		tv4.tick(cropImage,ir.getCheckPointsBool());

		// scannedp1.tick(cropImage);
		// scannedp2.tick(cropImage);
		// scannedp3.tick(cropImage);
		// scannedp4.tick(cropImage);
		// scannedp5.tick(cropImage);
		// scannedp6.tick(cropImage);
		// scannedp7.tick(cropImage);
		// scannedp8.tick(cropImage);
		// scannedp9.tick(cropImage);
		// scannedp10.tick(cropImage);

		ir.compareScannedPoints(tv4.get(1).getPixelColors(), tv4.get(2).getPixelColors(), tv4.get(3).getPixelColors(), tv4.get(4)
				.getPixelColors(), tv4.get(5).getPixelColors(), tv4.get(6).getPixelColors(), tv4.get(7).getPixelColors(), tv4.get(8)
				.getPixelColors(), tv4.get(9).getPixelColors(), tv4.get(10).getPixelColors());

		frame.repaint();

		System.out.println("Reklam : " + ir.isCommercial());
		if (!ir.isCommercial() && timeToSend) {
			sendPush.SendPush();
			timeToSend = false;
		}

		if (ir.isCommercial()) {
			timeToSend = true;
		}

		System.out.println(sdf.format(date));

		// Tv.changeChannel(Tv.Tv6);
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = false;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), this);
		g.drawImage(cropImage, image.getWidth() + 10, 0, cropImage.getWidth() * SCALE, cropImage.getHeight() * SCALE, this);

		g.setColor(Color.red);
		g.setFont(new Font("Areal", Font.BOLD, 40));
		if (!commer) {
			g.fillRect(rec.x, rec.y, rec.width, rec.height);
		}

		g.drawImage(cropImage, logoStartX, logoStartY, cropImage.getWidth(), cropImage.getHeight(), this);

		tv4.paint(g);


	}

	public static void main(String args[]) throws IOException {
		//create a JFrame and put the Main class in the frame
		Main main = new Main();

		main.frame.add(new JScrollPane(main));

		main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.frame.setLayout(new CardLayout());
		main.frame.setSize(739 + 5 + (main.tv.getCropImage().getWidth() * 12), 455);
		main.frame.setLocation(505, 0);
		main.frame.setVisible(true);

		main.start();
	}

	//The run method. The loop will be executed 1/s
	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 1.0;
		double delta = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (true) {
				frame.setTitle(" " + frame.getLocationOnScreen());
				tick();
				delta--;
			}
		}

	}
}
