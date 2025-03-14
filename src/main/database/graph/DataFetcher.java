package main.database.graph;

import java.time.LocalDateTime;

public interface DataFetcher {
    public void fetch();
    public void fetchRange(LocalDateTime start, LocalDateTime stop);
}
