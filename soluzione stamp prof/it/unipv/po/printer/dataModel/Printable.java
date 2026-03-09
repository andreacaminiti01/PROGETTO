package it.unipv.po.printer.dataModel;

import it.unipv.po.printer.colorModel.Colore;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public interface Printable {
    public double getNeededInk(Colore c);    
}
