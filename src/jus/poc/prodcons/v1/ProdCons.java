package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements jus.poc.prodcons.Tampon {

	int nbMessage;
	private int in, nextIn;
	private int out, nextOut;
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

	//m�thode sync
	public synchronized Message get(_Consommateur arg0) throws Exception, InterruptedException {
		MessageX msg;
		while(nbMessage == 0){
			wait();
		}
		nbMessage = nbMessage - 1;
		msg = (MessageX)(tampon[out]);
		out = (out+1)%taille();
		notifyAll();
		return (MessageX)(msg);
	}

	public synchronized void put(_Producteur cons, Message msg) throws Exception, InterruptedException {
		while(nbMessage == taille()){
			wait();
		}
		nbMessage = nbMessage + 1;
		tampon[in] = (MessageX)msg;
		in = (in+1)%taille();
		notifyAll();		
	}

	public int taille() {
		return tailleTampon;
	}

}
