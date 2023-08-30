// Suggest changes that should be done in order to support any data type (as opposed to only an int data type)

public class Node<T> {
    Node<T> left;
    Node<T> right;
    T data;

    public Node(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public Node(T data, Node<T> left, Node<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }
}

public interface TreeSerializer<T> {
    String serialize(Node<T> root);
    Node<T> deserialize(String str);
}
