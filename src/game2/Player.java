package game2;

import static game2.AIPlayer.RND;
import static game2.EnemyBoard.idMaker;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {

    protected OwnBoard ownBoard; // поле игрока
    protected EnemyBoard enemyBoard; //поле противника

    public Player(Node node1, Node node2) {
        ownBoard = new OwnBoard(node1);
        enemyBoard = new EnemyBoard(node2);
    }

    public OwnBoard getOwnBoard() {
        return ownBoard;
    }

    public EnemyBoard getEnemyBoard() {
        return enemyBoard;
    }

    //помечивание
    public void shootCell(int y, int x) {
        enemyBoard.shootCell(y, x);
    }

    //выстрел + прорисовка FX
    public int shootEnemy(int i, int j, String id) {
        Cell cell = enemyBoard.board[i][j];
        String fxID = idMaker(i, j, id);
        //если в ячейке корабль
        if (cell.isShip()) {
            cell.shootCell();
            enemyBoard.checkDeadShips(cell, id);
            ((ImageView) enemyBoard.node.lookup(fxID)).setImage(new Image(SHIPS.DEADSHIP));
            return 2;

        } // если ячейка не проверена
        else if (!cell.isStriked()) {
            cell.shootCell();
            ((ImageView) enemyBoard.node.lookup(fxID)).setImage(new Image(SHIPS.CHECKEDCELL));
            return 1;
        } // если ячейка проверена
        else {
            return 0;
        }
    }

    public double getWinProbability() {
        double own = ownBoard.getWinProbability();
        double enemy = enemyBoard.getWinProbability();
        return enemy / (own + enemy);
    }

    //установка кораблей
    public void setBoard() {
        ownBoard.resetBoard();
        int shipDirection = RND.nextInt(2);
        int shipX;
        int shipY;
        Ship tempShip;

        //4-палубник
        if (shipDirection == 0) {
            shipX = RND.nextInt(2) == 0 ? 0 : 9;
            shipY = RND.nextInt(7) + 3;
        } else {
            shipY = RND.nextInt(2) == 0 ? 0 : 9;
            shipX = RND.nextInt(7);
        }
        tempShip = new Ship(shipY, shipX, 4, shipDirection);
        ownBoard.addShip(tempShip);
        tempShip.paintShipEnviremontCells(ownBoard);

        //3-палубник
        int amount = 0;

        while (amount < 2) {
            shipDirection = RND.nextInt(2);
            if (shipDirection == 0) {
                shipX = RND.nextInt(10);
                shipY = RND.nextInt(8) + 2;
            } else {
                shipX = RND.nextInt(8);
                shipY = RND.nextInt(10);
            }
            tempShip = new Ship(shipY, shipX, 3, shipDirection);
            tempShip.getShipEnviroment(ownBoard, true);
            if (ownBoard.checkShipPlacement(tempShip)) {
                ownBoard.addShip(tempShip);
                tempShip.paintShipEnviremontCells(ownBoard);
                amount++;
            }
        }

        //2-палубник
        amount = 0;
        while (amount < 3) {
            shipDirection = RND.nextInt(2);
            if (shipDirection == 0) {
                shipX = RND.nextInt(10);
                shipY = RND.nextInt(9) + 1;
            } else {
                shipX = RND.nextInt(8);
                shipY = RND.nextInt(10);
            }
            tempShip = new Ship(shipY, shipX, 2, shipDirection);
            tempShip.getShipEnviroment(ownBoard, true);
            if (ownBoard.checkShipPlacement(tempShip)) {
                ownBoard.addShip(tempShip);
                tempShip.paintShipEnviremontCells(ownBoard);
                amount++;
            }
        }

        //1-палубник
        amount = 0;
        while (amount < 4) {
            int r = RND.nextInt(30);
            switch (r) {
                case 0: {
                    shipX = 0;
                    shipY = RND.nextInt(10);
                    break;
                }
                case 1: {
                    shipX = 9;
                    shipY = RND.nextInt(10);
                    break;
                }
                case 2: {
                    shipX = RND.nextInt(10);
                    shipY = 0;
                    break;
                }
                case 3: {
                    shipX = RND.nextInt(10);
                    shipY = 9;
                    break;
                }
                default: {
                    shipX = RND.nextInt(8) + 1;
                    shipY = RND.nextInt(8) + 1;
                    break;
                }
            }
            tempShip = new Ship(shipY, shipX, 1, shipDirection);
            tempShip.getShipEnviroment(ownBoard, true);
            if (ownBoard.checkShipPlacement(tempShip)) {
                ownBoard.addShip(tempShip);
                tempShip.paintShipEnviremontCells(ownBoard);
                amount++;
            }
        }
    }
}
