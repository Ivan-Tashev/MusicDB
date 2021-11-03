const albumsList = document.getElementById("albumsList");
const searchBar = document.getElementById("searchInput");
const allAlbums = [];

fetch("http://localhost:8080/albums/api")
    .then(response => response.json())
    .then(data => {
        data.forEach(album => allAlbums.push(album))
    })

