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

package ppptest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import kosui.ppplocalui.EiTriggerable;
import kosui.pppmodel.McSimpleRingBuffer;
import kosui.pppmodel.McTableAdapter;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScTable;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStampUtility;
import kosui.ppputil.VcSwingCoordinator;

/**
 *
 * @author Key Parker from K.I.C
 */
public class TestRingBuffer {
  
  public static final String C_V_PATHSEP
   = System.getProperty("file.separator");
  
  public static final String C_V_NEWLINE
   = System.getProperty("line.separator");
  
  public static final String C_V_OS
   = System.getProperty("os.name");
  
  public static final String C_V_PWD
   = System.getProperty("user.dir");
  
  private TestRingBuffer(){}//..!
  
  //=== model
  
  private static final McSimpleRingBuffer O_BUFFER
    = new McSimpleRingBuffer(255);
  
  private static final McTableAdapter O_MODEL = new McTableAdapter(){
    @Override public int getColumnCount() {
      return 2;
    }//+++
    @Override public String getColumnName(int pxColumnIndex) {
      switch(pxColumnIndex){
        case 0:return "ADDR";
        case 1:return "VAL";
        default:return "<?>";
      }//..?
    }//+++
    @Override public int getRowCount() {
      return O_BUFFER.ccGetCapacity();
    }//+++
    @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex) {
      switch (pxColumnIndex) {
        case 0:return Integer.toString(pxRowIndex);
        case 1:return Integer.toString(O_BUFFER.ccGet(pxRowIndex));
        default:return "?";
      }//...?
    }//+++
  };
  
  //=== inner ** frame
  
  private static final BufferedImage O_ICON
   = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
  
  private static final JFrame O_FRAME
   = new JFrame("TestRingBuffer v0.01");
  
  //=== inner ** ring
  
  private static final JPanel O_RING_PANE
   = ScFactory.ccCreateBorderPanel();
  
  private static final JTextField O_OFFER_BOX
    = new JTextField("0000");
  
  private static final JTextField O_POLL_BOX
    = new JTextField("0000");
  
  private static final ScTable O_TABLE
    = new ScTable(O_MODEL, -1, -1);
  
  private static final JTextField O_STATUS_BAR
    = new JTextField("[;]");
  
  private static final Random O_RGEN
    = new Random(VcStampUtility.ccSecond()*23);
  
  //=== inner ** console
  
  private static final JPanel O_CONSOLE_PANE
   = ScFactory.ccCreateBorderPanel();
  
  private static final JTextArea O_AREA
   = new JTextArea("standby::"+C_V_NEWLINE);
  
  private static final JTextField O_FIELD
   = new JTextField("");
  
  //=== action
  
  private static final EiTriggerable T_QUITTING = new EiTriggerable() {
    @Override public void ccTrigger() {
      System.out.println(
        "pppmain.MainActionManager.actionPerformed()::sys_exit <- 0"
      );System.exit(0);
    }//+++
  };
  
  private static final EiTriggerable T_INFO_POPPING = new EiTriggerable() {
    @Override public void ccTrigger() {
      ScConst.ccMessageBox("IN THE NAME OF TEST");
    }//+++
  };
  
  //===
  
  private static final EiTriggerable T_UI_REFRESHING = new EiTriggerable() {
    @Override public void ccTrigger() {
      O_STATUS_BAR.setText(O_BUFFER.toString());
      O_TABLE.ccRefresh();
    }//+++
  };
  
  private static final EiTriggerable T_OFFERING = new EiTriggerable() {
    @Override public void ccTrigger() {
      String lpInputed=O_OFFER_BOX.getText();
      int lpNewData;
      if(VcNumericUtility.ccIsIntegerString(lpInputed)){
        lpNewData=Integer.valueOf(lpInputed);
      }else{
        lpNewData=O_RGEN.nextInt()&0xFFFF;
      }//..?
      O_BUFFER.ccOffer(lpNewData);
      O_OFFER_BOX.setText("");
      O_OFFER_BOX.requestFocus();
      T_UI_REFRESHING.ccTrigger();
    }//+++
  };
  
  private static final EiTriggerable T_POLLING = new EiTriggerable() {
    @Override public void ccTrigger() {
      int lpRetrieved=O_BUFFER.ccPoll();
      O_POLL_BOX.setText(Integer.toString(lpRetrieved));
      T_UI_REFRESHING.ccTrigger();
    }//+++
  };
  
  private static final EiTriggerable T_CLEARING = new EiTriggerable() {
    @Override public void ccTrigger() {
      O_BUFFER.ccClear();
      T_UI_REFRESHING.ccTrigger();
    }//+++
  };
  
  //=== setup
  
  private static void ssSetupIcon(){
    for(int x=0;x<32;x++){for(int y=0;y<32;y++){
      O_ICON.setRGB(x, y, 0xFF339933);
      if(x>y){O_ICON.setRGB(x, y, 0xFFEEEEEE);}
      if(
        (x<=2 || x>=29)||
        (y<=2 || y>=29)
      ){O_ICON.setRGB(x, y, 0xFF111111);}
    }}//..?
  }//+++
  
  private static void ssSetupRingPane(){
    
    //-- trigger
    JButton lpOfferSW = ScFactory.ccCreateCommandButton(">>");
    VcSwingCoordinator.ccRegisterComponent(lpOfferSW, T_OFFERING);
    JButton lpPollSW = ScFactory.ccCreateCommandButton("<<");
    VcSwingCoordinator.ccRegisterComponent(lpPollSW, T_POLLING);
    JButton lpClearSW = ScFactory.ccCreateCommandButton("-X-");
    VcSwingCoordinator.ccRegisterComponent(lpClearSW, T_CLEARING);
    
    //-- reserved
    ScFactory.ccSetupTextLamp(O_POLL_BOX, 100, 24);
    ScFactory.ccSetupStatusBar(O_STATUS_BAR, -1, -1);
    
    //-- pack
    JPanel lpLeftWing = ScFactory.ccCreateGridPanel(16, 1);
    lpLeftWing.add(O_POLL_BOX);
    lpLeftWing.add(lpPollSW);
    lpLeftWing.add(O_OFFER_BOX);
    lpLeftWing.add(lpOfferSW);
    lpLeftWing.add(ScFactory.ccCreateSeparator(false));
    lpLeftWing.add(lpClearSW);
    O_RING_PANE.add(lpLeftWing,BorderLayout.LINE_START);
    O_RING_PANE.add(O_TABLE,BorderLayout.CENTER);
    O_RING_PANE.add(O_STATUS_BAR,BorderLayout.PAGE_END);
    
  }//+++
  
  private static void ssSetupConsolePane(){
    
    //-- component ** area
    ScFactory.ccSetupConsoleArea(O_AREA);
    JScrollPane lpCenterPane=new JScrollPane(O_AREA);
    
    //-- component ** field
    O_FIELD.addKeyListener(new KeyListener() {
      @Override public void keyTyped(KeyEvent ke){}//+++
      @Override public void keyPressed(KeyEvent ke){}//+++
      @Override public void keyReleased(KeyEvent ke){
        int lpCharCode=(int)ke.getKeyChar();
        switch(lpCharCode){
          case 0x0A:
            ccStackln(VcSwingCoordinator.ccExecute(O_FIELD.getText()));
            O_FIELD.setText("");
          break;
          default:break;
        }//..?
      }//+++
    });
    
    //-- packing
    O_CONSOLE_PANE.add(lpCenterPane,BorderLayout.CENTER);
    O_CONSOLE_PANE.add(O_FIELD,BorderLayout.PAGE_END);
    
  }//+++
  
  private static void ssSetupFrame() {
    
    //-- menu ** item
    JMenuItem lpInfoItem = new JMenuItem("Info");
    lpInfoItem.setActionCommand("--action-info");
    lpInfoItem.setMnemonic(KeyEvent.VK_I);
    VcSwingCoordinator.ccRegisterComponent(lpInfoItem, T_INFO_POPPING);
    
    JMenuItem lpQuitItem = new JMenuItem("Quit");
    lpQuitItem.setActionCommand("--action-quit");
    lpQuitItem.setMnemonic(KeyEvent.VK_Q);
    VcSwingCoordinator.ccRegisterComponent(lpQuitItem, T_QUITTING);
    
    //-- menu ** bar
    JMenu lpFileMenu=new JMenu("File");
    lpFileMenu.setMnemonic(KeyEvent.VK_F);
    lpFileMenu.add(lpQuitItem);
    
    JMenu lpHelpMenu = new JMenu("Help");
    lpHelpMenu.setMnemonic(KeyEvent.VK_H);
    lpHelpMenu.add(lpInfoItem);
    
    JMenuBar lpMenuBar = new JMenuBar();
    lpMenuBar.add(lpFileMenu);
    lpMenuBar.add(lpHelpMenu);
    
    //-- setup
    ssSetupIcon();
    ssSetupRingPane();
    ssSetupConsolePane();
    
    //-- pack
    JTabbedPane lpContentPane = new JTabbedPane();
    lpContentPane.setBorder(BorderFactory.createEtchedBorder());
    lpContentPane.add("Ring", O_RING_PANE);
    lpContentPane.add("Console", O_CONSOLE_PANE);
    lpContentPane.updateUI();
    
    //-- frame ** setup
    O_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    O_FRAME.setIconImage(O_ICON);
    O_FRAME.setJMenuBar(lpMenuBar);
    O_FRAME.setContentPane(lpContentPane);
    
    //-- frame ** packup
    Point lpOrigin=ScConst.ccGetScreenInitPoint();
    Dimension lpScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension lpWindowSize = new Dimension(640, 480);
    O_FRAME.setLocation(
      lpOrigin.x+lpScreenSize.width/2-lpWindowSize.width/2,
      lpOrigin.y+lpScreenSize.height/2-lpWindowSize.height/2
    );
    O_FRAME.setPreferredSize(lpWindowSize);
    O_FRAME.setResizable(false);
    O_FRAME.pack();
    O_FRAME.setVisible(true);
    VcSwingCoordinator.ccGetInstance().ccInit(O_FRAME);
    
    //-- post
    ccStackln("on", C_V_OS);
    ccStackln("from", C_V_PWD);
    ccStackln("*** have fun ***");
    O_FIELD.requestFocus();
    T_UI_REFRESHING.ccTrigger();
    
  }//+++
  
  //=== utility
  
  public static final void ccStackln(String pxLine){
    ccStackln(pxLine, null);
  }//+++
  
  public static final void ccStackln(String pxTag, Object pxVal){
    if(pxTag==null){return;}
    if(pxVal==null){
      O_AREA.append(pxTag+C_V_NEWLINE);
    }else{
      O_AREA.append(pxTag+":"+pxVal.toString()+C_V_NEWLINE);
    }//..?
    int lpLength = O_AREA.getText().length();
    O_AREA.setSelectionStart(lpLength-1);
    O_AREA.setSelectionEnd(lpLength);
  }//+++

  //=== entry
  
  public static final JFrame ccGetFrame(){return O_FRAME;}//+++

  public static void main(String[] args) {
    System.out.println("TestRingBuffer.main()::activate");
    ScConst.ccApplyLookAndFeel(4, false);
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ssSetupFrame();
        VcSwingCoordinator.ccRegisterCommand("quit",T_QUITTING);
        VcSwingCoordinator.ccRegisterCommand("info",T_INFO_POPPING);
      }//+++
    });
    System.out.println("TestRingBuffer.main()::over");
  }//++!

}//***eof
