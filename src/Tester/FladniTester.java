/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tester;

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
