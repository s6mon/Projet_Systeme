package jus.poc.prodcons.v3;

import jus.poc.prodcons.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.Thread.State;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Properties;

import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;
import jus.poc.prodcons.Tampon;

public class TestProdCons extends Simulateur {
	
	static int nbProd;
	static int nbCons;
	static int nbBuffer;
	static int tempsMoyenProduction;
	static int deviationTempsMoyenProduction;
	static int tempsMoyenConsommation;
	static int deviationTempsMoyenConsommation;
	static int nbMoyenDeProduction;
	static int deviationNbMoyenDeProduction;
	Observateur observateur;
	private int type;
	
	Producteur [] producteurs;
	Consommateur [] consommateurs;
	
	private ProdCons tampon;
	private static int nbProdFinit;
	private Consommateur consCurrent;
	

	public TestProdCons (Observateur observateur){super(observateur);}
	
	
	protected void run() throws Exception {
		
		String pathXML;
		pathXML = System.getProperty("user.dir").concat("/src/option.xml");
		init(pathXML);
		
		tampon = new ProdCons(nbBuffer);
		
		//Initialisation class Observateur
		observateur.init(nbProd, nbCons, nbBuffer);
		
		//Initialisation des producteurs
		for (int i=0;i< nbProd; i++){
			producteurs[i]=(new Producteur(type,observateur,tempsMoyenProduction,deviationTempsMoyenProduction, nbBuffer, tampon, this));
			observateur.newProducteur(producteurs[i]);
		}
		
		//Initialisation des consommateurs
		for (int i=0;i<nbCons;i++){
			//consommateurs[i]=(new Consommateur(type,observateur, tempsMoyenConsommation ,deviationTempsMoyenConsommation,tampon, this));
			observateur.newConsommateur(consommateurs[i]);
		}

		
		while(nbProdFinit > 0 || tampon.enAttente() != 0){
			Thread.sleep(100);
		}

		System.out.println("Fermeture des cons");
		for(int i=0; i<nbCons; i++){
			consCurrent = consommateurs[i];
			consCurrent.changeState();
			while(consCurrent.getState() == State.WAITING){
				tampon.liberer();
			}
			consCurrent.arret();
		}
	}
	
	public synchronized void prodFinit (){
		nbProdFinit--;
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
			
			nbProdFinit = nbProd;
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}


		private void creerProducteur () throws ControlException {
		Aleatoire aleaNbMsgToProd = new Aleatoire(nbMoyenDeProduction, deviationNbMoyenDeProduction);
		for (int prod = 0; prod < nbProd; prod++) {
			producteurs[prod] = new Producteur(1, observateur, tempsMoyenProduction, 
											deviationTempsMoyenProduction, aleaNbMsgToProd.next(), tampon, this);
			producteurs[prod].start();
		}
	}
	
	private void creerConsommateur () throws ControlException {
		for (int i = 0; i < nbCons; i++) {
			consommateurs[i] = new Consommateur(0, observateur, tempsMoyenConsommation, deviationTempsMoyenConsommation, tampon);
			consommateurs[i].start();
		}
	}	
	
	public static void main(String[] args){System.out.println("-----Version 2-----");new TestProdCons(new Observateur ()).start();}
}