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
import kosui.ppputil.VcAxis;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcResponsiveBar;
import kosui.ppputil.VcStacker;
import kosui.ppputil.VcTagger;

public class DemoCommonUsage extends PApplet {
  
  static private DemoCommonUsage self = null;

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
      VcAxis.ccGetInstance().ccSetIsEnabled();
    }//+++
  };
  
  private final EiTriggerable cmAnchorAxis = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcAxis.ccGetInstance().ccSetAnchor(mouseX, mouseY);
    }//+++
  };
  
  private final EiTriggerable cmFlipHelper = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcResponsiveBar.ccGetInstance().ccSetHelperVisible();
    }//+++
  };
  
  private final EiTriggerable cmEchoInput = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcStacker.ccStack(VcResponsiveBar.ccGetLastAccepted());
    }//+++
  };
  
  //=== overridden

  @Override public void setup() {
    println(".setup()::start");

    //-- pre
    size(320, 240);
    frame.setTitle("DemoCommonUsage");
    EcConst.ccSetupSketch(this);
    self=this;

    //-- init ** managers
    VcAxis.ccGetInstance().ccInit(self, true);
    VcTagger.ccGetInstance().ccInit(this, 7);
    VcResponsiveBar.ccGetInstance().ccInit(this);
    VcStacker.ccGetInstance().ccInit(this);
    
    //-- register ** key
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_Q, cmQuit);
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_M, cmFlipAxis);
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_N, cmAnchorAxis);
    
    //-- register ** command
    VcResponsiveBar.ccAddHelpMessage("help unavailable");
    VcResponsiveBar.ccRegisterTrigger("quit", cmQuit);
    VcResponsiveBar.ccRegisterTrigger("help", cmFlipHelper);
    VcResponsiveBar.ccRegisterTrigger("echo", cmEchoInput);
    
    //-- post setting
    println(".setup()::over");
    
  }//+++
  
  @Override public void draw() { 

    //-- pre
    ssRoll();

    //-- update
    background(0);
    
    //-- update ** system
    VcStacker.ccUpdate();
    VcResponsiveBar.ccUpdate();
    VcAxis.ccUpdate();
    
    //-- tag
    VcTagger.ccTag("roller", cmRoller);
    VcTagger.ccTag("rate", String.format("%.2f", frameRate));
    VcTagger.ccStabilize();

  }//+++
  
  @Override public void keyPressed() {
    if(VcResponsiveBar.ccKeyTyped(key, keyCode)){return;}
    VcLocalCoordinator.ccKeyPressed(keyCode);
  }//+++
  
  //=== utility

  private void ssRoll(){
    cmRoller++;cmRoller&=0x0F;
  }//+++
  
  //=== entry
  
  static public boolean ccGetRollingAbove(int pxZeroToFifteen){
    return cmRoller>pxZeroToFifteen;
  }//+++
  
  static public boolean ccGetRollingAt(int pxZeroToFifteen){
    return cmRoller==pxZeroToFifteen;
  }//+++
  
  static public DemoCommonUsage ccGetSketch(){return self;}//+++
  
  static public PApplet ccGetApplet(){return self;}//+++
  
  static public Frame ccGetFrame(){return self.frame;}//+++

  static public void main(String[] passedArgs) {
    PApplet.main(DemoCommonUsage.class.getCanonicalName());
  }//+++

}//***eof
