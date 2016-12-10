package jus.poc.prodcons.v1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;

public class Consommateur extends Acteur implements jus.poc.prodcons._Consommateur{
	
	private int type;
	private int moyenneTempsDeTraitement;
	private int deviationTempsDeTraitement;
	
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
		// TODO Auto-generated method stub
		return 0;
	}

	public void run() {
		boolean fin = false;
		MessageX msgRecut;
		Aleatoire aleaWait = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		int wait;
		
		while(!fin){
			
			try {
				msgRecut = (MessageX) tampon.get(this);
				System.out.println("Consommateur : "+identification()+" lit "+msgRecut.toString());
				wait  = aleaWait.next();
				sleep(wait);
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}
	
}
