# DMOffice

A programot egy egyetemi projekt keretében készítem. Célom a Java nyelv és az azzal kapcsolatos technológiák megismerése, gyakorlása. Jelenlegi formájában valós környezetben még nem használható, de prototípusként bemutatható, ami által a felhasználók már a fejlesztés korai szakaszában képet kaphatnak a szoftverről, tesztelhetik azt, illetve élhetnek módosítási javaslatokkal. 

A program olyan kis- és középvállalkozások számára lehet hasznos, ahol a dolgozók számára nehézséget jelethet egy bonyolultabb vállalati információs rendszer használata. Ennél fogva a felhasználói felületet úgy lett kialakítva hogy átlátható és könnyen kezelhető legyen. Továbbá az egyes funkciók egymástól elkülőnítve, kereszthivátkozások nélkül érhetők el. 

A program külön felületet biztosít a felhasználók és az adminok számára. Ezenkívül a felhasználókra további hozzáférési korlátozások vonatkoznak, munkakörüknek megfelelően, mindenki kizárólag a számára releváns funkciókhoz tud hozzáférni.
  
  ### Funkciók:
  - Admin:
    - Userek kezelése +
    - Userek tevékenysége
    - Report készítése
  - User:
    - Termékek kezelése +
    - Rendelés feladás +
    - Rendelés átvétel
    - Készletnyilvántartás
    - Leltár
    - Értékesítés
    - Áru összekészítés
    - Kiszállítás
    - Partnerek kezelése
    
Jelenleg megvalósított funkciók + jellel vannak jelölve.

### Technikai részletek

A program tervezése során célom volt a réteges felépítés kialakítása a következő formában: Adatbázis réteg <-> Adatbázis kapcsolati réteg <-> Logikai réteg <-> Felhasználói réteg.

Technológia:
  - Java (JDK 8u191)
  - JavaFX
  - CSS
  - MySQL (CE 8.0.13)

JAR állományok:
  - mysql-connector-java-5.1.47.jar 
  - poi-4.0.1.jar 
  
## Futtatás

- A letöltött projektet nyissuk meg egy IDE-ben, majd adjuk hozzá a JAR fájlokat ( DMOffice/Other/ ).
- Hozzunk létre egy MySQL adatbázist az alábbi utasításokkal ( DMOffice/Other/sql.txt )
- Írjuk át a config file-ban az adatbázis elérési útvonalát az általunk létrehozottéra ( DMOffice/Other/config.txt )
- Futtassuk a programot
- Belépeés admin felületre, felhasználónév: admin jelszó: admin
- Belépés user ferületre, felhasználónév: user jelszó: user
- User felületen, a Products funkióban, az Import gombra kattintva tölthető fel termékekkel az adatbázis


