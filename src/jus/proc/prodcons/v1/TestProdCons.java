package jus.proc.prodcons.v1;

import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {

	public TestProdCons (Observateur observateur){super(observateur);}
	protected void run() throws Exception {
		//le corps du programme principal
	}
	public static void main(String[] args){new TestProdCons(new Observateur ()).start();}
}