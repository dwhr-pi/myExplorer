# Architekturüberblick

## Zielbild

MyExplorer soll ein privatsphäreorientierter Datei-Manager für Android werden, der lokale Dateien, Netzwerkfreigaben und Cloudspeicher in einer gemeinsamen Oberfläche verwaltet.

## Derzeit umgesetzte Basis

### UI

- Jetpack Compose mit Material 3
- orange Markenidentität
- Light-, Dark- und AMOLED-Theme
- Startoberfläche mit vier Bereichen:
  - Übersicht
  - Cloud
  - Transfer
  - Privatsphäre

### Datenmodelle

- `CloudProvider`
- `CloudProfile`
- `AppSettingsExport`
- `DeviceTransferSelection`
- `MyExplorerBackupPayload`

### Sicherheit

- `CredentialVault` speichert Secrets in `EncryptedSharedPreferences`
- `BackupCrypto` verschlüsselt Exporte via AES-256-GCM
- Schlüsselableitung erfolgt über `PBKDF2WithHmacSHA256`
- Klartext-Passwörter sind im Profilmodell vermieden, stattdessen `credentialRef`

## Bewusst vorbereitet, aber noch nicht fertig

### Cloud-Zugriff

- WebDAV
- Nextcloud
- ownCloud
- SMB
- FTP / FTPS
- SFTP
- optionale spätere S3-Schicht

Der Provider-Katalog ist bereits da, die echten Netzwerk-Clients noch nicht.

### Geräteübertragung

Vorbereitet als Produktfluss:

1. Export in `.myexplorerbackup`
2. Pairing via QR oder NFC
3. verschlüsselte Session über Bluetooth oder lokales Netzwerk
4. Importvorschau mit selektiver Übernahme

Die Kryptobasis für Backup-Dateien ist vorhanden, die Transportschicht folgt später.

### Datenhaltung

Aktuell:

- Einstellungen in DataStore
- Secrets verschlüsselt in `EncryptedSharedPreferences`

Später:

- verschlüsselte Room-Datenbank für Cloud-Profile, Favoriten und Transferhistorie

## Sicherheitsprinzipien

- HTTPS standardmäßig bevorzugen
- HTTP nicht stillschweigend akzeptieren
- keine eingebauten Standard-Zugangsdaten
- Provider-URLs nur dann fest eintragen, wenn sie standardisiert oder verifiziert sind
- Import/Export nur verschlüsselt
- sensible Felder selektiv übertragbar
