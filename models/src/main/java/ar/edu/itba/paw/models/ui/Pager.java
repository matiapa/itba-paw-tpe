package ar.edu.itba.paw.models.ui;

public class Pager {
    private final int size;
    private final int page;
    private final int offset;
    private final int limit;

    public Pager(int size, int page, int offset, int limit){
        this.size = size;
        this.page = page;
        this.offset = offset;
        this.limit = limit;
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
