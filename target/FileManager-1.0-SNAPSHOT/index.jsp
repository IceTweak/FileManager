<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/main.css">
    <title>File Manager</title>
</head>
<body>

    <div id="head-div">
        <h1 id="header">Файловое хранилище</h1>
        <h3 id="subheader">Приложение позволяет загружать файлы <br>на сервер и позже скачивать их</h3>
    </div>
    <div id="drop-area">
        <form class="my-form" enctype="multipart/form-data">
            <p>Загрузите несколько файлов с помощью диалогового окна файла
                или путем перетаскивания файлов в пунктирную область.
            </p>
            <input type="file" id="fileElem" multiple onchange="handleFiles(this.files)">
            <label class="button" for="fileElem">Выбрать файлы</label>
            <p id="file_size">Размер файла не должен превышать <b><u>30 МБ</u></b></p>
        </form>
        <div id="upload-list"></div>
    </div>
    <div id="buttons">
        <button id="upload" class="button">Отправить файлы</button>
        <a href="getFiles"><button id="download" class="button">Мои файлы</button></a>
    </div>



    <script src="js/drop.js"></script>
</body>
</html>