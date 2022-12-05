import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectionPool {
    private static final int POOL_SIZE = 4;
    private static final ConnectionPool INSTANCE = new ConnectionPool();

    private List<Connection> connectionPool = new ArrayList<>();
    private Queue<QueueConnection> queue = new ArrayDeque<>();

    private boolean initialized = false;
    private ConnectionPool() {
        this.connectionPool.addAll(IntStream.range(0, POOL_SIZE)
                .mapToObj(Connection::new)
                .collect(Collectors.toList())
        );
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    private static Connection getConnection() {
        Optional<Connection> freeConnection = getInstance().connectionPool.stream()
                .filter(Connection::isFree)
                .findFirst();
        if (freeConnection.isPresent()) {
            return freeConnection.get();
        }
        return null;
    }

    public void query(String query) {
        Thread connectionThread = new Thread(() -> {
            getInstance().query(query, true);
        });
        connectionThread.start();
    }

    private void query(String query, boolean thread) {
        Connection connection = getConnection();
        if (connection != null && Status.STAND_BY.equals(connection.getStatus())) {
            connection.connect();
            connection.query(query);
            leaveConnection(connection);
        } else {
            getInstance().queue.offer(new QueueConnection(query));
            System.out.println("query waiting by connections. Queue size " + getInstance().queue.size());
        }
    }

    public static long countFreeConnections() {
        return getInstance().connectionPool.stream().filter(Connection::isFree).count();
    }

    public static long countQueueSize() {
        return getInstance().queue.size();
    }


    public void leaveConnection(Connection connection) {
        connection.leaveConnection();
        if (getInstance().queue.peek() != null) {
            QueueConnection queue = getInstance().queue.poll();
            System.out.println("Execute query in queue. timelapse: " + queue.getTimelapse() + "ms");
            connection.query(queue.getQuery());
        }
    }

}
