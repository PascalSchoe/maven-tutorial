# maven-tutorial
## Generelles
- starke Unterscheidung zw. Maven2 & Maven3
-
## Project Object Model (POM)
Enthält alle wichtigen Informationen zu dem jeweiligen Projekt so zum Beispiel seine [Koordinaten](#koordinaten) oder welche Abhängingkeiten es besitzt. Jedoch hat das *POM* nicht nur beschreibenden Charakter sondern ermöglicht auch das Verwenden von Plugins die an bestimmte *Phasen* innerhalb des Lebenszykluses gebunden sind.

Das POM sollte folgenden
- Dateienname muss, sofern nicht anders definiert im *settings.xml*, "pom.xml"
- Syntax dieses Files *XML*
- `modelVersion` -> derzeitig nur 4.0.0 unterstützt
### Koordinaten
#### `<groupId></groupId>`
Meist in Form einer 'umgekehrten URL' + Namensraum.
Beispiel: **org.example.kitchenware**

#### `<artifactId></artifactId>`
Name der Anwendung.
Beispiel: **blender**

#### `<version></version>`
Versionierung wird in der Form: *major.minor.revision* bevorzugt, es wird jedoch keine feste Form  verlangt.
Beispiel: **1.0-SNAPSHOT**

#### `<properties></properties>`
[TODO]-> ausfüllen....

#### `<packaging></packaging>`
Gehört nur indirekt zu den Koordinaten. Wenn nicht anders angegeben wird `jar` verwendet.

Folgende Werte sind verfügbar:

| Wert | Wirkung |
| ---- | ------- |
|*pom* |Erzeugt kein Archiv. Wird verwendet um ein eigenes [Super POM](#super-pom-offiziell) oder Module zu erzeugen.|
|*jar* |Defaul-Wert, erzeugt ein Java-Archiv.|
|*war* |Es wird ein Web-Application-Archive erzeugt.|
|*maven-archetype* |Wird für Projekt-Templates ([Maven Archetypes](#archetypes)) verwendet.|
|*maven-plugin* |Packaging-Type um eigene Maven-Plugins zuschreiben.|
|*ear* |Erzeugt ein Java-Enterprise-Archive.|
|*ejb* |Deploy-fähiges Java-Archive.|
|*rar* |Resource Adapter Archive. Liefert INformationen, mit denen eine Java-Applikation sich mit einem Enterprise-Information-System verbinden kann.|

Es gibt weitere Werte für `<packaging></packaging>`, die jedoch recht selten Verwendung finden.

#### `<distributionManagement></distributionManagement>`
Für das Deployment von Artefakten (Projekten), hier wird bestimmt *wohin* und *wie* das Projekt deployt wird. So gibt es verschiedene Elemente:

- `repository`: Deployment von **Releases**   
- `snapshotRepository`: Deployment von **SNAPSHOT-Versionen** ([siehe Versionen](#versionversion))
- `site`: Definiert die Addresse für das Deployment der von Maven genierten **Dokumentation**.

 Jedes der beschriebenen Elemente des *distributionManagement* benötigt folgende Attribute:

 - **id**: Identifiziert das Repository eindeutig unter mehreren.
 - **name**: Für Menschen lesbare Form der *id*.
 - **url** : Definiert die Adresse und das zu verwendene Protokoll um das Artefatkt zu transferieren.

### Super POM ([offiziell](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html))
Vergleichbar mit `Object` in Java erben alle *POM* vom *Super POM*, wenn nicht anders deklariert, somit ergeben sich gewisse Default-Werte. Es stellt sicher dass auch bei einem [Minimalem POM](#minimales-pom) die Funktionsweise *Mavens* gewährleistet wird.

### Minimales Pom
Das Minimum, das Maven benötigt, sind die [Koordinaten](#koordinaten) des Projekts, die es möglich machen euer Projekt eindeutig zu referenzieren.

```
<project>
  <modelversion>4.0.0</modelVersion>
  <groupId>org.company.kitchenware</groupId>
  <artifactId>blender</artifactId>
  <version>1.0.0</version>
</project>
```



# Notizen für mich
- eventuell vergleich zu *Ant* herstellen?
- was gibt es an Konkurrenz/Synergien?
- Classifier?
- Variablen in Maven?
- Properties
- Attribut durch ELement ersetzen?
- reporting (Pom)
- Lifecycle vor POM?
- CI-Element ?
- 4.4 Buch
- alle phasen link
- Installation

## Aufgaben
- Profile für development & production(S.153 !)

## Lebenszyklen
Ein Grundprinzip Mavens sind die Lebenszyklen in Verbindung mit [Plugins](#plugins).
Beim dem Übersetzen von Quellcode und dem Erzeugen von Anwendungen lassen sich wiederkehrende Aufgaben erkennen.
Maven verallgemeinert und abstrahiert diese in mehrere Schritte (*Phasen*), die logische Zusammenfassung eines mehrerer Phasen nennt Maven *Lebenszyklus*.
Maven kennt drei Standard-Lebenszyklen:

- Default/Build
- Clean
- Site

Für jede Teilaufgabe gibt es genau **ein** Maven-Plugin, welches die Abarbeitung vornimmt. Wenn ein Plugin mehrere Varianten einer Funktionalität bereitstellt muss zusätzlich das *Goal* angegeben werden, zb. `mvn compiler:compile` vs `mvn compiler:testCompile`.

> Kurz gesagt: **_Lebenszyklen_ bestehen aus _Phasen_, diese werden von _Plugins_, die wiederum verschiedene _Goals_ haben, realisiert.**

Nachfolgend werden die einzelnen Lebenszyklen im Detail beschrieben.

### Build Lebenszyklus
Beinhaltet alle *Phasen* die zum übersetzen von Quellcode und Erzeugen der Anwendung benötigt werden.
Nachfolgend eine Lister der Grundlegenden Phasen des Default-Lifecycle:

| Phase | Funktion |
| --- | --- |
| `validate` | Überprüft die Gültigkeit der Projektkonfiguration und das [POM](#project-object-model-(pom)). |
| `compile` | Übersetzt den Quellcode des Projektes in das Zielverzeichniss. |
| `test` | Führt die verfügbaren Unit-Test unter Verwendung des passenden Frameworks aus, zum Beispiel _JUnit_. |
| `package` |  Erzeugt ein Java-Archiv, je nach gewähltem [packaging-typ](#packagingpackaging). |
| `verify` |  Überprüft das erzeugte Archiv und stellt fest, ob es im Maven-Repository abgelegt werden darf. |
| `install` |  Legt das erzeugte Archiv im lokalen Maven-Repository ab. |
| `deploy` |  Legt das erzeugte Archiv im remote Maven-Repository, welches dies ist wird mit dem [distributionManagement-Attribut](#distributionmanagementdistributionmanagement) festgelegt). |

Dabei werden alle *Phasen* nach einer festen Reihenfolge abgearbeitet, wird eine spezifische Phase ausgeführt, wie zb `install`, so werden alle in der Reihenfolge, des Lebenszykluses, liegenden Phasen ebenfalls ausgeführt.

Beispiel  
### Clean Lebenszyklus
Beinhaltet alle Phansen die beim 'aufräumen' eines Projektes notwendig sind. Die von Maven erzeugten Dateien und Ordner unter dem Verzeichnis `target` werden entfernt.

| Phase | Funktion |
| --- | --- |
| `pre-clean` | Vorbereitungen für die *clean*-Phase. |
| `clean` | Räumt das Projekt auf und entfernt die vom letzten Build erzeugten Dateien und Ordner. |
| `post-clean` | Abschlussphase des Clean-Lifecycle. |

### Site Lebenszyklus
In diesem Lebenszyklus wird die von Maven erzeugte Projektdokumentation erzeugt.

| Phase | Funktion |
| --- | --- |
| `pre-site` | Vorbereitungen für das Erzeugen der Projektdokumentation. |
| `site` | Erzeugt die Projektdokumentation als *HTML* durch die Ausführung aller unter `reporting` konfigurierten [Reporting-Plugins](#reporting). |


## Plugins

## Profile
Um, aufbauend auf der Plattformunabhängigkeit Java's, Portierbarkeit und das Arbeiten in verschiedenen Umgebungen zu gewährleisten bietet sich die Verwendung von Profilen an. So ist es zum Beispiel möglich das Projekt nach *Development* und *Production* durch Profile zu unterscheiden und somit auch verschiedene Datenbanken oder Appserver zuverwenden.

**Namenskonvention**
Sollte ein Profil durch eine Variable und dem entsprechenden Wert aktiviert werden so ist folgender Name von Vorteil:
*variablenName-wertDerVariable*
So ist bei folgendem Aufruf: `mvn -Denv=test install` klar dass, sofern vorhanden, das Profil mit dem Namen: 'env-test' aktiv sein wird.

### Definition eines Profiles
Maven Profile können an verschieden Stellen definiert werden:

- Pro Projekt: [pom.xml](#project-object-model-(pom))
- [settings.xml](#settings)
  - Global: `%USER_HOME%/.m2/settings.xml`
  - Pro User: `${maven.home}/conf/settings.xml`
- in Maven2 zusätzlich in *profiles.xml*, in Maven3 nicht mehr!

Je nach Profilart lassen sich verschiedene Elemente manipulieren. Außerdem unterliegen sie verschiedenen Prioritäten bei Gleichnamigkeit. Diese Merkmale sollen in folgender Tabelle verdeutlicht werden.

| Profilart | Priorität | Elemente |
|--- | --- | --- |
| settings.xml -> Global | 1 | `repositories`<br/>`pluginRepositories`<br/>`properties`<br/> |
| settings.xml -> Pro User | 2 | `repositories`<br/>`pluginRepositories`<br/>`properties`<br/> |
| profiles.xml (nur Maven2!) | 3 | `repositories`<br/>`pluginRepositories`<br/>`properties`<br/> |
| pom.xml | 4 | `build`<br/>`dependencies`<br/>`dependencyManagement`<br/>`distributionManagement`<br/>`pluginRepositories`<br/>`modules`<br/>`plugins`<br/>`properties`<br/>`reporting`<br/>`repositories`<br/>`reporting` |

### Aktivierung eines Profiles
Die Aktivierung eines Profiles kann auf verschiende Wege geschehen, diese Möglichkeiten werden im Folgenden Sektionen beschrieben. Um festzustellen welche Profile derzeitig aktiv sind kann der Kommandozeilenaufruf: `mvn help:active-profiles` verwendet werden.

#### CLI
Durch Aufruf eines Profiles via Kommandozeile zb.: `mvn -P profile-1,profile-2`

#### Maven-Settings
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

Hier ist es möglich mehrere `activeProfile`-Elemente anzugeben.

#### Build-Umgebung
Profile können automatisch aktiviert werden je nach Zustand der Build-Umgebung, so werden bei Definition des Profiles gewisse *Trigger* in Form des `activation`-Elements übergeben. Um zu testen ob die Umgebung einer gewissen Kriterium genügt wird *prefix-matching* verwendet. So kann beispielsweise auf die *jdk*-Version getestet werden:

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

Das beschriebene Profil würde ebenfalls *triggern* bei den Versionen: 1.4.0_08, 1.4.13.37 oder der Gleichen, da wie schon beschrieben *prefix-matching* betrieben wird.
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
mehr Informationen zu Betriebssystemen bezüglich Maven findest du [hier](https://maven.apache.org/enforcer/enforcer-rules/requireOS.html).

Zu guter letzt gibt es noch die Möglichkeit die Aktivierung eines Profiles abhängig von den an Maven übergebenen Parametern zu machen, so würde das untere Profil bei dem Kommandozeilenaufruf:
`mvn org.company.kitchenware:blender:blend -Denv=production` aktiviert werden.
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
Es kann getestet werden ob eine Datei vorhanden ist oder nicht und entsprechend ein Profil aktiviert werden soll:

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
Auch möglich ist es ein Profil standardmäßig zu (de-)aktivieren:

```xml
<profiles>
  	<profile>
      <activation>
        <activeByDefault>true | false</activeByDefault>
      </activation>
    </profile>

    <!-- Profildetails -->
</profiles>
```

#### Deaktivieren
Profile können über die Kommandozeile mittels `mvn -P !profile-1, !profile-2` deaktiviert werden. Dies betrifft sowohl Profile die über *activeByDefault* oder ihrer Konfiguration andernfalls aktiv wären.

Alternativ zu dem Symbol '**!**' kann auch '**-**' als Prefix verwendet werden um eine Deaktivierung zu kennzeichnen.

### Variablen
Werden in der Form `${nameDerVariable}` abgefragt. *Properties* können werden entweder vom System selbst geliefert oder selbst definiert. Die Folgende Tabelle zeigt die verschiedenen Arten von Variablen die Maven kennt und in welcher Form diese abzurufen sind.

| Art | Herkunft | Form  | Beispiel |
| --- | --- | --- | --- |
| Umgebungsvariable | System | *env.X* | ${env.PATH} |
| Systemproperties | System | *system.property* | ${java.home} |
| Projekteigenschaften | Projektdefinition | *project.x* | ${project.groupId}/${project.version} |
| Settingswerte | [settings.xml](#maven-settings) | *settings.x* | ${settings.localRepository} |
| Property | [Properties-Element](#propertiesproperties) / Kommandozeilenparamter | *x*  | ${blender.rotationPerMinute} |

Eine Auflistung aller *Umgebungsvariablen* und *Systemproperties* die zur Verfügung stehen lassen sich mittels Kommandozeilenaufruf `mvn help:system` in Erfahrung bringen.

#### Filtering
Dateien, die sich in Ressourcen-Verzeichnissen befinden, können Variablen enthalten die anschließend während der process-resources-[phase](#lebenszyklen) durch den entsprechenden Wert ersetzt werden. Dieser Prozess wird *Filtering* genannt.
Durchgeführt wird das Filtering durch das *Resource-Plugin*, default ist es jedoch deaktiviert.
Möchte man nun das Filtering nutzen so muss man dies im POM explizit angeben:

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

Hier ersichtlich ist das *Filterdateien* eine weitere Möglichkeit bieten *Properties* zu deklarieren. Besonders vorsichtig müsst ihr sein wenn sich  Binärdateien in euren Ressourcen-Verzeichnissen befinden, diese könnten zusammen mit dem *Filtering* zu unerwünschten Ergebnissen führen. Um diese Problem zu umgeben sind **excludes** notwendig. Ein komplexeres Beispiel hierfür:

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

**Merke**
> Ist **kein Include** angegeben werden automatisch **alle Dateien eingeschlossen**. Ist **kein exclude** angegeben wird **keine Datei** ausgeschlossen.

Alternativ wird empfohlen zwei Ressourcen-Verzeichnisse anzulegen:
- `src/main/resources-filtered`
- `src/main/resources`

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

## Reporting
Maven erzeugt mit dem [Clean-Lifecycle](#clean-lifecycle) eine Website für das Projekt.
Die Bestandteile sind: Projektinformationen, Projektreports und Projektdokumentation.


### Projektinformationen
Direkt aus dem POM generiert. [Hier](https://maven.apache.org/plugins/maven-site-plugin/project-info.html) findet ihr eine Liste aller Elemente die dabei in Betracht gezogen werden.

### Projektreports
Werden durch Plugins aus dem Sourcecode oder anderen Projektbestandteilen generiert.
Maven erzeugt nur *Reports* wenn im POM in der Sektion `reporting` das entsprechende Plugin aufgeführt ist.
Nachfolgend wird beispielhaft die Verwendung des Javadoc Plugins beschrieben, die anderen [anderen Reports](https://maven.apache.org/plugins/maven-site-plugin/project-reports.html) die Maven erzeugt werden nahezu analog verwendet.

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

    <!-- eventuell weitere Reporting-Plugins die ihr nutzen wollt -->
  </plugins>
</reporting>
```

Anschließend mit dem Befehl `mvn site` die Website generiert. Unter `target/site/index.html` findet ihr die *Javadoc*.

Ursprünglich war mit *Maven3* beabsichtig alle *Reporting-Plugins* innerhalb der Konfigurations-Sektion des *maven-site-plugins*, anstatt im `reporting`-Element, anzugeben dies wurde jedoch vorerst rückgängig gemacht, ich weiß nicht ob sich dies zukünfigt ändern wird, [hier](https://maven.apache.org/plugins/maven-site-plugin/maven-3.html#New_Configuration_.28Maven_3_only.2C_no_reports_configuration_inheritance.29) nachzulesen.


#### Eigene Reports
[Eigene Reports](http://maven.apache.org/shared/maven-reporting-impl/index.html) werden so geschrieben.

### Projektdokumentation
Wird manuell erstellt.