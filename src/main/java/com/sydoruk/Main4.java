package com.sydoruk;

public class Main4 {
	public static void helloWorld(){
		String line = "Hello World!";
		int length = line.length();
		System.out.println(line.charAt(length - length));
		System.out.println(line.charAt(length - 1));
	}


	public static void isEnd() {
		String task = "Java Exercises";
		String task2 = "Java Exercise";
		String end = "se";
		System.out.println(end.equals(task.substring(task.length() - end.length())));
		System.out.println(end.equals(task2.substring(task2.length() - end.length())));
		// or another way
		System.out.println(task.endsWith(end));
		System.out.println(task2.endsWith(end));
	}

	public static void isConteins() {
		String lineOne = "Stephen Edwin King";
		System.out.println(lineOne.contains("Walter Winchell"));
		System.out.println(lineOne.contains("Edwin"));
	}

	public static void eqIgnoreCase() {
		String text1 = "Stephen Edwin King";
		String text2 = "Walter Winchell";
		String text3 = "stephen edwin king";
		System.out.println(text1.toLowerCase().contains(text2.toLowerCase()));
		System.out.println(text1.toLowerCase().contains(text3.toLowerCase()));
		// or another way
		System.out.println(text2.equalsIgnoreCase(text1));
		System.out.println(text3.equalsIgnoreCase(text1));

	}

	public static void isBegin() {
		String begin = "Red";
		String line2 = "Red is favorite color";
		String line3 = "Orange is also my favorite color";
		System.out.println(line2.indexOf(begin) == 0);
		System.out.println(line3.indexOf(begin) == 0);
		// or another way
		System.out.println(line2.startsWith(begin));
		System.out.println(line3.startsWith(begin));
	}



	public static void main(String[] args){
		System.out.println("Task 1");
		helloWorld();
		System.out.println("Task 2");
		isEnd();
		System.out.println("Task 3");
		isConteins();
		System.out.println("Task 4");
		eqIgnoreCase();
		System.out.println("Task 5");
		isBegin();
	}
}
