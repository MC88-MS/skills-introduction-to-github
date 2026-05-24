# 💊 SuppleScan

**SuppleScan** è un'app Android che analizza la qualità degli integratori alimentari, ispirata a Yuka. Scansiona il barcode di un integratore e ottieni in secondi un punteggio scientifico basato sugli ingredienti.

---

## 📱 Schermate principali

```
┌─────────────────┐   ┌─────────────────┐   ┌─────────────────┐
│   🏠 HOME       │   │  📷 SCANNER     │   │  📋 DETTAGLIO   │
│                 │   │                 │   │                 │
│  💊 SuppleScan  │   │  ┌──────────┐  │   │  ╔═══════╗      │
│  "Analizza i    │   │  │ CAMERA   │  │   │  ║  A+   ║      │
│   tuoi integr." │   │  │ PREVIEW  │  │   │  ║  96   ║      │
│                 │   │  └──────────┘  │   │  ╚═══════╝      │
│  [📷 Scansiona] │   │   ┌────────┐   │   │                 │
│  [✏️ Inserisci] │   │   │  ████  │   │   │  Eccellente     │
│                 │   │   └────────┘   │   │  Vitamina C Pro │
│  Statistiche:   │   │  "Inquadra il  │   │  Solgar         │
│  Avg: 78 | 5✅  │   │   codice a     │   │                 │
│                 │   │   barre"       │   │  🧪 Ingredienti │
│  [Recenti...]   │   │                │   │  ✅ Vitamina C  │
└─────────────────┘   └─────────────────┘   └─────────────────┘
```

---

## ✨ Funzionalità

### 🔍 Scansione Barcode
- Scansione in tempo reale con **ML Kit Barcode Scanning**
- Supporto EAN-13, EAN-8, UPC-A, UPC-E, QR Code
- Inserimento manuale del barcode se la fotocamera non è disponibile

### 📊 Sistema di Punteggio
Il punteggio (0-100) viene calcolato in base agli ingredienti:

| Grado | Punteggio | Significato |
|-------|-----------|-------------|
| **A+** | 90-100 | Formulazione eccellente |
| **A**  | 80-89  | Buona qualità |
| **B**  | 65-79  | Discreta |
| **C**  | 50-64  | Mediocre |
| **D**  | 30-49  | Scadente |
| **F**  | 0-29   | Da evitare |

### 🧪 Database Ingredienti
Database con **60+ ingredienti** valutati scientificamente, categorizzati in:

- 🟢 **Eccellente**: Vitamina C, D3, K2, Creatina, Omega-3, Ashwagandha, L-Citrullina, CoQ10...
- 🟡 **Buono**: Vitamina E, B6, Zinco, Caffeina, Probiotici, Beta-Alanina...
- 🟠 **Neutro**: Vitamina A, Cromo, L-Carnitina, Resveratrolo...
- 🔴 **Attenzione**: Yohimbina, Tribulus, CLA, Betacarotene (nei fumatori)...
- ⛔ **Da evitare**: Biossido di Titanio (E171), Efedrina...

Ogni ingrediente include:
- Descrizione scientifica
- Dettagli su evidenze, dosaggi e sicurezza
- Categoria (Vitamina, Minerale, Aminoacido, Erboristico, Probiotico...)

### 📚 Cronologia & Preferiti
- Salvataggio automatico di tutti i prodotti scansionati
- Sistema di preferiti per i prodotti preferiti
- Statistiche aggregate (punteggio medio, n° prodotti eccellenti)

### ✏️ Inserimento Manuale
- Inserisci nome, marca, categoria e ingredienti a mano
- Utile per prodotti non ancora nel database online

---

## 🏗️ Architettura

```
com.supplescan/
├── data/
│   ├── model/          # Supplement, Ingredient, enums
│   ├── database/       # Room DB (ScannedSupplement, Ingredient)
│   └── repository/     # SupplementRepository
├── domain/
│   └── scoring/        # ScoringEngine (punteggio 0-100)
├── di/                 # Hilt DI modules
├── ui/
│   ├── theme/          # Material3 theme, colori
│   ├── components/     # ScoreCircle, ScoreBadge, IngredientRow
│   └── screens/        # HomeScreen, ScannerScreen, ProductDetail,
│                       # HistoryScreen, ManualEntryScreen
├── MainActivity.kt     # Compose Navigation
└── SuppleScanApp.kt    # Hilt Application
```

**Stack tecnologico:**

| Libreria | Versione | Uso |
|----------|----------|-----|
| Jetpack Compose | BOM 2024.06 | UI dichiarativa |
| Material3 | - | Design system |
| Hilt | 2.51.1 | Dependency Injection |
| Room | 2.6.1 | Database locale |
| ML Kit Barcode | 17.3 | Scansione barcode |
| CameraX | 1.3.4 | Preview fotocamera |
| Navigation Compose | 2.7.7 | Navigazione |
| Accompanist | 0.34.0 | Gestione permessi |

---

## 🚀 Come costruire l'app

### Prerequisiti
- Android Studio Hedgehog o superiore
- JDK 17
- Android SDK 26+

### Build
```bash
# Clone
git clone https://github.com/mc88-ms/skills-introduction-to-github.git
cd skills-introduction-to-github/SuppleScan

# Build debug APK
./gradlew assembleDebug

# Installa su dispositivo
./gradlew installDebug
```

---

## 📈 Roadmap futura

- [ ] **Integrazione Open Food Facts API** — recupero automatico prodotti dal barcode
- [ ] **Integrazione OpenSupplements DB** — database specifico per integratori
- [ ] **Comparazione prodotti** — confronta due integratori fianco a fianco
- [ ] **Filtri per obiettivo** — "Voglio integratori per il sonno"
- [ ] **Interazioni tra ingredienti** — avvisi su combinazioni da evitare
- [ ] **Dose giornaliera raccomandata** — confronto con RDA/DRI
- [ ] **Widget** — accesso rapido alla scansione dal home screen
- [ ] **Modalità offline** — funzionalità complete senza internet

---

## 📋 Disclaimer

Le valutazioni degli ingredienti si basano su ricerche scientifiche pubblicate e linee guida di organismi regolatori (EFSA, FDA). Questa app è a scopo informativo e non sostituisce il parere di un medico o nutrizionista qualificato. Consulta sempre un professionista prima di iniziare o modificare un regime di integrazione.

---

## 📄 Licenza

MIT License — vedi [LICENSE](../LICENSE)
