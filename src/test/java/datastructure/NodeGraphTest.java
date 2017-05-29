package datastructure;

/**
 * Created by 101010.
 */
class NodeGraphTest {
    /**
     * The NodeGraph used to test.
     */
    private NodeGraph nodeGraph = null;

    /**
     * Before each test we set the nodeGraph to a new NodeGraph.
     */
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        nodeGraph = new NodeGraph();
    }

    /**
     * After each test we set the nodeGraph back to null.
     */
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        nodeGraph = null;
    }
}
