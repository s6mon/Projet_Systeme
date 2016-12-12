package jus.poc.prodcons.v2;

import java.util.EventListener;

public interface EtatProdListener extends EventListener {
	void etatProdChangee(boolean oldValue, boolean newValue);

}
