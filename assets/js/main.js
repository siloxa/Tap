var lastId,
 topMenu = $(".header-menu"),
 topMenuHeight = topMenu.outerHeight()+1,
 menuItems = topMenu.find("a"),
 scrollItems = menuItems.map(function(){
   var item = $($(this).attr("href"));
    if (item.length) { return item; }
 });

$(window).scroll(function(){
    var fromTop = $(this).scrollTop()+topMenuHeight;
    var cur = scrollItems.map(function(){
      if ($(this).offset().top - 400 < fromTop)
        return this;
    });
    cur = cur[cur.length-1];
    var id = cur && cur.length ? cur[0].id : "";
    
    if (lastId !== id) {
        lastId = id;
        menuItems
          .parent().removeClass("active")
          .end().filter(function(index) {
                 return $(this).attr("href") === "#" + id;
               }).parent().addClass("active");
    }                   
 });

const downloadButton = document.querySelector(".download-button-icon");
const downloadMenu = document.querySelector(".download-menu");

downloadButton.addEventListener("click", activeDownloadMenu);

function activeDownloadMenu() {
    downloadMenu.classList.toggle("active");
}

window.onclick = function(event) {
    if (
        !event.target.classList.contains("download-button-icon") 
        && !event.target.classList.contains("download-item")
        && downloadMenu.classList.contains("active")
      ) {
        activeDownloadMenu();
    }
}

 function download() {
    const a = document.createElement('a')
    a.href = "#download";
    document.body.appendChild(a)
    a.click()
    document.body.removeChild(a)
}