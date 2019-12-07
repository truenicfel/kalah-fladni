
package info.kwarc.teaching.AI.Kalah.WS1920.agents;

import info.kwarc.teaching.AI.Kalah.WS1920.agents.board.FladniBoard;
import info.kwarc.teaching.AI.Kalah.WS1920.agents.kalahTree.FladniNode;
import info.kwarc.teaching.AI.Kalah.WS1920.agents.kalahTree.FladniTree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

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
        
                // lets create an example tree
        FladniNode root = new FladniNode(board, false);
        FladniTree fladniTree = new FladniTree(root);
        
        root.doIds(2, 0);

//        System.out.println("Number of children: " + root.getChildren().size());
//        for (FladniNode node: root.getChildren()) {
//            FladniBoard gameState = node.getGameState();
//            System.out.println(gameState.toString());
//            System.out.println("-----");
//        }
        
        // FladniBoard intialisierung von Hand:
        
        ArrayList<Integer> myHouses = new ArrayList<>(Arrays.asList(new Integer[] 
            {1, 0, 3, 4, 5, 6}
        ));
        
        ArrayList<Integer> enemyHouses = new ArrayList<>(Arrays.asList(new Integer[] 
            {1, 2, 3, 4, 10, 6}
        )); 
        
        int myStore = 0;
        int enemyStore = 0;
        int numberHouses = 6; // important! must be the same number of elements as in
        // {} above
        
        //FladniBoard board = new FladniBoard(myHouses, enemyHouses, numberHouses, myStore, enemyStore);


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
