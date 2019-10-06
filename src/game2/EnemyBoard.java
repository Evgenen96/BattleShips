package game2;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EnemyBoard extends Board {

     public EnemyBoard() {
        super();
    }
    
    public EnemyBoard(Node node) {
        super(node);
    }
    
    public void drawBoard() {
        String fxID;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                fxID = idMaker(i, j, "#cekk");
                ((ImageView) node.lookup(fxID)).setImage(new Image("img/empty.png"));
            }
        }
    }

    

    //создать id для FX поле 2
    public static String idMaker(int y, int x, String s) {
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
                    fxID = idMaker(i, j, "#cekk");
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
    public void checkDeadShips(Cell cell, String id) {
        for (Ship ship : ships) {
            if (ship.getShipPlacement().contains(cell)) {
                if (ship.checkIfDead()) {
                    ship.getShipEnviroment(this, false);
                    ship.paintShipEnviremontCells(node, this, SHIPS.CHECKEDCELL, id);
                }
            }
        }
    }
}
