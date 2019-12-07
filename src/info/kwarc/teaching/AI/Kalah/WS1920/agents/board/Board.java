/*
 * Kalah Fladni Agent
 * 
 *  Group members:
 *   - Adrian Goess
 *   - Florian Wasmeier
 *   - Nico Dassler <nico.dassler@fau.de>
 */
package info.kwarc.teaching.AI.Kalah.WS1920.agents.board;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author nicfel
 */
public class Board {
    

    private final ArrayList<Integer> myHouses;
    private final ArrayList<Integer> enemyHouses;
    private final int myStore;
    private final int enemyStore;
    
    /**
     * Number of houses on one side of the map.
     * 
     * Total number of houses is 2*numberHouses.
     */
    private final int numberHouses;

    /**
     * Default initialization for the board.
     * 
     * This should be invoked when the game is starting. (init()-method)
     * 
     * @param initalSeedValue The number of seeds in
     * @param houses 
     */
    public Board(int initalSeedValue, int houses) {
        this.myHouses = new ArrayList<>(houses);
        this.enemyHouses = new ArrayList<>(houses);
        for (int i = 0; i < houses; i++) {
            this.myHouses.add(initalSeedValue);
            this.enemyHouses.add(initalSeedValue);
        }
        this.numberHouses = houses;
        this.myStore = 0;
        this.enemyStore = 0;
    }

    /**
     * This constructor is for internal purposes and is intended to be used
     * whenever a board gets modified.
     * 
     * Note: The lists are not copied.
     * 
     * @param myHouses
     * @param enemyHouses
     * @param myStore
     * @param enemyStore 
     */
    public Board(ArrayList<Integer> myHouses, ArrayList<Integer> enemyHouses, int numberHouses, int myStore, int enemyStore) {
        this.myHouses = myHouses;
        this.enemyHouses = enemyHouses;
        this.numberHouses = numberHouses;
        this.myStore = myStore;
        this.enemyStore = enemyStore;

    }
    
    /**
     * Perform a move. 
     * 
     * That means that all seeds from a given house are extracted and placed 
     * one by one in the adjacent houses (counter clock wise). 
     * 
     * @param me is it my side of the map?
     * @param house which house?
     * @return a map.entry (basically a pair) that contains if you are allowed to move again
     */
    public Map.Entry<Boolean, Board> makeMove(boolean me, int house) {
        
        if (house >= numberHouses || house < 0) {
            throw new IllegalArgumentException(
                    "The field with number " + house + "does not exist!"
            );
        }
        
        
        if (me) {
            if (myHouses.get(house) <= 0) {
                throw new IllegalArgumentException(
                        "This move is not legal. The house does "
                        + "not contain any seed. House: " + house
                );
            }
            return makeMoveMyHouse(house);
        } else {
            if (enemyHouses.get(house) <= 0) {
                throw new IllegalArgumentException(
                        "This move is not legal. The house does "
                        + "not contain any seed. House: " + house
                );
            }
            return makeMoveEnemyHouse(house);
        }
    }
    
    /**
     * This performs all the possible moves for a certain player and returns
     * the new boards and if it is possible to move again from that board.
     * 
     * @param me is it me performing the move?
     * @return List of Pairs containing if im allowed to move again and the new board.
     */
    public ArrayList<Map.Entry<Boolean, Board>> makeAllPossibleMoves(boolean me) {
        ArrayList<Map.Entry<Boolean, Board>> result = new ArrayList<>();
        if (me) {
            // me
            for (int index = 0; index < numberHouses; index++) {
                int numberSeeds = myHouses.get(index);
                if (numberSeeds > 0) {
                    // move possible
                    result.add(makeMove(me, index));
                }
            }
        } else {
            // enemy
            for (int index = 0; index < numberHouses; index++) {
                int numberSeeds = enemyHouses.get(index);
                if (numberSeeds > 0) {
                    // move possible
                    result.add(makeMove(me, index));
                }
            }
        }
        return result;
    }
    
    /**
     * Perform move on one of my houses.
     * 
     * The house is given with the parameter. The method will extract all
     * the seeds and place them with ccw orientation in all adjacent houses. It
     * will also place seeds in enemy houses and in the own store. The enemies 
     * store is ignored.
     * 
     * @param house the house which is used to perform the move.
     * @return a pair consisting of a boolean indicating if we can move again
     * and the new game board.
     */
    private Map.Entry<Boolean, Board> makeMoveMyHouse(int house) {
        ArrayList<Integer> copyMyHouses = new ArrayList(myHouses);
        ArrayList<Integer> copyEnemyHouses = new ArrayList(enemyHouses);
        int copyMyStore = myStore;
        int copyEnemyStore = enemyStore;
        boolean moveAgain = false;
        // take all seeds from that house into my virtual hand
        Integer seedsInHand = copyMyHouses.get(house);
        copyMyHouses.set(house, 0);
        
        while (seedsInHand > 0) {
            // now we need to place those seeds
            // place seeds in my houses
            Map.Entry<Integer, Integer> seedSettingResult 
                    = ccwSeedSetting(copyMyHouses, house + 1, seedsInHand);
            seedsInHand = seedSettingResult.getKey();
            // if our last seed was placed in a previously empty house
            // we are allowed to move again and steal the opponnents seeds
            // at the same index
            Integer lastSeedIndex = seedSettingResult.getValue();
            if (seedsInHand == 0 && myHouses.get(lastSeedIndex) == 0) {
                // we have placed the last seed in one of our own houses which
                // was previously empty
                moveAgain = true;
                // steal index
                int stealIndex = (numberHouses-1) - lastSeedIndex;
                Integer stolenSeeds = copyEnemyHouses.get(stealIndex);
                copyEnemyHouses.set(stealIndex, 0);
                copyMyStore += stolenSeeds;
                // also remove last seed planted
                copyMyHouses.set(stealIndex, 0);
                copyMyStore++;
            }
            // place seed in store
            if (seedsInHand > 0) {
                copyMyStore++;
                seedsInHand--;
            }
            // place seeds in enemy houses
            seedSettingResult = 
                    ccwSeedSetting(copyEnemyHouses, 0, seedsInHand);
            seedsInHand = seedSettingResult.getKey();
        }
        
        Board result = new Board(copyMyHouses, copyEnemyHouses, numberHouses, copyMyStore, copyEnemyStore);
        return Map.entry(
                moveAgain, 
                new Board(copyMyHouses, copyEnemyHouses, numberHouses, copyMyStore, copyEnemyStore)
        );
        
    }
    

    /**
     * This does the same as makeMoveMyHouse just for enemy houses.
     * 
     * @param house the house which is used to start the move.
     * @return a pair consisting of a boolean indicating if we can move again
     * and the new game board.
     */
    private Map.Entry<Boolean, Board> makeMoveEnemyHouse(int house) {
        ArrayList<Integer> copyMyHouses = new ArrayList(myHouses);
        ArrayList<Integer> copyEnemyHouses = new ArrayList(enemyHouses);
        int copyMyStore = myStore;
        int copyEnemyStore = enemyStore;
        boolean moveAgain = false;
        // take all seeds from that house into enemy's virtual hand
        Integer seedsInHand = copyEnemyHouses.get(house);
        copyEnemyHouses.set(house, 0);
        
        while (seedsInHand > 0) {
            // now we need to place those seeds
            // place seeds in enemy houses
            Map.Entry<Integer, Integer> seedSettingResult 
                    = ccwSeedSetting(copyEnemyHouses, house + 1, seedsInHand);
            seedsInHand = seedSettingResult.getKey();
            // if our last seed was placed in a previously empty house
            // we are allowed to move again and steal the opponnents seeds
            // at the same index
            Integer lastSeedIndex = seedSettingResult.getValue();
            if (seedsInHand == 0 && enemyHouses.get(lastSeedIndex) == 0) {
                // we have placed the last seed in one of our own houses which
                // was previously empty
                moveAgain = true;
                // steal index
                int stealIndex = (numberHouses-1) - lastSeedIndex;
                Integer stolenSeeds = copyMyHouses.get(stealIndex);
                copyMyHouses.set(stealIndex, 0);
                copyEnemyStore += stolenSeeds;
                // also remove last seed planted
                copyEnemyHouses.set(stealIndex, 0);
                copyEnemyStore++;
            }
            // place seed in store
            if (seedsInHand > 0) {
                copyEnemyStore++;
                seedsInHand--;
            }
            // place seeds in my houses
            seedSettingResult = 
                    ccwSeedSetting(copyMyHouses, 0, seedsInHand);
            seedsInHand = seedSettingResult.getKey();
        }
        
        return Map.entry(
                moveAgain, 
                new Board(copyMyHouses, copyEnemyHouses, numberHouses, copyMyStore, copyEnemyStore)
        );
        
    }
    
    /**
     * Place the seeds in the given copy of houses starting at the startIndex.
     * 
     * @param copyHouses the copy of houses to operate on
     * @param startIndex the index that will get the first seed placed in
     * @param seedsToBePlaced the number of seeds that need to be placed
     * @return a pair consisting of the remaining number of seeds and the index that
     * the last seed was placed in
     */
    private Map.Entry<Integer, Integer> ccwSeedSetting(ArrayList<Integer> copyHouses, int startIndex, int seedsToBePlaced) {
        int index = startIndex;
        int seeds = seedsToBePlaced;
        while (index < numberHouses && seeds > 0) {
            copyHouses.set(index, copyHouses.get(index) + 1);
            seeds--;
            index++;
        }
        // index needs to be decremented by one
        return Map.entry(seeds, index - 1);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("     ");
        for (int index = numberHouses - 1; index >= 0; index--) {
            stringBuilder.append(enemyHouses.get(index)).append("   ");
        }
        stringBuilder
                .append(System.lineSeparator())
                .append(enemyStore);
        for (int index = 0; index < numberHouses; index++) {
            stringBuilder.append("    ");
        }
        stringBuilder
                .append("     ")
                .append(myStore)
                .append(System.lineSeparator())
                .append("     ");
        myHouses.forEach((seeds) -> {
            stringBuilder.append(seeds).append("   ");
        });
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.myHouses);
        hash = 97 * hash + Objects.hashCode(this.enemyHouses);
        hash = 97 * hash + this.myStore;
        hash = 97 * hash + this.enemyStore;
        hash = 97 * hash + this.numberHouses;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Board other = (Board) obj;
        if (this.myStore != other.myStore) {
            return false;
        }
        if (this.enemyStore != other.enemyStore) {
            return false;
        }
        if (this.numberHouses != other.numberHouses) {
            return false;
        }
        if (!Objects.equals(this.myHouses, other.myHouses)) {
            return false;
        }
        if (!Objects.equals(this.enemyHouses, other.enemyHouses)) {
            return false;
        }
        return true;
    }


    
    
    
}
