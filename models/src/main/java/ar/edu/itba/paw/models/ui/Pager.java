package ar.edu.itba.paw.models.ui;

public class Pager {
    private static final int LIMIT = 5;
    private final int size;
    private final int page;
    private final int offset;
    private final int limit;

    public Pager(int size, int page){
        this.size = size;
        this.page = page;
        this.offset = page * LIMIT;
        this.limit = LIMIT;
    }

    public int getSize() {
        return size;
    }

    public int getPage() {
        return page;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}
