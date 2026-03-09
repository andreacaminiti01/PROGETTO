/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unipv.po.printer.inkJetprinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.unipv.po.printer.colorModel.Colore;
import it.unipv.po.printer.dataModel.Printable;
import it.unipv.po.printer.inkJetprinter.utils.Cartridge;
import it.unipv.po.printer.inkJetprinter.utils.exception.CartridgeEmpty;

public class InkJetPrinter {

    private Map<Colore,Cartridge> cartridges;

    public InkJetPrinter() {
  
        this.cartridges = new HashMap<Colore,Cartridge>();
        initCartridges();
    }

    /*
     * Init the cartridges to the maximum size possible.
     * Later, it could be possible to add cartridges with levels different from the maximum value
     */
    private void initCartridges(){
        for (Colore c : Colore.values()) {
            this.cartridges.put(c,new Cartridge(c, Cartridge.MAXLEVEL));
        }
    }
    public void changeCartridge(Cartridge c) {
        this.cartridges.put(c.getColor(),c);
    }

    public void stampa(Printable itemToPrint) throws CartridgeEmpty{
        for (Colore c : Colore.values()) {
            (this.cartridges.get(c)).useColor(itemToPrint.getNeededInk(c));
        }
    }
    
    public void getCartridgesLeve(){
        System.out.println("Livelli delle cartucce");
        for(Colore c : Colore.values()){
            System.out.println(Colore.valueOf(c.name()) + " " + cartridges.get(c).getLevel());
        }
        System.out.println("");
    }
}
