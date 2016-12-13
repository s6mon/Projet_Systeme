package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements jus.poc.prodcons.Tampon {

	int nbMessage;
	private int in;
	private int out;
	MessageX [] tampon;
	private int tailleTampon;
	
	public ProdCons (int tailleTampon){
		in = 0;
		out = 0;
		tampon = new MessageX [tailleTampon];
		nbMessage = 0;
		this.tailleTampon = tailleTampon;
	}
	
	public int enAttente() {
		return nbMessage;
	}

	public synchronized Message get(_Consommateur cons) throws Exception, InterruptedException {
		MessageX msg;
		while(nbMessage == 0){
			wait();
		}
		nbMessage--;
		msg = (MessageX)(tampon[out]);
		out = (out+1)%taille();
		notifyAll();
		return (MessageX)(msg);
	}

	public synchronized void put(_Producteur prod, Message msg) throws Exception, InterruptedException {
		while(nbMessage == taille()){
			wait();
		}
		nbMessage++;
		tampon[in] = (MessageX)msg;
		in = (in+1)%taille();
		notifyAll();		
	}

	public int taille() {
		return tailleTampon;
	}

}
