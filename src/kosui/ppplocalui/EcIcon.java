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

package kosui.ppplocalui;

import kosui.ppputil.VcNumericUtility;
import processing.core.PGraphics;

/**
 * an icon is a symbol representing a stuff and responding to you.<br>
 * actually it has no difference to a lamp expect you have to handle the 
 * draw code outside.<br>
 */
public class EcIcon extends EcElement{
  
  private final PGraphics cmOnGraph, cmOffGraph;
  
  /**
   * if any of them has been passed null than 
   * this is a normal EcElment.<br>
   * @param pxOnGraph will get shown if activated
   * @param pxOffGraph will get shown if not activated
   */
  public EcIcon(PGraphics pxOnGraph, PGraphics pxOffGraph){
    super();
    if(pxOnGraph==null || pxOffGraph==null){
      cmOnGraph=null;
      cmOffGraph=null;
    }else{
      int cmFixedW=VcNumericUtility.ccGreater
        (pxOnGraph.width, pxOffGraph.width);
      int cmFixedH=VcNumericUtility.ccGreater
        (pxOnGraph.height, pxOffGraph.height);
      ccSetSize(cmFixedW,cmFixedH);
      cmOnGraph=pxOnGraph;
      cmOffGraph=pxOffGraph;
    }//+++
    
  }//+++

  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate(){
    if(!ccIsVisible()){return;}
    if(cmOnGraph==null || cmOffGraph==null){
      super.ccUpdate();
    }else{
      pbOwner.image(
        cmIsActivated?cmOnGraph:cmOffGraph,
        cmX, cmY,cmW,cmH
      );
    }//..?
  }//+++
  
}//***eof
