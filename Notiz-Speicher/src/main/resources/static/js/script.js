function toggleSidebar() {
    let sidebar = document.getElementById("sidebar");
    sidebar.classList.toggle("minimized");
}

// Event-Listener für Fenstergrößenänderung
window.addEventListener('resize', function() {
    var sidebar = document.getElementById('sidebar');
    var sidebarToggleButton = document.getElementById('sidebarToggle');
    var menuItems = document.querySelectorAll('.sidebar ul li');

    // Wenn das Fenster kleiner oder gleich 768px ist, die Sidebar minimieren und Menü-Elemente ausblenden
    if (window.innerWidth <= 1000) {
        sidebar.classList.add('minimized');  // Sidebar minimieren
        sidebarToggleButton.disabled = true; // Toggle-Button deaktivieren
    } else {
        sidebar.classList.remove('minimized');  // Sidebar wiederherstellen
        sidebarToggleButton.disabled = false; // Toggle-Button aktivieren
    }
});
