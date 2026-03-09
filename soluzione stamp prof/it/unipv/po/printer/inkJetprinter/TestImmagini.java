/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unipv.po.printer.inkJetprinter;

import it.unipv.po.printer.dataModel.forme.Cerchio;
import it.unipv.po.printer.dataModel.forme.Rettangolo;
import it.unipv.po.printer.imageModel.ImgVett;
import it.unipv.po.printer.imageModel.exception.VectorComponentOutOfLimit;

import java.util.Arrays;


public class TestImmagini {

	public static void main(String[] args) {

		ImgVett images[] = new ImgVett[3];
		for (int i = 0; i < images.length; i++) {
			images[i] = new ImgVett();
		}
		try {
			images[0].addComponente(new Rettangolo(1, 1));
			images[0].addComponente(new Rettangolo(2, 1));
			images[0].addComponente(new Cerchio(1));
			images[0].addComponente(new Rettangolo(5, 3));

			images[1].addComponente(new Cerchio(3));
			images[1].addComponente(new Rettangolo(2, 2));
			images[1].addComponente(new Cerchio(4));

			images[2].addComponente(new Rettangolo(2, 6));
			images[2].addComponente(new Rettangolo(3, 4));
		}catch(VectorComponentOutOfLimit e) {
			e.printStackTrace();
		}

		System.out.println("Immagini");
		for (ImgVett img : images) {
			System.out.println(Util.format(img.getSommaAree()));
		}

		Arrays.sort(images);

		System.out.println("\nImmagini ordinate per area");
		for (ImgVett img : images) {
			System.out.println(Util.format(img.getSommaAree()));
		}

	}
}
