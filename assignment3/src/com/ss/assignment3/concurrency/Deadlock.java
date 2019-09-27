package com.ss.assignment3.concurrency;

/* 
 * Creates deadlock as fooThread must wait on barThread to finish 
 * 		thing.bar() and barThread must wait on fooThread to finish 
 * 			thing.foo()
 */
public class Deadlock {


	public static void main(String[] args) {

		Thing thing = new Thing();

		Thread fooThread = new Thread(new FooThread(thing));
		Thread barThread = new Thread(new BarThread(thing));

		fooThread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		barThread.start();
	}
}

//Prints "done" after 3 calls to bar()
class Thing {
	
	private int callCount = 0;
	
	Object fooLock = new Object();
	Object barLock = new Object();
	
	int getRemaining() {
		return 3 - callCount;
	}
	
	//Waits 5 seconds then calls bar()
	void foo() {
	
		synchronized(fooLock) {

			try {
				Thread.sleep(5000);
				bar();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

	void bar() {

		synchronized(barLock) {
			++callCount;
			if(getRemaining() == 0) {
				System.out.println("done");
				return;
			}
			
			System.out.println("Remaining calls: " + getRemaining());
			foo();
		}
	}
}


class FooThread implements Runnable {
	
	Thing thing;
	
	FooThread(Thing thing) {
		this.thing = thing;
	}

	public void run() {
		thing.foo();
	}
}

class BarThread implements Runnable {
	
	Thing thing;
	
	BarThread(Thing thing) {
		this.thing = thing;
	}
	
	public void run() {
		thing.bar();
	}
}

