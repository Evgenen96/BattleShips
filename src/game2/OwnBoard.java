package game2;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OwnBoard extends Board {

    public OwnBoard(Node node) {
        super(node);
    }

    //ограничение количества кораблей
    public boolean shipRules() {
        if (ships.size() == 10) {
            return false;
        }
        return true;
    }

    //предварительное сохранение корабля
    public void addShip(int y, int x, int shipSize, int direction) {
        if (!shipRules()) {
            return;
        }
        ships.add(new Ship(y, x, shipSize, direction));
        ships.get(ships.size() - 1).getShipEnviroment(this, false); //расчет всех занимаемых ячеек
    }

    public void addShip(Ship ship) {
        if (!shipRules()) {
            return;
        }
        ships.add(new Ship(ship));
        ships.get(ships.size() - 1).getShipEnviroment(this, false); //расчет всех занимаемых ячеек
    }

    //изменение состояния корабля
    public void changeShip(int index, int y, int x, int direction) {
        Ship ship = ships.get(index);
        ship.setX(x);
        ship.setY(y);
        ship.setDirection(direction);
    }

    public void changeShip(int index, int direction) {
        Ship ship = ships.get(index);
        ship.setDirection(direction);
    }

    public void changeShip(int index, int x, int y) {
        Ship ship = ships.get(index);
        ship.setX(x);
        ship.setY(y);
    }



//FX нарисовать все корабли на поле
    public void paintShipsAndEnv() {
        for (Ship ship : ships) {
            ship.paintShipEnviremontCells(node, this, "img/busy.png");
            ship.paint(node);

        }
    }
    
    //FX нарисовать все корабли на поле
    public void paintShips() {
        for (Ship ship : ships) {
            ship.paintShipEnviremontCells(node, this, "img/empty.png");
            ship.paint(node);

        }
    }

    //создать id для FX поле 1
    public static String idMaker(int y, int x) {
        String s = new String();
        s = "#cell";
        s = s.concat(String.valueOf(y)).concat(String.valueOf(x));
        return s;
    }

    //FX отрисовка ячеек
    public void paintCells() {
        String fxID;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Cell cell = board[i][j];
                if (!cell.isShip()) {
                    fxID = idMaker(i, j);
                    if (cell.isStriked()) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(SHIPS.CHECKEDCELL));
                    } else if (!cell.isAccessible()) {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(SHIPS.CHECKEDCELL));
                    } else {
                        ((ImageView) node.lookup(fxID)).setImage(new Image(SHIPS.EMPTYCELL));
                    }
                }
            }
        }
    }
     //поиск мертвых кораблей и отмечивание ячеек рядом
    public void checkDeadShips(Cell cell) {
        for (Ship ship : ships) {
            if (ship.getShipPlacement().contains(cell)) {
                if (ship.checkIfDead()) {
                    ship.paintShipEnviremontCells(node, this, SHIPS.CHECKEDCELL);
                }
            }
        }
    }
}
