interface TreeSerializer {
    String serialize(Node root);
    Node deserialize(String str);
}

class BasicTreeSerializer implements TreeSerializer {
    private static final String NULL_SYMBOL = "null";
    private static final String SEPARATOR = ",";

    @Override
    public String serialize(Node root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    private void serializeHelper(Node root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULL_SYMBOL).append(SEPARATOR);
            return;
        }
        sb.append(root.num).append(SEPARATOR);
        serializeHelper(root.left, sb);
        serializeHelper(root.right, sb);
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
