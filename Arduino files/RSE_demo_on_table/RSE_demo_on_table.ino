//const
int E1 = 6; //M1 Speed Control
int E2 = 5; //M2 Speed Control
int M1 = 8; //M1 Direction Control
int M2 = 7; //M2 Direction Control
int leftspeed = 255;
int rightspeed = 238;

//pins
#define trigPin 4
#define echoPin 3
#define led 2
#define blues 13

//distance
int d = 0;

/*------------------------------------------------------------------------------------
DISTANCE READING
*/

int distanceCalculator (void) {
  long duration;
  int distance;
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
  distance = (duration/2) / 29.1;
  return distance;
}

/*------------------------------------------------------------------------------------
DIRECTION CHANGE
*/

void halt (void) {  // CALL BEFORE ANY DIRECTION CHANGE PLEASE
  digitalWrite(E1,LOW);
  analogWrite(E1,0);
  digitalWrite(E2,LOW);
  analogWrite(E2,0);
  delay(100);
}

void forward (void) {
  analogWrite (E1,leftspeed);
  digitalWrite(M1,LOW);
  analogWrite (E2,rightspeed);
  digitalWrite(M2,LOW);
}

void reverse (void) {
  analogWrite (E1,leftspeed);
  digitalWrite(M1,HIGH);
  analogWrite (E2,rightspeed);
  digitalWrite(M2,HIGH);
}

/*------------------------------------------------------------------------------------
TURN
*/

void turnRightNinety(void) {
  halt(); // hihi, a PID would make things smoother (sudden halt, then reverse)
  // reverse for a second
  reverse();
  delay(1000);
  halt();
  // turn 90 deg
  analogWrite(E1,leftspeed);
  digitalWrite(M1,LOW);
  delay(1750);
  // stop
  halt();
}

/*------------------------------------------------------------------------------------
ARDUINO SETUP
*/

void setup() {
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(led, OUTPUT);
  pinMode(blues, OUTPUT);
  forward();
}

/*------------------------------------------------------------------------------------
ARDUINO LOOP
*/

void loop() {

  d = distanceCalculator();

  if (d > 10) {
    turnRightNinety();
    forward();
  }

  delay(50);
}
