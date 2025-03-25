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

function loadCategory(element) {
    const categoryId = element.getAttribute('data-id');
    const categoryName = element.getAttribute('data-name');

    document.getElementById('categoryIdInput').value = categoryId;
    document.getElementById('categoryNameInput').value = categoryName;
    document.getElementById('categoryModalLabel').textContent = 'Kategorie bearbeiten';

    const modal = new bootstrap.Modal(document.getElementById('categoryModal'));
    modal.show();
}
function clearCategoryModal() {
    document.getElementById('categoryIdInput').value = '';
    document.getElementById('categoryNameInput').value = '';
    document.getElementById('categoryModalLabel').textContent = 'Neue Kategorie';
}
function saveCategory() {
    const form = document.getElementById('categoryForm');
    form.submit();
}

function deleteCategory() {
    const categoryId = document.getElementById('categoryIdInput')?.value.trim();

    if (!categoryId) {
        alert('Keine Kategorie-ID vorhanden.');
        return;
    }

    const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;

    fetch(`/user/category/${categoryId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        }
    }).then(response => {
        window.location.href = '/user/profile';
    });
}


function copyShareLink() {
    const shareLinkInput = document.getElementById('shareLinkInput');
    shareLinkInput.select();
    navigator.clipboard.writeText(shareLinkInput.value);
    alert('Link wurde in die Zwischenablage kopiert!');
}
