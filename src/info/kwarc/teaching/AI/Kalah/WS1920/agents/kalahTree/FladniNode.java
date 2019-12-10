/*
 * Kalah Fladni Agent
 * 
 *  Group members:
 *   - Adrian Goess
 *   - Florian Wasmeier
 *   - Nico Dassler <nico.dassler@fau.de>
 */
package info.kwarc.teaching.AI.Kalah.WS1920.agents.kalahTree;

import info.kwarc.teaching.AI.Kalah.WS1920.agents.board.FladniBoard;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Represents a simple node in the tree.
 * 
 * A node contains:
 *  - reference to the parent node
 *  - references to child nodes
 * 
 * @author Nico
 */
public class FladniNode {
    
    /**
     * Not all nodes have a parent.
     */
    private final Optional<FladniNode> parent;
    
    /**
     * This is empty if there are no children.
     */
    private final List<FladniNode> children;

    /**
     * The game state.
     */
    private final FladniBoard gameState;

    /**
     * The result of the evaluation function.
     */
    private int alpha;
    private int beta;
    private int value;
    
    /**
     * True if we are the next one to make a move.
     */
    private final boolean me;
    
    private int bestMove;
    
    /**
     * Create a FladniNode with given parent and children.
     * 
     * @param parent 
     * @param children 
     * @param board 
     */
    public FladniNode(FladniNode parent, List<FladniNode> children, FladniBoard board, boolean me) {
        this.parent = Optional.of(parent);
        this.children = children;
        this.gameState = board;
        this.me = me;
        
        this.alpha = Integer.MIN_VALUE;
        this.beta = Integer.MAX_VALUE;
        if (me) {
            this.value = Integer.MIN_VALUE;
        } else {
            this.value = Integer.MAX_VALUE;
        }
        this.bestMove = 0;
        
    }
    
    /**
     * Create a FladniNode with given parent.
     * 
     * Note: Children will be empty.
     * 
     * @param parent 
     */
    public FladniNode(FladniNode parent, FladniBoard board, boolean me) {
        this.parent = Optional.of(parent);
        this.children = new ArrayList<>();
        this.gameState = board;
        this.me = me;
        this.alpha = Integer.MIN_VALUE;
        this.beta = Integer.MAX_VALUE;
        if (me) {
            this.value = Integer.MIN_VALUE;
        } else {
            this.value = Integer.MAX_VALUE;
        }
        this.bestMove = 0;
    }

    /**
     * Create a FladniNode.
     * 
     * Note: Children and parent will be empty.
     */
    public FladniNode(FladniBoard board, boolean me) {
        this.parent = Optional.empty();
        this.children = new ArrayList<>();
        this.gameState = board;
        this.me = me;
        this.alpha = Integer.MIN_VALUE;
        this.beta = Integer.MAX_VALUE;
        if (me) {
            this.value = Integer.MIN_VALUE;
        } else {
            this.value = Integer.MAX_VALUE;
        }
        this.bestMove = 0;
    }

    /**
     * Create a FladniNode.
     * 
     * Note: parent will be empty.
     * 
     * @param children list of children.
     */
    public FladniNode(List<FladniNode> children, FladniBoard board, boolean me) {
        this.parent = Optional.empty();
        this.children = children;
        this.gameState = board;
        this.me = me;
        this.alpha = Integer.MIN_VALUE;
        this.beta = Integer.MAX_VALUE;
        if (me) {
            this.value = Integer.MIN_VALUE;
        } else {
            this.value = Integer.MAX_VALUE;
        }
        this.bestMove = 0;
    }
    
    /**
     * Get parent.
     * 
     * Note: Check if it has a parent with hasParent() or
     * by calling .isPresent() on the returned value.
     * 
     * @return Optional<FladniNode> 
     */
    public Optional<FladniNode> getParent() {
        return parent;
    }
    
    
    /**
     * Checks if this node has a parent.
     * 
     * @return true if it has a parent, otherwise false. 
     */
    public boolean hasParent() {
        return parent.isPresent();
    }

    /**
     * Get the children. 
     * 
     * Note: List might be empty. Changes made to returned list
     * will be present in this instance of the node. So take 
     * care of that.
     * 
     * @return the list of children.
     */
    public List<FladniNode> getChildren() {
        return children;
    }
    
    /**
     * Adds a child to the end of children list.
     * 
     * @param child the FladniNode to add as a child. 
     */
    public void addChild(FladniNode child) {
        getChildren().add(child);
    }
    
    /**
     * Return a stream of children.
     * 
     * @return Stream<FladniNode>
     */
    public Stream<FladniNode> getChildStream() {
        return getChildren().stream();
    }
    
    /**
     * Get number of children.
     * 
     * @return int 
     */
    public int getNumberOfChildren() {
        return getChildren().size();
    }
    
    /**
     * Returns the child at the given index if it exists.
     * 
     * @param index
     * 
     * @return Optional<FladniNode>
     */
    public Optional<FladniNode> getChildByIndex(int index) {
        Optional<FladniNode> result = Optional.empty();
        if (index >= 0 && index < getNumberOfChildren()) {
            result = Optional.of(getChildren().get(index));
        }
        return result;
    }
    
    public void expandNode() { 
        expandBoard(gameState);
    }
    
    public void expandBoard(FladniBoard board) {
        ArrayList<Map.Entry<Boolean, FladniBoard>> possibleMoves = board.makeAllPossibleMoves(me);
        for (Map.Entry<Boolean, FladniBoard> entry : possibleMoves) {
            if (entry.getKey()) {
                expandBoard(entry.getValue());
            } else {
                addChild(new FladniNode(this, entry.getValue(), !me));
            }
        }
        
    }
    
    public void doIds(int depthLimit, int currentDepth) {
        ids(gameState, depthLimit, currentDepth);
    }
    
    public int ids(FladniBoard board, int depthLimit, int currentDepth) {
//        System.out.println("Depth: " + currentDepth);
//        System.out.println("Board: ");
//        System.out.println(board.toString());
//        System.out.println("-----------");
        
        int childrenAppended = 0;
        if (currentDepth < depthLimit) {
            // get all house indices that could be used to make a move
            ArrayList<Integer> nonEmptyHouseIndices = board.getNonEmptyHouseIndices(me);
            int childrenIndex = 0;
            int moveIndex = 0;
            boolean keepSearching = true;
            while (keepSearching && moveIndex < nonEmptyHouseIndices.size()) {
                Map.Entry<Boolean, FladniBoard> makeMove = board.makeMove(me, nonEmptyHouseIndices.get(moveIndex));
                if (makeMove.getKey()) {
                    int bestMoveBefore = bestMove;
                    // we are allowed to move again
                    // we call ids on this node with a different gamestate
                    int childrenAppendedByRecursiveCall = 
                            ids(makeMove.getValue(), depthLimit, currentDepth);
                    childrenAppended += childrenAppendedByRecursiveCall;
                    // this has to be -1 because the child we are currently processing
                    // has not been added but replaced with the children from the call
                    // to ids above
                    childrenIndex += childrenAppendedByRecursiveCall - 1;
                    if (bestMoveBefore != bestMove) {
                        bestMove = nonEmptyHouseIndices.get(moveIndex);
                    }
                } else {
                    // create new child
                    FladniNode child = new FladniNode(this, makeMove.getValue(), !me);
                    child.setAlpha(getAlpha());
                    child.setBeta(getBeta());
                    child.doIds(depthLimit, currentDepth + 1);
                    addChild(child);
                    childrenAppended++;
                    // we can now ask our fully explored new child for its alpha, beta and value
                    keepSearching = alphaBetaPruning(childrenIndex, nonEmptyHouseIndices.get(moveIndex));
               }
                moveIndex++;
                childrenIndex++;
            }
            if (me) {
                // max/alpha
                setValue(getAlpha());
            } else {
                setValue(getBeta());                
            }
        } else {
            value = gameState.evaluate();    
        }
        return childrenAppended;
    }

    public int getBestMove() {
        return bestMove;
    }
        
        
private boolean alphaBetaPruning(int childrenIndex, int moveIndex){
    boolean keepSearching;
    
    if (me) {
        // max/alpha
        int childValue = getChildren().get(childrenIndex).getValue();

        if (childValue > getAlpha()) {
            setAlpha(childValue);
            // best move updaten
            bestMove = moveIndex;
        }
        keepSearching = !(childValue >= getBeta());

    } else {
        // min/beta
        int childValue = getChildren().get(childrenIndex).getValue();
        if (childValue < getBeta()) {
            setBeta(childValue);
            // best move updaten
            bestMove = moveIndex;
        }
        keepSearching = !(childValue <= getAlpha());

    }
    
//    if (!keepSearching) {
//        System.out.println("Pruned something :)");
//    }
    
    return keepSearching;
}   
//    public void expandNodeIDS(int depthLimit, int currentDepth) {
//        System.out.println("Level: " + currentDepth);
//        System.out.println("Board: ");
//        System.out.println(gameState.toString());
//        expandNode();
//        
//        // TODO: maybe sort children before going dowwn
//        
//        if (currentDepth < depthLimit) {
//            // take first child and call expandNodeIDS
//            getChildren().forEach((child) -> {
//                child.expandNodeIDS(depthLimit, currentDepth + 1);
//            // welches child hat den besten wert?
//            if (me){
//                // alpha
//                // it was my turn so i want to maximize that means ill choose
//                // the biggest alpha value of my childs that is available
//                int maximum = getChildren().get(0).getValue();
//                for (FladniNode fladniNode : getChildren()) {
//                    if (maximum < fladniNode.getValue()) {
//                        maximum = fladniNode.getValue();
//                    }
//                }
//            } else {
//                // beta
//                // it is not my turn so i want to minimize that means ill choose
//                // the smallest beta value of my childs that is available
//                int minimum = getChildren().get(0).getValue();
//                for (FladniNode fladniNode : getChildren()) {
//                    if (minimum < fladniNode.getValue()) {
//                        minimum = fladniNode.getValue();
//                    }
//                }
//            }
//            });
//        } else {
//            // last node level explored by ids
//            value = gameState.evaluate();
//        }
//        
//        
//        
//        
//        
//            
//    }
    
    
//    public int ids(FladniBoard board, int depthLimit, int currentDepth) {
//        System.out.println("Level: " + currentDepth);
//        System.out.println("Game State: ");
//        System.out.println(gameState.toString());
//        System.out.println("Board: ");
//        System.out.println(board.toString());
//        System.out.println("-----------");
//        // funktion die uns alle indices expanden
//        
//        ArrayList<Integer> nonEmptyHouseIndices = board.getNonEmptyHouseIndices(me);
//        int upperBound = nonEmptyHouseIndices.size();
//        int offset = 0;
//        for (int index = 0; index < upperBound; index++) {
//            Map.Entry<Boolean, FladniBoard> makeMove = board.makeMove(me, nonEmptyHouseIndices.get(index - offset));
//            if (makeMove.getKey()) {
//                // we are allowed to move again
//                // we call ids on this node with a different gamestate
//                int childrenAppended = ids(makeMove.getValue(), depthLimit, currentDepth);
//                index = childrenAppended - 1;
//                upperBound += childrenAppended - 1;
//                offset += childrenAppended - 1;
//            } else {
//                if (currentDepth < depthLimit) {
//                    // create new child
//                    FladniNode child = new FladniNode(this, makeMove.getValue(), !me);
//                    child.setAlpha(getAlpha());
//                    child.setBeta(getBeta());
//                    child.doIds(depthLimit, currentDepth + 1);
//                    addChild(child);
//                } else {
//                    // last node level explored by ids
//                    value = gameState.evaluate();
//                    break;
//                }
//
//                // we can now ask our fully explored new child for its alpha, beta and value
//                if (me) {
//                    // max/alpha
//                    int childValue = getChildren().get(index).getValue();
//
//                    if (childValue > getAlpha()) {
//                        setAlpha(childValue);
//                    }
//                    if (childValue >= getBeta()) {
//                        // cancel loop
//                        index = nonEmptyHouseIndices.size();
//                    }
//
//                } else {
//                    // min/beta
//                    int childValue = getChildren().get(index).getValue();
//                    if (childValue < getBeta()) {
//                        setBeta(childValue);
//                    }
//                    if (childValue <= getAlpha()) {
//                        // cancel loop
//                        index = nonEmptyHouseIndices.size();
//                    }
//
//                }
//
//            }
//
//        }
//        if (currentDepth < depthLimit){
//            if (me) {
//                // max/alpha
//                setValue(getAlpha());
//            } else {
//                setValue(getBeta());
//            }
//        }
//
//        return nonEmptyHouseIndices.size();
//
//    }

    public FladniBoard getGameState() {
        return gameState;
    }

    public boolean isMe() {
        return me;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }





    
    
    
    
    
}
