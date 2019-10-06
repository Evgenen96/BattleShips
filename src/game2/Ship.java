package game2;


import static game2.OwnBoard.idMaker;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class Ship {

    private int x;
    private int y;
    private int shipSize;
    private int direction;
    private boolean dead; //корабль потоплен
    private ArrayList<Cell> shipPlacement; //ячейки, которые занимает корабль

    public Ship(int y, int x, int shipSize, int direction) {
        this.x = x;
        this.y = y;
        this.dead = false;
        this.shipSize = shipSize;
        this.direction = direction;
        shipPlacement = new ArrayList<Cell>();
    }

    public Ship(Ship ship) {
        this.x = ship.x;
        this.y = ship.y;
        this.dead = ship.dead;
        this.shipSize = ship.shipSize;
        this.direction = ship.direction;
        shipPlacement = ship.shipPlacement;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead() {
        this.dead = true;
    }

    public int getSize() {
        return shipSize;
    }

    public int getDirection() {
        return direction;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setShipSize(int shipSize) {
        this.shipSize = shipSize;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public ArrayList<Cell> getShipPlacement() {
        return shipPlacement;
    }

    public void addCellToShip(Cell cell) {
        this.shipPlacement.add(cell);
    }

    //FX корректная отрисовка корабля во время построения поля
    public boolean paint(Node node) {
        String fxID;
        String[] pics = new String[7];
        if (isDead()) {
            pics[0] = "img/squareD.png";
            pics[1] = "img/upSqD.png";
            pics[2] = "img/downSqD.png";
            pics[3] = "img/rightSqD.png";
            pics[4] = "img/leftSqD.png";
            pics[5] = "img/vSquareD.png";
            pics[6] = "img/hSquareD.png";
        } else {
            pics[0] = "img/square.png";
            pics[1] = "img/upSq.png";
            pics[2] = "img/downSq.png";
            pics[3] = "img/rightSq.png";
            pics[4] = "img/leftSq.png";
            pics[5] = "img/vSquare.png";
            pics[6] = "img/hSquare.png";
        }
        switch (direction) {
            // вверх
            case 0: {
                for (int k = getY(); k > getY() - getSize(); k--) {
                    if (getY() - getSize() + 1 <= -1) {
                        return false;
                    }
                    fxID = idMaker(k, getX());
                    if (getY() - k == 0) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[0]));
                    } else if (getY() - k == 1) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[1]));
                        fxID = idMaker(k + 1, getX());
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[2]));
                    } else if (getY() - k > 1) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[1]));
                        fxID = idMaker(k + 1, getX());
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[5]));
                    }
                }
                break;

            }
            //вправо
            case 1: {
                for (int k = getX(); k < getX() + getSize(); k++) {
                    if (getX() + getSize() > 10) {
                        return false;
                    }
                    fxID = idMaker(getY(), k);
                    if (k - getX() == 0) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[0]));
                    } else if (k - getX() == 1) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[3]));
                        fxID = idMaker(getY(), k - 1);
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[4]));
                    } else if (k - getX() > 1) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[3]));
                        fxID = idMaker(getY(), k - 1);
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[6]));
                    }
                }
                break;

            }
            //вниз
            case 2: {
                for (int k = getY(); k < getY() + getSize(); k++) {
                    if (getY() + getSize() > 10) {
                        return false;
                    }
                    fxID = idMaker(k, getX());
                    if (k - getY() == 0) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[0]));
                    } else if (k - getY() == 1) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[2]));
                        fxID = idMaker(k - 1, getX());
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[1]));
                    } else if (k - getY() > 1) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[2]));
                        fxID = idMaker(k - 1, getX());
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[5]));
                    }
                }
                break;

            }
            //влево
            case 3: {
                for (int k = getX(); k > getX() - getSize(); k--) {
                    if (getX() - getSize() + 1 <= -1) {
                        return false;
                    }
                    fxID = idMaker(getY(), k);
                    if (getX() - k == 0) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[0]));
                    } else if (getX() - k == 1) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[4]));
                        fxID = idMaker(getY(), k + 1);
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[3]));
                    } else if (getX() - k > 1) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[4]));
                        fxID = idMaker(getY(), k + 1);
                        ((ImageView) node.lookup(fxID)).setImage(new Image(pics[6]));
                    }
                }
                break;

            }

        }
        return true;
    }

    //список недоступных ячеек рядом с кораблем
    public ArrayList<Cell> getShipEnviroment(Board board, boolean temp) {
        shipPlacement = new ArrayList<Cell>();
        ArrayList<Cell> shipEnvCells = new ArrayList<Cell>();
        switch (direction) {
            // вверх
            case 0: {
                for (int i = y + 1; i > y - shipSize - 1; i--) {
                    for (int j = x - 1; j < x + 2; j++) {
                        if (i >= 0) {
                            //если там корабль         
                            if ((j == x) && (i < y + 1 && i > y - shipSize)) {
                                shipPlacement.add(board.board[i][j]);
                                if (!temp) {
                                    board.board[i][j].placeShip();
                                    board.board[i][j].setNotAccessible();
                                }
                            } else {
                                if (!checkRange(i, j)) {
                                    if(!temp) {
                                        board.board[i][j].setNotAccessible();
                                    }
                                    shipEnvCells.add(board.board[i][j]);
                                }
                            }
                        }
                    }
                }
                break;
            }
            //вправо
            case 1: {
                for (int i = y - 1; i < y + 2; i++) {
                    for (int j = x - 1; j < x + shipSize + 1; j++) {
                        //если там корабль
                        if (j < 10) {
                            if ((i == y) && (j > x - 1 && j < x + shipSize)) {
                                shipPlacement.add(board.board[i][j]);
                                if (!temp) {
                                    board.board[i][j].placeShip();
                                    board.board[i][j].setNotAccessible();
                                }
                            } else {
                                if (!checkRange(i, j)) {
                                    if(!temp) {
                                        board.board[i][j].setNotAccessible();
                                    }
                                    shipEnvCells.add(board.board[i][j]);
                                }
                            }
                        }
                    }
                }
                break;
            }
            //вниз
            case 2: {
                for (int i = y - 1; i < y + shipSize + 1; i++) {
                    for (int j = x - 1; j < x + 2; j++) {
                        //если там корабль
                        if (i < 10) {
                            if ((j == x) && (i > y - 1 && i < y + shipSize)) {;
                                shipPlacement.add(board.board[i][j]);
                                if (!temp) {
                                    board.board[i][j].placeShip();
                                    board.board[i][j].setNotAccessible();
                                }
                            } else {
                                if (!checkRange(i, j)) {
                                    if(!temp) {
                                        board.board[i][j].setNotAccessible();
                                    }
                                    shipEnvCells.add(board.board[i][j]);
                                }
                            }
                        }
                    }
                }
                break;
            }
            //влево
            case 3: {
                for (int i = y - 1; i < y + 2; i++) {
                    for (int j = x + 1; j > x - shipSize - 1; j--) {
                        //если там корабль
                        if (j >= 0) {
                            if ((i == y) && (j < x + 1 && j > x - shipSize)) {
                                shipPlacement.add(board.board[i][j]);
                                if (!temp) {
                                    board.board[i][j].placeShip();
                                    board.board[i][j].setNotAccessible();
                                }
                            } else {
                                if (!checkRange(i, j)) {
                                    if(!temp) {
                                        board.board[i][j].setNotAccessible();
                                    }
                                    shipEnvCells.add(board.board[i][j]);
                                }
                            }
                        }
                    }
                }
                break;
            }
        }

        return shipEnvCells;
    }

    //FX помечивание ячеек рядом с кораблем
    public void paintShipEnviremontCells(Node node, OwnBoard board, String picName) {
        ArrayList<Cell> shipEnvCells = getShipEnviroment(board, false);
        String fxID;
        for (Cell cell : shipEnvCells) {
            fxID = idMaker(cell.getY(), cell.getX());
            cell.setNotAccessible();
            ((ImageView) node.lookup(fxID)).setImage(new Image(picName));
        }
        for (Cell cell : shipPlacement) {
            cell.setNotAccessible();
        }
    }
    
    //FX помечивание ячеек рядом с кораблем поля противника
    public void paintShipEnviremontCells(Node node, EnemyBoard board, String picName, String id) {
        ArrayList<Cell> shipEnvCells = getShipEnviroment(board, false);
        String fxID;
        for (Cell cell : shipEnvCells) {
            fxID = EnemyBoard.idMaker(cell.getY(), cell.getX(), id);
            cell.shootCell();
            ((ImageView) node.lookup(fxID)).setImage(new Image(picName));
        }
        for (Cell cell : shipPlacement) {
            cell.setNotAccessible();
        }
    }

    //перегрузка для просчета ячеек
    public void paintShipEnviremontCells(Board board) {
        ArrayList<Cell> shipEnvCells = getShipEnviroment(board, false);
        for (Cell cell : shipEnvCells) {
            cell.setNotAccessible();
        }
        for (Cell cell : shipPlacement) {
            cell.setNotAccessible();
        }
    }
    
    //проверка жизни
    public boolean checkIfDead() {
        int deadCellsAmount = 0;
        for (Cell cell : shipPlacement) {
            if (cell.isStriked()) {
                deadCellsAmount++;
            }
        }
        //если мертв
        if (deadCellsAmount == shipSize) {
            setDead();
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkRange(int y, int x) {
        return (y > 9 || y < 0 || x < 0 || x > 9);
    }
}
