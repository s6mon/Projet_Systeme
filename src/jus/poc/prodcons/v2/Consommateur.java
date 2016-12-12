package jus.poc.prodcons.v2;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;

public class Consommateur extends Acteur implements jus.poc.prodcons._Consommateur{
	
	private int type;
	private int moyenneTempsDeTraitement;
	private int deviationTempsDeTraitement;
	private int nbMsg = 0;
	private boolean writing;
	
	Tampon tampon;

	protected Consommateur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, Tampon tampon)
				
		throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		// TODO Auto-generated constructor stub
		this.type = type;
		this.moyenneTempsDeTraitement = moyenneTempsDeTraitement;
		this.deviationTempsDeTraitement = deviationTempsDeTraitement;
		this.tampon = tampon;
	}


	public int nombreDeMessages() {
		return nbMsg;
	}

	public void run() {
		MessageX msgRecut;
		Aleatoire aleaWait = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		int wait;
		
		
		
		while(true){
			
			try {
				msgRecut = (MessageX) tampon.get(this);
				writing = true;
				nbMsg++;
				System.out.println("Consommateur : "+identification()+" lit son "+nombreDeMessages()+"-ième message, "+msgRecut.toString());
				writing = false;
				wait  = aleaWait.next();
				sleep(wait);
			}
			catch (InterruptedException e) {
				this.interrupt();
				break;
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void arret() {
		while(writing);
			this.interrupt();
	}
	
}
