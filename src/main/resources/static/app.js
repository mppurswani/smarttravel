// ==========================================
// CONFIG
// ==========================================
const API_BASE = "https://smarttravel-production-182f.up.railway.app/api";
const TOKEN_KEY = "smarttravel_token";
const USER_KEY = "smarttravel_user";

// ==========================================
// LANGUAGE SUPPORT (ENGLISH ONLY)
// ==========================================
const i18n = {
  en: {
    searchPlaceholder: "Search city name...",
    allCities: "All Cities",
    myFavourites: "❤ My Favourites",
    logout: "Logout",
    login: "Login",
    register: "Register",
    noFavourites: "No favourites yet. Click ❤ on any city to save it.",
    noCities: "No cities found",
    hiddenGem: "Hidden Gem",
    bestTime: "Best Time",
    language: "Language",
    entryFee: "Entry Fee",
    loading: "Loading cities...",
    loginSuccess: "Welcome back!",
    registerSuccess: "Account created!",
    logoutSuccess: "Logged out successfully"
  }
};

let currentLang = "en";
let t = i18n.en;

// ==========================================
// GLOBAL STATE
// ==========================================
let myFavIds = new Set();
let lastCities = [];
let currentCategory = "ALL";
let hiddenGemOnly = false;

// ==========================================
// LANGUAGE
// ==========================================
function setLang(lang, btn) {
  currentLang = "en";
  t = i18n.en;

  document.querySelectorAll(".lang-btn").forEach(b => b.classList.remove("active"));
  if (btn) btn.classList.add("active");

  const searchInput = document.getElementById("searchInput");
  const showAllBtn = document.getElementById("showAllBtn");

  if (searchInput) searchInput.placeholder = t.searchPlaceholder;
  if (showAllBtn) showAllBtn.textContent = t.allCities;

  if (getToken()) {
    const favBtn = document.getElementById("favDashboardBtn");
    const logoutBtn = document.getElementById("logoutBtn");
    if (favBtn) favBtn.textContent = t.myFavourites;
    if (logoutBtn) logoutBtn.textContent = t.logout;
  } else {
    const loginBtn = document.getElementById("openLoginBtn");
    const registerBtn = document.getElementById("openRegisterBtn");
    if (loginBtn) loginBtn.textContent = t.login;
    if (registerBtn) registerBtn.textContent = t.register;
  }
}

// ==========================================
// TOKEN HELPERS
// ==========================================
function getToken() {
  return localStorage.getItem(TOKEN_KEY);
}

function getUser() {
  const u = localStorage.getItem(USER_KEY);
  return u ? JSON.parse(u) : null;
}

function saveAuth(token, username, role) {
  localStorage.setItem(TOKEN_KEY, token);
  localStorage.setItem(USER_KEY, JSON.stringify({ username, role }));
}

function clearAuth() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(USER_KEY);
}

function authHeaders() {
  const token = getToken();
  return token
    ? {
        "Authorization": "Bearer " + token,
        "Content-Type": "application/json"
      }
    : {
        "Content-Type": "application/json"
      };
}

// ==========================================
// UI STATE
// ==========================================
function updateNavUI() {
  const user = getUser();
  const guestBtns = document.getElementById("guestButtons");
  const userPanel = document.getElementById("userPanel");
  const greeting = document.getElementById("userGreeting");

  if (user) {
    if (guestBtns) guestBtns.style.display = "none";
    if (userPanel) userPanel.style.display = "flex";
    if (greeting) greeting.textContent = "Hi, " + user.username;
  } else {
    if (guestBtns) guestBtns.style.display = "flex";
    if (userPanel) userPanel.style.display = "none";
  }
}

// ==========================================
// MODAL HELPERS
// ==========================================
function openModal(id) {
  const el = document.getElementById(id);
  if (el) el.style.display = "flex";
}

function closeModal(id) {
  const el = document.getElementById(id);
  if (el) el.style.display = "none";

  const errId = id === "loginModal" ? "loginError" : "registerError";
  const errEl = document.getElementById(errId);
  if (errEl) errEl.textContent = "";
}

function switchToRegister() {
  closeModal("loginModal");
  openModal("registerModal");
}

function switchToLogin() {
  closeModal("registerModal");
  openModal("loginModal");
}

function closeDashboard() {
  const dashboard = document.getElementById("favDashboard");
  if (dashboard) dashboard.style.display = "none";
}

// Close modal on overlay click
document.addEventListener("click", (e) => {
  ["loginModal", "registerModal", "favDashboard"].forEach(id => {
    const el = document.getElementById(id);
    if (el && e.target === el) {
      el.style.display = "none";
    }
  });
});

// ==========================================
// AUTH — LOGIN
// ==========================================
async function doLogin() {
  const username = document.getElementById("loginUsername").value.trim();
  const password = document.getElementById("loginPassword").value.trim();
  const errEl = document.getElementById("loginError");

  if (!username || !password) {
    errEl.textContent = "Please fill all fields";
    return;
  }

  try {
    const res = await fetch(API_BASE + "/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });

    const data = await res.json();

    if (res.ok && data.token) {
      saveAuth(data.token, data.username, data.role);
      closeModal("loginModal");
      updateNavUI();
      setLang("en");
      showToast(t.loginSuccess + " " + data.username);
      await loadMyFavIds();
      await loadAllCities();
    } else {
      errEl.textContent = data.message || "Invalid credentials";
    }
  } catch (err) {
    errEl.textContent = "Connection error";
    console.error("Login error:", err);
  }
}

// ==========================================
// AUTH — REGISTER
// ==========================================
async function doRegister() {
  const username = document.getElementById("regUsername").value.trim();
  const email = document.getElementById("regEmail").value.trim();
  const password = document.getElementById("regPassword").value.trim();
  const errEl = document.getElementById("registerError");

  if (!username || !email || !password) {
    errEl.textContent = "Please fill all fields";
    return;
  }

  try {
    const res = await fetch(API_BASE + "/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, email, password })
    });

    const data = await res.json();

    if (res.ok && data.token) {
      saveAuth(data.token, data.username, data.role);
      closeModal("registerModal");
      updateNavUI();
      setLang("en");
      showToast(t.registerSuccess);
      await loadMyFavIds();
      await loadAllCities();
    } else {
      errEl.textContent = data.message || "Registration failed";
    }
  } catch (err) {
    errEl.textContent = "Connection error";
    console.error("Register error:", err);
  }
}

// ==========================================
// AUTH — LOGOUT
// ==========================================
function doLogout() {
  clearAuth();
  myFavIds = new Set();
  updateNavUI();
  setLang("en");
  showToast(t.logoutSuccess);
  loadAllCities();
}

// ==========================================
// FAVOURITES
// ==========================================
async function loadMyFavIds() {
  if (!getToken()) {
    myFavIds = new Set();
    return;
  }

  try {
    const res = await fetch(API_BASE + "/favourites", {
      headers: authHeaders()
    });

    if (res.ok) {
      const data = await res.json();
      myFavIds = new Set(data.map(c => c.id));
    } else {
      myFavIds = new Set();
    }
  } catch (err) {
    myFavIds = new Set();
    console.error("Error loading favourites:", err);
  }
}

async function toggleFavourite(cityId, btn) {
  if (!getToken()) {
    openModal("loginModal");
    return;
  }

  const isFav = myFavIds.has(cityId);
  const method = isFav ? "DELETE" : "POST";

  try {
    const res = await fetch(API_BASE + "/favourites/" + cityId, {
      method,
      headers: authHeaders()
    });

    if (res.ok) {
      if (isFav) {
        myFavIds.delete(cityId);
        btn.classList.remove("fav-active");
        btn.textContent = "♡";
      } else {
        myFavIds.add(cityId);
        btn.classList.add("fav-active");
        btn.textContent = "❤";
      }
    } else {
      showToast("Could not update favourite");
    }
  } catch (err) {
    showToast("Error updating favourite");
    console.error("Toggle favourite error:", err);
  }
}

async function openDashboard() {
  if (!getToken()) {
    openModal("loginModal");
    return;
  }

  const favDashboard = document.getElementById("favDashboard");
  const favList = document.getElementById("favList");

  if (favDashboard) favDashboard.style.display = "flex";
  if (favList) favList.innerHTML = "<p class='empty'>Loading...</p>";

  try {
    const res = await fetch(API_BASE + "/favourites", {
      headers: authHeaders()
    });

    if (!res.ok) {
      favList.innerHTML = "<p class='empty'>Error loading favourites</p>";
      return;
    }

    const data = await res.json();

    if (!data || data.length === 0) {
      favList.innerHTML = "<p class='empty'>" + t.noFavourites + "</p>";
      return;
    }

    favList.innerHTML = data.map(city => `
      <div class="fav-item">
        <div class="fav-info">
          <div class="fav-name">${city.name}</div>
          <div class="fav-meta">
            ${city.state} · ${city.country}
            ${city.category ? ' · <span class="cat-pill">' + city.category + '</span>' : ''}
          </div>
        </div>
        <button class="fav-remove" onclick="removeFavAndRefresh(${city.id}, this)">✕</button>
      </div>
    `).join("");
  } catch (err) {
    favList.innerHTML = "<p class='empty'>Error loading favourites</p>";
    console.error("Open dashboard error:", err);
  }
}

async function removeFavAndRefresh(cityId, btn) {
  try {
    const res = await fetch(API_BASE + "/favourites/" + cityId, {
      method: "DELETE",
      headers: authHeaders()
    });

    if (!res.ok) {
      showToast("Error removing favourite");
      return;
    }

    myFavIds.delete(cityId);
    btn.closest(".fav-item").remove();

    const favList = document.getElementById("favList");
    if (favList && !favList.children.length) {
      favList.innerHTML = "<p class='empty'>" + t.noFavourites + "</p>";
    }

    renderCities(lastCities);
  } catch (err) {
    showToast("Error removing favourite");
    console.error("Remove favourite error:", err);
  }
}

// ==========================================
// CITY RENDERING
// ==========================================
function getCategoryEmoji(cat) {
  const map = {
    MOUNTAINS: "⛰",
    BEACHES: "🏖",
    HERITAGE: "🏛",
    RELIGIOUS: "🛕",
    FOOD_STREET: "🍜",
    ADVENTURE: "🧗",
    PARTY: "🎉",
    HIDDEN_GEM: "💎"
  };
  return map[cat] || "📍";
}

function renderCities(cities) {
  lastCities = cities;
  const container = document.getElementById("cityContainer");
  container.innerHTML = "";

  document.getElementById("searchBtn").disabled = false;
  document.getElementById("showAllBtn").disabled = false;

  if (!cities || cities.length === 0) {
    container.innerHTML = "<p class='empty'>" + t.noCities + "</p>";
    return;
  }

  cities.forEach((city, i) => {
    const isFav = myFavIds.has(city.id);
    const isHidden = city.isHiddenGem || city.hiddenGem;

    const card = document.createElement("div");
    card.className = "city-card";
    card.style.animationDelay = (i * 0.05) + "s";

    card.innerHTML = `
      <div class="card-top">
        <div class="card-badges">
          ${city.category ? `<span class="cat-badge">${getCategoryEmoji(city.category)} ${city.category.replaceAll("_", " ")}</span>` : ""}
          ${isHidden ? `<span class="gem-badge">💎 ${t.hiddenGem}</span>` : ""}
        </div>
        <button class="fav-btn ${isFav ? "fav-active" : ""}" onclick="toggleFavourite(${city.id}, this)">
          ${isFav ? "❤" : "♡"}
        </button>
      </div>
      <h3 class="city-name">📍 ${city.name}</h3>
      <p class="city-meta">${city.state} · ${city.country}</p>
      ${city.culture ? `<p class="city-detail"><span class="detail-label">Culture:</span> ${city.culture}</p>` : ""}
      ${city.touristSpots ? `<p class="city-detail"><span class="detail-label">Spots:</span> ${city.touristSpots}</p>` : ""}
      ${city.food ? `<p class="city-detail"><span class="detail-label">Food:</span> ${city.food}</p>` : ""}
      ${city.bestTimeToVisit ? `<p class="city-detail"><span class="detail-label">${t.bestTime}:</span> ${city.bestTimeToVisit}</p>` : ""}
      ${city.language ? `<p class="city-detail"><span class="detail-label">${t.language}:</span> ${city.language}</p>` : ""}
      ${city.entryFee ? `<p class="city-detail"><span class="detail-label">${t.entryFee}:</span> ${city.entryFee}</p>` : ""}
    `;

    container.appendChild(card);
  });
}

// ==========================================
// LOAD CITIES
// ==========================================
function showLoading() {
  document.getElementById("cityContainer").innerHTML = "<p class='empty'>" + t.loading + "</p>";
  document.getElementById("searchBtn").disabled = true;
  document.getElementById("showAllBtn").disabled = true;
}

async function loadAllCities() {
  currentCategory = "ALL";
  hiddenGemOnly = false;
  resetFilterUI();

  showLoading();

  try {
    await loadMyFavIds();
    const res = await fetch(API_BASE + "/cities?page=0&size=50&sortBy=name&sortDir=asc");

    if (!res.ok) throw new Error("Failed to load cities");

    const data = await res.json();
    renderCities(data.content || data);
  } catch (err) {
    console.error("Load all cities error:", err);
    document.getElementById("cityContainer").innerHTML = "<p class='empty'>Error loading cities</p>";
    document.getElementById("searchBtn").disabled = false;
    document.getElementById("showAllBtn").disabled = false;
  }
}

async function searchCities() {
  const keyword = document.getElementById("searchInput").value.trim();

  if (!keyword) {
    loadAllCities();
    return;
  }

  showLoading();

  try {
    await loadMyFavIds();
    const res = await fetch(API_BASE + "/cities/search?name=" + encodeURIComponent(keyword));

    if (!res.ok) throw new Error("Search failed");

    const data = await res.json();
    renderCities(data.content || data);
  } catch (err) {
    console.error("Search error:", err);
    document.getElementById("cityContainer").innerHTML = "<p class='empty'>Error searching cities</p>";
    document.getElementById("searchBtn").disabled = false;
    document.getElementById("showAllBtn").disabled = false;
  }
}

async function loadCitiesByCategory(category) {
  showLoading();

  try {
    await loadMyFavIds();
    const res = await fetch(API_BASE + "/cities/category/" + encodeURIComponent(category));

    if (!res.ok) throw new Error("Category fetch failed");

    const data = await res.json();
    renderCities(data.content || data);
  } catch (err) {
    console.error("Category error:", err);
    document.getElementById("cityContainer").innerHTML = "<p class='empty'>Error loading category</p>";
    document.getElementById("searchBtn").disabled = false;
    document.getElementById("showAllBtn").disabled = false;
  }
}

async function loadHiddenGems() {
  showLoading();

  try {
    await loadMyFavIds();

    let res = await fetch(API_BASE + "/cities/hidden-gems");

    if (!res.ok) {
      const allRes = await fetch(API_BASE + "/cities?page=0&size=100&sortBy=name&sortDir=asc");
      if (!allRes.ok) throw new Error("Hidden gems fetch failed");

      const allData = await allRes.json();
      const allCities = allData.content || allData;
      const filtered = allCities.filter(c => c.isHiddenGem || c.hiddenGem);
      renderCities(filtered);
      return;
    }

    const data = await res.json();
    renderCities(data.content || data);
  } catch (err) {
    console.error("Hidden gems error:", err);
    document.getElementById("cityContainer").innerHTML = "<p class='empty'>Error loading hidden gems</p>";
    document.getElementById("searchBtn").disabled = false;
    document.getElementById("showAllBtn").disabled = false;
  }
}

// ==========================================
// FILTER UI
// ==========================================
function resetFilterUI() {
  document.querySelectorAll(".filter-bar .chip").forEach(btn => btn.classList.remove("active"));
  const allBtn = document.querySelector(".filter-bar .chip");
  if (allBtn) allBtn.classList.add("active");
}

function filterCategory(category, btn) {
  currentCategory = category;
  hiddenGemOnly = false;

  document.querySelectorAll(".filter-bar .chip").forEach(b => b.classList.remove("active"));
  if (btn) btn.classList.add("active");

  if (category === "ALL") {
    loadAllCities();
  } else {
    loadCitiesByCategory(category);
  }
}

function filterHiddenGems(btn) {
  hiddenGemOnly = true;
  currentCategory = "ALL";

  document.querySelectorAll(".filter-bar .chip").forEach(b => b.classList.remove("active"));
  if (btn) btn.classList.add("active");

  loadHiddenGems();
}

// ==========================================
// THEME TOGGLE
// ==========================================
function toggleTheme() {
  const body = document.body;
  body.classList.toggle("dark-mode");

  const isDark = body.classList.contains("dark-mode");
  localStorage.setItem("theme", isDark ? "dark" : "light");

  const themeBtn = document.getElementById("themeToggle");
  if (themeBtn) {
    themeBtn.textContent = isDark ? "☀️" : "🌙";
  }
}

function loadTheme() {
  const theme = localStorage.getItem("theme") || "light";
  const themeBtn = document.getElementById("themeToggle");

  if (theme === "dark") {
    document.body.classList.add("dark-mode");
    if (themeBtn) themeBtn.textContent = "☀️";
  } else {
    document.body.classList.remove("dark-mode");
    if (themeBtn) themeBtn.textContent = "🌙";
  }
}

// ==========================================
// TOAST
// ==========================================
function showToast(msg, duration = 2500) {
  const toast = document.getElementById("toast");
  if (!toast) return;

  toast.textContent = msg;
  toast.classList.add("show");

  setTimeout(() => {
    toast.classList.remove("show");
  }, duration);
}

// ==========================================
// INIT
// ==========================================
document.addEventListener("DOMContentLoaded", async () => {
  loadTheme();

  const searchBtn = document.getElementById("searchBtn");
  const showAllBtn = document.getElementById("showAllBtn");
  const openLoginBtn = document.getElementById("openLoginBtn");
  const openRegisterBtn = document.getElementById("openRegisterBtn");
  const themeToggle = document.getElementById("themeToggle");
  const logoutBtn = document.getElementById("logoutBtn");
  const favDashboardBtn = document.getElementById("favDashboardBtn");
  const searchInput = document.getElementById("searchInput");

  if (searchBtn) searchBtn.addEventListener("click", searchCities);
  if (showAllBtn) showAllBtn.addEventListener("click", loadAllCities);
  if (openLoginBtn) openLoginBtn.addEventListener("click", () => openModal("loginModal"));
  if (openRegisterBtn) openRegisterBtn.addEventListener("click", () => openModal("registerModal"));
  if (themeToggle) themeToggle.addEventListener("click", toggleTheme);
  if (logoutBtn) logoutBtn.addEventListener("click", doLogout);
  if (favDashboardBtn) favDashboardBtn.addEventListener("click", openDashboard);

  if (searchInput) {
    searchInput.addEventListener("keypress", (e) => {
      if (e.key === "Enter") {
        searchCities();
      }
    });
  }

  updateNavUI();
  setLang("en");
  await loadMyFavIds();
  await loadAllCities();
});