package ZombieCrushSaga.events;

import static ZombieCrushSaga.ZombieCrushSagaConstants.SAGA_SCREEN_STATE;
import ZombieCrushSaga.ui.ZombieCrushSagaMiniGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author zhenjin wang
 */
public class UpHandler implements ActionListener {

    private ZombieCrushSagaMiniGame miniGame;

    public UpHandler(ZombieCrushSagaMiniGame initMiniGame) {
        miniGame = initMiniGame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (miniGame.isCurrentScreenState(SAGA_SCREEN_STATE)) {
            miniGame.moveUp();
        }
    }
}
