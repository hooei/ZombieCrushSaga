package ZombieCrushSaga.data;

import ZombieCrushSaga.ui.ZombieCrushSagaTile;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import ZombieCrushSaga.ZombieCrushSaga.ZombieCrushSagaPropertyType;
import mini_game.MiniGame;
import mini_game.MiniGameDataModel;
import mini_game.SpriteType;
import properties_manager.PropertiesManager;
import static ZombieCrushSaga.ZombieCrushSagaConstants.*;
import ZombieCrushSaga.ui.ZombieCrushSagaMiniGame;
import ZombieCrushSaga.ui.ZombieCrushSagaPanel;


/**
 * This class manages the game data for Mahjong Solitaire.
 *
 * @author Zhenjin Wang
 */
public class ZombieCrushSagaDataModel extends MiniGameDataModel {
    // THIS CLASS HAS A REFERERENCE TO THE MINI GAME SO THAT IT
    // CAN NOTIFY IT TO UPDATE THE DISPLAY WHEN THE DATA MODEL CHANGES

    private double speed;
    private MiniGame miniGame;
    // THE LEVEL GRID REFERS TO THE LAYOUT FOR A GIVEN LEVEL, MEANING
    // HOW MANY TILES FIT INTO EACH CELL WHEN FIRST STARTING A LEVEL
    private int[][] levelGrid;
    private int addOther;
    // LEVEL GRID DIMENSIONS
    private int gridColumns;
    private int gridRows;
    private boolean startPlaying;
    private ArrayList<int[]> currentNeedToMove;
    // THIS STORES THE TILES ON THE GRID DURING THE GAME
    private ArrayList<ZombieCrushSagaTile>[][] tileGrid;

    private ArrayList<ZombieCrushSagaTile>[][] currentGrid;
    // THESE ARE THE TILES THE PLAYER HAS MATCHED
    private ArrayList<ZombieCrushSagaTile> stackTiles;
    //   using to fill
    private ArrayList<ZombieCrushSagaTile> sixTiles;
    //   special tile
    private ArrayList<ZombieCrushSagaTile>[] wrappedTiles;
    private ArrayList<ZombieCrushSagaTile>[] stripedTiles;
    private ArrayList<ZombieCrushSagaTile>[] glassTiles;
    private ArrayList<ZombieCrushSagaTile> colorTiles;

    private ArrayList<ZombieCrushSagaTile>[] testTiles;
    // THESE ARE THE TILES THAT ARE MOVING AROUND, AND SO WE HAVE TO UPDATE
    private ArrayList<ZombieCrushSagaTile> movingTiles;
    // THIS IS A SELECTED TILE, MEANING THE FIRST OF A PAIR THE PLAYER
    // IS TRYING TO MATCH. THERE CAN ONLY BE ONE OF THESE AT ANY TIME
    private ZombieCrushSagaTile selectedTile;
    private ZombieCrushSagaTile selectTile;
    // THE INITIAL LOCATION OF TILES BEFORE BEING PLACED IN THE GRID
    private int unassignedTilesX;
    private int unassignedTilesY;
    // THESE ARE USED FOR TIMING THE GAME
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private long currentScore;
    private int currentMoves;
    // THE REFERENCE TO THE FILE BEING PLAYED
    private String currentLevel;
    private ArrayList<Integer> emptyCol;
    private int levelMultiplier;
    private int Scoretimes;
    private boolean stop;
    private int addToPos;
    private boolean finishExchange;
    private boolean startClear;
    private boolean startFillEmptyCol;
    private boolean beginUpdataBoard;
    private boolean legalMove;
    private ZombieCrushSagaTile replacingSpecial;
    private ZombieCrushSagaTile[][] backGrid;
    private boolean isBom;
    private int[] Bom;
    private ArrayList<ZombieCrushSagaTile> jellyTiles; //  jerry
    private ArrayList<ZombieCrushSagaTile> backTiles;
    private int[][] score;
    private ZombieCrushSagaTile smarsh;
    private boolean secondBom;
    private boolean bom;

    /**
     * Constructor for initializing this data model, it will create the data
     * structures for storing tiles, but not the tile grid itself, that is
     * dependent of file loading, and so should be subsequently initialized.
     *
     * @param initMiniGame The Mahjong game UI.
     */
    public ZombieCrushSagaDataModel(MiniGame initMiniGame) {
        // KEEP THE GAME FOR LATER
        miniGame = initMiniGame;
        currentScore = 0;
        this.currentMoves = 0;
        currentNeedToMove = new ArrayList<int[]>();
        // INIT THESE FOR HOLDING MATCHED AND MOVING TILES
        stackTiles = new ArrayList();
        sixTiles = new ArrayList();
        movingTiles = new ArrayList();
        emptyCol = new ArrayList<Integer>();
        Bom = new int[2];
        wrappedTiles = new ArrayList[6];
        for (int i = 0; i < 6; i++) {
            wrappedTiles[i] = new ArrayList();
        }
        stripedTiles = new ArrayList[6];
        for (int i = 0; i < 6; i++) {
            stripedTiles[i] = new ArrayList();
        }
        glassTiles = new ArrayList[6];
        for (int i = 0; i < 6; i++) {
            glassTiles[i] = new ArrayList();
        }

        colorTiles = new ArrayList<ZombieCrushSagaTile>();

        jellyTiles = new ArrayList<ZombieCrushSagaTile>();
        backTiles = new ArrayList<ZombieCrushSagaTile>();
        testTiles = new ArrayList[6];
        for (int i = 0; i < 6; i++) {
            testTiles[i] = new ArrayList();
        }
        addOther = 0;
        startPlaying = false;
        speed = 70;
        stop = false;
        Scoretimes = 1;
    }
   // ACCESSOR METHODS
    /**
     * Accessor method for getting the level currently being played.
     *
     * @return The level name used currently for the game screen.
     */
    public String getCurrentLevel() {
        return currentLevel;
    }

    public long getCurrentScore() {
        return currentScore;
    }

    public long getCurrentMoves() {
        return currentMoves;
    }

    /**
     * Accessor method for getting the number of tile columns in the game grid.
     *
     * @return The number of columns (left to right) in the grid for the level
     * currently loaded.
     */
    public int getGridColumns() {
        return gridColumns;
    }

    /**
     * Accessor method for getting the number of tile rows in the game grid.
     *
     * @return The number of rows (top to bottom) in the grid for the level
     * currently loaded.
     */
    public int getGridRows() {
        return gridRows;
    }

    /**
     * Accessor method for getting the tile grid, which has all the tiles the
     * user may select from.
     *
     * @return The main 2D grid of tiles the user selects tiles from.
     */
    public ArrayList<ZombieCrushSagaTile>[][] getTileGrid() {
        return tileGrid;
    }

    public ArrayList<ZombieCrushSagaTile> getSixTiles() {
        return this.sixTiles;
    }

    /**
     * Accessor method for getting the stack tiles.
     *
     * @return The stack tiles, which are the tiles the matched tiles are placed
     * in.
     */
    public ArrayList<ZombieCrushSagaTile> getStackTiles() {
        return stackTiles;
    }

    /**
     * Accessor method for getting the moving tiles.
     *
     * @return The moving tiles, which are the tiles currently being animated as
     * they move around the game.
     */
    public Iterator<ZombieCrushSagaTile> getMovingTiles() {
        return movingTiles.iterator();
    }
    
      public int[][] getScore() {
        return score;
    }

    public double getSpeed() {
        return this.speed;
    }

    public ArrayList<Integer> getEmptyCol() {
        return this.emptyCol;
    }
    public ZombieCrushSagaTile  getSelectTile(){
        return this.selectTile;
    }
    public ZombieCrushSagaTile  getSelectedTile(){
        return this.selectedTile;
    }
    public int getScoreTimes(){
        return this.Scoretimes;
    }
    public boolean getBom(){
        return this.bom;
    }
    public boolean getSecondBom(){
        return this.secondBom;
    }
    public boolean getLegalMove(){
        return this.legalMove;
    }
    public boolean getBeginUpdataBoard(){
        return this.beginUpdataBoard;
    }
    public boolean getFinishExchange(){
        return this.finishExchange;
    }
    public boolean getStartClear(){
        return this.startClear;
    }
    public boolean getStartFillEmptyCol(){
        return this.startFillEmptyCol;
    }
    /**
     * Mutator method for setting the currently loaded level.
     *
     * @param initCurrentLevel The level name currently being used to play the
     * game.
     */
    public void setCurrentLevel(String initCurrentLevel) {
        currentLevel = initCurrentLevel;
    }
    public void setData(int score, int moves) {
        this.currentMoves = 0;
        this.currentScore = 0;
    }
    public void setSelectTile(ZombieCrushSagaTile tile){
       this.selectTile=tile;
    }
    public void setSelectedTile(ZombieCrushSagaTile tile){
       this.selectTile=tile;
    }
    public void setStart(boolean t) {
        this.startPlaying = t;
    }
    public void setLevelMultiplier(int lm) {
        this.levelMultiplier = lm;
    }
    public void setScoreTimes(int st){
        this.Scoretimes=st;
    }
    public void setBom(boolean status){
        this.bom=status;
    }
    public void setSecondBom(boolean status){
        this.secondBom=status;
    }
    public void setLegalMove(boolean status){
        this.legalMove=status;
    }
    public void setBeginUpdataBoard(boolean status){
        this.beginUpdataBoard=status;
    }
    public void setFinishExchange(boolean status){
        this.finishExchange=status;
    }
    public void setStartClear(boolean status){
        this.startClear=status;
    }
    public void setStartFillEmptyCol(boolean status){
        this.startFillEmptyCol=status;
    }
    public boolean hasScoreToShow() {
        for (int i = 0; i < score.length; i++) {
            for (int j = 0; j < score[0].length; j++) {
                if (score[i][j] != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void clearScore() {
        for (int i = 0; i < score.length; i++) {
            for (int j = 0; j < score[0].length; j++) {
                score[i][j] = 0;
            }
        }

    }

    // INIT METHODS - AFTER CONSTRUCTION, THESE METHODS SETUP A GAME FOR USE
    // - initTiles
    // - initTile
    // - initLevelGrid
    // - initSpriteType
    /**
     * This method loads the tiles, creating an individual sprite for each. Note
     * that tiles may be of various types, which is important during the tile
     * matching tests.
     */
    public void initTiles() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(ZombieCrushSagaPropertyType.IMG_PATH);
        int spriteTypeID = 0;
        SpriteType sT;
        // THIS IS A HIGHLIGHTED BLANK TILE FOR WHEN THE PLAYER SELECTS ONE
        String blankTileSelectedFileName = props.getProperty(ZombieCrushSagaPropertyType.BLANK_TILE_SELECTED_IMAGE_NAME);
        BufferedImage blankTileSelectedImage = miniGame.loadImageWithColorKey(imgPath + blankTileSelectedFileName, COLOR_KEY);
        ((ZombieCrushSagaPanel) (miniGame.getCanvas())).setBlankTileSelectedImage(blankTileSelectedImage);
        ArrayList<String> typeCTiles = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.TYPE_C_TILES);
        int x = (int) Math.random() * 6;
        int x1 = 100 / 6;
        int x2 = 100 % 6;
        for (int i = 0; i < typeCTiles.size(); i++) // 100
        {
            String imgFile = imgPath + typeCTiles.get(i);

            sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + spriteTypeID);
            if (i == x) {
                for (int j = 0; j < x1; j++) {
                    initTile(sT, TILE_C_TYPE);
                }
            } else {
                for (int j = 0; j < x2 + x1; j++) {
                    initTile(sT, TILE_C_TYPE);
                }

            }
            spriteTypeID++;
        }
        this.addOther = 1;
        spriteTypeID = 0;
        for (int i = 0; i < 400; i++) //  using to fill empty
        {
            int pos = (int) (Math.random() * 6);

            String imgFile = imgPath + typeCTiles.get(pos);
            sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + pos);
            initTile(sT, TILE_C_TYPE);
        }
        ArrayList<String> typeSTiles = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.TYPE_S_TILES);
        this.addOther = 2;
        spriteTypeID = 0;
        for (int i = 0; i < 6; i++) //  using to striped
        {
            addToPos = i;
            for (int j = 0; j < 50; j++) {

                String imgFile = imgPath + typeSTiles.get(i);
                sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + i);
                initTile(sT, TILE_S_TYPE);
            }
        }
        ArrayList<String> typeWTiles = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.TYPE_W_TILES);
        this.addOther = 3;
        spriteTypeID = 0;
        for (int i = 0; i < 6; i++) //  using to wrapped
        {
            addToPos = i;
            for (int j = 0; j < 50; j++) {

                String imgFile = imgPath + typeWTiles.get(i);
                sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + i);
                initTile(sT, TILE_W_TYPE);
            }
        }
        ArrayList<String> typeColorTiles = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.TYPE_COLOR_TILES);
        this.addOther = 4;
        spriteTypeID = 0;
        for (int i = 0; i < 30; i++) //  using to color
        {
            String imgFile = imgPath + typeColorTiles.get(0);
            sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + 7);
            initTile(sT, TILE_COLOR_TYPE);
        }     
        this.addOther = 6;
        spriteTypeID = 0;
        for (int i = 0; i < typeCTiles.size(); i++) 
        {
            String imgFile = imgPath + typeCTiles.get(i);
            addToPos = i;
            sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + i);

            for (int j = 0; j < 200; j++) {
                initTile(sT, TILE_C_TYPE);
            }
        }
        String jellyTiles = BACK_GROUND_TYPE;
        this.addOther = 7;
        spriteTypeID = 0;
        for (int i = 0; i < 200; i++) // 100
        {
            String imgFile = imgPath + "./zomjong/background.png";
            addToPos = i;
            sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + 10);
            initTile(sT, BACK_GROUND_TYPE);
        }
        String backTiles = BACK_GROUND1_TYPE;
        this.addOther = 8;
        spriteTypeID = 0;
        for (int i = 0; i < 200; i++) 
        {
            String imgFile = imgPath + "./zomjong/background1.png";
            addToPos = i;
            sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + 11);
            initTile(sT, BACK_GROUND1_TYPE);
        }
        this.addOther = 9;
        String img = imgPath + "./zomjong/smash.png";
        sT = initTileSpriteType(img, "SMASH");
        initTile(sT, "SMASH");
    }

    public void addTestTiles() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(ZombieCrushSagaPropertyType.IMG_PATH);
        String blankTileSelectedFileName = props.getProperty(ZombieCrushSagaPropertyType.BLANK_TILE_SELECTED_IMAGE_NAME);
        BufferedImage blankTileSelectedImage = miniGame.loadImageWithColorKey(imgPath + blankTileSelectedFileName, COLOR_KEY);
        ((ZombieCrushSagaPanel) (miniGame.getCanvas())).setBlankTileSelectedImage(blankTileSelectedImage);
        SpriteType sT;
        ArrayList<String> typeCTiles = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.TYPE_C_TILES);
        for (int i = 0; i < testTiles.length; i++) {
            if (testTiles[i].size() < 150) {
                this.addOther = 6;
                String imgFile = imgPath + typeCTiles.get(i);
                addToPos = i;
                sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + i);
                for (int j = 0; j < 160; j++) {
                    initTile(sT, TILE_C_TYPE);
                }
            }
        }

    }

    public void addBackTiles() {
        if (jellyTiles.size() < 200 || backTiles.size() < 200) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String imgPath = props.getProperty(ZombieCrushSagaPropertyType.IMG_PATH);
            String blankTileSelectedFileName = props.getProperty(ZombieCrushSagaPropertyType.BLANK_TILE_SELECTED_IMAGE_NAME);
            BufferedImage blankTileSelectedImage = miniGame.loadImageWithColorKey(imgPath + blankTileSelectedFileName, COLOR_KEY);
            ((ZombieCrushSagaPanel) (miniGame.getCanvas())).setBlankTileSelectedImage(blankTileSelectedImage);
            SpriteType sT;
            this.addOther = 7;
            for (int i = 0; i < (201 - jellyTiles.size()); i++) //  using to fill empty
            {
                String imgFile = imgPath + "./zomjong/background.png";
                addToPos = i;
                sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + 10);
                initTile(sT, BACK_GROUND_TYPE);
            }

            this.addOther = 8;

            for (int i = 0; i < (210 - backTiles.size()); i++) //  using to fill empty
            {
                String imgFile = imgPath + "./zomjong/background1.png";
                addToPos = i;
                sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + 11);
                initTile(sT, BACK_GROUND1_TYPE);
            }
        }
    }

    public void clearBackground() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                backGrid[i][j] = null;
            }
        }
    }

    public boolean smash() {
        return ((ZombieCrushSagaMiniGame) miniGame).smash;
    }

    public void addTiles() {
        if (sixTiles.size() < 300) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String imgPath = props.getProperty(ZombieCrushSagaPropertyType.IMG_PATH);
            String blankTileSelectedFileName = props.getProperty(ZombieCrushSagaPropertyType.BLANK_TILE_SELECTED_IMAGE_NAME);
            BufferedImage blankTileSelectedImage = miniGame.loadImageWithColorKey(imgPath + blankTileSelectedFileName, COLOR_KEY);
            ((ZombieCrushSagaPanel) (miniGame.getCanvas())).setBlankTileSelectedImage(blankTileSelectedImage);
            SpriteType sT;
            ArrayList<String> typeCTiles = props.getPropertyOptionsList(ZombieCrushSagaPropertyType.TYPE_C_TILES);
            this.addOther = 1;
            for (int i = 0; i < (310 - sixTiles.size()); i++) //  using to fill empty
            {
                int pos = (int) (Math.random() * 6);
                String imgFile = imgPath + typeCTiles.get(pos);
                sT = initTileSpriteType(imgFile, TILE_SPRITE_TYPE_PREFIX + pos);
                initTile(sT, TILE_C_TYPE);
            }
        }
    }

    /**
     * Helper method for loading the tiles, it constructs the prescribed tile
     * type using the provided sprite type.
     *
     * @param sT The sprite type to use to represent this tile during rendering.
     *
     * @param tileType The type of tile. Note that there are 3 broad categories.
     */
    private void initTile(SpriteType sT, String tileType) {
        // CONSTRUCT THE TILE
        ZombieCrushSagaTile newTile = new ZombieCrushSagaTile(sT, unassignedTilesX, unassignedTilesY, 0, 0, INVISIBLE_STATE, tileType);
        // AND ADD IT TO THE STACK
        if (addOther == 0) {
            stackTiles.add(newTile);
        } else if (addOther == 1) {
            sixTiles.add(newTile);
        } else if (addOther == 2) {
            stripedTiles[addToPos].add(newTile);
        } else if (addOther == 3) {
            wrappedTiles[addToPos].add(newTile);
        } else if (addOther == 4) {
            colorTiles.add(newTile);
        } else if (addOther == 5) {
            glassTiles[addToPos].add(newTile);
        } else {
            if (addOther == 6) {
                testTiles[addToPos].add(newTile);
            } else if (addOther == 7) {
                jellyTiles.add(newTile);
            } else if (addOther == 8) {
                backTiles.add(newTile);
            } else {
                this.smarsh = newTile;
            }
        }
    }

    public boolean finishMoves() {
        int i = Integer.parseInt(this.getCurrentLevel().substring(5));
        int moves = Level_moves[i - 1];
        return (this.currentMoves == moves);
    }

    public void processWin() {
        int i = Integer.parseInt(this.getCurrentLevel().substring(5));
        int score = Level_1_Star[i - 1];
        int moves = Level_moves[i - 1];
        boolean loss = false;
        if (this.currentMoves == moves) {
            this.sleep(5);
            for (int k = 0; k < tileGrid.length; k++) {
                for (int j = 0; j < tileGrid[0].length; j++) {
                    if (tileGrid[k][j].size() > 0) {
                        ZombieCrushSagaTile tile = tileGrid[k][j].remove(0);
                        if (tile.getTileType().equals(BACK_GROUND_TYPE)) {
                            this.endGameAsLoss();
                            this.smarsh.setState(INVISIBLE_STATE);
                            loss = true;
                        }
                    }
                }
            }
            if (loss) {
                return;
            }
            if (this.currentScore < score) {
                this.smarsh.setState(INVISIBLE_STATE);
                this.endGameAsLoss();
            } else {
                this.smarsh.setState(INVISIBLE_STATE);
                this.endGameAsWin();
            }
        }
    }

    public void cheatToWin() {
        int i = Integer.parseInt(this.getCurrentLevel().substring(5));
        for (int k = 0; k < tileGrid.length; k++) {
            for (int j = 0; j < tileGrid[0].length; j++) {
                if (tileGrid[k][j].size() > 0) {
                    tileGrid[k][j].remove(0);
                }
            }
        }
        int score = Level_1_Star[i - 1];
        int moves = Level_moves[i - 1];
        this.currentMoves = moves;
        if (this.currentScore < score) {
            this.currentScore = score;
        }
        this.smarsh.setState(INVISIBLE_STATE);
        this.endGameAsWin();

    }

    public void cheatToLoss() {
        int i = Integer.parseInt(this.getCurrentLevel().substring(5));
        for (int k = 0; k < tileGrid.length; k++) {
            for (int j = 0; j < tileGrid[0].length; j++) {
                if (tileGrid[k][j].size() > 0) {
                    tileGrid[k][j].remove(0);
                }
            }
        }
        int score = Level_1_Star[i - 1];
        int moves = Level_moves[i - 1];
        this.currentMoves = moves - 2;
        if (this.currentScore >= score) {
            this.currentScore = score - 1;
        }
        if (this.currentScore == 0) {
            this.currentScore = 1;
        }
        this.smarsh.setState(INVISIBLE_STATE);
        this.endGameAsLoss();

    }

    /**
     * Called after a level has been selected, it initializes the grid so that
     * it is the proper dimensions.
     *
     * @param initGrid The grid distribution of tiles, where each cell specifies
     * the number of tiles to be stacked in that cell.
     *
     * @param initGridColumns The columns in the grid for the level selected.
     *
     * @param initGridRows The rows in the grid for the level selected.
     */
    public void initLevelGrid(int[][] initGrid, int initGridColumns, int initGridRows) {
        // KEEP ALL THE GRID INFO
        levelGrid = initGrid;
        gridColumns = initGridColumns;
        gridRows = initGridRows;
        // AND BUILD THE TILE GRID FOR STORING THE TILES
        // SINCE WE NOW KNOW ITS DIMENSIONS
        tileGrid = new ArrayList[gridColumns][gridRows];
        currentGrid = new ArrayList[gridColumns][gridRows];
        score = new int[gridColumns][gridRows];
        backGrid = new ZombieCrushSagaTile[gridColumns][gridRows];
        for (int i = 0; i < gridColumns; i++) {
            for (int j = 0; j < gridRows; j++) {
                // EACH CELL HAS A STACK OF TILES, WE'LL USE
                // AN ARRAY LIST FOR THE STACK
                tileGrid[i][j] = new ArrayList();
                currentGrid[i][j] = new ArrayList();
            }
        }
        // MAKE ALL THE TILES VISIBLE
        enableTiles(true);
    }

    public boolean isProcessMoving() {
        for (int i = 0; i < tileGrid.length; i++) {
            for (int j = 0; j < tileGrid[0].length; j++) {
                if (tileGrid[i][j].size() > 0 && tileGrid[i][j].size() < 3) {
                    ZombieCrushSagaTile tile = tileGrid[i][j].get(tileGrid[i][j].size() - 1);
                    if (tile.isMovingToTarget()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void saveCurrentGame() {
        for (int i = 0; i < tileGrid.length; i++) {
            for (int j = 0; j < tileGrid[0].length; j++) {
                if (tileGrid[i][j].size() > 0 && tileGrid[i][j].size() < 3) {
                    for (int h = tileGrid[i][j].size() - 1; h >= 0; h--) {
                        ZombieCrushSagaTile tile = tileGrid[i][j].get(h);
                        tile.setState(INVISIBLE_STATE);
                        currentGrid[i][j].add(tile);
                    }

                }

            }
        }

    }

    public void reloadCurrentGame() {
        for (int i = 0; i < tileGrid.length; i++) {
            for (int j = 0; j < tileGrid[0].length; j++) {
                if (currentGrid[i][j].size() > 0) {
                    for (int h = currentGrid[i][j].size() - 1; h >= 0; h--) {
                        ZombieCrushSagaTile tile = currentGrid[i][j].remove(h);
                        tile.setState(VISIBLE_STATE);
                        // PUT IT IN THE GRID
                        tile.setGridCell(i, j);
                        tileGrid[i][j].add(tile);
                    // WE'LL ANIMATE IT GOING TO THE GRID, SO FIGURE
                        // OUT WHERE IT'S GOING AND GET IT MOVING
                        float x = calculateTileXInGrid(i, 0);
                        float y = calculateTileYInGrid(j, 0);
                        tile.setTarget(x, y);
                        tile.startMovingToTarget(speed);
                        movingTiles.add(tile);
                    }
                }
            }
        }
    }

    /**
     * This helper method initializes a sprite type for a tile or set of similar
     * tiles to be created.
     */
    private SpriteType initTileSpriteType(String imgFile, String spriteTypeID) {
        // WE'LL MAKE A NEW SPRITE TYPE FOR EACH GROUP OF SIMILAR LOOKING TILES
        SpriteType sT = new SpriteType(spriteTypeID);
        addSpriteType(sT);
        // LOAD THE ART
        BufferedImage img = miniGame.loadImageWithColorKey(imgFile, COLOR_KEY);
        Image tempImage = img.getScaledInstance(TILE_IMAGE_WIDTH, TILE_IMAGE_HEIGHT, BufferedImage.SCALE_SMOOTH);
        img = new BufferedImage(TILE_IMAGE_WIDTH, TILE_IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        img.getGraphics().drawImage(tempImage, 0, 0, null);
        // WE'LL USE THE SAME IMAGE FOR ALL STATES
        sT.addState(INVISIBLE_STATE, img);
        sT.addState(VISIBLE_STATE, img);
        sT.addState(SELECTED_STATE, img);
        sT.addState(INCORRECTLY_SELECTED_STATE, img);
        return sT;
    }

 

    /**
     * Used to calculate the x-axis pixel location in the game grid for a tile
     * placed at column with stack position z.
     *
     * @param column The column in the grid the tile is located.
     *
     * @param z The level of the tile in the stack at the given grid location.
     *
     * @return The x-axis pixel location of the tile
     */
    public int calculateTileXInGrid(int column, int z) {
        int cellWidth = TILE_IMAGE_WIDTH;
        float leftEdge = miniGame.getBoundaryLeft();
        return (int) (leftEdge + (cellWidth * column) - (Z_TILE_OFFSET * z));
    }

    /**
     * Used to calculate the y-axis pixel location in the game grid for a tile
     * placed at row with stack position z.
     *
     * @param row The row in the grid the tile is located.
     *
     * @param z The level of the tile in the stack at the given grid location.
     *
     * @return The y-axis pixel location of the tile
     */
    public int calculateTileYInGrid(int row, int z) {
        int cellHeight = TILE_IMAGE_HEIGHT;
        float topEdge = miniGame.getBoundaryTop();
        return (int) (topEdge + (cellHeight * row) - (Z_TILE_OFFSET * z));
    }

    /**
     * Used to calculate the grid column for the x-axis pixel location.
     *
     * @param x The x-axis pixel location for the request.
     *
     * @return The column that corresponds to the x-axis location x.
     */
    public int calculateGridCellColumn(int x) {
        float leftEdge = miniGame.getBoundaryLeft();
        x = (int) (x - leftEdge);
        return x / TILE_IMAGE_WIDTH;
    }

    /**
     * Used to calculate the grid row for the y-axis pixel location.
     *
     * @param y The y-axis pixel location for the request.
     *
     * @return The row that corresponds to the y-axis location y.
     */
    public int calculateGridCellRow(int y) {
        float topEdge = miniGame.getBoundaryTop();
        y = (int) (y - topEdge);
        return y / TILE_IMAGE_HEIGHT;
    }

    // TIME TEXT METHODS
    // - timeToText
    // - gameTimeToText
    /**
     * This method creates and returns a textual description of the timeInMillis
     * argument as a time duration in the format of (H:MM:SS).
     *
     * @param timeInMillis The time to be represented textually.
     *
     * @return A textual representation of timeInMillis.
     */
    public String timeToText(long timeInMillis) {
        // FIRST CALCULATE THE NUMBER OF HOURS,
        // SECONDS, AND MINUTES
        long hours = timeInMillis / MILLIS_IN_AN_HOUR;
        timeInMillis -= hours * MILLIS_IN_AN_HOUR;
        long minutes = timeInMillis / MILLIS_IN_A_MINUTE;
        timeInMillis -= minutes * MILLIS_IN_A_MINUTE;
        long seconds = timeInMillis / MILLIS_IN_A_SECOND;

        // THEN ADD THE TIME OF GAME SUMMARIZED IN PARENTHESES
        String minutesText = "" + minutes;
        if (minutes < 10) {
            minutesText = "0" + minutesText;
        }
        String secondsText = "" + seconds;
        if (seconds < 10) {
            secondsText = "0" + secondsText;
        }
        return hours + ":" + minutesText + ":" + secondsText;
    }

    /**
     * This method builds and returns a textual representation of the game time.
     * Note that the game may still be in progress.
     *
     * @return The duration of the current game represented textually.
     */
    public String gameTimeToText() {
        // CALCULATE GAME TIME USING HOURS : MINUTES : SECONDS
        if ((startTime == null) || (endTime == null)) {
            return "";
        }
        long timeInMillis = endTime.getTimeInMillis() - startTime.getTimeInMillis();
        return timeToText(timeInMillis);
    }

    public void sleep(int i) {
        stop = true;
        long time1 = System.currentTimeMillis() + i;
        while (System.currentTimeMillis() < time1) {
          //   System.out.println(System.currentTimeMillis());

        }

        stop = false;
    }
    // GAME DATA SERVICE METHODS
    // -enableTiles
    // -findMove
    // -moveAllTilesToStack
    // -moveTiles
    // -playWinAnimation
    // -processMove
    // -selectTile
    // -undoLastMove

    /**
     * This method can be used to make all of the tiles either visible (true) or
     * invisible (false). This should be used when switching between the splash
     * and game screens.
     *
     * @param enable Specifies whether the tiles should be made visible or not.
     */
    public void enableTiles(boolean enable) {
        // PUT ALL THE TILES IN ONE PLACE WHERE WE CAN PROCESS THEM TOGETHER

        moveAllTilesToStack();

        // GO THROUGH ALL OF THEM 
        for (ZombieCrushSagaTile tile : stackTiles) {
            // AND SET THEM PROPERLY
            if (enable) {
                tile.setState(VISIBLE_STATE);
            } else {
                tile.setState(INVISIBLE_STATE);
            }
        }
    }

    /**
     * This method moves all the tiles not currently in the stack to the stack.
     */
    public void moveAllTilesToStack() {
        for (int i = 0; i < gridColumns; i++) {
            for (int j = 0; j < gridRows; j++) {
                ArrayList<ZombieCrushSagaTile> cellStack = tileGrid[i][j];
                moveTiles(cellStack, stackTiles);
            }
        }
    }

    /**
     *
     * Is free tile
     */
    private boolean isLegalMove(ZombieCrushSagaTile selectTile) {
        int col = selectTile.getGridColumn();
        int row = selectTile.getGridRow();
        int col1 = selectedTile.getGridColumn();
        int row1 = selectedTile.getGridRow();
        // check honrizontal
        if (row == row1) {
            if (col + 1 == col1) { //  selected left to right
                return true;
            }
            if (col == col1 + 1) { // selected right to left
                return true;
            }
        }
        if (col == col1) { // check vertical
            if (row + 1 == row1) {  // 
                return true;
            }
            if (row == row1 + 1) {
                return true;
            }
        }
        return false;

    }

    public boolean FinishAllMove() {
        for (int i = 0; i < tileGrid.length; i++) {
            for (int j = 0; j < tileGrid[0].length; j++) {
                if (tileGrid[i][j].size() == 2) {
                    if (check(tileGrid[i][j].get(tileGrid[i][j].size() - 1))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    //  check each tile and move
    public boolean check(ZombieCrushSagaTile tile) {
        String select = tile.getSpriteType().getSpriteTypeID();
        int col = tile.getGridColumn();
        int row = tile.getGridRow();
        int left = this.countLeft(select, col, row);
        int right = this.countRight(select, col, row);
        int up = this.countUp(select, col, row);
        int down = this.countDown(select, col, row);
        if (left + right >= 2) {
            if (this.startPlaying) {
                if (!tile.getTileType().equals(TILE_G_TYPE) && !tile.getTileType().equals(TILE_C_TYPE)) {
                    this.replacingSpecial = tile;
                }
            }
            return true;
        }
        if (up + down >= 2) {
            if (this.startPlaying) {
                if (!tile.getTileType().equals(TILE_G_TYPE) && !tile.getTileType().equals(TILE_C_TYPE)) {
                    this.replacingSpecial = tile;
                }
            }
            return true;
        }
        return false;

    }

    public void moveAllSameType(ZombieCrushSagaTile tile) {
        for (int i = 0; i < tileGrid.length; i++) {
            for (int j = 0; j < tileGrid[0].length; j++) {
                if (tileGrid[i][j].size() == 2) {
                    if (tileGrid[i][j].get(tileGrid[i][j].size() - 1).getSpriteType().getSpriteTypeID().equals(tile.getSpriteType().getSpriteTypeID())) {
                        this.movingToDrop(i, j, 1);
                        score[i][j] = (this.levelMultiplier * 10 + 20) * 3;
                    }
                }
            }
        }
    }
    public int moveTiles(ZombieCrushSagaTile tile) { // moving the match tiles and add score
        String select = tile.getSpriteType().getSpriteTypeID();
        int col = tile.getGridColumn();
        int row = tile.getGridRow();
        int left = this.countLeft(select, col, row);
        int right = this.countRight(select, col, row);
        int up = this.countUp(select, col, row);
        int down = this.countDown(select, col, row);

        if (up + down == 4) {
            this.moveDown(select, col, row);
            this.moveUp(select, col, row);

            this.replcaingTiles(colorTiles.remove(0), col, row);
            this.clearJerry(col, row);
            this.currentScore += this.calculateScore(5);
            this.scoreDown(select, col, row, this.calculateScore(5) / 5);
            this.scoreUp(select, col, row, this.calculateScore(5) / 5);
            score[col][row] = this.calculateScore(5) / 5;
            return 5;
        }
        if (up + down == 3) {
            this.moveDown(select, col, row);
            this.moveUp(select, col, row);
            this.replcaingTiles(stripedTiles[select.charAt(select.length() - 1) - '0'].remove(0), col, row);
            this.clearJerry(col, row);
            this.currentScore += this.calculateScore(4);
            this.scoreDown(select, col, row, this.calculateScore(4) / 4);
            this.scoreUp(select, col, row, this.calculateScore(4) / 4);
            score[col][row] = this.calculateScore(4) / 4;
            return 4;
        }
        if (left + right == 4) {
            this.moveLeft(select, col, row);
            this.moveRight(select, col, row);
            this.replcaingTiles(colorTiles.remove(0), col, row);
            this.clearJerry(col, row);
            this.currentScore += this.calculateScore(5);
            this.scoreLeft(select, col, row, this.calculateScore(5) / 5);
            this.scoreRight(select, col, row, this.calculateScore(5) / 5);
            score[col][row] = this.calculateScore(5) / 5;
            return 5;
        }
        if (left + right == 3) {
            this.moveLeft(select, col, row);
            this.moveRight(select, col, row);
            this.replcaingTiles(stripedTiles[select.charAt(select.length() - 1) - '0'].remove(0), col, row);
            this.clearJerry(col, row);
            this.currentScore += this.calculateScore(4);
            this.scoreLeft(select, col, row, this.calculateScore(4) / 4);
            this.scoreRight(select, col, row, this.calculateScore(4) / 4);
            score[col][row] = this.calculateScore(4) / 4;
            return 4;
        }
        if (left == 2 && up == 2) { // L 1
            this.moveLeft(select, col, row);
            this.moveUp(select, col, row);
            this.replcaingTiles(wrappedTiles[select.charAt(select.length() - 1) - '0'].remove(0), col, row);
            this.clearJerry(col, row);
            this.currentScore += this.calculateScore(5);
            this.scoreLeft(select, col, row, this.calculateScore(5) / 5);
            this.scoreUp(select, col, row, this.calculateScore(5) / 5);
            score[col][row] = this.calculateScore(5) / 5;
            return 5;
        }
        if (left == 2 && down == 2) {// L2
            this.moveLeft(select, col, row);
            this.moveDown(select, col, row);
            this.replcaingTiles(wrappedTiles[select.charAt(select.length() - 1) - '0'].remove(0), col, row);
            this.clearJerry(col, row);
            this.currentScore += this.calculateScore(5);
            this.scoreLeft(select, col, row, this.calculateScore(5) / 5);
            this.scoreDown(select, col, row, this.calculateScore(5) / 5);
            score[col][row] = this.calculateScore(5) / 5;
            return 5;
        }
        if (right == 2 && up == 2) {// L 3
            this.moveRight(select, col, row);
            this.moveUp(select, col, row);
            this.replcaingTiles(wrappedTiles[select.charAt(select.length() - 1) - '0'].remove(0), col, row);
            this.clearJerry(col, row);
            this.currentScore += this.calculateScore(5);
            this.scoreUp(select, col, row, this.calculateScore(5) / 5);
            this.scoreRight(select, col, row, this.calculateScore(5) / 5);
            score[col][row] = this.calculateScore(5) / 5;
            return 5;
        }
        if (right == 2 && down == 2) {// 4
            this.moveRight(select, col, row);
            this.moveDown(select, col, row);
            this.replcaingTiles(wrappedTiles[select.charAt(select.length() - 1) - '0'].remove(0), col, row);
            this.clearJerry(col, row);
            this.currentScore += this.calculateScore(5);
            this.scoreDown(select, col, row, this.calculateScore(5) / 5);
            this.scoreRight(select, col, row, this.calculateScore(5) / 5);
            score[col][row] = this.calculateScore(5) / 5;
            return 5;
        }
        if (left == 1 && right == 1 && up == 2) { //T 1
            this.moveLeft(select, col, row);
            this.moveRight(select, col, row);
            this.moveUp(select, col, row);
            this.replcaingTiles(wrappedTiles[select.charAt(select.length() - 1) - '0'].remove(0), col, row);
            this.clearJerry(col, row);
            this.currentScore += this.calculateScore(5);
            this.scoreLeft(select, col, row, this.calculateScore(5) / 5);
            this.scoreRight(select, col, row, this.calculateScore(5) / 5);
            this.scoreUp(select, col, row, this.calculateScore(5) / 5);
            score[col][row] = this.calculateScore(5) / 5;
            return 5;
        }

        if (left == 1 && right == 1 && down == 2) {// T 2
            this.moveLeft(select, col, row);
            this.moveRight(select, col, row);
            this.moveDown(select, col, row);
            this.replcaingTiles(wrappedTiles[select.charAt(select.length() - 1) - '0'].remove(0), col, row);
            this.clearJerry(col, row);
            this.currentScore += this.calculateScore(5);
            this.scoreLeft(select, col, row, this.calculateScore(5) / 5);
            this.scoreRight(select, col, row, this.calculateScore(5) / 5);
            this.scoreDown(select, col, row, this.calculateScore(5) / 5);
            score[col][row] = this.calculateScore(5) / 5;
            return 5;
        }

        if (up == 1 && down == 1 && left == 2) { // T 3
            this.moveUp(select, col, row);
            this.moveDown(select, col, row);
            this.moveLeft(select, col, row);
            this.replcaingTiles(wrappedTiles[select.charAt(select.length() - 1) - '0'].remove(0), col, row);
            this.clearJerry(col, row);
            this.currentScore += this.calculateScore(5);
            this.scoreLeft(select, col, row, this.calculateScore(5) / 5);
            this.scoreDown(select, col, row, this.calculateScore(5) / 5);
            this.scoreUp(select, col, row, this.calculateScore(5) / 5);
            score[col][row] = this.calculateScore(5) / 5;
            return 5;
        }
        if (up == 1 && down == 1 && right == 2) { //  T 4
            this.moveUp(select, col, row);
            this.moveDown(select, col, row);
            this.moveRight(select, col, row);
            this.replcaingTiles(wrappedTiles[select.charAt(select.length() - 1) - '0'].remove(0), col, row);
            this.clearJerry(col, row);
            this.currentScore += this.calculateScore(5);
            this.scoreDown(select, col, row, this.calculateScore(5) / 5);
            this.scoreRight(select, col, row, this.calculateScore(5) / 5);
            this.scoreUp(select, col, row, this.calculateScore(5) / 5);
            score[col][row] = this.calculateScore(5) / 5;
            return 5;
        }
        if (left + right == 2 || left == 2 || right == 2) { // H 3
            this.moveLeft(select, col, row);
            this.moveRight(select, col, row);
            int pos[] = {col, row};
            if (!this.isContain(pos)) {
                this.currentNeedToMove.add(pos);
            }
            this.scoreLeft(select, col, row, this.calculateScore(3) / 3);
            this.scoreRight(select, col, row, this.calculateScore(3) / 3);
            score[col][row] = this.calculateScore(3) / 3;
            this.currentScore += this.calculateScore(3);
            return 3;
        }

        if (up + down == 2 || up == 2 || down == 2) { // V 3
            this.moveUp(select, col, row);
            this.moveDown(select, col, row);
            int pos[] = {col, row};
            if (!this.isContain(pos)) {
                this.currentNeedToMove.add(pos);
            }
            this.scoreUp(select, col, row, this.calculateScore(3) / 3);
            this.scoreDown(select, col, row, this.calculateScore(3) / 3);
            score[col][row] = this.calculateScore(3) / 3;
            this.currentScore += this.calculateScore(3);
            return 3;
        }
        return 0;

    }

    public int calculateScore(int length) {
        int score = 0;
        if (length == 3) {
            score = (this.levelMultiplier * 10 + 20) * 3 * this.Scoretimes;
        }
        if (length == 4) {
            score = (this.levelMultiplier * 10 + 30) * 4 * this.Scoretimes;
        }
        if (length == 5) {
            score = (this.levelMultiplier * 10 + 40) * 5 * this.Scoretimes;
        }
        return score;
    }

    /**
     * This method removes all the tiles in from argument and moves them to
     * argument.
     *
     * @param from The source data structure of tiles.
     *
     * @param to The destination data structure of tiles.
     */
    private void moveTiles(ArrayList<ZombieCrushSagaTile> from, ArrayList<ZombieCrushSagaTile> to) {
        // GO THROUGH ALL THE TILES, TOP TO BOTTOM
        if (from.size() > 0 && from.size() < 3) {
            ZombieCrushSagaTile tile = from.remove(from.size() - 1);
            // ONLY ADD IT IF IT'S NOT THERE ALREADY
            if (!to.contains(tile)) {
                to.add(tile);
            }
        }
    }

    public void processStripedTIles(int col1, int row1, int col2, int row2) {
        if (col1 == col2) {
            this.clearCol(col2);
        }
        if (row1 == row2) {
            this.clearRow(row2);
        }

    }

    /**
     * This method updates all the necessary state information to process the
     * move argument.
     *
     * @param move The move to make. Note that a move specifies the cell
     * locations for a match.
     */
    public void processMove(ZombieCrushSagaMove move) {
        // REMOVE THE MOVE TILES FROM THE GRID

        try {
            miniGame.beginUsingData();
            ArrayList<ZombieCrushSagaTile> stack1 = tileGrid[move.col1][move.row1];
            ArrayList<ZombieCrushSagaTile> stack2 = tileGrid[move.col2][move.row2];
            ZombieCrushSagaTile tile1 = stack1.remove(stack1.size() - 1);
            ZombieCrushSagaTile tile2 = stack2.remove(stack2.size() - 1);
            int targetX1 = this.calculateTileXInGrid(move.col2, 0);
            int targetY1 = this.calculateTileYInGrid(move.row2, 0);
            int targetX2 = this.calculateTileXInGrid(move.col1, 0);
            int targetY2 = this.calculateTileYInGrid(move.row1, 0);
            // MAKE SURE BOTH ARE UNSELECTED
            tile1.setState(VISIBLE_STATE);
            tile2.setState(VISIBLE_STATE);
            // SEND THEM TO THE STACK
            tile1.setGridCell(move.col2, move.row2);
            tile2.setGridCell(move.col1, move.row1);
            tile1.setTarget(targetX1, targetY1);
            tile2.setTarget(targetX2, targetY2);
            // MAKE SURE THEY MOVE
            movingTiles.add(tile1);
            movingTiles.add(tile2);
            tile1.startMovingToTarget(speed);
            tile2.startMovingToTarget(speed);
            stack1.add(tile2);
            stack2.add(tile1);
        // AND MAKE SURE NEW TILES CAN BE SELECTED 
        } finally {
            miniGame.endUsingData();
        }
    }

    /**
     * count left
     */
    public int countLeft(String tileType, int col, int row) {
        int count = 0;
        int x1 = col - 1;
        while (x1 >= 0) {
            if (tileGrid[x1][row].size() != 2) {
                break;
            }
            if (tileGrid[x1][row].get(tileGrid[x1][row].size() - 1).getSpriteType().getSpriteTypeID().equals(tileType)) {
                count++;
            } else {
                break;
            }
            x1--;
        }
        return count;
    }

    public int scoreLeft(String tileType, int col, int row, int score1) {
        int count = 0;
        int x1 = col - 1;
        while (x1 >= 0) {
            if (tileGrid[x1][row].size() != 2) {
                break;
            }
            if (tileGrid[x1][row].get(tileGrid[x1][row].size() - 1).getSpriteType().getSpriteTypeID().equals(tileType)) {
                score[x1][row] = Math.max(score[x1][row], score1);
            } else {
                break;
            }
            x1--;
        }
        return count;
    }

    /**
     * move left
     */
    public void moveLeft(String tileType, int col, int row) {
        int x1 = col - 1;
        while (x1 >= 0) {
            if (tileGrid[x1][row].size() != 2) {
                break;
            }
            if (tileGrid[x1][row].get(tileGrid[x1][row].size() - 1).getSpriteType().getSpriteTypeID().equals(tileType)) {
                int pos[] = {x1, row};
                if (!isContain(pos)) {
                    currentNeedToMove.add(pos);
                }

            } else {
                break;
            }
            x1--;
        }

    }

    /**
     * count right
     */
    public int countRight(String tileType, int col, int row) {
        int count = 0;
        int x1 = col + 1;
        while (x1 < tileGrid.length) {
            if (tileGrid[x1][row].size() != 2) {
                break;
            }
            if (tileGrid[x1][row].get(tileGrid[x1][row].size() - 1).getSpriteType().getSpriteTypeID().equals(tileType)) {
                count++;
            } else {
                break;
            }
            x1++;
        }
        return count;
    }

    public int scoreRight(String tileType, int col, int row, int score1) {
        int count = 0;
        int x1 = col + 1;
        while (x1 < tileGrid.length) {
            if (tileGrid[x1][row].size() != 2) {
                break;
            }
            if (tileGrid[x1][row].get(tileGrid[x1][row].size() - 1).getSpriteType().getSpriteTypeID().equals(tileType)) {
                score[x1][row] = Math.max(score[x1][row], score1);
            } else {
                break;
            }
            x1++;
        }
        return count;
    }

    /**
     * move right
     */
    public void moveRight(String tileType, int col, int row) {
        int count = 0;
        int x1 = col + 1;
        while (x1 < tileGrid.length) {
            if (tileGrid[x1][row].size() != 2) {
                break;
            }
            if (tileGrid[x1][row].get(tileGrid[x1][row].size() - 1).getSpriteType().getSpriteTypeID().equals(tileType)) {
                int pos[] = {x1, row};
                if (!isContain(pos)) {
                    currentNeedToMove.add(pos);
                }
            } else {
                break;
            }
            x1++;
        }

    }

    /**
     * count up
     */
    public int countUp(String tileType, int col, int row) {
        int count = 0;
        int y1 = row - 1;
        while (y1 >= 0) {
            if (tileGrid[col][y1].size() != 2) {
                break;
            }
            if (tileGrid[col][y1].get(tileGrid[col][y1].size() - 1).getSpriteType().getSpriteTypeID().equals(tileType)) {
                count++;
            } else {
                break;
            }
            y1--;
        }
        return count;
    }

    public int scoreUp(String tileType, int col, int row, int score1) {
        int count = 0;
        int y1 = row - 1;
        while (y1 >= 0) {
            if (tileGrid[col][y1].size() != 2) {
                break;
            }
            if (tileGrid[col][y1].get(tileGrid[col][y1].size() - 1).getSpriteType().getSpriteTypeID().equals(tileType)) {
                score[col][y1] = Math.max(score[col][y1], score1);
            } else {
                break;
            }
            y1--;
        }
        return count;
    }

    /**
     * count up
     */
    public void moveUp(String tileType, int col, int row) {
        int y1 = row - 1;
        while (y1 >= 0) {
            if (tileGrid[col][y1].size() != 2) {
                break;
            }
            if (tileGrid[col][y1].get(tileGrid[col][y1].size() - 1).getSpriteType().getSpriteTypeID().equals(tileType)) {
                int pos[] = {col, y1};
                if (!isContain(pos)) {
                    currentNeedToMove.add(pos);
                }
            } else {
                break;
            }
            y1--;
        }

    }

    /**
     * count down
     */
    public int countDown(String tileType, int col, int row) {
        int count = 0;
        int y1 = row + 1;
        while (y1 < tileGrid[0].length) {
            if (tileGrid[col][y1].size() != 2) {
                break;
            }
            if (tileGrid[col][y1].get(tileGrid[col][y1].size() - 1).getSpriteType().getSpriteTypeID().equals(tileType)) {
                count++;
            } else {
                break;
            }
            y1++;
        }
        return count;
    }

    public int scoreDown(String tileType, int col, int row, int score1) {
        int count = 0;
        int y1 = row + 1;
        while (y1 < tileGrid[0].length) {
            if (tileGrid[col][y1].size() != 2) {
                break;
            }
            if (tileGrid[col][y1].get(tileGrid[col][y1].size() - 1).getSpriteType().getSpriteTypeID().equals(tileType)) {
                score[col][y1] = Math.max(score[col][y1], score1);
            } else {
                break;
            }
            y1++;
        }
        return count;
    }

    /**
     * count down
     */
    public void moveDown(String tileType, int col, int row) {
        int y1 = row + 1;
        while (y1 < tileGrid[0].length) {
            if (tileGrid[col][y1].size() != 2) {
                break;
            }
            if (tileGrid[col][y1].get(tileGrid[col][y1].size() - 1).getSpriteType().getSpriteTypeID().equals(tileType)) {
                int pos[] = {col, y1};
                if (!isContain(pos)) {
                    currentNeedToMove.add(pos);
                }
            } else {
                break;
            }
            y1++;
        }

    }

    /**
     * This method attempts to select the selectTile argument. Note that this
     * may be the first or second selected tile. If a tile is already selected,
     * it will attempt to process a match/move.
     *
     * @param selectTile The tile to select.
     */
    public void addMoves() {
        this.currentMoves++;
    }
    public void addScoreTimes() {
        this.Scoretimes++;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void resetEmptyCol() {
        this.emptyCol = new ArrayList<Integer>();
    }

    public ArrayList<int[]> getCurrentNeedToMove() {
        return this.currentNeedToMove;
    }

    public void selectTile(ZombieCrushSagaTile selectTile)
    {
        this.Scoretimes=1;
        // IF IT'S ALREADY THE SELECTED TILE, DESELECT IT
        if (selectTile == selectedTile) {
            selectedTile = null;
            selectTile.setState(VISIBLE_STATE);
            return;
        }
        if (selectedTile == null) {
            selectedTile = selectTile;
            selectedTile.setState(SELECTED_STATE);
            return;
        } else {
            if ((!selectedTile.match(selectTile) && isLegalMove(selectTile))) {
                this.selectTile = selectTile;
                this.beginUpdataBoard = true;
                speed = 4;
                Exchange(selectedTile, selectTile);   
                this.finishExchange = false;
                if ((this.check(selectTile) || this.check(selectedTile) || selectedTile.getTileType().equals(TILE_COLOR_TYPE) || selectTile.getTileType().equals(TILE_COLOR_TYPE))) {
                    this.legalMove = true;
                    this.currentMoves++;
                    this.Scoretimes = 1;
                    speed = 0.5;
                    this.currentNeedToMove = new ArrayList<int[]>();
                    this.emptyCol = new ArrayList<Integer>();
                    if (selectedTile.getTileType().equals(TILE_COLOR_TYPE)) {
                        this.moveAllSameType(selectTile);
                        int[] p = {selectedTile.getGridColumn(), selectedTile.getGridRow()};
                        this.currentNeedToMove.add(p);

                    } else {
                        if (selectTile.getTileType().equals(TILE_COLOR_TYPE)) {
                            this.moveAllSameType(selectedTile);
                            int[] p = {selectTile.getGridColumn(), selectTile.getGridRow()};
                            this.currentNeedToMove.add(p);

                        } else {
                            if (check(selectedTile)) {
                                this.moveTiles(selectedTile);
                            }
                            if (check(selectTile)){
                                this.moveTiles(selectTile);
                            }
                        }
                    }
                } else {
                    this.legalMove = false;
                    this.finishExchange = false;
                    selectedTile.setBeginExchange(true);
                }
                speed = 1;
            } else {
                updateMoving();
            }
        }
    }

    public void processSelect() {
        for (int i = 0; i < tileGrid.length; i++) {
            fillEmptyColumn(i);
            this.emptyCol.add(i);
        }
        boolean find = false;
        while (!emptyCol.isEmpty()) {   // check renew col
            int ecol = emptyCol.remove(0);
            for (int j = tileGrid[0].length - 1; j >= 0;) {  // from bottom to top
                find = false;
                if (this.check(tileGrid[ecol][j].get(tileGrid[ecol][j].size() - 1))) {
                    this.Scoretimes++;
                    this.moveTiles(tileGrid[ecol][j].get(tileGrid[ecol][j].size() - 1));
                    //   sleep(200);                              
                    clear();
                    for (int k = 0; k < tileGrid.length; k++) {
                        if (fillEmptyColumn(k)) {
                            this.emptyCol.add(k);
                        }
                    }
                    find = true;
                }
                if (find) {
                    j = tileGrid[0].length - 1;
                } else {
                    j--;
                }
            }
        }
        this.legalMove = false;
    }

    public void updateMoving() {
        if (selectedTile != null) {
            selectedTile.setState(VISIBLE_STATE);
            selectedTile = null;
        }
        if (selectTile != null) {
            selectTile.setState(VISIBLE_STATE);
            selectTile = null;
        }
    }

    public void clearJerry(int col, int row) {
        this.addBackTiles();
        if (!this.startPlaying) {
            return;
        }
        ArrayList<ZombieCrushSagaTile> stack = tileGrid[col][row];
        if (stack.size() == 1) {
            if (stack.get(0).getTileType().equals(BACK_GROUND_TYPE)) {
                this.replcaingTiles(backTiles.remove(0), col, row);
            }
        }
        if (stack.size() == 2) {
            ArrayList<ZombieCrushSagaTile> stack2 = tileGrid[col][row];
            ZombieCrushSagaTile tile1 = backTiles.remove(0);
            this.movingToDrop(col, row, 0);
            double i = speed;
            speed = 200;
            int z = 0;
            float targetX = this.calculateTileXInGrid(col, 0);
            float targetY = this.calculateTileYInGrid(row, 0);
            tile1.setX(targetX);
            tile1.setY(targetY);
            tile1.setState(VISIBLE_STATE);
            tile1.setGridCell(col, row);
            tile1.setTarget(targetX, targetY);
            movingTiles.add(tile1);
            tile1.startMovingToTarget(speed);
            tileGrid[col][row].add(0, tile1);
            speed = i;
        }
    }

    public void clear() {
        boolean getBom = false;
        for (int i = 0; i < this.currentNeedToMove.size(); i++) {
            ArrayList<ZombieCrushSagaTile> stack = tileGrid[currentNeedToMove.get(i)[0]][currentNeedToMove.get(i)[1]];
            if (stack.size() == 2) {
                ZombieCrushSagaTile s = stack.get(stack.size() - 1);
                if (s.getTileType().equals(TILE_W_TYPE) && !this.isBom) {
                    this.clearFirstBom(s.getGridColumn(), s.getGridRow());
                    this.isBom = false;
                    getBom = true;
                }
                clearJerry(s.getGridColumn(), s.getGridRow());
                if (selectTile != null && selectedTile != null) {
                    if (s.getTileType().equals(TILE_S_TYPE)) {
                        if (selectTile.getGridColumn() == selectedTile.getGridColumn()) {
                            this.clearCol(s.getGridColumn());
                        }
                        if (selectTile.getGridRow() == selectedTile.getGridRow()) {
                            this.clearRow(s.getGridRow());
                        }
                    }
                }
                if (!getBom) {
                    if (stack.size() == 2)
                    {
                        this.movingToDrop(s.getGridColumn(), s.getGridRow(), 1);
                    }
                }
                addTiles();
                getBom = false;
            }
        }
        while (this.currentNeedToMove.size() != 0) {
            this.currentNeedToMove.remove(0);
        }
    }

    public void clearCol(int col) {
        for (int i = 0; i < tileGrid[col].length; i++) {
            if (tileGrid[col][i].size() != 0) {
                score[col][i] = (this.levelMultiplier * 10 + 20) * 3;
            }
        }
        for (int i = 0; i < tileGrid[col].length; i++) {
            ArrayList<ZombieCrushSagaTile> stack = tileGrid[col][i];
            if (stack.size() == 2) {
                this.movingToDrop(col, i, 1);
                this.currentScore += (this.levelMultiplier * 10 + 20) * 3;
            }
        }
    }

    public void clearRow(int row) {
        for (int i = 0; i < tileGrid.length; i++) {
            if (tileGrid[i][row].size() != 0) {
                score[i][row] = (this.levelMultiplier * 10 + 20) * 3;
            }
        }
        for (int i = 0; i < tileGrid.length; i++) {
            ArrayList<ZombieCrushSagaTile> stack = tileGrid[i][row];
            if (stack.size() != 0) {
                score[i][row] = (this.levelMultiplier * 10 + 20) * 3;
            }
            if (stack.size() == 2) {
                this.movingToDrop(i, row, 1);
                this.currentScore += (this.levelMultiplier * 10 + 20) * 3;
            }
        }
    }

    public void delectNeedToMove(int col, int row) {
        for (int i = 0; i < this.currentNeedToMove.size(); i++) {
            if (currentNeedToMove.get(i)[0] == col && currentNeedToMove.get(i)[0] == row) {
                currentNeedToMove.remove(i);
                return;
            }
        }
    }

    public void clearFirstBom(int col, int row) {
        for (int i = col - 1; i >= 0 && i <= col + 1 && i < tileGrid.length; i++) {
            for (int j = row - 1; j >= 0 && j <= row + 1 && j < tileGrid[0].length; j++) {
                if (tileGrid[i][j].size() == 2 && (col != i || j != row)) {
                    score[i][j] = (this.levelMultiplier * 10 + 20) * 3;
                    this.currentScore += (this.levelMultiplier * 10 + 20) * 3;
                    this.movingToDrop(i, j, 1);
                }
            }
        }
        delectNeedToMove(col, row);
        this.isBom = true;
        this.secondBom = true;
        Bom[0] = col;
        Bom[1] = row + 1;
    }

    public void clearSecondBom() {
        int col = Bom[0];
        int row = Bom[1];
        if (row >= tileGrid[0].length) {
            row = tileGrid[0].length - 1;
        }
        if (tileGrid[col][row].size() == 0) {
            row++;
        }
        if (row >= tileGrid[0].length) {
            row = tileGrid[0].length - 1;
        }
        if (tileGrid[col][row].size() == 0) {
            row++;
        }
        if (row >= tileGrid[0].length) {
            row = tileGrid[0].length - 1;
        }
        for (int i = col - 1; i >= 0 && i <= col + 1 && i < tileGrid.length; i++) {
            for (int j = row - 1; j >= 0 && j <= row + 1 && j < tileGrid[0].length; j++) {
                if (tileGrid[i][j].size() == 2) {
                    score[i][j] = (this.levelMultiplier * 10 + 20) * 3;
                    this.currentScore += (this.levelMultiplier * 10 + 20) * 3;
                    this.movingToDrop(i, j, 1);
                }
            }
        }
        for (int i = 0; i < tileGrid.length; i++) {
            this.emptyCol.add(i);
        }
    }

    /**
     * left right exchange
     */
    public void Exchange(ZombieCrushSagaTile selectTile, ZombieCrushSagaTile selectedTile) {
        //  before change set  seted
        if (selectTile == null || selectedTile == null) {
            return;
        }
        if (!this.finishExchange) {
            if (selectTile.getState().equals(VISIBLE_STATE) && selectedTile.getState().equals(VISIBLE_STATE)) {
                return;
            }
        }
        ZombieCrushSagaMove move = new ZombieCrushSagaMove();
        move.col1 = selectTile.getGridColumn();
        move.col2 = selectedTile.getGridColumn();
        move.row1 = selectTile.getGridRow();
        move.row2 = selectedTile.getGridRow();
        processMove(move);
    }

    public boolean moveDownOneStep(ZombieCrushSagaTile tile) {

        int row2 = tile.getGridRow() + 1;
        if (row2 == tileGrid[0].length) {
            return false;  //   last row
        }
        if (tileGrid[tile.getGridColumn()][row2].size() == 2) {
            return false;
        }
        while (row2 < tileGrid[0].length) {
            if (levelGrid[tile.getGridColumn()][row2] == 3) {
                row2++;
            } else if (tileGrid[tile.getGridColumn()][row2].size() == 2) {
                return false;
            } else {
                break;
            }
        }
        if (row2 > tileGrid[0].length - 1) {
            return false;
        }
        if (levelGrid[tile.getGridColumn()][row2] == 3) {
            return false;
        }

        if (tileGrid[tile.getGridColumn()][row2].size() == 2) {
            return false;
        }
        if ((tileGrid[tile.getGridColumn()][row2].size() == 1)) {
            // REMOVE THE MOVE TILES FROM THE GRID
            ArrayList<ZombieCrushSagaTile> stack1 = tileGrid[tile.getGridColumn()][tile.getGridRow()];
            ArrayList<ZombieCrushSagaTile> stack2 = tileGrid[tile.getGridColumn()][row2];
            ZombieCrushSagaTile tile1 = stack1.remove(stack1.size() - 1); // move tile to tile down 1
            // SEND THEM TO THE STACK
            tile1.setTarget(TILE_STACK_X + TILE_STACK_OFFSET_X, TILE_STACK_Y + TILE_STACK_OFFSET_Y);
            tile1.startMovingToTarget(speed);
            stackTiles.add(tile1);
            // MAKE SURE THEY MOVE
            movingTiles.add(tile1);
            ZombieCrushSagaTile topTile = stackTiles.remove(stackTiles.size() - 1);
            // FIRST TILE 1
            int col = topTile.getGridColumn();
            int row = topTile.getGridRow();
            // target pos
            int col1 = topTile.getGridColumn();
            int row1 = row2;
            int z = tileGrid[col][row].size();
            float targetX = this.calculateTileXInGrid(col1, z);
            float targetY = this.calculateTileYInGrid(row1, z);
            topTile.setGridCell(col1, row1);
            topTile.setTarget(targetX, targetY);
            topTile.setState(VISIBLE_STATE);
            movingTiles.add(topTile);
            topTile.startMovingToTarget(speed);
            tileGrid[col1][row1].add(topTile);
            return true;
        }
        return false;
    }
    public boolean fillEmptyColumn(int col) {
        boolean fill = true;
        boolean get = false;
        this.speed = 1.5;
        while (fill) {
            fill = false;
            for (int i = tileGrid[0].length - 1; i >= 0; i--) {
                if (tileGrid[col][i].size() == 2 && i != tileGrid[0].length - 1 && levelGrid[col][i] != 3) {
                    ZombieCrushSagaTile currentTile = tileGrid[col][i].get(tileGrid[col][i].size() - 1);
                    // only when the 
                    if (tileGrid[col][i].size() == 2) {
                        if (moveDownOneStep(currentTile)) {
                            fill = true;
                            get = true;
                        }
                    }
                }
            }
            if (fillTop(col)) {
                fill = true;
                get = true;
            }
        }
        return get;
    }

    public boolean fillTop(int col) {
        if (tileGrid[col][0].size() == 1 && levelGrid[col][0] != 3) {
            ArrayList<ZombieCrushSagaTile> stack2 = tileGrid[col][0];
            ZombieCrushSagaTile tile1 = sixTiles.remove(0);
            int z = stack2.size();
            float targetX = this.calculateTileXInGrid(col, z);
            float targetY = this.calculateTileYInGrid(0, z);
            tile1.setX(targetX);
            tile1.setY(targetY - 55);
            tile1.setState(VISIBLE_STATE);
            tile1.setGridCell(col, 0);
            tile1.setTarget(targetX, targetY);
            movingTiles.add(tile1);
            tile1.startMovingToTarget(speed);
            tileGrid[col][0].add(tile1);
            return true;
        }
        return false;
    }

    public void replcaingTiles(ZombieCrushSagaTile tile1, int col, int row) {
        ArrayList<ZombieCrushSagaTile> stack2 = tileGrid[col][row];
        stack2.remove(tileGrid[col][row].size() - 1);
        double i = speed;
        speed = 200;
        int z = stack2.size();
        float targetX = this.calculateTileXInGrid(col, z);
        float targetY = this.calculateTileYInGrid(row, z);
        tile1.setX(targetX);
        tile1.setY(targetY);
        tile1.setState(VISIBLE_STATE);
        tile1.setGridCell(col, row);
        tile1.setTarget(targetX, targetY);
        movingTiles.add(tile1);
        tile1.startMovingToTarget(speed);
        tileGrid[col][row].add(tile1);
        speed = i;
    }

    public boolean isContain(int[] pos) {
        for (int i = 0; i < this.currentNeedToMove.size(); i++) {
            if (currentNeedToMove.get(i)[0] == pos[0] && currentNeedToMove.get(i)[1] == pos[1]) {
                return true;
            }
        }
        return false;

    }

    public void movingToDrop(int col, int row, int h) {
        ArrayList<ZombieCrushSagaTile> stack2 = tileGrid[col][row];
        ZombieCrushSagaTile tile1 = stack2.remove(h);
        this.clearJerry(col, row);
        double i = speed;
        speed = 10;
        int z = stack2.size();
        float targetX;
        float targetY;
        if (tile1.getTileType().equals(backTiles.get(0).getTileType())) {
            return;
        }
        if (tile1.getTileType().equals(jellyTiles.get(0).getTileType())) {
            targetX = 620;
            targetY = 900;
        } else {
            if (this.calculateTileXInGrid(col, 1) < 620) {
                targetX = -100;
            } else {
                targetX = 1350;
            }

            targetY = 360;
        }
        tile1.setState(VISIBLE_STATE);
        tile1.setTarget(targetX, targetY);
        movingTiles.add(tile1);
        tile1.startMovingToTarget(speed);
        speed = i;

    }

    // OVERRIDDEN METHODS
    // - checkMousePressOnSprites
    // - endGameAsWin
    // - endGameAsLoss
    // - reset
    // - updateAll
    // - updateDebugText
    /**
     * This method provides a custom game response for handling mouse clicks on
     * the game screen. We'll use this to close game dialogs as well as to
     * listen for mouse clicks on grid cells.
     *
     * @param game The Mahjong game.
     *
     * @param x The x-axis pixel location of the mouse click.
     *
     * @param y The y-axis pixel location of the mouse click.
     */
    @Override
    public void checkMousePressOnSprites(MiniGame game, int x, int y) {
        this.bom = false;
        if (this.isProcessMoving())// if continue moving can not select any tile
        {
            return;
        }
        // FIGURE OUT THE CELL IN THE GRID
        int col = calculateGridCellColumn(x);
        int row = calculateGridCellRow(y);
        // CHECK THE TOP OF THE STACK AT col, row
        if (tileGrid.length <= col || tileGrid[0].length <= row || col < 0 || row < 0) {
            return;
        }
        ArrayList<ZombieCrushSagaTile> tileStack = tileGrid[col][row];
        if (tileStack.size() == 2) {
            // GET AND TRY TO SELECT THE TOP TILE IN THAT CELL, IF THERE IS ONE
            ZombieCrushSagaTile testTile = tileStack.get(tileStack.size() - 1);
            //     System.out.println("  test tile "+testTile.getSpriteType().getSpriteTypeID());
            if (testTile.containsPoint(x, y)) {
                if (!stop) {
                    if (Level_moves[Integer.parseInt(this.getCurrentLevel().substring(5)) - 1] == this.currentMoves) {
                        processWin();
                    } else {
                        if (this.smash()) {
                            int[] p = {col, row};
                            this.bom = true;
                            this.movingToDrop(col, row, 1);
                            this.emptyCol.add(col);
                            if (tileGrid[col][row].get(0).getTileType().equals(BACK_GROUND_TYPE)) {
                                this.replcaingTiles(backTiles.remove(0), col, row);
                            }
                            this.beginUpdataBoard = true;
                            ((ZombieCrushSagaMiniGame) miniGame).smashInvalid();
                        } else {
                            selectTile(testTile);
                        }
                    }
                }

            }
        }
    }

    /**
     * Called when the game is won, it will record the ending game time, update
     * the player record, display the win dialog, and play the win animation.
     */
    @Override
    public void endGameAsWin() {
        // UPDATE THE GAME STATE USING THE INHERITED FUNCTIONALITY
        int i = Integer.parseInt(this.currentLevel.substring(5)) - 1;
        if (i < 9) {
            levelLimit[i + 1] = true; // open next level
            ((ZombieCrushSagaMiniGame) miniGame).getPlayerRecord().openNewLevelRecord(Level_Name[i + 1]);
        }
        ((ZombieCrushSagaMiniGame) miniGame).getPlayerRecord().addWin(currentLevel, currentScore);
        ((ZombieCrushSagaMiniGame) miniGame).savePlayerRecord();
        super.endGameAsWin();
        this.sleep(300);
        ((ZombieCrushSagaMiniGame) miniGame).switchToLevelScoreScreen();
    }

    /**
     *
     * Called when the game is loss, it will record the ending game time, update
     * the player record, display the loss dialog.
     *
     */
    @Override
    public void endGameAsLoss() {
        ((ZombieCrushSagaMiniGame) miniGame).getPlayerRecord().addLoss(currentLevel);
        ((ZombieCrushSagaMiniGame) miniGame).savePlayerRecord();
        super.endGameAsLoss();
        this.sleep(300);
        ((ZombieCrushSagaMiniGame) miniGame).switchToLevelScoreScreen();
    }

    /**
     * Called when a game is started, the game grid is reset.
     *
     * @param game
     */
    @Override
    public void reset(MiniGame game) {
        this.addBackTiles();
        this.clearScore();
        if (this.inProgress()) {
            this.reloadCurrentGame();
            return;
        }
        this.startPlaying = false;
        this.speed = 80;
        moveAllTilesToStack();
        for (ZombieCrushSagaTile tile : stackTiles) {
            tile.setX(-100);
            tile.setY(-100);
            tile.setState(VISIBLE_STATE);

        }
        for (ZombieCrushSagaTile tile : jellyTiles) {
            tile.setX(-50);
            tile.setY(-50);
            tile.setState(VISIBLE_STATE);

        }
        for (ZombieCrushSagaTile tile : backTiles) {
            tile.setX(-80);
            tile.setY(-80);
            tile.setState(VISIBLE_STATE);

        }
        // RANDOMLY ORDER THEM
        Collections.shuffle(stackTiles);
        // START THE CLOCK
        startTime = new GregorianCalendar();
        // NOW LET'S REMOVE THEM FROM THE STACK
        // AND PUT THE TILES IN THE GRID        
        for (int i = 0; i < gridColumns; i++) {
            for (int j = 0; j < gridRows; j++) {          
                if (levelGrid[i][j] == 2) {
                    ZombieCrushSagaTile tile1 = jellyTiles.remove(jellyTiles.size() - 1); // set jelly
                    tileGrid[i][j].add(tile1);
                    tile1.setGridCell(i, j);
                    // WE'LL ANIMATE IT GOING TO THE GRID, SO FIGURE
                    // OUT WHERE IT'S GOING AND GET IT MOVING
                    float x1 = calculateTileXInGrid(i, 0);
                    float y1 = calculateTileYInGrid(j, 0);
                    tile1.setTarget(x1, y1);
                    tile1.startMovingToTarget(speed);
                    movingTiles.add(tile1);
                    // TAKE THE TILE OUT OF THE STACK
                    ZombieCrushSagaTile tile = stackTiles.remove(stackTiles.size() - 1);
                    // PUT IT IN THE GRID
                    tileGrid[i][j].add(tile);
                    tile.setGridCell(i, j);
                    // WE'LL ANIMATE IT GOING TO THE GRID, SO FIGURE
                    // OUT WHERE IT'S GOING AND GET IT MOVING
                    float x = calculateTileXInGrid(i, 1);
                    float y = calculateTileYInGrid(j, 1);
                    tile.setTarget(x, y);
                    tile.startMovingToTarget(speed);
                    movingTiles.add(tile);

                } else if (levelGrid[i][j] == 1) { // set backgroud
                    ZombieCrushSagaTile tile1 = backTiles.remove(backTiles.size() - 1); // set jelly
                    tileGrid[i][j].add(tile1);
                    tile1.setGridCell(i, j);
                    // WE'LL ANIMATE IT GOING TO THE GRID, SO FIGURE
                    // OUT WHERE IT'S GOING AND GET IT MOVING
                    float x1 = calculateTileXInGrid(i, 0);
                    float y1 = calculateTileYInGrid(j, 0);
                    tile1.setTarget(x1, y1);
                    tile1.startMovingToTarget(speed);
                    movingTiles.add(tile1);
                    // TAKE THE TILE OUT OF THE STACK
                    ZombieCrushSagaTile tile = stackTiles.remove(stackTiles.size() - 1);
                    // PUT IT IN THE GRID
                    tileGrid[i][j].add(tile);
                    tile.setGridCell(i, j);
                    // WE'LL ANIMATE IT GOING TO THE GRID, SO FIGURE
                    // OUT WHERE IT'S GOING AND GET IT MOVING
                    float x = calculateTileXInGrid(i, 1);
                    float y = calculateTileYInGrid(j, 1);
                    tile.setTarget(x, y);
                    tile.startMovingToTarget(speed);
                    movingTiles.add(tile);
                }

            }
        }
        for (int i = 0; i < tileGrid.length; i++) {
            for (int j = tileGrid[0].length - 1; j >= 0; j--) {
                if (tileGrid[i][j].size() == 1 || tileGrid[i][j].size() == 2) {
                    ZombieCrushSagaTile tile = tileGrid[i][j].get(tileGrid[i][j].size() - 1);
                    if (tile.getTileType().equals(TILE_COLOR_TYPE)
                            || tile.getTileType().equals(TILE_W_TYPE) || tile.getTileType().equals(TILE_S_TYPE) || tile.getTileType().equals(BACK_GROUND_TYPE) || tile.getTileType().equals(BACK_GROUND1_TYPE)) {
                        this.replcaingTiles(this.sixTiles.remove(0), i, j);
                    }
                }

            }
        }
        for (int i = 0; i < tileGrid.length; i++) {
            for (int j = tileGrid[0].length - 1; j >= 0;) {
                if (tileGrid[i][j].size() == 1 || tileGrid[i][j].size() == 2) {
                    ZombieCrushSagaTile tile = tileGrid[i][j].get(tileGrid[i][j].size() - 1);
                    if (this.check(tile)) {
                        this.replcaingTiles(this.sixTiles.remove(0), i, j);
                    } else {
                        j--;
                    }
                } else {
                    j--;
                }
            }
        }
        // AND START ALL UPDATES
        beginGame();
        this.speed = 3;
        this.startPlaying = true;
        // CLEAR ANY WIN OR LOSS DISPLAY
        miniGame.getGUIDialogs().get(WIN_DIALOG_TYPE).setState(INVISIBLE_STATE);
        miniGame.getGUIDialogs().get(LOSS_DIALOG_TYPE).setState(INVISIBLE_STATE);
    }

    public void resetGame() {
        this.addBackTiles();
        this.clearScore();
        this.startPlaying = false;
        this.speed = 80;
        moveAllTilesToStack();
        for (ZombieCrushSagaTile tile : stackTiles) {
            tile.setX(-100);
            tile.setY(-100);
            tile.setState(VISIBLE_STATE);
        }
        // RANDOMLY ORDER THEM
        Collections.shuffle(stackTiles);
        // START THE CLOCK
        startTime = new GregorianCalendar();
        // NOW LET'S REMOVE THEM FROM THE STACK
        // AND PUT THE TILES IN THE GRID        
        for (int i = 0; i < gridColumns; i++) {
            for (int j = 0; j < gridRows; j++) {
                if (levelGrid[i][j] == 2 || levelGrid[i][j] == 1) {
                    ZombieCrushSagaTile tile = stackTiles.remove(stackTiles.size() - 1);
                    // PUT IT IN THE GRID
                    tileGrid[i][j].add(tile);
                    tile.setGridCell(i, j);
                    // WE'LL ANIMATE IT GOING TO THE GRID, SO FIGURE
                    // OUT WHERE IT'S GOING AND GET IT MOVING
                    float x = calculateTileXInGrid(i, 1);
                    float y = calculateTileYInGrid(j, 1);
                    tile.setTarget(x, y);
                    tile.startMovingToTarget(speed);
                    movingTiles.add(tile);
                }
            }
        }

        for (int i = 0; i < tileGrid.length; i++) {
            for (int j = tileGrid[0].length - 1; j >= 0; j--) {
                if (tileGrid[i][j].size() == 1 || tileGrid[i][j].size() == 2) {
                    ZombieCrushSagaTile tile = tileGrid[i][j].get(tileGrid[i][j].size() - 1);
                    if (tile.getTileType().equals(BACK_GROUND_TYPE) || tile.getTileType().equals(BACK_GROUND1_TYPE)) {
                        this.replcaingTiles(this.sixTiles.remove(0), i, j);
                    }
                }
            }
        }
        for (int i = 0; i < tileGrid.length; i++) {
            for (int j = tileGrid[0].length - 1; j >= 0;) {
                if (tileGrid[i][j].size() == 1 || tileGrid[i][j].size() == 2) {
                    ZombieCrushSagaTile tile = tileGrid[i][j].get(tileGrid[i][j].size() - 1);
                    if (this.check(tile) && tile.getTileType().equals(TILE_C_TYPE)) {
                        this.replcaingTiles(this.sixTiles.remove(0), i, j);
                    } else {
                        j--;
                    }
                } else {
                    j--;
                }
            }
        }

        beginGame();
        this.speed = 3;
        this.startPlaying = true;
        // CLEAR ANY WIN OR LOSS DISPLAY
        miniGame.getGUIDialogs().get(WIN_DIALOG_TYPE).setState(INVISIBLE_STATE);
        miniGame.getGUIDialogs().get(LOSS_DIALOG_TYPE).setState(INVISIBLE_STATE);
    }

    /**
     * Called each frame, this method updates all the game objects.
     *
     * @param game The Mahjong game to be updated.
     */
    @Override
    public void updateAll(MiniGame game) {
        // MAKE SURE THIS THREAD HAS EXCLUSIVE ACCESS TO THE DATA
        try {
            game.beginUsingData();
            // WE ONLY NEED TO UPDATE AND MOVE THE MOVING TILES
            for (int i = 0; i < movingTiles.size(); i++) {
                // GET THE NEXT TILE
                ZombieCrushSagaTile tile = movingTiles.get(i);
                // THIS WILL UPDATE IT'S POSITION USING ITS VELOCITY
                tile.update(game);
                // IF IT'S REACHED ITS DESTINATION, REMOVE IT
                // FROM THE LIST OF MOVING TILES
                if (!tile.isMovingToTarget()) {
                    movingTiles.remove(tile);
                }
            }
            // IF THE GAME IS STILL ON, THE TIMER SHOULD CONTINUE
            if (inProgress()) {
                // KEEP THE GAME TIMER GOING IF THE GAME STILL IS
                endTime = new GregorianCalendar();
            }
        } finally {
            // MAKE SURE WE RELEASE THE LOCK WHETHER THERE IS
            // AN EXCEPTION THROWN OR NOT
            game.endUsingData();
        }
    }

    /**
     * This method is for updating any debug text to present to the screen. In a
     * graphical application like this it's sometimes useful to display data in
     * the GUI.
     *
     * @param game The Mahjong game about which to display info.
     */
    @Override
    public void updateDebugText(MiniGame game) {
    }
    public void resetTestLevel(int[][] test) {
        this.speed = 80;
        addTestTiles();
        for (int i = 0; i < gridColumns; i++) {
            for (int j = 0; j < gridRows; j++) {
                if (tileGrid[i][j].size() == 2) {
                    tileGrid[i][j].remove(1);
                }
            }
        }
        // START THE CLOCK
        startTime = new GregorianCalendar();
        // NOW LET'S REMOVE THEM FROM THE STACK
        // AND PUT THE TILES IN THE GRID        
        for (int i = 0; i < gridColumns; i++) {
            for (int j = 0; j < gridRows; j++) {
                if (testTiles[test[i][j]].size() >= 1 && levelGrid[i][j] != 3) {
                    // TAKE THE TILE OUT OF THE STACK
                    ZombieCrushSagaTile tile = testTiles[test[i][j]].remove(testTiles[test[i][j]].size() - 1);
                    tile.setState(VISIBLE_STATE);
                    // PUT IT IN THE GRID
                    tileGrid[i][j].add(tile);
                    tile.setGridCell(i, j);
                    // WE'LL ANIMATE IT GOING TO THE GRID, SO FIGURE
                    // OUT WHERE IT'S GOING AND GET IT MOVING
                    float x = calculateTileXInGrid(i, 1);
                    float y = calculateTileYInGrid(j, 1);
                    tile.setTarget(x, y);
                    tile.startMovingToTarget(speed);
                    movingTiles.add(tile);
                }
            }
        }
        // AND START ALL UPDATES
        this.speed = 3;
        this.startPlaying = true;
    }
}
