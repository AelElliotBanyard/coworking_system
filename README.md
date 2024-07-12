# Dokumentation für die LB B - Modul 223

[Github Repository](https://github.com/AelElliotBanyard/coworking_system)

## Beschreibung

Eine Webapplikation für die Verwaltung eines Coworking Spaces. Die Plattform ermöglicht es Mitgliedern und Administratoren, sich anzumelden, ihre persönlichen Daten zu verwalten, Reservierungen zu erstellen und zu bearbeiten, sowie Informationen über den Coworking Space zu erhalten.

## Inhaltsverzeichnis

1. [Erweiterte Anforderungen](#Erweiterte-Anforderungen)
2. [Persona](#Persona)
3. [Anwendungsfalldiagramm](#Anwendungsfalldiagramm)
4. [Fachklassendiagramm](#Fachklassendiagramm)
5. [Schnittstellenplanung](#Schnittstellenplanung)
6. [Sequenzdiagramm](#Sequenzdiagramm)
7. [Testdaten](#Testdaten)

<div style="page-break-after: always;"></div>

## Aufsetzung des Projekts

Um das Spring Boot Projekt einzurichten, folgen Sie bitte den nachstehenden Schritten:

1. Öffnen Sie Ihre bevorzugte Entwicklungsumgebung (z.B. IntelliJ IDEA, Eclipse).
2. Klonen Sie das GitHub-Repository auf Ihren lokalen Computer:

```bash
git clone https://github.com/AelElliotBanyard/coworking_system.git
```

3. Navigieren Sie zum Projektverzeichnis:

```bash
cd coworking_system
```

4. Öffnen Sie das Projekt in Ihrer Entwicklungsumgebung.
5. Stellen Sie sicher, dass Sie eine Java Development Kit (JDK) Version 22 oder höher installiert haben.
6. Konfigurieren Sie Ihre Datenbankverbindung in der Datei `application.properties` im Verzeichnis `src/main/resources`. Geben Sie den Datenbank-URL, den Benutzernamen und das Passwort an.
7. Führen Sie die folgenden Befehle aus, um das Projekt zu kompilieren und die Abhängigkeiten herunterzuladen:

```bash
./mvnw clean install
```

8. Starten Sie die Anwendung mit dem folgenden Befehl:

```bash
./mvnw spring-boot:run
```

9. Die Anwendung sollte nun lokal auf Ihrem Computer gestartet werden. Sie können auf die API über den Endpunkt `http://localhost:8080` zugreifen.

Bitte beachten Sie, dass Sie möglicherweise weitere Konfigurationsschritte durchführen müssen, je nach Ihren spezifischen Anforderungen und Umgebungen. Stellen Sie sicher, dass Sie die Dokumentation des Projekts überprüfen, um weitere Informationen zu erhalten.

<div style="page-break-after: always;"></div>

## Starten des Projekts

Um das Projekt zu starten, führen Sie bitte die folgenden Schritte aus:

1. Öffnen Sie Ihre bevorzugte Entwicklungsumgebung (z.B. IntelliJ IDEA, Eclipse).
2. Navigieren Sie zum Projektverzeichnis:

3. Stellen Sie sicher, dass Sie eine Java Development Kit (JDK) Version 22 oder höher installiert haben.
4. Konfigurieren Sie Ihre Datenbankverbindung in der Datei `application.properties` im Verzeichnis `src/main/resources`. Geben Sie den Datenbank-URL, den Benutzernamen und das Passwort an.
5. Führen Sie die folgenden Befehle aus, um das Projekt zu kompilieren und die Abhängigkeiten herunterzuladen:

```bash
./mvnw clean install
```

6. Starten Sie die Anwendung mit dem folgenden Befehl:

```bash
./mvnw spring-boot:run
```

7. Die Anwendung sollte nun lokal auf Ihrem Computer gestartet werden. Sie können auf die API über den Endpunkt `http://localhost:8080` zugreifen.

Bitte beachten Sie, dass Sie möglicherweise weitere Konfigurationsschritte durchführen müssen, je nach Ihren spezifischen Anforderungen und Umgebungen. Stellen Sie sicher, dass Sie die Dokumentation des Projekts überprüfen, um weitere Informationen zu erhalten.

## Erweiterte Anforderungen

### A. Drei zusätzliche, einzigartige, funktionale Anforderungen sind als User Stories beschrieben

1. Als Mitglied oder Administrator, kann ich meine persönlichen Daten bearbeiten, um sie aktuell zu halten.
2. Als Mitglied oder Administrator, kann ich mein Konto löschen, um den Zugriff auf die Plattform zu beenden.
3. Als Administrator, kann ich Mitglieder sperren, um den Zugriff auf die Plattform zu verhindern.

### B. Drei zusätzliche, einzigartige, nicht-funktionale Anforderungen sind messbar beschrieben

1. Die Antwortzeit der Plattform soll unter 5 Sekunden liegen.
2. Die Plattform soll mindestens 99% der Zeit verfügbar sein.
3. Die Plattform soll dem Benutzer eine intuitive Benutzeroberfläche bieten.

<div style="page-break-after: always;"></div>

## Persona

### Persona 1: Administrator

**Name:** Michael Baumann  
**Alter:** 45 Jahre  
**Geschlecht:** Männlich  
**Berufliche Tätigkeit:** Betriebsleiter eines Coworking Spaces  
**Grund für die Nutzung des Coworking Spaces:** Michael nutzt das Coworking Space, um eine moderne Arbeitsumgebung zu schaffen, die flexibel auf die Bedürfnisse seiner Kunden eingeht. Er möchte eine produktive und inspirierende Atmosphäre bieten und gleichzeitig die Effizienz und Organisation des Raums verbessern.

<img src="./images/michael.jpeg" alt="Michael Baumann" width="200" height="200"/>

### Persona 2: Mitglied

**Name:** Friedrich Meier  
**Alter:** 43 Jahre  
**Geschlecht:** Mänlich  
**Berufliche Tätigkeit:** Freiberuflicher Grafikdesigner  
**Grund für die Nutzung des Coworking Spaces:** Friedrich nutzt den Coworking Space, um einen professionellen Arbeitsplatz zu haben, der er von den Ablenkungen zu Hause fernhält. Er schätzt die Möglichkeit des Networking und die inspirierende Umgebung, die ihre Kreativität fördert.

<img src="./images/friedrich.jpeg" alt="Friedrich Meier" width="200" height="200"/>

<div style="page-break-after: always;"></div>

### Persona 3: Besucher

**Name:** Tom Weber  
**Alter:** 28 Jahre  
**Geschlecht:** Männlich  
**Berufliche Tätigkeit:** Start-up-Gründer im Tech-Bereich  
**Grund für die Nutzung des Coworking Spaces:** Tom ist auf der Suche nach einem geeigneten Arbeitsplatz für sein Start-up-Team. Er möchte eine Umgebung schaffen, die die Zusammenarbeit und den Austausch von Ideen fördert und gleichzeitig flexibel und kosteneffizient ist.

<img src="./images/tom.jpeg" alt="Tom Weber" width="200" height="200"/>

<div style="page-break-after: always;"></div>

## Anwendungsfalldiagramm

![Anwendungsfalldiagramm](./images/use-case.png)

<div style="page-break-after: always;"></div>

## Fachklassendiagramm

![Fachklassendiagramm](./images/fachklassen.png)

<div style="page-break-after: always;"></div>

## Schnittstellenplanung

Schnittstellenplanung von Coworking System API

<table>
  <thead>
    <tr>
      <th>Methode</th>
      <th>Endpunkt</th>
      <th>Erfolgs- und Fehlerfälle</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>GET</td>
      <td>/rooms</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>200 Alle Räume</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>GET</td>
      <td>/rooms/{id}</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>200 Raum mit ID</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>404 Not Found</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>POST</td>
      <td>/rooms</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>201 Raum erstellt</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>409 Conflict</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>PUT</td>
      <td>/rooms/{id}</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>200 Raum aktualisiert</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>404 Not Found</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>DELETE</td>
      <td>/rooms/{id}</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>204 Raum gelöscht</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>404 Not Found</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>GET</td>
      <td>/bookings</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>200 Alle Buchungen</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>GET</td>
      <td>/bookings/{id}</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>200 Buchung mit ID</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>404 Not Found</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>POST</td>
      <td>/bookings</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>201 Buchung erstellt</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>409 Conflict</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>PUT</td>
      <td>/bookings/{id}</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>200 Buchung aktualisiert</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>404 Not Found</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>DELETE</td>
      <td>/bookings/{id}</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>204 Buchung gelöscht</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>404 Not Found</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>POST</td>
      <td>/users/login</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>200 Login erfolgreich</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>POST</td>
      <td>/users/register</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>201 Registrierung erfolgreich</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>409 Conflict</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>GET</td>
      <td>/users/verified</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>200 Verifizierung erfolgreich</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>GET</td>
      <td>/users</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>200 Alle Benutzer</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>GET</td>
      <td>/users/{id}</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>200 Benutzer mit ID</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>404 Not Found</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>PUT</td>
      <td>/users/{id}</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>200 Benutzer aktualisiert</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>404 Not Found</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>DELETE</td>
      <td>/users/{id}</td>
      <td>
        <p>Erfolge:</p>
        <ul>
          <li>204 Benutzer gelöscht</li>
        </ul>
        <p>Fehlerfall:</p>
        <ul>
          <li>400 Bad Request</li>
          <li>401 Unauthorized</li>
          <li>403 Forbidden</li>
          <li>404 Not Found</li>
          <li>500 Internal Server Error</li>
        </ul>
      </td>
    </tr>
  </tbody>
</table>

## Sequenzdiagramm

![Sequenzdiagramm](./images/sequenz.png)

<div style="page-break-after: always;"></div>

## Testdaten

```java
	String salt = BCrypt.gensalt();
	CoworkingUser u1 = new CoworkingUser("ael@banyard.ch", BCrypt.hashpw("123", salt), "ael", "banyard", false, Roles.ADMIN, salt);
	CoworkingUser u2 = new CoworkingUser("en.lueber@gmail.com", BCrypt.hashpw("123", salt), "evan", "lueber", false, Roles.MEMBERsalt);


	Room r1 = new Room("Room 1", "First Floor Room 1", "101", 1  , RoomType.CONFERENCE_ROOM, 20);
	Room r2 = new Room("Room 2", "First Floor Room 2", "102", 1  , RoomType.WORKSPACE, 3);
	Room r3 = new Room("Room 3", "First Floor Room 3", "103", 1  , RoomType.MEETING_ROOM, 10);
	Room r4 = new Room("Room 4", "Second Floor Room 1", "201", 2  , RoomType.WORKSPACE, 3);


	Booking b1 = new Booking(Date.valueOf("2024-10-11"), Day.HALF_DAY_MORNING, Status.REQUESTED, u1, r1, "ael1");
	Booking b2 = new Booking(Date.valueOf("2024-10-11"), Day.HALF_DAY_AFTERNOON, Status.REQUESTED, u2, r2, "evan1");
	Booking b3 = new Booking(Date.valueOf("2024-10-11"), Day.FUll_DAY, Status.REQUESTED, u1, r3, "ael2");
	Booking b4 = new Booking(Date.valueOf("2024-10-11"), Day.FUll_DAY, Status.REQUESTED, u2, r4, "evan2");
```

## Reflektion

Das Projekt war eine gute Gelegenheit, um die Konzepte von Spring Boot und Spring Security zu vertiefen. Es war auch eine gute Gelegenheit, um die Verwendung von Docker und Docker Compose für die Bereitstellung von Anwendungen zu üben. Die Implementierung der REST-API und die Verwendung von JWT-Token für die Authentifizierung und Autorisierung waren besonders lehrreich. Insgesamt war es eine lohnende Erfahrung, die mir half, meine Fähigkeiten in der Entwicklung von Webanwendungen zu verbessern.
