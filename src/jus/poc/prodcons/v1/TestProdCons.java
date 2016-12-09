package jus.poc.prodcons.v1;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Properties;

import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {
	
	//TODO dï¿½clarer toute les variables de XML
	
	static int nbProd;
	static int nbCons;
	static int nbBuffer;
	static int tempsMoyenProduction;
	static int deviationTempsMoyenProduction;
	static int tempsMoyenConsommation;
	static int deviationTempsMoyenConsommation;
	static int nbMoyenDeProduction;
	static int deviationNbMoyenDeProduction;
	
	Producteur [] producteurs;
	Consommateur [] consommateurs;
	
	private ProdCons tampon;

	public TestProdCons (Observateur observateur){super(observateur);}
	
	protected void run() throws Exception {
		
		
		//le corps du programme principal
		String pathXML;
		pathXML = System.getProperty("user.dir").concat("/src/jus/poc/prodcons/v1/option.xml");
		init(pathXML);
		System.out.println(nbCons);
		
		tampon = new ProdCons(nbBuffer);
		System.out.println("tampon créé");
		
		creerConsommateur();
		creerProducteurs();
		System.out.println("prod créé");
		
		
		
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
			
			producteurs = new Producteur[nbProd];
			consommateurs = new Consommateur[nbCons];
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}


	//créer producteur
	private void creerProducteurs() throws ControlException {
		Aleatoire aleaNbMsgToProd = new Aleatoire(nbMoyenDeProduction, deviationNbMoyenDeProduction);
		for (int i = 0; i < nbProd; i++) {
			Producteur prod = new Producteur (1, observateur, tempsMoyenProduction, deviationTempsMoyenProduction, aleaNbMsgToProd.next(), tampon);
			prod.start();
		}
	}
	
	private void creerConsommateur () throws ControlException {
		System.out.println("boucle for création cons nbCons = "+nbCons);
		for (int i = 0; i < nbCons; i++) {
			System.out.println("avant création cons");
			Consommateur cons = new Consommateur(0, observateur, tempsMoyenConsommation, deviationTempsMoyenConsommation, tampon);
			cons.start();
			
		}
	}
	
	

	
	
	
	
	public static void main(String[] args){new TestProdCons(new Observateur ()).start();}
	
	
}