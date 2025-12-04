// Menú Hamburguesa
document.addEventListener('DOMContentLoaded', function () {
    const hamburgerBtn = document.querySelector('.hamburger-btn');
    const dropdownMenu = document.querySelector('.dropdown-menu');

    if (hamburgerBtn && dropdownMenu) {
        // Menu al hacer clic en el botón
        hamburgerBtn.addEventListener('click', function (e) {
            e.stopPropagation();
            hamburgerBtn.classList.toggle('active');
            dropdownMenu.classList.toggle('active');
        });

        // Cerrar menú al hacer clic fuera
        document.addEventListener('click', function (e) {
            if (!hamburgerBtn.contains(e.target) && !dropdownMenu.contains(e.target)) {
                hamburgerBtn.classList.remove('active');
                dropdownMenu.classList.remove('active');
            }
        });

        // Cerrar menú al hacer clic en un enlace
        const menuLinks = dropdownMenu.querySelectorAll('a');
        menuLinks.forEach(link => {
            link.addEventListener('click', function () {
                hamburgerBtn.classList.remove('active');
                dropdownMenu.classList.remove('active');
            });
        });
    }
});
