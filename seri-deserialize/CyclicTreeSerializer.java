import java.util.HashSet;

interface TreeSerializer {
    String serialize(Node root);
    Node deserialize(String str);
}

public class CyclicTreeSerializer implements TreeSerializer {
    private static final String NULL_SYMBOL = "null";
    private static final String SEPARATOR = ",";

    @Override
    public String serialize(Node root) {
        StringBuilder sb = new StringBuilder();
        HashSet<Node> visited = new HashSet<>();
        serializeHelper(root, sb, visited);
        return sb.toString();
    }

    private void serializeHelper(Node root, StringBuilder sb, HashSet<Node> visited) {
        if (root == null) {
            sb.append(NULL_SYMBOL).append(SEPARATOR);
            return;
        }
        if (visited.contains(root)) {
            throw new RuntimeException("Cyclic connection detected");
        }
        visited.add(root);

        sb.append(root.num).append(SEPARATOR);
        serializeHelper(root.left, sb, visited);
        serializeHelper(root.right, sb, visited);
    }

    @Override
    public Node deserialize(String str) {
        String[] tokens = str.split(SEPARATOR);
        int[] index = {0};
        return deserializeHelper(tokens, index);
    }

    private Node deserializeHelper(String[] tokens, int[] index) {
        if (tokens[index[0]].equals(NULL_SYMBOL)) {
            index[0]++;
            return null;
        }
        Node root = new Node(Integer.parseInt(tokens[index[0]++]));
        root.left = deserializeHelper(tokens, index);
        root.right = deserializeHelper(tokens, index);
        return root;
    }
}
