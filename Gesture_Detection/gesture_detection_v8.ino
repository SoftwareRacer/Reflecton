// Created by Marco Hennermann on 20th of February 2018
#include <Wire.h>
#include <SparkFun_APDS9960.h>

// Pins
#define APDS9960_INT    2 // Needs to be an interrupt pin

// Global Variables
SparkFun_APDS9960 apds = SparkFun_APDS9960();
int isr_flag = 0;
bool state = false;

void setup() {

    // Set interrupt pin as input
    pinMode(APDS9960_INT, INPUT);

    // Initialize Serial port
    Serial.begin(9600);

    // Initialize interrupt service routine
    attachInterrupt(0, interruptRoutine, FALLING);

    // Initialize APDS-9960 (configure I2C and initial values)
    apds.init();

    // Start running the APDS-9960 gesture sensor engine
    apds.enableGestureSensor(true);
}

void loop() {
    if( isr_flag == 1 ) {
        detachInterrupt(0);
        handleGesture();
        isr_flag = 0;
        attachInterrupt(0, interruptRoutine, FALLING);
    }
}

void interruptRoutine() {
    isr_flag = 1;
}

void handleGesture() {
    if ( apds.isGestureAvailable() ) {
        switch ( apds.readGesture() ) {
            case DIR_UP:
                if(state == false) {
                    Serial.println(2);
                    state = true;
                }
                else {
                    Serial.println(-1);
                    state = false;
                }
                break;
            case DIR_DOWN:
                if(state == false) {
                    Serial.println(1);
                    state = true;
                }
                break;
            case DIR_LEFT:
                if(state == false) {
                    Serial.println(3);
                    state = true;
                }
                break;
            case DIR_RIGHT:
                if(state == false) {
                    Serial.println(0);
                    state = true;
                }
                break;
            default:
                break;
        }
    }
}