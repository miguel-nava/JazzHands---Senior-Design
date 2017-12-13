/* 
 Hardware setup:
 MPU9250 Breakout --------- Arduino
 VDD ---------------------- 3.3V
 SDA ----------------------- A4
 SCL ----------------------- A5
 GND ---------------------- GND
 */

 /*******************************LEFT GLOVE********************************************************/

#include "quaternionFilters.h"
#include "MPU9250.h"

/*----GLOBAL VARIABLES--------*/
MPU9250 myIMU;
int sample_size  = 2;
int button_sample_size  = 10;

// VARIABLES FOR SMOOTHING
float N = 50;
float alpha = 2/(N+1);
const double rMax=750.0;
const double lMax=750.0;
const double uMax=650.0;
const double dMax=650.0;
const double xNeutral=200.0;
const double yNeutral=200.0;
const double scaleperside= 49.0;
const float rightMaxIncrement=(rMax-xNeutral)/scaleperside;
const float leftMaxIncrement=(lMax-xNeutral)/scaleperside;
const float upMaxIncrement=(uMax-yNeutral)/scaleperside;
const float downMaxIncrement=(dMax-yNeutral)/scaleperside;

int x_rate = 0;
int y_rate = 0;
int z_rate = 0;
 
/*----------------------------*/

class Array {
private:
  int curr_size;
  int max_size;
  float *arr;
public:
  Array(){
    curr_size = 0;
    max_size = 10;
    arr = (float*)malloc(sizeof(float)*max_size);
  }
  Array(int s){
    curr_size = 0;
    max_size = s;
    arr = (float*)malloc(sizeof(float)*max_size);

    //Serial.println("Array constructor " + String(max_size));
  }
  int getCurrentSize(){
    return curr_size;
  }
  int getMaxSize(){
    return max_size;
  }
  void push(float a){
    //Serial.println("pushing " + String(a));
    
    if(curr_size >= max_size){
        pop();
    }   
    if (curr_size < max_size){
      arr[curr_size] = a;
      curr_size = curr_size + 1;
    }
  }

  
  int pop(){
    if(curr_size > 1){
      //Serial.println("Popping");
       for (int i = 0; i < curr_size; i++){
        arr[i] = arr[i+1];
      }
      curr_size--;
    } else{
      //Serial.println("Cannot pop, no items in the list");
    }
    
  }
  void displayArray(){
    Serial.print("{ ");

    if (curr_size <= max_size){
    for (int i = 0; i < curr_size ; i++){
      Serial.print(" " + String(arr[i]) + ", ");
    }
    }
    Serial.println(" }");
  }

  float average(){
    float avg = 0;
    float denominator = 0;
    int power = 0;
    for (int i = curr_size-1; i >= 0; i--){
      avg += (arr[i]*pow(1-alpha,power)); // Data_t + Data_t-1*(1-alpha) + Data_t-2*(1-alpha)^2 + Data_t-4*(1-alpha)^3....
      denominator += pow(1-alpha,power);  // 1 + (1-alpha) + (1-alpha)^2 + (1-alpha)^3....
      power++;
    }
    
    return (avg/denominator);
  }
};
// ============================================================================================

Array x_val(sample_size);
Array y_val(sample_size);
Array z_val(sample_size);

Array button1(button_sample_size);
Array button2(button_sample_size);
Array button3(button_sample_size);
Array button4(button_sample_size);

void determineRateX( float avg, int & rate );
void determineRateY( float avg, int & rate );
void determineRateZ( float avg, int & rate );

void setup()
{
 pinMode(8, INPUT);
 pinMode(7, INPUT);
 pinMode(6, INPUT);
 pinMode(5, INPUT);
 
 Wire.begin();
  Serial.begin(115200);

  byte c = myIMU.readByte(MPU9250_ADDRESS, WHO_AM_I_MPU9250);

  if (c == 0x71) // WHO_AM_I should always be 0x68
  {
    Serial.println("MPU9250 is online...");

    myIMU.MPU9250SelfTest(myIMU.SelfTest);
    myIMU.calibrateMPU9250(myIMU.gyroBias, myIMU.accelBias);
    myIMU.initMPU9250();


    byte d = myIMU.readByte(AK8963_ADDRESS, WHO_AM_I_AK8963);
    myIMU.initAK8963(myIMU.magCalibration);
  } 
  else
  {
    Serial.print("Could not connect to MPU9250: 0x");
    Serial.println(c, HEX);
    while(1) ; // Loop forever if communication doesn't happen
  }
}

void loop()
{
  
  // GETS DATA IF THERE IS SOMETHING TO READ
  if (myIMU.readByte(MPU9250_ADDRESS, INT_STATUS) & 0x01)
  {  
    myIMU.readAccelData(myIMU.accelCount);  // Read the x/y/z adc values
    myIMU.getAres();

    // Now we'll calculate the accleration value into actual g's
    // This depends on scale being set
    myIMU.ax = (float)myIMU.accelCount[0]*myIMU.aRes; // - accelBias[0];
    myIMU.ay = (float)myIMU.accelCount[1]*myIMU.aRes; // - accelBias[1];
    myIMU.az = (float)myIMU.accelCount[2]*myIMU.aRes; // - accelBias[2];
  /*
    myIMU.readGyroData(myIMU.gyroCount);  // Read the x/y/z adc values
    myIMU.getGres();

    // Calculate the gyro value into actual degrees per second
    // This depends on scale being set
    myIMU.gx = (float)myIMU.gyroCount[0]*myIMU.gRes;
    myIMU.gy = (float)myIMU.gyroCount[1]*myIMU.gRes;
    myIMU.gz = (float)myIMU.gyroCount[2]*myIMU.gRes;

    myIMU.readMagData(myIMU.magCount);  // Read the x/y/z adc values
    myIMU.getMres();
    // User environmental x-axis correction in milliGauss, should be
    // automatically calculated
    myIMU.magbias[0] = +470.;
    // User environmental x-axis correction in milliGauss TODO axis??
    myIMU.magbias[1] = +120.;
    // User environmental x-axis correction in milliGauss
    myIMU.magbias[2] = +125.;

    // Calculate the magnetometer values in milliGauss
    // Get actual magnetometer value, this depends on scale being set
    myIMU.mx = (float)myIMU.magCount[0]*myIMU.mRes*myIMU.magCalibration[0] -
               myIMU.magbias[0];
    myIMU.my = (float)myIMU.magCount[1]*myIMU.mRes*myIMU.magCalibration[1] -
               myIMU.magbias[1];
    myIMU.mz = (float)myIMU.magCount[2]*myIMU.mRes*myIMU.magCalibration[2] -
               myIMU.magbias[2];
    */
  } 
  x_val.push(myIMU.ax);
  determineRateX((1000*x_val.average()), x_rate);
      
  y_val.push(myIMU.ay);
  determineRateY((-1000*y_val.average()), y_rate);
      
 // z_val.push(myIMU.az);
 // determineRateZ((1000*z_val.average()), z_rate);


  
  int index = digitalRead(8);
  int middle = digitalRead(7);
  int ring = digitalRead(6);
  int pinky = digitalRead(5);
  button1.push(index);
  button2.push(middle);
  button3.push(ring);
  button4.push(pinky);
  
  if (pinky )
  {
      myIMU.MPU9250SelfTest(myIMU.SelfTest);
      myIMU.calibrateMPU9250(myIMU.gyroBias, myIMU.accelBias);
      myIMU.initMPU9250();
      byte d = myIMU.readByte(AK8963_ADDRESS, WHO_AM_I_AK8963);
      myIMU.initAK8963(myIMU.magCalibration);
  }
  
      
 //Serial.println(1000*x_val.average());
 // Serial.print(" ");
 // Serial.println(1000*y_val.average());
 //Serial.println(1000*z_val.average());
 
  if (x_rate <10)
      Serial.print(0);
  Serial.print(x_rate);
  if (y_rate <10)
      Serial.print(0);
  Serial.print(y_rate);
  if (z_rate <10)
      Serial.print(0);
  Serial.print(z_rate);
  Serial.print((int)button1.average());
  Serial.print((int)button2.average());
  Serial.print((int)button3.average());
  Serial.println((int)button4.average());
 
}

void determineRateY( float avg, int & rate ){
      if( avg >= -yNeutral && avg <= yNeutral)
         rate = 0;
      /* MOVE UP */
      else if (avg > yNeutral && avg <= uMax)
          rate = (avg-yNeutral)/upMaxIncrement;
      else if (avg > uMax)
          rate = scaleperside;
      /*MOVE DOWN*/
      else if (avg < -yNeutral && avg >= -dMax)
          rate = ((avg+yNeutral)/downMaxIncrement)*(-1)+50;
      else if (avg < -dMax)
          rate = scaleperside*2;
}

void determineRateX( float avg, int & rate ){
      /* STAY STILL */
      // range for right hand ( move left range (750-300)
      //range for right hand move right (750-300)
      if( avg >= -xNeutral && avg <= xNeutral)
         rate = 0;
      /* MOVE RIGHT */
      else if (avg > xNeutral && avg <= rMax)
          rate = (avg-xNeutral)/rightMaxIncrement;
      else if (avg > rMax)
          rate = scaleperside;
      /*MOVE LEFT*/
      else if (avg < -xNeutral && avg >= -lMax)
          rate = ((avg+xNeutral)/leftMaxIncrement)*(-1)+50;
      else if (avg < -lMax)
          rate = scaleperside*2;
}
void determineRateZ( float avg, int & rate ){
      if( avg >= -300 && avg <= 300)
         rate = 0;
      /* MOVE RIGHT */
      else if (avg > 300 && avg <= 1100)
          rate = (avg-300)/rightMaxIncrement;
      else if (avg > 1100)
          rate = scaleperside;
      /*MOVE LEFT*/
      else if (avg < -300 && avg >= -750)
          rate = ((avg+300)/leftMaxIncrement)*(-1)+50;
      else if (avg < -750)
          rate = scaleperside*2;
}
