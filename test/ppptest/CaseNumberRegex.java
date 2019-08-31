/*
 * Copyright (C) 2019 Key Parker
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

import static kosui.ppputil.VcConst.ccIsFloatString;
import static kosui.ppputil.VcConst.ccIsIntegerString;

public class CaseNumberRegex{
  public static void main(String[] args){
    System.out.println("CaseNumberRegex.main()::start");
    //--
        
    String[] lpInputTest={
      "0","00","000","1","2","4","08","004","4d","-256",
      "-26","-3","-09","-009",
      "0.0","0.003","0.52","1.2","1.32457","1.258","-1.5","-1.22222",
      "2.3.4"
    };
    
    System.out.println("=== itInteger?? ===");
    for(String it : lpInputTest){
      kosui.ppputil.VcConst.ccLogln(it, ccIsIntegerString(it));
    }//+++
    
    System.out.println("=== itFloat?? ===");
    for(String it : lpInputTest){
      kosui.ppputil.VcConst.ccLogln(it, ccIsFloatString(it));
    }//+++
    
    //--
    System.out.println("CaseNumberRegex.main()::over");
  }//!!!
}//***eof
