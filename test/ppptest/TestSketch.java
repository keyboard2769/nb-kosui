/* *
 * TEST SKETCH
 */

package ppptest;

import java.awt.Font;
import java.awt.Graphics;
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
  
  private static Graphics cmGRef;
  
  private static final ScGlassWindow O_GLASS
    = new ScGlassWindow(null, 320, 240);
  
  //=== action
  
  private final EiTriggerable cmQuitting = new EiTriggerable() {
    @Override public void ccTrigger(){
      println(".cmQuitting::call PApplet.exit");
      exit();
    }//+++
  };//***
  
  private static final Runnable R_SWING_INIT = new Runnable() {
    @Override public void run() {
    
      JMenuItem lpQuitItem=new JMenuItem("_quit");
      lpQuitItem.addActionListener(new ActionListener() {
        @Override public void actionPerformed(ActionEvent e) {
          self.cmQuitting.ccTrigger();
        }
      });
      
      ScLabel lpUpper = new ScLabel(" do you care? ", 8, 8);
      lpUpper.ccSetLocation(50, 50);
      lpUpper.ccSetupColor(
        ScGlassWindow.C_DEFAULT_PANE_COLOR,
        ScGlassWindow.C_DEFAULT_THEME_COLOR
      );
      lpUpper.ccSetHasBorder(true);
      
      ScLabel lpLower = new ScLabel("nope", 8, 8);
      lpLower.ccSetupColor(
        ScGlassWindow.C_DEFAULT_PANE_COLOR,
        ScGlassWindow.C_DEFAULT_THEME_COLOR
      );
      lpLower.ccSetHasBorder(true);
      
      O_GLASS.cmCanvas.ccAddPaintObject(lpUpper);
      O_GLASS.cmCanvas.ccAddPaintObject(lpLower);
      O_GLASS.ccSetEngageMax(32);
      O_GLASS.ccRegisterPopupItem(lpQuitItem);
      O_GLASS.ccFinish();
      
      cmGRef=O_GLASS.getGraphics();
      
      Font lpTestFont=new Font(Font.DIALOG_INPUT, Font.PLAIN, 12);
      
      VcConst.ccPrintln("windowgraph:", cmGRef.toString());
      O_GLASS.cmCanvas.ccSetFont(lpTestFont);
      
      lpUpper.ccSetSize(O_GLASS.cmCanvas.ccGetMetrics());
      lpLower.ccSetW(lpUpper.ccGetW());
      lpLower.ccSetH(30);
      lpLower.ccSetSize(O_GLASS.cmCanvas.ccGetMetrics(), 'X');
      lpLower.ccSetLocation(
        lpUpper.ccEndX()-lpLower.ccGetW(),
        lpUpper.ccEndY()+5
      );
      lpLower.ccSetIsReversed(true);
      
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
    
  }//++!
  
  @Override public void draw() { 
    
    //-- pre
    background(0);
    O_ROLLER.ccRoll();
    
    //-- scan
    
    //-- update
    VcLocalCoordinator.ccUpdate();
    
    //-- ???
    SwingUtilities.invokeLater(R_SWING_LOOP);
    
    //-- finishing
    VcLocalTagger.ccTag("roller",O_ROLLER.ccGetValue());
    VcLocalTagger.ccTag("page",EcComponent.ccGetCurrentPage());
    VcLocalTagger.ccTag("mouse",VcLocalCoordinator.ccGetMouseOverID());
    VcLocalTagger.ccStabilize();
    
  }//++~
  
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
  }//!!!
  
}//***eof
