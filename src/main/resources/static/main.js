const userList = document.querySelector('.users-table');
const userData = document.querySelector('.user-table');
const deleteForm = document.querySelector('.formDelete');
const editForm = document.querySelector('.formEdit');
const loginPage = document.getElementById('login');
const adminPage = document.getElementById('admin');
const userPage = document.getElementById('user');
let url = 'http://localhost:8080/api/users';
let delUserFrm = document.querySelector('.formBtnDelete');
let editUserFrm = document.querySelector('.formBtnEdit');

userPage.hidden = true;
loginPage.hidden = true;

if (document.getElementById('userAuth').textContent === 'USER') {
    showUserData();
    adminPage.hidden = true;
    userPage.hidden = false;
    document.title = 'User page';
} else if (document.getElementById('userAuth').textContent !== '') {
    document.title = 'Admin panel';
}

document.getElementById('userSelectBTN').onclick = function (e) {
    e.preventDefault();
    showUserData();
    adminPage.hidden = true;
    userPage.hidden = false;
    document.title = 'User page';
}

document.getElementById('adminSelectBTN').onclick = function (e) {
    e.preventDefault();
    adminPage.hidden = false;
    userPage.hidden = true;
    document.title = 'Admin panel';
}

showAllUsers();
/**
 * New user
 */
let newUserFrm = document.forms.reg;
newUserFrm.addEventListener('submit', async (e) => {
    e.preventDefault();
    let roles = newUserFrm.userRole.options;
    if (roles[0].selected && roles[1].selected) {
        roles = roles[0].text + " " + roles[1].text;
    } else {
        roles = newUserFrm.userRole.value
    }
    // for (i = 0; i < 2; i ++) {
    //     alert(obj.options[ i ].selected);
    // }
    // let sel = newUserFrm.userRole.selectedIndex;
    // console.log(newUserFrm.userRole.selectedIndex);
    // console.log(newUserFrm.userRole.multiple);
    // console.log(newUserFrm.userRole.options[sel].text);
    // console.log(roles);
    await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstName: newUserFrm.userFN.value,
            lastName: newUserFrm.userLN.value,
            age: newUserFrm.userAge.value,
            email: newUserFrm.userEm.value,
            password: newUserFrm.userPas.value,
            role: roles,
        })
    });
    showAllUsers();
    document.getElementById('nav-profile').setAttribute('class', 'tab-pane fade');
    document.getElementById('nav-home').setAttribute('class', 'tab-pane fade show active');
    document.getElementById('nav-profile-tab').setAttribute('class', 'nav-link');
    document.getElementById('nav-home-tab').setAttribute('class', 'nav-link active');
    e.target.reset();
});

function test(obj) {
    for (i = 0; i < 2; i ++) {
        alert(obj.options[ i ].selected);
    }
}

userList.addEventListener('click', (e) => {
    let delButton = e.target.id === 'delUser';
    let editButton = e.target.id === 'editUser';
    let userId = e.target.dataset.userid;
    let userRole = e.target.dataset.userrole;

    if (delButton) {
        fetch(`${url}/${userId}`)
            .then(response => response.json())
            .then(user => {
                deleteForm.innerHTML = `
            <div class="form-group">
                    <label for="id2" class="form-label"><strong>ID</strong></label>
                    <input type="text" class="form-control" value=${user.id} id="id2" disabled>
                </div>
                <div class="form-group">
                    <label for="first-name2" class="form-label"><strong>First name</strong></label>
                    <input type="text" class="form-control" value=${user.firstName} id="first-name2" disabled>
                </div>
                <div class="form-group">
                    <label for="last-name2" class="form-label"><strong>Last name</strong></label>
                    <input type="text" class="form-control" value=${user.lastName} id="last-name2" disabled>
                </div>
                <div class="form-group">
                    <label for="age2" class="form-label"><strong>Age</strong></label>
                    <input type="number" class="form-control" value=${user.age} id="age2" disabled>
                </div>
                <div class="form-group">
                    <label for="email2" class="form-label"><strong>Email</strong></label>
                    <input type="email" class="form-control" value=${user.email} id="email2"  disabled>
                </div>
                <div class="form-group">
                    <label for="role2" class="form-label"><strong>Role</strong></label>
                    <select multiple class="form-control" size="2" aria-label="Default select example" id="role2" disabled>
                        <option value="ADMIN">ADMIN</option>
                        <option value="USER">USER</option>
                    </select>
                </div>
        `
            });
        delUserById(userId);
    } else if (editButton) {
        fetch(`${url}/${userId}`)
            .then(response => response.json())
            .then(user => {
                editForm.innerHTML = `
            <div class="form-group">
                    <label for="id1" class="form-label"><strong>ID</strong></label>
                    <input name="userEID" type="text" class="form-control" value=${user.id} id="id1" disabled>
                </div>
                <div class="form-group">
                    <label for="first-name1" class="form-label"><strong>First name</strong></label>
                    <input name="userEFN" type="text" class="form-control" value=${user.firstName} id="first-name1" >
                </div>
                <div class="form-group">
                    <label for="last-name1" class="form-label"><strong>Last name</strong></label>
                    <input name="userELN" type="text" class="form-control" value=${user.lastName} id="last-name1" >
                </div>
                <div class="form-group">
                    <label for="age1" class="form-label"><strong>Age</strong></label>
                    <input name="userEAge" type="number" class="form-control" value=${user.age} id="age1" >
                </div>
                <div class="form-group">
                    <label for="email1" class="form-label"><strong>Email</strong></label>
                    <input name="userEemail" type="email" class="form-control" value=${user.email} id="email1"  >
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword11" class="form-label"><strong>Password</strong></label>
                    <input name="userEpass" type="password" class="form-control" value=${user.password} id="exampleInputPassword11">
                </div>
                <div class="form-group">
                    <label for="role1" class="form-label"><strong>Role</strong></label>
                    <select name="userErole" multiple class="form-control" size="2" aria-label="Default select example" id="role1" >
                        <option value="ADMIN">ADMIN</option>
                        <option value="USER">USER</option>
                    </select>
                </div>       `
            });
        editUser(userId, userRole);
    }
});

function delUserById(id) {
    delUserFrm.addEventListener('submit', async (e) => {
        e.preventDefault();
        // console.log(id);
        await fetch(`${url}/${id}`, {
            method: 'DELETE',
        });
        showAllUsers();
        document.getElementById('btnDelHide').click();
    });
}

function editUser(id, role) {
    let roleToSend = role;
    console.log("Edit role = " + role);
    editUserFrm.addEventListener('submit', async (e) => {
        e.preventDefault();
        if (editUserFrm.userErole.value !== '') {
            roleToSend = editUserFrm.userErole.value;
        }
        await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editUserFrm.userEID.value,
                firstName: editUserFrm.userEFN.value,
                lastName: editUserFrm.userELN.value,
                age: editUserFrm.userEAge.value,
                email: editUserFrm.userEemail.value,
                password: editUserFrm.userEpass.value,
                role: roleToSend,
            })
        })
        showAllUsers();
        document.getElementById('btnEditHide').click();
    });
}

function showAllUsers() {
    let output = ``;
    fetch(url)
        .then(response => response.json())
        .then(users => {
            users.forEach(user => {
                output += `
                    <tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.stringRoles}</td>
                    <td><a type="button" class="btn btn-info" data-bs-toggle="modal" id="editUser" data-userrole=${user.stringRoles} data-userid=${user.id} data-bs-target="#edit">Edit</a></td>
                    <td><a type="button" class="btn btn-danger" data-bs-toggle="modal" id="delUser" data-uFN=${user.firstName} data-userid=${user.id} data-bs-target="#del">Delete</a></td>
                    </tr>
                `;
            });
            userList.innerHTML = output;
        });
}

function showUserData() {
    let id = userData.getAttribute('user-id');
    fetch(`${url}/${id}`)
        .then(response => response.json())
        .then(user => {
            userData.innerHTML = `
                    <tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.stringRoles}</td>         
                    </tr>
                `;
            });
}