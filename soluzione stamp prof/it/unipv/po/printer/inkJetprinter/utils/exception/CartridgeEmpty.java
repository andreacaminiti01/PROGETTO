package it.unipv.po.printer.inkJetprinter.utils.exception;

import it.unipv.po.printer.colorModel.Colore;

public class CartridgeEmpty extends Exception {

	private final static String msg="Il Colore %s è terminato, sostiture la cartuccia";
	
	public CartridgeEmpty(Colore c) {
		super(String.format(msg, c.name()));
		
	}
	
	

}
