package org.example;

import org.example.cloudwatch.MetricDataResponse;
import org.example.cloudwatch.MetricFetcher;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

public class CloudWatch {
    public static void main(String[] args) {

        MetricFetcher metricFetcher = new MetricFetcher();

        List<MetricDataResponse> responses = new ArrayList<>();

        responses.add(metricFetcher.getCPUUtilization());
        responses.add(metricFetcher.getStatusCheckFailedSystem());
        responses.add(metricFetcher.getStatusCheckFailedInstance());
        responses.add(metricFetcher.getNetworkIn());
        responses.add(metricFetcher.getNetworkOut());
        responses.add(metricFetcher.getNetworkPacketsIn());
        responses.add(metricFetcher.getNetworkPacketsOut());
        responses.add(metricFetcher.getDiskReadOperations());
        responses.add(metricFetcher.getDiskReads());
        responses.add(metricFetcher.getDiskWriteOperations());
        responses.add(metricFetcher.getDiskWrites());

        for (MetricDataResponse response : responses) {
            out.println("id=" + response.getId());
            for (int i = response.getSize() - 1; i >= 0; i--){
                out.println("timestamp=" + response.getTimestamps().get(i));
                out.println("value=" + response.getValue().get(i));
            }
        }
    }
}