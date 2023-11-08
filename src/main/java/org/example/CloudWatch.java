package org.example;

import org.example.cloudwatch.MetricDataResponse;
import org.example.cloudwatch.Ec2MetricFetcher;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

public class CloudWatch {
    public static void main(String[] args) {

        Ec2MetricFetcher ec2MetricFetcher = new Ec2MetricFetcher();

        List<MetricDataResponse> responses = new ArrayList<>();

        responses.add(ec2MetricFetcher.getCPUUtilization());
        responses.add(ec2MetricFetcher.getStatusCheckFailedSystem());
        responses.add(ec2MetricFetcher.getStatusCheckFailedInstance());
        responses.add(ec2MetricFetcher.getNetworkIn());
        responses.add(ec2MetricFetcher.getNetworkOut());
        responses.add(ec2MetricFetcher.getNetworkPacketsIn());
        responses.add(ec2MetricFetcher.getNetworkPacketsOut());
        responses.add(ec2MetricFetcher.getDiskReadOperations());
        responses.add(ec2MetricFetcher.getDiskReads());
        responses.add(ec2MetricFetcher.getDiskWriteOperations());
        responses.add(ec2MetricFetcher.getDiskWrites());

        for (MetricDataResponse response : responses) {
            out.println("id=" + response.getId());
            for (int i = response.getSize() - 1; i >= 0; i--){
                out.println("timestamp=" + response.getTimestamps().get(i));
                out.println("value=" + response.getValue().get(i));
            }
        }
    }
}