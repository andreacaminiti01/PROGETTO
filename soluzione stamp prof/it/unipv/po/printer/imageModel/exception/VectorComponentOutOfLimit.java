package it.unipv.po.printer.imageModel.exception;

import it.unipv.po.printer.imageModel.ImgVett;

public class VectorComponentOutOfLimit extends Exception{

	private final static String msg="Raggiunto il numero massimo di forme per l'immagine (%s)";
	
	public VectorComponentOutOfLimit() {
		super(String.format(msg, ImgVett.MAXCOMP));
	}
}
