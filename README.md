photoplatform-sdf
[![Coverage Status](https://img.shields.io/coveralls/VincSch/photoplatform-sdf.svg)](https://coveralls.io/r/VincSch/photoplatform-sdf?branch=master)
=================
Beispielprojekte Rezeptverwaltung - RESTful Webservice mit Spring MVC, Spring Boot, Hibernate, und Angular JS Frontend.
Wir könnten das Projekt von mir als Grundlage für die Photoplattform benutzen. 
 
 Compile & Run
=======
Required: Maven 3+

Build:
mvn clean install

Run:
mvn spring-boot:run

Run in IDE:
Im Packet de.htw.sdf.photoplatform befindet sich ein Klasse mit dem Namen Application.java. Diese dient als Entry-Point und kann einfach als normale
Java-Anwendung gestartet werden (enthält main Methode). Spring Boot startet dann einen Tomcat-Server und deployed die Anwendung. Zusätzlich wird eine H2-Datenbank gestartet.

Erreichbar ist die Anwendung lokal unter folgenden Link: http://localhost:8080/

Daten werden in einer h2 in-memory Datenbank gespeichert. Auf diese kann während der laufenden Applikation mit Hilfe der H2-Console zugegriffen werden.

java -jar h2-database-console/h2-1.4.178.jar

Connection Informationen:

Generic H2 Embedded

Driver: org.h2.Driver

URL: jdbc:h2:tcp://localhost:8043/mem:photoplatformdb

Nutzer: sa

Passwort:

Das Fronend befindet sich im Ordner src/webapp
