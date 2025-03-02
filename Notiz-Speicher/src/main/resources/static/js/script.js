function toggleSidebar() {
    let sidebar = document.getElementById("sidebar");

    // Sidebar nur umschalten, wenn das Fenster größer als 1000px ist
    if (window.innerWidth > 1000) {
        sidebar.classList.toggle("minimized");
    }
}
// Event-Listener für Fenstergrößenänderung
window.addEventListener("resize", handleResize);

// Initial aufrufen, um die richtige Einstellung zu setzen
handleResize();

function handleResize() {
    let sidebar = document.getElementById("sidebar");
    let sidebarToggleButton = document.getElementById("sidebarToggle");

    if (window.innerWidth <= 1000) {
        sidebar.classList.add("minimized");  // Sidebar minimieren
        sidebarToggleButton.disabled = true; // Toggle-Button deaktivieren
    } else {
        sidebar.classList.remove("minimized");  // Sidebar wiederherstellen
        sidebarToggleButton.disabled = false; // Toggle-Button aktivieren
    }
}
