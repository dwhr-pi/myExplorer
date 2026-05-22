# Provider-Hinweise

## Grundregeln

- `verified` bedeutet: Schnittstelle oder URL ist standardisiert oder öffentlich dokumentiert.
- `community` bedeutet: Provider ist sinnvoll vorbereitet, aber Endpunkte oder Details bleiben benutzerspezifisch.
- `experimental` bedeutet: Schnittstelle ist unstet, im Auslaufen oder stark implementierungsabhängig.
- `unknown` bedeutet: bewusst noch keine belastbare Aussage.

## Warum viele Einträge keine feste URL haben

Viele Anbieter oder selbst gehostete Systeme besitzen:

- instanzabhängige Hostnamen
- benutzerabhängige Pfade
- reverse-proxy-spezifische URLs
- unterschiedliche Login-Flows

Darum trägt MyExplorer nur dort Standard-URLs ein, wo sie als Muster wirklich belastbar sind. Sonst bleibt der Provider absichtlich manuell.

## Bereits mit fester oder standardisierter Grundlage vorbereitet

- Nextcloud: `https://example.com/remote.php/dav/files/USERNAME/`
- ownCloud: `https://example.com/owncloud/remote.php/dav/files/USERNAME/`
- mailbox.org Drive: `https://dav.mailbox.org/servlet/webdav.infostore/Userstore/`
- Koofr: `https://app.koofr.net/dav/Koofr`
- Yandex Disk: `https://webdav.yandex.com`

## Vorsicht bei experimentellen Providern

- Icedrive ist im Katalog als `experimental` markiert, weil WebDAV laut Support seit dem 15. April 2026 schrittweise eingestellt wird.
- SharePoint/WebDAV ist historisch uneinheitlich und sollte später eher per API statt per Alt-WebDAV umgesetzt werden.
