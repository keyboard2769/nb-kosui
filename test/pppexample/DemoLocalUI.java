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
import kosui.ppplocalui.EcGauge;
import kosui.ppplocalui.EcPane;
import kosui.ppplocalui.EcRect;
import kosui.ppplocalui.EcSlider;
import kosui.ppplocalui.EcValueBox;
import kosui.ppplocalui.EiTriggerable;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcLocalTipHolder;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class DemoLocalUI extends PApplet {
  
  static private final String C_BLK_MSG
    = "... press F3 to show UI description.";
  
  static private DemoLocalUI self;

  static private int cmRoller=0;

  //=== overridden
  
  private final EcButton cmQuitSW = new EcButton("quit",0x9900);
  
  private final EcPane cmDragPane
    = new EcPane("Drag!!", 320-10*3-100, 80);
  private final EcPane cmClickPane
    = new EcPane("Click!!", 320-10*2, 80);
  
  private final EcGauge cmGauge = new EcGauge("G:");
  private final EcSlider cmSlider = new EcSlider("S'", 0xCA01, false);
  
  private final EcButton cmShiftLeftSW  = new EcButton("<", 0xAA01);
  private final EcButton cmDecrementSW  = new EcButton("-", 0xAA02);
  private final EcButton cmIncrementSW  = new EcButton("+", 0xAA03);
  private final EcButton cmShiftRightSW = new EcButton(">", 0xAA04);
  
  private final EcValueBox cmDecimalTB = new EcValueBox("dec", "0000  ");
  private final EcValueBox cmHexadecimalTB = new EcValueBox("hex", "0000 H");
  
  public final List<EcButton> cmDesBitSW
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
    ));//,,,
  
  private short cmCounterModel = 0;
  
  //=== action
  
  private final EiTriggerable cmScanning = new EiTriggerable() {
    @Override public void ccTrigger() {
      ssInput();
      ssOutput();
    }//+++
    private void ssInput(){
      if(cmSlider.ccIsMousePressed()){
        cmCounterModel=(short)(cmSlider.ccGetContentValue()*32);
      }//..?
    }//+++
    private void ssOutput(){
      //-- update ** binding
      cmGauge.ccSetProportion((int)cmCounterModel, 0x1FFF);
      cmDecimalTB.ccSetValue((int)cmCounterModel);
      cmHexadecimalTB.ccSetText("0x"+hex(cmCounterModel,4));
      for(int i=0;i<16;i++){
        cmDesBitSW.get(i).ccSetIsActivated
         (VcNumericUtility.ccBinaryLoad((int)cmCounterModel, i));
      }//..~
    }//+++
  };//***
  
  private final EiTriggerable cmQuitting = new EiTriggerable() {
    @Override public void ccTrigger(){
      println(".ssPover()::call PApplet.exit()");
      exit();
    }//+++
  };//***
  
  private final EiTriggerable cmTipShowing = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcLocalTipHolder.ccActivate();
    }//+++
  };//***
  
  private final EiTriggerable cmLeftShifting = new EiTriggerable() {
    @Override public void ccTrigger(){
      cmCounterModel<<=1;
    }//+++
  };//***
  
  private final EiTriggerable cmDecrementing = new EiTriggerable() {
    @Override public void ccTrigger(){
      cmCounterModel--;
    }//+++
  };
  
  private final EiTriggerable cmIncrementting = new EiTriggerable() {
    @Override public void ccTrigger(){
      cmCounterModel++;
    }//+++
  };//***
  
  private final EiTriggerable cmRightShifting = new EiTriggerable() {
    @Override public void ccTrigger(){
      cmCounterModel>>=1;
    }//+++
  };//***
  
  private final EiTriggerable cmManipulating = new EiTriggerable() {
    @Override public void ccTrigger(){
      int lpBitIndex
        =VcLocalCoordinator.ccGetMouseOverID()
        -cmDesBitSW.get(0).ccGetID();
      boolean lpCurrentBit
        =VcNumericUtility.ccBinaryLoad(cmCounterModel, lpBitIndex);
      lpCurrentBit=!lpCurrentBit;
      cmCounterModel=(short)VcNumericUtility.ccBinarySet(
        (int)cmCounterModel,
        lpBitIndex,
        lpCurrentBit
      );/* 4 */VcConst.ccLogln("mouse",lpBitIndex);
    }//+++
  };//***

  @Override public void setup() {
    println(".setup()::start");

    //-- pre
    size(320, 240);
    EcConst.ccSetupSketch(this);
    frame.setTitle("DemoLocalUI");
    self=this;

    //-- init
    VcLocalCoordinator.ccInit(this);
    VcLocalTipHolder.ccInit(this);
    
    //-- layout
    
    //-- layout ** system
    
    cmQuitSW.ccSetSize(100,100);
    cmQuitSW.ccSetLocation(10, 30);
    VcLocalCoordinator.ccAddElement(cmQuitSW);
    VcLocalCoordinator.ccRegisterMouseTrigger(cmQuitSW, cmQuitting);
    
    //-- layout ** drag
    cmDragPane.ccSetLocation(cmQuitSW, 10, 0);
    VcLocalCoordinator.ccAddShape(cmDragPane);
    
    cmGauge.ccSetLocation(cmDragPane, 32, 22);
    cmGauge.ccSetSize(150, 19);
    cmGauge.ccSetNameAlign('l');
    cmGauge.ccSetIsVertical(false);
    VcLocalCoordinator.ccAddElement(cmGauge);
    
    cmSlider.ccSetLocation(cmGauge, 0, 5);
    cmSlider.ccSetSize(cmGauge);
    cmSlider.ccSetupStyle(cmGauge);
    cmSlider.ccSetIsEnabled(true);
    VcLocalCoordinator.ccAddElement(cmSlider);
    VcLocalCoordinator.ccRegisterWheelUpTrigger(cmSlider, cmIncrementting);
    VcLocalCoordinator.ccRegisterWheelDownTrigger(cmSlider, cmDecrementing);
    VcLocalTipHolder.ccRegisterTipMessage(cmSlider,
      "Mouse Wheel Acceptable. "+VcConst.C_V_NEWLINE+"Draggable. "
    );
    
    //-- layout ** click
    cmClickPane.ccSetLocation(cmQuitSW,0, 10);
    VcLocalCoordinator.ccAddShape(cmClickPane);
    
    //-- layout ** click ** button
    cmShiftLeftSW.ccSetSize(20,20);
    cmShiftLeftSW.ccSetLocation(cmClickPane,5, 22);
    VcLocalCoordinator.ccAddElement(cmShiftLeftSW);
    VcLocalCoordinator.ccRegisterMouseTrigger(cmShiftLeftSW, cmLeftShifting);
    VcLocalTipHolder.ccRegisterTipMessage(cmShiftLeftSW, "Shift Left");
    
    cmDecrementSW.ccSetSize(cmShiftLeftSW);
    cmDecrementSW.ccSetLocation(cmShiftLeftSW, 4, 0);
    VcLocalCoordinator.ccAddElement(cmDecrementSW);
    VcLocalCoordinator.ccRegisterMouseTrigger(cmDecrementSW, cmDecrementing);
    VcLocalTipHolder.ccRegisterTipMessage(cmDecrementSW, "Decrement");
    
    cmIncrementSW.ccSetSize(cmDecrementSW);
    cmIncrementSW.ccSetLocation(cmDecrementSW, 4, 0);
    VcLocalCoordinator.ccAddElement(cmIncrementSW);
    VcLocalCoordinator.ccRegisterMouseTrigger(cmIncrementSW, cmIncrementting);
    VcLocalTipHolder.ccRegisterTipMessage(cmIncrementSW, "Increment");
    
    cmShiftRightSW.ccSetSize(cmIncrementSW);
    cmShiftRightSW.ccSetLocation(cmIncrementSW, 4, 0);
    VcLocalCoordinator.ccAddElement(cmShiftRightSW);
    VcLocalCoordinator.ccRegisterMouseTrigger(cmShiftRightSW, cmRightShifting);
    VcLocalTipHolder.ccRegisterTipMessage(cmShiftRightSW, "Shift Right");
    
    cmDesBitSW.get(0).ccSetLocation(
      cmClickPane.ccEndX()-25,
      cmShiftLeftSW.ccEndY()+10
    );
    EcRect.ccFlowLayout(cmDesBitSW, 2, false, true);
    VcLocalCoordinator.ccAddElement(cmDesBitSW);
    
    //-- layout ** click ** box
    cmDecimalTB.ccSetSize(64, 19);
    cmDecimalTB.ccSetLocation(cmShiftRightSW,32, 0);
    cmDecimalTB.ccSetNameAlign('l');
    VcLocalCoordinator.ccAddElement(cmDecimalTB);
    
    cmHexadecimalTB.ccSetSize(cmDecimalTB);
    cmHexadecimalTB.ccSetLocation(cmDecimalTB,32, 0);
    cmHexadecimalTB.ccSetNameAlign('l');
    VcLocalCoordinator.ccAddElement(cmHexadecimalTB);
    
    //-- binding
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_Q, cmQuitting);
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_F3, cmTipShowing);
    for(EcButton it:cmDesBitSW){
      VcLocalCoordinator.ccRegisterMouseTrigger(it, cmManipulating);
    }//..~
    
    //-- post setting
    println(".setup()::over");
    
  }//++!
  
  @Override public void draw() { 

    //-- pre
    ssRoll();
    background(0);

    //-- update ** manager
    cmScanning.ccTrigger();
    VcLocalCoordinator.ccUpdate();
    VcLocalTipHolder.ccUpdate();
    
    //-- update ** IV
    if(ccIsRollingAbove(7)){
      fill(0xFF33EE33);
      text(C_BLK_MSG,3,3);
    }//..?
    
  }//++~
  
  @Override public void keyPressed() {
    VcLocalCoordinator.ccKeyPressed(keyCode);
  }//+++

  @Override public void mousePressed(){
    VcLocalCoordinator.ccMousePressed();
  }//+++

  @Override public void mouseWheel(MouseEvent me) {
     int lpCount=-1*(int)(me.getAmount());//...i dont know why float cant work 
     VcLocalCoordinator.ccMouseWheel(lpCount>0, lpCount<0);
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
  
  static public DemoLocalUI ccGetSketch(){return self;}//+++
  
  static public PApplet ccGetApplet(){return self;}//+++
  
  static public Frame ccGetFrame(){return self.frame;}//+++

  static public void main(String[] passedArgs) {
    PApplet.main(DemoLocalUI.class.getCanonicalName());
  }//!!!

}//***eof
