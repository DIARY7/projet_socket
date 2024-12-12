var produit = new Array("Briquet","Sweet a Capuche", " Ordinateur" ," Guitare" );
var prix = new Array(1000,156320,78900,3656);
var select = document.getElementById("selection");
var btn = document.getElementById("btn");
var liste = document.querySelector(".liste");
var dell;

for (let i = 0; i < produit.length; i++) {
    var option = "<option>"+ produit[i] +"</option>";
    select.innerHTML += option;
}

btn.addEventListener("click",()=>{
    var option = document.querySelectorAll("#selection option");
    var indice;
    var line;

    for (let i = 0; i < option.length; i++) {
        if (option[i].value == select.value) {
            indice = i;
            line =  document.querySelector(".ligne"+i);
            if (line == null) {
                indice = i;
                var td1 = "<td>" + produit[i]  +"</td>";
                var td2 = "<td class=\"prix\">" + prix[i]  + "</td>";
                var td3 = "<td class=\"quantite\">"+ 1 +"</td>";
                var suppr = "<td> <input type=\"button\" value=\"Supprimer\" class=\"suppr"+ i +" del\" ></td> ";
                liste.innerHTML+="<tr class=\"ligne"+ i +"\" > " + td1 + td2 + td3 + suppr + "</tr>";
                
            } 
            else{
                var quantite =  document.querySelector(".ligne"+i+" .quantite");
                quantite.textContent = Number(quantite.textContent) + 1;
            }
            

            break;
        }
        
    }

    
    

    var total = somme();
    var lastLine = document.querySelector(".last");
    if (lastLine) {
        lastLine.remove();
    }
    
    var last = "<tr class=\"last\" > <td> Totale </td> <td>"+ total +"</td>  </tr>"
    liste.innerHTML+=last;
    
    
        delet();
    
           
    
    

});
function somme() { 
    var listeSal = document.querySelectorAll(".prix");
    var quantite = document.querySelectorAll(".quantite");
    var somme = 0;
    for (let i = 0; i < listeSal.length ; i++) {
        somme+= parseFloat(listeSal[i].textContent) * parseFloat(quantite[i].textContent); 
        
    }
   
    return somme;
}
function delet () {
    var ligne = document.querySelectorAll(".liste tr");
    var btn = document.querySelectorAll(".del");
    for (let i = 0; i < btn.length; i++) {
        btn[i].addEventListener('click',()=>{
            ligne[i].remove();
            //var lastLine = document.querySelector(".last");
            var caseTotal = document.querySelectorAll(".last td")[1];
            
            //lastLine.remove();
            total = somme();
            console.log(total);
            caseTotal.textContent = total;
            
            
            
           
        });
        
    }
}

