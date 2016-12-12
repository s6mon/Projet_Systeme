package jus.poc.prodcons.v4;

import jus.poc.prodcons.Acteur;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons.v4.MessageX;

public class Producteur extends Acteur implements jus.poc.prodcons._Producteur {

	private int nbMoyenMessage;
	private int deviationNbProduction;
	private int type;
	private int nbMessage;
	private Tampon [] tampons;
	private boolean writing;
	private TestProdCons test;

	
	protected Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, int nbMessage, Tampon [] tampons, TestProdCons test) 
			throws ControlException {
		
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		this.nbMoyenMessage = nbMoyenMessage;
		this.deviationNbProduction = deviationNbProduction;
		this.type = type;
		this.nbMessage = nbMessage;
		this.tampons = tampons;
		this.test = test;
	}

	public int nombreDeMessages() {
		return nbMessage;
	}
	
	public void tamponsPut (Tampon [] tampons, MessageX msg){
		for(int i=0; i<tampons.length; i++){
			try {
				tampons[i].put(this, msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
				tamponsPut(tampons, msgCurrent);
				writing = false;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
		test.prodFinit();
	}
}
