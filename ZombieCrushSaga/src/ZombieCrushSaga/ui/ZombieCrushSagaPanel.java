package ZombieCrushSaga.ui;

import ZombieCrushSaga.ZombieCrushSaga;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JPanel;
import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;
import ZombieCrushSaga.data.ZombieCrushSagaDataModel;
import static ZombieCrushSaga.ZombieCrushSagaConstants.*;
import ZombieCrushSaga.data.ZombieCrushSagaRecord;
import properties_manager.PropertiesManager;

/**
 * This class performs all of the rendering for the Mahjong game application.
 *
 * @author Zhenjin Wang
 */
public class ZombieCrushSagaPanel extends JPanel {

    // THIS IS ACTUALLY OUR Mahjong Solitaire APP, WE NEED THIS
    // BECAUSE IT HAS THE GUI STUFF THAT WE NEED TO RENDER
    private MiniGame game;

    // AND HERE IS ALL THE GAME DATA THAT WE NEED TO RENDER
    private ZombieCrushSagaDataModel data;

    // WE'LL USE THIS TO FORMAT SOME TEXT FOR DISPLAY PURPOSES
    private NumberFormat numberFormatter;

    // WE'LL USE THIS AS THE BASE IMAGE FOR RENDERING UNSELECTED TILES
    private BufferedImage blankTileImage;

    // WE'LL USE THIS AS THE BASE IMAGE FOR RENDERING SELECTED TILES
    private BufferedImage blankTileSelectedImage;
    private boolean fill;

    /**
     * This constructor stores the game and data references, which we'll need
     * for rendering.
     *
     * @param initGame the Mahjong Solitaire game that is using this panel for
     * rendering.
     *
     * @param initData the Mahjong Solitaire game data.
     */
    public ZombieCrushSagaPanel(MiniGame initGame, ZombieCrushSagaDataModel initData) {
        game = initGame;
        data = initData;
        numberFormatter = NumberFormat.getNumberInstance();
        numberFormatter.setMinimumFractionDigits(3);
        numberFormatter.setMaximumFractionDigits(3);
    }

    // MUTATOR METHODS
    // -setBlankTileImage
    // -setBlankTileSelectedImage
    /**
     * This mutator method sets the base image to use for rendering tiles.
     *
     * @param initBlankTileImage The image to use as the base for rendering
     * tiles.
     */
    public void setBlankTileImage(BufferedImage initBlankTileImage) {
        blankTileImage = initBlankTileImage;
    }

    /**
     * This mutator method sets the base image to use for rendering selected
     * tiles.
     *
     * @param initBlankTileSelectedImage The image to use as the base for
     * rendering selected tiles.
     */
    public void setBlankTileSelectedImage(BufferedImage initBlankTileSelectedImage) {
        blankTileSelectedImage = initBlankTileSelectedImage;
    }

    /**
     * This is where rendering starts. This method is called each frame, and the
     * entire game application is rendered here with the help of a number of
     * helper methods.
     *
     * @param g The Graphics context for this panel.
     */
    @Override
    public void paintComponent(Graphics g) {
        try {
            // MAKE SURE WE HAVE EXCLUSIVE ACCESS TO THE GAME DATA
            game.beginUsingData();
            renderClearScore(g);
            // CLEAR THE PANEL
            super.paintComponent(g);

            renderBackground(g);
            renderExchange(g);
            renderClear(g);
            renderFillEmpty(g);
            renderSecondBom(g);
            renderCheckToMoves(g);
            renderOrderToFill(g);
            renderGUIControls(g);
            renderTiles(g);
            renderScoreBar(g);
            renderMoves(g);
            renderNumLevel(g);
            renderHighestScore(g);
            renderStats(g);
            renderWin(g);
            renderGrid(g);
            
            // AND FINALLY, TEXT FOR DEBUGGING
            renderDebuggingText(g);
        } finally {
            // RELEASE THE LOCK
            game.endUsingData();

        }
    }

    // RENDERING HELPER METHODS
    // - renderBackground
    // - renderGUIControls
    // - renderTiles
    // - renderDialogs
    // - renderGrid
    // - renderDebuggingText
    public void renderWin(Graphics g) {
        ZombieCrushSagaDataModel dm = (ZombieCrushSagaDataModel) game.getDataModel();
        if (dm.getBeginUpdataBoard()&& !dm.isProcessMoving() && dm.finishMoves() && dm.inProgress()) {
            if (dm.FinishAllMove()) {
                dm.processWin();
            }
        }
    }

    /**
     * Renders the background image, which is different depending on the screen.
     *
     * @param g the Graphics context of this panel.
     */
    public void renderBackground(Graphics g) {
        // THERE IS ONLY ONE CURRENTLY SET
        Sprite bg = game.getGUIDecor().get(BACKGROUND_TYPE);
        renderSprite(g, bg);

    }

    public void renderExchange(Graphics g) {
        if (((ZombieCrushSagaMiniGame) game).isCurrentScreenState(GAME_SCREEN_STATE)) {
            ZombieCrushSagaDataModel dm = (ZombieCrushSagaDataModel) game.getDataModel();
            if (dm.getBeginUpdataBoard() && dm.getFinishExchange()) {
                dm.setSpeed(3);
                dm.Exchange(dm.getSelectTile(), dm.getSelectedTile());
                dm.updateMoving();
                dm.setBeginUpdataBoard(false);
                dm.setFinishExchange(false);
            }
        }
    }

    public void renderClear(Graphics g) {
        if (((ZombieCrushSagaMiniGame) game).isCurrentScreenState(GAME_SCREEN_STATE)) {
            ZombieCrushSagaDataModel dm = (ZombieCrushSagaDataModel) game.getDataModel();
            if (dm.getBeginUpdataBoard() && !dm.isProcessMoving() && dm.getStartClear()) {
                dm.setFinishExchange(false);
                dm.clear();
                dm.setStartClear(false);
                dm.setStartFillEmptyCol(true);
                dm.setSelectTile(null);
                dm.setSelectedTile(null);
            }
        }
    }

    public void renderSecondBom(Graphics g) {
        if (((ZombieCrushSagaMiniGame) game).isCurrentScreenState(GAME_SCREEN_STATE)) {
            ZombieCrushSagaDataModel dm = (ZombieCrushSagaDataModel) game.getDataModel();
            if (dm.getBeginUpdataBoard() && !dm.isProcessMoving() && dm.getSecondBom()) {
                dm.sleep(100);
                dm.setSpeed(2);
                dm.addScoreTimes();
                dm.clearSecondBom();
                dm.setSecondBom(false);
            }
        }
    }

    public void renderCheckToMoves(Graphics g) {
        if (((ZombieCrushSagaMiniGame) game).isCurrentScreenState(GAME_SCREEN_STATE)) {
            ZombieCrushSagaDataModel dm = (ZombieCrushSagaDataModel) game.getDataModel();
            if (!dm.getEmptyCol().isEmpty() && dm.getBeginUpdataBoard() && !dm.isProcessMoving()) {
                dm.sleep(100);
                dm.setSpeed(2);
                if (!dm.getBom()) {
                    dm.addScoreTimes();
                } else {
                    dm.setScoreTimes(0);
                    dm.setBom(false);
                }
                ArrayList<Integer> emptyCol = new ArrayList<Integer>();
                while (!dm.getEmptyCol().isEmpty()) {
                    emptyCol.add(dm.getEmptyCol().remove(0));
                }
                while (!emptyCol.isEmpty()) {
                    int ecol = emptyCol.remove(0);
                    for (int j = dm.getTileGrid()[0].length - 1; j >= 0; j--) {  // from bottom to top
                        if (dm.getTileGrid()[ecol][j].size() > 0) {
                            if (dm.check(dm.getTileGrid()[ecol][j].get(dm.getTileGrid()[ecol][j].size() - 1))) {
                                dm.moveTiles(dm.getTileGrid()[ecol][j].get(dm.getTileGrid()[ecol][j].size() - 1));
                            }
                        }
                    }
                }
                dm.clear();
                fill = true;
            }
        }
    }

    public void renderClearScore(Graphics g) {
        if (((ZombieCrushSagaMiniGame) game).isCurrentScreenState(GAME_SCREEN_STATE)) {
            ZombieCrushSagaDataModel dm = (ZombieCrushSagaDataModel) game.getDataModel();
            if (!dm.isProcessMoving()) {
                dm.clearScore();
            }
        }
    }

    public void renderOrderToFill(Graphics g) {
        if (((ZombieCrushSagaMiniGame) game).isCurrentScreenState(GAME_SCREEN_STATE)) {
            ZombieCrushSagaDataModel dm = (ZombieCrushSagaDataModel) game.getDataModel();
            if (dm.getBeginUpdataBoard() && fill) {
                dm.sleep(100);
                for (int k = 0; k < dm.getTileGrid().length; k++) {
                    if (dm.fillEmptyColumn(k)) {
                        dm.getEmptyCol().add(k);
                    }
                }
                fill = false;
            }
        }
    }

    public void renderFillEmpty(Graphics g) {
        if (((ZombieCrushSagaMiniGame) game).isCurrentScreenState(GAME_SCREEN_STATE)) {
            ZombieCrushSagaDataModel dm = (ZombieCrushSagaDataModel) game.getDataModel();
            if (dm.getBeginUpdataBoard()&& dm.getStartFillEmptyCol() && !dm.isProcessMoving()) {
                for (int i = 0; i < dm.getTileGrid().length; i++) {
                    if (dm.fillEmptyColumn(i)) {
                        dm.getEmptyCol().add(i);
                    }
                }
                dm.setStartFillEmptyCol(false);
            }
        }
    }

    public void renderHighestScore(Graphics g) {
        if (((ZombieCrushSagaMiniGame) game).isCurrentScreenState(GAME_SCREEN_STATE)) {
            ZombieCrushSagaDataModel dm = (ZombieCrushSagaDataModel) game.getDataModel();
            String moves = "HS:" + ((ZombieCrushSagaMiniGame) game).getPlayerRecord().getHighestScore(dm.getCurrentLevel());
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
            g.setColor(Color.GREEN);
            g.drawString(moves, BACK_BUTTON_X + 20 + 190, 45);
            String score = (int) dm.getCurrentScore() + "";
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
            g.setColor(Color.GREEN);
            g.drawString(score, BACK_BUTTON_X + 20 + 190 * 3, 45);
        }
    }

    public void renderNumLevel(Graphics g) {
        if (((ZombieCrushSagaMiniGame) game).isCurrentScreenState(GAME_SCREEN_STATE)) {
            ZombieCrushSagaDataModel dm = (ZombieCrushSagaDataModel) game.getDataModel();
            int level = Integer.parseInt(dm.getCurrentLevel().substring(5));
            String moves = "LEVEL: " + level;
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
            g.setColor(Color.GREEN);
            g.drawString(moves, BACK_BUTTON_X + 20 + 190 * 2, 45);
            for (int i = 0; i < dm.getScore().length; i++) {
                for (int j = 0; j < dm.getGridRows(); j++) {
                    if (dm.getScore()[i][j] > 0) {
                        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
                        g.setColor(Color.GREEN);
                        g.drawString(" " + dm.getScore()[i][j], dm.calculateTileXInGrid(i, 2) + 20, dm.calculateTileYInGrid(j, 2) + 45);
                    }
                }
            }
        }
    }

    public void renderMoves(Graphics g) {
        // draw moves left
        if (((ZombieCrushSagaMiniGame) game).isCurrentScreenState(GAME_SCREEN_STATE)) {
            ZombieCrushSagaDataModel dm = (ZombieCrushSagaDataModel) game.getDataModel();
            int level = Integer.parseInt(dm.getCurrentLevel().substring(5));
            String moves = "MOVES: " + (Level_moves[level - 1] - dm.getCurrentMoves()) + "";
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
            g.setColor(Color.GREEN);
            g.drawString(moves, BACK_BUTTON_X + 20, 45);
        }
    }

    public void renderScoreBar(Graphics g) {
        if (((ZombieCrushSagaMiniGame) game).isCurrentScreenState(GAME_SCREEN_STATE)) {
            ZombieCrushSagaDataModel dm = (ZombieCrushSagaDataModel) game.getDataModel();
            ZombieCrushSagaMiniGame mg = (ZombieCrushSagaMiniGame) game;
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            int level = Integer.parseInt(dm.getCurrentLevel().substring(5));
            int OneStar = Level_1_Star[level - 1];
            int SecondStar = Level_2_Star[level - 1];
            int ThirdStar = Level_3_Star[level - 1];
            String score1 = "" + OneStar;
            String score2 = "" + SecondStar;
            String score3 = "" + ThirdStar;
            int SCORE_BAR_LENGTH = 455;
            int leftMost = Integer.parseInt(props.getProperty(ZombieCrushSaga.ZombieCrushSagaPropertyType.GAME_WIDTH)) - SCORE_BAR_LENGTH + 46;
            int length = 370;
            int currentScore = (int) dm.getCurrentScore();
            int scorebar;
            if (currentScore > ThirdStar) {
                scorebar = length;
            } else {
                scorebar = currentScore * length / ThirdStar;
            }
            if (scorebar > 0) {
                g.setColor(Color.GREEN);
                g.drawRect(leftMost, 12, scorebar, 23);
                g.setColor(Color.GREEN);
                g.fillRect(leftMost, 12, scorebar, 23);
            }
            String partition = "|";
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
            g.setColor(Color.RED);
            g.drawString(partition, leftMost + (OneStar) * length / ThirdStar, 30);

            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
            g.setColor(Color.RED);
            g.drawString(partition, leftMost + (SecondStar) * length / ThirdStar, 30);

            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));   // draw first target score
            g.setColor(Color.GREEN);
            g.drawString(score1, leftMost + (OneStar) * length / ThirdStar, 46);
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));   // draw second target score
            g.setColor(Color.GREEN);
            g.drawString(score2, leftMost + (SecondStar) * length / ThirdStar, 46);

            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));   // draw last target score
            g.setColor(Color.GREEN);
            g.drawString(score3, leftMost + length - 15, 55);

        }

    }

    /**
     * Renders all the GUI decor and buttons.
     *
     * @param g this panel's rendering context.
     */
    public void renderGUIControls(Graphics g) {
        // GET EACH DECOR IMAGE ONE AT A TIME
        Collection<Sprite> decorSprites = game.getGUIDecor().values();
        for (Sprite s : decorSprites) {
            renderSprite(g, s);
        }
        // AND NOW RENDER THE BUTTONS
        Collection<Sprite> buttonSprites = game.getGUIButtons().values();
        for (Sprite s : buttonSprites) {
            renderSprite(g, s);
        }
    }

    /**
     * This method renders the on-screen stats that change as the game
     * progresses. This means things like the game time and the number of tiles
     * remaining.
     *
     * @param g the Graphics context for this panel
     */
    public void renderTime(Graphics g) {
        // RENDER THE GAME TIME
        if (((ZombieCrushSagaMiniGame) game).isCurrentScreenState(GAME_SCREEN_STATE)) {
            // RENDER THE TIME
            String time = data.gameTimeToText();
            int x = TIME_X + TIME_OFFSET;
            int y = TIME_Y + TIME_TEXT_OFFSET;
            g.drawString(time, x, y);
        }
    }

    /**
     * This method renders the on-screen stats that change as the game
     * progresses. This means things like the game time and the number of tiles
     * remaining.
     *
     * @param g the Graphics context for this panel
     */
    public void renderStats(Graphics g) {
        // RENDER THE GAME TIME
        if (((ZombieCrushSagaMiniGame) game).isCurrentScreenState(LEVEL_SCREEN_STATE)) {

            ZombieCrushSagaDataModel dm = (ZombieCrushSagaDataModel) game.getDataModel();
            ZombieCrushSagaMiniGame mg = (ZombieCrushSagaMiniGame) game;
            ZombieCrushSagaRecord record = mg.getPlayerRecord();
            //  render the  name of level

            String levelName = dm.getCurrentLevel().substring(5);
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
            g.setColor(Color.BLACK);
            g.drawString(levelName, 500, 80);
            // render moves

            String moves = "" + Level_moves[Integer.parseInt(levelName) - 1];
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
            g.setColor(Color.BLACK);
            g.drawString(moves, 536, 170);
            // render Target:
            if (jelly[Integer.parseInt(levelName) - 1]) {
                String jelly = "Clear All Jelly!";
                g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
                g.setColor(Color.BLACK);
                g.drawString(jelly, 572, 250);
            }

            // render Target
            // 1 star
            String OneStar = "" + Level_1_Star[Integer.parseInt(levelName) - 1];
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
            g.setColor(Color.BLACK);
            g.drawString(OneStar, 394, 315);

            // 2 star
            String TwoStar = "" + Level_2_Star[Integer.parseInt(levelName) - 1];
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
            g.setColor(Color.BLACK);
            g.drawString(TwoStar, 485, 400);

            // 3 star
            String ThreeStar = "" + Level_3_Star[Integer.parseInt(levelName) - 1];
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
            g.setColor(Color.BLACK);
            g.drawString(ThreeStar, 595, 490);

            // Highest Score
            int hs = (int) ((ZombieCrushSagaMiniGame) game).getPlayerRecord().getHighestScore(dm.getCurrentLevel());
            String highestScore = hs + "";
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
            g.setColor(Color.BLACK);
            g.drawString(highestScore, 786, 577);

            // current score
            String cs = dm.getCurrentScore() + "";
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 36));
            g.setColor(Color.BLACK);
            g.drawString(cs, 1165, 705);
          //  loss

            String win = "Win !";
            String loss = "Loss !";
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
            g.setColor(Color.RED);
            if (!dm.inProgress()) {
                if (dm.won()) {
                    g.drawString(win, 900, 360);
                }
                if (dm.lost() && dm.getCurrentScore() != 0) {
                    g.drawString(loss, 900, 360);
                }
            }
        }
    }

    /**
     * Renders all the game tiles, doing so carefully such that they are
     * rendered in the proper order.
     *
     * @param g the Graphics context of this panel.
     */
    public void renderTiles(Graphics g) {
        // DRAW THE TOP TILES ON THE STACK
        if (!data.won()) {
            // WE DRAW ONLY THE TOP 4 (OR 2 IF THERE ARE ONLY 2). THE REASON
            // WE DRAW 4 IS THAT WHILE WE MOVE MATCHES TO THE STACK WE WANT
            // TO SEE THE STACK
            ArrayList<ZombieCrushSagaTile> stackTiles = data.getStackTiles();
            if (stackTiles.size() > 3) {
                renderTile(g, stackTiles.get(stackTiles.size() - 3));
                renderTile(g, stackTiles.get(stackTiles.size() - 4));
            }
            if (stackTiles.size() > 1) {
                renderTile(g, stackTiles.get(stackTiles.size() - 1));
                renderTile(g, stackTiles.get(stackTiles.size() - 2));
            }
        }

        // THEN DRAW THE GRID TILES BOTTOM TO TOP USING
        // THE TILE'S Z TO STAGGER THEM AND GIVE THE ILLUSION
        // OF DEPTH
        ArrayList<ZombieCrushSagaTile>[][] tileGrid = data.getTileGrid();
        boolean noneOnLevel = false;
        int zIndex = 0;
        while (!noneOnLevel) {
            int levelCounter = 0;
            for (int i = 0; i < data.getGridColumns(); i++) {
                for (int j = 0; j < data.getGridRows(); j++) {
                    if (tileGrid[i][j].size() > zIndex) {
                        ZombieCrushSagaTile tile = tileGrid[i][j].get(zIndex);
                        renderTile(g, tile);
                        levelCounter++;
                    }
                }
            }
            if (levelCounter == 0) {
                noneOnLevel = true;
            }
            zIndex++;
        }

        // THEN DRAW ALL THE MOVING TILES
        Iterator<ZombieCrushSagaTile> movingTiles = data.getMovingTiles();
        while (movingTiles.hasNext()) {
            ZombieCrushSagaTile tile = movingTiles.next();
            renderTile(g, tile);
        }
        ArrayList<ZombieCrushSagaTile> needTiles = data.getSixTiles();
        for (int i = 0; i < needTiles.size(); i++) {
            ZombieCrushSagaTile tile = needTiles.get(i);
            renderTile(g, tile);
        }
    }

    /**
     * Helper method for rendering the tiles that are currently moving.
     *
     * @param g Rendering context for this panel.
     *
     * @param tileToRender Tile to render to this panel.
     */
    public void renderTile(Graphics g, ZombieCrushSagaTile tileToRender) {
        // ONLY RENDER VISIBLE TILES
        if (!tileToRender.getState().equals(INVISIBLE_STATE)) {
            // FIRST DRAW THE BLANK TILE IMAGE
            if (tileToRender.getState().equals(SELECTED_STATE)) {
                g.drawImage(blankTileSelectedImage, (int) tileToRender.getX(), (int) tileToRender.getY(), null);
            } else if (tileToRender.getState().equals(VISIBLE_STATE)) {
                g.drawImage(blankTileImage, (int) tileToRender.getX(), (int) tileToRender.getY(), null);
            }

            // THEN THE TILE IMAGE
            SpriteType bgST = tileToRender.getSpriteType();
            Image img = bgST.getStateImage(tileToRender.getState());
            g.drawImage(img, (int) tileToRender.getX() + TILE_IMAGE_OFFSET, (int) tileToRender.getY() + TILE_IMAGE_OFFSET, bgST.getWidth(), bgST.getHeight(), null);

            // IF THE TILE IS SELECTED, HIGHLIGHT IT
            if (tileToRender.getState().equals(SELECTED_STATE)) {
                g.setColor(SELECTED_TILE_COLOR);
                g.fillRoundRect((int) tileToRender.getX(), (int) tileToRender.getY(), bgST.getWidth(), bgST.getHeight(), 5, 5);
            } else {
                if (tileToRender.getState().equals(INCORRECTLY_SELECTED_STATE)) {
                    g.setColor(INCORRECTLY_SELECTED_TILE_COLOR);
                    g.fillRoundRect((int) tileToRender.getX(), (int) tileToRender.getY(), bgST.getWidth(), bgST.getHeight(), 5, 5);
                }

            }
        }
    }

    /**
     * Renders the game dialog boxes.
     *
     * @param g This panel's graphics context.
     */
    public void renderDialogs(Graphics g) {
        // GET EACH DECOR IMAGE ONE AT A TIME
        Collection<Sprite> dialogSprites = game.getGUIDialogs().values();
        for (Sprite s : dialogSprites) {
            // RENDER THE DIALOG, NOTE IT WILL ONLY DO IT IF IT'S VISIBLE
            renderSprite(g, s);
        }
    }

    /**
     * Renders the s Sprite into the Graphics context g. Note that each Sprite
     * knows its own x,y coordinate location.
     *
     * @param g the Graphics context of this panel
     *
     * @param s the Sprite to be rendered
     */
    public void renderSprite(Graphics g, Sprite s) {
        if (!s.getState().equals(INVISIBLE_STATE)) {
            SpriteType bgST = s.getSpriteType();
            Image img = bgST.getStateImage(s.getState());
            g.drawImage(img, (int) s.getX(), (int) s.getY(), bgST.getWidth(), bgST.getHeight(), null);
        }
    }

    /**
     * This method renders grid lines in the game tile grid to help during
     * debugging.
     *
     * @param g Graphics context for this panel.
     */
    public void renderGrid(Graphics g) {
        // ONLY RENDER THE GRID IF WE'RE DEBUGGING
        if (data.isDebugTextRenderingActive()) {
            for (int i = 0; i < data.getGridColumns(); i++) {
                for (int j = 0; j < data.getGridRows(); j++) {
                    int x = data.calculateTileXInGrid(i, 0);
                    int y = data.calculateTileYInGrid(j, 0);
                    g.drawRect(x, y, TILE_IMAGE_WIDTH, TILE_IMAGE_HEIGHT);
                }
            }
        }
    }

    /**
     * Renders the debugging text to the panel. Note that the rendering will
     * only actually be done if data has activated debug text rendering.
     *
     * @param g the Graphics context for this panel
     */
    public void renderDebuggingText(Graphics g) {
        // IF IT'S ACTIVATED
        if (data.isDebugTextRenderingActive()) {
            // ENABLE PROPER RENDER SETTINGS
            g.setFont(DEBUG_TEXT_FONT);
            g.setColor(DEBUG_TEXT_COLOR);

            // GO THROUGH ALL THE DEBUG TEXT
            Iterator<String> it = data.getDebugText().iterator();
            int x = data.getDebugTextX();
            int y = data.getDebugTextY();
            while (it.hasNext()) {
                // RENDER THE TEXT
                String text = it.next();
                g.drawString(text, x, y);
                y += 20;
            }

        }
    }
}
