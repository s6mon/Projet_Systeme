package jus.poc.prodcons.v1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.v1.ProdCons;
import jus.poc.prodcons.v1.TestProdCons.TypeActeur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.Tampon;

public class Producteur extends Acteur implements jus.poc.prodcons._Producteur {

	private int nbMsgToSend;
	private int nbMoyenMessage;
	private int deviationNbProduction;
	private int identification;
	private Tampon tampon;
	
	protected Producteur(int identification, Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, int nbMoyenMessage, int deviationNbProduction, Tampon tampon) 
			throws ControlException {
		
		super(identification, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		// TODO Auto-generated constructor stub
		this.nbMoyenMessage = nbMoyenMessage;
		this.deviationNbProduction = deviationNbProduction;
		this.identification = identification;
		this.tampon = tampon;
	}

	@Override
	public int deviationTempsDeTraitement() {
		return 0;

	}

	@Override
	public int identification() {
		return identification;
	}

	@Override
	public int moyenneTempsDeTraitement() {
		return 0;
	}

	@Override
	public int nombreDeMessages() {
		return Aleatoire.valeur(nbMoyenMessage, deviationNbProduction);
	}

	@Override
	public void run() {
		int i;
		Aleatoire alea = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO
		//on doit initialiser toutes nos constantes nb msg �
		nbMsgToSend = nombreDeMessages();
		
		//envoyer les nbMsgToSend
		for(i=nbMsgToSend; i > 0; i--){
			
			//cr�er le msg � envoyer : type, id, n�Msg
			MessageX msgCurrent = new MessageX(TypeActeur.PRODUCTEUR, identification(), i);
			
			//on doit d�poser le message dans le tampon
			//puis on attendre un temps al�atoire
			try {
				tampon.put(this, (Message)(msgCurrent));
				sleep(alea.next());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		
		
		
		//this.moniteur.put(this, new Message("message")); moniteur correspond � tampon
	}

}
