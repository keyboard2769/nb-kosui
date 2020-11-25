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
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import kosui.ppplocalui.EiTriggerable;
import kosui.pppmodel.McTableAdapter;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScTable;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcSwingConsole;
import kosui.ppputil.VcSwingCoordinator;
import processing.core.PApplet;

/**
 *
 * @author Key Parker from K.I.C
 */
public class TestFrame {
  
  private TestFrame(){}//..!
  
  //=== host
  
  private static final JFrame O_FRAME
   = new JFrame("Console Frame v0.1.0");
  
  //=== test
  
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
      ScConst.ccMessageBox(
        "CAST IN THE NAME OF TEST"+VcConst.C_V_NEWLINE
       +"YA NOT GUILTY"
      );
    }//+++
  };
  
  //=== setup
  
  private static JPanel ssCreateTestPane(){
    JPanel lpPane = ScFactory.ccCreateBorderPanel();
    
    final McTableAdapter lpModel=new McTableAdapter(){
      @Override public int getRowCount() {return 255;}//+++
      @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex) {
        return "eat my short!"+PApplet.nf(pxRowIndex, 4);
      }//+++
    };//***
    final ScTable lpTable = new ScTable(lpModel, -1, -1);
    
    final JButton lpRead = new JButton("read");
    VcSwingCoordinator.ccRegisterPressing(lpRead, new EiTriggerable() {
      @Override public void ccTrigger(){
        
        BoundedRangeModel lpModel = lpTable.getVerticalScrollBar().getModel();
        VcConst.ccPrintln("max", lpModel.getMaximum());
        VcConst.ccPrintln("min", lpModel.getMinimum());
        VcConst.ccPrintln("ext", lpModel.getExtent());
        VcConst.ccPrintln("val", lpModel.getValue());
        
      }//+++
    });
    
    final JButton lpScroll = new JButton("scroll");
    VcSwingCoordinator.ccRegisterPressing(lpScroll, new EiTriggerable() {
      @Override public void ccTrigger(){
        ScConst.ccScrollToLast(lpTable);
      }//+++
    });
    
    lpPane.add(lpRead,BorderLayout.PAGE_START);
    lpPane.add(lpTable,BorderLayout.CENTER);
    lpPane.add(lpScroll,BorderLayout.PAGE_END);
    
    return lpPane;
  }//+++
  
  private static void ssSetupFrame() {

    //-- menu ** item
    JMenuItem lpInfoItem = new JMenuItem("Info");
    lpInfoItem.setActionCommand("--action-info");
    lpInfoItem.setMnemonic(KeyEvent.VK_I);
    VcSwingCoordinator.ccRegisterAction(lpInfoItem, T_INFO_POPPING);
    
    JMenuItem lpQuitItem = new JMenuItem("Quit");
    lpQuitItem.setActionCommand("--action-quit");
    lpQuitItem.setMnemonic(KeyEvent.VK_Q);
    VcSwingCoordinator.ccRegisterAction(lpQuitItem, T_QUITTING);
    
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
    
    //-- content ** packup
    JTabbedPane lpContentPane = new JTabbedPane();
    lpContentPane.setBorder(BorderFactory.createEtchedBorder());
    lpContentPane.add("Test", ssCreateTestPane());
    lpContentPane.add("Console", VcSwingConsole.ccGetPanel());
    lpContentPane.updateUI();
    
    //-- frame ** setup
    ScFactory.ccSetupMainFrame(O_FRAME, 320, 240);
    O_FRAME.setJMenuBar(lpMenuBar);
    O_FRAME.setContentPane(lpContentPane);
    O_FRAME.pack();
    O_FRAME.setVisible(true);
    
    //-- coordinating
    VcSwingCoordinator.ccGetInstance().ccInit(O_FRAME);
    VcSwingCoordinator.ccRegisterCommand("quit",T_QUITTING);
    VcSwingCoordinator.ccRegisterCommand("info",T_INFO_POPPING);
    
    //-- post
    VcSwingConsole.ccWriteln("on", VcConst.C_V_OS);
    VcSwingConsole.ccWriteln("at", VcConst.C_V_PWD);
    VcSwingConsole.ccWriteln("*** have fun ***");
    VcSwingConsole.ccRequestFocus();
    
  }//+++
  
  //=== entry
  
  public static final JFrame ccGetFrame(){return O_FRAME;}//+++

  public static void main(String[] args) {
    System.out.println("TestFrame.main()::activate");
    ScConst.ccApplyLookAndFeel(4, false);
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {ssSetupFrame();}//+++
    });
    System.out.println("TestFrame.main()::over");
  }//++!

}//***eof
