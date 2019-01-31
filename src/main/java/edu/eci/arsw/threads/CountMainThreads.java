package edu.eci.arsw.threads;

public class CountMainThreads {
	public static void main(String args[]) {
		
		
		Count cnt = new Count(0,99);
		Count cnt2= new Count(99,199);
		Count cnt3 = new Count(200,299);
		try {

			while (cnt.isAlive()) {
				System.out.println("Main thread1 will be alive till the child thread is live");
				cnt.run();
				Thread.sleep(1500);
			}
			
			while (cnt2.isAlive()) {
				System.out.println("Main thread2 will be alive till the child thread is live");
				cnt2.run();
				Thread.sleep(1500);
			}
			
			while (cnt3.isAlive()) {
				System.out.println("Main thread3 will be alive till the child thread is live");
				cnt3.run();
				Thread.sleep(1500);
			}
			
		} catch (InterruptedException e) {
			System.out.println("Main thread interrupted");
		}
		
		System.out.println("Main thread's run is over");
	}
}
