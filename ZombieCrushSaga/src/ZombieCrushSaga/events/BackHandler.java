/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZombieCrushSaga.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ZombieCrushSaga.data.ZombieCrushSagaDataModel;
import ZombieCrushSaga.ui.ZombieCrushSagaMiniGame;

/**
 * @author zhenjin wang
 */
public class BackHandler implements ActionListener {

    // HERE'S THE GAME WE'LL UPDATE
    private ZombieCrushSagaMiniGame game;

    /**
     * This constructor just stores the game for later.
     *
     * @param initGame the game to update
     */
    public BackHandler(ZombieCrushSagaMiniGame initGame) {
        game = initGame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ZombieCrushSagaDataModel data = (ZombieCrushSagaDataModel) game.getDataModel();
        data.processWin();
        game.switchToLevelScoreScreen();
    }
}
