package gui;

public class Monitor {
	
	static Monitor m;
	
	private Monitor() {}

	public static Monitor instance() {
		if (m == null) 
			m = new Monitor();
		return m;
	}

	public synchronized void block() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void release() {
		notifyAll();
	}
}
