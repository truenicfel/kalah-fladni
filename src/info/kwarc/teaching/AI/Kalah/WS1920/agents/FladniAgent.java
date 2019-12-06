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
        if (playerOne) {
            setPlayerNumber(1);
        } else {
            setPlayerNumber(2);
        }
        
        
        int initSeeds = board.initSeeds();
        
        // lets create an example tree
        FladniNode root = new FladniNode();
        // add 4 children (this makes zero sense because all of the children are
        // exactly the same)
        // special wow
        FladniNode special = new FladniNode(root);
        special.addChild(new FladniNode(special));
        special.addChild(new FladniNode(special));
        root.addChild(special);
        root.addChild(new FladniNode(root));
        root.addChild(new FladniNode(root));
        root.addChild(new FladniNode(root));
        // add a couple of children
        FladniTree fladniTree = new FladniTree(root);
        
        // this tree is so amazing i cant handle it
    }

    @Override
    public int move() {
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
