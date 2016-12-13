package jus.poc.prodcons.v3;

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
	
	
	Tampon tampon;

	protected Consommateur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, Tampon tampon)
				
		throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		this.type = type;
		this.moyenneTempsDeTraitement = moyenneTempsDeTraitement;
		this.deviationTempsDeTraitement = deviationTempsDeTraitement;
		this.tampon = tampon;
		nbMsg = 0;
		work = true;
	}


	public int nombreDeMessages() {
		return nbMsg;
	}

	public void run() {
		MessageX msgRecut;
		Aleatoire aleaWait = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		int wait;
		
		while(work){
			
			
			try{
				wait  = aleaWait.next();
				sleep(wait);
				observateur.consommationMessage(this,msgRecut,wait);
			}
//			catch (InterruptedException e) {
	//			this.interrupt();
		//		break;
			//}
			catch (Exception e){
				msgRecut = (MessageX) tampon.get(this);
				observateur.retraitMessage(this, msgRecut);
				if(msgRecut != null){
					System.out.println("Consommateur : "+identification()+" lit son "+nombreDeMessages()+"-i�me message, "+msgRecut.toString());
				}
				observateur.consommationMessage(this, msgRecut, wait);
				nbMsg++;
			}
			// catch (InterruptedException e){
				//e.printStackTrace();
			//}
			
	}
	
	
	
	public void arret() {
		this.interrupt();
	}
	public void changeState(){
		work = false;
	}
	
}
