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

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EiTriggerable;
import kosui.pppmodel.McSemirigidTable;
import kosui.pppswingui.ScCanvas;
import kosui.pppswingui.ScChanneldLineChart;
import kosui.pppswingui.ScTable;
import kosui.pppswingui.ScTitledWindow;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalCoordinator;
import processing.core.PApplet;

public class DemoLineChart extends PApplet{
  
  private static int cmRoller = 0;
  
  private static final String C_INFO
    = "..press F to start log";
  
  //=== model
  
  public volatile float pbMarker = 0f;
  
  float cmLiner = 120f;
  float cmSiner = 0f;
  float cmMarker = -1f;
  int cmMarkerColor = 0xFF;
  
  boolean cmDoLog=false;
  
  private final McSemirigidTable cmLogModel = new McSemirigidTable() {
    
    //=== 
    @Override public String getColumnName(int pxColumnIndex) {
      switch(pxColumnIndex){
        case 0:return "time";
        case 1:return "value";
        default : return "<%>";
      }//..?
    }//+++

    @Override public int getRowCount() {
      /* 6 */return 3;
    }//+++

    @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex) {
      /* 6 */return "hey!";
    }//+++
    
    //===
    @Override
    public void ccInsertRowFist() {
    } //+++
    @Override public void ccInsertRowAbove(int pxIndex) {}//+++
    @Override public void ccInsertRowBelow(int pxIndex) {}
    @Override public void ccInsertRowLast() {
      /* 6 */VcConst.ccErrln("NOT YET!!");
    }//+++
    
    //===
    @Override public Object ccRetrieveRowFirst() {
      /* 6 */VcConst.ccErrln("NOT YET!!");
      return null;
    }//+++
    @Override public Object ccRetrieveRowAt(int pxIndex){return null;}//+++
    @Override public Object ccRetrieveRowLast() {return null;}//+++
    
  };//.
  
  //=== swing
  
  private final ScTable cmTable = new ScTable(cmLogModel, 200, 240);
  
  private final ScCanvas cmCanvas = new ScCanvas(240, 240);
  
  private final ScChanneldLineChart cmChart
    = new ScChanneldLineChart(220, 100);
  
  private final ScTitledWindow cmWindow = new ScTitledWindow(frame);
  
  //=== action
  
  private final EiTriggerable cmQuitting = new EiTriggerable() {
    @Override public void ccTrigger(){
      println(".cmQuitting::call PApplet.exit");
      exit();
    }//+++
  };
  
  private final EiTriggerable cmLogModeFlipping = new EiTriggerable() {
    @Override public void ccTrigger(){
      cmDoLog=!cmDoLog;
    }//+++
  };
  
  private final EiTriggerable cmMemoryDumping = new EiTriggerable() {
    @Override public void ccTrigger(){
      /* 6 */VcConst.ccPrintln(cmChart.ccGetModel(0)
        .ccGetData().tstPackupLogical(6));
      /* 6 */VcConst.ccPrintln(cmChart.ccGetModel(0)
        .tstPackupData(6));
      /* 6 */VcConst.ccPrintln(cmChart.ccGetModel(0)
        .ccGetData().tstPackupAbsolute(6));
    }//+++
  };
    
  private final Runnable cmSwingSetupRunner = new Runnable() {
    @Override public void run() {
      
      //-- register
      cmChart.ccSetLocation(10, 10);
      cmCanvas.ccAddPaintObject(cmChart);
      
      //-- layout
      JPanel lpContentPanel = new JPanel(new BorderLayout());
      lpContentPanel.add(cmTable,BorderLayout.LINE_START);
      lpContentPanel.add(cmCanvas,BorderLayout.CENTER);
      lpContentPanel.setBorder(BorderFactory.createEtchedBorder());
      
      //-- pack
      cmWindow.ccInit("Line Chart");
      cmWindow.ccAddCenter(lpContentPanel);
      cmWindow.ccFinish(true, 100, 100);
      
      //-- post
      
      /* [test]::
      for(int i=0;i<60;i++){cmChart.ccGetModel(0).ccOffer(0.1f+i*0.01f);}
      cmChart.ccValidataOffsets(0);
      cmCanvas.ccRefresh();
      */
      
    }//+++
  };//***
  
  private final Runnable cmSwingLogRunner = new Runnable() {
    @Override public void run(){
      cmChart.ccGetModel(0).ccOffer(pbMarker);
      cmChart.ccValidataOffsets(0);
      cmCanvas.ccRefresh();
    }//++++
  };//***
  
  //=== overridden
  
  @Override public void setup() {
    
    //-- pre setting
    size(320,240);
    frame.setTitle("Line Chart");
    EcConst.ccSetupSketch(this);
    VcLocalCoordinator.ccGetInstance().ccInit(this);
    
    //-- swing
    SwingUtilities.invokeLater(cmSwingSetupRunner);
    
    //-- bind
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_Q, cmQuitting);
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_F, cmLogModeFlipping);
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_SPACE, cmMemoryDumping);
    
    //-- post
    VcConst.ccPrintln(".setup()::over");
    
  }//+++

  @Override public void draw() {
  
    //-- pre
    background(0);
    ccRoll();
    
    //-- blink
    fill(0xFF33EE33);
    if(ccIsRollingAbove(7)){text(C_INFO,5,5);}
    
    //-- updating
    ccSine();
    ccDamping();
    
    if(!cmDoLog){return;}
    ccMark();
    cmLiner+=random(2f,16f);
    
  }//+++

  @Override public void keyPressed() {
    VcLocalCoordinator.ccKeyPressed(keyCode);
  }//+++
  
  //=== subroutine
  
  private void ccSine(){
    if(cmLiner>=314.15926f){cmLiner=0f;}
    stroke(0xFF33EE33);
    cmSiner=sin(cmLiner/100f)*height;
    line(0,cmSiner,width,cmSiner);
  }//+++
  
  private void ccMark(){
    if(ccIsRollingAt(9)){
      cmMarker=cmSiner;cmMarkerColor=0xFF;
      pbMarker=1.1f-0.8f*(cmMarker/height);
      SwingUtilities.invokeLater(cmSwingLogRunner);
    }//..?
    if(cmMarkerColor>0){
      stroke(cmMarkerColor&0xFF);
      line(0,cmMarker,width,cmMarker);
    }//..?
  }//+++
  
  private void ccDamping(){
    if(cmMarkerColor<=0x0){return;}
    cmMarkerColor-=0x10;
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
  
  public static void main(String[] args) {
    PApplet.main(DemoLineChart.class.getCanonicalName());
  }//..!

}//***eof
