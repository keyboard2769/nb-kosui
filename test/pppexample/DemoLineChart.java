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
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EiTriggerable;
import kosui.pppmodel.McBufferTable;
import kosui.pppswingui.ScCanvas;
import kosui.pppswingui.ScChanneldLineChart;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScLabel;
import kosui.pppswingui.ScTable;
import kosui.pppswingui.ScTitledWindow;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcLocalCoordinator;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStampUtility;
import kosui.ppputil.VcStringUtility;
import kosui.ppputil.VcSwingCoordinator;
import processing.core.PApplet;

public class DemoLineChart extends PApplet{
  
  private static int cmRoller = 0;
  
  private static final String C_INFO
    = "..press F to start log"+VcConst.C_V_NEWLINE
     +"  press 1 to bring up the trend window";
  
  private static final int C_LOG_MAX = 64;
  
  private static final List O_EMPTY_ROW = new ArrayList<Object>();
  
  //=== model
  
  public volatile float pbMarker = 0f;
  
  float cmLiner = 120f;
  float cmSiner = 0f;
  float cmMarker = -1f;
  int cmMarkerColor = 0xFF;
  
  boolean cmDoLog=true;
  
  private final McBufferTable cmLogModel = new McBufferTable() {
    
    private final Deque<String> cmQueueOfStamp
      = new LinkedList<String>();
    private final Deque<Float> cmQueueOfValue
      = new LinkedList<Float>();
    
    //===
    
    @Override public int getColumnCount() {
      return 2;
    }//+++
    
    @Override public String getColumnName(int pxColumnIndex) {
      switch (pxColumnIndex) {
        case 0:return "time";
        case 1:return "value";
        default:return "<?>";
      }//...?
    }//+++

    @Override public int getRowCount() {
      return cmQueueOfStamp.size();
    }//+++

    @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex) {
    
      switch (pxColumnIndex) {
        case 0:return cmQueueOfStamp.toArray()[pxRowIndex];
        case 1:return cmQueueOfValue.toArray()[pxRowIndex];
        default:return "<?>";
      }//...?
    }//+++
    
    //===
    
    @Override synchronized public void ccInsertRowLast(List pxRow) {
      if(pxRow==null){return;}
      if(pxRow.isEmpty()){return;}
      if(pxRow.size()!=2){return;}
      cmQueueOfStamp.add(pxRow.get(0).toString());
      Object lpV=pxRow.get(1);
      if(lpV instanceof Float){
        cmQueueOfValue.add((Float)lpV);
      }else{
        cmQueueOfValue.add(VcNumericUtility.ccParseFloatString(lpV.toString()));
      }//..?
    }//+++

    @Override synchronized public List ccRetrieveRowFirst() {
      if(getRowCount()<=2){return O_EMPTY_ROW;}
      String lpStamp=cmQueueOfStamp.poll();
      Float lpValue=cmQueueOfValue.poll();
      return Collections.unmodifiableList(Arrays.asList(lpStamp,lpValue));
    }//+++
    
  };//***
  
  //=== swing
  
  private final ScTable cmTable = new ScTable(cmLogModel, 200, 220);
  
  private final ScCanvas cmCanvas = new ScCanvas(240, 220);
  
  private final ScChanneldLineChart cmChart
    = new ScChanneldLineChart(220, 180);
  
  private final ScLabel cmDecorateLabel = new ScLabel(
    "elder <- younger", 48, 22
  );
  
  private final JTextField cmStatusBar = ScFactory.ccCreateStatusBar(-1, -1);
  
  private final ScTitledWindow cmWindow = new ScTitledWindow(frame);
  
  //=== action
  
  private final EiTriggerable cmWindowPopping = new EiTriggerable() {
    @Override public void ccTrigger(){
      cmWindow.setVisible(true);
      cmWindow.toFront();
    }//+++
  };//***
  
  private final EiTriggerable cmQuitting = new EiTriggerable() {
    @Override public void ccTrigger(){
      println(".cmQuitting::call PApplet.exit");
      exit();
    }//+++
  };//***
  
  private final EiTriggerable cmLogModeFlipping = new EiTriggerable() {
    @Override public void ccTrigger(){
      cmDoLog=!cmDoLog;
    }//+++
  };//***
  
  private final EiTriggerable cmMemoryDumping = new EiTriggerable() {
    @Override public void ccTrigger(){
      /* 6 */VcConst.ccPrintln(cmChart.ccGetModel(0)
        .ccGetData().tstPackupLogical(6));
      /* 6 */VcConst.ccPrintln(cmChart.ccGetModel(0)
        .tstPackupData(6));
      /* 6 */VcConst.ccPrintln(cmChart.ccGetModel(0)
        .ccGetData().tstPackupAbsolute(6));
    }//+++
  };//***
    
  private final Runnable cmSwingSetupRunner = new Runnable() {
    @Override public void run() {
      
      //-- canvas
      cmDecorateLabel.ccSetLocation(cmChart, 0, 4);
      cmChart.ccSetLocation(10, 10);
      cmCanvas.ccAddPaintObject(cmChart);
      cmCanvas.ccAddPaintObject(cmDecorateLabel);
      
      //-- toolbar ** button
      JButton lpQuitSW=ScFactory.ccCreateCommandButton("Quit");
      VcSwingCoordinator.ccRegisterAction(lpQuitSW, cmQuitting);
      
      //-- toolbar ** bar
      JToolBar lpBar=ScFactory.ccCreateStuckedToolBar();
      lpBar.add(lpQuitSW);
      lpBar.add(cmStatusBar);
      
      //-- layout
      JPanel lpContentPanel=ScFactory.ccCreateBorderPanel();
      lpContentPanel.add(cmTable,BorderLayout.LINE_START);
      lpContentPanel.add(cmCanvas,BorderLayout.CENTER);
      lpContentPanel.add(lpBar,BorderLayout.PAGE_END);
      
      //-- pack
      cmWindow.ccInit("Line Chart",Color.decode("#6699EE"));
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
      
      //-- chart
      cmChart.ccGetModel(0).ccOffer(pbMarker);
      cmChart.ccValidataOffsets(0);
      cmCanvas.ccRefresh();
      
      //-- retrieve
      int lpLogged = cmLogModel.getRowCount();
      if(lpLogged>C_LOG_MAX){
        int lpRetrievedCount=0;
        List lpRetrievedRow;
        VcConst.ccPrintln("max_reched >>>");
        for(int i=0;i<C_LOG_MAX;i++){
          lpRetrievedRow=cmLogModel.ccRetrieveRowFirst();
          if(lpRetrievedRow.equals(O_EMPTY_ROW)){break;}
          lpRetrievedCount++;
          VcConst.ccPrintln
            (lpRetrievedRow.get(0).toString(), lpRetrievedRow.get(1));
        }//..~
        VcConst.ccPrintln("<<< retrieved", lpRetrievedCount);
      }//+++
      
      //-- table
      cmLogModel.ccInsertRowLast(
        Collections.unmodifiableList(Arrays.asList(
          VcStampUtility.ccDataLogTypeI(),
          Float.valueOf(VcNumericUtility.ccRoundForTwoAfter(pbMarker))
        ))
      );
      cmTable.ccRefresh();
      ScConst.ccScrollToLast(cmTable);
      
      //-- status
      cmStatusBar.setText(
        VcStringUtility.ccPackupPairedTag("size", cmLogModel.getRowCount())
      );
      
    }//++++
  };//***
  
  //=== overridden
  
  @Override public void setup() {
    
    //-- pre setting
    size(320,240);
    frame.setTitle("Line Chart");
    EcConst.ccSetupSketch(this);
    VcLocalCoordinator.ccInit(this);
    
    //-- swing
    SwingUtilities.invokeLater(cmSwingSetupRunner);
    
    //-- bind
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_Q, cmQuitting);
    VcLocalCoordinator.ccRegisterKeyTrigger
      (java.awt.event.KeyEvent.VK_1, cmWindowPopping);
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
      pbMarker=0.99f-0.66f*(cmMarker/height);
      SwingUtilities.invokeLater(cmSwingLogRunner);
    }//..?
    if(cmMarkerColor>0){
      stroke(cmMarkerColor&0xFF);
      line(0,cmMarker,width,cmMarker);
      fill(cmMarkerColor&0xFF);
      text(
        VcNumericUtility.ccFormatPointTwoFloat(pbMarker),
        5,
        cmMarker<(height/2)?cmMarker:(cmMarker-18)
      );
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
