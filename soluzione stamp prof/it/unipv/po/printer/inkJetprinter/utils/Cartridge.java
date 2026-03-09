package it.unipv.po.printer.inkJetprinter.utils;

import it.unipv.po.printer.colorModel.Colore;
import it.unipv.po.printer.inkJetprinter.utils.exception.CartridgeEmpty;

public class Cartridge {
	
	public static int MAXLEVEL=1000;
	private Colore color;
	private double level;
	
	public Cartridge(Colore color, double level) {
		super();
		this.color = color;
		this.level=level;
	}

	public void useColor(double val) throws CartridgeEmpty {
		level-=val;
		if(level<0)
			throw new CartridgeEmpty(color);
	}

	public Colore getColor() {
		return color;
	}
	
	public double getLevel() {
		return level;
	}
	
}
