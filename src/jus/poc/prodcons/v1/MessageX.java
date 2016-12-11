package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.v1.TestProdCons;

public class MessageX implements Message {
	
	int idActeur; 
	int noMsg;
	int nbTotMsg;
	boolean estDernier;
	

	public MessageX (int idActeur, int noMsg, int nbTotMsg, boolean lastMsg){
		this.idActeur = idActeur;
		this.noMsg = noMsg;
		this.nbTotMsg = nbTotMsg;
		idActeur++;
		estDernier = lastMsg;
		}
	
	public String toString (){
		return "Producteur : "+idActeur+" | Num message : "+noMsg+"/"+nbTotMsg+" | Est le dernier ? : "+estDernier;
	}
}
