package jus.proc.prodcons.v4;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Properties;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {
	
	//TODO déclarer toute les variables de XML
	static int nbProd;
	static int nbCons;
	static int nbBuffer;
	static int tempsMoyenProduction;
	static int deviationTempsMoyenProduction;
	static int tempsMoyenConsommation;
	static int deviationTempsMoyenConsommation;
	static int nbMoyenDeProduction;
	static int deviationNbMoyenDeProduction;
	static int nbMoyenNbExemplaire;
	static int deviationNbMoyenNbExemplaire;

	public TestProdCons (Observateur observateur){super(observateur);}
	protected void run() throws Exception {
		//le corps du programme principal
		String pathXML;
		pathXML = System.getProperty("user.dir").concat("/src/jus/proc/prodcons/v1/option.xml");
		init(pathXML);
		System.out.println(nbCons);
		
	}
	
	public void init (String file){
		Properties properties = new Properties();
		try {
			properties.loadFromXML(new FileInputStream(file));

			nbProd = Integer.parseInt(properties.getProperty("nbProd"));
			nbCons = Integer.parseInt(properties.getProperty("nbCons"));
			nbBuffer = Integer.parseInt(properties.getProperty("nbBuffer"));
			tempsMoyenProduction = Integer.parseInt(properties.getProperty("tempsMoyenProduction"));
			deviationTempsMoyenProduction = Integer.parseInt(properties.getProperty("deviationTempsMoyenProduction"));
			tempsMoyenConsommation = Integer.parseInt(properties.getProperty("tempsMoyenConsommation"));
			deviationTempsMoyenConsommation = Integer.parseInt(properties.getProperty("deviationTempsMoyenConsommation"));
			nbMoyenDeProduction = Integer.parseInt(properties.getProperty("nombreMoyenDeProduction"));
			deviationNbMoyenDeProduction = Integer.parseInt(properties.getProperty("deviationNombreMoyenDeProduction"));
			nbMoyenNbExemplaire = Integer.parseInt(properties.getProperty("nbMoyenNbExemplaire"));
			deviationNbMoyenNbExemplaire = Integer.parseInt(properties.getProperty("deviationNbMoyenNbExemplaire"));
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