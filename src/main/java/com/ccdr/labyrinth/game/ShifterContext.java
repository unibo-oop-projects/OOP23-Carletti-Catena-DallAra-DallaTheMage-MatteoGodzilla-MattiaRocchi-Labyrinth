package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.Coordinate;

import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;

public class ShifterContext implements Context {
    private final Board board;
    private Set<Coordinate> selected = new HashSet<>();
    private int selectedRow;
    private int selectedColumn;
    private int indexSelected;
    private boolean done;
    private boolean isRow;

    public ShifterContext(Board board, Set<Coordinate> selected) {
        this.board = board;
        this.selected = selected;
        this.selected.clear();
        this.selectRow(selectedRow);
        this.isRow = true;
    }
    
    private void selectRow(int i) {
        isRow = true;
        selected.clear();
        indexSelected = i;
        for(Coordinate c : this.board.getMap().keySet()) {
            if(c.row() == i) {
                selected.add(c);
            }
        } 
    }

    private void selectColumn(int i) {
        isRow = false;
        selected.clear();
        indexSelected = i;
        for(Coordinate c : this.board.getMap().keySet()) {
            if(c.column() == i) {
                selected.add(c);
            }
        } 
    }

    private int calculateCorrectIndex(Function<Integer, Integer> rule, int i) {    
        return rule.apply(i);
    }

    @Override
    public void up() {
        do {
            this.selectRow(this.calculateCorrectIndex((index) -> index < 0 ? this.board.getHeight()-1 : index, --this.selectedRow));
        } while (this.board.getBlockedRows().contains(selectedRow));
    }

    @Override
    public void down() {
        do {
            this.selectRow(this.calculateCorrectIndex((index) -> index > this.board.getHeight()-1 ? 0 : index, ++this.selectedRow));  
        } while (this.board.getBlockedRows().contains(selectedRow));
    }

    @Override
    public void left() {
        do {
            this.selectColumn(this.calculateCorrectIndex((index) -> index < 0 ?  this.board.getWidth()-1 : index, --this.selectedColumn));   
        } while (this.board.getBlockedColumns().contains(selectedColumn));
    }

    @Override
    public void right() {
        do {
            this.selectColumn(this.calculateCorrectIndex((index) -> index > this.board.getWidth()-1 ? 0  : index, ++this.selectedColumn));   
        } while (this.board.getBlockedColumns().contains(selectedColumn));
    }

    @Override
    public void primary() {
        final boolean FORWARD = true;
        this.done = true;
        if(isRow) {
            this.board.shiftRow(this.indexSelected, FORWARD);
        } else {
            this.board.shiftColumn(this.indexSelected, FORWARD);
        }   
    }

    @Override
    public void secondary() { }

    @Override
    public void back() {
        final boolean FORWARD = false;
        this.done = true;
        if(isRow) {
            this.board.shiftRow(this.indexSelected, FORWARD);
        } else {
            this.board.shiftColumn(this.indexSelected, FORWARD);
        }       
    }

    @Override
    public boolean done() {
        return this.done;
    }

    @Override
    public Context getNextContext() {
        // TODO: FOR NOW DO NOTHING
        return this;    
    }

}
