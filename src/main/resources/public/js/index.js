"use strict";

// * Menu
(() => {
    const menuButton = document.getElementById("menu-button");
    const menu = document.getElementById('menu');

    menuButton.addEventListener('click', (_) => {
        menuButton.classList.toggle("change");

        if (menu.classList.contains('on')) {
            //to turn off
            setTimeout(() => {
                if (!menu.classList.contains('on')) {
                    menu.style.display = "none";
                    menu.style.zIndex = -1;
                }
            }, 500);
        } else {
            menu.style.display = null;
            menu.style.zIndex = null;
        }

        setTimeout(() => menu.classList.toggle('on'), 100);
    });
})();