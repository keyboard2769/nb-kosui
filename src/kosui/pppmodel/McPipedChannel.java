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

import kosui.ppputil.VcNumericUtility;

/**
 * a pipe is a stuff bunks whatever things out.<br>
 * and a channel is a brick with sixteen pipe wholes on it.<br>
 * 
 */
public final class McPipedChannel {
  
  /**
   * hard coded
   */
  public static final int C_MAX  = 16;
  
  /**
   * hard coded
   */
  public static final int C_MASK = 15;
  
  private volatile int
    vmXnZ,vmXnI,vmXnII,vmXnIII,
    vmXnIV,vmXnV,vmXnVI,vmXnVII,
    //--
    vmXnVIII,vmXnIX,vmXnX,vmXnXI,
    vmXnXII,vmXnXIII,vmXnXIV,vmXnXV
  ;//,,,
  
  //===
  
  /**
   * @param pxOrder will get masked to 0 - 15
   * @param pxVal can be anything
   */
  public final void ccSet(int pxOrder, int pxVal){
    switch(pxOrder&C_MASK){
      case  0:vmXnZ=pxVal;break;
      case  1:vmXnI=pxVal;break;
      case  2:vmXnII=pxVal;break;
      case  3:vmXnIII=pxVal;break;
      case  4:vmXnIV=pxVal;break;
      case  5:vmXnV=pxVal;break;
      case  6:vmXnVI=pxVal;break;
      case  7:vmXnVII=pxVal;break;
      //--
      case  8:vmXnVIII=pxVal;break;
      case  9:vmXnIX=pxVal;break;
      case 10:vmXnX=pxVal;break;
      case 11:vmXnXI=pxVal;break;
      case 12:vmXnXII=pxVal;break;
      case 13:vmXnXIII=pxVal;break;
      case 14:vmXnXIV=pxVal;break;
      case 15:vmXnXV=pxVal;break;
      //--
      default:break;
    }//..?
  }//++<
  
  /**
   * @param pxOrder will get masked to 0 - 15
   * @return 0 if anything went wrong
   */
  public final int ccGet(int pxOrder){
    switch(pxOrder&C_MASK){
      case  0:return vmXnZ;
      case  1:return vmXnI;
      case  2:return vmXnII;
      case  3:return vmXnIII;
      case  4:return vmXnIV;
      case  5:return vmXnV;
      case  6:return vmXnVI;
      case  7:return vmXnVII;
      //--
      case  8:return vmXnVIII;
      case  9:return vmXnIX;
      case 10:return vmXnX;
      case 11:return vmXnXI;
      case 12:return vmXnXII;
      case 13:return vmXnXIII;
      case 14:return vmXnXIV;
      case 15:return vmXnXV;
      //--
      default:return 0;
    }//..?
  }//++>
  
  //===
  
  /**
   * via VcNumericUtility.ccBinarySet
   * @param pxOrder will get passed to setter
   * @param pxBit will get passed to utility directly
   * @param pxVal could be anything
   */
  public final void ccSetBit(int pxOrder, int pxBit, boolean pxVal){
    int lpTarget = ccGet(pxOrder);
    lpTarget=VcNumericUtility.ccBinarySet(lpTarget, pxBit, pxVal);
    ccSet(pxOrder, lpTarget);
  }//++<
  
  /**
   * via VcNumericUtility.ccBinaryLoad
   * @param pxOrder will get passed to setter
   * @param pxBit will get passed to utility directly
   * @return tested result
   */
  public final boolean ccGetBit(int pxOrder, int pxBit){
    int lpTatget = ccGet(pxOrder);
    return VcNumericUtility.ccBinaryLoad(lpTatget, pxBit);
  }//++>
  
}//***eof
