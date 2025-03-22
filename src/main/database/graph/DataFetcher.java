package main.database.graph;

import java.time.LocalDateTime;

public interface DataFetcher {
    public void fetch(int profile_id);
    public void fetchRange(LocalDateTime start, LocalDateTime stop);
}
