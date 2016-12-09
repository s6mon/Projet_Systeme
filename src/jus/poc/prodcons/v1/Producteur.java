package jus.poc.prodcons.v1;

import jus.poc.prodcons.Acteur;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons.v1.MessageX;
import jus.poc.prodcons.v1.ProdCons;
import jus.poc.prodcons.v1.TestProdCons;

public class Producteur extends Acteur implements jus.poc.prodcons._Producteur {

	private int nbMsgToSend;
	private int nbMoyenMessage;
	private int deviationNbProduction;
	private int type;
	private int nbMessage;
	private Tampon tampon;
	
	protected Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, int nbMessage, Tampon tampon) 
			throws ControlException {
		
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		// TODO Auto-generated constructor stub
		this.nbMoyenMessage = nbMoyenMessage;
		this.deviationNbProduction = deviationNbProduction;
		this.type = type;
		this.nbMessage = nbMessage;
		this.tampon = tampon;
	}

	public int nombreDeMessages() {
		return nbMessage;
	}

	
	
	public void run() {
		int i = 0;
		Aleatoire aleaWait = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		boolean lastMsg = false;
		
		//envoyer les nbMsgToSend
		while(i < nombreDeMessages()){
			
			try {
				MessageX msgCurrent = new MessageX(identification(), i, lastMsg);
				tampon.put(this, (Message)(msgCurrent));
				sleep(aleaWait.next());
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
		
		
		
		
		//this.moniteur.put(this, new Message("message")); moniteur correspond � tampon
	}

}
