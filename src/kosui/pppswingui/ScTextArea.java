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
import java.awt.print.PrinterException;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * basically just a text area in a scroll pane. <br>
 * but i think this is a better name for text area. <br>
 */
public class ScTextArea extends JScrollPane {

  private final JTextArea cmArea;
  
  //===

  /**
   * 
   * @param pxR row
   * @param pxC column
   */
  public ScTextArea(int pxR, int pxC) {
    super();
    cmArea = new JTextArea("--standby::\n", pxR, pxC);
    cmArea.setEditable(false);
    cmArea.setEnabled(false);
    cmArea.setBackground(Color.BLACK);
    cmArea.setDisabledTextColor(Color.GREEN);
    setViewportView(cmArea);
  }//++!
  
  //===

  /**
   * may got blocked out from dispatch thread.
   * @param pxLine will be treated as a error if blocked out.
   */
  public final void ccStack(String pxLine) {
    if (ScConst.ccIsEDT()) {
      cmArea.append(pxLine + "\n");
      int lpLength = cmArea.getText().length();
      cmArea.setSelectionStart(lpLength - 2);
      cmArea.setSelectionEnd(lpLength - 1);
    }else{
      System.err.println(".ScConsole.ccStack()::blocked:" + pxLine);
    }
  }//+++
  
  /**
   * alias for JTextArea::setText("");
   */
  public final void ccClear(){
    cmArea.setText("");
  }//+++
  
  //===
  
  /**
   * 
   * @return #
   */
  public final JTextArea ccGetTextArea()
    {return cmArea;}//+++
  
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
  public final void ccPrint(){
    try{
      cmArea.print();
    }catch(PrinterException e){
      System.out.println("kosui.pppswingui.ScConsole.ccPrint()::"
        + "PrinterException:"+e.getLocalizedMessage());
    }//..%
  }//+++
  
}//***eof
