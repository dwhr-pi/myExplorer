# Security Model

MyExplorer speichert sensible Daten nach dem Prinzip: so wenig wie möglich, so lokal wie möglich, nie im Klartext.

## Zugangsdaten

- Cloud-Profile enthalten nur `credentialRef`.
- Passwörter, Tokens, App-Passwörter und private Schlüssel werden nicht im Klartext gespeichert.
- Secrets werden über Android Keystore und verschlüsselte lokale Stores abgelegt.
- Alte Tokens sollen im Sicherheitscenter sichtbar und löschbar werden.
- Das Fehlerreporting nutzt nur eine SMTP-Credential-Referenz; das echte Mail-Passwort wird nicht im Repository gespeichert.

## Backup

- Dateiendung: `.myexplorerbackup`
- Verschlüsselung: AES-256-GCM
- Schlüsselableitung: PBKDF2 mit SHA-256
- Passwortschutz ist Pflicht.
- Biometrie oder Geräte-PIN kann später als zusätzliche Freigabe vor Export und Import dienen.

## Verbindungen

- HTTPS hat Vorrang.
- HTTP soll sichtbar gewarnt und standardmäßig blockiert werden.
- Zertifikate sollen angezeigt werden.
- Unsichere Zertifikate sollen blockierbar sein.

## Lokale Datenbank

Version 1 nutzt DataStore und verschlüsselte Preferences als Fundament. Für persistente Cloudprofile ist eine verschlüsselte Room-Datenbank vorgesehen.

## Nicht-Ziele

- Keine Telemetrie.
- Keine Werbung.
- Keine Tracker.
- Keine eingebauten Testzugänge.
- Kein unverschlüsselter Export sensibler Daten.
