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

import processing.core.PGraphics;

/**
 * so before i know that an action can be a property, how could i
 * figure out how to make a sequence a property?<br>
 * but actually this is a better way. 
 * maybe i will make all the local stuff a graph someday.<br>
 */
public class EcGraph extends EcShape{
  
  private final PGraphics cmBaseGraph;
  
  /**
   * size will get applied to.
   * @param pxSource if passed, you got a normal EcShape.
   */
  public EcGraph(PGraphics pxSource){
    super();
    if(pxSource==null){
      cmBaseGraph=null;
    }else{
      ccSetSize(pxSource.width, pxSource.height);
      cmBaseGraph=pxSource;
    }//+++
  }//+++ 

  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate(){
    if(!ccIsVisible()){return;}
    if(cmBaseGraph==null){
      super.ccUpdate();
    }else{
      pbOwner.image(cmBaseGraph, cmX, cmY,cmW,cmH);
    }//..?
  }//+++
  
}//***eof
