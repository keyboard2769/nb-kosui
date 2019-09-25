/*
 * Copyright (C) 2019 Key Parker from K.I.C
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * i could not find out why swing does not have a canvas 
 * while AWT had one.<br>
 * i still cant figure out why this is the way i am supposed to 
 * deal with it.<br>
 */
public class ScCanvas extends JPanel{
  
  private final List<SiPaintable> cmPaintList
    = new ArrayList<SiPaintable>();
  
  private final JComponent cmCanvas = new JComponent() {
    @Override public void paint(Graphics g) {
      if(cmPaintList.isEmpty()){return;}
      for(SiPaintable it:cmPaintList){it.ccPaint(g);}
    }//+++
  };//***
  
  /**
   * pass minus value to void the size setting.<br>
   * @param pxW pix
   * @param pxH pix
   */
  public ScCanvas(int pxW, int pxH) {
    super(new BorderLayout());
    add(cmCanvas,BorderLayout.CENTER);
    if (pxW > 0 && pxH > 0) {
      setPreferredSize(new Dimension(pxW, pxH));
    }//..?
  }//+++
  
  //===
  
  /**
   * practically, these objects is a mimic to local shape objects.<br>
   * swing has what it has to has, all i needed is some free passive one.<br>
   * @param pxShape do not pass null
   */
  public final void ccAddPaintObject(SiPaintable pxShape){
    if(pxShape==null){return;}
    if(cmPaintList.contains(pxShape)){return;}
    cmPaintList.add(pxShape);
  }//+++
  
  /**
   * calls updateUI() than repaint().<br>
   */
  public final void ccRefresh(){
    cmCanvas.updateUI();
    cmCanvas.repaint();
  }//+++
  
  /**
   * alias for JComponent.getFontMetrics
   * @return could be null?
   */
  public final FontMetrics ccGetMetrics(){
    return cmCanvas.getFontMetrics(ccGetFont());
  }//+++
  
  /**
   * alias for JComponent.getFont
   * @return could be null?
   */
  public final Font ccGetFont(){
    return cmCanvas.getFont();
  }//+++
  
  /**
   * alias for JComponent.setFont
   * @param pxFont do not pass null
   */
  public final void ccSetFont(Font pxFont){
    if(pxFont==null){return;}
    cmCanvas.setFont(pxFont);
  }//+++
  
}//***eof
