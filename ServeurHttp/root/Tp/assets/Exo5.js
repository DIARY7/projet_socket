/* data */ 
var animal = new Array("Python","Souris","Lion","Black panthere");
var sary = new Array("phn1","phn32","phn18","phn25");
// concernant liste d'image
var select = document.getElementById("liste");
var btn = document.getElementById("btn");
var bloc = document.querySelector(".img .row");
/* anaty light box  */ 
var croix = document.querySelector(".croix");
var saryLight = document.querySelector(".light_box .sary");
var light_box = document.querySelector(".light_box");
var next = document.querySelector(".light_box .next");
var prev = document.querySelector(".light_box .previous");
var curImage;
/* momba Popover */ 
var insert; //indice n'ilay animal vao ny inserena

CreateOption();
function CreateOption() {
    for (let i = 0; i < animal.length; i++) {
        var option = document.createElement("option");
        option.id = i;
        option.innerHTML = animal[i];
        select.appendChild(option);
    }
}

btn.addEventListener('click',()=>{
    var option = document.querySelectorAll("#liste option");
    
    for (let i = 0; i < option.length; i++) {
        if (option[i].value == select.value ) {
            //var description = getDesc(i);
            var image = 
            `<div class="col-md-4 col-sm-6 colo-xs-12 ">
                <div class="thumbnail" >
                    <img src = "animal/${sary[i]}.jpg" class="saryListe" id='img${i}'  > 
                    <div class="caption" > <input type="button" class="examiner btn " value="Examiner" >  <input type="button" class="supprimer btn " value="Supprimer" > </div> 
                </div>
            </div> ` ;
            
            bloc.innerHTML += image;
            setPopover(i);
           
           
        }    
    }
    examiner();
    supprimer();
    
    
    
            
});

function setPopover(insert) { // mametraka mombamomban'ilay animal anaty popover
   var description = localStorage.getItem("desc"+insert); 
   var img = document.querySelectorAll("#img"+insert);
    img[img.length-1].setAttribute("data-delay",'{"show":"500" , "hide":"200" }');
    img[img.length-1].setAttribute('data-toogle',"popover");
    img[img.length-1].setAttribute('data-placement','right');
    img[img.length-1].setAttribute("data-trigger","hover");
    img[img.length-1].setAttribute("title",animal[insert]);
    img[img.length-1].setAttribute("data-content",description); 
    
   
   
   $('.thumbnail img').popover({html:true}); 
}

function examiner(){
    
    var btnExam = document.querySelectorAll(".row .examiner");
    var saryListe = document.querySelectorAll(".saryListe");
    

    for (let i = 0; i < btnExam.length; i++) {
        btnExam[i].addEventListener('click',()=>{
            curImage = i;
            
            saryLight.src=saryListe[i].src;
            light_box.style="display: flex ";
            
            console.log(curImage);

            if (i==0) {

                prev.disabled=true;
            } 
            if (i == btnExam.length-1) {
                next.disabled = true;
                
            }
            /* mi régler bouton next sy previous */
            else if(i>0 && i< btnExam.length-1)
            {
                next.disabled = false;
                prev.disabled = false;
            }
        });
        
    }
    
    
}

function supprimer() { //
    var blocImages = document.querySelectorAll(".col-md-4");
    var suppr = document.querySelectorAll(".row .supprimer");
    for (let i = 0; i < suppr.length; i++) {
        suppr[i].addEventListener('click',()=>{
            blocImages[i].innerHTML="";
        });
    }
}


/* fonction pour light_box */ 
croix.addEventListener('click',()=>{
    light_box.style="display:none";
});

next.addEventListener("click",()=>{
    var saryListe = document.querySelectorAll(".saryListe");
        

    curImage++;
    saryLight.src = saryListe[curImage].src;
        
    if (curImage == saryListe.length -1) {
        next.disabled = true;
    }
   /* Mamerina an'ilay prev a l'etat normal */ 
   if (curImage>0) {
    prev.disabled=false;
}
    
    
});
prev.addEventListener("click",()=>{
    var saryListe = document.querySelectorAll(".saryListe");

        curImage--;
        saryLight.src = saryListe[curImage].src;    
        
        if (curImage == 0) {
            prev.disabled=true;         
        }

        if (curImage < saryListe.length-1 ) {
            next.disabled=false;
        }
        
    
});