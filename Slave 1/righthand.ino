
//****************************RIGHT GLOVE************************************//

int fsrPin0 = 3;     
int fsrReading0;
int state0 = LOW;

int fsrPin1 = 2;     
int fsrReading1;
int state1 = LOW;

int fsrPin2 = 1;     
int fsrReading2;
int state2 = LOW;

int fsrPin3 = 0;     
int fsrReading3; 
int state3 = LOW;

int hold = 600;
int neutral=200;
void setup(void) {
  Serial.begin(115200);
  pinMode(2, OUTPUT);
  pinMode(3, OUTPUT);
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
}
 
void loop(void) {
  fsrReading0 = analogRead(fsrPin0);
  fsrReading1 = analogRead(fsrPin1); 
  fsrReading2 = analogRead(fsrPin2);
  fsrReading3 = analogRead(fsrPin3);  
 

  if (fsrReading0 > hold && state0 == LOW) {
    state0 = HIGH;
    //Serial.print("You just left clicked\n");
  } 
  if (fsrReading1 > hold && state1 == LOW) {
    state1 = HIGH;
    //Serial.print("You just right clicked\n");
  }
  if (fsrReading2 > hold && state2 == LOW) {
    state2 = HIGH;
    //Serial.print("Scrolling held\n");
  } 
  if (fsrReading3 > hold && state3 == LOW) {
    state3 = HIGH;
    //Serial.print("Recenter\n");
  }

  
  if (fsrReading0 < hold-neutral) {
    state0 = LOW;
  } 
  if (fsrReading1 < hold-neutral) {
    state1 = LOW;  }
  if (fsrReading2 < hold-neutral) {
    state2 = LOW;  } 
  if (fsrReading3 < hold-neutral) {
    state3 = LOW;  }

  digitalWrite(2,state0);
  digitalWrite(3,state1);
  digitalWrite(4,state2);
  digitalWrite(5,state3);
  

    //delay(10);
} 
