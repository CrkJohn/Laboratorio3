package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PrimeFinderThread extends Thread {

	int a, b;
	private static Object sync = new Object();

	private List<Integer> primes;
	private boolean isWait;

	public PrimeFinderThread(int a, int b) {
		super();
		this.primes = new ArrayList<>();
		this.a = a;
		this.b = b;
		isWait = true;
	}

	@Override
	public void run() {
		for (int i = a; i < b; i++) {
			if (isPrime(i)) {
				primes.add(i);
//				System.out.println(i);
			}
		}
		synchronized (sync) {
			if (true) {
				try {
					sync.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			isWait = false;
		}
	}

	boolean isPrime(int n) {
		boolean ans;
		if (n > 2) {
			ans = n % 2 != 0;
			for (int i = 3; ans && i * i <= n; i += 2) {
				ans = n % i != 0;
			}
		} else {
			ans = n == 2;
		}
		return ans;
	}

	public List<Integer> getPrimes() {
		return primes;
	}
	
	public void setWait(boolean newWait) {
		 isWait = true;
	}

}
