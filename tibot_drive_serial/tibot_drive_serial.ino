
/*
 * By Katherine Zamudio-Turcotte for Arduino Uno from DFRobotshop Rover kit
 */

// PINS
#define pinSpeedR 5 // Right Speed Control (cannot be changed)
#define pinSpeedL 6 // Left Speed Control (cannot be changed)
#define pinDirR   7 // Right Direction Control (cannot be changed)
#define pinDirL   8 // Left Direction Control (cannot be changed)

// COMMANDS
#define BUTTON_UP      5
#define BUTTON_DOWN    6
#define BUTTON_LEFT    7
#define BUTTON_RIGHT   8
#define BUTTON_RELEASE 0

// PRESETS
#define HIGH_R 255
#define HIGH_L 255

// ROBOT STATE (default=forward)
int speedR = 0;
int dirR   = LOW;
int speedL = 0;
int dirL   = LOW;

void startForward() {
  if (dirR != LOW) {
    analogWrite(pinSpeedR, 0);
    speedR = 0;
    digitalWrite(pinDirR, LOW);
    dirR = LOW;
  }
  if (dirL != LOW) {
    analogWrite(pinSpeedL, 0);
    speedL = 0;
    digitalWrite(pinDirL, LOW);
    dirL = LOW;
  }
  if (speedR != HIGH_R) {
    analogWrite(pinSpeedR, HIGH_R);
    speedR = HIGH_R;
  }
  if (speedL != HIGH_L) {
    analogWrite(pinSpeedL, HIGH_L);
    speedL = HIGH_L;
  }
}

void startBackward() {
  if (dirR != HIGH) {
    analogWrite(pinSpeedR, 0);
    speedR = 0;
    digitalWrite(pinDirR, HIGH);
    dirR = HIGH;
  }
  if (dirL != HIGH) {
    analogWrite(pinSpeedL, 0);
    speedL = 0;
    digitalWrite(pinDirL, HIGH);
    dirL = HIGH;
  }
  if (speedR != HIGH_R) {
    analogWrite(pinSpeedR, HIGH_R);
    speedR = HIGH_R;
  }
  if (speedL != HIGH_L) {
    analogWrite(pinSpeedL, HIGH_L);
    speedL = HIGH_L;
  }
}

void startLeft() {
  if (dirR != LOW) {
    analogWrite(pinSpeedR, 0);
    speedR = 0;
    digitalWrite(pinDirR, LOW);
    dirR = LOW;
  }
  if (dirL != HIGH) {
    analogWrite(pinSpeedL, 0);
    speedL = 0;
    digitalWrite(pinDirL, HIGH);
    dirL = HIGH;
  }
  if (speedR != HIGH_R) {
    analogWrite(pinSpeedR, HIGH_R);
    speedR = HIGH_R;
  }
  if (speedL != HIGH_L) {
    analogWrite(pinSpeedL, HIGH_L);
    speedL = HIGH_L;
  }
}

void startRight() {
  if (dirR != HIGH) {
    analogWrite(pinSpeedR, 0);
    speedR = 0;
    digitalWrite(pinDirR, HIGH);
    dirR = HIGH;
  }
  if (dirL != LOW) {
    analogWrite(pinSpeedL, 0);
    speedL = 0;
    digitalWrite(pinDirL, LOW);
    dirL = LOW;
  }
  if (speedR != HIGH_R) {
    analogWrite(pinSpeedR, HIGH_R);
    speedR = HIGH_R;
  }
  if (speedL != HIGH_L) {
    analogWrite(pinSpeedL, HIGH_L);
    speedL = HIGH_L;
  }
}

void stopRobot() {
  if (speedR != 0) {
    analogWrite(pinSpeedR, 0);
    speedR = 0;
  }
  if (speedL != 0) {
    analogWrite(pinSpeedL, 0);
    speedL = 0;
  }
}

void setup() {
  Serial.begin(115200);
}

void loop() {
  if(Serial.available()) {
    char data_rcvd = Serial.read();   // read one byte from serial buffer and save to data_rcvd
    switch(data_rcvd) {
      case BUTTON_UP: startForward(); break;
      case BUTTON_DOWN: startBackward(); break;
      case BUTTON_LEFT: startLeft(); break;
      case BUTTON_RIGHT: startRight(); break;
      case BUTTON_RELEASE: stopRobot(); break;
      default: break;
    }
  }
}
