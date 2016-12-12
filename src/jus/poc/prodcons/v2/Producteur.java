package jus.poc.prodcons.v2;


import javax.swing.event.EventListenerList;

import jus.poc.prodcons.Acteur;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons.v2.MessageX;

public class Producteur extends Acteur implements jus.poc.prodcons._Producteur {

	private int nbMoyenMessage;
	private int deviationNbProduction;
	private int type;
	private int nbMessage;
	private Tampon tampon;
	private boolean writing;
	private TestProdCons test;

	
	private final EventListenerList listeners = new EventListenerList();
	
	protected Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, int nbMessage, Tampon tampon, TestProdCons test) 
			throws ControlException {
		
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		// TODO Auto-generated constructor stub
		this.nbMoyenMessage = nbMoyenMessage;
		this.deviationNbProduction = deviationNbProduction;
		this.type = type;
		this.nbMessage = nbMessage;
		this.tampon = tampon;
		this.test = test;
	}

	public int nombreDeMessages() {
		return nbMessage;
	}
	
	public void run() {
		int i = 1;
		Aleatoire aleaWait = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		int wait;
		
		while(i <= nombreDeMessages()){
			
			try {
				MessageX msgCurrent = new MessageX(identification(), i, nombreDeMessages());
				writing = true;
				wait = aleaWait.next();
				sleep(wait);
				tampon.put(this, (Message)(msgCurrent));
				writing = false;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
		test.prodFinit();
	}
	
	public void arret(){
		while(writing){}
		this.interrupt();
	}
}
