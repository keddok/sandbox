package com.keddok.sandbox.cxf.webclient;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

/**
 * @author RMakhmutov
 * @since 21.12.2017
 */
public class ConnectionPoolExhaustingTest {
//    @Test
    public void testSuccess() throws InterruptedException {
        String existingKey = "1";
        runTestContinuosly(this::safeCallService, existingKey);
    }

//    @Test
    public void testFail() throws InterruptedException {
        String invalidKey = "thisKeyIsInvalid";
        runTestContinuosly(this::unsafeCallService, invalidKey);
    }

    private void runTestContinuosly(Function<String, String> testFunction, String serviceParameter) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (;;) {
            Thread.sleep(20);
            pool.execute(() -> {
                assert testFunction.apply(serviceParameter) != null;
            });
        }
    }

    private String safeCallService(String paramValue) {
        try {
            WebClient client = WebClient.create("http://localhost:8080/sandbox").path("get").query("key", paramValue);
            try {
                String out = client.get().readEntity(String.class);
                System.out.println(out);
                return out;
            } finally {
                if (client.getResponse() != null)
                    client.getResponse().close();
            }
        }
        catch (Exception ex) {
            /// ignored
        }

        return null;
    }

    private String unsafeCallService(String paramValue) {
        try {
            WebClient client = WebClient.create("http://localhost:8080/sandbox").path("get").query("key", paramValue);
            String out = IOUtils.toString((InputStream) client.get().getEntity(), "UTF-8");
            System.out.println(out);
            return out;
        }
        catch (Exception ex) {
            /// ignored
        }

        return null;
    }
}
