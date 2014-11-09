/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZombieCrushSaga.events;

import static ZombieCrushSaga.ZombieCrushSagaConstants.SAGA_SCREEN_STATE;
import ZombieCrushSaga.data.ZombieCrushSagaDataModel;
import ZombieCrushSaga.ui.ZombieCrushSagaMiniGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author zhenjin wang
 */
public class DownHandler implements ActionListener {
    private ZombieCrushSagaMiniGame miniGame;
    
    public DownHandler(ZombieCrushSagaMiniGame initMiniGame) {
        miniGame = initMiniGame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (miniGame.isCurrentScreenState(SAGA_SCREEN_STATE)) {
            miniGame.moveDown();
        }
    }
}
