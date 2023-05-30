//package com.library.book.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeoutException;
//
//@Service
//public class GoogleService {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    public static void createApiKey(String projectId)
//            throws IOException, ExecutionException, InterruptedException, TimeoutException {
//        // Initialize client that will be used to send requests. This client only needs to be created
//        // once, and can be reused for multiple requests. After completing all of your requests, call
//        // the `apiKeysClient.close()` method on the client to safely
//        // clean up any remaining background resources.
//        try (ApiKeysClient apiKeysClient = ApiKeysClient.create()) {
//
//            Key key = Key.newBuilder()
//                    .setDisplayName("My first API key")
//                    // Set the API key restriction.
//                    // You can also set browser/ server/ android/ ios based restrictions.
//                    // For more information on API key restriction, see:
//                    // https://cloud.google.com/docs/authentication/api-keys#api_key_restrictions
//                    .setRestrictions(Restrictions.newBuilder()
//                            // Restrict the API key usage by specifying the target service and methods.
//                            // The API key can only be used to authenticate the specified methods in the service.
//                            .addApiTargets(ApiTarget.newBuilder()
//                                    .setService("translate.googleapis.com")
//                                    .addMethods("translate.googleapis.com.TranslateText")
//                                    .build())
//                            .build())
//                    .build();
//
//            // Initialize request and set arguments.
//            CreateKeyRequest createKeyRequest = CreateKeyRequest.newBuilder()
//                    // API keys can only be global.
//                    .setParent(LocationName.of(projectId, "global").toString())
//                    .setKey(key)
//                    .build();
//
//            // Make the request and wait for the operation to complete.
//            Key result = apiKeysClient.createKeyAsync(createKeyRequest).get(3, TimeUnit.MINUTES);
//
//            // For authenticating with the API key, use the value in "result.getKeyString()".
//            // To restrict the usage of this API key, use the value in "result.getName()".
//            System.out.printf("Successfully created an API key: %s", result.getName());
//        }
//}
