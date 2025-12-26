const apiUrl = "http://localhost:8080/api/cities";

const searchInput = document.getElementById("searchInput");
const searchBtn = document.getElementById("searchBtn");
const showAllBtn = document.getElementById("showAllBtn");
const cityContainer = document.getElementById("cityContainer");

// Fetch all cities on page load
window.addEventListener("DOMContentLoaded", loadAllCities);

// Search button
searchBtn.addEventListener("click", () => {
    const query = searchInput.value.trim();
    if (!query) {
        loadAllCities();
    } else {
        searchCity(query);
    }
});

// Show All Cities button
showAllBtn.addEventListener("click", loadAllCities);

// Fetch all cities
function loadAllCities() {
    cityContainer.innerHTML = "<p>Loading cities...</p>";
    fetch(apiUrl)
        .then(res => {
            if (!res.ok) throw new Error("Failed to fetch city data");
            return res.json();
        })
        .then(data => renderCities(data))
        .catch(err => {
            console.error(err);
            cityContainer.innerHTML = "<p>Error fetching city data</p>";
        });
}

// Search city by name
function searchCity(name) {
    cityContainer.innerHTML = "<p>Searching...</p>";
    fetch(`${apiUrl}/search?name=${encodeURIComponent(name)}`)
        .then(res => {
            if (!res.ok) throw new Error("Failed to fetch city data");
            return res.json();
        })
        .then(data => renderCities(data))
        .catch(err => {
            console.error(err);
            cityContainer.innerHTML = "<p>Error fetching city data</p>";
        });
}

// Render cities
function renderCities(data) {
    const cities = data.content; // extract array from paginated response
    if (!cities || cities.length === 0) {
        cityContainer.innerHTML = "<p>No cities found.</p>";
        return;
    }

    cityContainer.innerHTML = cities
        .map(city => `
        <div class="city-card">
            <h2>${city.name}</h2>
            <p><strong>State:</strong> ${city.state}</p>
            <p><strong>Country:</strong> ${city.country}</p>
            <p><strong>Culture:</strong> ${city.culture}</p>
            <p><strong>Tourist Spots:</strong> ${city.touristSpots || "N/A"}</p>
            <p><strong>Food:</strong> ${city.food}</p>
        </div>
    `).join("");
}
