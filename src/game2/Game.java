package game2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class Game {

    private Player firstPlayer; // поле игрока
    private AIPlayer secondPlayer; //поле противника
    private Timeline tl;

    public Game(Node node1, Node node2) {
        firstPlayer = new Player(node1, node2);
        secondPlayer = new AIPlayer(node2, node1);
        firstPlayer.enemyBoard.board = secondPlayer.ownBoard.board;
        secondPlayer.enemyBoard.board = firstPlayer.ownBoard.board;
        firstPlayer.enemyBoard.ships = secondPlayer.ownBoard.ships;
        secondPlayer.enemyBoard.ships = firstPlayer.ownBoard.ships;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public AIPlayer getSecondPlayer() {
        return secondPlayer;
    }

    //Ход игры
    public int turn(int y, int x, ProgressBar pb, Label label) {
     
        if (getFirstPlayer().shootEnemy(y, x, "#cekk") == 0) {
            secondPlayer.ownBoard.node.setDisable(false);
            return 404;
        }
        if (getFirstPlayer().shootEnemy(y, x, "#cekk") == 2) {
            secondPlayer.ownBoard.node.setDisable(false);
            pb.setProgress(firstPlayer.getWinProbability());
            if (firstPlayer.getEnemyBoard().isAnyShipAlive()) {
                return 0;
            } else {
                //победа игрока
                return 1;

            }
        } else {
            label.setText("Ход ИИ");
            pb.setProgress(firstPlayer.getWinProbability());
            tl = new Timeline(new KeyFrame(Duration.seconds(1), ae -> {
                if (!secondPlayer.takeDecision()) {
                    pb.setProgress(firstPlayer.getWinProbability());
                    tl.stop();
                     label.setText("Ваш ход");
                    secondPlayer.ownBoard.node.setDisable(false);
                } else {
                    label.setText("Ход ИИ");
                    pb.setProgress(firstPlayer.getWinProbability());
                     
                }

            }));
            tl.setCycleCount(Timeline.INDEFINITE);
            tl.play();
        }
        if (!secondPlayer.getEnemyBoard().isAnyShipAlive()) {
            //победа ИИ
            return -1;
        }
        return 0;
    }
}
