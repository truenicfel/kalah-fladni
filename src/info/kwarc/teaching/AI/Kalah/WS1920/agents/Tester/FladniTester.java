/*
 * Kalah Fladni Agent
 *
 * Group members:
 *  - Adrian Goess
 *  - Florian Wasmeier
 *  - Nico Dassler <nico.dassler@fau.de>
 */
package info.kwarc.teaching.AI.Kalah.WS1920.agents.Tester;

import info.kwarc.teaching.AI.Kalah.WS1920.agents.board.FladniBoard;
import info.kwarc.teaching.AI.Kalah.WS1920.agents.kalahTree.FladniNode;
import info.kwarc.teaching.AI.Kalah.WS1920.agents.kalahTree.FladniTree;


/**
 *
 * @author root
 */
public class FladniTester extends Thread {

    public int bestMove;
    
    private final FladniBoard fladniBoard;
    
    private final int depth;
    
    private final boolean me;

    public FladniTester(FladniBoard fladniBoard, int depth, boolean me) {
        this.bestMove = -1;
        this.fladniBoard = fladniBoard;
        this.depth = depth;
        this.me = me;
    }
    
    
    
    @Override
    public void run() {
        
        // lets create the game tree
        FladniNode root = new FladniNode(fladniBoard, true);
        
        root.doIds(depth, 0);
        
        bestMove = root.getBestMove();
    }
    
}
