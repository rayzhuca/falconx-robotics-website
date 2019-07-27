'use strict';

function getY(ele) { // global for fade in and parallax
    let y = 0;

    if (ele.offsetTop === undefined) { // just in case
        throw new Error('ele does not have offsetTop property');
    }

    do {
        y += (ele.offsetTop - ele.scrollTop + ele.clientTop);
        ele = ele.offsetParent;
    } while (ele);

    return y;
}

// * Menu
(() => {
    const menuButton = document.getElementById('menu-button');
    const menu = document.getElementById('menu');
    const fadableEles = document.getElementsByClassName('fade');
    const header = document.getElementsByTagName('header')[0];

    function timeOutLoop(iterable, operation, interval = 10) {
        Array.prototype.forEach.call(iterable, function (v, i, arr) {
            console.log(interval * ++i);
            setTimeout(() => {
                console.log('yes');
                operation(v, i, arr)
            }, interval * ++i);
        });
    }

    let debounce = false;

    menuButton.addEventListener('click', (_) => {
        if (debounce) return;
        debounce = true;

        menuButton.classList.toggle('change');

        if (menu.classList.contains('on')) {
            // turn off
            document.documentElement.classList.remove('scroll-lock');
            document.body.classList.remove('scroll-lock');
            header.style.backgroundColor = null;


            setTimeout(() => {
                if (!menu.classList.contains('on')) { // so it won't turn off when user spams button
                    menu.style.display = 'none';
                    menu.style.zIndex = -1;

                    Array.prototype.forEach.call(fadableEles, (ele) => {
                        console.log(ele.classList);
                        ele.classList.remove('on');
                        console.log(ele.classList);
                    });
                }
            }, 500);
        } else {
            // turn on
            document.documentElement.classList.add('scroll-lock');
            document.body.classList.add('scroll-lock');
            menu.style.display = null;
            menu.style.zIndex = null;
            header.style.backgroundColor = 'white';
        }

        setTimeout(() => {
            menu.classList.toggle('on')

            if (menu.classList.contains('on')) {
                menu.style.display = null; // just in case
                menu.style.zIndex = null;
                timeOutLoop(fadableEles, (ele) => ele.classList.add('on'));
            }

            debounce = false;
        }, 100);
    });
})();

// * Parallax
(() => {
    // How to use:
    // data-parallax-slowdown: as n approaches infinity, parallax effect decreases.
    // data-parallax-static: add attribute (and value cannot be false)
    //                       for program to get y pos from the beginning,
    //                       thus decreasing compute time.


    const parallaxEles = document.getElementsByClassName('parallax');

    for (const ele of parallaxEles) {
        const isStatic = ele.dataset['parallaxStatic'];
        if (isStatic != undefined && isStatic != 'false') {
            ele.dataset['parallaxStaticYPos'] = getY(ele);
        }
    }

    window.addEventListener('scroll', (_) => {
        for (const ele of parallaxEles) {
            //If div is in the screen
            const yCord = parseFloat(ele.dataset['parallaxStaticYPos']) || getY(ele);
            if (window.scrollY + window.innerHeight >= yCord && window.scrollY <= yCord + ele.clientHeight) {
                const rate = parseFloat(ele.dataset['parallaxSlowdown']) || 5;
                ele.style.backgroundPositionY = (window.scrollY - yCord) / rate + 'px';
            }
        }
    });
})();

// * Header
// hide header on scroll down; unhide header on scroll up
(() => {
    const header = document.getElementsByTagName('header')[0];
    const menu = document.getElementById('menu');
    let lastScrollY;

    window.addEventListener('scroll', (_) => {
        // make sure menu don't hide when menu is open
        if (menu.classList.contains('on')) {
            header.style.top = '0';

            return;
        } else if (lastScrollY > window.scrollY) {
            header.style.top = '0';
        } else {
            header.style.top = 'calc(var(--header-height) * -2)';
        }

        lastScrollY = window.scrollY;
    });

})();

// * Fade in & footer
(() => {
    const footer = document.getElementsByClassName('footer'); // ! footer can be undefined (make sure I include that in code) 
    let fadeEles = document.getElementsByClassName('fade-in');

    function fadeIn(ele) {
        if (getY(ele) - ((1 / 2) * window.innerHeight) < window.scrollY) { // so ele fades in when scrolled to half of the screen + div y pos 
            ele.classList.add('to-fade');
            ele.classList.remove('fade-in');

            for (const eleClass of ele.classList) {
                if (eleClass.startsWith('fade-in')) {
                    ele.classList.remove(eleClass);
                }
            }

            setTimeout(() => {
                ele.classList.remove('to-fade');
            }, 1000);
        }
    }

    function onScroll(_) {
        for (let i = 0; i < fadeEles.length; i++) {
            fadeIn(fadeEles[i]);
        }
    }

    setTimeout(() => { // timeout to fix the weirdest fucking bug
        for (let i = 0; i < fadeEles.length; i++) {
            onScroll();
        }
    }, 500);


    window.addEventListener('scroll', onScroll);

    // window.addEventListener('load', onSCroll);

})();