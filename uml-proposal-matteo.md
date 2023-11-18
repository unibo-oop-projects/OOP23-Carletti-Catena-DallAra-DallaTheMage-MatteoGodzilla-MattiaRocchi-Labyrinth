# UML
[Link alla documentazione](https://mermaid.js.org/syntax/classDiagram.html)
```mermaid
classDiagram
    AbstractTile <|-- NormalTile
    AbstractTile <|-- MaterialTile

    Objective *-- Requirement
    Requirement *-- Material

    Game .. GameConfig
    Loader .. GameConfig
    Menu .. GameConfig

    World *-- Player
    World *-- Objective
    World *-- AbstractTile
    Player *-- Material

    class AbstractTile{
        <<Abstract>>
        openAbove: boolean
        openBelow: boolean
        openRight: boolean
        openLeft:  boolean

        # void onStep()
        # void onReveal()
    }

    class NormalTile{
        + void onStep()
        + void onReveal()
    }

    class MaterialTile{
        materialType: Material
        materialCount: int
    }

    class Player{
        resources: Map~Material-int~
    }

    class Objective{
        name: String
        icon: ImageTBD
        dependsOn: List~Requirement~
        + isFulfilled() boolean
    }

    class Requirement {
        material:Material
        materialCount:int
    }

    class Material{
        <<Enumeration>>
        WOOD
        IRON
        etc
    }


    class GameConfig{
        width: int
        height: int
    }


    class Game{
        + createGame(GameConfig config) void
        + runGameLoop()
    }


    class Menu{

    }


    class World{
        matrix: List~List~AbstractTile~~
        players: List~Player~
        Objectives: List~Objective~
    }


    class Loader{

    }


    class Input{

    }
```

# Note
* `GameConfig`: Struct per contenere i parametri per creare il labirinto
* `Game`: Gestisce il loop principale di gioco
* `World`: Contiene tutto lo stato del labirinto
* `Loader`: Responsabile del caricamento delle risorse da file
* `Input`: Legge gli input da tastiera per il gioco
* `Menu`: Gestisce tutto il menu iniziale del gioco
* Le classi che non sono collegate con World sono volutamente poco descritte in modo da lasciare la maggior libertà durante lo sviluppo. Quello che è descritto nel grafico ritengo sia il minimo necessario per iniziare a sviluppare il gioco
