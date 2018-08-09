package com.keddok.sandbox.cxf.soap.client;

import com.keddok.sandbox.cxf.soap.server.SoapStreaming;
import com.keddok.sandbox.cxf.soap.server.SoapStreamingService;
import com.sun.xml.internal.ws.developer.JAXWSProperties;
import com.sun.xml.internal.ws.developer.StreamingDataHandler;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.MTOMFeature;
import java.io.File;
import java.util.Map;

/**
 * @author RMakhmutov
 * @since 15.03.2018
 */
public class SoapStreamingClient {
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(SoapStreamingService.class);
        SoapStreaming port = (SoapStreamingService)factory.create();
//        MTOMFeature feature = new MTOMFeature();
//        SoapStreaming port = service.getMtomStreamingPortTypePort(
//                feature);
        Map<String, Object> ctxt=((BindingProvider)port).getRequestContext();
        ctxt.put(JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, 8192);
        DataHandler dh = new DataHandler(new
                FileDataSource("/tmp/example.jar"));
        port.fileUpload("/tmp/tmp.jar",dh);

        dh = port.fileDownload("/tmp/tmp.jar");
        StreamingDataHandler sdh = (StreamingDataHandler)dh;
        try{
            File file = new File("/tmp/tmp.jar");
            sdh.moveTo(file);
            sdh.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
  }
}
