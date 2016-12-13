package jus.poc.prodcons.v4;

import jus.poc.prodcons.Message;

public class MessageX implements Message {
	
	int idActeur; 
	int noMsg;
	int nbTotMsg;
	int noExemp;
	int nbExemp;

	public MessageX (int idActeur, int noMsg, int nbTotMsg, int nbExemp){
		this.idActeur = idActeur;
		this.noMsg = noMsg;
		this.nbTotMsg = nbTotMsg;
		this.nbExemp = nbExemp;
	}
	
	public String toString (){
		return "id Producteur : "+idActeur+" | No message : "+noMsg+"/"+nbTotMsg;
	}
	
	public synchronized void setNoExemp(int noExemp){
		noExemp += 1;
		this.noExemp = noExemp;
		
	}
	

}
