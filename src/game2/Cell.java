package game2;

public class Cell {

    private boolean ship; //есть корабль
    private boolean striked; //проверена ячейка?
    private boolean accessible; //доступность ячейки
    private int y;
    private int x;

    public Cell(int y, int x) {
        ship = false;
        striked = false;
        accessible = true;
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isShip() {
        return ship;
    }

    public boolean isStriked() {
        return striked;
    }

    public void placeShip() {
        ship = true;
    }
    
    public void shoot() {
        striked = true;
    }

    public void shootCell() {
        this.striked = true;
        this.accessible = false;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setNotAccessible() {
        this.accessible = false;
    }
    
    public void clear() {
        this.striked = false;
        this.ship = false;
        this.accessible = false;
    }
    

}
