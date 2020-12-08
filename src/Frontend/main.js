let List = [];

function addNote() {
    event.preventDefault();
    let input = $(".input").val();

    if(input.length > 0){
        let note = {
            text: input,
            
        }

        List.push(note);
        console.log(List);
    }
    //Updatera denna biten!
    else{
        alert("Input tom");
    }
    $(".input").val("");
    renderList();
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
            List.splice(i,1);
            $(this).parent().remove();
        })
    }
}


//function addImage(){}


//function addFile(){}

