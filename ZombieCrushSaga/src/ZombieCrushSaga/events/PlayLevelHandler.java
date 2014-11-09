/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZombieCrushSaga.events;

import ZombieCrushSaga.data.ZombieCrushSagaDataModel;
import ZombieCrushSaga.file.ZombieCrushSagaFileManager;
import ZombieCrushSaga.ui.ZombieCrushSagaMiniGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author zhenjin wang
 */
public class PlayLevelHandler implements ActionListener {

    private ZombieCrushSagaMiniGame game;

    // private ZombieCrushSaga mahJong;
    /**
     * This constructor just stores the game for later.
     *
     * @param initGame the game to update
     */
    public PlayLevelHandler(ZombieCrushSagaMiniGame initGame) {
        game = initGame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (!game.getDataModel().inProgress()) {
            ZombieCrushSagaDataModel data = (ZombieCrushSagaDataModel) game.getDataModel();
            String level = data.getCurrentLevel();
            data.setCurrentLevel(level);
            data.setData(0, 0);
            // UPDATE THE DATA
            ZombieCrushSagaFileManager fileManager = game.getFileManager();
            fileManager.loadLevel(level);
        }
        game.switchToGameScreen();
    }
}
