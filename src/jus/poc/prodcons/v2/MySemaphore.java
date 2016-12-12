package jus.poc.prodcons.v2;

public class MySemaphore {

	private int count;
	private int attente;
	
	public MySemaphore(int taille){
		count = taille;
		attente = 0;
	}
	
	public synchronized void p (){
		if(--count < 0){
			
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void v (){
		count++;
		notify();
	}
}
