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

package kosui.ppputil;

import processing.data.StringList;
import static processing.core.PApplet.join;

/**
 * stacks lines as a combined huge string for showing logs.<br>
 * you might want to link a bunch of them together.<br>
 */
public class McLineStacker {
  
  private int cmTrimSize=512;
  private int cmTrimDivisor=2;
  private String cmStacker="";
  
  //===
  
  /**
   * if the texts stacked reached the size passed,
   * it will be trimmed based on line number and the divisor passed.
   * the default size is 512, with divisor as 2;
   * @param pxSize will be masked to 31-65535, 1023 recommended
   * @param pxDivisor don't pass zero, 4 recommended
   */
  public final void ccSetTrim(int pxSize, int pxDivisor){
    if(pxDivisor<2){return;}
    cmTrimSize=((pxSize|0x3F)&0xFFFF);cmTrimDivisor=pxDivisor&0x0F;
  }//+++
  
  //===
  
  /**
   * tagged value version
   * @param pxTag will be catted a colon
   * @param pxVal toString() will be called.
   */
  public final void ccStack(String pxTag, Object pxVal)
    {ccStack(pxTag+":"+pxVal);}//+++
  
  /**
   * value version 
   * @param pxVal toString() will be called.
   */
  public final void ccStack(Object pxVal)
    {ccStack(pxVal.toString());}//+++
  
  /**
   * stacks text to show.<br>
   * a new line will added at head. (<b>NOT</b> the tail)<br>
   * @param pxLine the text
   */
  public final void ccStack(String pxLine){
    cmStacker=cmStacker+"\n"+pxLine;
    if(cmStacker.length()>cmTrimSize){ccTrimStack();}
  }//+++
  
  private void ccTrimStack(){
    StringList lpList=new StringList(cmStacker.split("\n"));
    int lpLength=lpList.size();
    if(lpLength<=cmTrimDivisor){
      cmStacker=cmStacker.substring(0,cmStacker.length()/cmTrimDivisor);
      return;
    }
    for(int i=0,s=lpLength/cmTrimDivisor;i<s;i++){lpList.remove(i);}
    cmStacker=join(lpList.array(),"\n");
  }//+++
  
  /**
   * clear all lines.
   */
  public final void ccClear()
    {cmStacker="";}//+++
  
  /**
   * @param pxDefault all lines will be replaced to this
   */
  public final void ccClear(String pxDefault)
    {cmStacker=pxDefault;}//+++
  
  //===
  
  /**
   * @return length of stacked text
   */
  public final int ccGetSize(){return cmStacker.length();}//+++
  
  /**
   * @return #
   */
  public final String ccGetStacked(){return cmStacker;}//+++
  
}//***eof