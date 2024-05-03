document.getElementById('imgBuscada').addEventListener('error', function() {
    console.log("imagen no encontrada");
    const cont_img = document.getElementById('contenedor-imagen');
    cont_img.classList.add('no-encontrada');
});