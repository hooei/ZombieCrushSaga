package ZombieCrushSaga.file;

import ZombieCrushSaga.ZombieCrushSaga;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import ZombieCrushSaga.ZombieCrushSaga.ZombieCrushSagaPropertyType;
import ZombieCrushSaga.data.ZombieCrushSagaLevelRecord;
import ZombieCrushSaga.data.ZombieCrushSagaDataModel;
import ZombieCrushSaga.data.ZombieCrushSagaRecord;
import ZombieCrushSaga.ui.ZombieCrushSagaMiniGame;
import properties_manager.PropertiesManager;
import static ZombieCrushSaga.ZombieCrushSagaConstants.*;

/**
 * This class provides services for efficiently loading and saving binary files
 * for the Mahjong game application.
 *
 * @author Zhenjin Wang, Richard McKenna
 */
public class ZombieCrushSagaFileManager {

    // WE'LL LET THE GAME KNOW WHEN DATA LOADING IS COMPLETE
    private ZombieCrushSagaMiniGame miniGame;

    /**
     * Constructor for initializing this file manager, it simply keeps the game
     * for later.
     *
     * @param initMiniGame The game for which this class loads data.
     */
    public ZombieCrushSagaFileManager(ZombieCrushSagaMiniGame initMiniGame) {
        // KEEP IT FOR LATER
        miniGame = initMiniGame;
    }

    /**
     * This method loads the contents of the levelFile argument so that the
     * player may then play that level.
     *
     * @param levelFile Level to load.
     */
    public void loadLevel(String levelFile) {
        // LOAD THE RAW DATA SO WE CAN USE IT
        // OUR LEVEL FILES WILL HAVE THE DIMENSIONS FIRST,
        // FOLLOWED BY THE GRID VALUES
        try {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String dataPath = props.getProperty(ZombieCrushSaga.ZombieCrushSagaPropertyType.DATA_PATH);
            File fileToOpen = new File(dataPath + "./zomjong/" + levelFile + ".zom");

            // LET'S USE A FAST LOADING TECHNIQUE. WE'LL LOAD ALL OF THE
            // BYTES AT ONCE INTO A BYTE ARRAY, AND THEN PICK THAT APART.
            // THIS IS FAST BECAUSE IT ONLY HAS TO DO FILE READING ONCE
            byte[] bytes = new byte[Long.valueOf(fileToOpen.length()).intValue()];
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            FileInputStream fis = new FileInputStream(fileToOpen);
            BufferedInputStream bis = new BufferedInputStream(fis);

            // HERE IT IS, THE ONLY READY REQUEST WE NEED
            bis.read(bytes);
            bis.close();

            // NOW WE NEED TO LOAD THE DATA FROM THE BYTE ARRAY
            DataInputStream dis = new DataInputStream(bais);

            // NOTE THAT WE NEED TO LOAD THE DATA IN THE SAME
            // ORDER AND FORMAT AS WE SAVED IT
            // FIRST READ THE GRID DIMENSIONS
            int initGridColumns = dis.readInt();
            int initGridRows = dis.readInt();
            int[][] newGrid = new int[initGridColumns][initGridRows];
            // AND NOW ALL THE CELL VALUES
            for (int i = 0; i < initGridColumns; i++) {
                for (int j = 0; j < initGridRows; j++) {
                    newGrid[i][j] = dis.readInt();
                }
            }
            // EVERYTHING WENT AS PLANNED SO LET'S MAKE IT PERMANENT
            ZombieCrushSagaDataModel dataModel = (ZombieCrushSagaDataModel) miniGame.getDataModel();
            dataModel.initLevelGrid(newGrid, initGridColumns, initGridRows);
            dataModel.setCurrentLevel(levelFile);
            miniGame.updateBoundaries();
        } catch (Exception e) {
            // LEVEL LOADING ERROR
            miniGame.getErrorHandler().processError(ZombieCrushSagaPropertyType.LOAD_LEVEL_ERROR);
        }
    }

    /**
     * This method loads the player record from the records file so that the
     * user may view stats.
     *
     * @return The fully loaded record from the player record file.
     */
    public ZombieCrushSagaRecord loadRecord() {
        ZombieCrushSagaRecord recordToLoad = new ZombieCrushSagaRecord();

        // LOAD THE RAW DATA SO WE CAN USE IT
        // OUR LEVEL FILES WILL HAVE THE DIMENSIONS FIRST,
        // FOLLOWED BY THE GRID VALUES
        try {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String dataPath = props.getProperty(ZombieCrushSagaPropertyType.DATA_PATH);
            String recordPath = dataPath + props.getProperty(ZombieCrushSagaPropertyType.RECORD_FILE_NAME);
            File fileToOpen = new File(recordPath);
            // LET'S USE A FAST LOADING TECHNIQUE. WE'LL LOAD ALL OF THE
            // BYTES AT ONCE INTO A BYTE ARRAY, AND THEN PICK THAT APART.
            // THIS IS FAST BECAUSE IT ONLY HAS TO DO FILE READING ONCE
            byte[] bytes = new byte[Long.valueOf(fileToOpen.length()).intValue()];
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            FileInputStream fis = new FileInputStream(fileToOpen);
            BufferedInputStream bis = new BufferedInputStream(fis);
            // HERE IT IS, THE ONLY READY REQUEST WE NEED
            bis.read(bytes);
            bis.close();
            // NOW WE NEED TO LOAD THE DATA FROM THE BYTE ARRAY
            DataInputStream dis = new DataInputStream(bais);
            // NOTE THAT WE NEED TO LOAD THE DATA IN THE SAME
            // ORDER AND FORMAT AS WE SAVED IT
            // FIRST READ THE NUMBER OF LEVELS
            int numLevels = dis.readInt();
            for (int i = 0; i < numLevels; i++) {
                String levelName = dis.readUTF();
                ZombieCrushSagaLevelRecord rec = new ZombieCrushSagaLevelRecord();
                rec.gamesPlayed = dis.readInt();
                rec.wins = dis.readInt();
                rec.losses = dis.readInt();
                rec.HighestScore = dis.readLong();
                rec.visible = dis.readBoolean();
                recordToLoad.addMahjongLevelRecord(levelName, rec);
                if (i > 0) {
                    if (rec.HighestScore >= Level_1_Star[i] || rec.visible) {
                        levelLimit[i] = true;
                    }
                } 
            }
        } catch (Exception e) {
            // THERE WAS NO RECORD TO LOAD, SO WE'LL JUST RETURN AN
            // EMPTY ONE AND SQUELCH THIS EXCEPTION
        }
        return recordToLoad;
    }

    /**
     * Save record to local file
     */
    public void saveRecord(ZombieCrushSagaRecord recordToSave) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String dataPath = props.getProperty(ZombieCrushSagaPropertyType.DATA_PATH);
        String recordPath = dataPath + props.getProperty(ZombieCrushSagaPropertyType.RECORD_FILE_NAME);
        try {
            byte[] bytes = recordToSave.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            FileOutputStream fis = new FileOutputStream(recordPath);
            BufferedOutputStream bis = new BufferedOutputStream(fis);
            DataOutputStream out = new DataOutputStream(bis);
            out.write(bytes, 0, bytes.length);
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method loads the contents of the levelTestFile argument so that the
     * player may then play that level.
     *
     * @param levelFile Level to load.
     */
    public int[][] loadTestLevel(int test) {
        int[][] newGrid = null;
        // LOAD THE RAW DATA SO WE CAN USE IT
        // OUR LEVEL FILES WILL HAVE THE DIMENSIONS FIRST,
        // FOLLOWED BY THE GRID VALUES
        try {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String dataPath = props.getProperty(ZombieCrushSaga.ZombieCrushSagaPropertyType.DATA_PATH);
            File fileToOpen = new File(dataPath + "./zomjong/" + "test" + test + ".zom");

            // LET'S USE A FAST LOADING TECHNIQUE. WE'LL LOAD ALL OF THE
            // BYTES AT ONCE INTO A BYTE ARRAY, AND THEN PICK THAT APART.
            // THIS IS FAST BECAUSE IT ONLY HAS TO DO FILE READING ONCE
            byte[] bytes = new byte[Long.valueOf(fileToOpen.length()).intValue()];
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            FileInputStream fis = new FileInputStream(fileToOpen);
            BufferedInputStream bis = new BufferedInputStream(fis);

            // HERE IT IS, THE ONLY READY REQUEST WE NEED
            bis.read(bytes);
            bis.close();

            // NOW WE NEED TO LOAD THE DATA FROM THE BYTE ARRAY
            DataInputStream dis = new DataInputStream(bais);

            // NOTE THAT WE NEED TO LOAD THE DATA IN THE SAME
            // ORDER AND FORMAT AS WE SAVED IT
            // FIRST READ THE GRID DIMENSIONS
            int initGridColumns = dis.readInt();
            int initGridRows = dis.readInt();
            newGrid = new int[initGridColumns][initGridRows];
            // AND NOW ALL THE CELL VALUES
            for (int i = 0; i < initGridColumns; i++) {
                for (int j = 0; j < initGridRows; j++) {
                    newGrid[i][j] = dis.readInt();
                }
            }
        } catch (Exception e) {
            // LEVEL LOADING ERROR
            miniGame.getErrorHandler().processError(ZombieCrushSagaPropertyType.LOAD_LEVEL_ERROR);
        }
        return newGrid;
    }
}
