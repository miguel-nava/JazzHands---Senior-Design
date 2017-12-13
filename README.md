# csce483-jazzhands
![logo](/img/logo.JPG)

**Peter Myers, Qinqi "Bill" Wang, Miguel Nava, Eunsu Kim, Storm Rubio, Stephen Rettenmaier**

## Running instructions 

*Download jazzhandsEXE.jar

*Connect the Glove Hands using the bluetooth. the password is still "1234". Follow connecting to Bluetooth Instructions.

![Application](/img/Javaapplication.png)

*Select the COM Port for each glove and select the dominant hand. Then run the application. 

## Control a computer with your hands
Jazz Hands is a globe-based human interface device that allows the user to freely control their computer. The basic functionality of this device is the control of the mouse pointer. The user is able to control their mouse pointer by tilting their hand and clicking their fingers. Jazz Hands also allows for a more natural experience with 3D Modeling. Jazz Hands is custom build to interact with Tinkercad.

## Table of Contents

* [Acquiring the source code](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#acquiring-the-source-code)
* [Setup](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#setup)
  * [Arduino](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#arduino)
    * [Download](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#download)
    * [Library](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#library)
  * [Java](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#java)
    * [Download](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#download-1)
    * [Workspace](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#workspace)
    * [Mouse Project](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#mouse-project)
    * [Libraries](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#libraries)
* [Usage](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#usage)
    * [Master](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#master)
    * [Slave1](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#slave-1)
    * [Slave2](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#slave-2)
    * [Bluetooth](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#bluetooth)
* [Control Flow](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#control-flow)
* [Wiring](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#wiring)
    * [Slave1 (Left Hand)](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#slave-1-left-hand)
    * [Slave1 (Right Hand)](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#slave-1-right-hand)
    * [Master](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#master-1)
    * [Pressure Sensors](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#pressure-sensors)
    * [Bluetooth](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#bluetooth-1)
    * [IMU](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#imu)
* [Truebleshooting](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#troubleshooting)
    * [Loss of Cursor Control](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#loss-of-cursor-control)
    * [Connection not established](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#connection-not-established)
    * [COM port in use](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#com-port-in-use)
    * [COM port driver error](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#com-port-driver-error)
* [Credits](https://github.com/ranierolg/csce483-jazzhands/blob/master/README.md#credits )


## Setup

### Acquiring the source code
```
git clone https://github.com/ranierolg/csce483-jazzhands.git
```

### Arduino 
Arduino IDE is used in order to program the Arduino nanos. 

#### Download
https://www.arduino.cc/en/Main/Software

#### Library
Take the following steps to obtain the necessary library.
```
Sketch -> Include Library -> Manage Libraries...
```
Then you should get a window pop up and in the search bar type 
```
SparkFun MPU-9250 9 DOF IMU Breakout
```
Click on it and then click "Install".
![step1](/img/arduino-lib-ide.JPG)
![step2](/img/arduino-lib-manager.JPG)
### Java
Eclipse Java IDE is used to run the Java code that reads data from the Arduino. Any of the Java IDE should work just fine. We used the Oxygen and Neon releases.Oxygen is the most current release. Once Eclipse is downloaded and installed the Workspace needs to be setup. 
#### Download
http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/oxygen1a

All the necessary Java libraries needed come inside the **lib** folder.

#### Workspace
1. Open Eclipse
2. When prompted to choose a **Workspace**, click **Browse...**
3. Navigate to the downloaded github repository.
4. Select **csce483-jazzhands** folder as your **Workspace**. Or whichever folder contains the **Mouse** folder.
5. Click **OK**

Now that the **Workspace** is setup, the Mouse project needs to be imported.

#### Mouse Project
All this is to be done in Eclipse inside the appropriate Workspace that was created.
```
File -> Import... 
```
![java-step1](/img/java-import-step1.JPG)
```
General -> Projects from Folder or Archive -> Next 
```
![java-step2](/img/java-import-step2.JPG)
```
Directory... -> Mouse -> OK
```
![java-step3](/img/java-import-step3.JPG)
```
Finish
```
![java-step4](/img/java-import-step4.JPG)

In the end, **Mouse** should show up in the left hand side of Eclipse.
Navigate to:
```
Mouse -> src -> (default packages) -> ArduinoConnect.java
```
Open **ArduinoConnect.java**
![java-step5](/img/java-import-step5.JPG)
<p>Now that the files are in Eclipse, the libraries need to be properly installed.

#### Libraries
```
Project -> Properties
```
![java-lib-step1](/img/java-lib-step1.JPG)
```
Java Build Path -> Libraries
```
![java-lib-step2](/img/java-lib-step.JPG)

Select four libraries shown and click **Remove**

![java-lib-step3](/img/java-lib-step3.JPG)

Click **Add JARs...** and navigate to the **lib/arduino** folder
```
Mouse -> lib -> arduino
```
Click on **arduino.jar**. Then click **OK**.

![java-lib-step4](/img/java-lib-step4.JPG)

Repeat the process for **jSerialComm-1.3.11.jar**

![java-lib-step5](/img/java-lib-step5.JPG)

Click **OK**. Now all the necessary libraries are installed and the Java application may be run in order to accept input from the Arduino.

## Usage
Naming of components are based on the following picture.
![glove](/img/glove.JPG)

### Master
This Nano runs the code found in the [Master folder](https://github.com/ranierolg/csce483-jazzhands/tree/master/Master) of the repository. Each hand has its respective master code that is uploaded into the Nanos. To upload the code:
1. Connect the Nano using the mini USB cable to the computer.
1. Open the respective master file using the Arduino IDE.
1. Click the **Upload** 
The file should compile and then upload. When the upload finishes the Nano may be disconnected and is now ready for use whenever it gets powered on.

The master takes all of the analog data from the IMU and smooths it out to be used in the java code. As well the master takes all of the digital data that is being sent to it from the slave 1. The digital and analog data is compiled into a single string of digits that are transferred from our bluetooth to the computer where it will be processed.

While the master will always record the same data how the java file interprets those commands depends on which hand is set. The master can be set to main hand or the off hand. The main hand will handle all of the mouse functions. This is the left click, right click, and the actual movement itself. The off hand however acts more as a keyboard and contains several sets of macros that can be used in conjunction with the mouse to accomplish things easily. Such as copy and pasting things or clicking the undo button. All the buttons are listed in the picture below.

![Page 4](/img/4.PNG)


### Slave 1
This Nano handles the input from the pressure sensors. The input is converted from a analog signal to a digital signal. The digital signal is then transferred over to the Master Nano. 
This Nano runs the code found in the [Slave 1 folder](https://github.com/ranierolg/csce483-jazzhands/tree/master/Slave%201) of the repository. Each hand has its respective slave 1 code that is uploaded into the Nano in the same manner as the master code. 

### Slave 2
This Nano uses a Daisy Chain wiring schema to power the other two Nanos on the glove. The processing power for this nano isn’t used at the current moment but is meant for future extendability. This way if in the future we ever needed to add on more sensors or wanted to change our design radically we would have the space and power to do so easily. This brings a great flexibility to to our project just by the slave 2 being open and ready to take on any role that is needed of it besides it’s current power solution role.

### Bluetooth 
There is no code needed for the bluetooth on the glove. The bluetooth just needs to get paired with the computer that has the Java code ready to run.

#### Connecting the Bluetooth
1. Open **Bluetooth settings** 
1. Turn on Bluetooth
1. Look for **LEFTJAZZHAND or RIGHTJAZZHAND**
1. Select it and click **Pair**
1. Pairing code is **0000**
1. Look for and click **More Bluetooth options**
1. Click on **COM** tab
1. Take note of which COM port is the **Outgoing** one. 
1. Go into the Java code and change **COM6** to your **Outgoing** COM port

## Control Flow
![control-flow](/img/control-flow.JPG)

## Wiring 
### Slave 1 (Left hand)
 Pointer Finger= A0 ->D2
 
 Second Finger= A1 -> D3
 
 Ring Finger= A2 -> D4
 
 Pinky = A3 -> D5
 
 Vin = 5V from Slave 2
 
 3.3v = split to all pressure sensors
 
### Slave 1 (Right hand) 
 Pointer Finger= A3 ->D2
 
 Second Finger= A2 -> D3
 
 Ring Finger= A1 -> D4
 
 Pinky = A0 -> D5
 
 Vin = 5V from Slave 2
 
 3.3v = split to all pressure sensors
 
 GND = connected to Common

### Master
 A4 = SDA from IMU
 
 A5 = SCL from IMU

 D5 = Pinky Finger (comes from D5 Slave 1) 

 D6 =  Ring finger (comes from D4 Slave 1)

 D7 = Second Finger (comes from D3 Slave 1)

 D8 = Pointer finger (Comes from D2 Slave 1)

 Tx1= Rxd from Bluetooth

 Rx0 =Txd from Bluetooth

 VIN = VIN from Slave 2

 Gnd= common ground

### Pressure Sensors
 Right pin= 3.3 V from Slave 1

 Left pin = split between Analogy pin and 47k resistor connected to Ground

### Bluetooth
 Txd= Rx0 from Master

 Rxd =Tx1 from Master

 Vin = 5V from Master

 GND= common ground

### IMU
 VDD = 3.3V from Slave 2

 SDA = A4 from Master

 SCL = A5 from Master


## Troubleshooting

### Loss of Cursor Control

If there is an issue with losing connection to the cursor and it becomes locked because of user disconnect without stopping the program. Select CTRL+ALT+DELETE and end the JAVA program to gain access to your cursor again. 

### Connection not established

Connection errors could be caused from the Gloves not being on. In the Bluetooth setting you can detect the the gloves and if you are not seeing anything try restarting the glove and making sure that no other computers within the area are currently paired or using the gloves. They have 60m operating area so they would be easy to detect. 

### COM port in use

For COM port errors one can re add the and remove the COM ports on the laptop. This is done by going to the advance section for the bluetooth and adding or removing COM ports. Try re running the application. if this fails disconnect whichever glove is the one that is failing. Reconnect and double check that you are trying to connect to the correct COM port. 

### COM port driver error

For COM port driver error use the same process for COM port in use. Add and remove the COM port. 


## Credits

Libraries/Code Used:

Pressure Sensors
“Force Sensitive Resistor (FSR).” Using an FSR | Force Sensitive Resistor (FSR) | Adafruit Learning System, learn.adafruit.com/force-sensitive-resistor-fsr/using-an-fsr.
IMU
“SparkFun IMU Breakout - MPU-9250.” Learn at SparkFun Electronics, learn.sparkfun.com/tutorials/mpu-9250-hookup-guide?_ga=2.195188908.1059619630.1512583802-491005975.1506529044.
Arduino Nano
Arduino Nano, https://store.arduino.cc/usa/arduino-nano.
HC-06
Product Data Sheet. Guangzhou HC Information Technology Co, www.olimex.com/Products/Components/RF/BLUETOOTH-SERIAL-HC-06/resources/hc06.pdf.

