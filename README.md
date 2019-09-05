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
- besides that a none local JWindow is wrapped
  to serve as a container alone side the PApplet frame. 

#### logic
- basically a bunch of model class mimics
  [programmable logic controller](https://en.wikipedia.org/wiki/Programmable_logic_controller) 
  instructions and devices like Timer, Stepper, Flicker, Pulse-Generator, etc. 
- all of these devices is supposed to run in a scan loop.
  so, thus, in processing, it is THE `draw()`. 

#### model
- basically wrapper classes for processing.core.data, 
  and do some tedious file IO operations.
- some data representation is rearranged for dealing with the two UI thread 
  and out-warded read/write.

#### utility
- a bunch of singleton classes provides
  lots of all singing all dancing utility functions. 
- some of them is referred as a "manager" or "coordinator" for UI components,
  they might work only under the certain UI thread.

#### test
- hand crafted case test codes.
- actual living examples is located here also.
- and bunch of untested experimental codes.

---
## build and compile or Modify
- the project is an ant project. the only the dependency is the `core.jar`.
- originally developed by oracle netbeans ide 8.2, 
  with oracle jdk 1.6, 
  on mac osx 10.8,
  using processing 2.1 core. 
- currently push and pull environment is apache netbeans ide 11,
  with oracle open jdk 12,
  on a chinese rebuilt windows 10 home 1809,
  using processing 2.0 core. 
- personally i still recommend oracle netbeans ide 8.2.
  apache 9.x+ works a little bit tissy to me. 
  but anyway.

---
## todo list

#### behold

- [ ] $ uno
- [ ] $ dos
- [ ] $ quatro? 
- [ ] $ tre!!
- [ ] $ a "drawing" package handles PDF and print stuff

#### plan 

- [ ] $ let's define more awt color!!
- [ ] $ fill up those array packup methods.
- [ ] $ a new test case framed with reflection
- [ ] $ imporve line chart drawer to give more information

#### onfire

- [ ] $ split indexing handling from ring buffer class
- [ ] $ re-implement local text stoker with ring buffer

#### currently

- [x] $ merged EcTip into EcTipMamager, make it a real manager.
- [x] $ rewritten all swing dialog method comment
- [x] $ a new EcImage class
- [x] $ a ScIcon class
- [x] $ make a class and demo for line chart.
- [x] $ a line chart class
- [x] $ a multi screen mechanism for local ui
- [x] $ a click-able map for swing ui

<hr><!--EOF-->
