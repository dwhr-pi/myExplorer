# Build APK

## Lokal

Wenn Gradle installiert ist:

```powershell
gradle assembleDebug
```

Die Debug-APK liegt danach hier:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## GitHub Actions

Das Repository enthält den Workflow `.github/workflows/android-alpha-apk.yml`. Nach dem Upload zu GitHub kann die Alpha-APK über **Actions > Build Android Alpha APK > Run workflow** gebaut werden.

Der Workflow nutzt:

- JDK 17
- Gradle 8.7
- `gradle assembleDebug`

Das Ergebnis wird als Artefakt `MyExplorer-alpha-debug-apk` hochgeladen.

## Release-Signatur

Die aktuelle APK ist eine Debug-/Alpha-APK. Für öffentliche Releases sollte später ein eigener Keystore verwendet werden, der nicht ins Repository committed wird.
