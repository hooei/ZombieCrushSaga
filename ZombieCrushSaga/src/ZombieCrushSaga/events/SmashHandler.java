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
public class SmashHandler implements ActionListener {

    private ZombieCrushSagaMiniGame game;
    // private ZombieCrushSaga mahJong;
    /**
     * This constructor just stores the game for later.
     *
     * @param initGame the game to update
     */
    public SmashHandler(ZombieCrushSagaMiniGame initGame) {
        game = initGame;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        game.processSmash();
    }
}
