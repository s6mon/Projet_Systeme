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
	private TestProdCons test;
	private int nbMoyenExemp;
	private int deviationNbExemp;

	
	protected Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement,
			int nbMessage, Tampon [] tampons, TestProdCons test, int nbMoyenExemp, int deviationNbExemp) 
			throws ControlException {
		
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		this.nbMoyenMessage = nbMoyenMessage;
		this.deviationNbProduction = deviationNbProduction;
		this.type = type;
		this.nbMessage = nbMessage;
		this.tampons = tampons;
		this.test = test;
		this.nbMoyenExemp = nbMoyenExemp;
		this.deviationNbExemp = deviationNbExemp;
	}

	public int nombreDeMessages() {
		return nbMessage;
	}
	
	public void tamponsPut (Tampon [] tampons, MessageX msg, int nbExemp){
		
		for(int i=0; i<tampons.length; i++){
			if(i < nbExemp){
				try {
					msg.setNoExemp(i);
					tampons[i].put(this, msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				try {
					tampons[i].put(null, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		}
	}
	
	
	public void run() {
		int i = 1;
		Aleatoire aleaWait = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		Aleatoire aleaNbExemplaire = new Aleatoire (nbMoyenExemp, deviationNbExemp);
		int wait;
		int nbExemp;
		
		while(i <= nombreDeMessages()){
			nbExemp = aleaNbExemplaire.next();
			
			try {
				MessageX msgCurrent = new MessageX(identification(), i, nombreDeMessages(), nbExemp);
				wait = aleaWait.next();
				sleep(wait);
				tamponsPut(tampons, msgCurrent, nbExemp);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
		test.prodFinit();
	}
}
