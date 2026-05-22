# Cloud Provider

Der Provider-Katalog liegt in `app/src/main/assets/cloud_providers.json` und enthält aktuell 82 vorbereitete Einträge. Jeder Eintrag ist modular gehalten und kann unabhängig geprüft, korrigiert oder deaktiviert werden.

## Schema

```json
{
  "providerId": "manual_webdav",
  "displayName": "Manueller WebDAV-Server",
  "category": "manuelle Server",
  "region": "global",
  "freeUsable": "unknown",
  "freeStorage": null,
  "webdavSupport": "yes",
  "defaultUrl": null,
  "loginType": ["manuelle URL", "Benutzername/Passwort", "App-Passwort"],
  "notes": "Nutzer tragen eigene Serverdaten ein.",
  "securityWarnings": ["HTTP nur nach ausdrücklicher Freigabe erlauben."],
  "status": "verified"
}
```

## Statuswerte

- `verified`: WebDAV-Unterstützung oder Standardpfad ist belastbar dokumentiert.
- `community`: Anbieter ist sinnvoll vorbereitet, aber URL oder Tarifdetails sind konto- oder instanzabhängig.
- `experimental`: Schnittstelle ist unstet, im Auslaufen oder stark abhängig von Serverkonfiguration.
- `unknown`: keine ausreichend sichere öffentliche Dokumentation gefunden.

## Verifizierte URL-Muster

- Nextcloud: `https://example.com/remote.php/dav/files/USERNAME/`
- ownCloud: `https://example.com/owncloud/remote.php/dav/files/USERNAME/`
- mailbox.org Drive: `https://dav.mailbox.org/servlet/webdav.infostore/Userstore/`
- Koofr: `https://app.koofr.net/dav/Koofr`
- Yandex Disk: `https://webdav.yandex.com`

## Prüfregel

Wenn kein belastbarer öffentlicher WebDAV-Endpunkt gefunden wird, bleibt `defaultUrl` leer und der Anbieter wird `unknown`, `community` oder `experimental`. MyExplorer baut keine erfundenen Provider-URLs und keine Zugangsdaten ein.

## Quellen

- Nextcloud WebDAV: https://docs.nextcloud.com/server/latest/user_manual/en/files/access_webdav.html
- ownCloud WebDAV API: https://owncloud.dev/apis/http/webdav/
- mailbox.org WebDAV: https://kb.mailbox.org/de/business/datei-cloud-mailbox-org-drive/webdav-unter-linux/
- Koofr WebDAV: https://app.koofr.net/help/webdav
- Yandex Disk WebDAV API: https://yandex.com/dev/disk/webdav/
- Hetzner Storage Box WebDAV: https://docs.hetzner.com/robot/storage-box/access/access-webdav/
- Synology WebDAV Server: https://www.synology.com/en-us/dsm/7.2/software_spec/webdav
- Icedrive WebDAV status: https://icedrive.net/help/account/does-icedrive-support-webdav
