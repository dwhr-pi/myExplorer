# Transfer Without Account

MyExplorer soll mehrere Geräte einrichten können, ohne dass ein zentraler Account nötig ist. Der Nutzer kontrolliert, was übertragen wird.

## Übertragbare Daten

- Design-Einstellungen
- Sprache
- Sortierung
- Favoriten
- Cloudprofile
- Server-URLs
- Benutzernamen optional
- Netzwerkprofile
- lokale Einstellungen
- Ausschlusslisten
- Sync-Regeln später optional

## Nie ungeschützt übertragen

- Passwörter
- Tokens
- App-Passwörter
- private Schlüssel

## Backup-Datei

Die lokale Backup-Datei ist der erste robuste Transferweg:

- Endung `.myexplorerbackup`
- AES-256-GCM
- Passwortschutz
- Importvorschau
- selektive Übernahme von Design, Cloudprofilen, Favoriten, Zugangsdaten und Netzwerkeinstellungen

## QR-Code

QR-Codes sollen nur kleine Konfigurationen ohne Passwörter oder Pairing-Schlüssel transportieren. Sensible Daten werden danach über eine verschlüsselte Sitzung übertragen.

## NFC

NFC überträgt nur ein Pairing-Token. Die eigentlichen Daten werden verschlüsselt über lokales Netzwerk, Bluetooth oder Wi-Fi Direct übertragen.

## Bluetooth

Bluetooth dient als Account-freier Direkttransfer mit Kopplung, Verschlüsselung, Fortschrittsanzeige und Importvorschau.

## Wi-Fi Direct und lokales Netzwerk

Ein Gerät startet temporär einen verschlüsselten Mini-Server. Das andere Gerät verbindet sich über Einmal-Code oder QR-Code. Der Server schaltet sich nach dem Transfer automatisch ab.

## RDP

RDP ist kein sinnvoller Sync-Weg für App-Konfigurationen. Es kann höchstens als Remote-Zugriff auf ein Gerät oder einen Rechner erklärt werden. MyExplorer-Konfigurationen sollen über Backup-Datei, WebDAV, QR-Code, NFC, Bluetooth oder lokales Netzwerk übertragen werden.
