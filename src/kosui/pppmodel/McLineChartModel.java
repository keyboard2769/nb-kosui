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

import java.util.Arrays;
import kosui.ppputil.VcNumericUtility;

/**
 *.<br>
 *.<br>
 */
public class McLineChartModel {
  
  
  //===
  
  private final int[] cmDesOffsetX;
  private final int[] cmDesOffsetY;
  


  /**
   * all logged data will get initiated as 0.<br>
   * all offset point will get initiated as 0.<br>
   */
  public McLineChartModel() {
    
    cmDesOffsetX  = new int[0];
    cmDesOffsetY = new int[0];
    

    ccValidateOffsets(0, 0);
  }//+++
  
  //===
  
  public final void ccValidateOffsets(int pxDivisionWidth, int pxFullHeight){
    /* 6 */
    Arrays.fill(cmDesOffsetX, -1);
    Arrays.fill(cmDesOffsetY, -1);
  }//+++
  
  //===
  

  
  //===
  

  //===
  
  public final int ccGetOffsetX(int pxIndex){/* 6 */return 0;}//+++
  
  public final int ccGetOffsetY(int pxIndex){/* 6 */return 0;}//+++
  
  //===

  
}//***eof
