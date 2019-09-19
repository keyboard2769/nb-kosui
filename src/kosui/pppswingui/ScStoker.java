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

import java.awt.print.PrinterException;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcStringUtility;

/**
 * basically just a text area in a scroll pane. <br>
 * but i think this is a better name for text area. <br>
 */
public class ScStoker extends JScrollPane {

  private final JTextArea cmArea;
  
  //===

  /**
   * construct JScrollPane and JTextArea with a hello message.<br>
   * @param pxHello can be anything
   * @param pxR max count
   * @param pxC max count
   */
  public ScStoker(String pxHello, int pxR, int pxC) {
    super();
    cmArea = new JTextArea(VcStringUtility.ccNulloutString(pxHello));
    ScFactory.ccSetupConsoleArea(cmArea);
    if(pxR>2 && pxC>4){
      cmArea.setRows(pxR);
      cmArea.setColumns(pxC);
    }//..?
    super.setViewportView(cmArea);
  }//++!
  
  //===

  /**
   * alias for JTextArea::append().<br>
   * re-direct viewport location via text selection setting.<br>
   * @param pxTag must have something
   * @param pxValue can be any thing
   */
  public final void ccWriteln(String pxTag, Object pxValue){
    if(!ScConst.ccIsEDT()){return;}
    if(!VcConst.ccIsValidString(pxTag)){return;}
    if(pxValue==null){
      cmArea.append(pxTag);
      cmArea.append(VcConst.C_V_NEWLINE);
    }else{
      cmArea.append(pxTag);
      cmArea.append(":");
      cmArea.append(pxValue.toString());
      cmArea.append(VcConst.C_V_NEWLINE);
    }//..?
    ScConst.ccScrollToLast(cmArea);
  }//+++
  
  /**
   * might get passed to JTextArea::append() eventually.<br>
   * @param pxLine must have some thing.
   */
  public final void ccWriteln(String pxLine) {
    ScStoker.this.ccWriteln(pxLine, null);
  }//+++
  
  /**
   * alias for JTextArea::setText("");
   */
  public final void ccClear(){
    cmArea.setText("");
  }//+++
  
  /**
   * clear with a initiation message
   * @param pxHello do not pass null
   */
  public final void ccClear(String pxHello){
    if(pxHello==null){ccClear();return;}
    if(pxHello.length()>64){ccClear();return;}//..arbitary
    cmArea.setText(pxHello);
  }//+++
  
  //===
  
  /**
   * @return the area
   */
  public final JTextArea ccGetTextArea(){
    return cmArea;
  }//+++
  
  /**
   * wrapper for JTextArea::getText
   * @return #
   */
  public final String ccGetText(){
    return cmArea.getText();
  }//+++
  
  //===
  
  /**
   * wrapper for JTextArea::print(). <br>
   * for advanced use you should create a print job outside.
   */
  public final void ccViewPortPrint(){
    try{
      cmArea.print();
    }catch(PrinterException e){
      System.out.println("kosui.pppswingui.ScConsole.ccPrint()::"
        + "PrinterException:"+e.getLocalizedMessage());
    }//..%
  }//+++
  
}//***eof
