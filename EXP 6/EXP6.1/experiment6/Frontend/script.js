const API_URL = "http://localhost:8083/api/users";

let currentPage = 0;
let totalPages = 0;

// ==========================
// Load Users
// ==========================
async function loadUsers() {
    const size = document.getElementById("pageSize").value;
    const sortBy = document.getElementById("sortBy").value;
    const direction = document.getElementById("direction").value;

    try {
        const response = await fetch(
            `${API_URL}?page=${currentPage}&size=${size}&sortBy=${sortBy}&direction=${direction}`
        );

        if (!response.ok) {
            throw new Error("Failed to load users");
        }

        const data = await response.json();

        displayUsers(data);

    } catch (error) {
        console.error(error);
        alert("Server connection failed");
    }
}

// ==========================
// Display Users
// ==========================
function displayUsers(data) {
    const tableBody = document.getElementById("userTableBody");

    tableBody.innerHTML = "";

    data.content.forEach(user => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${user.uid}</td>
            <td>${user.name}</td>
            <td>
                <button onclick="deleteUser('${user.uid}')">
                    Delete
                </button>
            </td>
        `;

        tableBody.appendChild(row);
    });

    totalPages = data.totalPages;

    document.getElementById("pageInfo").textContent =
        `Page ${data.pageNumber + 1} of ${data.totalPages}`;
}

// ==========================
// Add User
// ==========================
async function addUser() {
    const uid = document.getElementById("uid").value.trim();
    const name = document.getElementById("name").value.trim();

    if (!uid || !name) {
        alert("Please enter UID and Name");
        return;
    }

    try {
        const response = await fetch(API_URL, {
            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                uid: uid,
                name: name
            })
        });

        if (!response.ok) {
            const error = await response.text();
            throw new Error(error);
        }

        document.getElementById("uid").value = "";
        document.getElementById("name").value = "";

        currentPage = 0;

        await loadUsers();

        alert("User added successfully");

    } catch (error) {
        console.error(error);
        alert("Failed to add user");
    }
}

// ==========================
// Delete User
// ==========================
async function deleteUser(uid) {
    if (!confirm(`Delete user ${uid}?`)) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${uid}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error("Failed to delete user");
        }

        await loadUsers();

        alert("User deleted successfully");

    } catch (error) {
        console.error(error);
        alert("Failed to delete user");
    }
}

// ==========================
// Previous Page
// ==========================
function previousPage() {
    if (currentPage > 0) {
        currentPage--;
        loadUsers();
    }
}

// ==========================
// Next Page
// ==========================
function nextPage() {
    if (currentPage < totalPages - 1) {
        currentPage++;
        loadUsers();
    }
}

// ==========================
// Load users when page opens
// ==========================
document.addEventListener("DOMContentLoaded", loadUsers);