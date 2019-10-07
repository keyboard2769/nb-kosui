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

package kosui.pppmodel;

/**
 * a pipe is a stuff bunks whatever things out.<br>
 * and a channel is a brick with sixteen pipe wholes on it.<br>
 * 
 */
public final class McPipedChannel {
  
  public final int C_MAX  = 16;
  public final int C_MASK = 15;
  
  private volatile int
    mnCTSlotZ,mnCTSlotI,mnCTSlotII,mnCTSlotIII,
    mnCTSlotIV,mnCTSlotV,mnCTSlotVI,mnCTSlotVII,
    //--
    mnCTSlotVIII,mnCTSlotIX,mnCTSlotX,mnCTSlotXI,
    mnCTSlotXII,mnCTSlotXIII,mnCTSlotXIV,mnCTSlotXV
  ;//,,,
  
  //===
  
  public final void ccSet(int pxOrder, int pxVal){
    switch(pxOrder&C_MASK){
      case  0:mnCTSlotZ=pxVal;break;
      case  1:mnCTSlotI=pxVal;break;
      case  2:mnCTSlotII=pxVal;break;
      case  3:mnCTSlotIII=pxVal;break;
      case  4:mnCTSlotIV=pxVal;break;
      case  5:mnCTSlotV=pxVal;break;
      case  6:mnCTSlotVI=pxVal;break;
      case  7:mnCTSlotVII=pxVal;break;
      //--
      case  8:mnCTSlotVIII=pxVal;break;
      case  9:mnCTSlotIX=pxVal;break;
      case 10:mnCTSlotX=pxVal;break;
      case 11:mnCTSlotXI=pxVal;break;
      case 12:mnCTSlotXII=pxVal;break;
      case 13:mnCTSlotXIII=pxVal;break;
      case 14:mnCTSlotXIV=pxVal;break;
      case 15:mnCTSlotXV=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  public final int ccGet(int pxOrder){
    switch(pxOrder&C_MASK){
      case  0:return mnCTSlotZ;
      case  1:return mnCTSlotI;
      case  2:return mnCTSlotII;
      case  3:return mnCTSlotIII;
      case  4:return mnCTSlotIV;
      case  5:return mnCTSlotV;
      case  6:return mnCTSlotVI;
      case  7:return mnCTSlotVII;
      //--
      case  8:return mnCTSlotVIII;
      case  9:return mnCTSlotIX;
      case 10:return mnCTSlotX;
      case 11:return mnCTSlotXI;
      case 12:return mnCTSlotXII;
      case 13:return mnCTSlotXIII;
      case 14:return mnCTSlotXIV;
      case 15:return mnCTSlotXV;
      //--
      default:return 0;
    }//..?
  }//++>
  
}//***eof
