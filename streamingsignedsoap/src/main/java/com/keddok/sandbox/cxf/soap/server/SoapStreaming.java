package com.keddok.sandbox.cxf.soap.server;

import javax.activation.DataHandler;

/**
 * @author RMakhmutov
 * @since 15.03.2018
 */
public interface SoapStreaming {
    public void fileUpload(String fileName, DataHandler data);
    public DataHandler fileDownload(String filename);
}
