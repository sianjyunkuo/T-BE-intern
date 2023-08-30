public class Main {
    public static void main(String[] args) {
        // Create a sample tree
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(1);
        root.left.left = new Node(7);
        root.left.right = new Node(5);
        root.right.right = new Node(28);
        root.left.left.left = new Node(4);

        // Basic Serializer Test
        // System.out.println("Basic Serializer Test");
        TreeSerializer basicSerializer = new BasicTreeSerializer();
        testTreeSerialization(basicSerializer, root);

        // Cyclic Serializer Test
        // System.out.println("Cyclic Serializer Test");
        root.right.left = root;  // Introduce a cycle
        TreeSerializer cyclicSerializer = new CyclicTreeSerializer();
        try {
            testTreeSerialization(cyclicSerializer, root);
        } catch (RuntimeException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }

    public static void testTreeSerialization(TreeSerializer serializer, Node root) {
        String serializedStr = serializer.serialize(root);
        // System.out.println("Serialized string: " + serializedStr);

        Node deserializedRoot = serializer.deserialize(serializedStr);

        String serializedAgain = serializer.serialize(deserializedRoot);
        // System.out.println("Serialized again: " + serializedAgain);

        if (serializedStr.equals(serializedAgain)) {
            System.out.println("The test passed!");
        } else {
            System.out.println("The test failed!");
        }
    }
}
