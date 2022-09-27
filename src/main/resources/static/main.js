const userList = document.querySelector('.users-table');
const deleteForm = document.querySelector('.formDelete');
const editForm = document.querySelector('.formEdit');
const delModal = document.getElementById('del');
const editModal = document.getElementById('edit');
// const delModal = document.querySelector(".delModal")

let delUserFrm = document.querySelector('.formBtnDelete');
let editUserFrm = document.querySelector('.formBtnEdit');

document.getElementById('user').hidden = true;

document.getElementById('userSelectBTN').onclick = function (e) {
    e.preventDefault();
    document.getElementById('admin').hidden = true;
    document.getElementById('user').hidden = false;
}

// console.dir(document.getElementById('del'));

let url = 'http://localhost:8080/api/users';

showAllUsers();

// let output = '';
// fetch(url)
//     .then(response => response.json())
//     .then(users => {
//         users.forEach(user => {
//             output += `
//                 <tr>
//                 <td>${user.id}</td>
//                 <td>${user.firstName}</td>
//                 <td>${user.lastName}</td>
//                 <td>${user.age}</td>
//                 <td>${user.email}</td>
//                 <td>${user.role}</td>
//                 <td><a type="button" class="btn btn-info" data-bs-toggle="modal" id="editUser" data-userid=${user.id} data-bs-target="#edit">Edit</a></td>
//                 <td><a type="button" class="btn btn-danger" data-bs-toggle="modal" id="delUser" data-uFN=${user.firstName} data-userid=${user.id} data-bs-target="#del">Delete</a></td>
//                 </tr>
//             `;
//         });
//         userList.innerHTML = output;
//     });

let newUserFrm = document.forms.reg;
let userFirstName = newUserFrm.userFN;

newUserFrm.addEventListener('submit', async (e) => {
    e.preventDefault();
    console.log(newUserFrm.userFN.value)
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
            role: newUserFrm.userRole.value,
        })
    });
    showAllUsers();
    document.getElementById('nav-profile').setAttribute('class', 'tab-pane fade');
    document.getElementById('nav-home').setAttribute('class', 'tab-pane fade show active');
});

userList.addEventListener('click', (e) => {
    let delButton = e.target.id == 'delUser';
    let editButton = e.target.id == 'editUser';
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
                </div>
        `
            });
        editUser(userId, userRole);
    }
});

function delUserById(id) {

    delUserFrm.addEventListener('submit', async (e) => {
        // console.dir(document.getElementById('del'));
        e.preventDefault();
        console.log(id);
        await fetch(`${url}/${id}`, {
            method: 'DELETE',
        });
        const actionWithDelay = async () => {
            await delay(2000);
        }
        showAllUsers();
        document.getElementById('btnDelHide').click();

        // console.dir(document.body);
        // document.body.removeAttribute('class');
        //
        // document.getElementById('del').removeAttribute('style');
        // document.getElementById('del').removeAttribute('aria-modal');
        // document.getElementById('del').removeAttribute('role');
        // document.getElementById('del').removeAttribute('modal-backdrop');
        // delModal.hidden = true;
        //
        // document.getElementById('del').setAttribute('aria-hidden', 'true');
        // document.getElementById('del').setAttribute('class', 'modal fade');
        //
        // document.getElementById('nav-profile').setAttribute('class', 'tab-pane fade');
        // document.getElementById('nav-home').setAttribute('class', 'tab-pane fade show active');
        // document.getElementById('del').setAttribute('display', '');
        // delModal.hidden = true;  style="display: block;"
    });
}

function editUser(id, role) {
    let roleToSend = role;
    // console.log(roleToSend);

    editUserFrm.addEventListener('submit', async (e) => {
        e.preventDefault();
        if (editUserFrm.userErole.value != '') {
            roleToSend = editUserFrm.userErole.value;
        }
        // console.log(roleToSend);
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

// function closeModal() {
//     document.getElementById("del").style.display = "none";
//     // document.querySelector(".delModal").style.display = "none";
// }

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
                    <td>${user.role}</td>
                    <td><a type="button" class="btn btn-info" data-bs-toggle="modal" id="editUser" data-userrole=${user.role} data-userid=${user.id} data-bs-target="#edit">Edit</a></td>
                    <td><a type="button" class="btn btn-danger" data-bs-toggle="modal" id="delUser" data-uFN=${user.firstName} data-userid=${user.id} data-bs-target="#del">Delete</a></td>
                    </tr>
                `;
            });
            userList.innerHTML = output;
            // $('.users-table').html(output);
            // $(".users-table").replaceWith(output);
            // $(this).replaceWith(function (index, content)
        });
}