"use strict";

(()=>{
    const menuButton = document.getElementById("menu-button");
    const menu = document.getElementById('menu');

    menuButton.addEventListener('click', (_) => {
        menuButton.classList.toggle("change");
        menu.classList.toggle('on');
        if (!menu.classList.contains('on')) {
            setTimeout(() => {
                menu.classList.add('off')
            }, 200);
        } else {
            menu.classList.remove('off')
        }
    });
})();