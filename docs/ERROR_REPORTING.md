# Error Reporting

MyExplorer kann Fehler lokal protokollieren und als bewusst ausgelösten Email-Bericht versenden.

## Zieladresse

Die Zieladresse steht in `app/src/main/assets/app_config.json`:

```json
"recipientEmail": "ai-chat-to-markdown@web.de"
```

Die App nutzt außerdem eine eindeutige Produktkennung:

```json
"productId": "myexplorer-android-alpha"
```

Dadurch lassen sich Berichte von anderen Produkten unterscheiden, selbst wenn dieselbe Mailadresse verwendet wird.

## Passwort

Das Mail-Passwort wird nicht im Repository gespeichert. Auch eine statisch "verschlüsselte" Variante im APK wäre nicht sicher, weil App und Entschlüsselungslogik gemeinsam ausgeliefert werden.

Für späteren SMTP-Versand gibt es nur eine Referenz:

```json
"smtpPasswordCredentialRef": "credential:error-reporting:webde:smtp-password"
```

Das echte Passwort muss auf dem Gerät über Android Keystore beziehungsweise `CredentialVault` gespeichert werden.

Die Hilfsklasse `ErrorReportingCredentials` speichert, lädt und löscht dieses Secret über die Referenz. Damit bleibt die Config eindeutig, aber ohne eingebettetes Passwort.

## Versandmodell

Version 1 erzeugt einen Email-Intent. Der Nutzer sieht den Bericht und sendet ihn über seine Mail-App. Das passt besser zum Privacy-First-Ansatz als stiller Versand im Hintergrund.

Automatischer SMTP-Versand sollte erst aktiviert werden, wenn Credential-Eingabe, Keystore-Speicherung, Nutzerzustimmung, Rate-Limit und Datenschutzdialog implementiert sind.
