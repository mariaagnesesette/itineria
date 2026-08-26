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

document.addEventListener("DOMContentLoaded", () => {
  const openBtn = document.getElementById("openEasterEgg");
  const closeBtn = document.getElementById("closeEasterEgg");
  const modal = document.getElementById("easterEggModal");
  const magicBtn = document.getElementById("playMagicBtn");
  const confettiBox = document.getElementById("easterEggConfetti");
  const eggBox = document.getElementById("easterEggBox");
  const imageWrap = document.querySelector(".easter-egg-image-wrap");

  if (!openBtn || !closeBtn || !modal) return;

  function launchConfetti() {
    const colors = ["#c85a32", "#e27a4d", "#223625", "#4f6b57", "#f0c36d", "#ffffff"];

    for (let i = 0; i < 36; i++) {
      const piece = document.createElement("span");
      piece.className = "confetti-piece";
      piece.style.left = Math.random() * 100 + "%";
      piece.style.background = colors[Math.floor(Math.random() * colors.length)];
      piece.style.animationDuration = (1000 + Math.random() * 900) + "ms";
      piece.style.animationDelay = (Math.random() * 200) + "ms";
      piece.style.transform = `translateY(-40px) rotate(${Math.random() * 360}deg)`;
      confettiBox.appendChild(piece);

      setTimeout(() => {
        piece.remove();
      }, 2200);
    }

    eggBox.classList.remove("party");
    imageWrap.classList.remove("party");

    void eggBox.offsetWidth;
    void imageWrap.offsetWidth;

    eggBox.classList.add("party");
    imageWrap.classList.add("party");
  }

  function openModal() {
    modal.classList.add("is-open");
    document.body.style.overflow = "hidden";
    launchConfetti();
  }

  function closeModal() {
    modal.classList.remove("is-open");
    document.body.style.overflow = "";
  }

  openBtn.addEventListener("click", openModal);
  closeBtn.addEventListener("click", closeModal);

  modal.addEventListener("click", (e) => {
    if (e.target.classList.contains("easter-egg-backdrop")) {
      closeModal();
    }
  });

  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") {
      closeModal();
    }
  });

  if (magicBtn) {
    magicBtn.addEventListener("click", launchConfetti);
  }
});