
package it.unipv.po.printer.test;

import it.unipv.po.printer.colorModel.Colore;
import it.unipv.po.printer.dataModel.forme.Cerchio;
import it.unipv.po.printer.dataModel.forme.Rettangolo;
import it.unipv.po.printer.imageModel.Foto;
import it.unipv.po.printer.imageModel.ImgVett;
import it.unipv.po.printer.imageModel.exception.VectorComponentOutOfLimit;
import it.unipv.po.printer.inkJetprinter.InkJetPrinter;
import it.unipv.po.printer.inkJetprinter.utils.Cartridge;
import it.unipv.po.printer.inkJetprinter.utils.exception.CartridgeEmpty;


public class TestPrinter {

	public static void main(String[] args) {
		InkJetPrinter printer = new InkJetPrinter();

		ImgVett images[] = new ImgVett[3];
		for (int i = 0; i < images.length; i++) {
			images[i] = new ImgVett();
		}
		try {
			images[0].addComponente(new Rettangolo(1, 1));
			images[0].addComponente(new Rettangolo(2, 1));
			images[0].addComponente(new Cerchio(1));
			images[0].addComponente(new Rettangolo(5, 3));
		}catch (VectorComponentOutOfLimit e) {
			e.printStackTrace();
		}try {
			images[1].addComponente(new Cerchio(3));
			images[1].addComponente(new Cerchio(4));
			images[1].addComponente(new Rettangolo(2, 2));
			images[1].addComponente(new Cerchio(4));
		}catch (VectorComponentOutOfLimit e) {
			e.printStackTrace();
		}
		try {
			images[2].addComponente(new Rettangolo(2, 6));
			images[2].addComponente(new Rettangolo(3, 4));
		}catch (VectorComponentOutOfLimit e) {
			e.printStackTrace();
		}

		printer.getCartridgesLeve();
		for (ImgVett img : images) {
			System.out.println("-----------------");
			System.out.println("stampa in corso\n" + img);
			try {
				printer.stampa(img);
			} catch (CartridgeEmpty e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			printer.getCartridgesLeve();
		}

		//Let's print Fotos too! NOTE: on a new printer with bigger cartridges

		System.out.println("-----NEW PRINTER------");
		InkJetPrinter fotoPrinter = new InkJetPrinter();
		fotoPrinter.getCartridgesLeve();

		Foto fotos[] = new Foto[3];
		for (int i = 0; i < fotos.length; i++) {
			fotos[i] = new Foto(i+3);
		}
		for (Foto pic : fotos) {
			System.out.println("-----------------");
			System.out.println("stampa in corso\n" + pic);
			try {
				fotoPrinter.stampa(pic);
			} catch (CartridgeEmpty e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fotoPrinter.getCartridgesLeve();
		}

		System.out.println("-----Finally print also an ImgVett with the second printer------");
		System.out.println("stampa in corso\n" + images[0]);
		try {
			fotoPrinter.stampa(images[0]);
		} catch (CartridgeEmpty e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fotoPrinter.getCartridgesLeve();

		fotoPrinter.changeCartridge(new Cartridge(Colore.BLUE,0));
		try {
			fotoPrinter.stampa(images[0]);
		} catch (CartridgeEmpty e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fotoPrinter.getCartridgesLeve();

	}
}
