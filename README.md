# Minecraft - Informejtycy mod
Projekt Informejtycy to darmowa platforma online, oferująca edukację informatyczną dla każdego, niezależnie od statusu materialnego, wieku czy doświadczenia. Umożliwia naukę programowania i algorytmiki poprzez zadania i unikalne ćwiczenia, takie jak debugowanie kodu.

## CHANGELOG (v 1.2.0)

### Nowości:
- Dodano nową strukturę - wyspę Zarzyka
- Dodano nowego moba - Zarzyka
- Dodano nowy przedmiot - żel do włosów, uzyskiwany poprzez przyjęcie ataku Zarzyka
- Dodano chełm prezydenta
- Dodano płytę muzyczną "Czoło stelli jest gargantualne" (credit: Bartek)

### Poprawki:
- Mleko zmysia jest teraz klasyfikowane jako napój
- Blok gdynianki wypada po wykopaniu
- Zmysio dropi itemy jako loot a nie jako equipment
- Zmniejszono przebicie zbroi przez miecz zmysia do 30%
- Zwiększono szansę otrzymania płyty Zalewix Beat w skrzynce w dungeonie do 25%
- Zmieniono id płyt muzycznych
- Zmieniono typ generacji fortecy zmysia na concentric_rings (taki jak strongholdy)

## Building from source

Requirements:
- Java 21
- Gradle

Build:
```shell
git clone https://github.com/DawkaWody/Informejtycy-MC.git
cd Informejtycy-MC
.\gradlew build
```
The built mod will be located in `build/libs/`

Run client:
```shell
.\gradlew runClient
```
Run server:
```shell
.\gradlew runServer
```