// Variable globale
var tableau = document.getElementById("tabListe");
var idNom = document.getElementById("nom");
var idSalaire = document.getElementById("salaire");
var btnAjouter = document.getElementById("ajouter");
var liste = document.querySelector(".tabListe");

var inputs = [];
inputs[0] = idNom;
inputs[1] = idSalaire;

btnAjouter.addEventListener('click',ajouter);

//fonction
function ajouter(){
    
    var ligne = document.createElement("tr");
    
    for (let index = 0; index < inputs.length; index++) {
        var colonne = document.createElement("td");
        colonne.textContent = inputs[index].value;
        if (index==1) {
            var nomClass ="sal";
            colonne.classList.add(nomClass);
        }
        ligne.appendChild(colonne);

    }
    var suppr = document.createElement("td");
    suppr.classList.add("tt");
    suppr.innerHTML = "<a href=\"#\"> Supprimer </a>";
    
    suppr.addEventListener('click',()=>{
        
        ligne.remove();
        
        var total = somme();
        var lastLine = document.querySelector(".last");
        console.log(total); 
        lastLine.remove();
        var lastLine = document.createElement("tr");
        lastLine.classList.add("last"); 
        var column1 = document.createElement("td");
        var column2 = document.createElement("td");
        column1.textContent = "Totale";
        column2.textContent = total;
        lastLine.appendChild(column1);
        lastLine.appendChild(column2);
        
         liste.appendChild(lastLine);    
    });
    
    ligne.appendChild(suppr);
    liste.appendChild(ligne);
    
    // mi - renvoyer false izy raha null
    var lastLine = document.querySelector(".last");
    
    if (lastLine) {
        lastLine.remove();        
    }
    var totale = somme();
    var lastLine = document.createElement("tr");
    lastLine.classList.add("last");
    var column1 = document.createElement("td");
    var column2 = document.createElement("td");
    column1.textContent = "Totale";
    column2.textContent = totale;
    lastLine.appendChild(column1);
    lastLine.appendChild(column2);
    //var lastLine = " <tr class=\"last\" > <td> Totale </td> <td> "+ totale +" </td> </tr> ";
    liste.appendChild(lastLine);
    
    // Juste test removeEventListener
    var supprimer = document.querySelectorAll(".tabListe .tt");
    supprimer[2].removeEventListener()

}
function somme() { // miaraka @ update
    var listeSal = document.querySelectorAll(".sal");
    
    var somme = 0;
    for (let i = 0; i < listeSal.length ; i++) {
        somme+= parseFloat(listeSal[i].textContent); 
        
    }
   
    return somme;
}

