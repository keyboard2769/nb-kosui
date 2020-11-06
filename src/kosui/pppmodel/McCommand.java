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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcStringUtility;
import processing.core.PApplet;

/**
 * for some handy input command parsing. everything is finally integer. <br>
 * you can let user get to type a string like "addRect 12 12 12 12 -l -t"
 * and parse it to invoke your own addRect(12,12,12,12,LEFT,TOP)
 * at somewhere of your sketch. <br>
 */
public abstract class McCommand implements MiExecutable{
  
  /**
   * hard coded
   */
  public static final int
    C_M_GENERAL_SUCCESS =   0,
    C_M_GENERAL_FAILIOR =  -1,
    C_M_GENERAL_UNKNOWN =  -2,
    C_M_UNREGISTERED    = -63
  ;//,,,
  
  private static final HashMap<String, McCommand> O_MAP_OF_REGISTERED
    = new HashMap<String, McCommand>();
  
  /**
   * @param pxName do not pass null
   * @param pxExecution do not pass null
   */
  public static final void ccRegister(String pxName, McCommand pxExecution){
    if(!VcConst.ccIsValidString(pxName)){return;}
    if(pxExecution==null){return;}
    if(O_MAP_OF_REGISTERED.containsKey(pxName)){return;}
    O_MAP_OF_REGISTERED.put(pxName, pxExecution);
  }//+++
  
  /**
   * @param pxExecution do not pass null
   */
  public static final void ccRegister(McCommand pxExecution){
    if(pxExecution==null){return;}
    ccRegister(pxExecution.ccGetName(), pxExecution);
  }//+++
  
  /**
   * @param pxInput must have something
   * @return see those constants
   */
  @Override public int ccExecute(String pxInput){
    if(!VcConst.ccIsValidString(pxInput)){return C_M_GENERAL_FAILIOR;}
    String[] lpSpaceBreak = pxInput.split(" ");
    if(lpSpaceBreak==null){return C_M_GENERAL_FAILIOR;}
    if(lpSpaceBreak.length<1){return C_M_GENERAL_FAILIOR;}
    String lpName=lpSpaceBreak[0];
    if(!O_MAP_OF_REGISTERED.containsKey(lpName)){return C_M_UNREGISTERED;}
    McCommand lpExecution = O_MAP_OF_REGISTERED.get(lpName);
    if(lpExecution == null){return C_M_GENERAL_UNKNOWN;}
    lpExecution.ccParse(pxInput);
    return lpExecution.ccExecute();
  }//+++
  
  //=== 
  
  /**
   * inward use only.<br>
   */
  protected String cmRawHead = null;
  
  /**
   * inward use only.<br>
   */
  protected String[] cmDesRawBody = null;
  
  /**
   * inward use only.<br>
   */
  protected List<String> cmListOfOptionHead = new ArrayList<String>();
  
  /**
   * inward use only.<br>
   */
  protected List<String[]> cmListOfOptionBody= new ArrayList<String[]>();
  
  /**
   * inward use only.<br>
   */
  protected boolean cmIsSilent = true;
  
  /**
   * inward use only.<br>
   */
  protected int cmResult,cmParamW,cmParamL;
  
  /**
   * inward use only.<br>
   */
  protected final List<MiExecutable> cmListOfRawAction
    = new LinkedList<MiExecutable>();
  
  /**
   * inward use only.<br>
   */
  protected HashMap<String,MiExecutable> cmMapOfOptionAction
    = new HashMap<String,MiExecutable>();
  
  //=== interface
  
  /**
   * for subclassing.<br>
   * action registration is supposed to get done here
   */
  abstract public void ccInit();
  
  /**
   * for subclassing.<br>
   * @return serve as a key
   */
  abstract public String ccGetName();
  
  /**
   * for subclassing.<br>
   * @return serve as a lock before execution
   */
  abstract protected boolean ccVerify();
  
  //===
  
  /**
   * does report error and echo or not
   * @param pxVal could be anything
   */
  public final void ccSetIsSilent(boolean pxVal){
     cmIsSilent=pxVal;
  }//+++
  
  /**
   * inward use only.<br>
   */
  protected void ccClear(){
    cmRawHead=null;
    cmDesRawBody=null;
    cmListOfOptionHead.clear();
    cmListOfOptionBody.clear();
  }//+++
  
  /**
   * @param pxInput must have some thing
   */
  public final void ccParse(String pxInput){
    
    //-- clear
    ccClear();
    
    //-- check in
    if(!VcConst.ccIsValidString(pxInput)){return;}
    String lpFilterd = pxInput
      .trim()
      .replaceAll("-( )+-", "-")
      .replaceAll("(-)+", "-")
      .replaceAll("(- )", "");
    String[] lpHiphenBreak = lpFilterd.split("-");
    if(lpHiphenBreak==null){return;}
    
    //-- once upon the time
    int lpHiphened = lpHiphenBreak.length;
    if(lpHiphened>0){
      
      //-- raw command
      String lpMainTarget = lpHiphenBreak[0];
      if(!VcConst.ccIsValidString(lpMainTarget)){return;}
      String[] lpMainSpaceBreak = lpMainTarget.split(" ");
      if(lpMainSpaceBreak==null){return;}
      int lpMainSpacened = lpMainSpaceBreak.length;
      cmRawHead=lpMainSpaceBreak[0];
      if(lpMainSpacened>1){
        cmDesRawBody = lpMainSpaceBreak;
      }//..?
      
    }//..?
    
    //-- they ve got multiple
    if(lpHiphened>1){
      for(String it : lpHiphenBreak){
        
        if(it.equals(lpHiphenBreak[0])){continue;}
        
        //-- option command
        if(!VcConst.ccIsValidString(it)){continue;}
        String[] lpRawSpaceBreak = it.split(" ");
        if(lpRawSpaceBreak==null){continue;}
        int lpRawSpacened = lpRawSpaceBreak.length;
        cmListOfOptionHead.add(lpRawSpaceBreak[0]);
        if(lpRawSpacened>1){
          cmListOfOptionBody.add(lpRawSpaceBreak);
        }//..?
        
      }//..~
    }//..?
    
  }//+++
  
  /**
   * @return see those constants
   */
  public final int ccExecute() {
    
    //-- init
    cmResult=C_M_GENERAL_FAILIOR;
    if(!VcConst.ccIsValidString(ccGetName())){
      return ssReportError("unhandled_subclass");
    }//..?
    if(!VcConst.ccIsValidString(cmRawHead)){
      return ssReportError("bad_parsing_result");
    }//..?
    if(!cmRawHead.equals(ccGetName())){
      return ssReportError("unhandlable_parsing_result:" + cmRawHead);
    }//..?
    if(!cmIsSilent){
      VcConst.ccPrintln(ccGetName(),">>>");
    }//..?
    
    //-- check
    if(!ccVerify()){
      return ssReportError("verification_failed");
    }//..?
    
    //-- raw
    if(cmListOfRawAction.isEmpty()){
      return ssReportError("no_recoginized_task");
    }//..?
    for(MiExecutable it : cmListOfRawAction){
      cmResult=it.ccExecute(PApplet.join(cmDesRawBody, ' '));
      if(cmResult<0){
        return cmResult;
      }//..?
    }//..?
    
    //-- option
    int lpOptionHeadCount = cmListOfOptionHead.size();
    int lpOptionBodyCount = cmListOfOptionBody.size();
    if(lpOptionBodyCount!=lpOptionBodyCount){
      return McCommand.this.ssReportError("unhandled_option_registering_error",-2);
    }//..?
    if(lpOptionHeadCount!=0){
      for(int i=0,s=cmListOfOptionHead.size();i<s;i++){
        if(cmMapOfOptionAction.containsKey(cmListOfOptionHead.get(i))){
          cmResult=cmMapOfOptionAction.get(cmListOfOptionHead.get(i))
            .ccExecute(PApplet.join(cmListOfOptionBody.get(i), ' '));
        }//..?
      }//..~
    }//..?
    
    //-- 
    if(!cmIsSilent){
      VcConst.ccPrintln(ccGetName(),"<<<");
    }//..?
    return cmResult;
    
  }//+++
  
  //===
  
  private int ssReportError(String pxMessage){
    return McCommand.this.ssReportError(pxMessage, cmResult);
  }//+++
  
  private int ssReportError(String pxMessage, int pxResult){
    if(VcConst.ccIsValidString(pxMessage) && (!cmIsSilent)){
      System.out.println(
        "Excecution$"+VcStringUtility.ccNulloutString(ccGetName())
          + ":" + pxMessage
      );
    }//..?
    return pxResult;
  }//+++
  
  //===
  
  /**
   * @deprecated for test use only
   */
  @Deprecated public final void tstReadUp(){
    
    int lpRawL = cmDesRawBody==null?-1:cmDesRawBody.length;
    int lpOptionHeadL = cmListOfOptionHead==null?-1:cmListOfOptionHead.size();
    int lpOptionBodyL = cmListOfOptionBody==null?-1:cmListOfOptionBody.size();
    
    System.out.println("kosui.pppmodel.McExcecutable.tstReadUp()::begin");
    
    VcConst.ccPrintln(VcStringUtility.ccPackupPairedTag("raw-head", cmRawHead));
    VcConst.ccPrintln("raw-body-count", lpRawL);
    VcConst.ccPrintln("option-head-count", lpOptionHeadL);
    VcConst.ccPrintln("option-body-count", lpOptionBodyL);
    
    if(lpRawL!=-1){
      PApplet.println("=raw-body=");
      for(String it : cmDesRawBody){
        PApplet.println(it);
      }//..~
    }//..?
    if(lpOptionHeadL != -1){
      PApplet.println("=option-head=");
      for(String it : cmListOfOptionHead){
        PApplet.println(it);
      }
    }//..?
    if(lpOptionBodyL != 1){
      PApplet.println("=option-body=");
      for(String[] it : cmListOfOptionBody){
        PApplet.println(it);
        PApplet.println("<-");
      }
    }//..?
    
    System.out.println("kosui.pppmodel.McExcecutable.tstReadUp()::end");
    
  }//+++
  
  /**
   * %[not_yet]::%
   * %dont push it before we actually could rewrite it%
   * @deprecated but how do we re-write the whole stuff?
   * @param pxLine
   * @return #
   */
  public static final
  HashMap<String, String> ccParseInlineRecord(String pxLine){
    
    //-- check in
    if(!VcConst.ccIsValidString(pxLine)){return null;}
    
    //-- split
    String lpBuf = pxLine.trim();
    lpBuf = lpBuf.replaceAll("\\s+", " ");
    lpBuf = lpBuf.replaceAll(" -", " %--");
    String[] lpSplit = lpBuf.split("%-");
    for(String it : lpSplit){System.out.println(it);}
    
    //-- new 
    HashMap<String, String> lpRes = new HashMap<String, String>();
    
    lpRes.put("_TITLE_", "?");
    lpRes.put("_CONTENT_", "?");
    for(String it : lpSplit){
      if(it.startsWith("@")|it.startsWith("#")){
        lpRes.put("_TITLE_", it.substring(1,it.length()-1));
      }//..?
      if(it.startsWith("-")){
        int lpCut = it.indexOf(" ");
        if(lpCut<=1){continue;}
        lpRes.put(
          it.substring(0,lpCut),
          it.substring(lpCut, it.length()).trim()
        );
      }//..?
    }//..~
    
    //-- pack
    return lpRes;
    
  }//+++
  
}//***eof
