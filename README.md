# myExlorer
Mein Datei Explorer - ähnlich dem Mi Explorer Android


Erweitere das Android-Projekt `MyExplorer` zu einem Datei-Manager mit starkem Fokus auf:
- orange/dunkles Systemdesign
- WebDAV/Cloud-Speicher für die rund 70 bekannten Cloud dienste und eigene 
- einfache Geräteübertragung ohne Account-Zwang
- Datenschutz
- lokale Kontrolle

Projektname:
MyExplorer

Design-Vorgabe:
Das bestehende orange Design soll als Hauptidentität erhalten bleiben.

Erstelle ein eigenes Theme:
- Hauptfarbe: kräftiges Orange
- Dunkler Hintergrund optional als Standard
- Akzentfarben: Orange, Amber, Dunkelgrau, Schwarz
- Material 3
- Light Mode
- Dark Mode
- AMOLED Dark Mode
- Dynamic Color optional, aber orange Theme soll Vorrang haben
- App-Icon im Stil:
  - moderner Dateiordner
  - orange Verlauf
  - Explorer-/Kompass-/Cloud-Symbol
  - eigenständig, kein Xiaomi-, Windows- oder MIUI-Klon

Wichtig:
Das Design darf optisch zu einem orange angepassten Windows/Linux/Android-Setup passen, soll aber keine geschützten Designs oder Icons kopieren.

Neue Hauptfunktion:
Cloud & WebDAV Manager

Ziel:
MyExplorer soll viele kostenlose oder teilweise kostenlose Cloud-Dienste und WebDAV-Server verwalten können.

Unterstützte Verbindungstypen:
- WebDAV
- Nextcloud
- ownCloud
- Seafile, falls API sinnvoll verfügbar
- SMB
- FTP
- FTPS
- SFTP
- WebDAV-kompatible Anbieter
- lokale Netzwerkfreigaben
- optional später: S3-kompatible Speicher

Erstelle eine Provider-Datenbank:
`cloud_providers.json`

Diese soll ca. 70 Anbieter vorbereiten, aber modular und prüfbar bleiben.

Jeder Provider-Eintrag soll enthalten:
- providerId
- Anzeigename
- Kategorie
- Land/Region optional
- kostenlos nutzbar: ja/nein/unbekannt
- kostenloser Speicherplatz, falls bekannt
- WebDAV-Unterstützung: ja/nein/unbekannt
- Standard-WebDAV-URL, falls bekannt
- Login-Typ:
  - Benutzername/Passwort
  - App-Passwort
  - OAuth
  - Token
  - manuelle URL
- Hinweise
- Sicherheitswarnungen
- Status:
  - verified
  - community
  - experimental
  - unknown

Wichtig:
Codex soll keine unsicheren oder erfundenen Zugangsdaten einbauen.
Codex soll Provider-URLs nur eintragen, wenn sie öffentlich dokumentiert und verifizierbar sind.
Unklare Anbieter bleiben als `unknown` oder `manual`.
Der Nutzer muss eigene Zugangsdaten selbst eintragen.

Beispiele für Provider-Kategorien:
- WebDAV direkt
- Nextcloud-Anbieter
- ownCloud-Anbieter
- freie Cloudspeicher
- europäische Anbieter
- selbst gehostete Server
- NAS
- Fritz!NAS
- Uni-/Schulcloud
- S3-kompatible Speicher
- manuelle Server

Cloud-Verbindungsfunktionen:
- neuen Cloudspeicher hinzufügen
- Provider aus Liste auswählen
- manuellen WebDAV-Server hinzufügen
- Verbindung testen
- Zertifikat anzeigen
- optional unsichere Zertifikate blockieren
- Ordner browsen
- Dateien hochladen
- Dateien herunterladen
- Dateien kopieren
- Dateien verschieben
- Dateien löschen
- Ordner erstellen
- Favoriten speichern
- Offline-Cache optional
- Sync optional später

Sicherheitsanforderungen:
- Zugangsdaten niemals im Klartext speichern
- Android Keystore verwenden
- verschlüsselte lokale Datenbank
- Export nur verschlüsselt
- Master-Passwort oder Geräte-PIN/Biometrie für sensible Daten
- QR-/NFC-/Bluetooth-Transfer nur verschlüsselt
- Warnung bei unsicherem HTTP
- HTTPS bevorzugen
- keine Telemetrie
- keine Werbung
- keine Tracker

Neue Funktion:
Geräteübertragung ohne Account

Ziel:
Ein Nutzer soll MyExplorer auf mehreren Geräten einrichten können, ohne jeden Cloudspeicher erneut manuell einzutragen.

Übertragbare Daten:
- App-Design-Einstellungen
- Sprache
- Sortierung
- Favoriten
- Cloud-Provider-Konfigurationen
- Server-URLs
- Benutzernamen optional
- Zugangsdaten nur verschlüsselt
- Netzlaufwerke
- FTP/SFTP/WebDAV Profile
- lokale Einstellungen
- Ausschlusslisten
- Sync-Regeln optional

Nicht ungeschützt übertragen:
- Passwörter
- Tokens
- App-Passwörter
- private Schlüssel

Implementiere Export/Import:
1. Lokale verschlüsselte Backup-Datei
   - Dateiendung: `.myexplorerbackup`
   - AES-256-GCM
   - Passwortgeschützt
   - optional Biometrie-geschützt

2. QR-Code Transfer
   - nur für kleine Konfigurationen ohne Passwörter
   - oder nur Pairing-Schlüssel

3. NFC Transfer
   - NFC überträgt Pairing-Token
   - eigentliche Daten werden verschlüsselt per lokalem Netzwerk/Bluetooth/Wi-Fi Direct übertragen

4. Bluetooth Transfer
   - Geräte koppeln
   - verschlüsselter Austausch
   - Fortschrittsanzeige
   - Importvorschau

5. Wi-Fi Direct / lokales Netzwerk
   - temporärer verschlüsselter Mini-Server
   - Einmal-Code
   - QR-Code zum Verbinden
   - automatische Abschaltung nach Transfer

6. FTP/RDP-Hinweis
   - FTP kann für Dateiübertragung unterstützt werden
   - RDP selbst ist keine gute Methode für App-Konfigurationssync
   - Falls RDP genutzt wird, nur als Remote-Zugriff auf das Gerät/den Rechner erklären
   - App-Konfigurationsübertragung besser über Backup-Datei, WebDAV, Bluetooth, NFC oder lokales Netzwerk lösen

Geräte-Pairing:
- Gerät A: „Einstellungen exportieren“
- Gerät B: „Von anderem Gerät importieren“
- Einmal-Code anzeigen
- QR-Code oder NFC antippen
- Verbindung verschlüsselt aufbauen
- Importvorschau anzeigen
- Nutzer wählt aus:
  - Design
  - Cloudprofile
  - Favoriten
  - Zugangsdaten
  - Netzwerkeinstellungen
- danach Import bestätigen

Cloudprofile sollen so strukturiert sein:
```json
{
  "id": "nextcloud_manual_001",
  "type": "webdav",
  "displayName": "Meine Nextcloud",
  "serverUrl": "https://example.com/remote.php/dav/files/username/",
  "username": "username",
  "credentialRef": "android_keystore_reference",
  "favoriteFolders": [],
  "syncEnabled": false,
  "createdAt": "",
  "updatedAt": ""
}
```
