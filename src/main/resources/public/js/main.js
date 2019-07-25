"use strict";

// * Menu
(() => {
    const menuButton = document.getElementById("menu-button");
    const menu = document.getElementById('menu');
    const fadableEles = document.getElementsByClassName('fade');

    function timeOutLoop(iterable, operation, interval = 15) {
        Array.prototype.forEach.call(iterable, function (v, i, arr) {
            setTimeout(() => operation(v, i, arr), interval * ++i);
        });
    }

    menuButton.addEventListener('click', (_) => {
        menuButton.classList.toggle("change");

        if (menu.classList.contains('on')) {
            //to turn off
            setTimeout(() => {
                if (!menu.classList.contains('on')) { // so it won't turn off when user spams button
                    menu.style.display = "none";
                    menu.style.zIndex = -1;

                    Array.prototype.forEach.call(fadableEles, (ele) => {
                        console.log(ele.classList);
                        ele.classList.remove('on');
                        console.log(ele.classList);
                    });
                }
            }, 500);
        } else {
            menu.style.display = null;
            menu.style.zIndex = null;
        }

        setTimeout(() => {
            menu.classList.toggle('on')

            if (menu.classList.contains('on')) {
                timeOutLoop(fadableEles, (ele) => ele.classList.add('on'));
            }
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

// * Header
(() => {
    const header = document.getElementsByTagName('header')[0];
    let lastScrollY;

    window.addEventListener('scroll', (_) => {
        // if (window.scrollY <= window.innerHeight) {
        //     header.style.position = "absolute";
        //     header.style.top = "0";
        //     return;
        // } else if (header.style.position === "absolute") {
        //     header.style.position = "fixed";
        //     header.style.top = "calc(var(--header-height) * -2)";
        // }

        if (lastScrollY > window.scrollY) {
            header.style.top = "0";
        } else {
            header.style.top = "calc(var(--header-height) * -2)";
        }

        lastScrollY = window.scrollY;
    });

})();