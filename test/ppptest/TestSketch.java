/* *
 * TEST SKETCH
 */

package ppptest;

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
  
  
  private final EcButton cmSWI = new EcButton("I", 0xAAA1);
  private final EcButton cmSWII = new EcButton("II", 0xAAA2);
  private final EcButton cmSWIII = new EcButton("III", 0xAAA3);
  private final EcButton cmSWIV = new EcButton("IV", 0xAAA4);
  
  
  @Override public void setup() {
    
    //-- init
    size(320, 240);
    EcConst.ccSetupSketch(this);
    VcLocalTagger.ccGetInstance().ccInit(this, 7);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    
    //-- laytout
    
    
    cmSWI.ccSetBound(60, 60, 40, 40);
    cmSWII.ccSetSize(cmSWI);
    cmSWIII.ccSetSize(cmSWI);
    cmSWIV.ccSetSize(cmSWI);
    
    cmSWII.ccSetLocation(cmSWI, 5, 0);
    cmSWIII.ccSetLocation(cmSWI, 0, 5);
    cmSWIV.ccSetLocation(cmSWII, 0, 5);
    
    cmSWI.ccSetPage(1);
    cmSWII.ccSetPage(2);
    cmSWIII.ccSetPage(3);
    cmSWIV.ccSetPage(4);
    
    
    VcLocalCoordinator.ccAddElement(cmSWI);
    VcLocalCoordinator.ccAddElement(cmSWII);
    VcLocalCoordinator.ccAddElement(cmSWIII);
    VcLocalCoordinator.ccAddElement(cmSWIV);
    
    VcLocalCoordinator.ccRegisterMouseTrigger(cmSWI, new EiTriggerable() {
      @Override public void ccTrigger(){
        VcConst.ccPrintln("uno!!");
      }
    });
    
    
    VcLocalCoordinator.ccRegisterMouseTrigger(cmSWII, new EiTriggerable() {
      @Override public void ccTrigger(){
        VcConst.ccPrintln("dos!!");
      }
    });
    
    
    VcLocalCoordinator.ccRegisterMouseTrigger(cmSWIII, new EiTriggerable() {
      @Override public void ccTrigger(){
        VcConst.ccPrintln("tre!!");
      }
    });
    
    
    VcLocalCoordinator.ccRegisterMouseTrigger(cmSWIV, new EiTriggerable() {
      @Override public void ccTrigger(){
        VcConst.ccPrintln("cool!!");
      }
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
