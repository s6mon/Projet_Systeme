package jus.poc.prodcons.v2;

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
	
	Producteur [] producteurs;
	Consommateur [] consommateurs;
	
	private ProdCons tampon;
	private static int nbProdFinit = 0;
	boolean tamponEmpty;

	public TestProdCons (Observateur observateur){super(observateur);}
	
	protected void run() throws Exception {
		
		String pathXML;
		pathXML = System.getProperty("user.dir").concat("/src/option.xml");
		init(pathXML);
		
		tampon = new ProdCons(nbBuffer);
		
		creerConsommateur();
		creerProducteurs();
		
		for(int i=0; i < nbProd; i++){
			producteurs[i].addEtatProdListener(new EtatProdListener() {	
				public void etatProdChangee(boolean oldValue, boolean newValue) {
					prodFinit();
					if(nbProdFinit == nbProd){
						while(tampon.enAttente() != 0){
							System.out.println("je suis bloqué");
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						for(int i=0; i < nbCons; i++){
							consommateurs[i].arret();
						}
					}
				}
			});
		}
	}
	
	
	
	public synchronized void prodFinit (){
		nbProdFinit++;
		System.out.println("il y a "+nbProdFinit+"nb prod finit");
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
			producteurs[i] = new Producteur (1, observateur, tempsMoyenProduction,
					deviationTempsMoyenProduction, aleaNbMsgToProd.next(), tampon);
			producteurs[i].start();
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