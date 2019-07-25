"use strict";

// * Menu
(() => {
    const menuButton = document.getElementById("menu-button");
    const menu = document.getElementById('menu');
    const fadableEles = document.querySelectorAll('#menu *[data-fade]');

    function timeOutLoop(iterable, operation, interval = 15) {
        iterable.forEach(function (v, i, arr) {
            setTimeout(() => operation(v, i, arr), interval * ++i);
        });
    }

    menuButton.addEventListener('click', (_) => {
        menuButton.classList.toggle("change");

        if (menu.classList.contains('on')) {
            //to turn off
            setTimeout(() => {
                if (!menu.classList.contains('on')) {
                    menu.style.display = "none";
                    menu.style.zIndex = -1;

                    fadableEles.forEach((ele) => {
                        ele.classList.remove('on');
                    });
                }
            }, 500);
        } else {
            menu.style.display = null;
            menu.style.zIndex = null;
        }

        setTimeout(() => {
            menu.classList.toggle('on')
            timeOutLoop(fadableEles, (ele) => ele.classList.add('on'));
        }, 100);

    });
})();

// * Parallax
(() => {
    //How to use:
    //data-parallax-slowdown (0<n): as n approaches infinity, parallax effect decreases.
    //data-parallax-static: add attribute (and value cannot be false)
    //                      for program to get y pos from the beginning,
    //                      thus decreasing compute time.


    const parallaxEles = document.getElementsByClassName('parallax');

    for (const ele of parallaxEles) {
        const isStatic = ele.dataset['parallaxStatic'];
        if (isStatic != undefined && isStatic != "false") {
            ele.dataset['parallaxStaticYPos'] = getY(ele);
        }
    }

    function getY(ele) {
        let y = 0;

        if (ele.offsetTop === undefined) { // just in case
            throw new Error('ele does not have offsetTop property');
        }

        do {
            y += (ele.offsetTop - ele.scrollTop + ele.clientTop);
            ele = ele.offsetParent;
        } while (ele);

        return y
    }

    window.addEventListener('scroll', (_) => {
        for (const ele of parallaxEles) {
            //If div is in the screen
            const yCord = parseFloat(ele.dataset['parallaxStaticYPos']) || getY(ele);
            if (window.scrollY + window.innerHeight >= yCord && window.scrollY <= yCord + ele.clientHeight) {
                const rate = parseFloat(ele.dataset['parallaxSlowdown']) || 5;
                ele.style.backgroundPositionY = (window.scrollY - yCord) / rate + "px";
            }
        }
    });
})();