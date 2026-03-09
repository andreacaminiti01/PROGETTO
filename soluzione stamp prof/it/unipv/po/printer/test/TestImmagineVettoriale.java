
 package it.unipv.po.printer.test;

import it.unipv.po.printer.colorModel.Colore;
import it.unipv.po.printer.dataModel.forme.Cerchio;
import it.unipv.po.printer.dataModel.forme.Rettangolo;
import it.unipv.po.printer.imageModel.ImgVett;
import it.unipv.po.printer.imageModel.exception.VectorComponentOutOfLimit;
import it.unipv.po.printer.inkJetprinter.Util;


public class TestImmagineVettoriale {

	public static void main(String[] args) {

		ImgVett img1 = new ImgVett();
		try {
			img1.addComponente(new Rettangolo(1, 1));
			img1.addComponente(new Rettangolo(2, 1));
			img1.addComponente(new Cerchio(1));
			img1.addComponente(new Rettangolo(3, 2));
			img1.addComponente(new Cerchio(3));
			img1.addComponente(new Rettangolo(1, 5));
			img1.addComponente(new Cerchio(1));
			img1.addComponente(new Rettangolo(2, 1));
			img1.addComponente(new Cerchio(2));
		}catch(VectorComponentOutOfLimit e) {
			e.printStackTrace();
		}
		System.out.println("\noggetti grafici");
		System.out.println(img1);

		System.out.println("\nArea totale: " + Util.format(img1.getSommaAree()));

		for (Colore c
				: Colore.values()) {
			System.out.println("Area " + c.name()
			+ ": " + Util.format(img1.getSommaAree(c)));
		}
		System.out.println("\n");

	}

}
