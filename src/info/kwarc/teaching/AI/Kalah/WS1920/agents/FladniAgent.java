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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wasme
 */
public class FladniAgent extends Agent { 

    private final String name;

    private final List<String> nameList;
    
    public FladniAgent(String name) {
        this.name = name;
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
        // lets create an example tree:
        
        
    }

    @Override
    public int move() {
        return 0;
    }
    
}
