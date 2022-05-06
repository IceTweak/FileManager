package com.example.filemanager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "fileUpload", value = "/fileUpload")
//  Устанавливаем максимальные размеры файла и запроса
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 50)
public class FileUploadServlet extends HttpServlet {

    public static final String UPLOAD_DIR = "uploadedFiles";

    //  Обрабатываем POST запрос с файлами
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            handlePost(request);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public void handlePost(HttpServletRequest request) throws ServletException, IOException {
        // Получаем путь к директории для записи файлов
        String applicationPath = getServletContext().getRealPath("");
        String uploadPath = applicationPath + File.separator + UPLOAD_DIR;

        File fileUploadDirectory = new File(uploadPath);
        if (!fileUploadDirectory.exists()) {
            fileUploadDirectory.mkdirs();
        }
        String fileName;
        UploadDetail details;
        List<UploadDetail> fileList = new ArrayList<>();
        try {
            for (Part part : request.getParts()) {
                fileName = extractFileName(part);
                details = new UploadDetail();
                details.setFileName(fileName);
                details.setFileSize(part.getSize() / 1024);
                try {
                    part.write(uploadPath + File.separator + fileName);
                    details.setUploadStatus("Success");
                } catch (IOException ex) {
                    details.setUploadStatus("Failure : " + ex.getMessage());
                }
                fileList.add(details);
            }
        } catch (jakarta.servlet.ServletException e) {
            e.printStackTrace();
        }
    }

    // Вспомогательный метод для чтения имён файлов
    private String extractFileName(jakarta.servlet.http.Part part) {
        String fileName = "";
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return fileName;
    }
}