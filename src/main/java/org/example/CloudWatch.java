package org.example;

import org.example.cloudwatch.MetricDataResponse;
import org.example.cloudwatch.Ec2MetricFetcher;
import org.example.cloudwatch.S3MetricFetcher;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

public class CloudWatch {
    public static void main(String[] args) {

        Ec2MetricFetcher ec2MetricFetcher = new Ec2MetricFetcher();
        S3MetricFetcher s3MetricFetcher = new S3MetricFetcher();

        List<MetricDataResponse> ec2Responses = new ArrayList<>();
        List<MetricDataResponse> s3Responses = new ArrayList<>();

        ec2Responses.add(ec2MetricFetcher.getCPUUtilization());
        ec2Responses.add(ec2MetricFetcher.getStatusCheckFailedSystem());
        ec2Responses.add(ec2MetricFetcher.getStatusCheckFailedInstance());
        ec2Responses.add(ec2MetricFetcher.getNetworkIn());
        ec2Responses.add(ec2MetricFetcher.getNetworkOut());
        ec2Responses.add(ec2MetricFetcher.getNetworkPacketsIn());
        ec2Responses.add(ec2MetricFetcher.getNetworkPacketsOut());
        ec2Responses.add(ec2MetricFetcher.getDiskReadOperations());
        ec2Responses.add(ec2MetricFetcher.getDiskReads());
        ec2Responses.add(ec2MetricFetcher.getDiskWriteOperations());
        ec2Responses.add(ec2MetricFetcher.getDiskWrites());

        s3Responses.add(s3MetricFetcher.getS3BucketSize());
        s3Responses.add(s3MetricFetcher.getS3NumberOfObjects());

        for (MetricDataResponse response : ec2Responses) {
            out.println("id=" + response.getId());
            for (int i = response.getSize() - 1; i >= 0; i--){
                out.println("timestamp=" + response.getTimestamps().get(i));
                out.println("value=" + response.getValue().get(i));
            }
        }

        for (MetricDataResponse response : s3Responses) {
            out.println("id=" + response.getId());
            for (int i = response.getSize() - 1; i >= 0; i--){
                out.println("timestamp=" + response.getTimestamps().get(i));
                out.println("value=" + response.getValue().get(i));
            }
        }
    }
}