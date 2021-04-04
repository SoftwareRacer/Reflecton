#include <DistanceGP2Y0A21YK.h>
#include <Wire.h>
#include <SparkFun_APDS9960.h>

#define APDS9960_INT    2 // Needs to be an interrupt pin

// GLOBAL VARIABLES

// distance sensor
DistanceGP2Y0A21YK distanceSensor;

// gesture sensor
SparkFun_APDS9960 gestureSensor = SparkFun_APDS9960();

// CONSTANTS
int inUseThreshold = 100; // 100/1023 * 5V = 0.5 => (less than 5cm or) less than 60cm
// away / idle: more than about 80cm away
int awayThreshold = 80; // 80/1023 * 5V = 0.4V => (less than 2cm or) more than 80cm

// output variable, which contents the index of the widget that is signalised to be opened
int widget_number;

// STATE

// mirror is in use or not (someone stands in front of it)
boolean inUse = false;

// interrupt was received and gesture should be read from sensor
int isr_flag = 0;

// counter to ensure person is long enough away before marking mirror as idle
int countAway = 0;

// setup function is called on boot
void setup() {

  //Set output pins for all types of possible outputs
  pinMode(8, OUTPUT);//output for LEFT
  pinMode(9, OUTPUT);//output for UP
  pinMode(10, OUTPUT);//output for DOWN
  pinMode(11, OUTPUT);//output for RIGHT
  pinMode(12, OUTPUT);//output for NEAR/FAR
  // init gesture sensor

  // Set interrupt pin as input for gesture sensor
  pinMode(APDS9960_INT, INPUT);

  Serial.begin(9600);

  // Initialize gesture sensor APDS-9960 (configure I2C and initial values)
  if (gestureSensor.init()) {

    Serial.println(F("INFO: APDS-9960 initialization complete"));

    attachInterrupt(digitalPinToInterrupt(APDS9960_INT), interruptRoutine, FALLING);
    
  } else {

    Serial.println(F("ERROR: Something went wrong during APDS-9960 init!"));
  }
  
  // start listening to distance sensor
  distanceSensor.begin(A0);
  int initDistance = distanceSensor.getDistanceRaw();

  Serial.print(F("INFO: Distance sensor listening, initial distance: "));
  Serial.println(initDistance);
}

// main loop that is called repeatedly after setup
void loop() {
  
  // enable gesture sensor
  activateGestureSensorAndReportMirrorUse();
  inUse = true;

  // get gestures
  if (inUse) {

    // check if there was an interrupt in the meantime
    handleInterrupt();
    //HERE: send output to java-program
  }
  
  // wait before checking distance again and activate gesture sensor
  else {

    // wait 50ms until next measurement
    delay(50);
  }
}

void activateGestureSensorAndReportMirrorUse() {

  countAway = 0;
  inUse = true;
  //Serial.println(F("Person: PRESENT"));

  if (gestureSensor.enableGestureSensor(true)) {
    //Serial.println(F("INFO: Gesture sensor is now running"));
  } else {
    Serial.println(F("ERROR: Something went wrong during gesture sensor enable!"));
  }
}

void disableGestureSensorAndReportMirrorIdle() {

  countAway++;

  if (countAway > 100) {

    inUse = false;
    Serial.println(F("Person: AWAY"));

    if (gestureSensor.disableGestureSensor()) {
      Serial.println(F("INFO: Gesture sensor is now disabled"));
    } else {
      Serial.println(F("ERROR: Something went wrong during gesture sensor disable!"));
    }
  }
}

/* check for interrupt, if one is present it means gesture is present,
* in that case stop interrupt handler, handle gesture and attach
* interrupt handler again
*/
void handleInterrupt() {
  
  // if interrupt was set, read and print gesture, reset interrupt flag
  if (isr_flag == 1) {

    detachInterrupt(digitalPinToInterrupt(APDS9960_INT));

    handleGesture();

    isr_flag = 0;

    attachInterrupt(digitalPinToInterrupt(APDS9960_INT), interruptRoutine, FALLING);
  }
}

// interrupt function for interrupt pin that APDS9960 is attached to
void interruptRoutine() {

  isr_flag = 1;
}

// on gestor sensor interrupt send serial message with gesture
void handleGesture() {

  if (gestureSensor.isGestureAvailable()) {

    switch (gestureSensor.readGesture()) {
      case DIR_UP:
        Serial.println(F("Gesture: UP"));
        widget_number = 1;
        break;
      case DIR_DOWN:
        Serial.println(F("Gesture: DOWN"));
        widget_number = 2;
        break;
      case DIR_LEFT:
        Serial.println(F("Gesture: LEFT"));
        widget_number = 5;
        break;
      case DIR_RIGHT:
        Serial.println(F("Gesture: RIGHT"));
        widget_number = 3;
        break;
      case DIR_NEAR:
        Serial.println(F("Gesture: NEAR"));
        widget_number = 4;
        break;
      case DIR_FAR:
        Serial.println(F("Gesture: FAR"));
        widget_number = 4;
        break;
      default:
        Serial.println(F("Gesture: NONE"));
        widget_number = 0;
    }
  }

  if(widget_number == 4){//NEAR/FAR HIGH setzen
     digitalWrite(12, HIGH);
     digitalWrite(11, HIGH);
     digitalWrite(10, HIGH);
     digitalWrite(9, HIGH);
     digitalWrite(8, HIGH);
  }
  else if(widget_number == 3){//RIGHT HIGH setzen
     digitalWrite(12, HIGH);
     digitalWrite(11, HIGH);
     digitalWrite(10, HIGH);
     digitalWrite(9, HIGH);
     digitalWrite(8, HIGH);
  }
  else if(widget_number == 2){//DOWN HIGH setzen
     digitalWrite(12, HIGH);
     digitalWrite(11, HIGH);
     digitalWrite(10, HIGH);
     digitalWrite(9, HIGH);
     digitalWrite(8, HIGH);
  }
  else if(widget_number == 1){//UP HIGH setzen
     digitalWrite(12, HIGH);
     digitalWrite(11, HIGH);
     digitalWrite(10, HIGH);
     digitalWrite(9, HIGH);
     digitalWrite(8, HIGH);
  }
  else if(widget_number == 5){//LEFT HIGH setzen
     digitalWrite(12, HIGH);
     digitalWrite(11, HIGH);
     digitalWrite(10, HIGH);
     digitalWrite(9, HIGH);
     digitalWrite(8, HIGH);
  }
  else{//alles HIGH setzen, wenn keine Bewegung erkannt wurde
     digitalWrite(12, HIGH);
     digitalWrite(11, HIGH);
     digitalWrite(10, HIGH);
     digitalWrite(9, HIGH);
     digitalWrite(8, HIGH);
  }
}
