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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kosui.ppplocalui.EcButton;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcPane;
import kosui.ppplocalui.EcRect;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiTriggerable;
import kosui.ppplogic.ZcRangedValueModel;
import kosui.ppputil.VcLocalCoordinator;
import processing.core.PApplet;

public class DemoLocalUI extends PApplet {
  
  static private DemoLocalUI self;

  static private int cmRoller=0;

  //=== overridden
  
  private final EcPane cmMainPane = new EcPane("count!!", 300, 200);
  private final EcValueBox cmCounterTB = new EcValueBox("counter", "0000");
  private final EcButton cmAddSW = new EcButton("add", 0xAA01);
  private final EcButton cmResetSW = new EcButton("reset", 0xAA02);

  public final List<EcButton> cmDesSendBitSW
   = Collections.unmodifiableList(Arrays.asList(
    new EcButton("0", 0xBA00),new EcButton("1", 0xBA01),
    new EcButton("2", 0xBA02),new EcButton("3", 0xBA03),
    //
    new EcButton("4", 0xBA04),new EcButton("5", 0xBA05),
    new EcButton("6", 0xBA06),new EcButton("7", 0xBA07),
    //
    new EcButton("8", 0xBA08),new EcButton("9", 0xBA09),
    new EcButton("A", 0xBA0A),new EcButton("B", 0xBA0B),
    //
    new EcButton("C", 0xBA0C),new EcButton("D", 0xBA0D),
    new EcButton("E", 0xBA0E),new EcButton("F", 0xBA0F)
  ));
  
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
    
    cmDesSendBitSW.get(0).ccSetLocation(cmCounterTB, 0,20);
    EcRect.ccFlowLayout(cmDesSendBitSW, 2, false, false);
    VcLocalCoordinator.ccAddElement(cmDesSendBitSW);
    
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
