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
function confirmLogout() {
    return confirm("Möchtest du dich wirklich abmelden?");
}
document.querySelectorAll('.btn-danger').forEach(button => {
    button.addEventListener('click', function() {
        const noteId = this.getAttribute('data-id');
        document.querySelector('#deleteModal form').setAttribute('action', `/user/notes/${noteId}/delete`);
    });
});

document.getElementById('addNoteBtn').addEventListener('click', function() {
    const modal = document.getElementById('noteModal');
    if (modal) {
        modal.style.display = 'block';
    }
});
function closeModal() {
    const modal = document.getElementById('noteModal');
    if (modal) {
        modal.style.display = 'none';
    }
}
document.querySelectorAll('.grid-item').forEach(item => {
    item.addEventListener('click', function () {
        const id = this.getAttribute('data-id');
        const name = this.getAttribute('data-name');
        openCategoryModal(id, name);
    });
});

function openCategoryModal(id, name) {
    console.log(`Modal geöffnet für ID: ${id}, Name: ${name}`);

    const modal = document.getElementById('categoryModal');
    if (modal) {
        modal.style.display = 'block';
        document.getElementById('categoryId').value = id;
        document.getElementById('categoryName').value = name;
    }
}
document.addEventListener("DOMContentLoaded", function () {
    // Stelle sicher, dass alle Kategorie-Elemente vorhanden sind
    const categoryItems = document.querySelectorAll(".category-item");

    categoryItems.forEach(item => {
        item.addEventListener("click", function () {
            // Hole die ID und den Namen der Kategorie
            const categoryId = item.getAttribute("data-id");
            const categoryName = item.getAttribute("data-name");

            // Fülle die Werte in die Modal-Inputs
            document.getElementById("categoryIdInput").value = categoryId;
            document.getElementById("categoryNameInput").value = categoryName;

            // Öffne das Modal
            const categoryModal = new bootstrap.Modal(document.getElementById("categoryModal"));
            categoryModal.show();
        });
    });
});