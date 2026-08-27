document.addEventListener("DOMContentLoaded",()=>{

document.querySelectorAll(".support-card").forEach(card=>{

card.addEventListener("click",()=>{

const targetId=card.dataset.target;
const target=document.getElementById(targetId);

if(!target)return;

const subject=card.dataset.subject;

if(subject){
const select=document.getElementById("oggetto");
if(select)select.value=subject;
}

/* SCROLL PRECISO SOTTO LA NAVBAR */
const header=document.querySelector(".site-header");
const headerHeight=header?header.offsetHeight:0;

const targetPosition=
target.getBoundingClientRect().top+
window.scrollY-
headerHeight+2;

window.scrollTo({
top:targetPosition,
behavior:"smooth"
});

/* SE È UNA FAQ LA APRE */
if(target.classList.contains("support-faq")){
target.classList.add("is-open");

const question=target.querySelector(".support-question");

if(question){
question.setAttribute("aria-expanded","true");
}
}

/* SE È UN PROBLEMA DA COMPILARE PORTA IL CURSORE AL MESSAGGIO */
if(subject){
setTimeout(()=>{
const messaggio=document.getElementById("messaggio");

if(messaggio){
messaggio.focus({preventScroll:true});
}
},650);
}

});

});

/* FAQ */
document.querySelectorAll(".support-question").forEach(question=>{

question.addEventListener("click",()=>{

const faq=question.closest(".support-faq");

if(!faq)return;

const open=faq.classList.toggle("is-open");

question.setAttribute(
"aria-expanded",
open?"true":"false"
);

});

});

/* FORM */
const form=document.getElementById("supportForm");
const message=document.getElementById("supportFormMessage");

if(form){

form.addEventListener("submit",e=>{

e.preventDefault();

const nome=document.getElementById("nome");
const email=document.getElementById("email");
const oggetto=document.getElementById("oggetto");
const messaggio=document.getElementById("messaggio");

if(
!nome.value.trim()||
!email.value.trim()||
!oggetto.value||
!messaggio.value.trim()
){

if(message){
message.textContent="Compila tutti i campi prima di continuare.";
}

return;
}

if(!email.checkValidity()){

if(message){
message.textContent="Inserisci un indirizzo email valido.";
}

return;
}

if(message){
message.textContent="Modulo compilato correttamente.";
}

});

}

/* APERTURA FAQ TRAMITE URL */
const hash=window.location.hash;

if(hash){

const target=document.querySelector(hash);

if(target&&target.classList.contains("support-faq")){

target.classList.add("is-open");

const question=target.querySelector(".support-question");

if(question){
question.setAttribute("aria-expanded","true");
}

}

}

});