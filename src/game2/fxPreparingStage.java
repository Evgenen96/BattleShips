package game2;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class fxPreparingStage {

    @FXML
    public GridPane ownBoardNode;
    @FXML
    public GridPane enemyBoardNode;
    @FXML
    public AnchorPane ap2;
    @FXML
    public Pane footer;
    @FXML
    public ProgressBar fxWinProbability;
    @FXML
    public TextField fxAmount1;
    @FXML
    public TextField fxAmount2;
    @FXML
    public TextField fxAmount3;
    @FXML
    public TextField fxAmount4;
    @FXML
    public TextField fxAmount11;
    @FXML
    public TextField fxAmount21;
    @FXML
    public TextField fxAmount31;
    @FXML
    public TextField fxAmount41;
    @FXML
    public Button autoSetBt;
    @FXML
    public Button startGameBt;
    @FXML
    public Label label1;
    @FXML
    public Label label2;
    @FXML
    public Label messageLabel;

    private static Game game;
    private int shipSize = 4; //выбранный размер корабля
    private int shipDirection; //выбранное направление корабля
    private Ship tempShip;

    //FX нажатие по полю
    public void cellClick(MouseEvent mouseEvent) {
        if (game == null) {
            return;
        }

        Node source = (Node) mouseEvent.getSource();
        int[] coords = getCoordinatesFromFXId(source.getId());
        //левая кнопка мыши - установка корабля
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            tempShip.getShipEnviroment(game.getFirstPlayer().getOwnBoard(), true);
            //выход из метода если нельзя установить такой корабль
            if (tempShip.getShipPlacement().size() != shipSize || !game.getFirstPlayer().getOwnBoard().checkShipPlacement(tempShip)) {
                return;
            }
            game.getFirstPlayer().getOwnBoard().addShip(coords[0], coords[1], shipSize, shipDirection);
            game.getFirstPlayer().getOwnBoard().paintShipsAndEnv();
            //корабли расставлены - переход к игре
            if (!game.getFirstPlayer().getOwnBoard().shipRules()) {
                game.getFirstPlayer().getOwnBoard().paintShips();

                startGameAnimation();

                return;
            }
            //автосмена размера корабля
            switch (game.getFirstPlayer().getOwnBoard().getShipAmount()) {
                case 1: {
                    shipSize--;
                    tempShip = new Ship(-5, -5, shipSize, shipDirection);
                    break;
                }
                case 3: {
                    shipSize--;
                    tempShip = new Ship(-5, -5, shipSize, shipDirection);
                    break;
                }
                case 6: {
                    shipSize--;
                    tempShip = new Ship(-5, -5, shipSize, shipDirection);
                    break;
                }

            }
        }
        //правая кнопка мыши - поворот корабля
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            if (shipDirection == 3) {
                shipDirection = 0;
            } else {
                shipDirection++;
            }
            tempShip.setDirection(shipDirection);
            tempShip.getShipEnviroment(game.getFirstPlayer().getOwnBoard(), true);
            if (tempShip.getShipPlacement().size() != shipSize || !game.getFirstPlayer().getOwnBoard().checkShipPlacement(tempShip)) {
                game.getFirstPlayer().getOwnBoard().paintCells();
                game.getFirstPlayer().getOwnBoard().paintShipsAndEnv();
                return;
            }
            game.getFirstPlayer().getOwnBoard().paintCells();
            tempShip.paint(ownBoardNode);
            game.getFirstPlayer().getOwnBoard().paintShipsAndEnv();
        }
    }

    //перемещение корабля по полю
    public void cellPointer(MouseEvent mouseEvent) {
        if (game == null) {
            return;
        }
        if (tempShip == null) {
            tempShip = new Ship(-5, -5, shipSize, shipDirection);
            return;
        }
        //если все корабли расставлены, временный не появляется
        if (!game.getFirstPlayer().getOwnBoard().shipRules()) {
            return;
        }
        Node source = (Node) mouseEvent.getSource();
        int[] coords = getCoordinatesFromFXId(source.getId());
        tempShip.setY(coords[0]);
        tempShip.setX(coords[1]);

        tempShip.getShipEnviroment(game.getFirstPlayer().getOwnBoard(), true);
        //если нельзя ставить в этом месте
        if (tempShip.getShipPlacement().size() != shipSize || !game.getFirstPlayer().getOwnBoard().checkShipPlacement(tempShip)) {
            return;
        }

        //если не может нарисовать временный, то не перекрашивать
        if (tempShip.paint(ownBoardNode)) {
            game.getFirstPlayer().getOwnBoard().paintCells();
            tempShip.paint(ownBoardNode);
            game.getFirstPlayer().getOwnBoard().paintShipsAndEnv();
        }
    }

    // координаты ячеек из ID на форме
    public int[] getCoordinatesFromFXId(String ID) {
        int[] coords = new int[2];
        coords[0] = Integer.parseInt(ID.substring(4, 5));
        coords[1] = Integer.parseInt(ID.substring(5, 6));
        return coords;
    }

    //кнопки
    public void shipBtClick(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        source.setDisable(true);

        FadeTransition fxAppearOwnBoard = new FadeTransition();
        fxAppearOwnBoard.setNode(ownBoardNode);
        fxAppearOwnBoard.setFromValue(0);
        fxAppearOwnBoard.setDuration(Duration.seconds(2.5));
        fxAppearOwnBoard.setToValue(10);

        FadeTransition fxDissapearStartBt = new FadeTransition();
        fxDissapearStartBt.setNode(source);
        fxDissapearStartBt.setDuration(Duration.seconds(0.5));
        fxDissapearStartBt.setFromValue(10);
        fxDissapearStartBt.setToValue(0);

        FadeTransition fxAppearAutoBt = new FadeTransition();
        fxAppearAutoBt.setNode(autoSetBt);
        fxAppearAutoBt.setDuration(Duration.seconds(1.5));
        fxAppearAutoBt.setFromValue(0);
        fxAppearAutoBt.setToValue(10);

        SequentialTransition startBtAnimation = new SequentialTransition();
        startBtAnimation.getChildren().addAll(fxDissapearStartBt, fxAppearOwnBoard, fxAppearAutoBt);
        startBtAnimation.play();

        autoSetBt.setVisible(true);
        ownBoardNode.setVisible(true);
        ownBoardNode.setDisable(false);
        game = new Game(ownBoardNode, enemyBoardNode);
        game.getFirstPlayer().getOwnBoard().paintCells();

    }

    //FX нажатие по полю противника
    public void gameClick(MouseEvent mouseEvent) throws InterruptedException {
        Node source = (Node) mouseEvent.getSource();
        int[] coords = getCoordinatesFromFXId(source.getId());
        enemyBoardNode.setDisable(true);
 
        switch (game.turn(coords[0], coords[1], fxWinProbability, messageLabel)) {
            case -1: {
                //победа ИИ
                enemyBoardNode.setDisable(true);
                messageLabel.setText("ВЫ ПРОИГРАЛИ!");
                return;
            }
            case 1: {
                //победа игрока
                enemyBoardNode.setDisable(true);
                messageLabel.setText("ВЫ ПОБЕДИЛИ!!!");
                return;
            }
            case 404: {
                //ошибка
                break;
            }
        }
        messageLabel.setText("Ваш ход");
        changeFXShipsAmount();
    }

    public void changeFXShipsAmount() {
        int[] aliveShips = new int[4];
        for (Ship ship : game.getFirstPlayer().enemyBoard.ships) {
            if (!ship.isDead()) {
                aliveShips[ship.getSize() - 1]++;
            }
        }

        fxAmount41.setText(String.valueOf(aliveShips[3]) + " x");
        fxAmount31.setText(String.valueOf(aliveShips[2]) + " x");
        fxAmount21.setText(String.valueOf(aliveShips[1]) + " x");
        fxAmount11.setText(String.valueOf(aliveShips[0]) + " x");

        aliveShips = new int[4];
        for (Ship ship : game.getFirstPlayer().ownBoard.ships) {
            if (!ship.isDead()) {
                aliveShips[ship.getSize() - 1]++;
            }
        }

        fxAmount4.setText(String.valueOf(aliveShips[3]) + " x");
        fxAmount3.setText(String.valueOf(aliveShips[2]) + " x");
        fxAmount2.setText(String.valueOf(aliveShips[1]) + " x");
        fxAmount1.setText(String.valueOf(aliveShips[0]) + " x");
    }

    public void autoSetBtClick(ActionEvent actionEvent) {
        game.getFirstPlayer().setBoard();
        game.getFirstPlayer().ownBoard.paintCells();
        game.getFirstPlayer().ownBoard.paintShipsAndEnv();
        if (!startGameBt.isVisible()) {
            FadeTransition fxAppearStartBt = new FadeTransition();
            fxAppearStartBt.setNode(startGameBt);
            fxAppearStartBt.setDuration(Duration.seconds(1.5));
            fxAppearStartBt.setFromValue(0);
            fxAppearStartBt.setToValue(10);
            fxAppearStartBt.play();
            startGameBt.setVisible(true);
        }
    }

    public void startGameBtClick(ActionEvent actionEvent) {
        FadeTransition fxAppearStartBt = new FadeTransition();
        fxAppearStartBt.setNode(startGameBt);
        fxAppearStartBt.setDuration(Duration.seconds(1.5));
        fxAppearStartBt.setFromValue(10);
        fxAppearStartBt.setToValue(0);

        FadeTransition fxAppearStartBt2 = new FadeTransition();
        fxAppearStartBt2.setNode(autoSetBt);
        fxAppearStartBt2.setDuration(Duration.seconds(1.5));
        fxAppearStartBt2.setFromValue(10);
        fxAppearStartBt2.setToValue(0);

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(fxAppearStartBt, fxAppearStartBt2);
        startGameBt.setVisible(false);
        autoSetBt.setVisible(false);
        game.getFirstPlayer().getOwnBoard().paintShips();
        startGameAnimation();
    }

    public void startGameAnimation() {
        PathTransition fxMoveOwnBoardLeft = new PathTransition();
        fxMoveOwnBoardLeft.setNode(ownBoardNode);
        fxMoveOwnBoardLeft.setDuration(Duration.seconds(1.5));
        fxMoveOwnBoardLeft.setPath(new Line(205, 205, -50, 205));

        FadeTransition fxAppearEnemyBoard = new FadeTransition();
        fxAppearEnemyBoard.setNode(enemyBoardNode);
        fxAppearEnemyBoard.setDuration(Duration.seconds(0.5));
        fxAppearEnemyBoard.setFromValue(0);
        fxAppearEnemyBoard.setToValue(100);

        TranslateTransition fxAppearFooter = new TranslateTransition();
        fxAppearFooter.setNode(footer);
        fxAppearFooter.setDuration(Duration.seconds(1));
        fxAppearFooter.setFromY(163);
        fxAppearFooter.setToY(0);

        FadeTransition fxAppearLabel1 = new FadeTransition();
        fxAppearLabel1.setNode(label1);
        fxAppearLabel1.setDuration(Duration.seconds(1.5));
        fxAppearLabel1.setFromValue(0);
        fxAppearLabel1.setToValue(100);

        FadeTransition fxAppearLabel2 = new FadeTransition();
        fxAppearLabel2.setNode(label2);
        fxAppearLabel2.setDuration(Duration.seconds(1.5));
        fxAppearLabel2.setFromValue(0);
        fxAppearLabel2.setToValue(100);

        ParallelTransition pl = new ParallelTransition();
        pl.getChildren().addAll(fxAppearLabel1, fxAppearLabel2);
        pl.play();

        SequentialTransition startGameAnimations = new SequentialTransition();
        startGameAnimations.getChildren().addAll(fxMoveOwnBoardLeft, fxAppearEnemyBoard,fxAppearFooter);
        startGameAnimations.play();

        label1.setVisible(true);
        label2.setVisible(true);
        footer.setVisible(true);
        autoSetBt.setVisible(false);
        enemyBoardNode.setVisible(true);
        enemyBoardNode.setDisable(false);
        ownBoardNode.setDisable(true);
        
        game.getFirstPlayer().getEnemyBoard().drawBoard();
    }
}
