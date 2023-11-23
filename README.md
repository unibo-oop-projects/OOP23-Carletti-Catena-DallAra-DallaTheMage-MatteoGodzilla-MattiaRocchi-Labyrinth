Labyrinth è un gioco scritto in java dove più giocatori esplorano un labirinto.

# OBIETTIVI DELLA CALL
- [x] DEFINIZIONE E FINALIZZAZIONE CARATTERE DEL GIOCO (STILE e MECCANICHE).
- INDIVIDUAZIONE E DIVISIONE DEL DOMINIO.
- [x] GRAFICAZIONE BOZZA DIAGRAMMA UML.
- CONSEGUENTE RIMOZIONE SURPLUS.
- SCELTA ARCHITETTURALE.
- PROPOSTE SUI PATTERN DA UTILIZZARE.
- DEFINIRE QUALI SARANNO LE FUNZIONALITA' OBBLIGATORIE.
- CONSEGUENTE DEFINIZIONE DELLE FUNZIONALITA' OPZIONALI.

# Macro componenti
+ Inizializzazione (dalla)
    - Menu Iniziale per configurare il labirinto
    - Loader basato sulla configurazione (inizializzazione labirinto e obiettivi)
+ Rendering del gioco (parte grafica) (super rocchi)
+ Gestione Mondo (matteo)
    - Gestione del Labirinto (ovvero shift delle tessere)
    - Gestione delle sorgenti
+ Gestione input (carletto)
    - Gestione turni dei giocatori
    - Gestione movi
    - Gestione dadi movimento
+ Sistema obiettivi (matteo)
    - Bacheca

# Inizializzazione
## Numero di giocatori?
Da 2 a 4.
## Il gioco è solo su un client oppure aggiungiamo il supporto per più client che si connettono assieme?
La base deve funzionare su un singolo client. TUTTO DEVE ESSERE VISIBILE AGLI ALTRI GIOCATORI
## La difficoltà di gioco è statica oppure si può regolare?
La difficoltà viene regolata dalla difficoltà degli obiettivi da fare. Gli obiettivi saranno divisi in facili, medio e difficili, e nella bacheca la maggior parte degli obiettivi sono della difficoltà stabilita a inizio partita. Il numero di obiettivi è variabile
## Le dimensioni del labirinto devono essere fisse oppure no?
Le dimensioni possono essere scelte dal giocatore, ma in generale dovrebbe essere piccolo in modo da favorire la percorribilità

# Regole di gioco
All'inizio della partita ci sono un numero stabilito di oggetti (con i loro materiali per crearli) comune a tutti.
L'unico modo per ottenere punti è creare questi oggetti e una volta che un oggetto viene creato, gli altri giocatori non possono più crearlo.
La componente di strategia sta nella scelta di quali oggetti craftare, per cui il giocatore può scegliere un obiettivo facile ma che dà poco, oppure un obiettivo difficile ma con un gran reward di punteggio.
Quando le tessere vengono scoperte, queste possono contenere materiali di tipologia e quantità random. Ci sono delle sorgenti fisse nella mappa che danno una singola tipologia di materiale
Gli obiettivi sono nascosti fino a che un giocatore non entra nella bacheca, poi rimangono sempre visibili.
Il primo giocatore che arriva alla bacheca riceve un reward a scelta tra punti grezzi oppure materiali, così che il primo giocatore possa scegliere cosa conviene in quel momento.

## Durata del gioco?
target è di ~1h, finisce quando un giocatore raggiunge un threshold di punteggio **(basato su vari fattori).** La soglia deve essere decisamente inferiore alla somma totale dei punteggi possibili che si possono fare nella partita.
DA TESTARE A MANO
Il gioco darà comunque una stima del tempo di gioco in base alla quantità e difficoltà degli obiettivi scelti nella configurazione della partita
* Variante: vedere quanti punti possono fare gli altri giocatori e decidere la vittoria nel caso sia forzata
* Variante: finire quanto tutti gli obiettivi sono stati fatti

## Quanti obiettivi attivi può un giocatore avere in un momento qualsiasi della partita?
Un giocatore per poter craftare/eseguire un obiettivo lo deve prima trattenere dalla bacheca, poi può anche riscattarlo nello stesso turno.
Una volta che un obiettivo è stato trattenuto, gli altri giocatori non possono completare quella quest, neache se hanno tutti i requisiti necessari

## Come sono strutturati i turni?
Il giocatore si può spostare di una quantità di tessere >= 1
## I turni hanno limiti di tempo / altri limiti intrinsechi ?
Limiti di tempo nel turno, ma molto lunghe (>5 minuti)
## Ci sono fasi diverse di gioco?
Non ci sono fasi esplicite dettate dal gioco, ma sono dettati dal giocatore stesso in base a ciò che gli conviene nell'istante
## C'è una componente risk/reward?
* Ogni obiettivo ha un punteggio associato in base a quando è difficile realizzarlo
* Alla fine della partita i materiali non utilizzati vengono contati come penalità

## Quanti materiali danno la sorgente fissa / come funzionano?
Le sorgenti hanno un massimo di materiali pre-stabilito di quanti ne possono dare. Le sorgenti non si ricaricano immediatamente ma in un numero tot di turni.
Se un altro giocatore raggiunge la sorgente quando non è completamente carica, ottiene parte dei materiali in base allo stato di ricarica della sorgente.
Il turno di ricarica della sorgente viene fatto dopo i turni dei giocatori, partendo dal giocatore che per primo ha ottenuto i materiali.

## Quante sorgenti per materiali ci sono?
* dipende da un fattore costante moltiplicato per l'area del labirinto
* La disposizione delle sorgenti dovrebbe essere simmetrica radialmente nel labirinto

## le tessere del labirinto sono spostabili?
Secondo le regole standard del gioco da tavolo, ma con leggere differenze:
    * non esiste la tessera aggiuntiva, ma si fa semplicemente lo shift delle  tessere con wrap around. Se il giocatore si trova nel bordo, viene spostato assieme alla tessera
    * le tessere spostabili nascondono il loro stato (direzioni camminabili, materiali aggiuntivi contenuti) fino a che un giocatore non ci va vicino
le tessere devono avere 4 direzioni cardinali con 4 muri distinti
il giocatore può ruotare una delle 8 caselle adiancenti prima/dopo dello spostamento

# Rendering
Due visioni della mappa: una globale dove si vede tutto con poco dettaglio e una locale al giocatore dove si vedono meglio le tessere con i loro effetti (area da decidere) Con un bottone per cambiare tra le due
Ogni tessera è divisa in 9 parti, ognuna delle quali può mostrare esclusivamente muro o sentiero.
## Con quale tecnologia verrà visualizzato?
JavaFX basato su canvas e non su elementi gui
## Stile grafico
DA DECIDERE

# Input
## Giocabile solo con tastiera e mouse oppure anche supporto a controller?
Solo tastiera
## Ci sono requisiti particolari ?
No, basiamo i controlli su lettere, frecce direzionali, enter e escape

# Funzionalità opzionali
Aggiungere networking per collegare più client assieme
Temi diversi per il labirinto
