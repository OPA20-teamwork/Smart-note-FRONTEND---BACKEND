let List = [];

getNotes();

function addNote() {
    event.preventDefault();
    let input = $("#notes-input").val();
    let titleInput = $("#title-input").val();
    
    if(input.length > 0 ){
        let note = {
            title: titleInput,
            text: input,
            
        }
        
        List.push(note);
        createNote(note);
        console.log(List);
        getNotes();
    }
    //Updatera denna biten!
    else{
        alert("Input tom");
    }
    $(".input").val("");
    
}



function renderList() {
    
    $("#notes-ul").empty();
    for(let i = 0 ; i< List.length ; i++){
    $("#notes-ul").append(`<li>
    <h3>${List[i].title}</h3><br>
    <p>${List[i].text}</p>
    <button class="deleteB">X</button>
    </li><br>`);
    }
    deleteNote();
}

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

async function getNotes(){
    let result = await fetch("/rest/notes");
    List = await result.json();
    renderList();
}

async function createNote(note){
    let result = await fetch("/rest/notes",{
        method: "POST",
        body: JSON.stringify(note)
    });

    console.log(await result.text());
}

async function deleteNoteDB(note){
    let result = await fetch("/rest/notes/id",{
        method: "DELETE",
        body: JSON.stringify(note)
    })
    
    console.log(await result.text());
}
