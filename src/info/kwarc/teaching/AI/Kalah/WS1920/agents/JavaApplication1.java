
package info.kwarc.teaching.AI.Kalah.WS1920.agents;

import Tester.FladniTester;
import info.kwarc.teaching.AI.Kalah.Agents.HumanPlayer;
import info.kwarc.teaching.AI.Kalah.Board;
import info.kwarc.teaching.AI.Kalah.Game;
import info.kwarc.teaching.AI.Kalah.Interfaces.Fancy;
import info.kwarc.teaching.AI.Kalah.WS1920.agents.board.FladniBoard;
import info.kwarc.teaching.AI.Kalah.WS1920.agents.kalahTree.FladniNode;
import info.kwarc.teaching.AI.Kalah.WS1920.agents.kalahTree.FladniTree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wasme
 */
public class JavaApplication1 {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FladniBoard board = new FladniBoard(4, 6);
        

//        System.out.println("Number of children: " + root.getChildren().size());
//        for (FladniNode node: root.getChildren()) {
//            FladniBoard gameState = node.getGameState();
//            System.out.println(gameState.toString());
//            System.out.println("-----");
//        }
        
        // FladniBoard intialisierung von Hand:
        
        ArrayList<Integer> myHouses = new ArrayList<>(Arrays.asList(new Integer[] 
            {0,0,2,0,0,6}
        ));
        
        ArrayList<Integer> enemyHouses = new ArrayList<>(Arrays.asList(new Integer[] 
            {0,0,2,0,1,1}
        )); 
        
        int myStore = 14;
        int enemyStore = 22;
        int numberHouses = 6; // important! must be the same number of elements as in

    }
    
}
