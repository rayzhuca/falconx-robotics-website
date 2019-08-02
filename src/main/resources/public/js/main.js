"use strict";

// global for fade in and parallax
function getY(ele) {
	let y = 0;

	if (ele.offsetTop === undefined) {
		// just in case
		throw new Error("ele does not have offsetTop property");
	}

	do {
		y += ele.offsetTop - ele.scrollTop + ele.clientTop;
		ele = ele.offsetParent;
	} while (ele);

	return y;
}

// global for footer and menu
// async
function timeOutLoop(iterable, operation, interval = 10) {
	// return new Promise((res, err) => {
	Array.prototype.forEach.call(iterable, function (v, i, arr) {
		setTimeout(() => {
			operation(v, i, arr);

			if (arr.length === i) {
				// res(arr);
			}
		}, interval * ++i);
	});
	// });
}

// * Menu
(() => {
	const menuButton = document.getElementById("menu-button");
	const menu = document.getElementById("menu");
	const fadableEles = document.getElementsByClassName("fade");
	const header = document.getElementsByTagName("header")[0];

	let debounce = false;

	menuButton.addEventListener("click", _ => {
		if (debounce) return;
		debounce = true;

		menuButton.classList.toggle("change");

		if (menu.classList.contains("on")) {
			// turn off
			document.documentElement.classList.remove("scroll-lock");
			document.body.classList.remove("scroll-lock");
			header.style.backgroundColor = null;

			setTimeout(() => {
				if (!menu.classList.contains("on")) {
					// so it won't turn off when user spams button
					menu.style.display = "none";
					menu.style.zIndex = -1;

					Array.prototype.forEach.call(fadableEles, ele => {
						console.log(ele.classList);
						ele.classList.remove("on");
						console.log(ele.classList);
					});
				}
			}, 500);
		} else {
			// turn on
			document.documentElement.classList.add("scroll-lock");
			document.body.classList.add("scroll-lock");
			menu.style.display = null;
			menu.style.zIndex = null;
			header.style.backgroundColor = "var(--background-color)";
		}

		setTimeout(() => {
			menu.classList.toggle("on");

			if (menu.classList.contains("on")) {
				menu.style.display = null; // just in case
				menu.style.zIndex = null;
				timeOutLoop(fadableEles, ele => ele.classList.add("on"));
			}

			debounce = false;
		}, 100);
	});
})();

// * Parallax
(() => {
	// How to use (all units in px):
	// data-parallax-slowdown: as n approaches infinity, parallax effect decreases
	// data-parallax-static: add attribute (and value cannot be false)
	//                       for program to get y pos from the beginning,
	//                       thus decreasing compute time
	// data-parallax-translate: translates ele instead of backgroundPositionY
	// data-parallax-reverse: reverses parallax effect
	// data-parallax-middle: (made for parallax eles on the front page)
	//                       initializes div before start (this will not result of a parabola motion)
	// data-parallax-offset: self-explainatory

	const parallaxEles = document.getElementsByClassName("parallax");

	for (const ele of parallaxEles) {
		const isStatic = ele.dataset["parallaxStatic"];
		const isMiddle = ele.dataset["parallaxMiddle"];
		if ((isStatic !== undefined && isStatic != "false") || (isMiddle !== undefined && isMiddle != "false")) {
			ele.dataset["parallaxStaticYPos"] = getY(ele);
		}

		if (isMiddle !== undefined) {
			const rate = (parseInt(ele.dataset["parallaxSlowdown"]) || 5) * (ele.dataset["parallaxReverse"] === undefined ? 1 : -1);
			const y = ((parseInt(ele.dataset["parallaxOffset"]) || 0) + (window.scrollY - parseInt(ele.dataset["parallaxStaticYPos"]) || getY(ele))) / rate;

			if (ele.dataset["parallaxTranslate"] !== undefined) {
				ele.style.transform = `translate(0, ${y}px)`;
			} else {
				ele.style.backgroundPositionY = `${y}px`;
			}
		}
	}

	window.addEventListener("scroll", _ => {
		for (const ele of parallaxEles) {
			//If div is in the screen
			const yCord = parseInt(ele.dataset["parallaxStaticYPos"]) || getY(ele);
			if (
				window.scrollY + window.innerHeight >= yCord &&
				window.scrollY <= yCord + ele.clientHeight
			) {
				const rate = (parseInt(ele.dataset["parallaxSlowdown"]) || 5) * (ele.dataset["parallaxReverse"] === undefined ? 1 : -1);
				// the difference from screen to div + offset + more offset if parallax-middle
				// const y = (window.scrollY - yCord + (parseInt(ele.dataset["parallaxOffset"]) || 0) + (ele.dataset["parallaxMiddle"] !== undefined ? (parseInt(ele.dataset["parallaxStaticYPos"]) || getY(ele)) : 0)) / rate;
				const y = (window.scrollY - yCord + (parseInt(ele.dataset["parallaxOffset"]) || 0)) / rate;


				// console.log(window.scrollY - yCord)
				// console.log((parseInt(ele.dataset["parallaxOffset"]) || 0));
				// console.log((ele.dataset["parallaxMiddle"] !== undefined ? (parseInt(ele.dataset["parallaxStaticYPos"]) || getY(ele)) : 0));


				if (ele.dataset["parallaxTranslate"] !== undefined) {
					ele.style.transform = `translate(0, ${y}px)`;
				} else {
					ele.style.backgroundPositionY = `${y}px`;
				}
			}
		}
	});
})();

// * Header
// hide header on scroll down; unhide header on scroll up
(() => {
	const header = document.getElementsByTagName("header")[0];
	const menu = document.getElementById("menu");
	let lastScrollY;

	window.addEventListener("scroll", _ => {
		// make sure menu don't hide when menu is open
		if (menu.classList.contains("on")) {
			header.style.top = "0";

			return;
		} else if (lastScrollY > window.scrollY) {
			header.style.top = "0";
		} else {
			header.style.top = "calc(var(--header-height) * -2)";
		}

		lastScrollY = window.scrollY;
	});
})();

// * Fade in & footer
setTimeout(() => {
	const footer = document.getElementsByClassName("footer"); // ! footer can be undefined (make sure I include that in code)
	let fadeEles = document.getElementsByClassName("fade-in");

	function fadeIn(ele) {
		if (ele.classList.contains("in-footer")) return;

		ele.classList.add("to-fade");
		// setTimeout(() =>
		ele.classList.remove("fade-in");
		// , 100);

		for (const eleClass of ele.classList) {
			if (eleClass.startsWith("fade-in")) {
				ele.classList.remove(eleClass);
			}
		}

		setTimeout(() => {
			ele.classList.remove("to-fade");
		}, 700);
	}

	function isClose(num1, num2, range) {
		// num1, num2 = parseInt(num1), parseInt(num2);

		// n1: 9, n2: 10
		// 9 + 5/2 > 10
		// 9 - 5/2 < 10

		return num1 + range / 2 >= num2 && num1 - range / 2 <= num2;
	}

	// function isInFooter(ele) {
	// 	let parent = ele.parentElement;
	// 	while (parent && parent !== document.body) {
	// 		if (parent.tagName === 'FOOTER') {
	// 			return true;
	// 		}
	// 		ele = parent;
	// 		parent = ele.parentElement;
	// 	}
	// 	return false;
	// }

	function animateFooter() {
		const footerFadeEles = document.querySelectorAll("footer *.fade-in");

		timeOutLoop(
			footerFadeEles,
			v => {
				fadeIn(v);
			},
			150
		);
	}

	function onScroll(_) {
		for (let i = 0; i < fadeEles.length; i++) {
			// ele fades in when scrolled to half of the screen + div y pos
			if (getY(fadeEles[i]) - (1 / 2) * window.innerHeight < window.scrollY) {
				fadeIn(fadeEles[i]);
			}
		}

		if (
			footer &&
			isClose(
				window.scrollY + window.innerHeight,
				document.documentElement.scrollHeight,
				400
			)
		) {
			animateFooter();
		}
	}

	setTimeout(() => {
		// timeout to fix the weirdest fucking bug
		for (let i = 0; i < fadeEles.length; i++) {
			onScroll();
		}
	}, 500);

	window.addEventListener("scroll", onScroll);
}, 1500);