package com.keddok.test;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author RMakhmutov
 * @since 27.07.2016
 */
@WebServlet("/StreamingProxyServlet")
public class StreamingProxyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        InputStream in = req.getInputStream();
        OutputStream proxyOut = null;

        try {
            ServletFileUpload upload = new ServletFileUpload();
            // Parse the request
            FileItemIterator iter = upload.getItemIterator(req);
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                String name = item.getFieldName();
                in = item.openStream();

                byte[] bytes = new byte[1024];
                int bytesRead;

                URL url = new URL("http://localhost:8080/StreamingServlet");

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setChunkedStreamingMode(1024);

                proxyOut = connection.getOutputStream();

                while ((bytesRead = in.read(bytes)) != -1) {
                    proxyOut.write(bytes, 0, bytesRead);
                }
                proxyOut.flush();

                out.write(("<html>http code: </html>"+connection.getResponseCode()).getBytes());

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (connection.getInputStream())));

                String output;
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }

                connection.disconnect();
            }
        }
        catch (Exception ex)
        {
            out.write("<html>proxy error</html>".getBytes());
        }
        finally {
            // do the following in a finally block:
            if (proxyOut != null)
                proxyOut.close();
            in.close();
            out.close();
        }
    }
}
