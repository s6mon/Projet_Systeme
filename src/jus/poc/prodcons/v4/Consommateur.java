package jus.poc.prodcons.v4;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;

public class Consommateur extends Acteur implements jus.poc.prodcons._Consommateur{
	
	private int type;
	private int moyenneTempsDeTraitement;
	private int deviationTempsDeTraitement;
	private int nbMsg;
	private boolean reading;
	private boolean work;
	
	
	Tampon [] tampons;

	protected Consommateur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, Tampon [] tampons)
				
		throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		this.type = type;
		this.moyenneTempsDeTraitement = moyenneTempsDeTraitement;
		this.deviationTempsDeTraitement = deviationTempsDeTraitement;
		this.tampons = tampons;
		nbMsg = 0;
		work = true;
	}


	public int nombreDeMessages() {
		return nbMsg;
	}
	
	public MessageX [] tamponsGet (Tampon [] tampons){
		MessageX [] msgs = new MessageX[tampons.length];
		for(int i=0; i<tampons.length; i++){
			try {
				msgs[i] = (MessageX) tampons[i].get(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return msgs;
	}

	public void run() {
		MessageX msgRecut;
		Aleatoire aleaWait = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		int wait;
		
		while(work){
			
			tamponsGet(tampons);
		}
		
	}
	
	public void arret() {
		this.interrupt();
	}
	public void changeState(){
		work = false;
	}
	
}
