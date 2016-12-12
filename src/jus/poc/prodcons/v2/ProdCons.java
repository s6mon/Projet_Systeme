package jus.poc.prodcons.v2;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements jus.poc.prodcons.Tampon {

	int nbMessage;
	private int in;
	private int out;
	MessageX [] tampon;
	private int tailleTampon;
	private MySemaphore mutex, semProd, semCons;
	
	public ProdCons (int tailleTampon){
		in = 0;
		out = 0;
		tampon = new MessageX [tailleTampon];
		nbMessage = 0;
		this.tailleTampon = tailleTampon;
		mutex = new MySemaphore(1);
		semProd = new MySemaphore(tailleTampon);
		semCons = new MySemaphore(0);
	}
	
	public int enAttente() {
		return ((in - out)+taille())%taille();
	}

	public Message get(_Consommateur cons) throws Exception, InterruptedException {
		semCons.p();
		mutex.p();
		
		MessageX msg = tampon[out];
		tampon[out] = null;
		if(msg != null){
			System.out.println("Consommateur : "+cons.identification()+" lit son "+cons.nombreDeMessages()+"-ième message, "+msg.toString());
		}
		out = (out+1)%taille();
		
		mutex.v();
		semProd.v();
		return msg;
	}

	public void put(_Producteur prod, Message msg) throws Exception, InterruptedException {
		semProd.p();
		mutex.p();
		
		tampon[in] = (MessageX)msg;
		in = (in+1)%taille();
		
		mutex.v();
		semCons.v();		
	}

	public int taille() {
		return tailleTampon;
	}
	
	public void liberer(){
		semCons.v();
	}

}
