package ui;

import core.ComputerPlayer;
import core.GameLogic;
import core.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * GUI for Snakes and Ladders.
 */
public class GameGUI extends Application {

    private GameLogic logic;
    private Map<Integer, StackPane> tileMap;
    private Button rollDie;
    private Text gameUpdates;

    final static int ROWS = 10;
    final static int COLUMNS = 10;
    final static int TILE_SIZE = 50;

    /**
     * Prompts the user (in GUI format) to choose a computer opponent or a real opponent.
     * Initiates appropriate game based on choice.
     * @param gameTypeStage
     */
    @Override
    public void start(Stage gameTypeStage) {
        // Game Type Selection

        FlowPane gameTypePane = new FlowPane();
        gameTypePane.setHgap(20);
        gameTypePane.setAlignment(Pos.CENTER);

        Button vsComputer = new Button("Computer");
        Button vsPlayer = new Button("Another Player");

        gameTypePane.getChildren().addAll(vsComputer, vsPlayer);

        gameTypeStage.setScene(new Scene(gameTypePane, 250, 100));
        gameTypeStage.show();

        vsComputer.setOnAction(e -> {
            logic = new GameLogic(1); // Computer Player
            gameTypeStage.close();
            launchBoardGUI(logic);
        });

        vsPlayer.setOnAction(e -> {
            logic = new GameLogic(2); // Another Player
            gameTypeStage.close();
            launchBoardGUI(logic);
        });
    }

    /**
     * Displays game board in GUI environment.
     * Has a button for rolling the die, and a reset button for ending the game at any point.
     * Also displays messages / game updates in the bottom center.
     * Tiles that transport players are dark color, tiles that players are transported TO are lighter colors.
     * @param logic The appropriate logic for the game type chosen on the previous screen.
     */
    private void launchBoardGUI(GameLogic logic) {

        tileMap = new HashMap<>();
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setHgap(0); // Ensures no space between tiles
        board.setVgap(0);

        boolean white = true; // for alternating tile colors
        int squareNumber = 100;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                StackPane tile = new StackPane();
                Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
                Text number;
                if (Table.isSnake(squareNumber)){
                    rect.setFill(Color.CRIMSON);
                    number = new Text(String.valueOf(squareNumber) + "🐍");
                }
                else if (Table.isLadder(squareNumber)){
                    rect.setFill(Color.FORESTGREEN);
                    number = new Text(String.valueOf(squareNumber) + "🪜");
                }
                else if (Table.isLadderHead(squareNumber)){
                    rect.setFill(Color.LIGHTGREEN);
                    number = new Text(String.valueOf(squareNumber) + "🪜");
                }
                else if (Table.isSnakeTail(squareNumber)){
                    rect.setFill(Color.PALEVIOLETRED);
                    number = new Text(String.valueOf(squareNumber) + "🐍");
                }
                else {
                    rect.setFill(white ? Color.BEIGE : Color.LIGHTBLUE);
                    number = new Text(String.valueOf(squareNumber));
                }
                rect.setStroke(Color.BLACK);
                tile.getChildren().addAll(rect, number);
                board.add(tile, col, row);

                tileMap.put(squareNumber, tile); // Maps logic square to GUI tile
                white = !white;
                squareNumber--;
            }
            white = !white; // alternate each row
        }


        Circle player1Token = new Circle(15, Color.RED);
        Circle player2Token = new Circle(15, Color.BLUE);

        Text p1Label = new Text("P1");
        p1Label.setFill(Color.WHITE);
        Text p2Label = new Text("P2");
        p2Label.setFill(Color.WHITE);

        StackPane p1Stack = new StackPane(player1Token, p1Label);
        StackPane p2Stack = new StackPane(player2Token, p2Label);

        tileMap.get(1).getChildren().addAll(p1Stack, p2Stack); // Start each player on space 1

        rollDie = new Button("Roll Die");
        rollDie.setPrefWidth(100);

        gameUpdates = new Text("Game started!");

        Button resetGame = new Button("Reset");
        resetGame.setPrefWidth(100);
        resetGame.setOnAction(e -> {
            //Close current board
            Stage stage = (Stage) resetGame.getScene().getWindow();
            stage.close();
            try {
                start(new Stage()); // relaunch selection menu
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        HBox bottomPane = new HBox(20); // space between button and text
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.getChildren().addAll(rollDie, gameUpdates, resetGame);

        Map<Player, StackPane> playerTokens = Map.of(
                logic.getPlayer1(), p1Stack,
                logic.getPlayer2(), p2Stack
        );

        rollDie.setOnAction(e -> {
            Player current = logic.getCurrentPlayer();
            StackPane token = playerTokens.get(current);
            int oldPos = current.getPos();
            int roll = logic.playTurn();
            int newPos = current.getPos();
            tileMap.get(oldPos).getChildren().remove(token);
            tileMap.get(newPos).getChildren().add(token);
            gameUpdates.setText(current.getName() + " rolled a " + roll + " and moved to " + newPos);
            if (logic.winner()) {
                gameUpdates.setText(current.getName() + " wins!");
            }
            // If next player is computer, wait 2.5 sec then take turn
            if (logic.getCurrentPlayer() instanceof ComputerPlayer) {
                PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
                pause.setOnFinished(ev -> rollDie.fire()); // trigger computer's turn
                pause.play();
            }
        });

        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setVgap(20);
        root.add(board, 0, 0);
        root.add(bottomPane, 0, 1);

        Stage gameStage = new Stage();
        gameStage.setScene(new Scene(root, COLUMNS * TILE_SIZE + 40, ROWS * TILE_SIZE + 120));
        gameStage.setTitle("Snakes and Ladders");
        gameStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}