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
public class Controller {

   @ResponseBody
   @RequestMapping(value = "/", method = GET, produces = "application/json")
   public String main() {
       return "localstack-spring-s3-po";
   }

   @ResponseBody
   @RequestMapping(value = "/hello", method = GET, produces = "application/json")
   public String hello() {
       return "Hello World!";
   }

   @ResponseBody 
   @RequestMapping(value = "/listBuckets", method = GET, produces = "application/json", params="foo")
   public String listBuckets(@RequestParam("foo") String foo) {
       listTheBuck(foo);
       return "{\"list\":\"buckets\"}";
   }

   @ResponseBody 
   @RequestMapping(value = "/listObjects", method = GET, produces = "application/json", params="bucketName")
   public String listObjects(@RequestParam("bucketName") String bucketName) {
       listTheBuck(bucketName);
       return "{\"list\":\"buckets\"}";
   }

   @ResponseBody 
   @RequestMapping(value = "/readObject", method = GET, produces = "application/json", params = { "bucketName", "objectName" })
   public String readObject(@RequestParam("bucketName") String bucketName, @RequestParam("objectName") String objectName) 
   {
       listTheBuck(bucketName);
       //System.out.println(objectName);
       return "{\"list\":\"buckets\"}";
   }

   public String stringifyContentsOfBucketObject(String bucketName, String objectName)
   {
       String returnString = "";
       try {
        S3Object o = s3.getObject(new GetObjectRequest(foo, "hello.txt"));
    //S3ObjectInputStream s3is = o.getObjectContent();
    displayTextInputStream(o.getObjectContent());
    
    //FileOutputStream fos = new FileOutputStream(new File(key_name));
    //byte[] read_buf = new byte[1024];
    //int read_len = 0;
    //while ((read_len = s3is.read(read_buf)) > 0) {
        //sb.append(read_len);
    //}
    //s3is.close();
    //fos.close();
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    //System.exit(1);
} catch (FileNotFoundException e) {
    System.err.println(e.getMessage());
    //System.exit(1);
} catch (IOException e) {
    System.err.println(e.getMessage());
    //System.exit(1);
}


   }

   public boolean listTheBuck(String foo)
   {
       System.out.println("helosd");
       //final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4572/","eu-west-1"))
        .withPathStyleAccessEnabled(true)
        .build();

        List<Bucket> buckets = s3.listBuckets();
        System.out.println("Your Amazon S3 buckets are:");
        for (Bucket b : buckets) {
            System.out.println("* " + b.getName());
        }

        ListObjectsV2Result result = s3.listObjectsV2("mytestbucket");
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        for (S3ObjectSummary os: objects) {
            System.out.println("* " + os.getKey());
        }
        StringBuilder sb = new StringBuilder();


try {
    S3Object o = s3.getObject(new GetObjectRequest(foo, "hello.txt"));
    //S3ObjectInputStream s3is = o.getObjectContent();
    displayTextInputStream(o.getObjectContent());
    
    //FileOutputStream fos = new FileOutputStream(new File(key_name));
    //byte[] read_buf = new byte[1024];
    //int read_len = 0;
    //while ((read_len = s3is.read(read_buf)) > 0) {
        //sb.append(read_len);
    //}
    //s3is.close();
    //fos.close();
} catch (AmazonServiceException e) {
    System.err.println(e.getErrorMessage());
    //System.exit(1);
} catch (FileNotFoundException e) {
    System.err.println(e.getMessage());
    //System.exit(1);
} catch (IOException e) {
    System.err.println(e.getMessage());
    //System.exit(1);
}

       return true;
   }




    private static void displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println();
    }

}

/*
@Controller
public class RestController {
 
    @GetMapping(value = "/student/{studentId}")
    public @ResponseBody Student getTestData(@PathVariable Integer studentId) {
        Student student = new Student();
        student.setName("Peter");
        student.setId(studentId);
 
        return student;
    }
}
*/

//create bucket
//list buckets
//add item
//list items
