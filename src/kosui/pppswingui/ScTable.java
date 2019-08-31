/*
 * Copyright (C) 2018 Key Parker from K.I.C.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package kosui.pppswingui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.print.PrinterException;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * it actually pissed me off at first i create every pane for a table. <br>
 * know i see separate them is not a bad idea at all. <br>
 */
public final class ScTable extends JScrollPane {

  private final JTable cmTable;

  //===
  
  /**
   * selection mode will be set single mode
   * @param pxModel we have a adaptor in this package 
   * @param pxW width
   * @param pxH height
   */
  public ScTable(TableModel pxModel, int pxW, int pxH) {
    super();
    cmTable = new JTable(pxModel);
    cmTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    cmTable.getTableHeader().setReorderingAllowed(false);
    setViewportView(cmTable);
    if (pxW > 0 && pxH > 0) {
      setPreferredSize(new Dimension(pxW, pxH));
    }//..?
  }//+++
  
  //===
  
  private int ccFixIndex(int pxIndex) {
    int lpMasked = pxIndex & 0xFFFF;
    int lpSize = cmTable.getColumnModel().getColumnCount();
    return lpMasked > lpSize ? lpSize - 1 : lpMasked;
  }//+++

  //===
  
  /**
   * 
   * @param pxFore text
   * @param pxBack background
   * @param pxGrid grid lines
   */
  public final void ccSetColor
    (Color pxFore, Color pxBack, Color pxGrid)
  { cmTable.setForeground(pxFore);
    cmTable.setBackground(pxBack);
    cmTable.setGridColor(pxGrid);
  }//+++

/**
 * 
 * @param pxState #
 */
  public final void ccSetEnabled(boolean pxState)
  {cmTable.setEnabled(pxState);}//+++

  /**
   * 
   * @param pxIndex #
   * @param pxWidth #
   */
  public final void ccSetColumnWidth
    (int pxIndex, int pxWidth)
  { TableColumn lpColumn = cmTable.getColumnModel()
      .getColumn(ccFixIndex(pxIndex));
    lpColumn.setMinWidth(0x04);
    lpColumn.setMaxWidth(0xFF);
    lpColumn.setPreferredWidth(pxWidth);
  }//+++

  /**
   * 
   * @param pxModel #
   */
  public final void ccSetModel(TableModel pxModel) {
    cmTable.setModel(pxModel);
  }//+++
  
  //===
  
  /**
   * 
   * @return #
   */
  public final JTable ccGetTable(){
    return cmTable;
  }//+++
  
  /**
   * does NOT check for EDT.
   * @return getSelectedRow() of JTable.
   */
  public final int ccGetSelectedRowIndex(){
    return cmTable.getSelectedRow();
  }//+++
  
  //===
  
  /**
   * calls both repaint() and updateUI() of JTable. 
   */
  public final void ccRefresh(){
    if(ScFactory.ccIsEDT()){
      cmTable.repaint();
      cmTable.updateUI();
    }//..?
  }//+++
  
  /**
   * calls repaint() of JTable.
   */
  public final void ccRepaintTable(){
    if(ScFactory.ccIsEDT()){
      cmTable.repaint();
    }//..?
  }//+++
  
  /**
   * calls updateUI() of JTable. <br>
   * for the situation that modification does not show up by repainting,
   * actually i don t know why it is necessary. <br>
   */
  public final void ccUpdateTable(){
    if(ScFactory.ccIsEDT()){
      cmTable.updateUI();
    }//..?
  }//+++
  
  //===
  
  /**
   * wrapper for JTable::print(). <br>
   * for advanced use you should create a print job outside.
   */
  public final void ccPrint(){
    try{
      cmTable.print();
    }catch(PrinterException ex){
      System.out.println("kosui.pppswingui.ScConsole.ccPrint()::"
        + "PrinterException:"+ex.getLocalizedMessage());
    }//..%
  }//+++

}//***eof
