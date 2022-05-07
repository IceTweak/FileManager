package com.example.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet(name = "fileDownload", value = "/fileDownload")
public class FileDownloadServlet extends HttpServlet {


    public static int BUFFER_SIZE = 1024 * 100;
    public static final String UPLOAD_DIR = "uploadedFiles";


    // Метод для реализации скачивания файлов с сервера
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            handleGet(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем путь до директории с файлами на сервере
        String fileName = request.getParameter("fileName"),
                applicationPath = getServletContext().getRealPath(""),
                downloadPath = applicationPath + File.separator + UPLOAD_DIR,
                filePath = downloadPath + File.separator + fileName;

        File file = new File(filePath);
        OutputStream outStream = null;
        FileInputStream inputStream = null;
        if (file.exists()) {
            // Установка атрибутов содержимого для объекта ответа
            String mimeType = "application/octet-stream";
            response.setContentType(mimeType);
            // Установка заголовка содержимого для объекта ответа
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
            response.setHeader(headerKey, headerValue);
            try {
                // Получение выходного потока для записи ответа
                outStream = response.getOutputStream();
                inputStream = new FileInputStream(file);
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                //Запись каждого байта данных, считанных из входного потока в выходной
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
            } catch(IOException ex) {
                System.out.println("Ошибка при записи?= " + ex.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                outStream.flush();
                if (outStream != null) {
                    outStream.close();
                }
            }
        } else {

            response.setContentType("text/html");

            response.getWriter().println("<h3>Файл "+ fileName +" Отсутствует в списке .....!</h3>");
        }
    }
}
