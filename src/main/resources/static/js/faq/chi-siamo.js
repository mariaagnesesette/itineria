document.addEventListener("DOMContentLoaded",()=>{
const button=document.getElementById("magicButton");
const reveal=document.getElementById("secretReveal");
const box=document.getElementById("secretBox");
if(!button||!reveal||!box)return;

let discovered=false;

button.addEventListener("click",()=>{
reveal.classList.toggle("is-visible");
reveal.setAttribute("aria-hidden",reveal.classList.contains("is-visible")?"false":"true");

if(!discovered){
discovered=true;
button.innerHTML='<span><i class="fa-solid fa-sparkles"></i> Segreto scoperto</span>';
}

const symbols=["✦","✧","★","✦","✧","★","✦","★","✧","✦","★","✧"];

symbols.forEach((symbol,index)=>{
setTimeout(()=>{
const spark=document.createElement("span");
spark.className="magic-spark";
spark.textContent=symbol;
spark.style.left=(10+Math.random()*80)+"%";
spark.style.bottom=(10+Math.random()*15)+"px";
spark.style.animationDelay=(Math.random()*.25)+"s";
box.appendChild(spark);
setTimeout(()=>spark.remove(),2200);
},index*55);
});
});
});