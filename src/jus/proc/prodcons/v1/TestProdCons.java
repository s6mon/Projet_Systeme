package jus.proc.prodcons.v1;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {
	
	//TODO déclarer toute les variables de XML

	public TestProdCons (Observateur observateur){super(observateur);}
	protected void run() throws Exception {
		//le corps du programme principal
	}

	protected static void init (String file){
		Properties properties = new Properties();
		try {
			properties.loadFromXML(new FileInputStream(file));
			//nb_consommateurs = Integer.parserInt(properties.getProperties("nbConsommateurs"));
			//toute les lignes de init
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	//TODO créer consommateur
	/*private void creerConsommateurs() throws ControlException {
		for (int i = 0; i < array.length; i++) {
			Consommateur cons = new Consommateur (/*param à passer*//*);
			this.observateur.newConsommateur(cons);
			this.nb_consommateurs.add(cons);
		}
	}*/
	
	
	
	
	
	public static void main(String[] args){new TestProdCons(new Observateur ()).start();}
	
	
}