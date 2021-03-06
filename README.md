# maven-tutorial

## Installation

### Maven
Installiere bitte [Maven](https://maven.apache.org/download.cgi).<br/>
Anschließend folge [diesen Anweisungen](https://maven.apache.org/install.html).

### Wildfly
Der Anwendungsserver der in diesem Tutorial genutzt wird ist [hier](http://wildfly.org/downloads/) zu finden.
Nach erfolgreichem Download entpacke das Verzeichnis bitte an die gewünschte Stelle auf deinem Computer. Anschließend müssen wir die Konfiguration des Servers etwas anpassen, dies ist nur nötig wenn bei dir auch der *Port 8080* belegt ist, im meinem Fall ist es so. Um den *Default-Port* zu ändern führe bitte folgende Schritte aus:

1. Navigiere in das Verzeichnis `${wildfly.home}\standalone\configuration`
2. Öffne die Datei `standalone.xml`
3. Suche nach `jboss.http.port`, dieser ist derzeitig noch auf *8080* eingestellt
4. Ändere den Wert auf *4242*
5. Speichern & schließen.

Lass uns nun den Server starten:
Navigiere zu `${wildfly.home}\bin` durch Doppelklick auf die `standalone.bat` starten wir den Server. Anschließend solltest du im Browser unter der Adresse `localhost:4242` folgendes sehen:

![startscrenn wildfly](resources/wildfly.PNG)


### Eclipse einrichten
Bitte installiere folgende Plugins über den *Marketplace* von Eclipse:

- EGit - Git Integration for Eclipse
- JBoss Tools
- Maven Integration for Eclipse

### Maven-Tutorial Projekt
Bitte lade dir, falls noch nicht geschehen [git](https://git-scm.com/downloads) herunter.<br/>
Anschließend führe in der Kommmandozeile (mit Adminrechten) folgende Befehle aus:

```console
$ cd ${wunschVerzeichnis}
$ git clone https://github.com/PascalSchoe/maven-tutorial.git
```

Nun wird das *Repository* in dein Wunschverzeichnis geladen. Nachdem *Git* seine Arbeit verrichtet hat öffne *Eclipse*.

Hier sind nun folgende Schritte auszuführen:

1. `File > Import > Existing Maven Projects > Browse...`
2. Suche das *Maven-Tutorial*.
3. drücke `select all`

Nun sollte Eclipse das [Multi-Modul-Projekt](http://www.codetab.org/apache-maven-tutorial/maven-multi-module-project/) laden, `maven-tutorial` ist dabei unser Orientierungspunkt (Parent Pom).
Du kannst testen ob alles geklappt hat indem du einen Rechtsklick auf `maven-tutorial` tätigst und im Menü `Show in Local Terminal > Terminal` auswählst. Nun gib in die Konsole die sich öffnet folgendes ein: `mvn install` sollte das Build erfolgreich sein hat alles geklappt.

**Beachte:**
> Das *Submodule* `Plugins` ist nur funktionsfähig nachdem du den [Server](#wildfly) gestartet hast.

## Lebenszyklen
Ein Grundprinzip *Mavens* sind die *Lebenszyklen* in Verbindung mit [Plugins](#plugins).
Beim dem Übersetzen von Quellcode und dem Erzeugen von Anwendungen lassen sich wiederkehrende Aufgaben erkennen.
*Maven* verallgemeinert und abstrahiert diese in mehrere Schritte (*Phasen*), die logische Zusammenfassung mehrerer *Phasen* nennt man *Lebenszyklus*.
Maven kennt drei *Standard-Lebenszyklen*:

- `Default/Build`
- `Clean`
- `Site`

Für jede Teilaufgabe gibt es genau **ein** *Maven-Plugin*, welches die Abarbeitung übernimmt. Wenn ein *Plugin* mehrere Varianten einer Funktionalität bereitstellt muss zusätzlich das *Goal* angegeben werden, zb. `mvn compiler:compile` vs `mvn compiler:testCompile` (sofern kein *Default-Goal* definiert ist).

**Kurz gesagt:**
> **Lebenszyklen** bestehen aus **Phasen**, diese werden von **Plugins**, die wiederum verschiedene **Goals** haben, realisiert.

Nachfolgend werden die einzelnen Lebenszyklen im Detail beschrieben.

### Build Lebenszyklus
Beinhaltet alle *Phasen* die zum übersetzen von Quellcode und Erzeugen der Anwendung benötigt werden.
Nachfolgend eine List der grundlegenden Phasen des *Default-Lifecycle*:

| Phase | Funktion |
| --- | --- |
| `validate` | Überprüft die Gültigkeit der Projektkonfiguration und das [Pom](#project-object-model-pom). |
| `compile` | Übersetzt den Quellcode des Projektes in das Zielverzeichnis. |
| `test` | Führt die verfügbaren [Unit-Tests](#testing) unter Verwendung des passenden *Frameworks* aus, zum Beispiel *JUnit*. |
| `package` |  Erzeugt ein *Java-Archiv*, je nach gewähltem [packaging-typ](#packaging). |
| `verify` |  Überprüft das erzeugte Archiv und stellt fest, ob es im [Maven-Repository](#repositories) abgelegt werden darf. |
| `install` |  Legt das erzeugte Archiv im lokalen [Maven-Repository](#repositories) ab. |
| `deploy` |  Legt das erzeugte Archiv im remote [Maven-Repository](#repositories), welches dies ist wird mit dem [distributionManagement-Element](#distributionmanagement) festgelegt). |

Dabei werden alle *Phasen* nach einer festen Reihenfolge abgearbeitet, wird eine spezifische *Phase* ausgeführt, wie zum Beispiel `install`, so werden alle in der Reihenfolge, des Lebenszykluses, davor liegenden *Phasen* ebenfalls ausgeführt.

### Clean Lebenszyklus
Beinhaltet alle *Phansen* die beim 'Aufräumen' eines Projektes notwendig sind. Die von *Maven* erzeugten Dateien und Ordner in dem Verzeichnis `target` werden dabei entfernt.

| Phase | Funktion |
| --- | --- |
| `pre-clean` | Vorbereitungen für die *Clean-Phase*. |
| `clean` | Räumt das Projekt auf und entfernt die vom letzten *Build* erzeugten Dateien und Ordner. |
| `post-clean` | Abschlussphase des *Clean-Lifecycle*. |

### Site Lebenszyklus
In diesem *Lebenszyklus* wird die Projektdokumentation von *Maven* erzeugt.

| Phase | Funktion |
| --- | --- |
| `pre-site` | Vorbereitungen für das Erzeugen der Projektdokumentation. |
| `site` | Erzeugt die Projektdokumentation als *HTML* durch die Ausführung aller unter `reporting` konfigurierten [Reporting-Plugins](#reporting). |
| `post-site` | Schließt die Erzeugung der Projektdokumentation ab. |
| `site-deploy` | Stellt die Dokumentation auf einem auf dem im [DistributionManagement](#distributionmanagement) festgelegt Server zu Verfügung. |

## Project Object Model (Pom)
Enthält alle wichtigen Informationen zu dem jeweiligen Projekt so zum Beispiel seine [Koordinaten](#koordinaten) oder welche [Abhägingkeiten](#dependencies) es besitzt. Jedoch hat das *Pom* nicht nur beschreibenden Charakter sondern ermöglicht auch das Verwenden von Plugins die an bestimmte *Phasen* innerhalb eines [Lebenszykluses](#lebenszyklen) gebunden sind.

Das *Pom* ist ein *xml-formatiertes* Dokument. Sein Name **muss** `pom.xml` sein.
Derzeitig wird für das *Element* `<modelVersion></modelVersion>` nur der Wert *4.0.0* unterstützt.

### Koordinaten
Die *Koordinaten* ermöglichen es *Maven* ein Projekt **eindeutig** zu Identifizieren. Sie sind daher unerlässlich.

#### GroupId
Meist in Form einer 'umgekehrten URL' + Namensraum.<br/>
Beispiel: `<groupId>org.kitchenstuff.tools</groupId>`

#### ArtifactId
Name der Anwendung.<br/>
Beispiel: `<artifactId>blender</artifactId>`

#### Version
Versionierung wird in der Form: `major.minor.revision` bevorzugt, es wird jedoch keine feste Form  verlangt.<br/>
Beispiel: `<version>1.0-SNAPSHOT</version>`

Folgendes Szenario wir deklarieren im *Pom* dass wir ein *Plugin* `XY-maven-plugin` nutzen wollen. Nun gibt es drei Arten die *Version* eben dieses *Plugins* anzugeben:

1. Keine *Version* angeben. In diesem Fall verwendet *Maven* die aktuellste *Version* des *Plugins* **aus dem lokalen Repository**, nur falls es keinen Eintrag im lokalem Repository gibt wird vom remote Repository geladen.
2. *Release Version*, zum Beispiel: `<version>1.0</version>`, Maven verwendet die *Release Version* 1.0 des *Plugins*. Es wird zu erst im lokalem Repository gesucht und anschließend, wenn nicht gefunden, wird im *remote Repository* gesucht.
3. *Snapshot Version*, zum Beispiel: `<version>1.0-SNAPSHOT</version>`, Maven sucht je nach konfiguriertem Intervall([`updatePolicy`](#repositories)) im remote Repository nach neueren Versionen. Der *'SNAPSHOT'-Anteil* der *Version* wird von *Maven* mit einem Zeitstempel ersetzt.

### Dependencies
Im Element `<dependencies></dependencies>` werden die Abhängigkeiten beschrieben die ein Projekt hat.<br/>
Ein Beispiel:

```xml
<dependencies>
  <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.5</version>
    <scope>test</scope>
  </dependency>

  <!-- mehr dependencies -->
</dependencies>
```
Mit dem Element `<dependencyManagement></dependencyManagement>` lassen sich im *Parent-Projekt* die *Dependencies* zentral konfigurieren. Das erbende *Pom* muss dann lediglich nur noch die *Koordinaten* des Plugins aufführen, es kann jedoch auch die Konfiguration des *Parent-Pom* überschreiben.<br/>Hierzu ein Beispiel:

**Parent-Pom**
```xml
<!-- anderes -->
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>servlet-api</artifactId>
      <version>2.4</version>
      <scope>provided</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
<!-- noch mehr anderes -->
```

**child-Pom**
```xml
<!-- anderes -->
<dependencies>
  <dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>servlet-api</artifactId>
  </dependency>
</dependencies>
<!-- noch mehr anderes -->
```

### Properties
Hier lassen sich *Variablen* für das Projekt anlegen.<br/>
Beispiel:

```xml
<properties>
  <project.build>42</dieZahl>
  <andereVariable>abcdef</andereVariable>
</properties>
```

Mehr zu *Properties* und wie sie verwendet werden [hier](#variablen).

### Packaging
Gehört nur indirekt zu den [Koordinaten](#koordinaten). Wenn nicht anders angegeben wird `jar` verwendet.<br/>
Folgende Werte sind verfügbar:

| Wert | Wirkung |
| ---- | ------- |
|*pom* |Erzeugt kein Archiv. Wird verwendet um ein eigenes [Super Pom](#super-pom-offiziell) oder ein [Multi Module Projekt](http://www.codetab.org/apache-maven-tutorial/maven-multi-module-project/) zu erzeugen.|
|*jar* |*Defaul-Wert*, erzeugt ein *Java-Archiv*.|
|*war* |Es wird ein *Web-Application-Archive* erzeugt.|
|*maven-archetype* |Wird für Projekt-Templates ([Maven Archetypes](#eigene-archetypes-schreiben)) verwendet.|
|*maven-plugin* |*Packaging-Type* um eigene [Maven-Plugins](#plugins-selber-schreiben) zuschreiben.|
|*ear* |Erzeugt ein *Java-Enterprise-Archive*.|
|*ejb* |*Deploy-fähiges Java-Archive*.|
|*rar* |*Resource Adapter Archive*. Liefert Informationen, mit denen eine *Java-Applikation* sich mit einem *Enterprise-Information-System* verbinden kann.|

Es gibt weitere Werte für das *packaging*, die jedoch recht selten Verwendung finden.

### DistributionManagement
Für das *Deployment* von *Artefakten* (Projekten), hier wird bestimmt **wohin** und **wie** das Projekt *deployt* wird. So gibt es verschiedene Elemente innerhalb des `distributionManagement`:

- `repository`: Deployment von *Releases*   
- `snapshotRepository`: Deployment von *SNAPSHOT-Versionen* ([siehe Versionen](#version))
- `site`: Definiert die Addresse für das *Deployment* der von *Maven* genierten [Dokumentation](#reporting).

 Jedes der beschriebenen Elemente des `distributionManagement` benötigt folgende Elemente:

 - `id`: Identifiziert das *Repository* eindeutig unter mehreren.
 - `name`: Für Menschen lesbare Form der *id*.
 - `url` : Definiert die Adresse und das zu verwendene Protokoll um das Artefatkt zu transferieren.

### Repositories
Legt fest **wo** Maven nach *Plugins* und *Dependencies* suchen soll. Hier eine Beispiel Konfiguration:

```xml
<repositories>
	<repository>
		<id>snapshots</id>
		<url>http://maven.kitchenware.com/snapshot</url>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
		</snapshots>
	</repository>

	<repository>
		<id>release</id>
		<url>http://maven.kitchenware.org/release</url>
	</repository>
</repositories>
```

Die `updatePolicy` 'always' bewirkt das wirklich bei **jedem** *Build* nach einer neueren Version im *remote Repository* gesucht wird. Default wird einmal pro Tag nach einer neueren *SNAPSHOT-Version* gesucht.

*Repositories* können im [pom.xml](#project-object-model-pom) (evtl. sogar *Super-Pom*) und/oder in der [settings.xml](#maven-settings) definiert werden.

### Super Pom ([offiziell](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html))
Vergleichbar mit `Object` in *Java* erben alle *Pom* vom *Super Pom*, wenn nicht anders deklariert, somit ergeben sich gewisse *Default-Werte*. Es stellt sicher dass auch bei einem *Minimalem Pom* die Funktionsweise *Mavens* gewährleistet wird.

### Minimales Pom
Das Minimum, das *Maven* benötigt, sind die [Koordinaten](#koordinaten) des Projekts, die es möglich machen dein Projekt eindeutig zu referenzieren.

```
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelversion>4.0.0</modelVersion>

  <groupId>org.kitchenstuff.tools</groupId>
  <artifactId>blender</artifactId>
  <version>1.0.0</version>
</project>
```
## Plugins
Da *Maven* nichts anderes ist als ein *Framework* das verschiedene *Plugins* bündelt und koordiniert. Kommt dieser Thematik eine große Bedeutung zu. Ein *Plugin* ist für **genau eine** Aufgabe zuständing zum Beispiel das Kompilieren von *Sourcecode* oder das Erzeugen von *Javadoc*, sobald es mehrere Ausführungen einer Aufgabe gibt, zum Beispiel das Kompilieren von Quellcode und Test-Quellcode, werden entsprechend mehrere *goals* von diesem Plugin bereit gestellt.

**Namenskonvention:**
> `${eigentlicherName}-maven-plugin`<br/>
Plugins mit folgendem Namen: `maven-${eigentlicherName}-plugin` stellen offizielle Plugins dar, daher sollte von solch einer Namensgebung bei der Erstellung eigener Plugins abgesehen werden.

### Wie werden Plugins ausgeführt?
Es gibt grundlegend drei Arten wie *Plugins* ausgeführt werden:

- Über die Kommandozeile
- Getriggert über die Pom-Konfiguaration
- Verknüpft über Annotationen im [Mojo](#plugins-selber-schreiben)

#### Kommandozeile
Generell werden *Plugins* in folgender Form aufgerufen:

```console
$ mvn ${groupId}:${artifactId}[${pluginVersion}]:${goal}
```

Wird jedoch die Nameskonvention wie beschrieben eingehalten kann das *Plugin* (*dosome-maven-plugin*) folgendermaßen aufgerufen werden:

```console
$ mvn dosome:now
```

Parameter werden in der Form `-Dparameter1=wert1 -Dparamter2=wertdrölf` übergeben.

#### Getriggert(Pom)
*Plugins* können entweder im Element `reporting` oder im Element `build` deklariert werden.

*Plugins* die in `reporting` deklariert sind werden **automatisch** aufgerufen sobald mittels `mvn site` die [Reports](#reporting) generiert werden. *Plugins* die innerhalb des `build` Elements deklariert wurden werden nur automatisch zusammen mit der entsprechend konfigurierten [Lifecycle-Phase](#lebenszyklen) ausgeführt. Ein Beispiel einer solchen Konfiguration:

```xml
<build>
	<plugins>
		<plugin>
			<groupId>org.wildfly.plugins</groupId>
			<artifactId>wildfly-maven-plugin</artifactId>
			<version>1.2.1.FINAL</version>
			<executions>
				<execution>
					<id>undeploy</id>

					<goals>
						<goal>undeploy</goal>
					</goals>

					<phase>clean</phase>
				</execution>
			</executions>
		</plugin>

		<!-- andere Plugins -->
	</plugins>
</build>
```  

Diese Konfiguration bewirkt dass das *Wildfly-Plugin* mit der *Clean-Phase* verknüpft wird und sobald diese ausgeführt wird wird `wildfly:undeploy` automatisch mit ausgeführt. Ein komplexeres Beispiel ist [hier](plugins/pom.xml) zu finden.

#### Mojo-Konfiguration
Mit Hilfe von *Annotationen* können *Plugins* und deren *Goals* innerhalb eines *Mojo* an Phasen gebunden werden. Mehr zu zum Thema Mojo [hier](#plugins-selber-schreiben).

### Konfiguration von Plugins im Pom
Mit dem `configuration`-Element des *Plugins* werden Parameter festgelegt.
Aufbauend auf unserem vorherigen Beispiel hier eine Konfiguration.

```xml
<plugin>
	<groupId>org.wildfly.plugins</groupId>
	<artifactId>wildfly-maven-plugin</artifactId>
	<version>1.2.1.FINAL</version>
	<executions>
		<execution>
			<id>undeploy</id>
			<phase>clean</phase>

			<goals>
				<goal>undeploy</goal>
			</goals>

      <!-- hier der neue Teil -->
			<configuration>
				<ignoreMissingDeployment>true</ignoreMissingDeployment>
			</configuration>
		</execution>
	</executions>
</plugin>
```

*Plugins* können pro `execution` oder auch für gesamt-gültig konfiguriert werden.

### pluginManagement
Hier sollten Plugins deklariert werden die nicht mit einer spezifischen *Phase* verknüpft sind.

### Plugins selber schreiben
Ein *Maven Plugin* ist ein *jar-Archiv*, das **ein oder mehrere Java-Klassen** enthält. Diese Klassen werden *Mojos* genannt, das steht für 'Maven plain old Java Object', in Anspielung an Pojos(Plain old Java Object). Jedes *Mojo* stellt ein *Goal* für das jeweilige *Plugin* dar.

[Hier](custom-plugins/) findest du ein Beispiel für ein selbst geschriebenes Plugin, dieses wird in das [tester-projekt](tester/pom.xml) eingebunden und kann mit

```console
$ cd tester
$ mvn friendly:say-something
```
aufgerufen werden.

## Profile
Um, aufbauend auf der Plattformunabhängigkeit Java's, Portierbarkeit und das Arbeiten in verschiedenen Umgebungen zu gewährleisten bietet sich die Verwendung von *Profilen* an. So ist es zum Beispiel möglich das Projekt nach *Development* und *Production* durch Profile zu unterscheiden und somit auch verschiedene *Datenbanken* oder *Appserver* zuverwenden.

**Namenskonvention:**
> Sollte ein Profil durch eine Variable und dem entsprechenden Wert aktiviert werden so ist folgender Name von Vorteil:
`${variablenName}-${wertDerVariable}`
So ist bei folgendem Aufruf: `mvn -Denv=test install` klar dass, sofern vorhanden, das Profil mit dem Namen: 'env-test' aktiv sein wird.

### Definition eines Profiles
Maven *Profile* können an verschieden Stellen definiert werden:

- Pro Projekt: [pom.xml](#project-object-model-pom)
- [settings.xml](#maven-settings)
  - Pro User: `${user.home}\.m2\settings.xml`
  - Global: `${maven.home}\conf\settings.xml`
- in Maven2 zusätzlich in `profiles.xml`, in Maven3 nicht mehr!

Je nach Profilart lassen sich verschiedene *Elemente* manipulieren. Außerdem unterliegen sie verschiedenen *Prioritäten* bei Gleichnamigkeit. Diese Merkmale sollen in folgender Tabelle verdeutlicht werden.

| Profilart | Priorität | Elemente |
|--- | --- | --- |
| `settings.xml` -> Global | 1 | `repositories`<br/>`pluginRepositories`<br/>`properties`<br/> |
| `settings.xml` -> Pro User | 2 | `repositories`<br/>`pluginRepositories`<br/>`properties`<br/> |
| `profiles.xml` (nur Maven2!) | 3 | `repositories`<br/>`pluginRepositories`<br/>`properties`<br/> |
| `pom.xml` | 4 | `build`<br/>`dependencies`<br/>`dependencyManagement`<br/>`distributionManagement`<br/>`pluginRepositories`<br/>`modules`<br/>`plugins`<br/>`properties`<br/>`reporting`<br/>`repositories`<br/>`reporting` |

### Aktivierung eines Profiles
Die Aktivierung eines *Profiles* kann auf verschiende Wege geschehen, diese Möglichkeiten werden im Folgenden Sektionen beschrieben. Um festzustellen welche *Profile* derzeitig aktiv sind kann der Kommandozeilenaufruf:

```console
$ mvn help:active-profiles
```

 verwendet werden.

#### Kommandozeile
Durch Aufruf eines *Profiles* via Kommandozeile zb.:

```console
$ mvn -P profile-1,profile-2`
```

#### Aktivierung durch Maven-Settings
Beispiel:

```xml
<settings>
  <!-- Some Stuff -->  
  <activeProfiles>
    <activeProfile>profile-1</activeProfile>
  </activeProfiles>
  <!-- Other Stuff -->  
</settings>
```

**Achtung:**
> Hier ist es möglich mehrere `activeProfile`-Elemente anzugeben.

#### Build-Umgebung
*Profile* können automatisch aktiviert werden je nach Zustand der *Build-Umgebung*, so werden bei Definition des *Profiles* gewisse *Trigger* in Form des `activation`-Elements übergeben. Um zu testen ob die *Umgebung* einer gewissen Kriterium genügt wird *prefix-matching* verwendet. So kann beispielsweise auf die *jdk*-Version getestet werden:

```xml
<profiles>
  	<profile>
      <activation>
        <jdk>1.4</jdk>
      </activation>
    </profile>

    <!-- Profildetails -->
</profiles>
```

Das beschriebene *Profil* würde ebenfalls *triggern* bei den Versionen: 1.4.0_08, 1.4.13.37 oder der Gleichen, da wie schon beschrieben *prefix-matching* betrieben wird.
Bei der Erkennung ist es außerdem  möglich *Ranges* von Versionen anzugeben zb.: `<jdk>[1.3,1.6)</jdk>`, hier werden [Version Ranges](https://maven.apache.org/enforcer/enforcer-rules/versionRanges.html) genutzt.

Ebenfalls ist es möglich auf das Betriebssystem zu testen:

```xml
<profiles>
  	<profile>
      <activation>
        <os>
          <name>Windows XP</name>
          <family>Windows</family>
          <arch>x86</arch>
          <version>5.1.2600</version>
        </os>
      </activation>
    </profile>

    <!-- Profildetails -->
</profiles>
```

mehr Informationen zu Betriebssystemen bezüglich *Maven* findest du [hier](https://maven.apache.org/enforcer/enforcer-rules/requireOS.html).

Zu guter letzt gibt es noch die Möglichkeit die Aktivierung eines *Profiles* abhängig von den an *Maven* übergebenen Parametern zu machen, so würde das untere *Profil* bei dem Kommandozeilenaufruf:

```console
$ mvn org.kitchenstuff.tools:blender:blend -Denv=production
```

aktiviert werden.

```xml
<profiles>
  	<profile>
      <activation>
        <property>
          <name>env</name>
          <value>production</value>

          <!-- Test ob Parameter **nicht** vorhanden ist
          <name>!env</name>
          -->

          <!-- Test ob Parameter vorhanden ist, Wert ist jedoch egal
          <name>env</name>
          -->
        </property>
      </activation>
    </profile>

    <!-- Profildetails -->
</profiles>
```

#### (Nicht-)Vorhandensein von Dateien
Es kann getestet werden ob eine Datei vorhanden ist oder nicht und entsprechend ein *Profil* aktiviert werden soll:

```xml
<profiles>
  	<profile>
      <activation>
        <file>
          <exists>src/main/resources/config</exists>
          <!-- das Gegenstück: <missing>src/main/resources/config</missing> -->
        </file>
      </activation>
    </profile>

    <!-- Profildetails -->
</profiles>
```

#### Default
Auch möglich ist es ein *Profil* standardmäßig zu (de-)aktivieren:

```xml
<profiles>
  	<profile>
      <activation>
        <activeByDefault>true|false</activeByDefault>
      </activation>
    </profile>

    <!-- Profildetails -->
</profiles>
```

#### Deaktivieren
*Profile* können über die Kommandozeile mittels `mvn -P !profile-1, !profile-2` deaktiviert werden. Dies betrifft sowohl Profile die über *activeByDefault* oder ihrer Konfiguration andernfalls aktiv wären.

Alternativ zu dem Symbol '**!**' kann auch '**-**' als Prefix verwendet werden um eine Deaktivierung zu kennzeichnen.

## Variablen
Werden in der Form `${nameDerVariable}` abgefragt. *Properties* können entweder vom System selbst geliefert oder selbst definiert werden. Die Folgende Tabelle zeigt die verschiedenen Arten von *Variablen* die *Maven* kennt und in welcher Form diese abzurufen sind.

| Art | Herkunft | Form  | Beispiel |
| --- | --- | --- | --- |
| Umgebungsvariable | System | *env.X* | ${env.PATH} |
| Systemproperties | System | *system.property* | ${java.home} |
| Projekteigenschaften | Projektdefinition | *project.x* | ${project.groupId}/${project.version} |
| Settingswerte | [settings.xml](#maven-settings) | *settings.x* | ${settings.localRepository} |
| Property | [Properties-Element](#properties) / Kommandozeilenparamter | *x*  | ${blender.rotationPerMinute} |

Eine Auflistung aller *Umgebungsvariablen* und *Systemproperties* die zur Verfügung stehen lassen sich mittels Kommandozeilenaufruf `mvn help:system` in Erfahrung bringen.

### Filtering
Dateien, die sich in *Ressourcen-Verzeichnissen* befinden, können *Variablen* enthalten die anschließend während der [process-resources-phase](#lebenszyklen) durch den entsprechenden Wert ersetzt werden. Dieser Prozess wird *Filtering* genannt.
Durchgeführt wird das *Filtering* durch das *Resource-Plugin*, default ist es jedoch deaktiviert.
Möchte man nun das *Filtering* nutzen so muss man dies im [pom](#project-object-model-pom) explizit angeben:

```xml
<build>
  <resources>
    <resource>
      <directory>src/main/resources/</directory>
      <filtering>true</filtering>
    </resource>
  </resources>
</build>
```

Desweiteren ist es möglich eigene *Filter* anzulegen. Folgendes Beispiel soll die Anwendung illustrieren:

**pom.xml**
```xml
<build>
  <resources>
    <resource>
      <directory>src/main/resources/</directory>
      <filtering>true</filtering>
    </resource>
  </resources>
  <filters>
    <filter>src/main/filters/filter.properties</filter>
  </filters>
</build>
```

**Filterdatei**
```
blender.producer=Kitchen Stuff and Co
blender.model=overNineThousand
blender.rotationPerMinute=9001
```


**blender.properties**
```
## Properties für ${name}
# Koordinaten : ${groupId}:${artifactId}:${version}
# Archivtyp   : ${packaging}
# Archivname  : ${project.build.finalName}.${packaging}
# JDK         : ${java.version}

# Aus der Filterdatei....
producer= ${blender.producer}
model=${blender.model}
rotationPerMinute=${blender.rotationPerMinute}
```

Hier ersichtlich ist das *Filterdateien* eine weitere Möglichkeit bieten *Properties* zu deklarieren. Besonders vorsichtig musst du sein wenn sich *Binärdateien* in deinem *Ressourcen-Verzeichnissen* befinden, diese könnten zusammen mit dem *Filtering* zu unerwünschten Ergebnissen führen. Um diese Problem zu umgeben sind *excludes* notwendig. Ein komplexeres Beispiel hierfür:

**pom.xml**
```xml
<build>
  <resources>

    <resource>
      <directory>src/main/resources/</directory>
      <filtering>true</filtering>
      <excludes>
        <exclude>*.png<exclude>
      </excludes>
    </resource>

    <resource>
      <directory>src/main/resources/</directory>
      <filtering>false</filtering>
      <excludes>
        <exclude>*.xml<exclude>
        <exclude>*.porperties</exclude>
      </excludes>
    </resource>

  </resources>

  <!-- other stuff .... -->

</build>
```

**Merke:**
> Ist **kein Include** angegeben werden automatisch **alle Dateien eingeschlossen**. Ist **kein exclude** angegeben wird **keine Datei** ausgeschlossen.

Alternativ wird empfohlen zwei *Ressourcen-Verzeichnisse* anzulegen:
- `src\main\resources-filtered`
- `src\main\resources`

anschließend muss nur noch das *filtering* auf erstererem aktiviert werden:

**pom.xml**
```xml
<build>
    <resource>
      <directory>src/main/resources-filtered</directory>
      <filtering>true</filtering>
    </resource>
</build>
```

## Testing
Mit *Maven* können natürlich unter Verwendung von *Plugins* **automatisiert** Tests ausgeführt werden.
Es werden zwei Arten von Tests hier thematisiert: *Unittests* und *Integrationstests* erstere testen atomare Bestandteile der Software und letzteres testet die Funktion der Komponenten in Kombination.

**Achtung:**
> Maven findet Tests basierend auf ihrem Namen, das bedeutet die Namesgebung ist entscheidend.

Abgesehen davon stellt es ein *Best-Practices* dar seine Testklassen und Methoden semantisch zu benennen. Solltest du dich dennoch gegen eine solche Namesgebung entscheiden gibt es noch die Möglichkeit Klassen über die Konfiguration des jeweiligen *Plugins* dem *Test-Framework* bekannt zu machen. Mehr dazu später. Ich empfehle eine folgende Struktur der Verzeichnisse um eine gute Übersicht über das Projekt zu gewährleisten.

```
projekt                                       # Root
├── src
│   ├── main                                  # QuellcodeRoot
│   │   ├── resources                         # Ressourcen für euren Quellcode
│   │   │   └── ...
│   │   ├── java
│   │   │   ├── ClassA.java                    # Definition ClassA
│   │   │   ├── ClassB.java                    # Definition ClassB
│   │   │   └── ...
│   │   └── ...
│   └── test                                   # TestRoot
│       ├── resources                          # Ressourcen für eure Tests
│       │   └── ...
│       └── java
│           ├── it                             # Integrationstest kommen hier rein
│           │   ├── ClassABInteractionsIT.java # Integrationstests für ClassA und ClassB
│           │   └── ...
│           └── unit                           # Unittest kommen hier rein
│               ├── ClassATests.java           # Unittests für ClassA
│               ├── ClassBTests.java           # Unittests für ClassB
│               └── ...
├── pom.xml                                    # Definition eures Projktes
└── ...
```

Auf die Plugins wird nachfolgend noch genauer eingegangen.

### Surefire-Plugin
Surefire folgt gewissen [Regeln](https://maven.apache.org/surefire/maven-surefire-plugin/examples/inclusion-exclusion.html) um Tests im Quellcode zu finden. Es übernimmt die Abarbeitung der *Unittests*. Um *Unittests* auszuführen wird folgender Befehl verwendet:

```console
$ mvn test
```

### Failsafe-Plugin
*Integrationstests* werden durch vier Phasen des [Build-Lifecycles](#build-lebenszyklus) abgearbeitet:

- `pre-integration-test`
- `integration-test`
- `post-integration-test`
- `verify`

Theoretisch kann das *Surefire-Plugin* auch *Integrationstests* übernehmen, *Failsafe* jedoch, wie der Name schon verspricht, bricht bei einem Test-Fehler **nicht** in der `integration-test`-Phase ab sondern ermöglicht Maven noch die `post-integration-test`-Phase auszuführen und somit kann die Test Umgebung sicher abgebaut und Ressourcen wieder freigegeben werden.

*Failsafe* folgt gewissen [Regeln](https://maven.apache.org/surefire/maven-failsafe-plugin/examples/inclusion-exclusion.html) um Tests im Quellcode zu finden. Um *Integrationstests* auszuführen wird folgender Befehl verwendet:

```console
$ mvn verify
```

## Reporting
*Maven* erzeugt mit dem [Site-Lifecycle](#site-lebenszyklus) eine Website für das Projekt.
Die Bestandteile sind:

- Projektinformationen
- Projektreports
- Projektdokumentation

### Projektinformationen
Direkt aus dem [Pom](#project-object-model-pom) generiert.<br/> [Hier](https://maven.apache.org/plugins/maven-site-plugin/project-info.html) findest du eine Liste aller Elemente die dabei in Betracht gezogen werden.

### Projektreports
Werden durch *Plugins* aus dem *Sourcecode* oder anderen Projektbestandteilen generiert.
*Maven* erzeugt nur *Reports* wenn im *Pom* in der Sektion `reporting` das entsprechende *Plugin* aufgeführt ist.
Nachfolgend wird beispielhaft die Verwendung des *Javadoc Plugins* beschrieben, die [anderen Berichte](https://maven.apache.org/plugins/maven-site-plugin/project-reports.html) die Maven erzeugt werden nahezu analog verwendet.

**pom.xml**
```xml
<build>
    <plugins>
      <plugin>
        <groupId>org.apachae.maven.plugins</groupId>
        <artifactId>maven-site-plugin</artifactId>
        <version>3.7.1</version>
      </plugin>
    </plugins>
</build>

<reporting>
  <plugins>
    <plugin>
      <groupId>org.apache.maven-plugins</groupId>
      <artifactId>maven-javadoc-plugin</artifactId>
      <version>3.0.1</version>
    </plugin>

    <!-- eventuell weitere Reporting-Plugins die du nutzen willst -->
  </plugins>
</reporting>
```

Anschließend mit dem Befehl `mvn site` die Website generiert. Unter `target\site\index.html` findest du die *Javadoc*.

Ursprünglich war mit *Maven3* beabsichtig alle *Reporting-Plugins* innerhalb der Konfigurations-Sektion des *maven-site-plugins*, **anstatt** im `reporting`-Element, anzugeben dies wurde jedoch vorerst rückgängig gemacht, ich weiß nicht ob sich dies zukünfigt ändern wird, [hier](https://maven.apache.org/plugins/maven-site-plugin/maven-3.html#New_Configuration_.28Maven_3_only.2C_no_reports_configuration_inheritance.29) nachzulesen.

### Eigene Reports
Ähnlich zu Plugins werden eigene Reports [so](http://maven.apache.org/shared/maven-reporting-impl/index.html) geschrieben.

### Projektdokumentation
Die *Projektdokumentation* wird manuell erstllt und kann zum Beispiel Bedienungsanleitungen, *FAQs* oder *Wikis* sonstiges enthalten.

## Maven Settings
Einstellungen an *Maven* können auf drei Ebenen geschehen:

1. Projekt: pom.xml
2. User: settings.xml  
3. Global : settings.xml

In diesem Abschnitt wird auf Punkt 2 und 3 eingegangen. Maven unterscheidet zwischen User spezifischen Einstellungen und Globalen, zu finden unter:

```
User Einstellungen: ${user.home}\.m\settings.xml
Globale Einstellungen: ${maven.home}\conf\settings.xml
```

Sind beide Dateien vorhanden werden diese zur Laufzeit kombiniert, sollten beide die gleichen Elemente konfigurieren besitzen die *User-Settings* eine **höhere Priorität** und setzen sich damit automatisch durch.

In beiden Dateien können die [gleichen Einstellungen](https://maven.apache.org/ref/current/maven-settings/settings.html) vorgenommen werden. Jedoch bietet es sich an die *Global-Settings* als *firmeneinheitliche Einstellung* zu verwenden.

Nachfolgend werde ich auf einige *Elemente* gesondert eingehen.

### Repositories vs pluginRepositories
In Maven1 wurden *Plugins* und normale *Artefakte* noch in getrennten *Repositories* hinterlegt, dies wurde in *Maven2* geändert. Eine wichtige Unterscheidung zwischen *Plugins*   und *Artefakten* ist dass *Plugins* zur Laufzeit verwendet werden wie zum Beispiel das *Compiler-Plugin* zum Übersetzen von Java-Quellcode, *Artefakte* werden aber wie Bibliotheken verwendet.

Desweiteren bietet die Nutzung beider dieser *Elemente* die Möglichkeit verschiedene Konfigurationen vorzunehmen, zum Beispiel könnte es ja von Nutzen sein die `UpdatePolicy` anzupassen oder keine `SNAPSHOT`-Plugins zuverwenden.

### Mirrors
Durch das `Repositories`-Element legst du fest von wo *Plugins/Artefakte* geladen werden sollen. Mit dem `mirror`-Element lässt sich anhand der *id* des *Repository* festlegen für welches *Repository* dieser *Mirror* 'greift'. *Maven* bemerkt wenn es versucht etwas aus einem *Repository* herunterzuladen dass ein *Mirror* für dieses *Repository* registriert wurde und verwendet stattdessen die angegeben Adresse. Nachfolgend ein Beispiel:

```xml
<settings>
	<!-- other settings-stuff -->

	<mirrors>
		<mirror>
			<id>UK</id>
			<name>UK Central</name>
			<url>http://uk.maven.org/maven2</url>
			<mirrorOf>central</mirrorOf> <!-- Id des Repository -->
		</mirror>
	</mirrors>

	<!-- somemore settings-stuff -->
</settings>
```

diese Konfiguration bewirkt das sollte *Maven* ein etwas aus dem *Central-Repository* laden wollen wird **anstelle** dem in den *USA gehostetem Repository* seine Entsprechung in UK verwendet.

**Achtung:**
> Es darf immer nur **ein** *Mirror* auf ein *Repository* verweisen!

Mehr Informationen kannst du [hier](https://maven.apache.org/guides/mini/guide-mirror-settings.html) finden, aus dieser Quelle wurde auch das Beispiel entnommen.

## Archetypes
*Archetypes* sind quasi *Projekt-Templates*, so ist es zb. möglich sich schnell von *Maven* die Struktur für ein *deploybares Webprojekt* erzeugen zu lassen wenn der entsprechende *Archetype* vorhanden ist. Somit müssen ähnliche Projekte nicht immer wieder komplett von vorn definiert werden.

### Eigene Archetypes schreiben
Um einen eigenen *Archetype* zu schreiben muss lediglich das Projekt so zusammen gestellt werden wie es später einmal auszusehen hat. Anschließend wird mit

```console
$ mvn archetype:create-from-project
```

der *Archetype* von *Maven* generiert, dieser ist nun unter
`${project}\target\generated-sources\archetype` zu finden. Mit den folgenden Befehlen wird der Archetype im *Katalog* für Maven hinterlegt, dies ist notwendig damit wir im Anschluß ein neues Projekt mit unserem Archetype generieren können.

```console
$ cd ${project}\target\generated-sources\archetype
$ mvn install				
```

**alternativ:**
> *deploy* anstelle von *install* wenn unser Archetype im *remote Katalog* hinterlegt werden soll  

Nun kann ein neues Projekt aus unserem Archetype erzeugt werden. Ein Beispiel findest du [hier](custom-archetypes/).
