/*
 * Kalah Fladni Agent
 *
 * Group members:
 *  - Adrian Goess
 *  - Florian Wasmeier
 *  - Nico Dassler <nico.dassler@fau.de>
 */
package info.kwarc.teaching.AI.Kalah.WS1920.agents;

import Tester.FladniTester;
import info.kwarc.teaching.AI.Kalah.Agents.Agent;
import info.kwarc.teaching.AI.Kalah.Board;
import info.kwarc.teaching.AI.Kalah.WS1920.agents.board.FladniBoard;
import info.kwarc.teaching.AI.Kalah.WS1920.agents.kalahTree.FladniNode;
import info.kwarc.teaching.AI.Kalah.WS1920.agents.kalahTree.FladniTree;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wasme
 */
public class FladniAgent extends Agent { 

    private final String name;

    private final List<String> nameList;
    
    private int playerNumber;
    
    private Board board;
    
    public FladniAgent() {
        super();
        this.name = "Fladni";
        // this is an illegal value --> has to be 1 or 2
        // will be properly initialized in init()
        this.playerNumber = 0;
        nameList = new ArrayList<>();
        nameList.add("Nico Dassler");
        nameList.add("Florian Wasmeier");
        nameList.add("Adrian Goess");
    }
    
    @Override
    public String name() {
        return name;
    }

    @Override
    public Iterable<String> students() {
        return nameList;
    }

    @Override
    public void init(Board board, boolean playerOne) {
        
        // baum aufzubauen
        this.board = board;
        if (playerOne) {
            setPlayerNumber(1);
        } else {
            setPlayerNumber(2);
        }
    }

    @Override
    public int move() {
        // das neue board auslesen (this.board)
        // ein fladni board erzeugen
        // baum entfalten
        ArrayList<Integer> myHouses = new ArrayList<>();
        ArrayList<Integer> enemyHouses = new ArrayList<>();
        int enemyPlayerNumber = getPlayerNumber()%2 + 1;
        for (int index = 0; index < board.houses(); index++) {
            myHouses.add(board.getSeed(getPlayerNumber(), index));
            enemyHouses.add(board.getSeed(enemyPlayerNumber, index));
        }
        
        FladniBoard fladniBoard = new FladniBoard(
                myHouses, enemyHouses, board.houses(), 
                board.getScore(getPlayerNumber()), board.getScore(enemyPlayerNumber));
        
        
        int cores = Runtime.getRuntime().availableProcessors();
        
        ArrayList<FladniTester> runners = new ArrayList<>();
        
        int depth = 8;
        for (int index = 0; index < cores/2; index++) {
            FladniTester thread = new FladniTester(fladniBoard, depth, true);
            runners.add(thread);
            depth++;
            thread.start();
        }
        int bestMove = 0;
        
        // we find out all the possible moves
        ArrayList<Integer> possibleMoves = fladniBoard.getNonEmptyHouseIndices(true);
        if (possibleMoves.size() > 0) {
            // set the default as the first available
            bestMove = possibleMoves.get(0);
        }
        
        timeoutMove_$eq(bestMove);
        
        for (FladniTester tester: runners) {
            try {
                tester.join();
                if (tester.bestMove >= 0) {
                    bestMove = tester.bestMove;
                    timeoutMove_$eq(bestMove);
                }
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }
        }
        
        
            
        
        return bestMove;
    }

    private int getPlayerNumber() {
        return playerNumber;
    }

    private void setPlayerNumber(int playerNumber) {
        if (playerNumber == 1 || playerNumber == 2) {
            this.playerNumber = playerNumber;        
        }
    }
    
    
    
    
}
