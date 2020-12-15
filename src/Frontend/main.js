let List = [];

getNotes();


//FUNGERAR
async function createNote(e){
    e.preventDefault();

    // upload image with FormData
    let files = document.querySelector('input[type=file]').files;
    let formData = new FormData();

    for(let file of files) {
        formData.append('files', file, file.name);
    }

    // upload selected files to server
    let uploadResult = await fetch('/api/file-upload', {
        method: 'POST',
        body: formData
    });

    // get the uploaded image url from response
    let imageUrl = await uploadResult.text();

    let titleInput = document.querySelector('#title-input');
    let contentInput = document.querySelector('#notes-input');

    // create a post object containing values from inputs
    // and the uploaded image url
    let note = {
        title: titleInput.value,
        text: contentInput.value,
        imageUrl: imageUrl
    }

    let result = await fetch("/rest/notes", {
        method: "POST",
        body: JSON.stringify(note)
    });

    console.log("funkar du", note);
    List.push(note);
    renderList();
}


//FUNGERAR
function renderList() {
    
    $("#notes-ul").empty();
    for(let i = 0 ; i< List.length ; i++){
    $("#notes-ul").append(`<li>
    <h3>${List[i].title}</h3><br>
    <p>${List[i].text}</p>
    <img src="${List[i].imageUrl}" alt="uppladdad bild...."><br>
    <button class="deleteB">X</button>
    </li><br>`);
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
        })
    }
}


//FUNGERAR
async function getNotes(){
    let result = await fetch("/rest/notes");
    List = await result.json();
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
