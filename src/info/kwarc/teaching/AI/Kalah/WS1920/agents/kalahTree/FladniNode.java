/*
 * Kalah Fladni Agent
 * 
 *  Group members:
 *   - Adrian Goess
 *   - Florian Wasmeier
 *   - Nico Dassler <nico.dassler@fau.de>
 */
package info.kwarc.teaching.AI.Kalah.WS1920.agents.kalahTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Represents a simple node in the tree.
 * 
 * A node contains:
 *  - reference to the parent node
 *  - references to child nodes
 * 
 * @author Nico
 */
public class FladniNode {
    
    /**
     * Not all nodes have a parent.
     */
    private final Optional<FladniNode> parent;
    
    /**
     * This is empty if there are no children.
     */
    private final List<FladniNode> children;

    // TODO: Variables for computing Min Max / Alpha Beta Stuff...
    
    
    /**
     * Create a FladniNode with given parent and children.
     * 
     * @param parent 
     * @param children 
     */
    public FladniNode(FladniNode parent, List<FladniNode> children) {
        this.parent = Optional.of(parent);
        this.children = children;
    }
    
    /**
     * Create a FladniNode with given parent.
     * 
     * Note: Children will be empty.
     * 
     * @param parent 
     */
    public FladniNode(FladniNode parent) {
        this.parent = Optional.of(parent);
        this.children = new ArrayList<>();
    }

    /**
     * Create a FladniNode.
     * 
     * Note: Children and parent will be empty.
     */
    public FladniNode() {
        this.parent = Optional.empty();
        this.children = new ArrayList<>();
    }

    /**
     * Create a FladniNode.
     * 
     * Note: parent will be empty.
     * 
     * @param children list of children.
     */
    public FladniNode(List<FladniNode> children) {
        this.parent = Optional.empty();
        this.children = children;
    }
    
    /**
     * Get parent.
     * 
     * Note: Check if it has a parent with hasParent() or
     * by calling .isPresent() on the returned value.
     * 
     * @return Optional<FladniNode> 
     */
    public Optional<FladniNode> getParent() {
        return parent;
    }
    
    
    /**
     * Checks if this node has a parent.
     * 
     * @return true if it has a parent, otherwise false. 
     */
    public boolean hasParent() {
        return parent.isPresent();
    }

    /**
     * Get the children. 
     * 
     * Note: List might be empty. Changes made to returned list
     * will be present in this instance of the node. So take 
     * care of that.
     * 
     * @return the list of children.
     */
    public List<FladniNode> getChildren() {
        return children;
    }
    
    /**
     * Adds a child to the end of children list.
     * 
     * @param child the FladniNode to add as a child. 
     */
    public void addChild(FladniNode child) {
        getChildren().add(this);
    }
    
    /**
     * Return a stream of children.
     * 
     * @return Stream<FladniNode>
     */
    public Stream<FladniNode> getChildStream() {
        return getChildren().stream();
    }
    
    /**
     * Get number of children.
     * 
     * @return int 
     */
    public int getNumberOfChildren() {
        return getChildren().size();
    }
    
    /**
     * Returns the child at the given index if it exists.
     * 
     * @param index
     * 
     * @return Optional<FladniNode>
     */
    public Optional<FladniNode> getChildByIndex(int index) {
        Optional<FladniNode> result = Optional.empty();
        if (index >= 0 && index < getNumberOfChildren()) {
            result = Optional.of(getChildren().get(index));
        }
        return result;
    }
}
