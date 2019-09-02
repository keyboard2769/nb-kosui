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

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.Deque;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcPoint;
import kosui.ppplocalui.EiTriggerable;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScTitledWindow;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcSwingCoordinator;
import processing.core.PApplet;
import static processing.core.PApplet.println;

public class DemoLoopQueue extends PApplet {
  
  static private DemoLoopQueue self;

  static private int cmRoller=0;
  
  static private final String C_MESSAGE = 
    "blinker blinks all the time...";

  //=== overridden
  
  private volatile int cmAmountValue = 0;
  
  private final ScTitledWindow cmOperateWindow = new ScTitledWindow(frame);
  private final JTextField cmAmountTB = new JTextField("00");
  private final JButton cmAddSW = new JButton("add");
  private final JButton cmRemoveSW = new JButton("remove");
  private final JButton cmClearSW = new JButton("clear");
  private final JButton cmQuitSW = new JButton("quit");
  
  private final Deque<EcPoint> cmStackOfPoint = new LinkedList<EcPoint>();
  
  //=== action
  
  private final Runnable cmRefreshAmountTB = new Runnable() {
    @Override public void run(){
      if(!ScConst.ccIsEDT()){return;}
      cmAmountTB.setText(Integer.toString(cmAmountValue));
    }//+++
  };
  
  private final EiTriggerable cmQuit = new EiTriggerable() {
    @Override public void ccTrigger(){
      println(".ssPover()::call PApplet.exit()");
      exit();
    }//+++
  };
  
  private final EiTriggerable cmAddBlinker = new EiTriggerable() {
    @Override public void ccTrigger(){
      cmStackOfPoint.addFirst(new EcPoint(
        ceil(random(1f)*VcNumericUtility.ccFloat(width)),
        ceil(random(1f)*VcNumericUtility.ccFloat(height))
      ));
    }//+++
  };
  
  private final EiTriggerable cmRemoveBlinker = new EiTriggerable() {
    @Override public void ccTrigger(){
      if(cmStackOfPoint.isEmpty()){return;}
      cmStackOfPoint.removeFirst();
    }//+++
  };
  
  private final EiTriggerable cmClearBlinker = new EiTriggerable() {
    @Override public void ccTrigger(){
      if(cmStackOfPoint.isEmpty()){return;}
      cmStackOfPoint.clear();
    }//+++
  };
  
  private final Runnable cmInitSwingUI = new Runnable() {
    @Override public void run(){
      
      if(cmOperateWindow.ccIsFinished()){return;}
      
      VcSwingCoordinator.ccRegisterComponent(cmAddSW, new EiTriggerable() {
        @Override public void ccTrigger(){
          VcLocalCoordinator.ccInvokeLater(cmAddBlinker);
        }//+++
      });
      
      VcSwingCoordinator.ccRegisterComponent(cmRemoveSW,
        new EiTriggerable(){ @Override public void ccTrigger(){
          VcLocalCoordinator.ccInvokeLater(cmRemoveBlinker);
        }}//+++
      );
      
      VcSwingCoordinator.ccRegisterComponent(cmClearSW,
        new EiTriggerable(){ @Override public void ccTrigger(){
          VcLocalCoordinator.ccInvokeLater(cmClearBlinker);
        }}//+++
      );
      
      VcSwingCoordinator.ccRegisterComponent(cmQuitSW,
        new EiTriggerable(){ @Override public void ccTrigger(){
          VcLocalCoordinator.ccInvokeLater(cmQuit);
        }}//+++
      );
      
      ScFactory.ccSetupTextLamp(cmAmountTB, -1, -1);
      
      JPanel lpCenterPane = new JPanel(new GridLayout(8, 1));
      lpCenterPane.add(new JLabel("amount:"));
      lpCenterPane.add(cmAmountTB);
      lpCenterPane.add(new JSeparator(SwingConstants.HORIZONTAL));
      lpCenterPane.add(cmAddSW);
      lpCenterPane.add(cmRemoveSW);
      lpCenterPane.add(cmClearSW);
      lpCenterPane.add(new JSeparator(SwingConstants.HORIZONTAL));
      lpCenterPane.add(cmQuitSW);
    
      cmOperateWindow.ccInit("[+] operate",Color.DARK_GRAY);
      cmOperateWindow.ccAddCenter(lpCenterPane);
      cmOperateWindow.ccFinish(true, 100, 100);
      
    }//+++
  };

  @Override public void setup() {
    println(".setup()::start");

    //-- pre
    size(320, 240);
    EcConst.ccSetupSketch(this);
    frame.setTitle("Loop Queue");
    self=this;
    
    //-- init ** swing
    VcSwingCoordinator.ccGetReference().ccInit(this);
    SwingUtilities.invokeLater(cmInitSwingUI);

    //-- init ** manager
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    
    //-- binding
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_Q, cmQuit);
    
    //-- post setting
    println(".setup()::over");
    
  }//+++
  
  @Override public void draw() { 

    //-- pre
    ssRoll();

    //-- update
    background(0);
    
    //-- update **
    VcLocalCoordinator.ccUpdate();
    
    //-- update **
    cmAmountValue=cmStackOfPoint.size();
    if(!cmStackOfPoint.isEmpty()){
      fill(0x99);
      for(EcPoint it : cmStackOfPoint){
        ellipse(it.ccGetX(), it.ccGetY(), random(8f), random(8f));
      }//..~
    }//..?
    
    //-- update **
    SwingUtilities.invokeLater(cmRefreshAmountTB);
    
    //-- update **
    if(ccIsRollingAbove(7)){
      fill(0xEE);
      text(C_MESSAGE,5,5);
    }//..?
    
  }//+++
  
  @Override public void keyPressed() {
    VcLocalCoordinator.ccKeyPressed(keyCode);
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
  
  static public DemoLoopQueue ccGetSketch(){return self;}//+++
  
  static public PApplet ccGetApplet(){return self;}//+++
  
  static public Frame ccGetFrame(){return self.frame;}//+++

  static public void main(String[] passedArgs) {
    PApplet.main(DemoLoopQueue.class.getCanonicalName());
  }//+++

}//***eof
