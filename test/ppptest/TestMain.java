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

package ppptest;

import static processing.core.PApplet.println;

import kosui.ppplocalui.*;
import kosui.ppplogic.*;
import kosui.pppswingui.*;
import kosui.ppputil.*;
import processing.core.PApplet;

public class TestMain{
  
  static private final int[] O_CASE=new int[]{
    -3,-992,-1,0,1,12,123,
    234,345,456,567,678,789,899,987,
    999,1000,1001,
    1123,1234,1345,1456,1567,1678,1789,1899,1900,
    2123,2234,2345,2456,2500,2567,2678,2789,2899,2999,
    3123,3234,3345,3456,3567,3678,3789,3899,3999,
    4000
  };
  
  private static void ssTestComparator(){
      ZcLevelComparator lpC= new ZcLevelComparator(3200);
    
      lpC.tstReadup();
    
    for(int it:O_CASE){
      VcConst.ccPrintln(Integer.toString(it), lpC.ccComparate(it));
    }
  }

  
  public static void main(String[] args){
    System.out.println("TestMain.main()::hellow:");
    //--
    
    VcConst.ccPrintln("exc??", ZcRangedModel.ccLimitExclude(3, 10, 20));
    
      ZcLevelComparator lpC= new ZcLevelComparator(3200);
      lpC.ccSetLevelValue(16, 9966);
      lpC.tstReadup();
    
    
    //--
    System.out.println("TestMain.main()::over");
  }//+++
  
}//***eof
