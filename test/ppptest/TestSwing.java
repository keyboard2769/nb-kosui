/* *
 * <def A="revision"/>
 * # %flag% "file/version" :issue $description
 * - %% "" : $
 * - %% "" : $
 * - %% "" : $
 * <end/>
 *
 * code : _
 * name : 
 * core : 
 * original : NKH653
 *
 */

package ppptest;

import kosui.pppswingui.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

final class TestSwing extends JPanel implements ActionListener{
  
  static private JFrame lpFrame=new JFrame("TestSwing");

  static private JTextField pbStatusBar=new JTextField("::standby");
  static private JMenuItem pbQuitMI = new JMenuItem("quit");
  static private JMenuItem pbInfoMI = new JMenuItem("info");
  
  //=== initiator
  
  public TestSwing(){
    
    TestSwing lpSelf=this;
    
    //-- public
    
    pbStatusBar.setEditable(false);
    pbStatusBar.setEnabled(false);
    pbStatusBar.setDisabledTextColor(Color.LIGHT_GRAY);
    pbStatusBar.setHorizontalAlignment(JTextField.RIGHT);
    
    pbQuitMI.addActionListener(lpSelf);
    pbInfoMI.addActionListener(lpSelf);
    
    //-- layout
    
    JToolBar lpMainToolBar=new JToolBar();
    lpMainToolBar.setFloatable(false);
    lpMainToolBar.setRollover(false);
    lpMainToolBar.add(ScFactory
      .ccMyCommandButton("dummy","--toolbar-dummy", lpSelf));
    
    setLayout(new BorderLayout());
    add(lpMainToolBar,BorderLayout.PAGE_START);
    add(ScFactory.ccMyDummyScrollList(320, 230),BorderLayout.CENTER);//..replace this!!
    add(pbStatusBar,BorderLayout.PAGE_END);
    
  }//+++
  
  private static void ccInit(){
    
    lpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    lpFrame.setLocation(100, 100);
    lpFrame.setResizable(false);
    
    JMenu lpOperateMenu= new JMenu("operate");
    lpOperateMenu.add(pbQuitMI);
    
    JMenu lpInfoMenu=new JMenu("info");
    lpInfoMenu.add(pbInfoMI);
    
    JMenuBar lpMainMenuBar = new JMenuBar();
    lpMainMenuBar.add(lpOperateMenu);
    lpMainMenuBar.add(lpInfoMenu);
    
    lpFrame.setJMenuBar(lpMainMenuBar);

    JPanel lpContentPane=new TestSwing();
    lpContentPane.setOpaque(true);
    lpFrame.setContentPane(lpContentPane);

    lpFrame.pack();
    lpFrame.setVisible(true);
  }//+++
  
  //=== support
  
  private void fsPover(){
    System.exit(0);
  }//+++
  
  //=== interface

  @Override  public void actionPerformed(ActionEvent e){
    String lpCommand=e.getActionCommand();
    
    if(lpCommand.equals("info")){
      JOptionPane.showMessageDialog(lpFrame, "help info unavailable");
      return;
    }
    if(lpCommand.equals("quit")){fsPover();return;}
    
    System.err.println(".actionPerformed()::unhandled:"+lpCommand);
  }//+++

  //=== entry
  
  public static void main(String[] args){
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        ccInit();
      }
    });
  }//+++

}//***eof
