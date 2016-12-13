package jus.poc.prodcons.v2;

import jus.poc.prodcons.Message;

public class MessageX implements Message {
	
	int idActeur; 
	int noMsg;
	int nbTotMsg;	
	boolean estDernier;

	public MessageX (int idActeur, int noMsg, int nbTotMsg, boolean estDernier){
		this.idActeur = idActeur;
		this.noMsg = noMsg;
		this.nbTotMsg = nbTotMsg;
		this.estDernier = estDernier;
		}
	
	public String toString (){
		return "id Producteur : "+idActeur+" | Num message : "+noMsg+"/"+nbTotMsg;
	}
	
	public boolean getIsFin (){
		return estDernier;
	}
}
