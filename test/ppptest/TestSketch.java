/* *
 * TEST SKETCH
 */

package ppptest;

import java.util.ArrayList;
import java.util.Arrays;

import kosui.ppplocalui.*;
import kosui.ppplogic.*;
import kosui.pppswingui.*;
import kosui.ppputil.*;

import javax.swing.SwingUtilities;

import processing.core.*;

public class TestSketch extends PApplet {
  
  static volatile int pbRoller=0;
  
  //=== overridden
  
  
  @Override public void setup() {
    
    size(320, 240);
    noSmooth();
    EcConst.ccInit(this);
    
    
  }//+++
  
  @Override public void draw() { 
    
    background(0);
    pbRoller++;pbRoller&=0x0F;
    
    //-- test bit
    int lpTestValue=ceil(map(mouseX,0,width,0,500));
    float lpTestRatio=map((float)mouseX,0.0f,(float)width,0.0f,1.0f);
    boolean lpTestBit=(pbRoller<7);
    boolean lpFullSecondPLS=pbRoller==7;
    
    //-- local loop
    //   DONT DELET THE IF PART!!
    if(lpTestBit){
      
    }else{
      
    }
    
    //-- AND DONT DELETE THIS
    
    
    //-- finishing
    VcTagger.ccTag("roller",pbRoller);
    VcTagger.ccStabilize();
    
  }//+++
  
  @Override public void keyPressed() {
    if(VcConsole.ccKeyTyped(key, keyCode)){return;}
    
    switch(key){
      
      case 'q':fsPover();break;
      default:break;
    }
  }//+++
  
  //=== supportor
  
  void fsPover(){
    println("-- TestSketch::exit done.");
    exit();
  }//+++
  
  //=== utility
    
  boolean fnIsPressed(char pxKey){
    return keyPressed && (key==pxKey);
  }//+++
  
  //=== inner
  
  //=== tests
  
  //=== entry
  
  static public void main(String[] passedArgs) {
    PApplet.main(TestSketch.class.getCanonicalName());
    /* [ALT]::
      String[] appletArgs = new String[] { "TestSketch" };
      if (passedArgs != null) {PApplet.main(concat(appletArgs, passedArgs));}
      else {PApplet.main(appletArgs);}
    */
  }//+++
  
}//***eof
