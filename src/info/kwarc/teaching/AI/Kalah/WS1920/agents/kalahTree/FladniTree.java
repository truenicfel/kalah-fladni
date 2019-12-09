/*
 * Kalah Fladni Agent
 *
 * Group members:
 *  - Adrian Goess
 *  - Florian Wasmeier
 *  - Nico Dassler <nico.dassler@fau.de>
 */
package info.kwarc.teaching.AI.Kalah.WS1920.agents.kalahTree;

import info.kwarc.teaching.AI.Kalah.WS1920.agents.board.FladniBoard;

/**
 * A basic tree implementation for the Fladni Agent!
 * 
 * @author Nico
 */
public class FladniTree {
    
    private final FladniNode root;

    public FladniTree(FladniBoard board, boolean me) {
        this.root = new FladniNode(board, me);
    }
    
    public FladniTree(FladniNode root) {
        this.root = root;
    }
    
    public void startIdsAtRoot(int limit) {
        root.doIds(limit, 0);
    }
    
    
}
