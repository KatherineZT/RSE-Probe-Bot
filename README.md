# RSE : Robot Arduino pour ateliers pédagogiques

Fabriqué dans le cadre d'un projet de fin de programme en Science Informatiques et Mathématiques au Collège de Bois-de-Boulogne\
Cours 420-204-RE, Hiver 2018\
\
Le robot RSE est facilement pilotable à l'aide d'une application qui fonctionne sur iOS et sur Android.\
**Attention: Le robot utilise une communication Bluetooth. Il faut donc rester à moins de 10m de celui-ci pour pouvoir communiquer avec lui.**\
\
Pour des questions concernant ce projet, prière de contacter [Katherine Zamudio-Turcotte](https://www.linkedin.com/in/katherine-zt/), ancienne directrice de PolyOrbite et personne en charge de ce projet.

## Pour démarrer le robot
1. S'assurer que le robot est équippé du Adafruit BLE shield, d'un buzzer piézoélectrique (qui est branché à GND et à la pin digitale 2) et d'un capteur à ultrasons qui n'est pas branché
2. S'assurer que 4 batteries AA rechargées se trouvent dans son compartiment à batteries
3. S'assurer d'avoir téléchargé l'application Bluefruit Connect sur votre téléphone intelligent Apple ou Android
4. S'assurer d'avoir téléchargé Arduino IDE sur votre PC
5. Sur votre PC, naviguez vers https://github.com/KatherineZT/RSE-Probe-Bot/tree/ble-control, cliquez sur le bouton vert "Code" et choisissez "Download ZIP"
6. Une fois l'archive ZIP extraite, double-cliquez sur le fichier `.ino` qui se trouvera dans `RSE-Probe-Bot\tibot_ble`
7. Une fois Arduino IDE ouvert, branchez le robot à votre PC avec un câble micro-USB
8. Sur Arduino IDE, allez dans "Tools" et vérifiez que le "Board" sélectionné est un Arduino Uno et qu'il y a un "Port" sélectionné
9. Faites Ctrl-U pour téléverser le code dans le robot
10. Sur votre téléphone, ouvrez l'application Bluefruit Connect et choisissez le dispositif "Adafruit Bluefruit LE"
11. Le robot fera une petite mélodie lorsque la connexion sera établie
12. Sur votre téléphone, choisissez "Controller" et ensuite "Control Pad" et avec les flèches, mettez-vous à conduire le robot!

Les flèches ↑↓ permettent de se déplacer vers l'avant ou vers l'arrière\
Les flèches ←→ permettent de pivoter de côté