package game2;

import java.util.Random;
import javafx.scene.Node;

public class AIPlayer extends Player {

    public static final Random RND = new Random();
    private boolean finishShip; //добит ли корабль
    private int priority; //есть ли корабль вида 4,3,2,1
    private int x;
    private int y;
    private int foundShipX;
    private int foundShipY;
    private EnemyBoard virtualBoard;
    private int[] virtualShipSizes; //просчет оставшихся сил соперника

    public AIPlayer(Node node1, Node node2) {
        super(node1, node2);
        setBoard();
        finishShip = true;
        priority = 4;
        virtualBoard = new EnemyBoard();
        virtualShipSizes = new int[4];
        for (int i = 0; i < 4; i++) {
            virtualShipSizes[i] = 4 - i;
        }

    }

    
    

    //выбор хода
    public boolean takeDecision() {
        int[][] powerBoard = new int[10][10]; //таблица ценностей ячеек
        //если нет раненого корабля
        if (finishShip) {
            //проход по полю
            Ship tempShip;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    //4 направления корабля
                    for (int k = 0; k < 4; k++) {
                        tempShip = new Ship(i, j, priority, k);
                        tempShip.getShipEnviroment(virtualBoard, true);
                        //если можно поставить такой корабль, увеличиваем ценность ячейки
                        if (virtualBoard.checkShipPlacement(tempShip)) {
                            for (Cell shipCell : tempShip.getShipPlacement()) {
                                //только свободные ячейки
                                if (shipCell.isAccessible() && !shipCell.isShip()) {
                                    powerBoard[shipCell.getY()][shipCell.getX()]++;
                                }
                            }
                        }
                    }
                }
            }
        } //добивание раненого
        else {
            Ship tempShip;
            int foundShipCellsAmount;
            //по вертикали
            for (int k = priority; k > 1; k--) {
                for (int i = y - k + 1; i <= y; i++) {
                    if (i >= 0 && i < 10) {
                        tempShip = new Ship(i, x, k, 2);
                        tempShip.getShipEnviroment(virtualBoard, true);
                        //пробуем поставить корабль
                        foundShipCellsAmount = 0;
                        if (virtualBoard.checkShipPlacement(tempShip)) {
                            for (Cell shipCell : tempShip.getShipPlacement()) {
                                if (shipCell.isAccessible()) {
                                    powerBoard[shipCell.getY()][shipCell.getX()]++;
                                }
                                //если корабль содержит ячейку с кораблем виртуального поля, добавляем ценности
                                if (virtualBoard.board[shipCell.getY()][shipCell.getX()].isShip()) {
                                    foundShipCellsAmount++;
                                    for (Cell virtualShipCell : tempShip.getShipPlacement()) {
                                        if (foundShipCellsAmount > 1) {
                                            powerBoard[virtualShipCell.getY()][virtualShipCell.getX()] += 20;
                                        }
                                    }
                                    //убираем ценость ячейки с кораблем
                                    powerBoard[shipCell.getY()][shipCell.getX()] = 0;
                                }
                            }
                        }
                    }
                }
                //по горизонтали
                for (int i = x - k + 1; i <= x; i++) {
                    if (i >= 0 && i < 10) {
                        tempShip = new Ship(y, i, k, 1);
                        tempShip.getShipEnviroment(virtualBoard, true);
                        //пробуем поставить корабль
                        foundShipCellsAmount = 0;
                        if (virtualBoard.checkShipPlacement(tempShip)) {
                            for (Cell shipCell : tempShip.getShipPlacement()) {
                                if (shipCell.isAccessible()) {
                                    powerBoard[shipCell.getY()][shipCell.getX()]++;
                                }
                                //если корабль содержит ячейку с кораблем виртуального поля, добавляем ценности
                                if (virtualBoard.board[shipCell.getY()][shipCell.getX()].isShip()) {
                                    foundShipCellsAmount++;
                                    for (Cell virtualShipCell : tempShip.getShipPlacement()) {
                                        if (foundShipCellsAmount > 1) {
                                            powerBoard[virtualShipCell.getY()][virtualShipCell.getX()] += 20;
                                        }
                                    }
                                    //убираем ценость ячейки с кораблем
                                    powerBoard[shipCell.getY()][shipCell.getX()] = 0;
                                }
                            }
                        }
                    }
                }

            }

            //выбор стороны добивания по сумме ценностей
            if (priority > 2) {
                for (int i = 0; i <= priority - 3; i++) {
                    if (y - 2 - i >= 0) {
                        powerBoard[y - 1][x] += powerBoard[y - 2 - i][x];
                        powerBoard[y - 2 - i][x] = 0;
                    }
                    if (y + 2 + i < 10) {
                        powerBoard[y + 1][x] += powerBoard[y + 2 + i][x];
                        powerBoard[y + 2 + i][x] = 0;
                    }
                    if (x + 2 + i < 10) {
                        powerBoard[y][x + 1] += powerBoard[y][x + 2 + i];
                        powerBoard[y][x + 2 + i] = 0;
                    }
                    if (x - 2 - i >= 0) {
                        powerBoard[y][x - 1] += powerBoard[y][x - 2 - i];
                        powerBoard[y][x - 2 - i] = 0;
                    }
                }
            }
            powerBoard[y][x] = 0;
        }
        int maxPower = 0;
        int random = 0;
        for (int i = 0;
                i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!virtualBoard.board[i][j].isShip()) {
                    if (maxPower <= powerBoard[i][j]) {
                        //выбор случайных из макс
                        if (maxPower == powerBoard[i][j]) {
                            random = finishShip ? RND.nextInt(7) : RND.nextInt(4);
                            if (random == 0) {
                                maxPower = powerBoard[i][j];
                                y = i;
                                x = j;
                            }
                        } else {
                            maxPower = powerBoard[i][j];
                            y = i;
                            x = j;
                        }
                    }
                }
            }
        }

        //если нет раненого корабля
        if (finishShip) {
            //стреляем
            finishShip = !(shootEnemy(y, x, "#cell") == 2);

            //если попали по кораблю - отмечаем у бота
            if (!finishShip) {
                virtualBoard.board[y][x].placeShip();

                //если однопалубный, то продолжаем поиск  
                if (enemyBoard.getShip(new Cell(y, x)).getSize() == 1) {
                    for (Cell cell : enemyBoard.getShip(new Cell(y, x)).getShipEnviroment(enemyBoard, false)) {
                        virtualBoard.shootCell(cell.getY(), cell.getX());
                    }
                    virtualShipSizes[0]--;
                    finishShip = true;
                    return true;
                } else {
                    foundShipX = x;
                    foundShipY = y;
                }
                return true;
            } else {
                virtualBoard.shootCell(y, x);
                return false;
            }

        } else {
            //если есть раненый, то находим его
            Ship tempShip = enemyBoard.getShip(new Cell(foundShipY, foundShipX));
            //если попали
            if (shootEnemy(y, x, "#cell") == 2) {
                //если добили, убираем окружение корабля
                if (tempShip.isDead()) {
                    for (Cell cell : enemyBoard.getShip(new Cell(y, x)).getShipEnviroment(enemyBoard, false)) {
                        virtualBoard.shootCell(cell.getY(), cell.getX());
                    }
                    virtualShipSizes[tempShip.getSize() - 1]--;
                    //меняем приоритет поиска
                    if (priority < 0) {
                        return false;
                    } else {
                        while (virtualShipSizes[priority - 1] == 0) {
                            priority--;
                        }
                    }
                    finishShip = true;
                } else { //не добили
                    finishShip = false;
                }
                virtualBoard.board[y][x].placeShip();
                return true;
            } else {  //не попали
                virtualBoard.shootCell(y, x);
                x = foundShipX;
                y = foundShipY;
                return false;
            }

        }
    }

    private void showPowerBoard(int[][] powerBoard) {
        for (int i = 0; i < 10; i++) {
            System.out.println("");
            for (int j = 0; j < 10; j++) {
                System.out.print(powerBoard[i][j] + "  ");
            }
        }
        System.out.println("");
    }
}
