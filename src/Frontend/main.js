let List = [];
let imagesToRender = [];

getNotes();




//FUNGERAR
async function createNote(e){
    e.preventDefault();

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
    //imagesToRender.push(await uploadResult.text());

    let titleInput = document.querySelector('#title-input');
    let contentInput = document.querySelector('#notes-input');
    let fileInput = document.querySelector('#fileInput');
    //imagesToRender = fileInput.value;
    console.log(imagesToRender);

    // create a post object containing values from inputs
    // and the uploaded image url
    let note = {
        title: titleInput.value,
        text: contentInput.value,
        //imageUrl: imageUrl
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
}

    console.log("funkar du", note);
    List.push(note);
    renderList();

    
}


//FUNGERAR
// function renderList() {
    
//     $("#notes-ul").empty();
//     for(let i = 0 ; i< List.length ; i++){
//     $("#notes-ul").append(`<div>
//     <li>
//     <h3>${List[i].title}</h3><br>
//     <p>${List[i].text}</p>
//     <embed src="${List[i].imageUrl}" width="200" height="150"><br>
//     <button class="deleteB">X</button>
//     </li></div><br>`);
//     }
//     deleteNote();
// }


function renderList() {

    $("#notes-ul").empty();
    for(let i = 0 ; i < List.length ; i++){
        let string = `<div class="fullNote">
    <h3>${List[i].title}</h3><br>
    <p>${List[i].text}</p>`;

    for(let j = 0; j < imagesToRender.length; j++){
    string += `<embed src="${imagesToRender[j]}" width="200" height="150"><br>`;
    }
    string +=
    `<button class="deleteB">X</button>
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
