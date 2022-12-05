public class Main {
    public static void main(String[] args) {

        ConnectionPool connection = ConnectionPool.getInstance();
        connection.query("SELECT * FROM PEOPLES");
        ConnectionPool.getInstance().query("SELECT * FROM CARS");
        ConnectionPool.getInstance().query("SELECT * FROM SELLERS");
        ConnectionPool.getInstance().query("SELECT * FROM ANIMALS");
        ConnectionPool.getInstance().query("SELECT * FROM BILLS");
        ConnectionPool.getInstance().query("SELECT * FROM ACCOUNT");
        ConnectionPool.getInstance().query("SELECT * FROM OTHERS");
        ConnectionPool.getInstance().query("SELECT * FROM ANOTHER");

    }

}
