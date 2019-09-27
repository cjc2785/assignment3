package com.ss.assignment3.concurrency;



public class ProducerConsumer {
	
	public static void main(String[] args) {
		
		Buffer buffer = new Buffer(5);
		Thread producer = new Thread(new Producer(buffer));
		Thread consumer = new Thread(new Consumer(buffer));
		
		consumer.start();

		

		producer.start();
	}
}


class Buffer {


	private final int[] nums;
	private int size = 0;


	public Buffer(int maxSize) {
		this.nums = new int[maxSize];
	}

	public boolean isFull() {
		return size == nums.length;
	}

	public boolean isEmpty() {
		return size == 0;
	}


	public void write(int num) {
		synchronized(this) {
			if(isFull()) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			nums[size] = num;
			++size;

			notify();
		}
	}

	public int read() {
		synchronized(this) {

			if(isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			int num = nums[0];

			for(int i = 1; i < size; ++i) {
				nums[i - 1] = nums[i]; 
			}

			--size;

			notify();

			return num;
		}
	}
}


class Producer implements Runnable {

	Buffer buffer;
	Consumer consumer;

	Producer(Buffer buffer) {
		this.buffer = buffer;
	}

	public void run() {
		int num = 0;
		while(true) {
			++num;
			buffer.write(num);
			
			try {
				if(num % 4 == 0) {
				Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Consumer implements Runnable {

	Buffer buffer;

	Consumer(Buffer buffer) {
		this.buffer = buffer;
	}

	public void run() {
		
		while(true) {


			int num = buffer.read();

			System.out.println("consumed: " + num);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}