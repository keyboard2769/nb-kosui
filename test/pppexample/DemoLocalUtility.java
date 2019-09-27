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

package pppexample;

import java.awt.Frame;
import processing.core.PApplet;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EiTriggerable;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalAxis;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcLocalConsole;
import kosui.ppputil.VcLocalHelper;
import kosui.ppputil.VcLocalStoker;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcNumericUtility;

public class DemoLocalUtility extends PApplet {
  
  static private DemoLocalUtility self = null;

  static private int cmRoller=0;
  
  //=== inner
  
  private final EiTriggerable cmQuitting = new EiTriggerable() {
    @Override public void ccTrigger(){
      println(".cmTriggerOfQuit::call PApplet.exit()");
      exit();
    }//+++
  };
  
  private final EiTriggerable cmConsoleRefreshing = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcLocalHelper.ccGetInstance().ccSetVisible(false);
    }//+++
  };
  
  
  private final EiTriggerable cmFlipAxis = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcLocalAxis.ccGetInstance().ccSetIsEnabled();
    }//+++
  };
  
  private final EiTriggerable cmAnchorAxis = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcLocalAxis.ccGetInstance().ccSetAnchor(mouseX, mouseY);
    }//+++
  };
  
  private final EiTriggerable cmHelperFlipping = new EiTriggerable() {
    @Override public void ccTrigger(){
      //VcLocalConsole.ccGetInstance().ccSetHelperVisible();
      VcLocalHelper.ccGetInstance().ccSetVisible();
    }//+++
  };
  
  private final EiTriggerable cmEchoing = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcLocalStoker.ccWriteln(VcLocalConsole.ccGetLastAccepted());
    }//+++
  };
  
  //=== overridden
  
  @Override public void setup() {
    println(".setup()::start");
    
    //-- pre
    size(320, 240);
    frame.setTitle("Common Usage");
    EcConst.ccSetupSketch(this);
    self=this;
    
    //-- managers ** init
    VcLocalAxis.ccGetInstance().ccInit(self, true);
    VcLocalTagger.ccGetInstance().ccInit(this);
    VcLocalConsole.ccGetInstance().ccInit(this);
    VcLocalStoker.ccGetInstance().ccInit(this);
    VcLocalHelper.ccGetInstance().ccInit(this);
    
    //-- managers ** config
    VcLocalTagger.ccGetInstance().ccSetAsBar(true);
    VcLocalTagger.ccGetInstance().ccSetLocationOffset(1, 1);
    VcLocalTagger.ccGetInstance().ccSetGap(85, 20);
    
    //-- register ** key
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_Q, cmQuitting);
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_M, cmFlipAxis);
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_N, cmAnchorAxis);
    
    //-- register ** command
    VcLocalHelper.ccAddHelpMessage(
      "CASE IN THE NAME"+VcConst.C_V_NEWLINE
     +"  OF TEST"+VcConst.C_V_NEWLINE
     +"YA NOT GUILTY"
    );
    VcLocalConsole.ccRegisterCommand("quit", cmQuitting);
    VcLocalConsole.ccRegisterCommand("help", cmHelperFlipping);
    VcLocalConsole.ccRegisterCommand("echo", cmEchoing);
    VcLocalConsole.ccRegisterEmptiness(cmConsoleRefreshing);
    
    //-- post setting
    println(".setup()::over");
    
  }//+++
  
  @Override public void draw() { 

    //-- pre
    ssRoll();
    
    //-- update
    background(0);
    
    //-- update ** system
    VcLocalStoker.ccUpdate();
    VcLocalConsole.ccUpdate();
    VcLocalHelper.ccUpdate();
    VcLocalAxis.ccUpdate();
    
    //-- tag
    VcLocalTagger.ccTag("roller", PApplet.nf(cmRoller, 3));
    VcLocalTagger.ccTag
      ("rate", VcNumericUtility.ccFormatPointTwoFloat(frameRate));
    VcLocalTagger.ccTag("keyC", 
      Character.isLetterOrDigit(key)?
      Character.toString(key):"<NUC>"
    );
    VcLocalTagger.ccTag("keyI", PApplet.hex(keyCode, 4));
    VcLocalTagger.ccStabilize();
    
  }//+++
  
  @Override public void keyPressed() {
    if(VcLocalConsole.ccKeyTyped(key, keyCode)){return;}
    VcLocalCoordinator.ccKeyPressed(keyCode);
  }//+++
  
  //=== utility

  private void ssRoll(){
    cmRoller++;cmRoller&=0x0F;
  }//+++
  
  //=== entry
  
  static public boolean ccIsRollingAbove(int pxZeroToFifteen){
    return cmRoller>pxZeroToFifteen;
  }//+++
  
  static public boolean ccIsRollingAt(int pxZeroToFifteen){
    return cmRoller==pxZeroToFifteen;
  }//+++
  
  static public DemoLocalUtility ccGetSketch(){return self;}//+++
  
  static public PApplet ccGetApplet(){return self;}//+++
  
  static public Frame ccGetFrame(){return self.frame;}//+++

  static public void main(String[] passedArgs) {
    PApplet.main(DemoLocalUtility.class.getCanonicalName());
  }//+++

}//***eof
