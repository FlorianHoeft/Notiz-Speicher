---
title: Anleitung für Projekttemplate Programmieren 3
---
# Template für Abgabe "Programmieren 3"

## Umfang und Anforderungen der Prüfung

### Überprüfte Lernziele

Die Prüfungsleistung im Modul 8522 "Programmiersprachen III" besteht aus der Umsetzung und der Dokumentation eines Beispielprojektes. Durch ihre Implementierung soll nachgewiesen werden:

- Befähigung zur Planung und zum Entwurf einer einfachen serverseitigen Anwendung
- Kenntnisse in Planung und Umsetzung eines Datenmodells, insbesondere Anwendung von SQL-Kenntnissen (Querbezug zum Modul Datenbanken)
- Kenntnisse in der Sicherstellung von Software-Qualität durch Anwendung geeigneter Testverfahren
- **Optional**: Umsetzung eines kleineren Software-Projektes als Team-Arbeit

Die Bearbeitung des Abschlussprojektes kann als **Einzelleistung** oder als **Zweier-Team** erfolgen. Bei Abgabe einer Gruppenleistung müssen die einzelnen Leistungen in der Ausarbeitung nicht getrennt dargestellt werden. In diesem Fall erhalten beide Team-Mitglieder die gleiche Note.

### Tool-Chain

Sie können ihr Projekt mit den Programmiersprachen **Java** oder **Go* realisieren. Der Schwerpunkt des Moduls liegt im Bereich der Backend-Entwicklung; dieses sollte sich auch in ihrer Abgabe widerspiegeln. Wenn Sie ein Frontend mit React oder Angular ergänzen möchten, dann wird das wohlwollend in der Bewertung berücksichtigt. Ein aufwendiges Frontend kann allerdings keine Defizite im Backend ausgleichen.

Darüber hinaus empfiehlt es sich, Techniken zur Sourcode-Verwaltung und zum strukturierten Logging einzusetzen:

- GIT als Versionskontrollsystem bzw. eine Web-Plattform wie [Github](https://www.github.com) oder [Codeberg](https://www.codeberg.org)
- Bei Gruppenarbeit: ein System zur Abstimmung innerhalb des Teams wie bspw. **Discord** oder **Mattermost**

### Abgabe

Die Abgabe ihres Projektes kann über ihre Repository-URL oder über den Abgabeordner im [Ilias](https://www.th-owl.de/ecampus) erfolgen. Es gilt:

- Das abgegebene Projekt muss **kompilier-** und **lauffähig** sein.
- Das Projekt kann über ein Buildsystem wie bspw. **Maven** oder **Gradle** gebaut und getestet werden.
- Falls Sie externe Abhängigkeiten haben, fügen Sie ihrem Projekt eine Installationsanweisung bei. Verwenden Sie kein externes **MySQL** oder gar **PHPMyAdmin**. Falls es unbedingt MySQL sein soll, können Sie H2 im [MySQL Compatibility mode](http://www.h2database.com/html/features.html#compatibility) betreiben.

### Projektthemen

Im Ilias finden Sie Projektvorschläge, die Sie als Team bis maximal 3 Entwicklerinnen und Entwickler umsetzen können.

Nach **Absprache** dürfen Sie gern eigene Vorschläge implementieren.

## Verwendung des Beispielprojektes

### Umfang

Im vorliegenden Template sind alle Techniken eingesetzt, die im Laufe des Semesters erprobt wurden. Damit entfällt der Konfigurationsaufwand und Sie können direkt mit der Implementierung starten. Eingebunden sind:

- Spring Boot Basissystem (Dependency Injection, `@autowired`)
- Spring Web (HTTP-Unterstützung `@Controller`, REST-API)
- Spring JPA (automatisierte Objektspeicherung in SQL-Datenbank `@Entity`)
- Lombok (Verzicht auf Getter/Setter/isEqual etc.)
- SLF4J (Strukturiertes Logging)
- Thymeleaf (Interarktives Web-Frontend mit HTML-Markup)

### Compile and test

Nach dem Auspacken des Projektes können Sie die Übersetzung mit `mvn install` anstoßen. Nach dem Durchführen aller Unit-Tests wird im Ordner *target* ein lauffähiges Java-Archiv (Typ: *war*) angelegt.

### Start der Web-Anwendung

Die gesamte Anwendung kann innerhalb von VS.Code mit "Project: Run" oder der Funktionstaste **F5** gestartet werden. In der Konsole können Sie es über `mvn spring-boot:run` ausführen.

Anschließend können Sie mit [http://localhost:8080/user](http://localhost:8080/user) auf die Webseite zugreifen. Diese URL verwendet die Controller-Klasse `gui.web.de.thowl.prog3.UserFormController`

### Zugriff auf den Datenbank-Server

Beim Starten eines Unit-Tests oder beim Ausführen der Web-Anwendung wird der Datenbank-Server mitgestartet. Sie können anschließend über das Web-Frontend auf den DB-Server zugreifen, die URL lautet:

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

Die benötigten Zugangsdaten (Username = sa, Password = "") finden Sie in der Datei `src/main/resources/application-context.xml`.

### Zugriff REST-API

Das Projekt stellt auch eine Mini-Implementierung einer REST-Schnittstelle zur Verfügung. Die URL lauten:

- [User service](http://localhost:8080/api/v1/users/): Base URL, bound to `api.web.de.thowl.prog3.UserController`
- [API docs](http://localhost:8080/api-docs): API documentation in JSON format. Used to autogenerate client software.
- [Swagger API](http://localhost:8080/swagger-ui/index.html): Interactive Swagger API documentation. Used to explore and to test the actual API endpoints.
