# Wildfly-Plugin
Um das Plugin wie [hier](pom.xml) definiert nutzen zu können muss eine Instanz des Wildfly-Applikations-Server lokal laufen.

Die Anwendung wird automatisch mit der [*package-phase*](../pom.xml#lebenszyklen) auf den Server deployt. Und während der [*clean-phase*](../pom.xml#lebenszyklen) *undeployed*. Das Plugin bietet natürlich sehr viel [mehr](https://docs.jboss.org/wildfly/plugins/maven/latest/) Interaktionsmöglichkeiten.