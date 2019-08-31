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

import java.util.HashMap;
import javax.swing.SwingUtilities;
import kosui.ppplocalui.ViOperable;
import kosui.ppputil.VcConst;

/**
 * a stupid mechanism to ensure the draw() from your sketch only 
 * response to one thing from swing at any given time. <br>
 * commands should be defined from outside. <br>
 */
public final class VcKeeper {
  
  private static final String
    C_INVALID     = "",
    C_DEFAULT_SEP = ","
  ;//...
  
  //===

  private static volatile String cmCommand=C_INVALID;
  private static volatile String cmSeparator=",";
  private static final HashMap<String, ViOperable> O_MAP
    =new HashMap<String, ViOperable>();

  //===

  /**
   * supposedly this should be called from draw loop
   */
  public static final void ccKeep(){
    if(VcConst.ccIsValidString(cmCommand)){
      String[] lpLine=cmCommand.split(cmSeparator);
      if(O_MAP.containsKey(lpLine[0]))
        {O_MAP.get(lpLine[0]).ccOperate(lpLine);}
      else{
        SwingUtilities.invokeLater(new Runnable() {
          @Override public void run(){
            ScFactory.ccMessageBox("unhandled_command:"+cmCommand);
          }//+++
        }); 
      }//..?
      cmCommand=C_INVALID;
    }//..?
  }//+++

  //===
  
  /**
   * a command serves as a key to the hash-map. 
   * an inputed command may get split by passed separator. <br>
   * supposedly the default one is ",". <br>
   * @param pxSeparator #
   */
  synchronized public static final
  void ccSetSeparator(String pxSeparator){
    if(pxSeparator==null){return;}
    if(pxSeparator.length()!=1){return;}
    cmSeparator=pxSeparator;
  }//+++
  
  /**
   * will be set to default one.
   */
  synchronized public static final
  void ccSetSeparator(){
    cmSeparator=C_DEFAULT_SEP;
  }//+++
  
  /**
   * 
   * @param pxCommand does check for existence
   * @param pxOperation # 
   */
  public static final 
  void ccAddOperation(String pxCommand, ViOperable pxOperation){
    if(!O_MAP.containsKey(pxCommand)){
      O_MAP.put(pxCommand, pxOperation);
    }//..?
  }//+++
  
  /**
   * 
   * @param pxCommand #
   */
  synchronized public static final 
  void ccSetCommand(String pxCommand)
    {cmCommand=pxCommand;}//+++
  
}//***eof
