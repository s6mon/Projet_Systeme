package jus.poc.prodcons.v5;

import jus.poc.prodcons.Message;

public class MessageX implements Message {
	
	int idActeur; 
	int noMsg;
	int nbTotMsg;	

	public MessageX (int idActeur, int noMsg, int nbTotMsg){
		this.idActeur = idActeur;
		this.noMsg = noMsg;
		this.nbTotMsg = nbTotMsg;
		}
	
	public String toString (){
		return "id Producteur : "+idActeur+" | Num message : "+noMsg+"/"+nbTotMsg;
	}
}
