package game2;

import java.util.ArrayList;
import javafx.scene.Node;

public class Board {

    Cell[][] board = new Cell[10][10]; //поле ячеек
    protected int[] shipAmount; //количество кораблей каждой величины
    protected ArrayList<Ship> ships; //корабли
    protected Node node; //ссылка на форму FX

    public Board() {
        shipAmount = new int[4];
        ships = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
    }

    public Board(Node node) {
        shipAmount = new int[4];
        ships = new ArrayList<>(10);
        this.node = node;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
    }

    //количество кораблей на поле (и живых и мертвых
    public int getShipAmount() {
        return ships.size();
    }

    //выстрел по полю
    public void shootCell(int y, int x) {
        Cell cell = board[y][x];
        // если ячейка не проверена
        if (!cell.isStriked()) {
            cell.shootCell();
        } // если ячейка проверена
        else {
            return;
        }
    }

    //очистка ячейки
    public void clearCell(int y, int x) {
        Cell cell = board[y][x];
        cell.clear();
    }

    //проверка присутствия корабля в ячейке cell
    public Ship getShip(Cell cell) {
        for (Ship ship : ships) {
            for (Cell shipCell : ship.getShipPlacement()) {
                if (shipCell.getX() == cell.getX() && shipCell.getY() == cell.getY()) {
                    return ship;
                }
            }
        }
        return null;
    }

    //проверка можно ли поставить корабль ship на текущее поле
    public boolean checkShipPlacement(Ship ship) {
        int i = 0;
        for (Cell shipCell : ship.getShipPlacement()) {
            if (!shipCell.isAccessible()) {
                return false;
            }
        }
        return true;
    }

    //проверка 
    public boolean isAnyShipAlive() {
        for (Ship ship : ships) {
            if (!ship.isDead()) {
                return true;
            }
        }
        return false;
    }

    public double getWinProbability() {
        double P;
        double emptyCellsAmount = 0;
        double shipCellsAmount = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!board[i][j].isStriked()) {
                    emptyCellsAmount++;
                }
            }
        }
        for (Ship ship : ships) {
            if (!ship.isDead()) {
                
                shipCellsAmount += ((1/ship.getSize()) * 12);
            }
        }
        P = (100-shipCellsAmount) / emptyCellsAmount;
        return P;
    }
    
    public void resetBoard() {
        ships.clear();
        shipAmount = new int[4];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
    }
}
