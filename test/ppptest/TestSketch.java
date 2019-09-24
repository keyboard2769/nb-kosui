/* *
 * TEST SKETCH
 */

package ppptest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import kosui.ppputil.VcLocalTagger;
import javax.swing.JMenuItem;
import kosui.ppplocalui.*;
import kosui.pppswingui.*;
import kosui.ppputil.*;
import javax.swing.SwingUtilities;
import kosui.ppplogic.ZcRoller;
import processing.core.*;

public class TestSketch extends PApplet {
  
  static private TestSketch self=null;
  
  private static final ZcRoller O_ROLLER = new ZcRoller(16, 2);
  
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
      
      ScLabel lpL = new ScLabel("XxXaaX", 48, 16);
      lpL.ccSetLocation(100, 100);
      lpL.ccSetupColor(
        ScGlassWindow.C_DEFAULT_PANE_COLOR,
        ScGlassWindow.C_DEFAULT_THEME_COLOR
      );
      lpL.ccSetHasBorder(true);
      
      O_GLASS.ccAddPaintObject(lpL);
      O_GLASS.ccSetEngageMax(32);
      O_GLASS.ccRegisterPopupItem(lpQuitItem);
      O_GLASS.ccFinish();
    
    }//+++
  };//***
  
  private static final Runnable R_SWING_LOOP = new Runnable() {
    @Override public void run() {
      O_GLASS.ccDecrementEngageCount();
      O_GLASS.ccRefresh();
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
      (java.awt.event.KeyEvent.VK_ESCAPE, new EiTriggerable() {
      @Override public void ccTrigger(){
        System.out.println("ESC is Disabled!!");
      }
    });
    
    
    //-- post
    VcConst.ccPrintln("kosui.ppptest.TestSketch.setup()::over");
    
  }//+++
  
  @Override public void draw() { 
    
    //-- pre
    background(0);
    O_ROLLER.ccRoll();
    
    //-- scanq
    
    //-- update
    VcLocalCoordinator.ccUpdate();
    
    //-- ???
    SwingUtilities.invokeLater(R_SWING_LOOP);
    
    //-- finishing
    VcLocalTagger.ccTag("roller",O_ROLLER.ccGetValue());
    VcLocalTagger.ccTag("page",EcComponent.ccGetCurrentPage());
    VcLocalTagger.ccTag("mouse",VcLocalCoordinator.ccGetMouseOverID());
    VcLocalTagger.ccStabilize();
    
  }//+++
  
  @Override public void keyPressed() {
    VcLocalCoordinator.ccGuardEscKey(this);
    VcLocalCoordinator.ccKeyPressed(keyCode);
  }//+++

  @Override public void mousePressed(){
    VcLocalCoordinator.ccMousePressed();
  }//+++
  
  //=== entry
  
  static public void main(String[] passedArgs) {
    SwingUtilities.invokeLater(R_SWING_INIT);
    PApplet.main(TestSketch.class.getCanonicalName());
  }//..!
  
}//***eof
