/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZombieCrushSaga.events;

import ZombieCrushSaga.ui.ZombieCrushSagaMiniGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author zhenjin wang
 */
public class ResetGameHandler implements ActionListener {

    private ZombieCrushSagaMiniGame miniGame;

    public ResetGameHandler(ZombieCrushSagaMiniGame initMiniGame) {
        miniGame = initMiniGame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        miniGame.getDataModel().reset(miniGame);
    }

}
