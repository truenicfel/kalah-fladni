/*
 * Kalah Fladni Agent
 *
 * Group members:
 *  - Adrian Goess
 *  - Florian Wasmeier
 *  - Nico Dassler <nico.dassler@fau.de>
 */
package info.kwarc.teaching.AI.Kalah.WS1920.agents;

import info.kwarc.teaching.AI.Kalah.Agents.Agent;
import info.kwarc.teaching.AI.Kalah.Board;
import info.kwarc.teaching.AI.Kalah.WS1920.agents.board.FladniBoard;
import info.kwarc.teaching.AI.Kalah.WS1920.agents.kalahTree.FladniNode;
import info.kwarc.teaching.AI.Kalah.WS1920.agents.kalahTree.FladniTree;
import java.util.ArrayList;
import java.util.List;

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
        
        FladniBoard fladniBoard = new FladniBoard(board.houses(), board.initSeeds());
        
        // lets create the game tree
        FladniNode root = new FladniNode(fladniBoard, playerOne);
        FladniTree fladniTree = new FladniTree(root);
        
        while (!Thread.interrupted()) {
            fladniTree.startIdsAtRoot(playerNumber);
        }
        // assume variable depth for agent
        // assume depth = 3
        // set index = depth + 1
        // while (!Thread.interrupted())
        //      doids(index)
        //      wenn zeit fuer doids < 5sek
        //          depth = index
        //      index ++
        
        
        
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
        
        // lets create the game tree
        FladniNode root = new FladniNode(fladniBoard, true);
        FladniTree fladniTree = new FladniTree(root);
        // do ids
        int limit = 0;
        int step = 3; // step = depth
        while (!Thread.interrupted()) {
            
            limit += step;
            fladniTree.startIdsAtRoot(limit);
            
            // find best move and write to
            root.getValue();
            root.getChildren();
            //super.timeoutMove = ...
        }
        
        
        return 0;
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
