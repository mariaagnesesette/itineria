document.addEventListener("DOMContentLoaded",()=>{

const openBtn=document.getElementById("openEasterEgg");
const closeBtn=document.getElementById("closeEasterEgg");
const modal=document.getElementById("easterEggModal");
const backdrop=document.getElementById("easterEggBackdrop");

const revealClaudioBtn=document.getElementById("revealClaudioBtn");
const claudioReveal=document.getElementById("claudioReveal");

const revealMemeBtn=document.getElementById("revealMemeBtn");
const memeReveal=document.getElementById("easterEggMemeReveal");

const magicBtn=document.getElementById("playMagicBtn");
const confettiBox=document.getElementById("easterEggConfetti");
const eggBox=document.getElementById("easterEggBox");
const imageWrap=document.querySelector(".easter-egg-image-wrap");
const caption=document.getElementById("easterEggCaption");

if(!openBtn||!closeBtn||!modal||!eggBox)return;

const captions=[
"Quattro persone, centinaia di righe di codice, bug ovunque e una quantità non dichiarata di pazienza.",
"Quando finalmente funziona e nessuno osa più toccare il codice.",
"Prima il debug. Poi gli applausi.",
"Errore 500? Non sappiamo di cosa stai parlando.",
"Funziona. Non sappiamo perché. Non tocchiamo più niente.",
"Il vero progetto erano i bug che abbiamo risolto lungo il percorso."
];

function launchConfetti(){
if(!confettiBox)return;

const colors=["#c85a32","#e27a4d","#223625","#4f6b57","#f0c36d","#ffffff"];

for(let i=0;i<55;i++){
const piece=document.createElement("span");

piece.className="confetti-piece";
piece.style.left=Math.random()*100+"%";
piece.style.top="-20px";
piece.style.background=colors[Math.floor(Math.random()*colors.length)];
piece.style.animationDuration=(1000+Math.random()*1000)+"ms";
piece.style.animationDelay=(Math.random()*150)+"ms";

confettiBox.appendChild(piece);

setTimeout(()=>{
piece.remove();
},2300);
}

eggBox.classList.remove("party");
void eggBox.offsetWidth;
eggBox.classList.add("party");

if(imageWrap){
imageWrap.classList.remove("party");
void imageWrap.offsetWidth;
imageWrap.classList.add("party");
}
}

function openModal(){
modal.classList.add("is-open");
modal.setAttribute("aria-hidden","false");
document.body.style.overflow="hidden";

eggBox.scrollTop=0;

launchConfetti();
}

function closeModal(){
modal.classList.remove("is-open");
modal.setAttribute("aria-hidden","true");
document.body.style.overflow="";
}

openBtn.addEventListener("click",openModal);

closeBtn.addEventListener("click",closeModal);

if(backdrop){
backdrop.addEventListener("click",closeModal);
}

document.addEventListener("keydown",e=>{
if(e.key==="Escape"&&modal.classList.contains("is-open")){
closeModal();
}
});

/* =========================
   QUINTO MOSCHETTIERE
   ========================= */

if(revealClaudioBtn&&claudioReveal){

revealClaudioBtn.addEventListener("click",()=>{

const visible=claudioReveal.classList.toggle("is-visible");

claudioReveal.setAttribute(
"aria-hidden",
visible?"false":"true"
);

revealClaudioBtn.innerHTML=visible
?'<i class="fa-solid fa-hands-praying"></i> Quinto moschettiere rivelato'
:'<i class="fa-solid fa-eye"></i> Svela il quinto moschettiere';

if(visible){

launchConfetti();

setTimeout(()=>{
eggBox.scrollTo({
top:claudioReveal.offsetTop-20,
behavior:"smooth"
});
},350);

}

});

}

/* =========================
   MEME DIETRO LE QUINTE
   ========================= */

if(revealMemeBtn&&memeReveal){

revealMemeBtn.addEventListener("click",()=>{

const visible=memeReveal.classList.toggle("is-visible");

memeReveal.setAttribute(
"aria-hidden",
visible?"false":"true"
);

revealMemeBtn.innerHTML=visible
?'<i class="fa-solid fa-lock-open"></i> Dietro le quinte sbloccato'
:'<i class="fa-solid fa-lock"></i> Svela il vero dietro le quinte';

if(visible){

launchConfetti();

setTimeout(()=>{
eggBox.scrollTo({
top:memeReveal.offsetTop-20,
behavior:"smooth"
});
},350);

}

});

}

/* =========================
   FESTEGGIAMO ANCORA
   ========================= */

if(magicBtn){

magicBtn.addEventListener("click",()=>{

launchConfetti();

if(caption){
caption.textContent=
captions[Math.floor(Math.random()*captions.length)];
}

});

}

});