/*
 * Copyright (C) 2019 Key Parker
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

package pppcase;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import kosui.ppplocalui.EiTriggerable;
import kosui.ppplogic.ZcLevelComparator;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScFactory;
import kosui.ppputil.VcArrayUtility;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStringUtility;
import kosui.ppputil.VcSwingConsole;
import kosui.ppputil.VcSwingCoordinator;
import processing.core.PApplet;
import processing.data.StringList;

public class CaseNumericUtility extends JFrame{
  
  private static final StringList O_TEST_LIST = new StringList();
  
  //===
  
  public static final String[] O_CASE_NUMERIC_STRING={
    "0","00","000","0000",
    "1","12","123","12345","","","","","",
    "1","2","4","08","004","4d","-256",
    "-26","-3","-09","-009",
    "0.0","0.003",
    "0.52","1.2","1.32457","1.258","-1.5","-1.22222",
    "2.3.4"
  };
  
  public static final int[] O_CASE_INT={
    1,2,3,4,11,12,13,14,123,1234,1235,123456,1234567,12345678,
    -1,-2,-3,-4,-11,-12,-13,-14,-123,-1234,-1235,-123456,-1234567,-12345678,
    0xFEDC,0xBA98,0x7654,0x3210,
    0xFE,0xDC,0xBA,0x98,0x76,0x54,0x32,0x10
  };
  
  public static final float[] O_CASE_FLOAT = {
    1f,2f,3f,4f,
    1.2f,1.2f,1.23f,1.234f
  };
    
  //==
  
  public final EiTriggerable lvcomp = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcSwingConsole.ccWriteln("ZcLevelCompatator::ccComparate >>>");
      VcSwingConsole.ccWriteln("value","level");
      int[] lpCase=new int[]{
        -3,-992,-1,0,1,12,123,
        234,345,456,567,678,789,899,987,
        999,1000,1001,
        1123,1234,1345,1456,1567,1678,1789,1899,1900,
        2123,2234,2345,2456,2500,2567,2678,2789,2899,2999,
        3123,3234,3345,3456,3567,3678,3789,3899,3999,
        4000
      };//...
      ZcLevelComparator lpController= new ZcLevelComparator(3200);
      for(int it:lpCase){
        VcSwingConsole.ccWriteln(Integer.toString(it), lpController.ccComparate(it));
      }//..~
      VcSwingConsole.ccWriteln("<<<");
    }//+++
  };//***
  
  public final EiTriggerable isfloat = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcSwingConsole.ccWriteln("IsFloatString >>>");
      for(String it:O_CASE_NUMERIC_STRING){
        VcSwingConsole.ccWriteln(it, VcNumericUtility.ccIsFloatString(it));
      }//..~
      VcSwingConsole.ccWriteln("<<<");
    }//+++
  };//***
  
  public final EiTriggerable isint = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcSwingConsole.ccWriteln("ccIsIntegerString >>>");
      for(String it:O_CASE_NUMERIC_STRING){
        VcSwingConsole.ccWriteln(it, VcNumericUtility.ccIsIntegerString(it));
      }//..~
      VcSwingConsole.ccWriteln("<<<");
    }//+++
  };//***
  
  public final EiTriggerable binld = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcSwingConsole.ccWriteln("== ssTestBinaryLoad >>");
      for(int i=-1;i<17;i++){
        VcSwingConsole.ccWriteln(
          Integer.toString(i), 
          VcNumericUtility.ccBinaryLoad(7, i)
        );
      }//..~
      VcSwingConsole.ccWriteln("== <<");
    }//+++
  };//***
  
  public final EiTriggerable binset = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcSwingConsole.ccWriteln("ccBinarySet >>>");
      int lpSource=1;
      for(int i=-1;i<17;i++){
        lpSource=VcNumericUtility.ccBinarySet(lpSource, i, true);
        VcSwingConsole.ccWriteln(
          Integer.toString(i), 
          PApplet.hex(lpSource,16)
        );
      }//..~
      for(int i=-1;i<17;i++){
        lpSource=VcNumericUtility.ccBinarySet(lpSource, i, false);
        VcSwingConsole.ccWriteln(
          Integer.toString(i), 
          PApplet.hex(lpSource,16)
        );
      }//..~
      VcSwingConsole.ccWriteln("<<<");
    }//+++
  };//***
  
  public final EiTriggerable topowii = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcSwingConsole.ccWriteln("ccToPowerOfTwo >>>");
      for(int i:O_CASE_INT){
        VcSwingConsole.ccWriteln(
          VcStringUtility.ccPackupPairedTag
            ("before", i),
          VcStringUtility.ccPackupPairedTag
            ("after ", VcNumericUtility.ccToPowerOfTwo(i))
        );
      }//..~
      VcSwingConsole.ccWriteln("<<<");
    }//+++
  };//***
  
  //=== default action
  
  private static final EiTriggerable T_QUITTING = new EiTriggerable(){
    @Override public void ccTrigger() {
      System.out.println(
        "pppmain.MainActionManager.actionPerformed()::sys_exit <- 0"
      );System.exit(0);
    }//+++
  };//***
  
  private static final EiTriggerable T_INFO_POPPING = new EiTriggerable(){
    @Override public void ccTrigger() {
      ScConst.ccMessageBox(
        "CAST IN THE NAME OF TEST"+VcConst.C_V_NEWLINE
       +"YA NOT GUILTY"
      );
    }//+++
  };//***
  
  private static final EiTriggerable T_LIST_TEST = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcSwingConsole.ccWriteln("registerd >>>");
      for(String it:O_TEST_LIST){
        VcSwingConsole.ccWriteln(it);
      }//..~
      VcSwingConsole.ccWriteln("<<<");
    }//+++
  };//***
  
  private static final EiTriggerable T_CLEAR = new EiTriggerable() {
    @Override public void ccTrigger(){
      VcSwingConsole.ccClear();
    }//+++
  };//***
  
  //=== entry
  
  private static final CaseNumericUtility SELF = new CaseNumericUtility();
  
  private static final Runnable R_FRAME_SETTINGUP = new Runnable() {
    @Override public void run(){
      
      //-- menu ** item
      JMenuItem lpInfoItem = new JMenuItem("Info");
      lpInfoItem.setActionCommand("--action-info");
      lpInfoItem.setMnemonic(KeyEvent.VK_I);
      VcSwingCoordinator.ccRegisterAction(lpInfoItem, T_INFO_POPPING);

      JMenuItem lpQuitItem = new JMenuItem("Quit");
      lpQuitItem.setActionCommand("--action-quit");
      lpQuitItem.setMnemonic(KeyEvent.VK_Q);
      VcSwingCoordinator.ccRegisterAction(lpQuitItem, T_QUITTING);

      //-- menu ** menu
      JMenu lpFileMenu=new JMenu("File");
      lpFileMenu.setMnemonic(KeyEvent.VK_F);
      lpFileMenu.add(lpQuitItem);
      
      JMenu lpHelpMenu = new JMenu("Help");
      lpHelpMenu.setMnemonic(KeyEvent.VK_H);
      lpHelpMenu.add(lpInfoItem);

      //-- menu ** reflective 
      JMenu lpTestMenu=new JMenu("Test");
      lpTestMenu.setMnemonic(KeyEvent.VK_E);
      Class<?> lpIdentity=SELF.getClass();
      Field[] lpDesField = lpIdentity.getFields();
      if(VcArrayUtility.ccIsValidArray(lpDesField)){
        for(Field it:lpDesField){
          Object lpSource=VcConst.ccRetrieveField(it, SELF);
          if(lpSource==null){continue;}
          if(lpSource instanceof EiTriggerable){
            EiTriggerable lpAction=((EiTriggerable)lpSource);
            String lpKey=it.getName();
            JMenuItem lpGeneratedItem= new JMenuItem(lpKey);
            VcSwingCoordinator.ccRegisterAction(lpGeneratedItem, lpAction);
            VcSwingCoordinator.ccRegisterCommand(lpKey, lpAction);
            lpTestMenu.add(lpGeneratedItem);
            O_TEST_LIST.append(lpKey);
          }//..?
        }//..~
      }else{
        VcConst.ccLogln("[!]failed to relect member fields.");
      }//+++
      
      //-- menu ** bar
      JMenuBar lpMenuBar = new JMenuBar();
      lpMenuBar.add(lpFileMenu);
      lpMenuBar.add(lpTestMenu);
      lpMenuBar.add(lpHelpMenu);
      
      //-- pack
      ScFactory.ccSetupMainFrame(SELF, 640, 480);
      SELF.setJMenuBar(lpMenuBar);
      SELF.setContentPane(VcSwingConsole.ccGetPanel());
      SELF.pack();
      SELF.setVisible(true);
      
      //-- coordinating
      VcSwingCoordinator.ccInit(SELF);
      VcSwingCoordinator.ccRegisterCommand("quit",T_QUITTING);
      VcSwingCoordinator.ccRegisterCommand("info",T_INFO_POPPING);
      VcSwingCoordinator.ccRegisterCommand("ls",T_LIST_TEST);
      VcSwingCoordinator.ccRegisterCommand("clear",T_CLEAR);
      
      //-- post
      VcSwingConsole.ccWriteln("on", VcConst.C_V_OS);
      VcSwingConsole.ccWriteln("at", VcConst.C_V_PWD);
      VcSwingConsole.ccWriteln
        ("*** all registered test item can be invoked via comand input ***");
      VcSwingConsole.ccWriteln("*** have fun ***");
      T_LIST_TEST.ccTrigger();
      VcSwingConsole.ccRequestFocus();
      
    }//+++
  };//***
  
  public static void main(String[] args){
    System.out.println("CaseNumericUtilities.main()::start");
    SwingUtilities.invokeLater(R_FRAME_SETTINGUP);
    System.out.println("CaseNumericUtilities.main()::over");
  }//..!
  
}//***eof
