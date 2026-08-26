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

target.scrollIntoView({behavior:"smooth",block:"start"});

if(target.classList.contains("support-faq")){
target.classList.add("is-open");
const question=target.querySelector(".support-question");
if(question)question.setAttribute("aria-expanded","true");
}
});
});

document.querySelectorAll(".support-question").forEach(question=>{
question.addEventListener("click",()=>{
const faq=question.closest(".support-faq");
const open=faq.classList.toggle("is-open");
question.setAttribute("aria-expanded",open?"true":"false");
});
});

const form=document.getElementById("supportForm");
const message=document.getElementById("supportFormMessage");

if(form){
form.addEventListener("submit",e=>{
e.preventDefault();

const nome=document.getElementById("nome");
const email=document.getElementById("email");
const oggetto=document.getElementById("oggetto");
const messaggio=document.getElementById("messaggio");

if(!nome.value.trim()||!email.value.trim()||!oggetto.value||!messaggio.value.trim()){
message.textContent="Compila tutti i campi prima di continuare.";
return;
}

if(!email.checkValidity()){
message.textContent="Inserisci un indirizzo email valido.";
return;
}

message.textContent="Modulo compilato correttamente.";
});
}

const hash=window.location.hash;
if(hash){
const target=document.querySelector(hash);
if(target&&target.classList.contains("support-faq")){
target.classList.add("is-open");
const question=target.querySelector(".support-question");
if(question)question.setAttribute("aria-expanded","true");
}
}

});