from typing import Optional

class Node:
    """
    Class representing a single node of a binary tree containing integer values.
    ...
    Attributes
    ----------
    value: int
        Value stored in the node.
    parent: Node, optional
        Parent of the current node. Can be None.
    left: Node, optional
        Left child of the current node. Can be None.
    right: Node, optional
        Right child of the current node. Can be None.
    """
    def __init__(self, value) -> None:
        self.value = value
        self.parent = self.right = self.left = None
    
    def set_left_child(self, node) -> None:
        """
        Set the the left child of self to the given node.
        Sets the node's parent to self (if it is not None).
        Args:
            node (Node, optional): the node to set as the child.
        """
        self.left = node
        if node is not None:
            node.parent = self

    def set_right_child(self, node) -> None:
        """
        Set the the right child of self to the given node.
        Sets the node's parent to self (if it is not None).
        Args:
            node (Node, optional): the node to set as the child.
        """
        self.right = node
        if node is not None:
            node.parent = self
    
    def left_rotate(self, tree) -> None:
        """
        Left rotate the tree around given node.
        Args:
            tree (BinaryTree): The tree to rotate. 
        """
        right = self.right
        if not right:
            return
        parent = self.parent
        if parent:
            if parent.left == self:
                parent.set_left_child(right)
            if parent.right == self:
                parent.set_right_child(right)
        else:
            right.parent = None
            tree.root = right
        temp = right.left
        right.set_left_child(self)
        self.set_right_child(temp)
    
    def right_rotate(self, tree) -> None:
        """
        Right rotate the tree around given node.
        Args:
            tree (BinaryTree): The tree to rotate. 
        """
        left = self.left
        if not left:
            return
        parent = self.parent
        if parent:
            if parent.left == self:
                parent.set_left_child(left)
            if parent.right == self:
                parent.set_right_child(left)
        else:
            left.parent = None
            tree.root = left
        temp = left.right
        left.set_right_child(self)
        self.set_left_child(temp)

    
    def node_count(self) -> int:
        """
        Recursively count the number of child nodes.
        Returns:
            int: The number of nodes.
        """
        return 1 + (self.right.node_count() if self.right else 0) + (self.left.node_count() if self.left else 0)



class BinaryTree:
    """
    Class repreesenting a binary tree, consisting of Nodes.
    ...
    Attributes
    ----------
    root : Node, optional
        the root node of the BinaryTree of type Node (or None)
    """
    def __init__(self, root: Node) -> None:
        self.root = root

    def left_backbone(self) -> None:
        curr = self.root
        while curr is not None:
            curr_right = curr.right
            if curr_right is not None:
                curr.left_rotate(self)
                curr = curr_right
            else:
                curr = curr.left
    
    def set_root(self, node: Optional[Node]) -> None:
        """
        Set the root of the tree to the provided node and set the node's parent to None (if the node is not None).
        Args:
            node (Node, optional): The Node object to set as the root (whose parent is set to None)
        """
        self.root = node
        if self.root is not None:
            self.root.parent = None
    
    def insert(self, value: int) -> bool:
        """
        Insert the given integer value into the tree at the right position.
        Args:
            value (int): The value to insert
        Returns:
            bool: True if the element was not already in the tree (insertion was successful), otherwise False.
        """
        node = self.root
        if node is None:
            self.set_root(Node(value))
            return True
        while node is not None:
            if value < node.value:
                if node.left is None:
                    node.set_left_child(Node(value))
                    break
                else:
                    node = node.left
            elif value > node.value:
                if node.right is None:
                    node.set_right_child(Node(value))
                    break
                else:
                    node = node.right
            else:
                return False
        return True

    def node_count(self) -> int:
        """
        Count the number of nodes in the tree. Return 0 if root is None.
        Returns:
            int: Number of nodes in the tree.
        """
        return self.root.node_count() if self.root else 0

    def __repr__(self) -> str:
        """
        Get the string representation of the Node.
        Returns:
            str: A string representation which can create the Node object.
        """
        return f"Node({self.value})"

        

from math import floor, ceil, log, pow

def DSW_rotates(tree,lcount):
    node,A=tree.root,None
    while lcount>0:
        A1=node.left
        node.right_rotate(tree)
        lcount=lcount-1
        A=A1
        if node != None:
            node=A.left

def DSW(tree: BinaryTree) -> None:
    """
    Balances the binary tree using right backbone

    Args:
        tree (BinaryTree): The tree o balanse.
    """
    #TODO: Implement the DWS tree balansing
    if tree.root is None: return
    tree.left_backbone()
    n=tree.node_count()
    h=ceil(log(n+1,2))
    i=pow(2,h-1)-1
    DSW_rotates(tree,n-i)
    while i>1:
        i=floor(i/2)
        DSW_rotates(tree,i)

    #pass

root = Node(19)
root.set_left_child(Node(7))
root.left.set_right_child(Node(9))
root.set_right_child(Node(48))
root.right.set_right_child(Node(66))
root.right.right.set_left_child(Node(59))

tree = BinaryTree(root)

DSW(tree)

root = tree.root

assert root.value == 19
assert root.left.value == 7
assert root.left.right.value == 9
assert root.right.value == 59
assert root.right.left.value == 48
assert root.right.right.value == 66