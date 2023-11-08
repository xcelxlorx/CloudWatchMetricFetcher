package org.example.cloudwatch;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class S3MetricFetcher {

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");
    private static final Region REGION = Region.AP_NORTHEAST_2;
    private static final String NAMESPACE = "AWS/S3";
    private static final String STAT = "Average";
    private static final int PERIOD = 86400;
    private static final int MAX_DATA_POINTS = 100;

    private String bucketName = "";

    private final Instant start = LocalDate.now(ZONE_ID).minusWeeks(2).atStartOfDay(ZONE_ID).toInstant();
    private final Instant end = LocalDate.now(ZONE_ID).atStartOfDay(ZONE_ID).toInstant();

    public MetricDataResponse getS3BucketSize(){
        return getMetricDataResponse("BucketSizeBytes", "StandardStorage", "s3BucketSizeQuery");
    }

    public MetricDataResponse getS3NumberOfObjects(){
        return getMetricDataResponse("NumberOfObjects", "AllStorageTypes", "s3NumberOfObjectsQuery");
    }

    private MetricDataResponse getMetricDataResponse(String metricName, String storageType, String dataQueryId) {
        AwsCredentialsProvider credentialsProvider = getCredentialsProvider();

        CloudWatchClient cloudWatchClient = CloudWatchClient.builder()
                .region(REGION)
                .credentialsProvider(credentialsProvider)
                .build();

        try{
            Dimension dimension1 = Dimension.builder()
                    .name("BucketName")
                    .value(bucketName)
                    .build();

            Dimension dimension2 = Dimension.builder()
                    .name("StorageType")
                    .value(storageType)
                    .build();

            Metric metric = Metric.builder()
                    .namespace(NAMESPACE)
                    .metricName(metricName)
                    .dimensions(
                            dimension1,
                            dimension2
                    )
                    .build();

            MetricStat metricStat = MetricStat.builder()
                    .stat(STAT)
                    .period(PERIOD)
                    .metric(metric)
                    .build();

            MetricDataQuery dataQuery = MetricDataQuery.builder()
                    .metricStat(metricStat)
                    .id(dataQueryId)
                    .returnData(true)
                    .build();

            List<MetricDataQuery> queries = new ArrayList<>();
            queries.add(dataQuery);

            GetMetricDataRequest request = GetMetricDataRequest.builder()
                    .startTime(start)
                    .endTime(end)
                    .metricDataQueries(queries)
                    .maxDatapoints(MAX_DATA_POINTS)
                    .build();

            GetMetricDataResponse response = cloudWatchClient.getMetricData(request);
            List<Instant> timestamps = new ArrayList<>();
            List<Double> values = new ArrayList<>();
            String id = "";

            for (MetricDataResult result : response.metricDataResults()) {
                id = result.id();
                timestamps = result.timestamps();
                values = result.values();
            }

            return new MetricDataResponse(id, timestamps, values);

        }catch(CloudWatchException e){
            System.exit(1);
            return null;
        }finally{
            cloudWatchClient.close();
        }
    }

    private static AwsCredentialsProvider getCredentialsProvider() {
        String accessKey = "";
        String secretKey = "";
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return StaticCredentialsProvider.create(credentials);
    }
}
