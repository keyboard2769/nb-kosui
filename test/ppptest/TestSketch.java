/* *
 * TEST SKETCH
 */

package ppptest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcLocalConsole;
import java.util.ArrayList;
import java.util.Arrays;

import kosui.ppplocalui.*;
import kosui.ppplogic.*;
import kosui.pppswingui.*;
import kosui.ppputil.*;

import javax.swing.SwingUtilities;
import kosui.pppmodel.MiPixillatable;

import processing.core.*;

public class TestSketch extends PApplet {
  
  static private volatile int cmRoller=0;
  
  //=== action
  
  private final EiTriggerable cmQuitting = new EiTriggerable() {
    @Override public void ccTrigger(){
      println(".cmQuitting::call PApplet.exit");
      exit();
    }//+++
  };
  
  //=== overridden
  
  EcButton cmPSWI=new EcButton("P1", 0x3111);
  EcButton cmPSWII=new EcButton("P2", 0x3112);
  EcButton cmPSWIII=new EcButton("P3", 0x3113);
  EcButton cmPSWIV=new EcButton("P4", 0x3114);
  
  EcButton cmTSWI=new EcButton("T1", 0x3211);
  EcButton cmTSWII=new EcButton("T2", 0x3212);
  EcButton cmTSWIII=new EcButton("T3", 0x3213);
  EcButton cmTSWIV=new EcButton("T4", 0x3214);
  
  int cmTestColor;

  @Override public void setup() {
    
    //-- init
    size(320, 240);
    EcConst.ccSetupSketch(this);
    VcLocalTagger.ccGetInstance().ccInit(this, 7);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    
    //-- laytout
    
    cmPSWI.ccSetLocation(100, 5);
    cmPSWII.ccSetLocation(cmPSWI, 2, 0);
    cmPSWIII.ccSetLocation(cmPSWII, 2, 0);
    cmPSWIV.ccSetLocation(cmPSWIII, 2, 0);
    
    cmTSWI.ccSetLocation(100, 66);cmTSWI.ccSetPage(1);
    cmTSWII.ccSetLocation(cmTSWI, 2, 0);cmTSWII.ccSetPage(2);
    cmTSWIII.ccSetLocation(cmTSWII, 2, 0);cmTSWIII.ccSetPage(3);
    cmTSWIV.ccSetLocation(cmTSWIII, 2, 0);cmTSWIV.ccSetPage(99);
    
    VcLocalCoordinator.ccAddElement(Arrays.asList(cmPSWI,cmPSWII,cmPSWIII,cmPSWIV,
      cmTSWI,cmTSWII,cmTSWIII,cmTSWIV
    ));
    
    VcLocalCoordinator.ccRegisterMouseTrigger(cmPSWI, new EiTriggerable() {
      @Override public void ccTrigger(){
        EcComponent.ccSetCurrentPage(1);
      }//+++
    });
    
    VcLocalCoordinator.ccRegisterMouseTrigger(cmPSWII, new EiTriggerable() {
      @Override public void ccTrigger(){
        EcComponent.ccSetCurrentPage(2);
      }//+++
    });
    VcLocalCoordinator.ccRegisterMouseTrigger(cmPSWIII, new EiTriggerable() {
      @Override public void ccTrigger(){
        EcComponent.ccSetCurrentPage(3);
      }//+++
    });
    VcLocalCoordinator.ccRegisterMouseTrigger(cmPSWIV, new EiTriggerable() {
      @Override public void ccTrigger(){
        EcComponent.ccSetCurrentPage(4);
      }//+++
    });
    
    
    //-- bind
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_Q, cmQuitting);
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_D, new EiTriggerable() {
      @Override public void ccTrigger(){
        EcComponent.ccShiftCurrentPage(1);
      }
    });
    
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_A, new EiTriggerable() {
      @Override public void ccTrigger(){
        EcComponent.ccShiftCurrentPage(-1);
      }
    });
    
    //-- ???
    cmTestColor=EcConst.ccBlendColor(EcConst.C_DARK_BLUE, EcConst.C_DARK_GREEN);
    println(hex(cmTestColor,8));
    
    //-- post
    VcConst.ccPrintln("kosui.ppptest.TestSketch.setup()::over");
    
  }//+++
  
  @Override public void draw() { 
    
    //-- pre
    background(0);
    ccRoll();
    
    //-- scan
    
    //-- update
    VcLocalCoordinator.ccUpdate();
    
    //-- ???
    fill(cmTestColor);
    rect(mouseX,mouseY,-18,-18);
    
    //-- finishing
    VcLocalTagger.ccTag("roller",cmRoller);
    VcLocalTagger.ccTag("page",EcComponent.ccGetCurrentPage());
    VcLocalTagger.ccTag("mouse",VcLocalCoordinator.ccGetMouseOverID());
    VcLocalTagger.ccStabilize();
    
  }//+++
  
  @Override public void keyPressed() {
    VcLocalCoordinator.ccKeyPressed(keyCode);
  }//+++

  @Override public void mousePressed(){
    VcLocalCoordinator.ccMousePressed();
  }//+++
  
  //=== entry
  
  static private void ccRoll(){
    cmRoller++;cmRoller&=0x0F;
  }//+++
  
  static public boolean ccIsRollingAbove(int pxZeroToFifteen){
    return cmRoller>pxZeroToFifteen;
  }//+++
  
  static public boolean ccIsRollingAt(int pxZeroToFifteen){
    return cmRoller==pxZeroToFifteen;
  }//+++
  
  static public void main(String[] passedArgs) {
    PApplet.main(TestSketch.class.getCanonicalName());
  }//..!
  
}//***eof
