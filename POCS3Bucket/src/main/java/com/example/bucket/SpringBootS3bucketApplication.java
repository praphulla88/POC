package com.example.bucket;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.example.bucket.service.AWSS3Service;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.FileUtils;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;

import java.io.File;
import java.util.concurrent.Executors;

public class SpringBootS3bucketApplication {

	public static void main(String[] args) throws Exception {
		String existingBucketName = "reception-praphulla";
		String keyName = "test.txt";
		String filePath = "C:\\Users\\praphulla88\\dummy.txt";
		String bucketName = "workzone-praphulla";

		AWSCredentials credentials =  new BasicAWSCredentials(
				"AKIA5F2SIRX5MUWBVX4K",
				"5eUJwA3iEQUAP8uyhM/JD3sKhzhVh4UUXtNygzlK"
		);
		AmazonS3 amazonS3  =  AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.AP_SOUTH_1)
				.build();

		AWSS3Service awsService = new AWSS3Service(amazonS3);

		//creating a bucket
		if(awsService.doesBucketExist(bucketName)) {
			System.out.println("Bucket name is not available."
					+ " Try again with a different Bucket name.");
			//return;
		}
		//awsService.createBucket(bucketName);

		//list all the buckets
		for(Bucket s : awsService.listBuckets() ) {
			System.out.println(s.getName());
		}



		//uploading object
		awsService.putObject(
				existingBucketName,
				"Document/test.txt",
				new File("C:\\Users\\praphulla88\\dummy1.txt")
		);

		//listing objects
		ObjectListing objectListing = awsService.listObjects(existingBucketName);
		for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
			System.out.println(os.getKey());
		}

		//downloading an object
		S3Object s3object = awsService.getObject(existingBucketName, "Document/test.txt");
		S3ObjectInputStream inputStream = s3object.getObjectContent();
		FileUtils.copyInputStreamToFile(inputStream, new File("C:\\Users\\praphulla88\\testdownload.txt"));

		//copying an object
		awsService.copyObject(
				existingBucketName,
				"test.txt",
				bucketName,
				"test.txt"
		);

		//deleting an object
		awsService.deleteObject(bucketName, "Document/hello.txt");

		//deleting multiple objects
		String objkeyArr[] = {
				"Document/hello2.txt",
				"Document/picture.png"
		};

		/*DeleteObjectsRequest delObjReq = new DeleteObjectsRequest("baeldung-bucket")
				.withKeys(objkeyArr);
		awsService.deleteObjects(delObjReq);*/

		//deleting bucket
		//awsService.deleteBucket(bucketName);


		/*int maxUploadThreads = 5;

		TransferManager tm = TransferManagerBuilder
				.standard()
				.withS3Client(amazonS3)
				.withMultipartUploadThreshold((long) (5 * 1024 * 1024))
				.withExecutorFactory(() -> Executors.newFixedThreadPool(maxUploadThreads))
				.build();

		ProgressListener progressListener =
				progressEvent -> System.out.println("Transferred bytes: " + progressEvent.getBytesTransferred());

		PutObjectRequest request = new PutObjectRequest(existingBucketName, keyName, new File(filePath));

		request.setGeneralProgressListener(progressListener);

		Upload upload = tm.upload(request);

		try {
			upload.waitForCompletion();
			System.out.println("Upload complete.");
		} catch (AmazonClientException e) {
			System.out.println("Error occurred while uploading file");
			e.printStackTrace();
		}*/
	}
}