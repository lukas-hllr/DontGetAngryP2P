# DontGetAngryP2p

## Start der Anwendung
- Lade zunächst mit `mvn clean install` alle dependencys des Projekts herunter.
- Starte anschließend mit einer IDE deiner Wahl die Main Klasse.
- Neues Spiel Starten und dabei einen von der Firewall geöffneten Port angeben (Bspw. Port 80)
- Auf zweitem Client die IP des SessionLeaders & Port angeben.

## Funktionen der Anwendung
- Spielen mit 2-4 Spielern
- P2P Anwendung welche ein Netzwerk mit einer vollvermaschten Topologie aufbaut
- Es gibt keinen dedizierten Server

## Einschänkungen
- Zum Testen der Anwendung sind 2 Computer nötig, da die Anwendung jeweils immer den entsprechenden Port blockiert, der nötig wäre um sie ein zweites mal zu starten.
- Die Anwendung sollte mit einer minimalen Bidschirmauflösung von 1080x1920 betrieben werden.
- Das Würfeln findet nicht dezentral statt. Schummeln wäre durch Code-Manipulation möglich.


