package jus.poc.prodcons.v2;

public class MySemaphore {

	int count;
	
	public MySemaphore(int taille){
		count = taille;
	}
	
	public synchronized void p (){
		//count--;
		if(--count < 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void v (){
		//count++;
		if(++count <= 0){
			notify();
		}
	}
}
