# Comment le robot RSE fonctionne (version vulgarisée)

Le robot RSE a des yeux (le capteur à ultrasons à l'avant), un cerveau (la puce électronique qu'on peut voir sur le Arduino Mega sur le dessus), des roues, une source d'énergie (des batteries), une voix (le buzzer sur le dessus) et une antenne (la puce Bluetooth sur le shield Adafruit/Bluefruit sur le dessus).\
\
Comme tout robot, il doit **percevoir** certaines caractéristiques dans son environnement, seulement celles dont il a besoin pour accomplir sa tâche. C'est son entrée d'information. Il doit aussi pouvoir **bouger/parler** et donc interagir avec son environnement : c'est sa sortie d'information. Entre les deux, il doit traiter l'information : transformer l'entrée en sortie. Cela ce passe dans son cerveau, bien évidemment.

# Comment le robot RSE fonctionne (version pour les ingénieurs)

Le robot est bâti avec 3 PCBs (Printed Circuit Boards), dont deux boards Arduino et un shield Bluetooth.\
\
Le kit DFRobotshop Rover V2 sert de structure principale au robot. Celui-ci inclus un des Arduino, un Arduino Uno modifié.\
\
Le Arduino Uno modifié contient quelques fonctionnalités de plus qu'un Arduino Uno normal, dont un pont-en-H (H-bridge), qui est une puce électronique spéciale permettant de contrôler des moteurs DC (voir https://cours.polymtl.ca/inf1900/materiel/pontH/ pour une explication plus détaillée d'un pont-en-H).\
\
En résumé, le Arduino Uno bâtit les données qui seront envoyées aux moteurs pour les faire rouler. Ces données passent par les 2 bornes vertes sur chaque côté du PCB. Les bornes sont connectées aux pins digitales 5, 6, 7 et 8 sur la puce ATmega qui sert d'ordinateur sur le Arduino Uno.\
\
Le shield Bluetooth est principalement composé d'une puce Bluetooth Low Energy (BLE), qui communique avec l'application iOS/Android. Une fois stacké sur un Arduino quelconque, les traces sur le shield/PCB connectent la puce Bluetooth avec certaines pins sur le Arduino Mega. En fait, dans l'exemple ici, on utilise le port SPI hardware (aussi appelé ICSP, puisqu'il peut aussi servir à programmer l'Arduino), et les pins digitales 7 et 8.\
\
On remarque que le shield Bluetooth et les moteurs utilisaient les pins digitales 7 et 8. C'est ce conflit qui est à l'origine de l'utilisation du Arduino Mega. On aurait pu stacker le shield sur le Arduino Uno directement, mais on n'aurait jamais pu changer la direction des moteurs (qui est contrôlée par les pins 7 et 8). C'est le shield qui aurait remporté le conflit (essayé par Katherine).\
\
L'alimentation du Arduino Mega est une batterie LiPo rechargeable de 7.4V. L'alimentation du Arduino Uno et donc des moteurs est un paquet de 4 batteries AA se trouvant sous le robot.\
\
Le capteur à ultrasons n'est pas utilisé sur le robot, il est simplement mis là pour que le robot ait l'air d'avoir des yeux!