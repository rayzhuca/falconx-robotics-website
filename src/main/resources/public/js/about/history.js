const slides = document.getElementsByClassName('slide');
let currentSlideIndex = 0;

console.log(slides);

//Slider
(() => {
    const nextButton = document.getElementById('next-button');
    const beforeButton = document.getElementById('back-button');
    const pathButtons = document.getElementsByClassName('path');

    function changeSlideTo(slideIndex, direction = 'forward') {
        if (slideIndex === currentSlideIndex) {
            return;
        }

        const lastSlide = slides[currentSlideIndex];
        lastSlide.classList.add('removing');
        lastSlide.classList.remove('current');
        if (direction == "backward") {
            lastSlide.style.left = `-${window.innerWidth}px`;
        } else {
            lastSlide.style.left = `${window.innerWidth}px`
        }

        setTimeout((currentSlideIndex) => {
            if (slideIndex !== currentSlideIndex) {
                lastSlide.classList.remove('removing');
            }
        }, 1000, currentSlideIndex);

        const slide = slides[slideIndex];

        slide.classList.add('current');
        if (direction === "forward") { // going forwards
            slide.style.left = `-${window.innerWidth}px`;
        } else { // going backwards
            slide.style.left = `${window.innerWidth}px`
        }
        setTimeout(() => {
            slide.style.left = "0";
        }, 100);

        currentSlideIndex = slideIndex;
    }

    function onNextButtonClick(_) {
        let slideIndex = currentSlideIndex;
        if (slides.length <= currentSlideIndex + 1) {
            slideIndex = 0;
        } else {
            slideIndex++;
        }
        changeSlideTo(slideIndex, "forward");
    }

    function onBeforeButtonClick(_) {
        let slideIndex = currentSlideIndex;
        if (currentSlideIndex <= 0) {
            slideIndex = slides.length - 1;
        } else {
            slideIndex--;
        }
        changeSlideTo(slideIndex, "backward");
    }

    nextButton.addEventListener('click', onNextButtonClick);
    beforeButton.addEventListener('click', onBeforeButtonClick);

    for (const bln of pathButtons) {
        bln.addEventListener('click', (e) => {
            let slideIndex;
            if (slideIndex = parseInt(e.currentTarget.dataset['slideIndex'])) {
                changeSlideTo(slideIndex);
            }
        })
    }

})();