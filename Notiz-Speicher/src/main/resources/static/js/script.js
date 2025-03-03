function toggleSidebar() {
    let sidebar = document.getElementById("sidebar");
    let logo = document.getElementById("logo");

    // Sidebar nur umschalten, wenn das Fenster größer als 1000px ist
    if (window.innerWidth > 1000) {
        sidebar.classList.toggle("minimized");
        // Logo ändern je nach Sidebar-Status
        if (sidebar.classList.contains("minimized")) {
            logo.src = "/img/thowl_logo_short.png"; // Minimiertes Logo
            logo.classList.add("minimized");        // Minimiertes Styling
        } else {
            logo.src = "/img/thowl_logo.png";       // Originales Logo
            logo.classList.remove("minimized");     // Normales Styling
        }
    }
}

// Event-Listener für Fenstergrößenänderung
window.addEventListener("resize", handleResize);

// Initial aufrufen, um die richtige Einstellung zu setzen
handleResize();

function handleResize() {
    let sidebar = document.getElementById("sidebar");
    let logo = document.getElementById("logo");
    let sidebarToggleButton = document.getElementById("sidebarToggle");

    if (window.innerWidth <= 1000) {
        sidebar.classList.add("minimized");     // Sidebar minimieren
        sidebarToggleButton.disabled = true;    // Toggle-Button deaktivieren
        logo.src = "/img/thowl_logo_short.png"; // Minimiertes Logo
        logo.classList.add("minimized");        // Minimiertes Styling
    } else {
        sidebar.classList.remove("minimized");  // Sidebar wiederherstellen
        sidebarToggleButton.disabled = false;   // Toggle-Button aktivieren
        // Logo basierend auf Sidebar-Status setzen
        if (sidebar.classList.contains("minimized")) {
            logo.src = "/img/thowl_logo_short.png"; // Minimiertes Logo
            logo.classList.add("minimized");
        } else {
            logo.src = "/img/thowl_logo.png";       // Originales Logo
            logo.classList.remove("minimized");
        }
    }
}
