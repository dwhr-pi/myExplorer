# Update Checks

Eine Updateprüfung ist auch ohne Play Store möglich. Für Alpha-Versionen ist ein eigenes Update-Manifest sinnvoll.

## Alpha-Manifest

Die App kann später eine JSON-Datei abrufen:

```json
{
  "productId": "myexplorer-android-alpha",
  "channel": "alpha",
  "latestVersionCode": 2,
  "latestVersionName": "0.2.0-alpha",
  "downloadUrl": "https://example.com/myexplorer-alpha.apk",
  "releaseNotesUrl": "https://example.com/myexplorer/releases/0.2.0-alpha",
  "publishedAt": "2026-05-22T00:00:00Z",
  "required": false
}
```

Der Manifest-Link wird in `app/src/main/assets/app_config.json` unter `updates.manifestUrl` eingetragen.

## Play Store

Wenn MyExplorer später im Play Store liegt, kann zusätzlich die Play Core In-App-Update-API genutzt werden. Für Alpha-Builds außerhalb des Stores ist das Manifest-Modell einfacher und transparenter.

## Sicherheit

- Manifest nur über HTTPS abrufen.
- Produktkennung und Kanal prüfen.
- APK-Download nicht automatisch installieren.
- Release Notes anzeigen.
- Nutzer muss den Download bestätigen.
