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
  
  EcButton lpPSWI=new EcButton("P1", 0x3111);
  EcButton lpPSWII=new EcButton("P2", 0x3112);
  EcButton lpPSWIII=new EcButton("P3", 0x3113);
  EcButton lpPSWIV=new EcButton("P4", 0x3114);
  
  EcButton lpTSWI=new EcButton("T1", 0x3211);
  EcButton lpTSWII=new EcButton("T2", 0x3212);
  EcButton lpTSWIII=new EcButton("T3", 0x3213);
  EcButton lpTSWIV=new EcButton("T4", 0x3214);

  @Override public void setup() {
    
    //-- init
    size(320, 240);
    EcConst.ccSetupSketch(this);
    VcLocalTagger.ccGetInstance().ccInit(this, 7);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    
    //-- laytout
    
    lpPSWI.ccSetLocation(100, 5);
    lpPSWII.ccSetLocation(lpPSWI, 2, 0);
    lpPSWIII.ccSetLocation(lpPSWII, 2, 0);
    lpPSWIV.ccSetLocation(lpPSWIII, 2, 0);
    
    lpTSWI.ccSetLocation(100, 66);lpTSWI.ccSetPage(1);
    lpTSWII.ccSetLocation(lpTSWI, 2, 0);lpTSWII.ccSetPage(2);
    lpTSWIII.ccSetLocation(lpTSWII, 2, 0);lpTSWIII.ccSetPage(3);
    lpTSWIV.ccSetLocation(lpTSWIII, 2, 0);lpTSWIV.ccSetPage(99);
    
    VcLocalCoordinator.ccAddElement(Arrays.asList(
      lpPSWI,lpPSWII,lpPSWIII,lpPSWIV,
      lpTSWI,lpTSWII,lpTSWIII,lpTSWIV
    ));
    
    VcLocalCoordinator.ccRegisterMouseTrigger(lpPSWI, new EiTriggerable() {
      @Override public void ccTrigger(){
        EcComponent.ccSetCurrentPage(1);
      }//+++
    });
    
    VcLocalCoordinator.ccRegisterMouseTrigger(lpPSWII, new EiTriggerable() {
      @Override public void ccTrigger(){
        EcComponent.ccSetCurrentPage(2);
      }//+++
    });
    VcLocalCoordinator.ccRegisterMouseTrigger(lpPSWIII, new EiTriggerable() {
      @Override public void ccTrigger(){
        EcComponent.ccSetCurrentPage(3);
      }//+++
    });
    VcLocalCoordinator.ccRegisterMouseTrigger(lpPSWIV, new EiTriggerable() {
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
