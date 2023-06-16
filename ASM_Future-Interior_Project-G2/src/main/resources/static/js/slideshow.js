const slider = document.querySelector('.slider');
    const prevBtn = document.querySelector('.prev-btn');
    const nextBtn = document.querySelector('.next-btn');
    
    const slideWidth = slider.querySelector('.slideproduct').offsetWidth;
    let currentPosition = 0;
    
    prevBtn.addEventListener('click', () => {
      currentPosition += slideWidth;
      if (currentPosition > 0) {
        currentPosition = -(slideWidth * (slider.childElementCount - 1));
      }
      slider.style.transform = `translateX(${currentPosition}px)`;
    });
    
    nextBtn.addEventListener('click', () => {
      currentPosition -= slideWidth;
      if (currentPosition < -(slideWidth * (slider.childElementCount - 1))) {
        currentPosition = 0;
      }
      slider.style.transform = `translateX(${currentPosition}px)`;
    });