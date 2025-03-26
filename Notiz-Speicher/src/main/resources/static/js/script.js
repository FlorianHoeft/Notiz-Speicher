function toggleSidebar() {
    let sidebar = document.getElementById("sidebar");
    let logo = document.getElementById("logo");


    if (window.innerWidth > 1000) {
        sidebar.classList.toggle("minimized");
        // Logo ändern je nach Sidebar-Status
        if (sidebar.classList.contains("minimized")) {
            logo.src = "/img/thowl_logo_short.png";
            logo.classList.add("minimized");
        } else {
            logo.src = "/img/thowl_logo.png";
            logo.classList.remove("minimized");
        }
    }
}


window.addEventListener("resize", handleResize);
handleResize();

function handleResize() {
    let sidebar = document.getElementById("sidebar");
    let logo = document.getElementById("logo");
    let sidebarToggleButton = document.getElementById("sidebarToggle");

    if (window.innerWidth <= 1000) {
        sidebar.classList.add("minimized");
        sidebarToggleButton.disabled = true;
        logo.src = "/img/thowl_logo_short.png";
        logo.classList.add("minimized");
    } else {
        sidebar.classList.remove("minimized");
        sidebarToggleButton.disabled = false;
        // Logo basierend auf Sidebar-Status setzen
        if (sidebar.classList.contains("minimized")) {
            logo.src = "/img/thowl_logo_short.png";
            logo.classList.add("minimized");
        } else {
            logo.src = "/img/thowl_logo.png";
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
