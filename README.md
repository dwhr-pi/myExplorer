# MyExplorer

MyExplorer ist ein eigenständiger orangefarbener Privacy-First-Dateimanager für Android. Die App orientiert sich an lokaler Kontrolle, WebDAV/Cloud-Speichern, verschlüsseltem Export und Geräteübertragung ohne Account-Zwang.

## Aktueller Stand

Dieses Repository enthält jetzt ein neues Android-Grundgerüst auf Basis von:

- Kotlin
- Jetpack Compose
- Material 3
- DataStore
- Android Keystore / `EncryptedSharedPreferences`

Bereits angelegt:

- orange Theme mit Light, Dark und AMOLED-Variante
- eigenständiges Launcher-Icon im orangefarbenen Dateiordner-/Kompass-Stil
- Cloud-Provider-Datenbank `app/src/main/assets/cloud_providers.json`
- Navigation: Start, Dateien, Cloud, Netzwerk, Werkzeuge, Einstellungen
- Datenmodelle für Cloud-Profile und Export-/Import-Payloads
- AES-256-GCM-Backup-Verschlüsselung mit PBKDF2-Härtung
- Compose-Oberfläche für Cloud-Katalog, Transfer, Sicherheitscenter und Datenschutz

## Projektziele

- lokale Dateiverwaltung mit starker Material-3-Oberfläche
- Cloud- und WebDAV-Manager für viele bekannte Dienste und eigene Server
- einfache Übertragung von Einstellungen zwischen Geräten
- keine Telemetrie, keine Werbung, keine Tracker
- sensible Daten nicht im Klartext speichern

## Cloud/WebDAV-Konzept

Version 1 priorisiert manuelles WebDAV für eigene Server, Nextcloud und ownCloud. Provider aus dem Katalog dienen als geprüfte Vorlagen oder als bewusst manuelle Einträge. Feste URLs werden nur dort eingetragen, wo sie öffentlich dokumentiert oder als Standardpfad belastbar sind.

Unklare Anbieter bleiben `unknown` oder erfordern manuelle Einrichtung. Zugangsdaten werden nie mitgeliefert und müssen vom Nutzer selbst eingetragen werden.

## Geräteübertragung ohne Account

MyExplorer soll Einstellungen und Profile zwischen Geräten übertragen, ohne einen zentralen Account zu benötigen. Der erste Weg ist eine passwortgeschützte `.myexplorerbackup`-Datei. Danach folgen QR-Code-Pairing, NFC-Pairing, Bluetooth und lokales WLAN oder Wi-Fi Direct.

RDP ist kein echter Sync-Weg für App-Konfigurationen. Es wird nur als Remote-Zugriff erwähnt; für Konfigurationsübertragung sind Backup-Datei, WebDAV, Bluetooth, NFC, QR-Code oder lokales Netzwerk besser geeignet.

## Sicherheitsmodell

Passwörter, Tokens, App-Passwörter und private Schlüssel dürfen nicht im Klartext gespeichert oder ungeschützt exportiert werden. Cloud-Profile referenzieren Secrets über `credentialRef`; Secrets liegen im Android Keystore beziehungsweise in verschlüsselten lokalen Stores.

Exportdaten werden per AES-256-GCM verschlüsselt. HTTP-Verbindungen sollen sichtbar gewarnt werden, HTTPS hat Vorrang. Die App enthält keine Werbung, keine Telemetrie und keine Tracker.

## Eigenständigkeit

MyExplorer ist keine Xiaomi-App, kein Mi-File-Manager-Klon und nutzt weder Xiaomi-Code noch Xiaomi-Marken oder geschützte UI-Elemente. Das orange Design ist eine eigene Open-Source-Identität, passend für orange angepasste Android-, Linux- oder Windows-Setups.

## Wichtige Dateien

- `app/src/main/assets/cloud_providers.json`
- `app/src/main/java/com/dwhr/myexplorer/data/model`
- `app/src/main/java/com/dwhr/myexplorer/data/security`
- `app/src/main/java/com/dwhr/myexplorer/ui`
- `docs/ARCHITECTURE.md`
- `docs/CLOUD_PROVIDERS.md`
- `docs/SECURITY_MODEL.md`
- `docs/TRANSFER_WITHOUT_ACCOUNT.md`
- `docs/ORANGE_DESIGN_SYSTEM.md`
- `docs/ROADMAP.md`
- `docs/PRIVACY.md`

## Hinweis zum Implementierungsstatus

Dieses Fundament ist bewusst sicherheits- und architekturlastig aufgebaut. Vollständige Dateioperationen gegen WebDAV/SMB/FTP/SFTP, Zertifikatsdialoge, verschlüsselte Room-Datenbank und echte Geräte-zu-Gerät-Transferschichten sind als nächste Ausbaustufe vorgesehen, aber noch nicht vollständig integriert.
