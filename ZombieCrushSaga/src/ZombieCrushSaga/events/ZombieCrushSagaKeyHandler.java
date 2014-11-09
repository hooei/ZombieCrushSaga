/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZombieCrushSaga.events;

import static ZombieCrushSaga.ZombieCrushSagaConstants.GAME_SCREEN_STATE;
import ZombieCrushSaga.data.ZombieCrushSagaDataModel;
import ZombieCrushSaga.file.ZombieCrushSagaFileManager;
import ZombieCrushSaga.ui.ZombieCrushSagaMiniGame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author zhenjin wang
 */
public class ZombieCrushSagaKeyHandler extends KeyAdapter {

    // THE MAHJONG GAME ON WHICH WE'LL RESPOND
    private ZombieCrushSagaMiniGame game;

    /**
     * This constructor simply inits the object by keeping the game for later.
     *
     * @param initGame The Mahjong game that contains the back button.
     */
    public ZombieCrushSagaKeyHandler(ZombieCrushSagaMiniGame initGame) {
        game = initGame;
    }

    /**
     * This method provides a custom game response to when the user presses a
     * keyboard key.
     *
     * @param ke Event object containing information about the event, like which
     * key was pressed.
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        // CHEAT BY ONE MOVE. NOTE THAT IF WE HOLD THE C
        // KEY DOWN IT WILL CONTINUALLY CHEAT
        ZombieCrushSagaDataModel data = (ZombieCrushSagaDataModel) game.getDataModel();
        ZombieCrushSagaFileManager fileManager = game.getFileManager();
        if (!game.isCurrentScreenState(GAME_SCREEN_STATE)) {
            return;
        }
        if (ke.getKeyCode() == KeyEvent.VK_1) {
            int[][] testGrid = fileManager.loadTestLevel(1);
            data.resetTestLevel(testGrid);
        }
        if (ke.getKeyCode() == KeyEvent.VK_2) {
            int[][] testGrid = fileManager.loadTestLevel(2);
            data.resetTestLevel(testGrid);
        }
        if (ke.getKeyCode() == KeyEvent.VK_3) {
            int[][] testGrid = fileManager.loadTestLevel(3);
            data.resetTestLevel(testGrid);
        }
        if (ke.getKeyCode() == KeyEvent.VK_4) {
            int[][] testGrid = fileManager.loadTestLevel(4);
            data.resetTestLevel(testGrid);
        }
        if (ke.getKeyCode() == KeyEvent.VK_W) {
            data.cheatToWin();
        }
        if (ke.getKeyCode() == KeyEvent.VK_L) {
            data.cheatToLoss();
        }
        if (ke.getKeyCode() == KeyEvent.VK_R) {
            data.resetGame();
        }
    }
}
