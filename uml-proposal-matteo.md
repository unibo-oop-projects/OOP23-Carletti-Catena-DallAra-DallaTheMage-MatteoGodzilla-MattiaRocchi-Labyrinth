# UML
Gestione mondo + sistema obiettivi
[Link alla documentazione](https://mermaid.js.org/syntax/classDiagram.html)

```mermaid
classDiagram
    World *-- Player
    World *-- AbstractTile
    World *-- Objective

    AbstractTile <|-- NormalTile
    AbstractTile <|-- OneTimeMaterialTile
    AbstractTile <|-- MaterialSourceTile

    Objective *-- Requirement
    Requirement <|-- MaterialRequirement

    MaterialRequirement -- Material
    OneTimeMaterialTile -- Material
    MaterialSourceTile -- Material
    Player -- Material

    class World{
        width: int
        height: int
        matrix: List~List~AbstractTiles~~
        objectives: List~Objective~
        players: List~Player~
        sources: List~MaterialSourceTile~

        + canShiftColumn(int column): boolean
        + canShiftRow(int row): boolean
        + shiftRowRight(int row): void
        + shiftRowLeft(int row): void
        + shiftColumnUpwards(int row): void
        + shiftColumnDownwards(int row): void
        + rotateTileClockwise(int row, int column): void
        + rotateTileCounterClockwise(int row, int column): void
        + getObjective(int index): Objective
        + assignObjective(Objective o, Player p): void
        + redeemObjective(Objective o): void

    }

    class AbstractTile{
        <<Abstract>>
        openAbove: boolean
        openBelow: boolean
        openRight: boolean
        openLeft:  boolean
        revealed: boolean

        # onStep(): void
        # onReveal(): void
    }

    class NormalTile{
        + onStep(): void
        + onReveal(): void
    }

    class OneTimeMaterialTile{
        materialType: Material
        materialCount: int
        + onStep(): void
    }

    class MaterialSourceTile{
        materialType: Material
        materialCount: int
        + onStep(): void
        + onTurnRefresh(): void
    }

    class Objective{
        name: String
        icon: Image?
        dependsOn: List~Requirement~
        fulfilledBy: Optional~Player~
        + isFulfilled(): boolean
        + isAssigned(): boolean
    }

    class Requirement {
        <<Interface>>
        + isFulfilled(Player p): boolean
    }

    class MaterialRequirement {
        material:Material
        materialCount:int
        + isFulfilled(Player p): boolean
    }

    class Material{
        <<Enumeration>>
        WOOD
        IRON
        etc
    }

    class Player{
        row: int
        column: int
        points: int
        inventory: Map~Material~Int~
    }
```

## Note
* `World` è l'unico luogo dove oggetti di tipo `Objective` devono essere effettivamente contenuti, nelle altre posizioni ci deve essere sempre esclusivamente un riferimento