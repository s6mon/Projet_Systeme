package jus.poc.prodcons.v4;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.Thread.State;
import java.util.Properties;

import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

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
	static int nombreMoyenNbExemplaire;
	static int deviationNombreMoyenNbExemplaire;
	
	Producteur [] producteurs;
	Consommateur [] consommateurs;
	private ProdCons [] tampons;
	
	private static int nbProdFinit;
	private Consommateur consCurrent;
	private int nbMaxExemplaire;
	

	public TestProdCons (Observateur observateur){super(observateur);}
	
	
	protected void run() throws Exception {
		
		String pathXML;
		pathXML = System.getProperty("user.dir").concat("/src/option.xml");
		init(pathXML);
		
		//création du nb max exemplaire buffer
		
		for(int i=0; i < nbMaxExemplaire; i++){
			tampons[i] = new ProdCons(nbBuffer);
		}
		
		
		creerConsommateur();
		creerProducteur();
		

		
		while(nbProdFinit > 0 || !tamponsIsVide(tampons)){
			Thread.sleep(100);
		}

		System.out.println("Fermeture des cons");
		for(int i=0; i<nbCons; i++){
			consCurrent = consommateurs[i];
			if(consCurrent.isAlive()){
				consCurrent = consommateurs[i];
				consCurrent.changeState();
				while(consCurrent.getState() == State.WAITING){
					tamponsFree(tampons);
				}
			}
		}
	}
	
	public synchronized void prodFinit (){
		nbProdFinit--;
	}
	
	public boolean tamponsIsVide(ProdCons [] tampons){
		int nbVide = 0;
		for(int i=0; i<tampons.length; i++){
			if(tampons[i].enAttente() == 0){
				nbVide++;
			}
		}
		return (nbVide == tampons.length);
	}
	
	
	public void tamponsFree (ProdCons [] tampons){
		for(int i=0; i<tampons.length; i++){
			tampons[i].liberer();
		}
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
			nombreMoyenNbExemplaire = Integer.parseInt(properties.getProperty("nombreMoyenNbExemplaire"));
			deviationNombreMoyenNbExemplaire = Integer.parseInt(properties.getProperty("deviationNombreMoyenNbExemplaire"));
			
			nbMaxExemplaire = nombreMoyenNbExemplaire + deviationNombreMoyenNbExemplaire;
			
			producteurs = new Producteur[nbProd];
			consommateurs = new Consommateur[nbCons];
			tampons = new ProdCons[nbMaxExemplaire];
			
			nbProdFinit = nbProd;
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}


		private void creerProducteur () throws ControlException {
		Aleatoire aleaNbMsgToProd = new Aleatoire(nbMoyenDeProduction, deviationNbMoyenDeProduction);
		for (int prod = 0; prod < nbProd; prod++) {
			producteurs[prod] = new Producteur(1, observateur, tempsMoyenProduction, 
											deviationTempsMoyenProduction, aleaNbMsgToProd.next(), tampons, this, nombreMoyenNbExemplaire, deviationNombreMoyenNbExemplaire);
			producteurs[prod].start();
		}
	}
	
	private void creerConsommateur () throws ControlException {
		for (int i = 0; i < nbCons; i++) {
			consommateurs[i] = new Consommateur(0, observateur, tempsMoyenConsommation, deviationTempsMoyenConsommation, tampons);
			consommateurs[i].start();
		}
	}	
	
	public static void main(String[] args){System.out.println("-----Version 4-----");new TestProdCons(new Observateur ()).start();}
}