package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.v1.TestProdCons;

public class MessageX implements Message {
	
	int idActeur; 
	int noMsg;
	boolean estDernier;

	public MessageX (int idActeur, int noMsg, boolean lastMsg){
		this.idActeur = idActeur;
		this.noMsg = noMsg;
		estDernier = lastMsg;
		}
	
	public String toString (){
		return "Acteur(producteur) n° : "+idActeur+" | Num message : "+noMsg+" | Est le dernier : "+estDernier;
	}
}
