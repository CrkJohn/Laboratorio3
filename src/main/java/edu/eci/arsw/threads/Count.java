package edu.eci.arsw.threads;

import java.lang.Thread;

public class Count extends Thread {
	
	private int A,B;
	
	Count() {
		super("my extending thread");
		System.out.println("my thread created" + this);
		start();
	}
	
	Count(int A, int B) {
		super("my extending thread");
		this.A = A;
		this.B=B;
		System.out.println("my thread created" + this);
		start();
	}
	
	
	public void run() {
		try {
			for (int i = A; i <= B; i++) {
				System.out.println("Printing the count " + i);
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("my thread interrupted");
		}
		System.out.println("My thread run is over");
	}
	
}