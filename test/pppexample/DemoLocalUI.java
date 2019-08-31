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
import kosui.ppplocalui.EcButton;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcPane;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiTriggerable;
import kosui.ppplogic.ZcRangedValueModel;
import kosui.ppputil.VcLocalCoordinator;
import processing.core.PApplet;

public class DemoLocalUI extends PApplet {
  
  static private DemoLocalUI self;

  static private int cmRoller=0;

  //=== overridden
  
  private final EcPane cmMainPane
    = new EcPane("count!!", 300, 200);
  private final EcValueBox cmCounterTB
    = new EcValueBox("counter", "0000");
  private final EcButton cmAddSW
    = new EcButton("add", 0xAA01);
  private final EcButton cmResetSW
    = new EcButton("reset", 0xAA02);
  
  private final ZcRangedValueModel cmCounterModel
    = new ZcRangedValueModel(0, 99);
  
  //=== action
  
  private final EiTriggerable cmQuit = new EiTriggerable() {
    @Override public void ccTrigger(){
      println(".ssPover()::call PApplet.exit()");
      exit();
    }//+++
  };
  
  private final EiTriggerable cmAddCoounter = new EiTriggerable() {
    @Override public void ccTrigger(){
      cmCounterModel.ccShift(1);
    }//+++
  };
  
  private final EiTriggerable cmResetCounter = new EiTriggerable() {
    @Override public void ccTrigger(){
      cmCounterModel.ccSetValue(0);
    }//+++
  };

  @Override public void setup() {
    println(".setup()::start");

    //-- pre
    size(320, 240);
    EcConst.ccSetupSketch(this);
    frame.setTitle("DemoLocalUI");
    self=this;

    //-- init
    
    //-- init ** 
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    
    //-- layout
    cmMainPane.ccSetLocation(10, 22);
    VcLocalCoordinator.ccAddShape(cmMainPane);
    
    cmCounterTB.ccSetSize(72, 19);
    cmCounterTB.ccSetLocation(cmMainPane,5, 22);
    VcLocalCoordinator.ccAddElement(cmCounterTB);
    
    cmAddSW.ccSetSize(48,20);
    cmAddSW.ccSetLocation(cmCounterTB, 4, 0);
    VcLocalCoordinator.ccAddElement(cmAddSW);
    VcLocalCoordinator.ccRegisterMouseTrigger(cmAddSW, cmAddCoounter);
    
    cmResetSW.ccSetSize(cmAddSW);
    cmResetSW.ccSetLocation(cmAddSW, 4, 0);
    VcLocalCoordinator.ccAddElement(cmResetSW);
    VcLocalCoordinator.ccRegisterMouseTrigger(cmResetSW, cmResetCounter);
    
    //-- binding
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_Q, cmQuit);
    
    //-- post setting
    println(".setup()::over");
    
  }//+++
  
  @Override public void draw() { 

    //-- pre
    ssRoll();
    background(0);

    //-- update ** binding
    cmCounterTB.ccSetValue(cmCounterModel.ccGetValue());
    
    //-- update ** manager
    VcLocalCoordinator.ccUpdate();
    
  }//+++
  
  @Override public void keyPressed() {
    VcLocalCoordinator.ccKeyPressed(keyCode);
  }//+++

  @Override public void mousePressed(){
    VcLocalCoordinator.ccMousePressed();
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
  
  static public DemoLocalUI ccGetSketch(){return self;}//+++
  
  static public PApplet ccGetApplet(){return self;}//+++
  
  static public Frame ccGetFrame(){return self.frame;}//+++

  static public void main(String[] passedArgs) {
    PApplet.main(DemoLocalUI.class.getCanonicalName());
  }//+++

}//***eof
