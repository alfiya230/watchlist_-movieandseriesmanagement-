document.addEventListener('DOMContentLoaded', () => {
    const movieForm = document.getElementById('addMovieForm');
    const movieGrid = document.querySelector('.movie-grid');
    const searchInput = document.querySelector('.toolbar input[placeholder*="Search"]');

    // 1. Initial Data Fetch on Page Load
    fetchMovies();

    // 2. Handle Form Submission (Add Movie)
    if (movieForm) {
        movieForm.addEventListener('submit', function(event) {
            event.preventDefault();

            // Match your frontend input names
            const title = document.getElementById('movieTitle').value;
            const genre = document.getElementById('movieGenre').value;
            const duration = parseInt(document.getElementById('movieDuration').value, 10);
            const rating = parseFloat(document.getElementById('movieRating').value);
            const releaseYear = parseInt(document.getElementById('movieReleaseYear').value, 10);

            const moviePayload = {
                title: title,
                genre: genre,
                rating: rating,
                releaseYear: releaseYear,
                duration: duration
            };

            fetch('http://localhost:8080/watchlist-app/MovieServlet', { 
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(moviePayload)
            })
            .then(async (response) => {
                const data = await response.json();
                if (response.ok) {
                    alert('Success: ' + data.message);
                    movieForm.reset(); 
                    fetchMovies(); // Reload layout without full refresh
                } else {
                    alert('Server Warning: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Connection Error:', error);
                alert('Connection failure: Cannot reach Tomcat backend.');
            });
        });
    }

    // 3. Fetch Movies and Update UI Layout
    function fetchMovies() {
        fetch('http://localhost:8080/watchlist-app/MovieServlet?action=list')
            .then(res => res.json())
            .then(movies => {
                renderMovies(movies);
                updateStats(movies);
            })
            .catch(err => console.error('Failed to pull watchlist database rows:', err));
    }

    // 4. Render Dynamic Cards Matching HTML Aesthetics
    function renderMovies(movies) {
        if (!movieGrid) return;
        movieGrid.innerHTML = '';

        if (!movies || movies.length === 0) {
            movieGrid.innerHTML = '<p style="grid-column: 1/-1; color: var(--movie-muted);">No movies found in your collection.</p>';
            return;
        }

        movies.forEach(movie => {
            const card = document.createElement('div');
            card.className = 'movie-card';
            card.innerHTML = `
                <div class="movie-card-top">
                    <div class="movie-icon">🎬</div>
                    <span class="rating">⭐ ${movie.rating.toFixed(1)}</span>
                </div>
                <h3>${movie.title}</h3>
                <span class="genre-tag">${movie.genre}</span>
                <div class="movie-details">
                    <span>Year: <strong>${movie.releaseYear}</strong></span>
                    <span>Duration: <strong>${movie.duration} mins</strong></span>
                </div>
            `;
            movieGrid.appendChild(card);
        });
    }

    // 5. Update Total/Average Statistics Automatically
    function updateStats(movies) {
        const totalCard = document.querySelector('.stats-grid .stat-card:nth-child(1) .stat-value');
        const avgRatingCard = document.querySelector('.stats-grid .stat-card:nth-child(2) .stat-value');
        const watchtimeCard = document.querySelector('.stats-grid .stat-card:nth-child(3) .stat-value');

        if (totalCard) totalCard.textContent = movies.length;
        
        if (avgRatingCard && movies.length > 0) {
            const totalRating = movies.reduce((sum, m) => sum + m.rating, 0);
            avgRatingCard.textContent = (totalRating / movies.length).toFixed(1);
        } else if (avgRatingCard) {
            avgRatingCard.textContent = '0.0';
        }

        if (watchtimeCard) {
            const totalMinutes = movies.reduce((sum, m) => sum + m.duration, 0);
            watchtimeCard.textContent = totalMinutes + 'm';
        }
    }
});
