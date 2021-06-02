package ar.edu.itba.paw.webapp.controller.common;

public class Pager {
    private static final int PAGE_SIZE = 10;

    private final int collectionSize;
    private final int page;
    private final int offset;
    private final int limit;

    public Pager(int collectionSize, int currentPage){
        this.collectionSize = collectionSize;
        this.page = currentPage >= 0 ? currentPage : getLastPage() - (currentPage+1);
        this.offset = this.page * PAGE_SIZE;
        this.limit = PAGE_SIZE;
    }

    public int getCollectionSize() {
        return collectionSize;
    }

    public int getCurrPage() {
        return page;
    }

    public int getLastPage() {
        return ((int) Math.ceil(((double) collectionSize) / PAGE_SIZE) ) - 1;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

}
