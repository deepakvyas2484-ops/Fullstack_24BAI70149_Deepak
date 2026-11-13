const API_URL = "/api/users";


// Load users when page opens
window.onload = function () {
    loadUsers();
};


// Load all users
async function loadUsers() {

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Failed to load users");
        }

        const users = await response.json();

        displayUsers(users);

        setStatus("Users loaded successfully");

    } catch (error) {

        console.error(error);

        setStatus("Server connection failed");

    }
}


// Display users in table
function displayUsers(users) {

    const tableBody = document.getElementById("userTableBody");

    tableBody.innerHTML = "";

    users.forEach(user => {

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.uid}</td>
            <td>${user.name}</td>
            <td>${user.city}</td>
            <td>${user.country}</td>

            <td>
                <button
                    class="delete-btn"
                    onclick="deleteUser(${user.id})">
                    Delete
                </button>
            </td>
        `;

        tableBody.appendChild(row);
    });
}


// Add user
async function addUser() {

    const uid = document.getElementById("uid").value.trim();
    const name = document.getElementById("name").value.trim();
    const city = document.getElementById("city").value.trim();
    const country = document.getElementById("country").value.trim();


    if (!uid || !name || !city || !country) {

        setStatus("Please fill all fields");

        return;
    }


    const user = {
        uid: uid,
        name: name,
        city: city,
        country: country
    };


    try {

        const response = await fetch(API_URL, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(user)
        });


        if (!response.ok) {
            throw new Error("Failed to add user");
        }


        document.getElementById("uid").value = "";
        document.getElementById("name").value = "";
        document.getElementById("city").value = "";
        document.getElementById("country").value = "";


        setStatus("User added successfully");

        loadUsers();

    } catch (error) {

        console.error(error);

        setStatus("Failed to add user");
    }
}


// Delete user
async function deleteUser(id) {

    try {

        const response = await fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        });


        if (!response.ok) {
            throw new Error("Failed to delete user");
        }


        setStatus("User deleted successfully");

        loadUsers();

    } catch (error) {

        console.error(error);

        setStatus("Failed to delete user");
    }
}


// Query optimization tests
async function runQuery(type) {

    const start = performance.now();


    try {

        const response = await fetch(`${API_URL}/${type}`);

        if (!response.ok) {
            throw new Error("Query failed");
        }


        const data = await response.json();

        const end = performance.now();

        displayUsers(data.users);

        const backendTime = Number(data.time).toFixed(2);
        const frontendTime = (end - start).toFixed(2);


        setStatus(
            `${data.operation} completed in ${backendTime} ms`
        );


        console.log(
            `${data.operation}: Backend ${backendTime} ms | Frontend ${frontendTime} ms`
        );


    } catch (error) {

        console.error(error);

        setStatus("Query failed");
    }
}


// Sort users
async function sortUsers(type) {

    try {

        const start = performance.now();

        const response = await fetch(
            `${API_URL}/sort/${type}`
        );


        if (!response.ok) {
            throw new Error("Sort failed");
        }


        const data = await response.json();

        const end = performance.now();

        displayUsers(data.users);


        const time = Number(data.time).toFixed(2);


        setStatus(
            `${data.operation} completed in ${time} ms`
        );


        console.log(
            `Total request time: ${(end - start).toFixed(2)} ms`
        );


    } catch (error) {

        console.error(error);

        setStatus("Sort failed");
    }
}


// Update status message
function setStatus(message) {

    document.getElementById("status").textContent = message;
}