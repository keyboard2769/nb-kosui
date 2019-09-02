/* *
 * TEST SKETCH
 */

package ppptest;

import kosui.ppputil.VcTagger;
import kosui.ppputil.VcResponsiveBar;
import java.util.ArrayList;
import java.util.Arrays;

import kosui.ppplocalui.*;
import kosui.ppplogic.*;
import kosui.pppswingui.*;
import kosui.ppputil.*;

import javax.swing.SwingUtilities;

import processing.core.*;

public class TestSketch extends PApplet {
  
  static private volatile int cmRoller=0;
  
  //=== action
  
  private final EiTriggerable cmQuitting = new EiTriggerable() {
    @Override public void ccTrigger(){
      println("kosui.ppptest.TestSketch.cmQuitting::call PApplet.exit");
      exit();
    }//+++
  };
  
  //=== overridden
  
  private final EcGauge cmTesterA = new EcGauge("t", 0xAA01);
  private final EcGauge cmTesterB = new EcGauge("t", 0xAA02);
  private final EcSlider cmTesterC = new EcSlider("t", 0xAA03);
  private final EcSlider cmTesterD = new EcSlider("t", 0xAA04);
  
  @Override public void setup() {
    
    //-- init
    size(320, 240);
    EcConst.ccSetupSketch(this);
    VcTagger.ccGetInstance().ccInit(this, 7);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    
    //-- laytout
    cmTesterA.ccSetLocation(50, 50);
    cmTesterA.ccSetIsVertical(true);
    cmTesterA.ccSetSize(80,80);
    cmTesterA.ccSetIsEnabled(true);
    
    
    cmTesterB.ccSetLocation(cmTesterA,8, 0);
    cmTesterB.ccSetIsVertical(false);
    cmTesterB.ccSetSize(80,80);
    cmTesterB.ccSetIsEnabled(true);
    
    
    cmTesterC.ccSetLocation(cmTesterA,0,8);
    cmTesterC.ccSetIsVertical(true);
    cmTesterC.ccSetSize(80,80);
    cmTesterC.ccSetIsEnabled(true);
    
    
    cmTesterD.ccSetLocation(cmTesterC,8,0);
    cmTesterD.ccSetIsVertical(false);
    cmTesterD.ccSetSize(80,80);
    cmTesterD.ccSetIsEnabled(true);
    
    
    
    VcLocalCoordinator.ccAddElement(cmTesterA);
    VcLocalCoordinator.ccAddElement(cmTesterB);
    VcLocalCoordinator.ccAddElement(cmTesterC);
    VcLocalCoordinator.ccAddElement(cmTesterD);
    
    //-- bind
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_Q, cmQuitting);
    
    //-- post
    VcConst.ccPrintln("kosui.ppptest.TestSketch.setup()::over");
    
  }//+++
  
  @Override public void draw() { 
    
    //-- pre
    background(0);
    cmRoller++;cmRoller&=0x0F;
    
    //-- scan
    
    //-- update
    VcLocalCoordinator.ccUpdate();
    
    //-- finishing
    VcTagger.ccTag("roller",cmRoller);
    VcTagger.ccTag("a",cmTesterA.ccGetContentValue());
    VcTagger.ccTag("b",cmTesterB.ccGetContentValue());
    VcTagger.ccTag("c",cmTesterC.ccGetContentValue());
    VcTagger.ccTag("d",cmTesterD.ccGetContentValue());
    VcTagger.ccStabilize();
    
  }//+++
  
  @Override public void keyPressed() {
    VcLocalCoordinator.ccKeyPressed(keyCode);
  }//+++
  
  //=== entry
  
  static public void main(String[] passedArgs) {
    PApplet.main(TestSketch.class.getCanonicalName());
  }//+++
  
}//***eof
