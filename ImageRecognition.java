package com.reklamkoll.server;


import java.util.ArrayList;

public class ImageRecognition {

	private boolean commercial = false;
	public boolean[] checkPoints = new boolean[6];

	private ArrayList<String> booleanList = new ArrayList<>();

	private int scanned1, scanned2, scanned3, scanned4, scanned5, scanned6, scanned7, scanned8, scanned9, scanned10;

	public ImageRecognition() {
		System.out.println("ImageRecognition started");
	}

	public boolean isCommercial() {
		return commercial;
	}

	public void compareScannedPoints(int[] sp1, int[] sp2, int[] sp3, int[] sp4, int[] sp5, int[] sp6, int[] sp7, int[] sp8, int[] sp9, int[] sp10) {

		scanned1 = arrayToInt(sp1);
		scanned2 = arrayToInt(sp2);
		scanned3 = arrayToInt(sp3);
		scanned4 = arrayToInt(sp4);
		scanned5 = arrayToInt(sp5);
		scanned6 = arrayToInt(sp6);
		scanned7 = arrayToInt(sp7);
		scanned8 = arrayToInt(sp8);
		scanned9 = arrayToInt(sp9);
		scanned10 = arrayToInt(sp10);

		checkPoints[0] = test(scanned2, scanned1);
		checkPoints[1] = test(scanned4, scanned3);
		checkPoints[2] = test(scanned6, scanned5);
		checkPoints[3] = test(scanned8, scanned7);
		checkPoints[4] = test(scanned9, scanned10);

		System.out.println(getCheckPoints() + " / " + checkPoints.length);
		System.out.println(((scanned9 - scanned10) + " -----------------"));

		if (getCheckPoints() > 3) {
			commercial = isCommersial(true);
		}
		if (getCheckPoints() < 3) {
			commercial = isCommersial(false);
		}

	}

	private boolean isCommersial(boolean bool) {
		addToBooleanList(bool);
		int yes = 0, no = 0;

		for (int i = 0; i < booleanList.size(); i++) {
			if (getBoolean(i) == true) yes++;
			else
				no++;
		}
		System.out.println("Is reklam : Yes/no : " + yes + "/" + no);
		if (yes == 6) return true;
		if (no == 6) return false;

		return commercial;
	}

	public boolean getBoolean(int index) {
		String temp = booleanList.get(index);
		if (temp.equalsIgnoreCase("true")) return true;
		return false;
	}

	private int getCheckPoints() {
		int counter = 0;
		for (int i = 0; i < checkPoints.length; i++) {
			if (checkPoints[i] == true) counter++;
		}

		return counter;
	}

	public boolean[] getCheckPointsBool() {
		return checkPoints;
	}

	private boolean test(int white, int black) {
		if (white - black < 10) return true;
		return false;
	}

	private int arrayToInt(int[] arr) {
		int x = 0;
		for (int i = 0; i < 3; i++) {
			x += arr[i];
		}
		return x / 3;
	}

	public void addToBooleanList(boolean bool) {
		if (bool == true) {
			booleanList.add(0, "true");
		} else
			booleanList.add(0, "false");
		if (booleanList.size() > 6) {
			removeFromBooleanList(6);
		}
	}

	private void removeFromBooleanList(int index) {
		booleanList.remove(index);
	}

}
