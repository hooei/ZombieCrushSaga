/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZombieCrushSaga.events;

import ZombieCrushSaga.data.ZombieCrushSagaDataModel;
import ZombieCrushSaga.ui.ZombieCrushSagaMiniGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author zhenjin wang
 */
public class PlayGameHandler implements ActionListener {

    private ZombieCrushSagaMiniGame miniGame;

    public PlayGameHandler(ZombieCrushSagaMiniGame initMiniGame) {
        miniGame = initMiniGame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ZombieCrushSagaDataModel data = (ZombieCrushSagaDataModel) miniGame.getDataModel();
        if (miniGame.isCurrentScreenState("LEVEL_SCREEN_STATE")) {
            data.endGameAsLoss();
        }
        miniGame.switchToSagaScreen();
    }
}
