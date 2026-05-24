package com.supplescan.data.database

/**
 * Scientific database of supplement ingredients with health ratings.
 * Ratings based on peer-reviewed research, EFSA/FDA assessments, and clinical evidence.
 */
object IngredientSeedData {

    val ingredients: List<IngredientEntity> = listOf(

        // ─── VITAMINS ────────────────────────────────────────────────────────
        IngredientEntity(
            normalizedName = "vitamina c",
            displayName = "Vitamina C (Acido Ascorbico)",
            rating = "EXCELLENT",
            category = "VITAMIN",
            description = "Potente antiossidante essenziale per il sistema immunitario e la sintesi del collagene.",
            details = "Ampiamente studiata con migliaia di studi clinici. Sicura anche ad alte dosi (fino a 2g/die). Riduce la durata dei raffreddori, supporta la funzione immunitaria, migliora l'assorbimento del ferro. Riconosciuta da EFSA con 14 health claims approvati."
        ),
        IngredientEntity(
            normalizedName = "acido ascorbico",
            displayName = "Acido Ascorbico (Vitamina C)",
            rating = "EXCELLENT",
            category = "VITAMIN",
            description = "Forma naturale della Vitamina C, potente antiossidante.",
            details = "Identica alla Vitamina C. Eccellente profilo di sicurezza e tollerabilità. Dosi elevate possono causare disturbi gastrici."
        ),
        IngredientEntity(
            normalizedName = "vitamina d3",
            displayName = "Vitamina D3 (Colecalciferolo)",
            rating = "EXCELLENT",
            category = "VITAMIN",
            description = "Fondamentale per ossa, sistema immunitario e salute generale.",
            details = "La forma più biodisponibile di Vitamina D. Carenza molto comune (>50% della popolazione italiana). Supporta l'assorbimento del calcio, la funzione immunitaria, la salute muscolare. Dosi >4000 UI/die richiedono monitoraggio."
        ),
        IngredientEntity(
            normalizedName = "vitamina d",
            displayName = "Vitamina D",
            rating = "EXCELLENT",
            category = "VITAMIN",
            description = "Vitamina liposolubile essenziale per ossa e immunità.",
            details = "Prodotta naturalmente dalla pelle con la luce solare. Supplementazione raccomandata in molte popolazioni, specialmente in inverno."
        ),
        IngredientEntity(
            normalizedName = "vitamina b12",
            displayName = "Vitamina B12 (Cianocobalamina)",
            rating = "EXCELLENT",
            category = "VITAMIN",
            description = "Essenziale per il sistema nervoso e la produzione di globuli rossi.",
            details = "Carenza comune in vegani/vegetariani e anziani. Importante per la funzione neurologica e la sintesi del DNA. Nessuna tossicità nota anche ad alte dosi."
        ),
        IngredientEntity(
            normalizedName = "metilcobalamina",
            displayName = "Metilcobalamina (Vitamina B12 attiva)",
            rating = "EXCELLENT",
            category = "VITAMIN",
            description = "Forma bioattiva della Vitamina B12, superiore per alcuni usi.",
            details = "Forma coenzimata della B12, direttamente utilizzabile dall'organismo senza conversione. Preferibile per supporto neurologico."
        ),
        IngredientEntity(
            normalizedName = "vitamina b6",
            displayName = "Vitamina B6 (Piridossina)",
            rating = "GOOD",
            category = "VITAMIN",
            description = "Importante per il metabolismo proteico e la funzione neurologica.",
            details = "Essenziale per oltre 100 reazioni enzimatiche. Attenzione: dosi >100mg/die per lungo periodo possono causare neuropatia periferica. Dosi standard negli integratori sono sicure."
        ),
        IngredientEntity(
            normalizedName = "vitamina b1",
            displayName = "Vitamina B1 (Tiamina)",
            rating = "EXCELLENT",
            category = "VITAMIN",
            description = "Cruciale per il metabolismo energetico e la funzione nervosa.",
            details = "Nessuna tossicità nota. Fondamentale per il metabolismo dei carboidrati. Carenza grave causa il beriberi."
        ),
        IngredientEntity(
            normalizedName = "vitamina b2",
            displayName = "Vitamina B2 (Riboflavina)",
            rating = "EXCELLENT",
            category = "VITAMIN",
            description = "Essenziale per la produzione di energia cellulare.",
            details = "Ottimo profilo di sicurezza. Può colorare le urine di giallo intenso (innocuo). Importante per la funzione mitocondriale."
        ),
        IngredientEntity(
            normalizedName = "niacina",
            displayName = "Niacina (Vitamina B3)",
            rating = "GOOD",
            category = "VITAMIN",
            description = "Supporta il metabolismo energetico e la salute cardiovascolare.",
            details = "Dosi alte (>35mg) possono causare 'flushing' (rossore cutaneo). La niacinamide è una forma alternativa senza questo effetto. Efficace per i lipidi ematici ad alte dosi mediche."
        ),
        IngredientEntity(
            normalizedName = "acido folico",
            displayName = "Acido Folico (Vitamina B9)",
            rating = "EXCELLENT",
            category = "VITAMIN",
            description = "Fondamentale in gravidanza per prevenire difetti del tubo neurale.",
            details = "Raccomandato ufficialmente in gravidanza. Importante per la sintesi del DNA e la divisione cellulare. L-metilfolato è la forma bioattiva preferibile per chi ha varianti MTHFR."
        ),
        IngredientEntity(
            normalizedName = "folato",
            displayName = "Folato (5-MTHF)",
            rating = "EXCELLENT",
            category = "VITAMIN",
            description = "Forma bioattiva dell'acido folico, superiore per l'utilizzo cellulare.",
            details = "Più biodisponibile dell'acido folico sintetico. Importante per gravidanza e salute neurologica."
        ),
        IngredientEntity(
            normalizedName = "vitamina e",
            displayName = "Vitamina E (Tocoferolo)",
            rating = "GOOD",
            category = "VITAMIN",
            description = "Antiossidante liposolubile che protegge le membrane cellulari.",
            details = "L'alfa-tocoferolo è la forma più attiva. Dosi molto alte (>1000mg/die) possono interferire con la coagulazione. La forma naturale (d-alpha) è superiore alla sintetica (dl-alpha)."
        ),
        IngredientEntity(
            normalizedName = "vitamina k2",
            displayName = "Vitamina K2 (Menachinone)",
            rating = "EXCELLENT",
            category = "VITAMIN",
            description = "Dirige il calcio nelle ossa e protegge le arterie dalla calcificazione.",
            details = "Spesso carente nella dieta occidentale. Sinergistica con la Vitamina D3 per la salute ossea. MK-7 ha emivita più lunga di MK-4. Non interferisce con la coagulazione alle dosi standard."
        ),
        IngredientEntity(
            normalizedName = "vitamina a",
            displayName = "Vitamina A (Retinolo)",
            rating = "NEUTRAL",
            category = "VITAMIN",
            description = "Essenziale per la vista e il sistema immunitario.",
            details = "ATTENZIONE: Il retinolo preformato è teratogeno ad alte dosi. Dosi >3000 mcg/die possono essere tossiche. Preferire il betacarotene come precursore. Evitare in gravidanza ad alte dosi."
        ),
        IngredientEntity(
            normalizedName = "betacarotene",
            displayName = "Beta-carotene",
            rating = "GOOD",
            category = "VITAMIN",
            description = "Precursore della Vitamina A, antiossidante naturale.",
            details = "Il corpo converte il betacarotene in Vitamina A secondo necessità, evitando la tossicità. ATTENZIONE: alte dosi nei fumatori associate ad aumento del rischio di cancro ai polmoni."
        ),
        IngredientEntity(
            normalizedName = "biotina",
            displayName = "Biotina (Vitamina B7/H)",
            rating = "GOOD",
            category = "VITAMIN",
            description = "Supporta salute di capelli, unghie e pelle.",
            details = "Ottimo profilo di sicurezza. Alte dosi possono interferire con alcuni esami del sangue (falsi positivi/negativi). Avvertire il medico prima degli esami."
        ),
        IngredientEntity(
            normalizedName = "acido pantotenico",
            displayName = "Acido Pantotenico (Vitamina B5)",
            rating = "EXCELLENT",
            category = "VITAMIN",
            description = "Necessario per il metabolismo dei grassi e la produzione di ormoni.",
            details = "Nessuna tossicità nota. Importante per la sintesi del coenzima A e il metabolismo energetico."
        ),

        // ─── MINERALS ────────────────────────────────────────────────────────
        IngredientEntity(
            normalizedName = "magnesio",
            displayName = "Magnesio",
            rating = "EXCELLENT",
            category = "MINERAL",
            description = "Minerale coinvolto in oltre 300 reazioni enzimatiche.",
            details = "Carenza molto comune. Fondamentale per la funzione muscolare, nervosa e ossea. Il glicinato e il malato sono le forme con migliore biodisponibilità. L'ossido ha bassa biodisponibilità. Dosi eccessive causano effetto lassativo."
        ),
        IngredientEntity(
            normalizedName = "magnesio glicinato",
            displayName = "Magnesio Glicinato",
            rating = "EXCELLENT",
            category = "MINERAL",
            description = "Forma chelata di magnesio con eccellente biodisponibilità e tollerabilità.",
            details = "Ottima assorbibilità, non causa disturbi gastrici. Ideale per il supporto al sonno e alla riduzione dello stress. Forma preferita per la supplementazione."
        ),
        IngredientEntity(
            normalizedName = "calcio",
            displayName = "Calcio",
            rating = "GOOD",
            category = "MINERAL",
            description = "Minerale fondamentale per ossa, denti e funzione muscolare.",
            details = "Importante in donne in menopausa e anziani. Dosi eccessive (>2500mg/die) possono aumentare il rischio cardiovascolare. Il citrato è la forma più biodisponibile, soprattutto per chi ha acloridria."
        ),
        IngredientEntity(
            normalizedName = "zinco",
            displayName = "Zinco",
            rating = "EXCELLENT",
            category = "MINERAL",
            description = "Essenziale per immunità, guarigione delle ferite e fertilità maschile.",
            details = "Importante per oltre 300 enzimi. Dosi >40mg/die possono interferire con l'assorbimento del rame. Bisglizinato e acetato hanno migliore biodisponibilità dell'ossido."
        ),
        IngredientEntity(
            normalizedName = "ferro",
            displayName = "Ferro",
            rating = "NEUTRAL",
            category = "MINERAL",
            description = "Essenziale per il trasporto dell'ossigeno, ma da usare solo se carente.",
            details = "NON assumere senza una diagnosi di carenza (esami del sangue). Eccesso di ferro è ossidante e può causare danni. Donne in età fertile e vegetariani sono i soggetti più a rischio di carenza."
        ),
        IngredientEntity(
            normalizedName = "selenio",
            displayName = "Selenio",
            rating = "GOOD",
            category = "MINERAL",
            description = "Antiossidante, supporta tiroide e immunità.",
            details = "Finestra terapeutica stretta: la dose ottimale è 55-200 mcg/die. Dosi >400 mcg/die possono causare selenosi (tossicità). La selenometionina ha ottima biodisponibilità."
        ),
        IngredientEntity(
            normalizedName = "iodio",
            displayName = "Iodio",
            rating = "NEUTRAL",
            category = "MINERAL",
            description = "Essenziale per la funzione tiroidea.",
            details = "Sia la carenza che l'eccesso possono causare disfunzioni tiroidee. Supplementare solo in caso di carenza documentata. Chi ha patologie tiroidee deve consultare un medico."
        ),
        IngredientEntity(
            normalizedName = "cromo",
            displayName = "Cromo (Picolinato di Cromo)",
            rating = "NEUTRAL",
            category = "MINERAL",
            description = "Spesso promosso per il controllo della glicemia.",
            details = "Evidenze scientifiche limitate per l'uso negli integratori. Alcune preoccupazioni sul picolinato di cromo come potenziale genotossico ad alte dosi. Le prove per la gestione del peso sono deboli."
        ),

        // ─── AMINO ACIDS ─────────────────────────────────────────────────────
        IngredientEntity(
            normalizedName = "creatina",
            displayName = "Creatina Monoidrato",
            rating = "EXCELLENT",
            category = "AMINO_ACID",
            description = "Uno degli integratori sportivi più studiati e sicuri al mondo.",
            details = "Oltre 1000 studi clinici. Migliora forza, potenza e recupero muscolare. Completamente sicura nelle dosi raccomandate (3-5g/die). Può aumentare temporaneamente il peso corporeo (ritenzione d'acqua nei muscoli). Nessuna associazione con danni renali in soggetti sani."
        ),
        IngredientEntity(
            normalizedName = "l-glutammina",
            displayName = "L-Glutammina",
            rating = "GOOD",
            category = "AMINO_ACID",
            description = "Aminoacido condizionatamente essenziale, importante per l'intestino.",
            details = "Supporta la salute della mucosa intestinale e il sistema immunitario. Utile in periodi di stress intenso o malattia. Evidenze moderate per il recupero sportivo."
        ),
        IngredientEntity(
            normalizedName = "bcaa",
            displayName = "BCAA (Aminoacidi Ramificati)",
            rating = "GOOD",
            category = "AMINO_ACID",
            description = "Leucina, Isoleucina e Valina: supportano la sintesi proteica muscolare.",
            details = "Efficaci per ridurre il catabolismo muscolare e il dolore post-allenamento. Se si assume già proteina sufficiente, il beneficio aggiuntivo è limitato. Sicuri alle dosi raccomandate."
        ),
        IngredientEntity(
            normalizedName = "leucina",
            displayName = "L-Leucina",
            rating = "EXCELLENT",
            category = "AMINO_ACID",
            description = "L'aminoacido chiave per attivare la sintesi proteica muscolare (mTOR).",
            details = "Il più importante dei BCAA per la crescita muscolare. Necessario circa 2-3g per dose per stimolare la sintesi proteica. Sicuro e ben tollerato."
        ),
        IngredientEntity(
            normalizedName = "l-arginina",
            displayName = "L-Arginina",
            rating = "GOOD",
            category = "AMINO_ACID",
            description = "Precursore dell'ossido nitrico, supporta la circolazione sanguigna.",
            details = "Migliora il flusso sanguigno e la pompa muscolare. Evidenze più forti per la funzione cardiovascolare. Può riattivarsi il virus dell'herpes in soggetti sensibili. Può causare disturbi gastrici a dosi alte."
        ),
        IngredientEntity(
            normalizedName = "l-citrullina",
            displayName = "L-Citrullina",
            rating = "EXCELLENT",
            category = "AMINO_ACID",
            description = "Superiore all'arginina per aumentare i livelli di ossido nitrico.",
            details = "Più biodisponibile dell'arginina per aumentare il NO. Riduce l'affaticamento muscolare e migliora le performance. Dose efficace: 6-8g di citrullina o 8g di citrullina malato. Ottimo profilo di sicurezza."
        ),
        IngredientEntity(
            normalizedName = "beta-alanina",
            displayName = "Beta-Alanina",
            rating = "GOOD",
            category = "AMINO_ACID",
            description = "Aumenta la carnosina muscolare, ritardando l'affaticamento.",
            details = "Efficace per performance di endurance (1-4 minuti). Causa parestesia (formicolio cutaneo) inoffensiva ma fastidiosa a dosi singole alte. Dosi distribuite riducono questo effetto. Dose efficace: 3.2-6.4g/die."
        ),
        IngredientEntity(
            normalizedName = "l-carnitina",
            displayName = "L-Carnitina",
            rating = "NEUTRAL",
            category = "AMINO_ACID",
            description = "Trasporta gli acidi grassi nei mitocondri per la produzione di energia.",
            details = "Evidenze per la perdita di peso deboli in soggetti sani. Più efficace in soggetti con carenza (vegani, anziani). L'acetil-L-carnitina ha benefici cognitivi. Nota: alcuni studi associano la TMAO (metabolita) a rischio cardiovascolare, ma le prove non sono conclusive."
        ),
        IngredientEntity(
            normalizedName = "taurina",
            displayName = "Taurina",
            rating = "GOOD",
            category = "AMINO_ACID",
            description = "Aminoacido con proprietà antiossidanti e cardioprotettive.",
            details = "Presente naturalmente nel corpo. Supporta la funzione cardiaca, nervosa e muscolare. Sicura anche ad alte dosi. L'associazione con caffeina nelle energy drink è l'unica preoccupazione (per la caffeina, non la taurina)."
        ),
        IngredientEntity(
            normalizedName = "l-teanina",
            displayName = "L-Teanina",
            rating = "EXCELLENT",
            category = "AMINO_ACID",
            description = "Aminoacido presente nel tè verde, promuove calma e focus.",
            details = "Riduce ansia senza sedazione. Sinergico con la caffeina per focus e performance cognitiva. Ottimo profilo di sicurezza. Dose efficace: 100-200mg."
        ),

        // ─── HERBAL EXTRACTS ──────────────────────────────────────────────────
        IngredientEntity(
            normalizedName = "ashwagandha",
            displayName = "Ashwagandha (Withania somnifera)",
            rating = "EXCELLENT",
            category = "HERBAL_EXTRACT",
            description = "Adattogeno ayurvedico con forti prove per stress, ansia e testosterone.",
            details = "Numerosi RCT dimostrano riduzione del cortisolo, miglioramento dell'ansia e aumento del testosterone. KSM-66 e Sensoril sono gli estratti standardizzati meglio studiati. Controindicato in gravidanza e nelle malattie autoimmuni."
        ),
        IngredientEntity(
            normalizedName = "rhodiola rosea",
            displayName = "Rhodiola Rosea",
            rating = "EXCELLENT",
            category = "HERBAL_EXTRACT",
            description = "Adattogeno con prove per riduzione dell'affaticamento mentale e fisico.",
            details = "Evidenze buone per combattere la fatica da stress. Migliora la performance cognitiva e fisica. Estratto standardizzato in rosavine (3%) e salidroside (1%). Dose: 400-600mg/die."
        ),
        IngredientEntity(
            normalizedName = "curcumina",
            displayName = "Curcumina (da Curcuma)",
            rating = "EXCELLENT",
            category = "HERBAL_EXTRACT",
            description = "Potente anti-infiammatorio naturale con ampia evidenza scientifica.",
            details = "Scarsa biodisponibilità: necessaria formulazione con piperina, fitosomi (Meriva) o nanotecnologie. Anti-infiammatorio, antiossidante, supporta articolazioni e salute cognitiva. Sicura alle dosi raccomandate."
        ),
        IngredientEntity(
            normalizedName = "curcuma",
            displayName = "Curcuma",
            rating = "GOOD",
            category = "HERBAL_EXTRACT",
            description = "Spezia ricca di curcuminoidi con proprietà anti-infiammatorie.",
            details = "La curcuma in polvere contiene solo 2-5% di curcumina. Per benefici terapeutici, preferire estratti standardizzati di curcumina."
        ),
        IngredientEntity(
            normalizedName = "ginkgo biloba",
            displayName = "Ginkgo Biloba",
            rating = "GOOD",
            category = "HERBAL_EXTRACT",
            description = "Migliora la circolazione cerebrale e la memoria.",
            details = "Evidenze moderate per la funzione cognitiva, soprattutto in anziani. Estratto EGb 761 standardizzato. Può interagire con anticoagulanti. Raro rischio di mal di testa."
        ),
        IngredientEntity(
            normalizedName = "echinacea",
            displayName = "Echinacea",
            rating = "GOOD",
            category = "HERBAL_EXTRACT",
            description = "Erba con proprietà immunostimolanti, usata per prevenire i raffreddori.",
            details = "Prove moderate per ridurre la durata e la gravità dei raffreddori. Non raccomandato per uso continuo prolungato. Evitare in malattie autoimmuni."
        ),
        IngredientEntity(
            normalizedName = "piperina",
            displayName = "Piperina (BioPerine)",
            rating = "GOOD",
            category = "HERBAL_EXTRACT",
            description = "Estratto di pepe nero che aumenta l'assorbimento di altri nutrienti.",
            details = "Aumenta la biodisponibilità di curcumina, CoQ10 e molti altri composti. Può interagire con alcuni farmaci (inibisce CYP3A4). Sicura alle dosi standard (5-20mg)."
        ),
        IngredientEntity(
            normalizedName = "tribulus terrestris",
            displayName = "Tribulus Terrestris",
            rating = "CAUTION",
            category = "HERBAL_EXTRACT",
            description = "Erba popolare per la salute maschile, ma con prove deboli.",
            details = "Le evidenze scientifiche per l'aumento di testosterone e performance sessuali sono molto deboli o negative. Non è dimostrato che aumenti il testosterone negli umani. Promosso aggressivamente dal marketing."
        ),
        IngredientEntity(
            normalizedName = "efedrina",
            displayName = "Efedrina / Ma Huang",
            rating = "AVOID",
            category = "HERBAL_EXTRACT",
            description = "Stimolante con gravi rischi cardiovascolari, vietato in molti paesi.",
            details = "VIETATO come integratore negli USA (FDA, 2004) e in molti paesi. Associato a ictus, infarto e morte improvvisa. Evitare assolutamente."
        ),
        IngredientEntity(
            normalizedName = "yohimbina",
            displayName = "Yohimbina",
            rating = "CAUTION",
            category = "HERBAL_EXTRACT",
            description = "Stimolante alfa-2 antagonista promosso per la perdita di grasso.",
            details = "Alcune prove per la perdita di grasso localizzata, ma effetti collaterali significativi: ansia, tachicardia, ipertensione. Interagisce con antidepressivi. Dosi difficili da standardizzare nei prodotti. Usare con cautela."
        ),
        IngredientEntity(
            normalizedName = "valerian",
            displayName = "Valeriana",
            rating = "GOOD",
            category = "HERBAL_EXTRACT",
            description = "Erba tradizionale per migliorare la qualità del sonno.",
            details = "Prove moderate per migliorare la latenza del sonno. Sicura per uso a breve termine. Può causare sonnolenza diurna. Non interrompere bruscamente dopo uso prolungato."
        ),

        // ─── PROBIOTICS ───────────────────────────────────────────────────────
        IngredientEntity(
            normalizedName = "lactobacillus acidophilus",
            displayName = "Lactobacillus acidophilus",
            rating = "EXCELLENT",
            category = "PROBIOTIC",
            description = "Ceppo probiotico fondamentale per la salute intestinale.",
            details = "Ampiamente studiato per sindrome dell'intestino irritabile, diarrea da antibiotici e salute vaginale. Sicuro anche in gravidanza. Preferire prodotti con >10 miliardi UFC per dose."
        ),
        IngredientEntity(
            normalizedName = "bifidobacterium longum",
            displayName = "Bifidobacterium longum",
            rating = "EXCELLENT",
            category = "PROBIOTIC",
            description = "Ceppo probiotico con prove per salute intestinale e immunità.",
            details = "Uno dei probiotici più studiati. Efficace per IBS, stitichezza e riduzione dell'ansia. Sicuro per la popolazione generale."
        ),
        IngredientEntity(
            normalizedName = "saccharomyces boulardii",
            displayName = "Saccharomyces boulardii",
            rating = "EXCELLENT",
            category = "PROBIOTIC",
            description = "Probiotico a base di lievito, ottimo durante e dopo gli antibiotici.",
            details = "Il probiotico con le prove più forti per la diarrea associata agli antibiotici e per il Clostridioides difficile. Non viene eliminato dagli antibiotici. Sicuro per la maggior parte delle persone."
        ),

        // ─── FATTY ACIDS ──────────────────────────────────────────────────────
        IngredientEntity(
            normalizedName = "omega 3",
            displayName = "Omega-3 (EPA/DHA)",
            rating = "EXCELLENT",
            category = "FATTY_ACID",
            description = "Acidi grassi essenziali con prove robuste per cuore, cervello e infiammazione.",
            details = "Ampia evidenza scientifica per salute cardiovascolare, cognitiva e anti-infiammatoria. Dose efficace: 1-3g EPA+DHA/die. L'olio di pesce può essere contaminato da mercurio; preferire marchi certificati (IFOS). L'olio di alghe è la fonte vegana."
        ),
        IngredientEntity(
            normalizedName = "epa",
            displayName = "EPA (Acido Eicosapentaenoico)",
            rating = "EXCELLENT",
            category = "FATTY_ACID",
            description = "Omega-3 con proprietà anti-infiammatorie e cardioprotettive.",
            details = "Importante per ridurre l'infiammazione e i trigliceridi. Utile per depressione e salute cardiovascolare."
        ),
        IngredientEntity(
            normalizedName = "dha",
            displayName = "DHA (Acido Docosaesaenoico)",
            rating = "EXCELLENT",
            category = "FATTY_ACID",
            description = "Omega-3 fondamentale per il cervello e la vista.",
            details = "Componente strutturale del cervello e della retina. Fondamentale in gravidanza e allattamento per lo sviluppo neurale del bambino."
        ),
        IngredientEntity(
            normalizedName = "olio di pesce",
            displayName = "Olio di Pesce",
            rating = "EXCELLENT",
            category = "FATTY_ACID",
            description = "Fonte principale di Omega-3 EPA e DHA.",
            details = "Verificare la certificazione (IFOS, NSF) per escludere contaminanti. L'odore di pesce può causare reflusso; preferire capsule enteric-coated."
        ),
        IngredientEntity(
            normalizedName = "cla",
            displayName = "CLA (Acido Linoleico Coniugato)",
            rating = "CAUTION",
            category = "FATTY_ACID",
            description = "Acido grasso promosso per la perdita di grasso corporeo.",
            details = "Prove deboli per la perdita di grasso nell'uomo nonostante promesse. Alcuni studi segnalano effetti negativi sulla resistenza all'insulina. L'eccesso può aumentare l'infiammazione in alcuni soggetti."
        ),

        // ─── ANTIOXIDANTS & OTHER ─────────────────────────────────────────────
        IngredientEntity(
            normalizedName = "coq10",
            displayName = "Coenzima Q10 (CoQ10/Ubiquinolo)",
            rating = "EXCELLENT",
            category = "ANTIOXIDANT",
            description = "Antiossidante essenziale per la produzione di energia mitocondriale.",
            details = "Indicato per chi assume statine (che riducono il CoQ10). L'ubiquinolo è la forma ridotta e più biodisponibile. Supporta la salute cardiovascolare e riduce l'affaticamento. Sicuro a lungo termine."
        ),
        IngredientEntity(
            normalizedName = "resveratrolo",
            displayName = "Resveratrolo",
            rating = "NEUTRAL",
            category = "ANTIOXIDANT",
            description = "Antiossidante del vino rosso, popolare ma con prove umane limitate.",
            details = "I risultati negli studi in vitro e animali sono molto promettenti, ma gli studi nell'uomo sono deludenti per la scarsa biodisponibilità. I benefici negli integratori standard non sono dimostrati solidamente."
        ),
        IngredientEntity(
            normalizedName = "acido alfa lipoico",
            displayName = "Acido Alfa-Lipoico (ALA)",
            rating = "GOOD",
            category = "ANTIOXIDANT",
            description = "Antiossidante universale, solubile sia in acqua che nei grassi.",
            details = "Prove per neuropatia diabetica e controllo glicemico. Antiossidante versatile. Può ridurre i livelli di tiamina. Non confondere con l'acido alfa-linolenico (diverso composto)."
        ),
        IngredientEntity(
            normalizedName = "glutatione",
            displayName = "Glutatione",
            rating = "NEUTRAL",
            category = "ANTIOXIDANT",
            description = "Il principale antiossidante del corpo, ma bassa biodisponibilità orale.",
            details = "La forma orale tradizionale è ampiamente degradata nell'intestino. La forma liposomiale o S-acetil-glutatione hanno migliore assorbimento. Preferire N-Acetil-Cisteina (NAC) come precursore."
        ),
        IngredientEntity(
            normalizedName = "nac",
            displayName = "N-Acetil-Cisteina (NAC)",
            rating = "EXCELLENT",
            category = "ANTIOXIDANT",
            description = "Precursore del glutatione con prove robuste per molte condizioni.",
            details = "Aumenta il glutatione endogeno. Prove per danno epatico da paracetamolo (uso medico), salute polmonare, OCD e dipendenze. Attenzione allo stato regolatorio (in USA non è più OTC in alcuni contesti)."
        ),
        IngredientEntity(
            normalizedName = "melatonina",
            displayName = "Melatonina",
            rating = "EXCELLENT",
            category = "OTHER",
            description = "Ormone del sonno, efficace per jet lag e disturbi del ritmo circadiano.",
            details = "Dosi basse (0.5-1mg) sono efficaci quanto dosi alte (5-10mg). Usare 30-60 minuti prima di dormire. Sicura per uso a breve termine. In Italia è un farmaco SOP. Dosi alte potrebbero desensibilizzare i recettori."
        ),
        IngredientEntity(
            normalizedName = "caffeina",
            displayName = "Caffeina",
            rating = "GOOD",
            category = "OTHER",
            description = "Il più popolare stimolante al mondo, con prove solide per performance.",
            details = "Migliora forza, endurance e funzione cognitiva. Dose efficace: 3-6mg/kg di peso corporeo. Tolleranza si sviluppa rapidamente. Evitare nel pomeriggio/sera per non disturbare il sonno. Attenzione in individui sensibili (ansia, tachicardia)."
        ),

        // ─── ADDITIVES ────────────────────────────────────────────────────────
        IngredientEntity(
            normalizedName = "aspartame",
            displayName = "Aspartame (E951)",
            rating = "NEUTRAL",
            category = "SWEETENER",
            description = "Dolcificante artificiale ampiamente usato, sicuro secondo EFSA/FDA.",
            details = "Rivalutato sicuro da EFSA (2023) e FDA. Da evitare nella fenilchetonuria (PKU) - contiene fenilalanina. Alcuni consumatori riportano sensibilità (mal di testa), ma non dimostrata negli studi. Precedente classificazione IARC come 'possibilmente cancerogeno' non implica rischio reale."
        ),
        IngredientEntity(
            normalizedName = "sucralosio",
            displayName = "Sucralosio (E955)",
            rating = "NEUTRAL",
            category = "SWEETENER",
            description = "Dolcificante artificiale stabile al calore.",
            details = "Generalmente considerato sicuro. Alcuni studi recenti suggeriscono possibili effetti sul microbioma intestinale, ma le prove sono ancora preliminari. Non calorico."
        ),
        IngredientEntity(
            normalizedName = "stevia",
            displayName = "Stevia (Steviosidi)",
            rating = "GOOD",
            category = "SWEETENER",
            description = "Dolcificante naturale estratto dalla pianta Stevia rebaudiana.",
            details = "Approvato da EFSA come sicuro. Zero calorie. Nessun effetto sul microbioma nelle prove attuali. Può avere retrogusto amaro ad alte concentrazioni."
        ),
        IngredientEntity(
            normalizedName = "biossido di titanio",
            displayName = "Biossido di Titanio (E171)",
            rating = "AVOID",
            category = "ADDITIVE",
            description = "Colorante bianco VIETATO in Europa da 2022 come additivo alimentare.",
            details = "EFSA (2021) ha concluso che non può essere considerato sicuro come additivo alimentare. Vietato nell'UE da agosto 2022. Ancora usato in alcuni prodotti fuori dall'UE. Da evitare."
        ),
        IngredientEntity(
            normalizedName = "stearato di magnesio",
            displayName = "Stearato di Magnesio",
            rating = "NEUTRAL",
            category = "ADDITIVE",
            description = "Lubrificante usato nella produzione di capsule e compresse.",
            details = "Comunemente usato nella produzione farmaceutica. Alcune preoccupazioni sulla riduzione dell'assorbimento di nutrienti non sono state confermate agli umani. Sicuro alle dosi negli integratori."
        ),
        IngredientEntity(
            normalizedName = "gelatina",
            displayName = "Gelatina (capsula)",
            rating = "NEUTRAL",
            category = "ADDITIVE",
            description = "Materiale animale usato per le capsule, non adatto a vegani.",
            details = "Derivato bovino o suino. Non presenta rischi per la salute. Non adatto a vegani, vegetariani o chi segue diete religiose specifiche. Alternativa: capsule HPMC (vegetali)."
        ),
        IngredientEntity(
            normalizedName = "idrossipropilmetilcellulosa",
            displayName = "HPMC (capsula vegetale)",
            rating = "EXCELLENT",
            category = "ADDITIVE",
            description = "Materiale vegetale per capsule, adatto a tutti.",
            details = "Cellulose di origine vegetale. Sicura per vegani, vegetariani e chi segue diete religiose. Ottimo profilo di sicurezza."
        )
    )
}
