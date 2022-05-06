let dropArea = document.getElementById('drop-area');

['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
    dropArea.addEventListener(eventName, preventDefaults);
})

function preventDefaults (e) {
    e.preventDefault();
    e.stopPropagation();
}

['dragenter', 'dragover'].forEach(eventName => {
    dropArea.addEventListener(eventName, highlight);
});

['dragleave', 'drop'].forEach(eventName => {
    dropArea.addEventListener(eventName, unhighlight);
});

function highlight() {
    dropArea.classList.add('highlight');
}

function unhighlight() {
    dropArea.classList.remove('highlight');
}

dropArea.addEventListener('drop', handleDrop);

function handleDrop(e) {
    let dt = e.dataTransfer;
    let files = dt.files;

    handleFiles(files);
}

// Формирование списка файлов для отправки
function handleFiles(files) {
    files = [...files];
    files.forEach(previewFile);

    let uploadBtn = document.getElementById('upload');
    uploadBtn.addEventListener('click', function () {
        console.log(files);
        uploadFile(files);
        let uploadList = document.getElementById('upload-list');
        while (uploadList.firstChild) {
            uploadList.removeChild(uploadList.lastChild);
        }
    });

    let deleteElem = document.getElementsByClassName('del-btn');
    let deleteElemArr = [...deleteElem];
    deleteElemArr.forEach((onebyone) => {
        onebyone.addEventListener('click', function (elem, index) {
            elem.target.parentNode.remove();
            files.splice(index, 1);
        });
    });
}

// Отображение списка файлов на странице
function previewFile(file) {
    let uploadList = document.getElementById('upload-list');
    uploadList.innerHTML += '<p>' + file.name + '<span class="del-btn">&#10006</span>' + '</p>';

}

// Отправка POST запроса на сервер
function uploadFile(files) {
    let url = "fileUpload";
    let xhr = new XMLHttpRequest();
    let formData = new FormData();
    xhr.open('post', url, true);

    xhr.addEventListener('readystatechange', function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
        }
        else if (xhr.readyState === 4 && xhr.status !== 200) {
            alert("Произошла ошибка повторите отправку снова")
        }
    })
    for(let file of files) {
        formData.append(file.name, file);
    }
    console.log(...formData)
    for(let [key, value] of formData){
        console.log(`Key : ${key}`);
        console.log(`Value : ${value}`);
    }
    xhr.send(formData);
}

