/* *
 * TEST SKETCH
 */

package ppptest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import kosui.ppputil.VcLocalTagger;
import kosui.ppputil.VcLocalConsole;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JMenuItem;

import kosui.ppplocalui.*;
import kosui.ppplogic.*;
import kosui.pppswingui.*;
import kosui.ppputil.*;

import javax.swing.SwingUtilities;
import kosui.pppmodel.MiPixillatable;

import processing.core.*;

public class TestSketch extends PApplet {
  
  static private TestSketch self=null;
  
  static private volatile int cmRoller=0;
  
  static private volatile int cmEngageGlass=0;
  
  private static final ScGlassWindow O_GLASS
    = new ScGlassWindow(null, 320, 240);
  
  //=== action
  
  private final EiTriggerable cmQuitting = new EiTriggerable() {
    @Override public void ccTrigger(){
      println(".cmQuitting::call PApplet.exit");
      exit();
    }//+++
  };
  
  private static final Runnable R_SWING_INIT = new Runnable() {
    @Override public void run() {
    
      JMenuItem lpQuitItem=new JMenuItem("_quit");
      lpQuitItem.addActionListener(new ActionListener() {
        @Override public void actionPerformed(ActionEvent e) {
          self.cmQuitting.ccTrigger();
        }
      });
      
      O_GLASS.cmCanvas.ccAddPaintObject(new SiPaintable() {
        @Override public void ccPaint(Graphics pxGI) {
          pxGI.setColor(Color.GREEN);
          
          pxGI.drawRect(100, 100, 50, 50);
          
          pxGI.drawString("XA",100,100);
          pxGI.drawString("XB",100,150);
          pxGI.drawString("XC",150,100);
          pxGI.drawString("XD",150,150);
          
        }
      });
      
      O_GLASS.ccSetEngageMax(32);
      O_GLASS.ccRegisterPopupItem(lpQuitItem);
      
      O_GLASS.ccFinish();
    
    }//+++
  };//***
  
  private static final Runnable R_SWING_LOOP = new Runnable() {
    @Override public void run() {
      O_GLASS.ccDecrementEngageCount();
      O_GLASS.cmCanvas.ccRefresh();
    }//+++
  };//***
  
  //=== overridden
  
  @Override public void setup() {
    
    //-- init
    size(320, 240);
    self=this;
    EcConst.ccSetupSketch(this);
    VcLocalTagger.ccGetInstance().ccInit(this, 7);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    
    //-- laytout
    
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
    
    //-- scanq
    
    //-- update
    VcLocalCoordinator.ccUpdate();
    
    
    //-- ???
    SwingUtilities.invokeLater(R_SWING_LOOP);
    
        
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
  
  //=== roll
  
  static private void ccRoll(){
    cmRoller++;cmRoller&=0x0F;
  }//+++
  
  static public boolean ccIsRollingAbove(int pxZeroToFifteen){
    return cmRoller>pxZeroToFifteen;
  }//+++
  
  static public boolean ccIsRollingAt(int pxZeroToFifteen){
    return cmRoller==pxZeroToFifteen;
  }//+++
  
  //=== entry
  
  static public void main(String[] passedArgs) {
    SwingUtilities.invokeLater(R_SWING_INIT);
    PApplet.main(TestSketch.class.getCanonicalName());
  }//..!
  
}//***eof
