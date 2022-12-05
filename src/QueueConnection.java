class QueueConnection {
    private long timelapse;
    private String query;

    protected QueueConnection(String query) {
        this.query = query;
        this.timelapse = System.currentTimeMillis();
    }

    public String getQuery() {
        return query;
    }

    public long getTimelapse() {
        return System.currentTimeMillis() - timelapse;
    }
}

