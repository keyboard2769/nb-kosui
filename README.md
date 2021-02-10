Key Optional System User Interface
===
my Processing based utility classes, based on processing's `core.jar`, 
for building quit craft-able hardware-panel like SCADA system.
i know it is a nonsense name, but anyway. 

---
## packages

#### local UI
- basically a direct drawing UI library with no event handling mechanism.
- instead, it has two ID based system for mouse and keyboard input.

#### swing UI
- basically a bunch of wrapper class based on JPanel
  to make swing library just a little bit easy to use for me. 
- besides, a none local JWindow is wrapped
  to serve as a container alone side the PApplet frame. 

#### logic
- basically a bunch of model class mimics
  [programmable logic controller](https://en.wikipedia.org/wiki/Programmable_logic_controller) 
  instructions and devices like Timer, Stepper, Flicker, Pulse-Generator, etc. 
- all of these devices is supposed to run in a scan loop.
  so, thus, in processing, it is THE `draw()` loop. 

#### model
- basically a bunch of wrapper classes for processing.core.data, 
  handles some tedious file IO operations.
- some data representation is rearranged for dealing with the two UI thread 
  and outward read/write.

#### utility
- basically a bunch of singleton classes provides
  lots of all singing all dancing utility functions. 
- some of them is referred as a "manager" or "coordinator" for UI components,
  they might work only under the certain UI thread.

#### test
- hand crafted case test codes.
- actual living examples is located here also.
- and bunch of untested experimental codes.

---
## build and compile or Modify
- simply `Open Project ...` or `Import Project | From ZIP ...` 
  if you can use the neatbeans ide.
- originally created on oracle netbeans ide 8.2
   | oracle jdk 1.6 | mac osx 10.8,
  using processing 2.1 core. 
- currently push and pull environment is apache netbeans ide 11
   | oracle open jdk 12 | windows 10 home 1809,
  using processing 2.0 core. 
- personally i still recommend oracle netbeans ide 8.2.
  apache 9.x+ works a little bit tissy to me, but anyway.
- well ok just forget about netbeans.
  the project is just an ant project and the only dependency is THE `core.jar`.

---
## todo list

#### plan 

- [ ] $ tre
- [ ] $ dos
- [ ] $ uno
- [ ] $ all staticalized manager class need to be tested
- [ ] $ the translator is lefted tobe staticalized and refactored

#### heading

- none

#### last

- [x] $ all mannager class is pure static now
- [x] $ local axis now transforms him self and his owner
- [x] $ a new local transformer class for sketch viewport transformation
- [x] $ improve line chart drawer to give more information
- [x] $ let's define more awt color!!
- [x] $ two new class for PGraphics based composing mechanism
- [x] $ a floating JWindow aka "glass pane window"
- [x] $ re-arranged ScLabel for glass pane window
- [x] $ maybe we really need a roller class

<hr><!--EOF-->
