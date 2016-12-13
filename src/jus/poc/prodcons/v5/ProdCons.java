package jus.poc.prodcons.v5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.v5.MessageX;

public class ProdCons implements jus.poc.prodcons.Tampon {

	int nbMessage;
	private int in;
	private int out;
	MessageX [] tampon;
	private int tailleTampon;
	private Lock lock;
	private Condition full, empty;
	
	public ProdCons (int tailleTampon){
		in = 0;
		out = 0;
		tampon = new MessageX [tailleTampon];
		nbMessage = 0;
		this.tailleTampon = tailleTampon;
		
		lock = new ReentrantLock();
		full  = lock.newCondition(); 
		empty = lock.newCondition();
	}
	
	public int enAttente() {
		return ((in - out)+taille())%taille();
	}

	public Message get(_Consommateur cons) throws Exception, InterruptedException {
		MessageX msg;
		  lock.lock();
		  try {
		    while (nbMessage == 0){
		      full.await();
		    }
		    
			msg = (MessageX)(tampon[out]);
			out = (out+1)%taille();
			nbMessage--;
			
			empty.signal();
			return (MessageX)(msg);
		  }
		  finally {
		    lock.unlock();
		  }
		}

	public void put(_Producteur prod, Message msg) throws Exception, InterruptedException {
		
	lock.lock();
	try {
		while(in == nbMessage){
			empty.await();
		}
		tampon[in] = (MessageX)msg;
		in = (in+1)%taille();
		nbMessage++;
		
		full.signal();
	  }
	finally {
	    lock.unlock();
	  }
	}

	public int taille() {
		return tailleTampon;
	}
	
	public void liberer(){
		full.signal();
	}
}



