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
import kosui.ppputil.VcLocalAxis;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcLocalConsole;
import kosui.ppputil.VcLocalStoker;
import kosui.ppputil.VcLocalTagger;

public class DemoLocalUtility extends PApplet {
  
  static private DemoLocalUtility self = null;

  static private int cmRoller=0;
  
  //=== inner
  
  private final EiTriggerable cmQuit = new EiTriggerable() {
    @Override public void ccTrigger(){
      println(".cmTriggerOfQuit::call PApplet.exit()");
      exit();
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
  
  private final EiTriggerable cmFlipHelper = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcLocalConsole.ccGetInstance().ccSetHelperVisible();
    }//+++
  };
  
  private final EiTriggerable cmEchoInput = new EiTriggerable() {
    @Override public void ccTrigger(){
      
      VcLocalStoker.ccStokeln(VcLocalConsole.ccGetLastAccepted());
      
      
      
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

    //-- init ** managers
    VcLocalAxis.ccGetInstance().ccInit(self, true);
    VcLocalTagger.ccGetInstance().ccInit(this, 7);
    VcLocalConsole.ccGetInstance().ccInit(this);
    VcLocalStoker.ccGetInstance().ccInit(this);
    
    //-- register ** key
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_Q, cmQuit);
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_M, cmFlipAxis);
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_N, cmAnchorAxis);
    
    //-- register ** command
    VcLocalConsole.ccAddHelpMessage("help unavailable");
    VcLocalConsole.ccRegisterTrigger("quit", cmQuit);
    VcLocalConsole.ccRegisterTrigger("help", cmFlipHelper);
    VcLocalConsole.ccRegisterTrigger("echo", cmEchoInput);
    
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
    VcLocalAxis.ccUpdate();
    
    //-- tag
    VcLocalTagger.ccTag("roller", cmRoller);
    VcLocalTagger.ccTag("rate", String.format("%.2f", frameRate));
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
