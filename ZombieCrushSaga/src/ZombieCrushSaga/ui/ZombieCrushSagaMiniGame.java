package ZombieCrushSaga.ui;

import java.awt.image.BufferedImage;
import ZombieCrushSaga.data.ZombieCrushSagaDataModel;
import mini_game.MiniGame;
import static ZombieCrushSaga.ZombieCrushSagaConstants.*;
import mini_game.Sprite;
import mini_game.SpriteType;
import properties_manager.PropertiesManager;
import ZombieCrushSaga.ZombieCrushSaga.ZombieCrushSagaPropertyType;
import ZombieCrushSaga.file.ZombieCrushSagaFileManager;
import ZombieCrushSaga.data.ZombieCrushSagaRecord;
import ZombieCrushSaga.events.BackHandler;
import ZombieCrushSaga.events.DownHandler;
import ZombieCrushSaga.events.QuitHandler;
import ZombieCrushSaga.events.PlayGameHandler;
import ZombieCrushSaga.events.PlayLevelHandler;
import ZombieCrushSaga.events.ResetGameHandler;
import ZombieCrushSaga.events.SelectLevelHandler;
import ZombieCrushSaga.events.SmashHandler;
import ZombieCrushSaga.events.UpHandler;
import ZombieCrushSaga.events.ZombieCrushSagaKeyHandler;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Iterator;

/**
 * This is the actual mini game, as extended from the mini game framework. It
 * manages all the UI elements.
 * @author zhenjin wang
 */
public class ZombieCrushSagaMiniGame extends MiniGame {

    // THE PLAYER RECORD FOR EACH LEVEL, WHICH LIVES BEYOND ONE SESSION
    private ZombieCrushSagaRecord record;

    // HANDLES ERROR CONDITIONS
    private ZombieCrushSagaErrorHandler errorHandler;

    // MANAGES LOADING OF LEVELS AND THE PLAYER RECORDS FILES
    private ZombieCrushSagaFileManager fileManager;

    // THE SCREEN CURRENTLY BEING PLAYED
    private String currentScreenState;
    private int currentSaga;
    public boolean smash;
    // ACCESSOR METHODS
    // - getPlayerRecord
    // - getErrorHandler
    // - getFileManager
    // - isCurrentScreenState

    /**
     * Accessor method for getting the player record object, which summarizes
     * the player's record on all levels.
     *
     * @return The player's complete record.
     */
    public ZombieCrushSagaRecord getPlayerRecord() {
        return record;
    }

    public boolean getSmash() {
        return this.smash;
    }

    /**
     * Accessor method for getting the application's error handler.
     *
     * @return The error handler.
     */
    public ZombieCrushSagaErrorHandler getErrorHandler() {
        return errorHandler;
    }

    /**
     * Accessor method for getting the app's file manager.
     *
     * @return The file manager.
     */
    public ZombieCrushSagaFileManager getFileManager() {
        return fileManager;
    }

    /**
     * Used for testing to see if the current screen state matches the
     * testScreenState argument. If it mates, true is returned, else false.
     *
     * @param testScreenState Screen state to test against the current state.
     *
     * @return true if the current state is testScreenState, false otherwise.
     */
    public boolean isCurrentScreenState(String testScreenState) {
        return testScreenState.equals(currentScreenState);
    }

    // SERVICE METHODS
    // - displayStats
    // - savePlayerRecord
    // - switchToGameScreen
    // - switchToSplashScreen
    // - updateBoundaries
    /**
     * This method forces the file manager to save the current player record.
     */
    public void savePlayerRecord() {
        // THIS CURRENTLY DOES NOTHING, INSTEAD, IT MUST SAVE ALL THE
        // PLAYER RECORDS IN THE SAME FORMAT IT IS BEING LOADED
        fileManager.saveRecord(record);
        // this.//getPlayerRecord();
    }

    /**
     * This method switches the application to the game screen, making all the
     * appropriate UI controls visible & invisible.
     */
    public void switchToSagaScreen() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(SAGA_SCREEN_STATE + 1);
        closeAllButtons();
        guiButtons.get(UP_S_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(UP_S_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(DOWN_S_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(DOWN_S_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(QUIT_S_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(QUIT_S_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(Level_Name[0]).setState(VISIBLE_STATE);
        guiButtons.get(Level_Name[0]).setEnabled(true);
        for (int i = 1; i < Level_Name.length; i++) {
            if (levelLimit[i]) {
                guiButtons.get(Level_Name[i]).setState(VISIBLE_STATE);
                guiButtons.get(Level_Name[i]).setEnabled(true);
            }
        }

        // AND CHANGE THE SCREEN STATE
        currentScreenState = SAGA_SCREEN_STATE;

    }

    public void smashValid() {
        guiButtons.get(SMASH_TYPE1).setState(VISIBLE_STATE);
        guiButtons.get(SMASH_TYPE1).setEnabled(true);
        guiButtons.get(SMASH_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(SMASH_TYPE).setEnabled(false);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        String imgPath = props.getProperty(ZombieCrushSagaPropertyType.IMG_PATH);
        String img = imgPath + "./zomjong/smashtool.png";
        Image image = this.loadImageWithColorKey(img, COLOR_KEY);
        Cursor mouse = toolkit.createCustomCursor(image, new Point(canvas.getX(),canvas.getY()), "");
        this.canvas.setCursor(mouse);
        this.smash = true;
    }

    public void smashInvalid() {
        guiButtons.get(SMASH_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(SMASH_TYPE).setEnabled(true);
        guiButtons.get(SMASH_TYPE1).setState(INVISIBLE_STATE);
        guiButtons.get(SMASH_TYPE1).setEnabled(false);
        this.canvas.setCursor(Cursor.getDefaultCursor());
        this.smash = false;
    }

    public void processSmash() {
        if (guiButtons.get(SMASH_TYPE).getState().equals(MOUSE_OVER_STATE)) {
            this.smashValid();
        } else {
            this.smashInvalid();
        }
    }

    public void switchToGameScreen() {
        guiDecor.get(BACKGROUND_TYPE).setState(GAME_SCREEN_STATE);
        closeAllButtons();

        guiButtons.get(BACK_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(BACK_BUTTON_TYPE).setEnabled(true);

        guiButtons.get(SMASH_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(SMASH_TYPE).setEnabled(true);

        guiDecor.get(SCORE_BAR_TYPE).setState(VISIBLE_STATE);
        guiDecor.get(SCORE_BAR_TYPE).setEnabled(true);

        guiDecor.get(PRINT_TYPE).setState(VISIBLE_STATE);
        guiDecor.get(PRINT_TYPE).setEnabled(true);
        guiDecor.get(NUM_LEVEL_TYPE).setState(VISIBLE_STATE);
        guiDecor.get(NUM_LEVEL_TYPE).setEnabled(true);
        guiDecor.get(HS_TYPE).setState(VISIBLE_STATE);
        guiDecor.get(HS_TYPE).setEnabled(true);
        guiDecor.get(TIME_TYPE).setState(VISIBLE_STATE);
        guiDecor.get(TIME_TYPE).setEnabled(true);

          // MOVE THE TILES TO THE STACK AND MAKE THEM VISIBLE
        ((ZombieCrushSagaDataModel) data).enableTiles(true);
        data.reset(this);

        currentScreenState = GAME_SCREEN_STATE;

    }

    public void switchToLevelScoreScreen() {
        if (((ZombieCrushSagaDataModel) data).inProgress()) {
            ((ZombieCrushSagaDataModel) data).saveCurrentGame();
        }
        guiDecor.get(BACKGROUND_TYPE).setState(LEVEL_SCREEN_STATE);
        closeAllButtons();
        // ACTIVATE THE TOOLBAR AND ITS CONTROLS
        guiButtons.get(PLAY_LEVEL_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(PLAY_LEVEL_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(CLOSE_LEVEL_SCORE_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(CLOSE_LEVEL_SCORE_BUTTON_TYPE).setEnabled(true);
        //   this.displayStats();
        int hs = (int) this.getPlayerRecord().getHighestScore(((ZombieCrushSagaDataModel) data).getCurrentLevel());
        int cs = (int) ((ZombieCrushSagaDataModel) data).getCurrentScore();
        int star1 = Level_1_Star[Integer.parseInt(((ZombieCrushSagaDataModel) data).getCurrentLevel().substring(5)) - 1];
        int star2 = Level_2_Star[Integer.parseInt(((ZombieCrushSagaDataModel) data).getCurrentLevel().substring(5)) - 1];
        int star3 = Level_3_Star[Integer.parseInt(((ZombieCrushSagaDataModel) data).getCurrentLevel().substring(5)) - 1];
        if (hs >= star3) {
            guiDecor.get(STAR_EARN_3_TYPE).setState(VISIBLE_STATE);
            guiDecor.get(STAR_EARN_3_TYPE).setEnabled(true);
        } else {
            if (hs >= star2) {
                guiDecor.get(STAR_EARN_2_TYPE).setState(VISIBLE_STATE);
                guiDecor.get(STAR_EARN_2_TYPE).setEnabled(true);
            } else if (hs >= star1) {
                guiDecor.get(STAR_EARN_1_TYPE).setState(VISIBLE_STATE);
                guiDecor.get(STAR_EARN_1_TYPE).setEnabled(true);
            }
        }
        if (cs >= star1 && this.data.won()) {
            guiDecor.get(STAR_1_TYPE).setState(VISIBLE_STATE);
            guiDecor.get(STAR_1_TYPE).setEnabled(true);
        }
        if (cs >= star2 && this.data.won()) {
            guiDecor.get(STAR_2_TYPE).setState(VISIBLE_STATE);
            guiDecor.get(STAR_2_TYPE).setEnabled(true);
        }
        if (cs >= star3 && this.data.won()) {
            guiDecor.get(STAR_3_TYPE).setState(VISIBLE_STATE);
            guiDecor.get(STAR_3_TYPE).setEnabled(true);
        }
        currentScreenState = LEVEL_SCREEN_STATE;
    }

    /**
     * This method switches the application to the splash screen, making all the
     * appropriate UI controls visible & invisible.
     */
    public void switchToSplashScreen() {
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(SPLASH_SCREEN_STATE);
        closeAllButtons();
        guiDecor.get(PLAY_GAME_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(PLAY_GAME_BUTTON_TYPE).setEnabled(true);
        guiDecor.get(RESET_GAME_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(RESET_GAME_BUTTON_TYPE).setEnabled(true);
        guiDecor.get(QUIT_GAME_BUTTON_TYPE).setState(VISIBLE_STATE);
        guiButtons.get(QUIT_GAME_BUTTON_TYPE).setEnabled(true);
        currentScreenState = SPLASH_SCREEN_STATE;
    }

    /**
     * This method updates the game grid boundaries, which will depend on the
     * level loaded.
     */
    public void updateBoundaries() {
        // NOTE THAT THE ONLY ONES WE CARE ABOUT ARE THE LEFT & TOP BOUNDARIES
        float totalWidth = ((ZombieCrushSagaDataModel) data).getGridColumns() * TILE_IMAGE_WIDTH;
        float halfTotalWidth = totalWidth / 2.0f;
        float halfViewportWidth = data.getGameWidth() / 2.0f;
        boundaryLeft = halfViewportWidth - halfTotalWidth;

        // THE LEFT & TOP BOUNDARIES ARE WHERE WE START RENDERING TILES IN THE GRID
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        float topOffset = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_TOP_OFFSET.toString()));
        float totalHeight = ((ZombieCrushSagaDataModel) data).getGridRows() * TILE_IMAGE_HEIGHT;
        float halfTotalHeight = totalHeight / 2.0f;
        float halfViewportHeight = (data.getGameHeight() - topOffset) / 2.0f;
        boundaryTop = topOffset + halfViewportHeight - halfTotalHeight;
    }

    // METHODS OVERRIDDEN FROM MiniGame
    // - initAudioContent
    // - initData
    // - initGUIControls
    // - initGUIHandlers
    // - reset
    // - updateGUI
    @Override
    /**
     * Initializes the sound and music to be used by the application.
     */
    public void initAudioContent() {

    }

    /**
     * Initializes the game data used by the application. Note that it is this
     * method's obligation to construct and set this Game's custom GameDataModel
     * object as well as any other needed game objects.
     */
    @Override
    public void initData() {
        // INIT OUR ERROR HANDLER
        errorHandler = new ZombieCrushSagaErrorHandler(window);

        // INIT OUR FILE MANAGER
        fileManager = new ZombieCrushSagaFileManager(this);

        // LOAD THE PLAYER'S RECORD FROM A FILE
        record = fileManager.loadRecord();

        // INIT OUR DATA MANAGER
        data = new ZombieCrushSagaDataModel(this);

        // LOAD THE GAME DIMENSIONS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        int gameWidth = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_WIDTH.toString()));
        int gameHeight = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_HEIGHT.toString()));
        data.setGameDimensions(gameWidth, gameHeight);

        // THIS WILL CHANGE WHEN WE LOAD A LEVEL
        boundaryLeft = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_LEFT_OFFSET.toString()));
        boundaryTop = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_TOP_OFFSET.toString()));
        boundaryRight = gameWidth - boundaryLeft;
        boundaryBottom = gameHeight;
    }

    /**
     * Initializes the game controls, like buttons, used by the game
     * application. Note that this includes the tiles, which serve as buttons of
     * sorts.
     */
    @Override
    public void initGUIControls() {
        // WE'LL USE AND REUSE THESE FOR LOADING STUFF
        BufferedImage img;
        float x, y;
        SpriteType sT;
        Sprite s;

        // FIRST PUT THE ICON IN THE WINDOW
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(ZombieCrushSagaPropertyType.IMG_PATH);

        String windowIconFile = props.getProperty(ZombieCrushSagaPropertyType.WINDOW_ICON);
        img = loadImage(imgPath + windowIconFile);
        window.setIconImage(img);

        // CONSTRUCT THE PANEL WHERE WE'LL DRAW EVERYTHING
        canvas = new ZombieCrushSagaPanel(this, (ZombieCrushSagaDataModel) data);

        // LOAD THE BACKGROUNDS, WHICH ARE GUI DECOR
        currentScreenState = SPLASH_SCREEN_STATE;
        sT = new SpriteType(BACKGROUND_TYPE);

        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.GAME_BACKGROUND_IMAGE_NAME));
        sT.addState(GAME_SCREEN_STATE, img);
        String path = props.getProperty(ZombieCrushSagaPropertyType.SAGA_SCREEN_IMAGE_NAME);
        for (int i = 1; i <= 12; i++) {
            img = loadImage(path.substring(0, 16) + i + ".png");
            sT.addState(SAGA_SCREEN_STATE + i, img);
        }
        currentSaga = 1;
        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.SPLASH_SCREEN_IMAGE_NAME));
        sT.addState(SPLASH_SCREEN_STATE, img);

        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.LEVEL_SCREEN_IMAGE_NAME));
        sT.addState(LEVEL_SCREEN_STATE, img);
        s = new Sprite(sT, 0, 0, 0, 0, SPLASH_SCREEN_STATE);
        guiDecor.put(BACKGROUND_TYPE, s);

        float gameWidth = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_WIDTH));
        float gameHeight = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_HEIGHT));
        x = 0;
        y = Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_HEIGHT)) - 110;

        // PlayGameButton
        sT = new SpriteType(PLAY_GAME_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.PLAY_GAME_IMAGE_NAME));
        sT.addState(VISIBLE_STATE, img);

        img = loadImage(imgPath + props.getProperty(ZombieCrushSagaPropertyType.PLAY_GAME_MOUSE_OVER_IMAGE_NAME));
        sT.addState(MOUSE_OVER_STATE, img);

        s = new Sprite(sT, gameWidth - 210, y, 0, 0, VISIBLE_STATE);
        guiButtons.put(PLAY_GAME_BUTTON_TYPE, s);

        x = gameWidth / 2 - 100;

        // reset button
        sT = new SpriteType(RESET_GAME_BUTTON_TYPE);
        img = loadImageWithColorKey(imgPath + props.getProperty(ZombieCrushSagaPropertyType.RESET_GAME_IMAGE_NAME), COLOR_KEY);

        sT.addState(VISIBLE_STATE, img);
        img = loadImageWithColorKey(imgPath + props.getProperty(ZombieCrushSagaPropertyType.RESET_GAME_MOUSE_OVER_IMAGE_NAME), COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, x, y, 0, 0, VISIBLE_STATE);
        guiButtons.put(RESET_GAME_BUTTON_TYPE, s);
        x = gameWidth - 210;

        //  QuitGameButton
        sT = new SpriteType(QUIT_GAME_BUTTON_TYPE);
        String quit = props.getProperty(ZombieCrushSagaPropertyType.QUIT_GAME_IMAGE_NAME);

        img = loadImageWithColorKey(imgPath + quit, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String quitOver = props.getProperty(ZombieCrushSagaPropertyType.QUIT_GAME_MOUSE_OVER_IMAGE_NAME);
        img = loadImageWithColorKey(imgPath + quitOver, COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, 0, y, 0, 0, VISIBLE_STATE);
        guiButtons.put(QUIT_GAME_BUTTON_TYPE, s);

        //play level button
        sT = new SpriteType(PLAY_LEVEL_BUTTON_TYPE);
        img = loadImageWithColorKey(imgPath + props.getProperty(ZombieCrushSagaPropertyType.PLAY_LEVEL_IMAGE_NAME), COLOR_KEY);

        sT.addState(VISIBLE_STATE, img);
        img = loadImageWithColorKey(imgPath + props.getProperty(ZombieCrushSagaPropertyType.PLAY_LEVEL_MOUSE_OVER_IMAGE_NAME), COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(PLAY_LEVEL_BUTTON_TYPE, s);
        x = gameWidth - 150 - 200;
        
        //close level score button
        sT = new SpriteType(CLOSE_LEVEL_SCORE_BUTTON_TYPE);
        img = loadImageWithColorKey(imgPath + props.getProperty(ZombieCrushSagaPropertyType.CLOSE_LEVEL_SCORE_IMAGE_NAME), COLOR_KEY);

        sT.addState(VISIBLE_STATE, img);
        img = loadImageWithColorKey(imgPath + props.getProperty(ZombieCrushSagaPropertyType.CLOSE_LEVEL_SCORE_MOUSE_OVER_IMAGE_NAME), COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(CLOSE_LEVEL_SCORE_BUTTON_TYPE, s);
        x = gameWidth - 150 + 10;

        // up button
        sT = new SpriteType(UP_BUTTON_TYPE);
        String up = props.getProperty(ZombieCrushSagaPropertyType.UP_BUTTON_IMAGE_NAME);

        img = loadImageWithColorKey(imgPath + up, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String upOver = props.getProperty(ZombieCrushSagaPropertyType.UP_BUTTON_MOUSE_OVER_IMAGE_NAME);
        img = loadImageWithColorKey(imgPath + upOver, COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, 0, y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(UP_BUTTON_TYPE, s);

        // down button
        sT = new SpriteType(DOWN_BUTTON_TYPE);
        String down = props.getProperty(ZombieCrushSagaPropertyType.DOWN_BUTTON_IMAGE_NAME);

        img = loadImageWithColorKey(imgPath + down, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String downOver = props.getProperty(ZombieCrushSagaPropertyType.DOWN_BUTTON_MOUSE_OVER_IMAGE_NAME);

        img = loadImageWithColorKey(imgPath + downOver, COLOR_KEY);

        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, gameWidth / 2 - 100, y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(DOWN_BUTTON_TYPE, s);

        // down_s 
        sT = new SpriteType(DOWN_S_BUTTON_TYPE);
        String downS = props.getProperty(ZombieCrushSagaPropertyType.DOWN_S_BUTTON_IMAGE_NAME);

        img = loadImageWithColorKey(imgPath + downS, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String downSOver = props.getProperty(ZombieCrushSagaPropertyType.DOWN_S_BUTTON_MOUSE_OVER_IMAGE_NAME);
        img = loadImageWithColorKey(imgPath + downSOver, COLOR_KEY);

        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, gameWidth - 60, gameHeight / 2 + 67, 0, 0, INVISIBLE_STATE);
        guiButtons.put(DOWN_S_BUTTON_TYPE, s);
        // up_s
        sT = new SpriteType(UP_S_BUTTON_TYPE);
        String upS = props.getProperty(ZombieCrushSagaPropertyType.UP_S_BUTTON_IMAGE_NAME);

        img = loadImageWithColorKey(imgPath + upS, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String upSOver = props.getProperty(ZombieCrushSagaPropertyType.UP_S_BUTTON_MOUSE_OVER_IMAGE_NAME);

        img = loadImageWithColorKey(imgPath + upSOver, COLOR_KEY);

        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, gameWidth - 60, gameHeight / 2 - 67 - 47, 0, 0, INVISIBLE_STATE);
        guiButtons.put(UP_S_BUTTON_TYPE, s);
        // quit_s
        sT = new SpriteType(QUIT_S_BUTTON_TYPE);
        String quitS = props.getProperty(ZombieCrushSagaPropertyType.QUIT_S_BUTTON_IMAGE_NAME);

        img = loadImageWithColorKey(imgPath + quitS, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String quitSOver = props.getProperty(ZombieCrushSagaPropertyType.QUIT_S_BUTTON_MOUSE_OVER_IMAGE_NAME);

        img = loadImageWithColorKey(imgPath + quitSOver, COLOR_KEY);

        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, gameWidth - 70, gameHeight / 2, 0, 0, INVISIBLE_STATE);
        guiButtons.put(QUIT_S_BUTTON_TYPE, s);
        // THEN THE BACK BUTTON
        String backButton = props.getProperty(ZombieCrushSagaPropertyType.BACK_IMAGE_NAME);
        sT = new SpriteType(BACK_BUTTON_TYPE);
        img = loadImage(imgPath + backButton);
        sT.addState(VISIBLE_STATE, img);
        String backMouseOverButton = props.getProperty(ZombieCrushSagaPropertyType.BACK_MOUSE_OVER_IMAGE_NAME);
        img = loadImage(imgPath + backMouseOverButton);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, 0, BACK_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        guiButtons.put(BACK_BUTTON_TYPE, s);

        // THEN THE BACK BUTTON
        String smashButton = "./zomjong/smash.png";
        sT = new SpriteType(SMASH_TYPE);
        img = loadImage(imgPath + smashButton);
        sT.addState(VISIBLE_STATE, img);

        img = loadImage(imgPath + "./zomjong/smash.png");
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, 1000, 600, 0, 0, INVISIBLE_STATE);
        guiButtons.put(SMASH_TYPE, s);

        // THEN THE BACK BUTTON
        String smashButton1 = "./zomjong/smash1.png";
        sT = new SpriteType(SMASH_TYPE1);
        img = loadImage(imgPath + smashButton1);
        sT.addState(VISIBLE_STATE, img);

        img = loadImage(imgPath + "./zomjong/smash1.png");
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, 1000, 600, 0, 0, INVISIBLE_STATE);
        guiButtons.put(SMASH_TYPE1, s);

        //  print moves
        String moves = props.getProperty(ZombieCrushSagaPropertyType.PRINT_MODEL_IMAGE_NAME);
        sT = new SpriteType(PRINT_TYPE);
        img = loadImage(imgPath + moves);
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, BACK_BUTTON_X, BACK_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        guiDecor.put(PRINT_TYPE, s);
        // print HS
        String times = props.getProperty(ZombieCrushSagaPropertyType.PRINT_MODEL_IMAGE_NAME);
        sT = new SpriteType(HS_TYPE);
        img = loadImage(imgPath + times);
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, BACK_BUTTON_X + img.getWidth(), BACK_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        guiDecor.put(HS_TYPE, s);
        // print level
        String level = props.getProperty(ZombieCrushSagaPropertyType.PRINT_MODEL_IMAGE_NAME);
        sT = new SpriteType(NUM_LEVEL_TYPE);
        img = loadImage(imgPath + moves);
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, BACK_BUTTON_X + img.getWidth() * 2, BACK_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        guiDecor.put(NUM_LEVEL_TYPE, s);
        // //  Score bar
        String scoreBar = props.getProperty(ZombieCrushSagaPropertyType.SCORE_BAR_IMAGE_NAME);
        sT = new SpriteType(SCORE_BAR_TYPE);
        img = loadImage(imgPath + scoreBar);
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_WIDTH)) - img.getWidth(), 0, 0, 0, INVISIBLE_STATE);
        guiDecor.put(SCORE_BAR_TYPE, s);
        // star 1
        String star1 = props.getProperty(ZombieCrushSagaPropertyType.STAR_1_IMAGE_NAME);
        System.out.println(star1);
        sT = new SpriteType(STAR_1_TYPE);
        img = loadImage(imgPath + star1);
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, 531, 652, 0, 0, INVISIBLE_STATE);
        guiDecor.put(STAR_1_TYPE, s);
        // star 2
        String star2 = props.getProperty(ZombieCrushSagaPropertyType.STAR_2_IMAGE_NAME);
        sT = new SpriteType(STAR_2_TYPE);
        img = loadImage(imgPath + star2);
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, 620, 590, 0, 0, INVISIBLE_STATE);
        guiDecor.put(STAR_2_TYPE, s);
        //star 3
        String star3 = props.getProperty(ZombieCrushSagaPropertyType.STAR_3_IMAGE_NAME);
        sT = new SpriteType(STAR_3_TYPE);
        img = loadImage(imgPath + star3);
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, 734, 653, 0, 0, INVISIBLE_STATE);
        guiDecor.put(STAR_3_TYPE, s);
        // star earn 1
        String starEarn1 = props.getProperty(ZombieCrushSagaPropertyType.STAR_EARN_1_IMAGE_NAME);
        sT = new SpriteType(STAR_EARN_1_TYPE);
        img = loadImage(imgPath + starEarn1);
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, 276, 248, 0, 0, INVISIBLE_STATE);
        guiDecor.put(STAR_EARN_1_TYPE, s);
        // star earn 2
        String starEarn2 = props.getProperty(ZombieCrushSagaPropertyType.STAR_EARN_2_IMAGE_NAME);
        sT = new SpriteType(STAR_EARN_2_TYPE);
        img = loadImage(imgPath + starEarn2);
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, 276, 248, 0, 0, INVISIBLE_STATE);
        guiDecor.put(STAR_EARN_2_TYPE, s);
        // star earn 3
        String starEarn3 = props.getProperty(ZombieCrushSagaPropertyType.STAR_EARN_3_IMAGE_NAME);
        sT = new SpriteType(STAR_EARN_3_TYPE);
        img = loadImage(imgPath + starEarn3);
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, 276, 248, 0, 0, INVISIBLE_STATE);
        guiDecor.put(STAR_EARN_3_TYPE, s);
         // Level

        String[] levelType = Level_Name;
        for (int i = 0; i < levelType.length; i++) {
            sT = new SpriteType(LEVEL_SCREEN_STATE);
            img = loadImageWithColorKey(imgPath + props.getProperty(ZombieCrushSagaPropertyType.LEVEL_IMAGE_NAME), COLOR_KEY);
            sT.addState(VISIBLE_STATE, img);
            img = loadImageWithColorKey(imgPath + props.getProperty(ZombieCrushSagaPropertyType.LEVEL_MOUSE_OVER_IMAGE_NAME), COLOR_KEY);
            sT.addState(MOUSE_OVER_STATE, img);
            s = new Sprite(sT, pos_x[i] - 15, pos_y - 20, 0, 0, INVISIBLE_STATE);
            guiButtons.put(levelType[i], s);
            x += 20;
            y += 20;
        }

        // AND THE TIME DISPLAY
        String timeContainer = props.getProperty(ZombieCrushSagaPropertyType.TIME_IMAGE_NAME);
        sT = new SpriteType(TIME_TYPE);
        img = loadImage(imgPath + timeContainer);
        sT.addState(VISIBLE_STATE, img);
        s = new Sprite(sT, BACK_BUTTON_X + 190 * 3, BACK_BUTTON_Y, 0, 0, INVISIBLE_STATE);
        guiDecor.put(TIME_TYPE, s);
        // AND THE WIN CONDITION DISPLAY
        String winDisplay = props.getProperty(ZombieCrushSagaPropertyType.WIN_DIALOG_IMAGE_NAME);
        sT = new SpriteType(WIN_DIALOG_TYPE);
        img = loadImageWithColorKey(imgPath + winDisplay, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        x = (data.getGameWidth() / 2) - (img.getWidth(null) / 2);
        y = (data.getGameHeight() / 2) - (img.getHeight(null) / 2);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE_STATE);
        guiDialogs.put(WIN_DIALOG_TYPE, s);

        // AND THE LOSS CONDITION DISPLAY
        String lossDisplay = props.getProperty(ZombieCrushSagaPropertyType.LOSS_DIALOG_IMAGE_NAME);
        sT = new SpriteType(LOSS_DIALOG_TYPE);
        img = loadImageWithColorKey(imgPath + lossDisplay, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        x = (data.getGameWidth() / 2) - (img.getWidth(null) / 2);
        y = (data.getGameHeight() / 2) - (img.getHeight(null) / 2);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE_STATE);
        guiDialogs.put(LOSS_DIALOG_TYPE, s);
        ((ZombieCrushSagaDataModel) data).initTiles();
    }

    /**
     * Initializes the game event handlers for things like game gui buttons.
     */
    @Override
    public void initGUIHandlers() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String dataPath = props.getProperty(ZombieCrushSagaPropertyType.DATA_PATH);

        // WE'LL HAVE A CUSTOM RESPONSE FOR WHEN THE USER CLOSES THE WINDOW
        PlayGameHandler pgh = new PlayGameHandler(this);
        guiButtons.get(PLAY_GAME_BUTTON_TYPE).setActionListener(pgh);
        guiButtons.get(CLOSE_LEVEL_SCORE_BUTTON_TYPE).setActionListener(pgh);
        // Reset game even handler
        ResetGameHandler rgh = new ResetGameHandler(this);
        guiButtons.get(RESET_GAME_BUTTON_TYPE).setActionListener(rgh);
        // Quit  even handler
        QuitHandler qh = new QuitHandler(this);
        guiButtons.get(QUIT_GAME_BUTTON_TYPE).setActionListener(qh);
        guiButtons.get(QUIT_S_BUTTON_TYPE).setActionListener(qh);
        // up button
        UpHandler uh = new UpHandler(this);
        guiButtons.get(UP_BUTTON_TYPE).setActionListener(uh);
        guiButtons.get(UP_S_BUTTON_TYPE).setActionListener(uh);
        DownHandler dh = new DownHandler(this);
        guiButtons.get(DOWN_BUTTON_TYPE).setActionListener(dh);
        guiButtons.get(DOWN_S_BUTTON_TYPE).setActionListener(dh);

        BackHandler bh = new BackHandler(this);
        guiButtons.get(BACK_BUTTON_TYPE).setActionListener(bh);

        SmashHandler sh = new SmashHandler(this);
        guiButtons.get(SMASH_TYPE).setActionListener(sh);
        guiButtons.get(SMASH_TYPE1).setActionListener(sh);
        PlayLevelHandler plh = new PlayLevelHandler(this);
        guiButtons.get(PLAY_LEVEL_BUTTON_TYPE).setActionListener(plh);

        for (String levelFile : Level_Name) {
            SelectLevelHandler slh = new SelectLevelHandler(this, levelFile);
            guiButtons.get(levelFile).setActionListener(slh);
        }
        ZombieCrushSagaKeyHandler mkh = new ZombieCrushSagaKeyHandler(this);
        this.setKeyListener(mkh);

    }

    /**
     * Invoked when a new game is started, it resets all relevant game data and
     * gui control states.
     */
    @Override
    public void reset() {
        data.reset(this);
    }

    /**
     * Updates the state of all gui controls according to the current game
     * conditions.
     */
    @Override
    public void updateGUI() {
        // GO THROUGH THE VISIBLE BUTTONS TO TRIGGER MOUSE OVERS
        Iterator<Sprite> buttonsIt = guiButtons.values().iterator();
        while (buttonsIt.hasNext()) {
            Sprite button = buttonsIt.next();

            // ARE WE ENTERING A BUTTON?
            if (button.getState().equals(VISIBLE_STATE)) {
                if (button.containsPoint(data.getLastMouseX(), data.getLastMouseY())) {
                    button.setState(MOUSE_OVER_STATE);
                }
            } // ARE WE EXITING A BUTTON?
            else if (button.getState().equals(MOUSE_OVER_STATE)) {
                if (!button.containsPoint(data.getLastMouseX(), data.getLastMouseY())) {
                    button.setState(VISIBLE_STATE);
                }
            }
        }
    }

    public void resetSagaButtons(int y) {
        guiButtons.remove(QUIT_GAME_BUTTON_TYPE);
        guiButtons.remove(UP_BUTTON_TYPE);
        guiButtons.remove(DOWN_BUTTON_TYPE);
        Sprite s;
        SpriteType sT;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        sT = new SpriteType(QUIT_GAME_BUTTON_TYPE);
        String quit = props.getProperty(ZombieCrushSaga.ZombieCrushSaga.ZombieCrushSagaPropertyType.QUIT_GAME_IMAGE_NAME);
        String imgPath = props.getProperty(ZombieCrushSagaPropertyType.IMG_PATH);
        BufferedImage img;
        img = loadImageWithColorKey(imgPath + quit, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String quitOver = props.getProperty(ZombieCrushSaga.ZombieCrushSaga.ZombieCrushSagaPropertyType.QUIT_GAME_MOUSE_OVER_IMAGE_NAME);
        img = loadImageWithColorKey(imgPath + quitOver, COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_WIDTH)) - 210, Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_HEIGHT)) - 110 - y, 0, 0, VISIBLE_STATE);
        guiButtons.put(QUIT_GAME_BUTTON_TYPE, s);

        QuitHandler qh = new QuitHandler(this);
        guiButtons.get(QUIT_GAME_BUTTON_TYPE).setActionListener(qh);

        // up button
        sT = new SpriteType(UP_BUTTON_TYPE);
        String up = props.getProperty(ZombieCrushSagaPropertyType.UP_BUTTON_IMAGE_NAME);

        img = loadImageWithColorKey(imgPath + up, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String upOver = props.getProperty(ZombieCrushSagaPropertyType.UP_BUTTON_MOUSE_OVER_IMAGE_NAME);
        img = loadImageWithColorKey(imgPath + upOver, COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, 0, Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_HEIGHT)) - 110 - y, 0, 0, VISIBLE_STATE);
        guiButtons.put(UP_BUTTON_TYPE, s);

        UpHandler uh = new UpHandler(this);
        guiButtons.get(UP_BUTTON_TYPE).setActionListener(uh);

        // down button
        sT = new SpriteType(DOWN_BUTTON_TYPE);
        String down = props.getProperty(ZombieCrushSagaPropertyType.DOWN_BUTTON_IMAGE_NAME);

        img = loadImageWithColorKey(imgPath + down, COLOR_KEY);
        sT.addState(VISIBLE_STATE, img);
        String downOver = props.getProperty(ZombieCrushSagaPropertyType.DOWN_BUTTON_MOUSE_OVER_IMAGE_NAME);
        img = loadImageWithColorKey(imgPath + downOver, COLOR_KEY);
        sT.addState(MOUSE_OVER_STATE, img);
        s = new Sprite(sT, Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_WIDTH)) / 2 - 100, Integer.parseInt(props.getProperty(ZombieCrushSagaPropertyType.GAME_HEIGHT)) - 110 - y, 0, 0, VISIBLE_STATE);
        guiButtons.put(DOWN_BUTTON_TYPE, s);
        DownHandler dh = new DownHandler(this);
        guiButtons.get(DOWN_BUTTON_TYPE).setActionListener(dh);

    }

    public void closeAllButtons() {
        guiDialogs.get(WIN_DIALOG_TYPE).setState(INVISIBLE_STATE);
        guiDialogs.get(LOSS_DIALOG_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(UP_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(UP_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(DOWN_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(DOWN_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(PLAY_LEVEL_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(PLAY_LEVEL_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(CLOSE_LEVEL_SCORE_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(CLOSE_LEVEL_SCORE_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(BACK_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(BACK_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(SMASH_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(SMASH_TYPE).setEnabled(false);

        guiButtons.get(SMASH_TYPE1).setState(INVISIBLE_STATE);
        guiButtons.get(SMASH_TYPE1).setEnabled(false);
        guiButtons.get(PLAY_GAME_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(PLAY_GAME_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(RESET_GAME_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(RESET_GAME_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(QUIT_GAME_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(QUIT_GAME_BUTTON_TYPE).setEnabled(false);
        guiDecor.get(SCORE_BAR_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(SCORE_BAR_TYPE).setEnabled(false);
        for (String level : Level_Name) {
            guiButtons.get(level).setState(INVISIBLE_STATE);
            guiButtons.get(level).setEnabled(false);
        }
        guiDecor.get(TIME_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(TIME_TYPE).setEnabled(false);
        guiDecor.get(PRINT_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(PRINT_TYPE).setEnabled(false);
        guiDecor.get(NUM_LEVEL_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(NUM_LEVEL_TYPE).setEnabled(false);
        guiDecor.get(HS_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(HS_TYPE).setEnabled(false);
        guiButtons.get(UP_S_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(UP_S_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(DOWN_S_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(DOWN_S_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(QUIT_S_BUTTON_TYPE).setState(INVISIBLE_STATE);
        guiButtons.get(QUIT_S_BUTTON_TYPE).setEnabled(false);

        guiDecor.get(STAR_EARN_1_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(STAR_EARN_1_TYPE).setEnabled(false);
        guiDecor.get(STAR_EARN_2_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(STAR_EARN_2_TYPE).setEnabled(false);
        guiDecor.get(STAR_EARN_3_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(STAR_EARN_3_TYPE).setEnabled(false);
        guiDecor.get(STAR_1_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(STAR_1_TYPE).setEnabled(false);
        guiDecor.get(STAR_2_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(STAR_2_TYPE).setEnabled(false);
        guiDecor.get(STAR_3_TYPE).setState(INVISIBLE_STATE);
        guiDecor.get(STAR_3_TYPE).setEnabled(false);
        
        ((ZombieCrushSagaDataModel) data).enableTiles(false);
    }

    public void moveUp() {
        if (currentSaga < 12) {
            currentSaga++;
            guiDecor.get(BACKGROUND_TYPE).setState(SAGA_SCREEN_STATE + currentSaga);
            clearLevel();
        }
    }

    public void moveDown() {

        if (currentSaga > 1) {
            currentSaga--;
            guiDecor.get(BACKGROUND_TYPE).setState(SAGA_SCREEN_STATE + currentSaga);
            clearLevel();
        }
    }

    private void clearLevel() {
        if (currentSaga != 1) {
            for (String level : Level_Name) {
                guiButtons.get(level).setState(INVISIBLE_STATE);
                guiButtons.get(level).setEnabled(false);
            }
        } else {
            guiButtons.get(Level_Name[0]).setState(VISIBLE_STATE);
            guiButtons.get(Level_Name[0]).setEnabled(true);
            for (int i = 1; i < Level_Name.length; i++) {
                if (levelLimit[i]) {
                    guiButtons.get(Level_Name[i]).setState(VISIBLE_STATE);
                    guiButtons.get(Level_Name[i]).setEnabled(true);
                }
            }
        }
    }
}
