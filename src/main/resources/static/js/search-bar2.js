const albumsList = document.getElementById("albumsList");
const searchBar = document.getElementById("searchInput");
const allAlbums = [];

fetch("http://localhost:8080/albums/api")
    .then(response => response.json())
    .then(data => {
        data.forEach(album => allAlbums.push(album))
    })

searchBar.addEventListener('keyup', (e) => {
    const inputChars = searchBar.value.toLowerCase();
    let filteredAlbums = allAlbums.filter(album => {
        return album.name.toLowerCase().includes(inputChars)
            || album.artistEntity.name.toLowerCase().includes(inputChars)
    });
    displayAlbums(filteredAlbums);
})

const displayAlbums = (albums) => {
    albumsList.innerHTML = albums.map((album) => {
        return `
        <div class="col-md-3">
            <div class="card mb-4 box-shadow">
                <img src="${album.imageUrl}" class="card-img-top" alt="Thumbnail [100%x225]"
                     data-holder-rendered="true"
                     style="height: 225px; width: 100%; display: block;">
                <div class="card-body">
                    <div class="text-center">
                        <p class="card-text border-bottom ">Name: ${album.name}</p>
                        <p class="card-text border-bottom ">Artist: ${album.artistEntity.name}</p>
                        <p class="card-text border-bottom ">Genre: ${album.genre}</p>
                        <p class="card-text border-bottom ">Price: ${album.price}</p>
                        <p class="card-text border-bottom">Release Date: ${album.releaseDate}</p>
                    </div>
                    <div class="d-flex justify-content-between align-items-center">
                        <div class="btn-group">
                            <a href="/albums/details/ + ${album.id}" type="button" class="btn btn-sm btn-outline-secondary">Details</a>
                        </div>
                        <div class="btn-group">
                            <a type="button" class="btn btn-sm btn-outline-secondary">Delete</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        `
    })
}