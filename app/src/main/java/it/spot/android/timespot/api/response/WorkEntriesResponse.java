package it.spot.android.timespot.api.response;

import java.util.List;

import it.spot.android.timespot.api.domain.WorkEntry;

/**
 * @author a.rinaldi
 */
public class WorkEntriesResponse {

    private List<WorkEntry> items;
    private int total;
    private int pages;
    private int currentPage;
    private int itemsPerPage;
    private long amountTime;

    public WorkEntriesResponse() {
        super();
    }

    public int getTotal() {
        return total;
    }

    public WorkEntriesResponse setTotal(int total) {
        this.total = total;
        return this;
    }

    public int getPages() {
        return pages;
    }

    public WorkEntriesResponse setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public WorkEntriesResponse setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public WorkEntriesResponse setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
        return this;
    }

    public long getAmountTime() {
        return amountTime;
    }

    public WorkEntriesResponse setAmountTime(long amountTime) {
        this.amountTime = amountTime;
        return this;
    }

    public List<WorkEntry> getItems() {
        return items;
    }

    public WorkEntriesResponse setItems(List<WorkEntry> items) {
        this.items = items;
        return this;
    }
}
