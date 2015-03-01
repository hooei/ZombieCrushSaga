package ZombieCrushSaga;

import java.awt.Color;
import java.awt.Font;

/**
 * This class stores the types of controls and their possible states which we'll
 * use to control the flow of the application. Note that these control types and
 * states are NOT flavor-specific.
 *
 * @author Zhenjin Wang,Richard McKenna
 */
public class ZombieCrushSagaConstants {
    // WE ONLY HAVE A LIMITIED NUMBER OF UI COMPONENT TYPES IN THIS APP

    // TILE SPRITE TYPES
    public static final String TILE_S_TYPE = "TILE_S_TYPE";
    public static final String TILE_W_TYPE = "TILE_W_TYPE";
    public static final String TILE_C_TYPE = "TILE_C_TYPE";
    public static final String TILE_G_TYPE = "TILE_G_TYPE";
    public static final String TILE_COLOR_TYPE = "TILE_COLOR_TYPE";
    public static final String TILE_SPRITE_TYPE_PREFIX = "TILE_";

    // EACH SCREEN HAS ITS OWN BACKGROUND TYPE
    public static final String BACKGROUND_TYPE = "BACKGROUND_TYPE";

    // THIS REPRESENTS THE BUTTONS ON THE SPLASH SCREEN FOR LEVEL SELECTION
    public static final String LEVEL_SELECT_BUTTON_TYPE = "LEVEL_SELECT_BUTTON_TYPE";
    public static final String LEVEL_BUTTON_TYPE = "LEVEL_1_BUTTON_TYPE";
    // IN-GAME UI CONTROL TYPES
    public static final String NEW_GAME_BUTTON_TYPE = "NEW_GAME_BUTTON_TYPE";
    public static final String TIME_TYPE = "TIME_TYPE";
    public static final String STATS_BUTTON_TYPE = "STATS_BUTTON_TYPE";
    public static final String TILE_STACK_TYPE = "TILE_STACK_TYPE";
    public static final String BACK_BUTTON_TYPE = "BACK_BUTTON_TYPE";
    public static final String TILES_COUNT_CONTAINER_TYPE = "TILES_COUNT_CONTAINER_TYPE";

    public static final String RESET_GAME_BUTTON_TYPE = "RESET_GAME_BUTTON_TYPE";
    public static final String PLAY_GAME_BUTTON_TYPE = "PLAY_GAME_BUTTON_TYPE";
    public static final String QUIT_GAME_BUTTON_TYPE = "QUIT_GAME_BUTTON_TYPE";
    public static final String QUIT_GAME_SAGA_BUTTON_TYPE = "QUIT_GAME_SAGA_BUTTON_TYPE";
    public static final String UP_BUTTON_TYPE = "UP_BUTTON_TYPE";
    public static final String DOWN_BUTTON_TYPE = "DOWN_BUTTON_TYPE";
    public static final String PLAY_LEVEL_BUTTON_TYPE = "PLAY_LEVEL_BUTTON_TYPE";
    public static final String CLOSE_LEVEL_SCORE_BUTTON_TYPE = "CLOSE_LEVEL_SCORE_BUTTON_TYPE";
    public static final String SCORE_BAR_TYPE = "SCORE_BAR_TYPE";
    public static final String PRINT_TYPE = "PRINT_TYPE";
    public static final String NUM_LEVEL_TYPE = "NUM_LEVEL_TYPE";
    public static final String HS_TYPE = "HS_TYPE";
    public static final String DOWN_S_BUTTON_TYPE = "DOWN_S_BUTTON_TYPE";
    public static final String UP_S_BUTTON_TYPE = "UP_S_BUTTON_TYPE";
    public static final String QUIT_S_BUTTON_TYPE = "QUIT_S_BUTTON_TYPE";
    public static final String STAR_1_TYPE = "STAR_1_TYPE";
    public static final String STAR_2_TYPE = "STAR_2_TYPE";
    public static final String STAR_3_TYPE = "STAR_3_TYPE";
    public static final String STAR_EARN_1_TYPE = "STAR_EARN_1_TYPE";
    public static final String STAR_EARN_2_TYPE = "STAR_EARN_2_TYPE";
    public static final String STAR_EARN_3_TYPE = "STAR_EARN_3_TYPE";
    public static final String SMASH_1_TYPE = "SMASH_1_TYPE";
    public static final String SMASH_2_TYPE = "SMASH_2_TYPE";
    // DIALOG TYPES
    public static final String STATS_DIALOG_TYPE = "STATS_DIALOG_TYPE";
    public static final String WIN_DIALOG_TYPE = "WIN_DIALOG_TYPE";
    public static final String LOSS_DIALOG_TYPE = "LOSS_DIALOG_TYPE";

    // WE'LL USE THESE STATES TO CONTROL SWITCHING BETWEEN THE TWO
    public static final String SPLASH_SCREEN_STATE = "SPLASH_SCREEN_STATE";
    public static final String GAME_SCREEN_STATE = "GAME_SCREEN_STATE";
    public static final String SAGA_SCREEN_STATE = "SAGA_SCREEN_STATE";
    public static final String LEVEL_SCREEN_STATE = "LEVEL_SCREEN_STATE";
    // THE TILES MAY HAVE 4 STATES:
    // - INVISIBLE_STATE: USED WHEN ON THE SPLASH SCREEN, MEANS A TILE
    // IS NOT DRAWN AND CANNOT BE CLICKED
    // - VISIBLE_STATE: USED WHEN ON THE GAME SCREEN, MEANS A TILE
    // IS VISIBLE AND CAN BE CLICKED (TO SELECT IT), BUT IS NOT CURRENTLY SELECTED
    // - SELECTED_STATE: USED WHEN ON THE GAME SCREEN, MEANS A TILE
    // IS VISIBLE AND CAN BE CLICKED (TO UNSELECT IT), AND IS CURRENTLY SELECTED     
    // - NOT_AVAILABLE_STATE: USED FOR A TILE THE USER HAS CLICKED ON THAT
    // IS NOT FREE. THIS LET'S US GIVE THE USER SOME FEEDBACK
    public static final String INVISIBLE_STATE = "INVISIBLE_STATE";
    public static final String VISIBLE_STATE = "VISIBLE_STATE";
    public static final String SELECTED_STATE = "SELECTED_STATE";
    public static final String INCORRECTLY_SELECTED_STATE = "NOT_AVAILABLE_STATE";
    public static final String MOUSE_OVER_STATE = "MOUSE_OVER_STATE";
    public static final String BACK_GROUND_TYPE = "BACK_GROUND_TYPE";
    public static final String BACK_GROUND1_TYPE = "BACK_GROUND1_TYPE";
    // THE BUTTONS MAY HAVE 2 STATES:
    // - INVISIBLE_STATE: MEANS A BUTTON IS NOT DRAWN AND CAN'T BE CLICKED
    // - VISIBLE_STATE: MEANS A BUTTON IS DRAWN AND CAN BE CLICKED
    // - MOUSE_OVER_STATE: MEANS A BUTTON IS DRAWN WITH SOME HIGHLIGHTING
    // BECAUSE THE MOUSE IS HOVERING OVER THE BUTTON

    // UI CONTROL SIZE AND POSITION SETTINGS
    // OR POSITIONING THE LEVEL SELECT BUTTONS
    public static final int LEVEL_BUTTON_WIDTH = 200;
    public static final int LEVEL_BUTTON_MARGIN = 5;
    public static final int LEVEL_BUTTON_Y = 570;

    // FOR STACKING TILES ON THE GRID
    public static final int NUM_TILES = 144;
    public static final int TILE_IMAGE_OFFSET = 1;
    public static final int TILE_IMAGE_WIDTH = 65;
    public static final int TILE_IMAGE_HEIGHT = 65;
    public static final int Z_TILE_OFFSET = 5;
    // FOR MOVING TILES AROUND
    public static final int MAX_TILE_VELOCITY = 70;

    // UI CONTROLS POSITIONS IN THE GAME SCREEN
    public static final int CONTROLS_MARGIN = 0;
    public static final int NEW_BUTTON_X = 0;
    public static final int NEW_BUTTON_Y = 0;
    public static final int BACK_BUTTON_X = NEW_BUTTON_X + 130 + CONTROLS_MARGIN;
    public static final int BACK_BUTTON_Y = 0;
    public static final int TILES_COUNT_CONTAINER_X = BACK_BUTTON_X + 130 + CONTROLS_MARGIN;
    public static final int TILES_COUNT_CONTAINER_Y = 0;
    public static final int TILE_TEXT_OFFSET = 55;
    public static final int TILE_OFFSET = 135;
    public static final int TIME_X = TILES_COUNT_CONTAINER_X + 232 + CONTROLS_MARGIN;
    public static final int TIME_Y = 0;
    public static final int TIME_OFFSET = 130;
    public static final int TIME_TEXT_OFFSET = 55;
    public static final int STATS_X = TIME_X + 310 + CONTROLS_MARGIN;
    public static final int STATS_Y = 0;
    public static final int UNDO_BUTTON_X = STATS_X + 160 + CONTROLS_MARGIN;
    public static final int UNDO_BUTTON_Y = 0;
    public static final int TILE_STACK_X = UNDO_BUTTON_X + 130 + CONTROLS_MARGIN;
    public static final int TILE_STACK_Y = 0;
    public static final int TILE_STACK_OFFSET_X = 30;
    public static final int TILE_STACK_OFFSET_Y = 12;
    public static final int TILE_STACK_2_OFFSET_X = 105;

    // THESE ARE USED FOR FORMATTING THE TIME OF GAME
    public static final long MILLIS_IN_A_SECOND = 1000;
    public static final long MILLIS_IN_A_MINUTE = 1000 * 60;
    public static final long MILLIS_IN_AN_HOUR = 1000 * 60 * 60;

    // USED FOR DOING OUR VICTORY ANIMATION
    public static final int WIN_PATH_NODES = 100;
    public static final int WIN_PATH_TOLERANCE = 4;
    public static final int WIN_PATH_COORD = 100;

    // COLORS USED FOR RENDERING VARIOUS THINGS, INCLUDING THE
    // COLOR KEY, WHICH REFERS TO THE COLOR TO IGNORE WHEN
    // LOADING ART.
    public static final Color COLOR_KEY = Color.WHITE;//new Color(255, 174, 201);
    public static final Color DEBUG_TEXT_COLOR = Color.BLACK;
    public static final Color TEXT_DISPLAY_COLOR = new Color(10, 160, 10);
    public static final Color SELECTED_TILE_COLOR = new Color(255, 255, 0, 100);
    public static final Color INCORRECTLY_SELECTED_TILE_COLOR = new Color(255, 50, 50, 100);
    public static final Color STATS_COLOR = new Color(0, 60, 0);

    // FONTS USED DURING FOR TEXTUAL GAME DISPLAYS
    public static final Font TEXT_DISPLAY_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 48);
    public static final Font DEBUG_TEXT_FONT = new Font(Font.MONOSPACED, Font.BOLD, 14);
    public static final Font STATS_FONT = new Font(Font.MONOSPACED, Font.BOLD, 24);

    // SMASH
    public static final String SMASH = "SMASH";
   
    // level target
    public static final int Level_moves[] = {6, 15, 18, 15, 20, 25, 50, 20, 25, 40};
    public static final int Level_1_Star[] = {300, 1900, 4000, 4500, 5000, 9000, 60000, 20000, 22000, 40000};
    public static final int Level_2_Star[] = {400, 2100, 6000, 6000, 8000, 11000, 75000, 30000, 44000, 70000};
    public static final int Level_3_Star[] = {500, 2400, 8000, 9000, 12000, 13000, 85000, 45000, 66000, 100000};
    public static final String Level_Name[] = {"Level1", "Level2", "Level3", "Level4", "Level5", "Level6", "Level7", "Level8", "Level9", "Level10"};
    public static final int pos_x[] = {181, 230, 289, 337, 388, 449, 511, 586, 648, 714};
    public static final int pos_y = 65;/// for first 10 level
    public static boolean levelLimit[] = new boolean[10];
    public static int totalTile[] = {40, 73, 45, 69, 44, 45, 80, 73, 39, 78};
    public static boolean jelly[] = {false, false, false, false, false, true, true, true, true, true};

}
