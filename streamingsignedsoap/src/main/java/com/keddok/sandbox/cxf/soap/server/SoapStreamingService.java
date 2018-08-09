package com.keddok.sandbox.cxf.soap.server;

import com.sun.xml.internal.ws.developer.StreamingAttachment;
import com.sun.xml.internal.ws.developer.StreamingDataHandler;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.MTOM;
import java.io.File;

/**
 * @author RMakhmutov
 * @since 15.03.2018
 */
@StreamingAttachment(parseEagerly = true, memoryThreshold = 40000L)
@MTOM
@WebService(name = "SoapStreaming",
        serviceName = "SoapStreamingService",
        targetNamespace = "http://streaming.soap.sandbox.keddok.com")
public class SoapStreamingService implements SoapStreaming {
    // Use @XmlMimeType to map to DataHandler on the client side
    public void fileUpload(String fileName, @XmlMimeType("application/octet-stream") DataHandler data) {
        try {
            StreamingDataHandler dh = (StreamingDataHandler) data;
            File file = new File(fileName);
            dh.moveTo(file);
            dh.close();
        } catch (Exception e) {
            throw new WebServiceException(e);
        }
    }

    @XmlMimeType("application/octet-stream")
    @WebMethod
    @Oneway
    public DataHandler fileDownload(String filename) {
        return new DataHandler(new FileDataSource(filename));
    }
}
