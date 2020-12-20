let List = [];
let imagesToRender = [];
let filesToRender = [];

getNotes();
getFiles();


function addFilesandNotes(e){
    e.preventDefault();
    createNote();
    //getNotes();
    //getFiles();

}

//FUNGERAR
async function createNote(){
    //e.preventDefault();

    // upload image with FormData
    let files = document.querySelector('input[type=file]').files;
    let formData = new FormData();

    for(let file of files) {
        formData.append('files', file, file.name);
        imagesToRender.push('/Uploads/' + file.name);
        console.log(file.name);
    }

    // upload selected files to server
    if(imagesToRender > 0){
        let uploadResult = await fetch('/api/file-upload', {
            method: 'POST',
            body: formData
        });

    } 

    // get the uploaded image url from response

    let titleInput = document.querySelector('#title-input');
    let contentInput = document.querySelector('#notes-input');
    let fileInput = document.querySelector('#fileInput');
    console.log(imagesToRender);

    // create a post object containing values from inputs
    // and the uploaded image url
    let note = {
        title: titleInput.value,
        text: contentInput.value,

    }



    let result = await fetch("/rest/notes", {
        method: "POST",
        body: JSON.stringify(note)
    });

    for(let i = 0; i < imagesToRender.length; i++){
        let userInput = {
            imageUrl: imagesToRender[i]
        }
    let result1 = await fetch("/rest/files", {
        method: "POST",
        body: JSON.stringify(userInput)
        
    });

    filesToRender.push(userInput);
}

    imagesToRender.splice(0, imagesToRender.length);
    console.log("funkar du", note);
    List.push(note);
    location.reload(true);
    //renderList();

}

function renderList() {

    $("#notes-ul").empty();
    for(let i = 0 ; i < List.length ; i++){
        let string = `<div class="fullNote">
    <h3>${List[i].title}</h3><br>
    <textarea class="updateTextArea">${List[i].text}</textarea>`;

    for(let j = 0; j < filesToRender.length; j++){
    if(filesToRender[j].notesID == List[i].id){
        string += `<embed src="${filesToRender[j].imageUrl}" width="200" height="150"><br>`;
    }
    }
    string +=
    `<button class="updateB">Update</button>
     <button class="deleteB">X</button>
    </div>`;
    $("#notes-ul").append(string);
    }
    deleteNote();

}

//FUNGERAR
function deleteNote(){

    let deleteBtns = $(".deleteB");
    for(let i = 0 ;  i < deleteBtns.length ; i++){
        $(deleteBtns[i]).click(function(){
            deleteNoteDB(List[i]);
            List.splice(i,1);
            $(this).parent().remove();
            getNotes();
            getFiles();
        })
    }
}

function updateNote(){
    let updateBtns = $(".updateB");
    for(let i = 0; i < updateBtns.length; i++){
        $(updateBtns[i]).click(function(){
            let updatedText = this.document.getElementsByClassName("updateTextArea").value();
            List[i].text = updatedText;
            updateNoteDB(List[i]);
        })
    }
}


//FUNGERAR
async function getNotes(){
    let result = await fetch("/rest/notes");
    List = await result.json();
    renderList();
}

async function getFiles(){
    let result = await fetch("/rest/files");
    filesToRender = await result.json();
    renderList();
}

//FUNGERAR!
async function deleteNoteDB(note){
    let result = await fetch("/rest/notes/id",{
        method: "DELETE",
        body: JSON.stringify(note)
    })
    
    console.log(await result.text());
}


async function updateNoteDB(note){
    let result = await fetch("/rest/notes/id", {
        method: "PUT",
        body: JSON.stringify(note)
    })

    console.log(await result.text());
}



//sök function
function search(userInput) {
    let allItems = $('.fullNote');
   
   for(let item of allItems) {
         let h3 = $(item).find('h3').text();
   
        if (h3.includes(userInput)) {
        $(item).show();
        }else {
        $(item).hide();
     }
    }
}
