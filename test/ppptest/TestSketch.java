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
import kosui.pppmodel.MiPixillatable;

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
  
  EcImage t = new EcImage(32, 32);
  
  
  @Override public void setup() {
    
    //-- init
    size(320, 240);
    EcConst.ccSetupSketch(this);
    VcTagger.ccGetInstance().ccInit(this, 7);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    
    //-- laytout
    
    
    t.ccFillPixel(0xFF33EE33, new MiPixillatable() {
      @Override public boolean ccPixillate(int pxX, int pxY){
        return true;
      }//+++
    });
    
    t.ccFillPixel(0xFF999999, new MiPixillatable() {
      @Override public boolean ccPixillate(int pxX, int pxY){
        return pxX>pxY;
      }//+++
    });
    
    t.ccFillPixel(0xFFEEEEEE, new MiPixillatable() {
      @Override public boolean ccPixillate(int pxX, int pxY){
        return pxX<3 || pxX>28;
      }//+++
    });
    
    t.ccFillPixel(0xFFEEEEEE, new MiPixillatable() {
      @Override public boolean ccPixillate(int pxX, int pxY){
        return pxY<3 || pxY>28;
      }//+++
    });
    
    
    VcLocalCoordinator.ccAddShape(t);
    
    
    
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
    t.ccSetLocation(mouseX, mouseY);
    
    //-- update
    VcLocalCoordinator.ccUpdate();
    
    //-- finishing
    VcTagger.ccTag("roller",cmRoller);
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
