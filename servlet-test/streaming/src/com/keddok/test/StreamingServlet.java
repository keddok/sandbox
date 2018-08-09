package com.keddok.test;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;


/**
 * @author RMakhmutov
 * @since 26.07.2016
 */
@WebServlet("/StreamingServlet")
public class StreamingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        InputStream in = req.getInputStream();
        File file = new File("D:\\JavaProjects\\servlet-test\\upload.file");
        FileOutputStream fileWriter = new FileOutputStream(file);

        try {
                byte[] bytes = new byte[1024];
                int bytesRead;

                while ((bytesRead = in.read(bytes)) != -1) {
                    fileWriter.write(bytes, 0, bytesRead);
                }

                out.write("<html>upload ok</html>".getBytes());
        }
        catch (Exception ex)
        {
            out.write("<html>upload error</html>".getBytes());
        }
        finally {
            fileWriter.close();
            in.close();
            out.close();
        }
    }
}
