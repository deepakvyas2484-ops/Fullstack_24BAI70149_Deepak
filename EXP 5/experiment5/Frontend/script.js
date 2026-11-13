const API_URL = "http://localhost:8081/api/users";

const userForm = document.getElementById("userForm");
const uidInput = document.getElementById("uid");
const nameInput = document.getElementById("name");
const message = document.getElementById("message");
const userTableBody = document.getElementById("userTableBody");
const refreshBtn = document.getElementById("refreshBtn");

const searchUid = document.getElementById("searchUid");
const searchBtn = document.getElementById("searchBtn");
const clearBtn = document.getElementById("clearBtn");

// Load users when page opens
loadUsers();


// Add User
userForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    const uid = uidInput.value.trim();
    const name = nameInput.value.trim();

    if (!uid || !name) {
        showMessage("Please enter UID and Name", "error");
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

        const result = await response.json();

        if (response.ok) {

            showMessage(result.message, "success");

            userForm.reset();

            loadUsers();

        } else {

            showMessage(
                result.message || "Failed to add user",
                "error"
            );
        }

    } catch (error) {

        showMessage("Server connection failed", "error");

        console.error(error);
    }
});


// Get all users
async function loadUsers() {

    try {

        const response = await fetch(API_URL);

        const result = await response.json();

        userTableBody.innerHTML = "";

        if (result.success && result.data) {

            result.data.forEach(user => {
                addUserRow(user);
            });

        }

    } catch (error) {

        console.error("Error loading users:", error);

    }
}


// Search User
searchBtn.addEventListener("click", async function () {

    const uid = searchUid.value.trim();

    if (!uid) {

        showMessage("Please enter UID to search", "error");

        return;
    }

    try {

        const response = await fetch(`${API_URL}/${uid}`);

        const result = await response.json();

        userTableBody.innerHTML = "";

        if (response.ok && result.success) {

            addUserRow(result.data);

            showMessage("User found successfully", "success");

        } else {

            showMessage(
                result.message || "User not found",
                "error"
            );
        }

    } catch (error) {

        showMessage("Server connection failed", "error");

        console.error(error);
    }
});


// Clear Search
clearBtn.addEventListener("click", function () {

    searchUid.value = "";

    loadUsers();

    showMessage("Showing all users", "success");

});


// Add row to table
function addUserRow(user) {

    const row = document.createElement("tr");

    row.innerHTML = `
        <td>${user.uid}</td>

        <td>${user.name}</td>

        <td>

            <button
                class="edit-btn"
                onclick="editUser('${user.uid}', '${user.name}')">
                Edit
            </button>

            <button
                class="delete-btn"
                onclick="deleteUser('${user.uid}')">
                Delete
            </button>

        </td>
    `;

    userTableBody.appendChild(row);
}


// Delete User
async function deleteUser(uid) {

    if (!confirm("Are you sure you want to delete this user?")) {
        return;
    }

    try {

        const response = await fetch(`${API_URL}/${uid}`, {
            method: "DELETE"
        });

        const result = await response.json();

        if (response.ok) {

            showMessage(result.message, "success");

            loadUsers();

        } else {

            showMessage(
                result.message || "Delete failed",
                "error"
            );
        }

    } catch (error) {

        showMessage("Server connection failed", "error");

        console.error(error);
    }
}


// Edit User
async function editUser(uid, oldName) {

    const newName = prompt(
        "Enter new name:",
        oldName
    );

    if (!newName || newName.trim() === "") {
        return;
    }

    try {

        const response = await fetch(`${API_URL}/${uid}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                uid: uid,
                name: newName.trim()
            })
        });

        const result = await response.json();

        if (response.ok) {

            showMessage(result.message, "success");

            loadUsers();

        } else {

            showMessage(
                result.message || "Update failed",
                "error"
            );
        }

    } catch (error) {

        showMessage("Server connection failed", "error");

        console.error(error);
    }
}


// Refresh button
refreshBtn.addEventListener("click", loadUsers);


// Display message
function showMessage(text, type) {

    message.textContent = text;

    message.className = type;

    setTimeout(() => {

        message.textContent = "";

        message.className = "";

    }, 3000);
}