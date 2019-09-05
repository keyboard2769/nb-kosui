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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import kosui.ppplocalui.EiTriggerable;
import kosui.pppmodel.MiPixillatable;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.ScIcon;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcSwingConsole;
import kosui.ppputil.VcSwingCoordinator;

/**
 *
 * @author Key Parker from K.I.C
 */
public class TestFrame {
  
  private TestFrame(){}//..!
  
  //=== host
  
  private static final ScIcon O_ICON
    = new ScIcon();
  
  private static final JFrame O_FRAME
   = new JFrame("Console Frame v0.1.0");
  
  //=== guest
  
  private static final JTextField O_PTT =ScFactory.ccCreateTextLamp("003");

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
  
  private static void ssSetupIcon(){
    O_ICON.ccFillPixel(0xFF339999, new MiPixillatable() {
      @Override public boolean ccPixillate(int pxX, int pxY) {
        return true;
      }//+++
    });
    O_ICON.ccFillPixel(0xFFEEEEEE, new MiPixillatable() {
      @Override public boolean ccPixillate(int pxX, int pxY) {
        return pxX>pxY;
      }//+++
    });
    O_ICON.ccFillPixel(0xFF111111, new MiPixillatable() {
      @Override public boolean ccPixillate(int pxX, int pxY) {
        return         
          (pxX<=2 || pxX>=29)||
          (pxY<=2 || pxY>=29);
      }//+++
    });
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
    
    //-- test ** packup
    JPanel lpTestTab = ScFactory.ccCreateGridPanel(6, 1);
    lpTestTab.add(O_PTT);
    
    //-- content ** packup
    JTabbedPane lpContentPane = new JTabbedPane();
    lpContentPane.setBorder(BorderFactory.createEtchedBorder());
    lpContentPane.add("Test", lpTestTab);
    lpContentPane.add("Console", VcSwingConsole.ccGetInstance());
    lpContentPane.updateUI();
    
    //-- frame ** setup
    O_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    O_FRAME.setJMenuBar(lpMenuBar);
    O_FRAME.setContentPane(lpContentPane);
    
    //-- frame ** icon
    ssSetupIcon();
    O_FRAME.setIconImage(O_ICON);
    
    //-- frame ** packup
    Point lpOrigin=ScConst.ccGetScreenInitPoint();
    Dimension lpScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension lpWindowSize = new Dimension(320, 240);
    O_FRAME.setLocation(
      lpOrigin.x+lpScreenSize.width/2-lpWindowSize.width/2,
      lpOrigin.y+lpScreenSize.height/2-lpWindowSize.height/2
    );
    O_FRAME.setPreferredSize(lpWindowSize);
    O_FRAME.setResizable(false);
    O_FRAME.pack();
    O_FRAME.setVisible(true);
    
    //-- coordination
    VcSwingCoordinator.ccGetInstance().ccInit(O_FRAME);
    
    VcSwingCoordinator.ccRegisterPressing(O_PTT, new EiTriggerable() {
      @Override public void ccTrigger(){
        String lpD=ScConst.ccGetStringByInputBox("give something", "hellow?");
        if(!VcConst.ccIsValidString(lpD)){return;}
        O_PTT.setText(lpD);
      }
    });
    
    VcSwingCoordinator.ccRegisterCommand("quit",T_QUITTING);
    VcSwingCoordinator.ccRegisterCommand("info",T_INFO_POPPING);
    
    //-- post
    VcSwingConsole.ccStackln("on", VcConst.C_V_OS);
    VcSwingConsole.ccStackln("at", VcConst.C_V_PWD);
    VcSwingConsole.ccStackln("*** have fun ***");
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
