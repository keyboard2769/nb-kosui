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

package kosui.pppmodel;

import processing.data.IntList;
import processing.data.StringList;

import java.util.HashMap;

import static kosui.ppputil.VcConst.ccIsValidString;
import static kosui.ppputil.VcConst.ccLogln;
import kosui.ppputil.VcNumericUtility;
import static processing.core.PApplet.println;
import static processing.core.PApplet.parseInt;

/**
 * for some handy input command parsing. everything is finally integer. <br>
 * you can let user get to type a string like "addRect 12 12 12 12 -l -t"
 * and parse it to invoke your own addRect(12,12,12,12,LEFT,TOP)
 * at somewhere of your sketch. <br>
 */
public class McCommand{

  /**
   * value of minus one.<br>
   * a undefined parameter value or bad index will lead to this.<br>
   */
  static public final int C_INVALID_PARAM=-1;
  
  /**
   * value of zero.<br>
   * a undefined command will lead to this.<br>
   */
  static public final int C_EMPTY_CMD=0;
  
  static private final HashMap<String, Integer> O_CMD_MAP=new HashMap();

  private String cmInstruction;

  private int cmCommandID;

  private int[] cmIntegerParameter;
  private String[] cmStringParameter;
  
  //=== builder
  
  /**
   * alias version combines setting and parsing and constructs a new 
   * object. <br>
   * can be avoided by using setCommand() and parse() separately. <br>
   * @param pxInstruction #
   * @return #
   */
  static public McCommand ccParseCommand(String pxInstruction){
    McCommand lpRes=new McCommand(pxInstruction);
    lpRes.ccParse();
    return lpRes;
  }//+++

  /**
   * 
   * @param pxInstruction as "command param1 param2 param3 ..."
   */
  public McCommand(String pxInstruction){
    ccSetCommand(pxInstruction);
  }//+++
  
  /**
   * 
   * @return true if the command id is defined
   */
  public final boolean ccParse(){
    
    //-- pre check
    if(O_CMD_MAP==null){return false;}
    if(!ccIsValidString(cmInstruction)){return false;}
    
    //-- get command id
    String[] lpTokens=cmInstruction.split(" ");
    if(lpTokens==null){return false;}
    if(lpTokens.length==0){return false;}
    Integer lpCommandID=O_CMD_MAP.get(lpTokens[0]);
    if(lpCommandID!=null){cmCommandID=lpCommandID;}
    
    //-- get params value
    IntList lpIntegerList=new IntList();
    StringList lpStringList=new StringList();
    for(int i=1,s=lpTokens.length;i<s;i++){
      String lpToken=lpTokens[i];
      if(VcNumericUtility.ccIsIntegerString(lpToken)){
        lpIntegerList.append(parseInt(lpToken));
      }else{
        lpStringList.append(lpToken);
        lpIntegerList.append(
          O_CMD_MAP.containsKey(lpToken)?
          O_CMD_MAP.get(lpToken):C_INVALID_PARAM
        );
      }//..?
    }//..~
    cmIntegerParameter=lpIntegerList.array();
    cmStringParameter=lpStringList.array();
    
    //-- end
    return cmCommandID!=0;
    
  }//+++
  
  //=== modifier
  
  /**
   * parsed integer value is based on these definitions.<br>
   * @param pxCommand #
   * @param pxID #
   */
  static public final void ccDefineCommand(String pxCommand, int pxID){
    O_CMD_MAP.put(pxCommand, pxID);
  }//+++
  
  /**
   * note this does NOT automatically parse the command.<br>
   * @param pxInstruction as "command param1 param2 param3 ..."
   */
  public final void ccSetCommand(String pxInstruction){
    cmInstruction=pxInstruction;
    cmCommandID=C_EMPTY_CMD;
    cmIntegerParameter=null;
  }//+++

  //=== supportor
  
  /**
   * for test use only
   */
  public final void ccReadUp(){
    ccLogln("inputed:",cmInstruction);
    ccLogln("commandID:",cmCommandID);
    println("integer_param:"+ccGetIntegerParameterCount());
    if(ccGetIntegerParameterCount()>=0){println(cmIntegerParameter);}
    println("string_param:"+ccGetStringParameterCount());
    if(ccGetStringParameterCount()>=0){println(cmStringParameter);}
  }//+++
  
  //=== 
  
  /**
   * @return zero means nothing
   */
  public final int ccGetCommandID(){
    return cmCommandID;
  }//+++
  
  /**
   * using getParam() to get a single value but not this to get the 
   * whole array is recommended.<br>
   * @return can be null
   */
  public final int[] ccGetIntegerParameter(){
    return cmIntegerParameter;
  }//+++
  
  /**
   * @return invalid constant if parameters is not initiated.
   */
  public final int ccGetIntegerParameterCount(){
    if(cmIntegerParameter==null){return C_INVALID_PARAM;}
    return cmIntegerParameter.length;
  }//+++
  
  /**
   * parsed parameter.<br>
   * @param pxIndex #
   * @return -1 if something went wrong
   */
  public final int ccGetIntegerParameter(int pxIndex){
    if(cmIntegerParameter==null){return C_INVALID_PARAM;}
    if(pxIndex<0 && pxIndex>=cmIntegerParameter.length)
      {return C_INVALID_PARAM;}
    return cmIntegerParameter[pxIndex];
  }//+++
  
  /**
   * @return can be null
   */
  public final String[] ccGetStringParameter(){
    return cmStringParameter;
  }//+++
  
  /**
   * @return invalid constant if parameters is not initiated.
   */
  public final int ccGetStringParameterCount(){
    if(cmStringParameter==null){return C_INVALID_PARAM;}
    return cmStringParameter.length;
  }//+++
  
  /**
   * stacked parameter 
   * @param pxIndex #
   * @return *NN* if something went wrong
   */
  public final String ccGetStringParameter(int pxIndex){
    if(cmStringParameter==null){return "<nn/>";}
    if(pxIndex<0 && pxIndex>=cmIntegerParameter.length){return "<nn/>";}
    return cmStringParameter[pxIndex];
  }//+++
  
}//***eof
