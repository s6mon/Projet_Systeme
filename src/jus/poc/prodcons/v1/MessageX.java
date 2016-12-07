package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.v1.TestProdCons.TypeActeur;

public class MessageX implements Message {
	
	TypeActeur type;
	int id; 
	int noMsg;

	public MessageX (TypeActeur type, int id, int noMsg){
		this.type = type;
		this.id = id;
		this.noMsg = noMsg;
	}
	
	public String toString (){
		return "Type acteur : "+type+" | Id : "+id+" | Num message : "+noMsg;
	}
}
