# RSE : Robot Arduino pour ateliers pédagogiques

Fabriqué dans le cadre d'un projet de fin de programme en Science Informatiques et Mathématiques au Collège de Bois-de-Boulogne\
Cours 420-204-RE, Hiver 2018\
\
Le robot RSE est facilement pilotable à l'aide d'une application qui fonctionne sur iOS et sur Android.\
**Attention: Le robot utilise une communication Bluetooth. Il faut donc rester à moins de 10m de celui-ci pour pouvoir communiquer avec lui.**\
\
Pour des questions concernant ce projet, prière de contacter [Katherine Zamudio-Turcotte](https://www.linkedin.com/in/katherine-zt/), ancienne directrice de PolyOrbite et personne en charge de ce projet.

## Pour démarrer le robot
1. S'assurer que la betterie LiPo est rechargée.
2. S'assurer que 4 batteries AA rechargées se trouvent dans le compartiment à batteries sous le robot
3. S'assurer d'avoir téléchargé l'application Bluefruit Connect sur votre téléphone intelligent Apple ou Android
4. Allumez l'interrupteur à l'arrière du robot (sur le Arduino Uno) et branchez la batterie LiPo au port noir du Arduino Mega
5. Sur votre téléphone, ouvrez l'application Bluefruit Connect et choisissez le dispositif "Adafruit Bluefruit LE"
6. Le robot fera une petite mélodie lorsque la connexion sera établie
7. Sur votre téléphone, choisissez "Controller" et ensuite "Control Pad" et avec les flèches, mettez-vous à conduire le robot!

**Les flèches ↑↓ permettent de se déplacer vers l'avant ou vers l'arrière\
Les flèches ←→ permettent de pivoter de côté**

## Pour reprogrammer le robot
**IMPORTANT: DÉBRANCHEZ LA BATTERIE LIPO ET ÉTEIGNEZ L'ARDUINO UNO AVEC L'INTERRUPTEUR À L'ARRIÈRE!**
1. S'assurer d'avoir téléchargé Arduino IDE sur votre PC
2. Sur votre PC, naviguez vers https://github.com/KatherineZT/RSE-Probe-Bot/tree/ble-control, cliquez sur le bouton vert "Code" et choisissez "Download ZIP"
3. Une fois l'archive ZIP extraite, double-cliquez sur le fichier `.ino` qui se trouvera dans `RSE-Probe-Bot\tibot_drive_serial`
4. Une fois Arduino IDE ouvert, branchez le Arduino Uno à votre PC avec un câble micro-USB\
**IMPORTANT: DÉBRANCHEZ LES FILS SUR LES PINS 0 ET 1 DU ARDUINO, SINON, LE CODE NE TÉLÉVERSERA PAS À L'ÉTAPE 6!**
5. Sur Arduino IDE, allez dans "Tools" et vérifiez que le "Board" sélectionné est un Arduino Uno et qu'il y a un "Port" sélectionné
6. Faites Ctrl-U pour téléverser le code dans le robot et rebranchez les fils (le jaune sur 0 et le vert sur 1)
7. Dans l'archive extraite, double-cliquez maintenant sur le fichier `.ino` qui se trouvera dans `RSE-Probe-Bot\tibot_ble`
8. Sur Arduino IDE, allez dans "Tools" et vérifiez que le "Board" sélectionné est un Arduino Mega, que le processeur sélectionné est bien "ATmega2560" et qu'il y a un "Port" sélectionné
9. Branchez le Arduino Mega à votre PC avec un câble USB-B
10. Faites Ctrl-U pour téléverser le code dans le Arduino Mega
11. Débranchez tous les câbles USB et rebranchez la batterie LiPo au Arduino Mega, puis rallumez l'interrupteur du Arduino Uno