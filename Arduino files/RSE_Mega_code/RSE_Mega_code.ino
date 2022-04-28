#include <Adafruit_Si7021.h>

int ledSwitch = 0;
Adafruit_Si7021 sensor = Adafruit_Si7021();
int temperature = 0;
int humidite = 0;
int lumiere = 0;
int gazs = 0;
int CO = 0;
int etat = 0;

void setup() {
    Serial1.begin(9600);
    Serial2.begin(9600);
}

void loop() {
  
  if(Serial1.available()>0){
    int cmd;
    cmd = Serial1.read();
    Serial2.write(cmd);
  }
  
  if(Serial2.available()>0){
   etat = Serial2.read();

  }
  
  sensor.begin();
  temperature = sensor.readTemperature();
  humidite = sensor.readHumidity();
  lumiere = analogRead(10);
  gazs = analogRead(6); 
  CO = analogRead(8);

  Serial1.write(temperature/256);
  Serial1.write(temperature%256);
  Serial1.write(humidite/256);
  Serial1.write(humidite%256);
  Serial1.write(lumiere/256);
  Serial1.write(lumiere%256);
  Serial1.write(gazs/256);
  Serial1.write(gazs%256);
  Serial1.write(CO/256);
  Serial1.write(CO%256);
  Serial1.write(etat);
  Serial1.flush();
  
  delay(1000);
    
    
}
