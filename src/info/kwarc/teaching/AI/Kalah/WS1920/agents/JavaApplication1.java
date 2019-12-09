
package info.kwarc.teaching.AI.Kalah.WS1920.agents;

import Tester.FladniTester;
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
        FladniBoard board = new FladniBoard(6, 6);
        


//        System.out.println("Number of children: " + root.getChildren().size());
//        for (FladniNode node: root.getChildren()) {
//            FladniBoard gameState = node.getGameState();
//            System.out.println(gameState.toString());
//            System.out.println("-----");
//        }
        
        // FladniBoard intialisierung von Hand:
        
        ArrayList<Integer> myHouses = new ArrayList<>(Arrays.asList(new Integer[] 
            {1, 0, 0, 0, 0, 3}
        ));
        
        ArrayList<Integer> enemyHouses = new ArrayList<>(Arrays.asList(new Integer[] 
            {1, 2, 3, 4, 10, 6}
        )); 
        
        int myStore = 0;
        int enemyStore = 0;
        int numberHouses = 6; // important! must be the same number of elements as in
        // {} above
        
        //FladniBoard board = new FladniBoard(myHouses, enemyHouses, numberHouses, myStore, enemyStore);

                        // lets create an example tree
        FladniNode root = new FladniNode(board, true);
        FladniTree fladniTree = new FladniTree(root);
        
        root.doIds(3, 0);
        
        Thread thread = new Thread(new FladniTester());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
        }
        
        System.out.println("hello im the main thread");

//        System.out.println("Initial board: ");
//        System.out.println(board.toString());
//        System.out.println("My possible moves: ");
//        ArrayList<Map.Entry<Boolean, FladniBoard>> makeAllPossibleMoves = board.makeAllPossibleMoves(true);
//        makeAllPossibleMoves.stream().forEachOrdered((currentBoard) -> {
//            System.out.println("Board: ");
//            System.out.println(currentBoard.getValue().toString());
//            System.out.println("Move again: " + currentBoard.getKey());
//        });
//        System.out.println("Enemy possible moves: ");
//        makeAllPossibleMoves = board.makeAllPossibleMoves(false);
//        makeAllPossibleMoves.stream().forEachOrdered((currentBoard) -> {
//            System.out.println("Board: ");
//            System.out.println(currentBoard.getValue().toString());
//            System.out.println("Move again: " + currentBoard.getKey());
//        });

    }
    
}
