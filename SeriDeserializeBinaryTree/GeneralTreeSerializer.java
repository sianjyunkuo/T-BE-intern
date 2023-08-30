public class Node<T> {
    Node<T> left;
    Node<T> right;
    T data;

    public Node(T data) {
        this.data = data;
    }
}

public interface TreeSerializer<T> {
    String serialize(Node<T> root);
    Node<T> deserialize(String str);
}
