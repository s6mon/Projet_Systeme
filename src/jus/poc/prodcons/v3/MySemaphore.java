package jus.poc.prodcons.v3;

public class MySemaphore {

	private int count;
	
	public MySemaphore(int count){
		this.count = count;
	}
	
	public synchronized void p (){
		count--;
		if(count < 0){
			
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void v (){
		count++;
		if(count <= 0){
			notify();
		}
	}
}
