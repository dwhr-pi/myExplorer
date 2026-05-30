package com.dwhr.myexplorer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dwhr.myexplorer.data.model.AppThemeMode
import com.dwhr.myexplorer.data.model.CloudProfile
import com.dwhr.myexplorer.data.model.CloudProvider
import com.dwhr.myexplorer.data.update.UpdateStatus

private data class Destination(
    val label: String,
    val marker: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyExplorerApp(
    state: MyExplorerState,
    onThemeModeChanged: (AppThemeMode) -> Unit,
    onDynamicColorChanged: (Boolean) -> Unit,
    onSendErrorReport: () -> Unit,
) {
    val destinations = listOf(
        Destination("Start", "S"),
        Destination("Dateien", "D"),
        Destination("Cloud", "C"),
        Destination("Netzwerk", "N"),
        Destination("Werkzeuge", "W"),
        Destination("Einstellungen", "E"),
    )
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("MyExplorer", fontWeight = FontWeight.Bold)
                        Text(
                            text = "Orange File Manager für lokale Kontrolle",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar {
                destinations.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = { Text(destination.marker, fontWeight = FontWeight.Bold) },
                        label = { Text(destination.label) },
                    )
                }
            }
        },
    ) { innerPadding ->
        Surface(modifier = Modifier.fillMaxSize()) {
            when (selectedIndex) {
                0 -> OverviewScreen(innerPadding, state, onThemeModeChanged, onDynamicColorChanged)
                1 -> FilesScreen(innerPadding)
                2 -> CloudScreen(innerPadding, state.providers, state.profiles)
                3 -> NetworkScreen(innerPadding)
                4 -> ToolsScreen(innerPadding, state, onSendErrorReport)
                else -> SettingsScreen(innerPadding, state, onThemeModeChanged, onDynamicColorChanged)
            }
        }
    }
}

@Composable
private fun OverviewScreen(
    innerPadding: PaddingValues,
    state: MyExplorerState,
    onThemeModeChanged: (AppThemeMode) -> Unit,
    onDynamicColorChanged: (Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            HeroCard(
                title = "Cloud & lokale Dateien ohne Lock-in",
                body = "MyExplorer bündelt WebDAV, Nextcloud, SMB und sichere Geräteübertragung in einer orangefarbenen Material-3-Oberfläche.",
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                MetricCard("Provider", state.providers.size.toString(), Modifier.weight(1f))
                MetricCard("Profile", state.profiles.size.toString(), Modifier.weight(1f))
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                MetricCard("Fehlerlogs", state.errorLogCount.toString(), Modifier.weight(1f))
                MetricCard("Kanal", state.appConfig?.updates?.channel ?: "alpha", Modifier.weight(1f))
            }
        }
        item {
            ThemeModeCard(
                themeMode = state.themeMode,
                dynamicColor = state.dynamicColor,
                onThemeModeChanged = onThemeModeChanged,
                onDynamicColorChanged = onDynamicColorChanged,
            )
        }
        item {
            RoadmapCard(
                title = "Version 1 Fokus",
                lines = listOf(
                    "Lokaler Datei-Manager",
                    "Orange Material-3-Design",
                    "Manuelles WebDAV für Nextcloud und ownCloud",
                    "Verschlüsselte Speicherung und Backup-Datei",
                ),
            )
        }
    }
}

@Composable
private fun FilesScreen(innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            HeroCard(
                title = "Dateien",
                body = "Lokale Ordner, Favoriten, Sortierung und spätere Offline-Caches bekommen hier ihren Arbeitsbereich.",
            )
        }
        item {
            RoadmapCard(
                title = "Lokale Kontrolle",
                lines = listOf(
                    "Favoriten und Sortierung lokal speichern",
                    "Offline-Cache opt-in vorbereiten",
                    "Ausschlusslisten für Backup und Sync führen",
                    "Keine Telemetrie und keine externen Tracker",
                ),
            )
        }
    }
}

@Composable
private fun CloudScreen(
    innerPadding: PaddingValues,
    providers: List<CloudProvider>,
    profiles: List<CloudProfile>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            HeroCard(
                title = "Cloud & WebDAV Manager",
                body = "Vorbereitete Anbieter, manuelle Serverprofile und sichere Credential-Referenzen bilden das Fundament für spätere Dateioperationen.",
            )
        }
        item {
            RoadmapCard(
                title = "Cloud-Aktionen",
                lines = listOf(
                    "Verbundene Speicher anzeigen",
                    "Provider hinzufügen",
                    "Manuellen WebDAV-Server hinzufügen",
                    "Verbindung testen und Zertifikat anzeigen",
                    "Backup, Import und Geräteübertragung starten",
                    "Sicherheitscenter öffnen",
                ),
            )
        }
        item {
            Text("Gespeicherte Profile", style = MaterialTheme.typography.titleMedium)
        }
        items(profiles) { profile ->
            InfoCard(
                title = profile.displayName,
                subtitle = "${profile.type.uppercase()} • ${profile.serverUrl}",
                tags = listOfNotNull(profile.username?.let { "User: $it" }, profile.credentialRef?.let { "Keystore" }),
            )
        }
        item {
            Text("Anbieter-Katalog", style = MaterialTheme.typography.titleMedium)
        }
        items(providers.take(12)) { provider ->
            InfoCard(
                title = provider.displayName,
                subtitle = "${provider.category} • Status: ${provider.status}",
                tags = buildList {
                    add("WebDAV: ${provider.webdavSupport}")
                    provider.region?.let(::add)
                    provider.freeStorage?.let(::add)
                },
            )
        }
        if (providers.size > 12) {
            item {
                Text(
                    text = "… plus ${providers.size - 12} weitere vorbereitete Provider in `cloud_providers.json`.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun NetworkScreen(innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            HeroCard(
                title = "Netzwerk",
                body = "SMB, FTP, FTPS, SFTP, WebDAV und lokale Freigaben werden als getrennte Verbindungstypen vorbereitet.",
            )
        }
        item {
            RoadmapCard(
                title = "Verbindungstypen",
                lines = listOf(
                    "WebDAV, Nextcloud und ownCloud zuerst",
                    "SMB für lokale Freigaben",
                    "SFTP bevorzugt vor FTP",
                    "FTP nur mit klarer Warnung",
                ),
            )
        }
    }
}

@Composable
private fun ToolsScreen(
    innerPadding: PaddingValues,
    state: MyExplorerState,
    onSendErrorReport: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            RoadmapCard(
                title = "Geräteübertragung",
                lines = listOf(
                    "Einstellungen exportieren",
                    "Von anderem Gerät importieren",
                    "Einmal-Code anzeigen",
                    "QR-Code oder NFC als Pairing-Start verwenden",
                    "Importvorschau vor Übernahme anzeigen",
                ),
            )
        }
        item {
            RoadmapCard(
                title = "Backup und Transfer",
                lines = listOf(
                    "`.myexplorerbackup` mit Passwortschutz",
                    "QR nur für kleine Konfigurationen oder Pairing-Schlüssel",
                    "Bluetooth mit Fortschrittsanzeige",
                    "Wi‑Fi Direct oder LAN-Mini-Server mit Auto-Shutdown",
                    "RDP nur als Remote-Zugriff erwähnen, nicht als Sync-Weg",
                ),
            )
        }
        item {
            RoadmapCard(
                title = "Fehlerprotokoll",
                lines = listOf(
                    "Produktkennung: ${state.appConfig?.productId ?: "myexplorer-android-alpha"}",
                    "Zieladresse: ${state.appConfig?.errorReporting?.recipientEmail ?: "nicht geladen"}",
                    "SMTP-Passwort nur als Keystore-Referenz",
                    "Versand per sichtbarem Email-Intent vorbereitet",
                ),
            )
        }
        item {
            Button(onClick = onSendErrorReport) {
                Text("Fehlerprotokoll per Email senden")
            }
        }
    }
}

@Composable
private fun SettingsScreen(
    innerPadding: PaddingValues,
    state: MyExplorerState,
    onThemeModeChanged: (AppThemeMode) -> Unit,
    onDynamicColorChanged: (Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            HeroCard(
                title = "Einstellungen",
                body = "Design, Sicherheit, App-Sperre, Biometrie und Exportoptionen werden hier zusammengeführt.",
            )
        }
        item {
            ThemeModeCard(
                themeMode = state.themeMode,
                dynamicColor = state.dynamicColor,
                onThemeModeChanged = onThemeModeChanged,
                onDynamicColorChanged = onDynamicColorChanged,
            )
        }
        item {
            RoadmapCard(
                title = "Sicherheitscenter",
                lines = listOf(
                    "Gespeicherte Zugänge prüfen",
                    "Unsichere Verbindungen anzeigen",
                    "Backup verschlüsseln",
                    "Alte Tokens entfernen",
                    "App-Sperre und Biometrie aktivieren",
                ),
            )
        }
        item {
            RoadmapCard(
                title = "Updateprüfung",
                lines = listOf(
                    updateStatusText(state.updateStatus),
                    "Alpha-Versionen funktionieren über ein eigenes HTTPS-Update-Manifest.",
                    "Play Store ist später optional über Play Core möglich.",
                ),
            )
        }
        item {
            RoadmapCard(
                title = "Datenschutz",
                lines = listOf(
                    "Keine Werbung",
                    "Keine Tracker",
                    "Keine Telemetrie",
                    "Keine Zugangsdaten im Klartext",
                ),
            )
        }
    }
}

private fun updateStatusText(status: UpdateStatus): String = when (status) {
    UpdateStatus.NotConfigured -> "Noch kein Update-Manifest konfiguriert."
    UpdateStatus.Checking -> "Updateprüfung läuft."
    is UpdateStatus.UpToDate -> "Aktuell: ${status.currentVersionName}"
    is UpdateStatus.Available -> "Update verfügbar: ${status.latestVersionName}"
    is UpdateStatus.Failed -> "Updateprüfung fehlgeschlagen: ${status.message}"
}

@Composable
private fun HeroCard(title: String, body: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(28.dp),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(title, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
            Text(body, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}

@Composable
private fun MetricCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(24.dp)) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun ThemeModeCard(
    themeMode: AppThemeMode,
    dynamicColor: Boolean,
    onThemeModeChanged: (AppThemeMode) -> Unit,
    onDynamicColorChanged: (Boolean) -> Unit,
) {
    Card(shape = RoundedCornerShape(24.dp)) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Theme", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppThemeMode.entries.forEach { mode ->
                    FilterChip(
                        selected = themeMode == mode,
                        onClick = { onThemeModeChanged(mode) },
                        label = { Text(mode.name) },
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text("Dynamic Color optional")
                    Text(
                        "Orange bleibt Vorrang, Dynamic Color ist nur ein Zusatz.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Switch(checked = dynamicColor, onCheckedChange = onDynamicColorChanged)
            }
        }
    }
}

@Composable
private fun InfoCard(title: String, subtitle: String, tags: List<String>) {
    Card(shape = RoundedCornerShape(22.dp)) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                tags.take(3).forEach { tag ->
                    AssistChip(onClick = {}, label = { Text(tag) })
                }
            }
        }
    }
}

@Composable
private fun RoadmapCard(title: String, lines: List<String>) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            lines.forEach { line ->
                Text("• $line")
            }
        }
    }
}
