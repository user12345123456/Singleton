public class Connection {
    private int pid;
    private long time;
    private Status status;

    protected Connection(int pid) {
        this.pid = pid;
        this.time = System.currentTimeMillis();
        this.status = Status.STAND_BY;
    }

    public void connect() {
        status = Status.CONNECTED;
    }

    protected void leaveConnection() {
        status = Status.STAND_BY;
    }

    protected void close() {
        status = Status.CLOSED;
    }

    public int getPid() {
        return pid;
    }

    public String getTime() {
        long timeLapse = System.currentTimeMillis() - this.time;
        return "Alive " + timeLapse + "ms";
    }

    protected void query(String query) {
        try {
            System.out.println("PID " + getPid() + " executing  " + query);
            Thread.sleep((long) (Math.random() * 8000));
            System.out.println("PID " + getPid() + " query completed. Connection live  " + getTime());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public Status getStatus() {
        return status;
    }

    public boolean isFree() {
        return Status.STAND_BY.equals(status);
    }
}



