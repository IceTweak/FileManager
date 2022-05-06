<%@ page import="com.example.filemanager.UploadDetail" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ваши файлы</title>
    <link rel="stylesheet" href="css/upload.css" />
</head>
<body>
<div class="panel">
    <h1 id="upload-head">Файлы на сервере</h1>
    <table class="bordered_table">
        <thead>
        <tr align="center"><th class="thead">Имя файла</th><th class="thead">Размер файла</th><th class="thead">Ссылка</th></tr>
        </thead>
        <tbody>
        <% List<UploadDetail> uploadDetails = (List<UploadDetail>)request.getAttribute("uploadedFiles");
            if(uploadDetails != null && uploadDetails.size() > 0) {
                for (UploadDetail uploadDetail : uploadDetails) {
        %>
        <tr>
            <td align="center"><span id="fileName"><%=uploadDetail.getFileName() %></span></td>
            <td align="center"><span id="fileSize"><%=uploadDetail.getFileSize() %> KB</span></td>
            <td align="center"><span id="fileDownload"><a id="downloadLink" class="hyperLink"
                                                          href="<%=request.getContextPath()%>/fileDownload?fileName=<%=uploadDetail.getFileName() %>">Скачать</a></span>
            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="3" align="center"><span id="noFiles">Файлы еще не были загружены.....!</span></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <div id="back-link">
        <a id="fileUpload" class="hyperLink" href="<%=request.getContextPath()%>/index.jsp">Назад</a>
    </div>
</div>
</body>
</html>
