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
		return nbMessage;
	}

	public Message get(_Consommateur cons) throws Exception, InterruptedException {
		semCons.p();
		mutex.p();
		
		System.out.println("cons "+cons.identification()+" a pris le mutex");
		MessageX msg;
		nbMessage--;
		msg = (MessageX)(tampon[out]);
		out = (out+1)%taille();
		
		System.out.println("cons "+cons.identification()+" va rendre le mutex");
		mutex.v();
		semProd.v();
		return (MessageX)(msg);
	}

	public void put(_Producteur prod, Message msg) throws Exception, InterruptedException {
		semProd.p();
		mutex.p();
		
		System.out.println("prod "+prod.identification()+" a pris le mutex");
		nbMessage++;
		tampon[in] = (MessageX)msg;
		in = (in+1)%taille();
		
		System.out.println("prod "+prod.identification()+" va rendre le mutex");
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
