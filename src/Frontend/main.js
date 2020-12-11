let List = [];

getNotes();

function addNote() {
    event.preventDefault();
    let input = $(".input").val();

    if(input.length > 0){
        let note = {
            text: input,
            
        }

        List.push(note);
        createNote(note);
        getNotes();
        console.log(List);
    }
    //Updatera denna biten!
    else{
        alert("Input tom");
    }
    $(".input").val("");

}



function renderList() {
    
    $(".input-list").empty();
    for(let i = 0 ; i< List.length ; i++){
    $(".input-list").append(`<li> ${List[i].text}<button class="deleteB">X</button></li>`);
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
