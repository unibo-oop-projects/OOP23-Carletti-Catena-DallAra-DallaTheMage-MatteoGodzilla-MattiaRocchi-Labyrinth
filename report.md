# Labyrinth

## 1. Analisi
### 1.1 Requisiti
### 1.2 Analisi e modello del dominio

## 2. Design
### 2.1 Architettura
### 2.2 Design Dettagliato
#### 2.2.1 Carletti Lorenzo
#### 2.2.2 Catena Matteo
- **Problema:** Il game loop principale responsabile per l'esecuzione del codice deve poter eseguire in momenti separati la classe comtroller del menu, la classe controller del gameplay e quella controller dei risultati.
La classe che gestisce il game loop principale non dovrebbe definire al suo interno quali classi vengono eseguite, perchè ciò limita notevolmente la riusabilità del codice.
Inoltre lo scambio tra i controller dovrebbe essere possibile anche se le classi non hanno riferimenti diretti agli altri controller che devono essere eseguiti.
**Soluzione:** Design della classe `Engine` e dell'interfaccia `Executor`.
Il game loop principale è contenuto internamente nella classe `Engine`, che permette successivamente di eseguire i metodi definiti dall'interfaccia `Executor`.
La classe `Engine` può eseguire un solo oggetto `Executor` alla volta, ma attraverso il metodo `changeExecutor` si può ordinare alla classe `Engine` di cambiare *quale* oggetto `Executor` deve essere eseguito da quel momento.

- **Problema:** Quando il controller che deve essere eseguito cambia, questo richiede anche un trasferimento di informazioni dalla precedente alla successiva, come per esempio la configurazione del gioco dal menu al controller del gioco.
**Soluzione:** è stato utilizzato il pattern observer, in modo tale percui il trasferimento delle informazioni viene fatto attraverso funzioni lambda.
In questo modo le classi controller hanno codice concettualmente separato, senza nessun riferimento esterno, rendendo possibile fare uno scambio tra i controller attivi in modo circolare.

#### 2.2.3 Dall'Ara Lorenzo
#### 2.2.4 Rocchi Mattia

## 3. Sviluppo
### 3.1 Testing automatico
### 3.2 Note di sviluppo
- Utilizzo di lambda functions, per esempio nel collegamento tra i diversi controller
Permalink:
- Utilizzo di stream sulle collezioni
Permalink:
- Utilizzo di JavaFX per la gestione della parte visuale e della ricezione degli input del giocatore.
Permalink:

## 4. Commenti finali
### 4.1 Autovalutazione e lavori futuri

## Appendice A: Guida utente

## Appendice B: Esercitazioni di laboratorio
### B.01 lorenzo.carletti3@studio.unibo.it
- Laboratorio 07:
- Laboratorio 08:
- Laboratorio 09:
- Laboratorio 10:
- Laboratorio 11:
### B.02 matteo.catena3@studio.unibo.it
- Laboratorio 07: https://virtuale.unibo.it/mod/forum/discuss.php?d=147598#p209276
- Laboratorio 08: https://virtuale.unibo.it/mod/forum/discuss.php?d=148025#p209762
- Laboratorio 09: https://virtuale.unibo.it/mod/forum/discuss.php?d=149231#p211482
- Laboratorio 10: https://virtuale.unibo.it/mod/forum/discuss.php?d=150252#p212700
- Laboratorio 11: https://virtuale.unibo.it/mod/forum/discuss.php?d=151542#p213921


