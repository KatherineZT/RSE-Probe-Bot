//constantes
int E1 = 6; //M1 Speed Control
int E2 = 5; //M2 Speed Control
int M1 = 8; //M1 Direction Control
int M2 = 7; //M2 Direction Control
int leftspeed = 255;
int rightspeed = 238;
int tooClose = 20;

//pins
#define trigPin 4
#define echoPin 3
#define led 2
#define blues 13

//variables
int mouvementsTab[100][2];
boolean peutAvancer;
int compteurChangementMouvement = 0;
int d;


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
MÉTHODES MOUVEMENT
*/

void stop (void) {
  digitalWrite(E1,LOW);
  analogWrite(E1,0);
  digitalWrite(E2,LOW);
  analogWrite(E2,0);
}

void forward (int a, int b) {
  analogWrite (E1,a);
  digitalWrite(M1,LOW);
  analogWrite (E2,b);
  digitalWrite(M2,LOW);
}

void reverse (int a, int b) {
  analogWrite (E1,a);
  digitalWrite(M1,HIGH);
  analogWrite (E2,b);
  digitalWrite(M2,HIGH);
}

//MÉTHODES POUR TOURNER

void turnLeftNinety (int a, int b) {
  analogWrite(E2,b);
  digitalWrite(M2,LOW);
  delay(1100);
  stop();
}

void turnRightNinety(int a, int b) {
  analogWrite(E1,a);
  digitalWrite(M1,LOW);
  delay(1750);
  stop();
}

void turnLeftFortyFive (int a, int b) {
  analogWrite(E2,b);
  digitalWrite(M2,LOW);
  delay(750);
  stop();
}

void turnRightFortyFive(int a, int b) {
  analogWrite(E1,a);
  digitalWrite(M1,LOW);
  delay(850);
  stop();
}

void turnAround(int a, int b){
  analogWrite(E1,a);
  digitalWrite(M1, LOW);
  delay(2400);
}

//Méthode d'évitement

char avoid (int a, int b){
  //le robot vérifie chaque direction possible
  
  reverse(a,b);
  delay(2000);
  stop();
  delay(200);
  
  turnRightFortyFive(a,b);
  delay(800);
  int distC = distanceCalculator();
  turnRightFortyFive(a,b);
  delay(800);
  int distD = distanceCalculator();
  turnLeftNinety(a,b);
  turnLeftFortyFive(a,b);
  delay(800);
  int distB = distanceCalculator();
  turnLeftFortyFive(a,b);
  delay(800);
  int distA = distanceCalculator();
  
  int distTab[] = {distA,distB,distC,distD};
  
  //tri pour trouver quelle direction offre le chemin le plus libre
  
  for(int i = 0; i < 3; i++){
    if(distTab[i]>distTab[i+1]){
      int y;
      y = distTab[i];
      distTab[i] = distTab[i+1];
      distTab[i+1] = y;
    }
  }
  
  //tourne le robot dans la meilleure direction
  
  if(distTab[3] > 50){
  
    if(distTab[3] == distA){
    return 'a';
    }
    if(distTab[3] == distB){
    turnRightFortyFive(a,b);
    return 'b';
    }
    if(distTab[3] == distC){
    turnRightNinety(a,b);
    turnRightFortyFive(a,b);
    return 'c';
    }
    if(distTab[3] == distD){
    turnRightNinety(a,b);
    turnRightNinety(a,b);
    return 'd';
    }
  }else{
    turnLeftNinety(a,b);
    return 'e';
  }
  
}  

void retour(int a, int b){
  
  //passer dans le tableau pour inverser les mouvements
  for (int c = compteurChangementMouvement; c>=0 ; c--) {
     if (mouvementsTab[c][0]==1) {
       mouvementsTab[c][0]=4;
     }
     else if (mouvementsTab[c][0]==2) {
       mouvementsTab[c][0]=3;
     }
     else if (mouvementsTab[c][0]==3) {
       mouvementsTab[c][0]=2;
     }
     else if (mouvementsTab[c][0]==4) {
       mouvementsTab[c][0]=1;
     }
     else {
       //le chiffre est 5, le mouvement inverse est toujours 5 (turn around)
     }
  }
  
  //faire les mouvements à l'envers
  for (int c = compteurChangementMouvement; c>=0 ; c--) {
    stop();
    delay(400);
    if (mouvementsTab[c][0]==1) {
      turnRightNinety(a,b);
    }
    else if (mouvementsTab[c][0]==2) {
       turnRightFortyFive(a,b);
     }
     else if (mouvementsTab[c][0]==3) {
       turnLeftFortyFive(a,b);
     }
     else if (mouvementsTab[c][0]==4) {
       turnLeftNinety(a,b);
     }
     else if (mouvementsTab[c][0]==5) {
       turnAround(a,b);
     }
    forward(a,b);
    int loops = mouvementsTab[c][1];
    delay(50*loops);
  }
  
  //sortie lorsque le robot est revenu
  Serial.write(1);
  peutAvancer = false;
  
}

//----------------------------------------------------------------------------------

void setup() {
  int i;
  for(i=5;i<=8;i++)
    pinMode(i, OUTPUT);
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(led, OUTPUT);
  pinMode(blues, OUTPUT);
  peutAvancer = false;
}

//----------------------------------------------------------------------------------

void loop() {
  int cmd = 5;
  //Vérifie les commandes de l'utilisateur (lues du MEGA)
  if(Serial.available()>0){
    stop();
    delay(1000);
    cmd = Serial.read();
    if(cmd == '1'){
      peutAvancer = true; //L'utilisateur a appuyé sur start
    }
    if(cmd == '0'){
      mouvementsTab[compteurChangementMouvement][0] = 5;
      retour(leftspeed,rightspeed);//L'utilisateur a appuyé sur recall
    }
  }
  
  //Si l'utilisateur a appuyé start, le robot va commencer à rouler
  if(peutAvancer){
    digitalWrite(blues,HIGH);                       //6 robot blue leds ON when going straight
    digitalWrite(led,LOW); 
    forward (leftspeed,rightspeed); 
    //décider si aller tout droit ou déclencher évitement
    d = distanceCalculator();
    if (d < 200 && d > 3){
    if (d < tooClose) {
     digitalWrite(blues,LOW);                      //blues turned OFF
     digitalWrite(led,HIGH);                       //white led turned ON
     
     char direction = avoid(leftspeed,rightspeed);                     //sauvegarde la lettre associée à la direction à prendre
     
     if(direction == 'a'){                         //Change la vitesse 
       mouvementsTab[compteurChangementMouvement][0] = 4;
     }if(direction == 'b'){
       mouvementsTab[compteurChangementMouvement][0] = 3;
     }if(direction == 'c'){
       mouvementsTab[compteurChangementMouvement][0] = 2;
     }if(direction == 'd'){
       mouvementsTab[compteurChangementMouvement][0] = 1;
     }if(direction == 'e'){
       mouvementsTab[compteurChangementMouvement][0] = 5;
     }
      digitalWrite(led,LOW);                        //white led turned OFF
      digitalWrite(blues,HIGH);
      compteurChangementMouvement ++;
      
    }
    else {
     digitalWrite(led,LOW);                        //led turned OFF
     digitalWrite(blues,HIGH);
      forward (leftspeed,rightspeed);               //aller droit devant
    }
    }
    
    mouvementsTab[compteurChangementMouvement][1] ++;
    
  }
  delay(50); //repos entre chaque fois où il regarde distance si tout va bien
}
