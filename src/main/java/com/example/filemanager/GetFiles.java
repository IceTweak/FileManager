package com.example.filemanager;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "getFiles", value = "/getFiles")
public class GetFiles extends HttpServlet {

    public static final String UPLOAD_DIR = "uploadedFiles";

    // GET метод для формирования информации о фалах на сервере
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            handleGet(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String applicationPath = getServletContext().getRealPath("");
        String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
        File fileUploadDirectory = new File(uploadPath);
        if (!fileUploadDirectory.exists()) {
            fileUploadDirectory.mkdirs();
        }

        UploadDetail details;
        File[] allFiles = fileUploadDirectory.listFiles();
        List<UploadDetail> fileList = new ArrayList<>();
        for (File file : allFiles) {
            details = new UploadDetail();
            details.setFileName(file.getName());
            details.setFileSize(file.length() / 1024);
            fileList.add(details);
        }

        request.setAttribute("uploadedFiles", fileList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("uploads.jsp");
        dispatcher.forward(request, response);
    }
}
