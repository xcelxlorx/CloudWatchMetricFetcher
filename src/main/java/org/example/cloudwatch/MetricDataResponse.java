package org.example.cloudwatch;

import java.time.Instant;
import java.util.List;

public class MetricDataResponse {
    private String id;
    private List<Instant> timestamps;
    private List<Double> value;
    private int size;

    public MetricDataResponse(String id, List<Instant> timestamps, List<Double> value) {
        this.id = id;
        this.timestamps = timestamps;
        this.value = value;
        this.size = timestamps.size();
    }

    public String getId() {
        return id;
    }

    public List<Instant> getTimestamps() {
        return timestamps;
    }

    public List<Double> getValue() {
        return value;
    }

    public int getSize() {
        return size;
    }
}
