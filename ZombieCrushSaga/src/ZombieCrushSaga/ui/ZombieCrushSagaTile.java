package ZombieCrushSaga.ui;

import java.util.ArrayList;
import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;
import ZombieCrushSaga.data.ZombieCrushSagaDataModel;

/**
 * This class represents a single tile in the game world.
 *
 * @author Zhenjin Wang
 */
public class ZombieCrushSagaTile extends Sprite {

    // EACH TILE IS EITHER AN A, B, OR C TYPE. IT MIGHT
    // BE BEST TO DO THIS WITH AN enum, BUT WE COULD
    // ALSO DO IT THIS WAY, WITH STRING CONSTANTS
    private String tileType;
    private boolean beginExchange;
    // WHEN WE PUT A TILE IN THE GRID WE TELL IT WHAT COLUMN AND ROW
    // IT IS LOCATED TO MAKE THE UNDO OPERATION EASY LATER ON
    private int gridColumn;
    private int gridRow;

    // THIS IS true WHEN THIS TILE IS MOVING, WHICH HELPS US FIGURE
    // OUT WHEN IT HAS REACHED A DESTINATION NODE
    private boolean movingToTarget;

    // THE TARGET COORDINATES IN WHICH IT IS CURRENTLY HEADING
    private float targetX;
    private float targetY;

    // WIN ANIMATIONS CAN BE GENERATED SIMPLY BY PUTTING TILES ON A PATH    
    private ArrayList<Integer> winPath;

    // THIS INDEX KEEPS TRACK OF WHICH NODE ON THE WIN ANIMATION PATH
    // THIS TILE IS CURRENTLY TARGETING

    private double moveSpeed;
    private boolean replaceSpecial;

    /**
     * This constructor initializes this tile for use, including all the
     * sprite-related data from its ancestor class, Sprite.
     */
    public ZombieCrushSagaTile(SpriteType initSpriteType,
            float initX, float initY,
            float initVx, float initVy,
            String initState, String initTileType) {
        // SEND ALL THE Sprite DATA TO A Sprite CONSTRUCTOR
        super(initSpriteType, initX, initY, initVx, initVy, initState);

        // INIT THE TILE TYPE
        tileType = initTileType;
    }

    // ACCESSOR METHODS
    // -getTileType
    // -getGridColumn
    // -getGridRow
    // -getTargetX
    // -getTargetY
    // -isMovingToTarget
    /**
     * Accessor method for getting this tile type.
     *
     * @return The tile type for this tile.
     */
    public String getTileType() {
        return tileType;
    }

    /**
     * Accessor method for getting the tile grid column that this tile is either
     * currently in, or was most recently in.
     *
     * @return The grid column this tile is or most recently was located in.
     */
    public int getGridColumn() {
        return gridColumn;
    }

    /**
     * Accessor method for getting the tile grid row that this tile is either
     * currently in, or was most recently in.
     *
     * @return The grid row this tile is or most recently was located in.
     */
    public int getGridRow() {
        return gridRow;
    }

    /**
     * Accessor method for getting the x-axis target coordinate for this tile.
     *
     * @return The x-axis target coordinate for this tile.
     */
    public float getTargetX() {
        return targetX;
    }

    /**
     * Accessor method for getting the y-axis target coordinate for this tile.
     *
     * @return The y-axis target coordinate for this teil.
     */
    public float getTargetY() {
        return targetY;
    }

    /**
     * Accessor method for getting whether this tile is currently moving toward
     * target coordinates or not.
     *
     * @return true if this tile is currently moving toward target coordinates,
     * false otherwise.
     */
    public boolean isMovingToTarget() {
        return this.movingToTarget;
    }
    public boolean getBeginExchange(){
        return this.beginExchange;
    }

    // MUTATOR METHODS
    // -setGridCell
    // -setTarget
    /**
     * Mutator method for setting both the grid column and row that this tile is
     * being placed in.
     *
     * @param initGridColumn The column this tile is being placed in in the
     * Mahjong game grid.
     *
     * @param initGridRow The row this tile is being placed in in the Mahjong
     * game grid.
     */
    public void setGridCell(int initGridColumn, int initGridRow) {
        gridColumn = initGridColumn;
        gridRow = initGridRow;
    }

    /**
     * Mutator method for setting bot the x-axis and y-axis target coordinates
     * for this tile.
     *
     * @param initTargetX The x-axis target coordinate to move this tile
     * towards.
     *
     * @param initTargetY The y-axis target coordinate to move this tile
     * towards.
     */
    public void setTarget(float initTargetX, float initTargetY) {
        targetX = initTargetX;
        targetY = initTargetY;
    }
    public void setBeginExchange(boolean status){
        this.beginExchange=status;
    }
    // METHOD FOR MATHING
    // -match
    /**
     * This method tests to see if this tile matches the testTile argument and
     * returns true if they match, false otherwise.
     *
     * @param testTile The tile to compare this tile to.0
     *
     * @return true if this tile is a match for the testTile argument, false
     * otherwise.
     */
    public boolean match(ZombieCrushSagaTile testTile) {
         // THIS DOESN'T WORK PROPERLY, IT SAYS EVERY TILE PAIR IS A MATCH
        if (testTile.getSpriteType().getSpriteTypeID() == this.getSpriteType().getSpriteTypeID()) {
            return true;
        }
        return false;
    }

    // PATHFINDING METHODS
    // -calculateDistanceToTarget
    // -startMovingToTarget
    /**
     * This method calculates the distance from this tile's current location to
     * the target coordinates on a direct line.
     *
     * @return The total distance on a direct line from where the tile is
     * currently, to where its target is.
     */
    public float calculateDistanceToTarget() {
        // GET THE X-AXIS DISTANCE TO GO
        float diffX = targetX - x;

        // AND THE Y-AXIS DISTANCE TO GO
        float diffY = targetY - y;

        // AND EMPLOY THE PYTHAGOREAN THEOREM TO CALCULATE THE DISTANCE
        float distance = (float) Math.sqrt((diffX * diffX) + (diffY * diffY));

        // AND RETURN THE DISTANCE
        return distance;
    }

    /**
     * Allows the tile to start moving by initializing its properly scaled
     * velocity vector pointed towards it target coordinates.
     *
     * @param maxVelocity The maximum velocity of this tile, which we'll then
     * compute the x and y axis components for taking into account the
     * trajectory angle.
     */
    public void startMovingToTarget(double maxVelocity) {
        // LET ITS POSITIONG GET UPDATED
        movingToTarget = true;
        this.moveSpeed = maxVelocity;
        // CALCULATE THE ANGLE OF THE TRAJECTORY TO THE TARGET
        float diffX = targetX - x;
        float diffY = targetY - y;
        float tanResult = diffY / diffX;
        float angleInRadians = (float) Math.atan(tanResult);

        // COMPUTE THE X VELOCITY COMPONENT
        vX = (float) (maxVelocity * Math.cos(angleInRadians));

        // CLAMP THE VELOCTY IN CASE OF NEGATIVE ANGLES
        if ((diffX < 0) && (vX > 0)) {
            vX *= -1;
        }
        if ((diffX > 0) && (vX < 0)) {
            vX *= -1;
        }

        // COMPUTE THE Y VELOCITY COMPONENT
        vY = (float) (maxVelocity * Math.sin(angleInRadians));

        // CLAMP THE VELOCITY IN CASE OF NEGATIVE ANGLES
        if ((diffY < 0) && (vY > 0)) {
            vY *= -1;
        }
        if ((diffY > 0) && (vY < 0)) {
            vY *= -1;
        }
    }

    // METHODS OVERRIDDEN FROM Sprite
    // -update
    /**
     * Called each frame, this method ensures that this tile is updated
     * according to the path it is on.
     *
     * @param game The Mahjong game this tile is part of.
     */
    @Override
    public void update(MiniGame game) {
        // IF WE ARE IN A POST-WIN STATE WE ARE PLAYING THE WIN
        // ANIMATION, SO MAKE SURE THIS TILE FOLLOWS THE PATH
        if (calculateDistanceToTarget() < moveSpeed) {
            vX = 0;
            vY = 0;
            x = targetX;
            y = targetY;
            movingToTarget = false;
            if (((ZombieCrushSagaDataModel) ((ZombieCrushSagaMiniGame) game).getDataModel()).getBeginUpdataBoard()) {
                if (this.beginExchange) {
                    ((ZombieCrushSagaDataModel) ((ZombieCrushSagaMiniGame) game).getDataModel()).setFinishExchange(true);
                    this.beginExchange = false;
                }

                if (((ZombieCrushSagaDataModel) ((ZombieCrushSagaMiniGame) game).getDataModel()).getLegalMove()) {
                    ((ZombieCrushSagaDataModel) ((ZombieCrushSagaMiniGame) game).getDataModel()).setStartClear(true);
                }
                ((ZombieCrushSagaDataModel) ((ZombieCrushSagaMiniGame) game).getDataModel()).setLegalMove(false);
            }
        }
        else {
            super.update(game);
        }
    }
}
