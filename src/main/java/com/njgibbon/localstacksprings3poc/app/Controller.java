package com.njgibbon.localstacksprings3poc;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.GetObjectRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/")
public class Controller 
{

   @ResponseBody
   @RequestMapping(value = "/", method = GET, produces = "application/json")
   public String main() 
   {
       return "localstack-spring-s3-poc";
   }

   @ResponseBody
   @RequestMapping(value = "/hello", method = GET, produces = "application/json")
   public String hello() 
   {
       return "Hello World!";
   }

   @ResponseBody 
   @RequestMapping(value = "/listBuckets", method = GET, produces = "application/json")
   public String listBuckets() 
   {
       return stringifyListBuckets();
   }

   @ResponseBody 
   @RequestMapping(value = "/listObjects", method = GET, produces = "application/json", params="bucketName")
   public String listObjects(@RequestParam("bucketName") String bucketName) 
   {
       return stringifyListObjects(bucketName);
   }

   @ResponseBody 
   @RequestMapping(value = "/readObject", method = GET, produces = "application/json", params = { "bucketName", "objectName" })
   public String readObject(@RequestParam("bucketName") String bucketName, @RequestParam("objectName") String objectName) 
   {
       return stringifyContentsOfBucketObject(bucketName, objectName);
   }


   public String stringifyContentsOfBucketObject(String bucketName, String objectName)
   {
       String returnString = "";
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4572/","eu-west-1"))
        .withPathStyleAccessEnabled(true)
        .build();

        try 
        {
            S3Object o = s3.getObject(new GetObjectRequest(bucketName, objectName));
            returnString = readTextInputStream(o.getObjectContent());
        }
        catch (AmazonServiceException e) 
        {
            returnString = "oops";
            System.err.println(e.getErrorMessage());
        } 
        catch (FileNotFoundException e) 
        {
            returnString = "oops";
            System.err.println(e.getMessage());
        } 
        catch (IOException e) 
        {
            returnString = "oops";
            System.err.println(e.getMessage());
        }
        return returnString; 
   }

   public String stringifyListBuckets()
   {
        String returnString="";

        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4572/","eu-west-1"))
        .withPathStyleAccessEnabled(true)
        .build();

        List<Bucket> buckets = s3.listBuckets();
        System.out.println("Your Amazon S3 buckets are:");
        for (Bucket b : buckets) {
            System.out.println("* " + b.getName());
            returnString = returnString + b.getName() + " ";
        }

        return returnString;
   }

   public String stringifyListObjects(String bucketName)
   {
        String returnString="";

        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4572/","eu-west-1"))
        .withPathStyleAccessEnabled(true)
        .build();

        ListObjectsV2Result result = s3.listObjectsV2(bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        for (S3ObjectSummary os: objects) {
            System.out.println("* " + os.getKey());
            returnString = returnString + os.getKey() + " ";
        }

        return returnString;
   }


    private String readTextInputStream(InputStream input) throws IOException 
    {
        String returnString="";
        //Read the text input stream one line at a time and display each line.
        //And concat
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            returnString = returnString + line + " ";
        }
        System.out.println();
        return returnString;
    }
}
