const slides = document.getElementsByClassName('slide');
let currentSlideIndex = 0;

console.log(slides);

//Slider
(()=>{
    const nextButton = document.getElementById('nextButton');
    const beforeButton = document.getElementById('backButton');

    function changeSlideTo(slideIndex) {
        console.log(slideIndex);
        if (slideIndex === currentSlideIndex) {
            return;
        }

        slides[currentSlideIndex].classList.remove('current');

        const slide = slides[slideIndex];

        if (slideIndex > currentSlideIndex) { // going forwards
            slide.style.left = `-${window.innerWidth}px`;
        } else { // going backwards
            slide.style.left = `${window.innerWidth}px`
        }
        setTimeout(() => {
            slide.classList.add('current')
            slide.style.left = "0";
        }, 100);

        currentSlideIndex = slideIndex;
    }

    function onNextButtonClick(_) {
        let slideIndex = currentSlideIndex;
        if (slides.length <= currentSlideIndex+1) {
            slideIndex = 0;
        } else {
            slideIndex++;
        }
        changeSlideTo(slideIndex);
    }

    function onBeforeButtonClick(_) {
        let slideIndex = currentSlideIndex;
        if (currentSlideIndex <= 0) {
            slideIndex = slides.length - 1;
        } else {
            slideIndex--;
        }
        changeSlideTo(slideIndex);
    }

    nextButton.addEventListener('click', onNextButtonClick);

    beforeButton.addEventListener('click', onBeforeButtonClick);
})();