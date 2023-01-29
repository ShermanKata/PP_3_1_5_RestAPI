const urlUsers = '/api/users/';
const urlRoles = '/api/roles/';

const allUsers = fetch(urlUsers).then(response => response.json())
const allRoles = fetch(urlRoles).then(response => response.json())

const deleteUserModal = new bootstrap.Modal(document.getElementById('deleteUserModal'))

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e.target)
        }
    })
}

// Заполнение таблицы пользователей
allUsers.then(users => {
        let result = ''
        for (const i in users) {
            let roles = ''
            users[i].roles.forEach(role => {
                roles += ' '
                roles += role.name.replace("ROLE_", "")
            })
            result += `<tr>
                    <td>${users[i].id}</td>
                    <td>${users[i].username}</td>
                    <td>${users[i].lastname}</td>
                    <td>${users[i].age}</td>
                    <td>${users[i].email}</td>
                    <td>${roles}</td>
                    <td>
                        <button type="button" class="btn btn-info btn-sm text-white" id="editUserButton"}">Edit</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger btn-sm" id="deleteUserButton">Delete</button>
                    </td>
                    </tr>`
        }
        document.getElementById("users-info-table").innerHTML = result
    }
)

// Функция заполнения ролей
fillRoles = function (elementId) {
    allRoles.then(roles => {
        let result = ''
        for (const i in roles) {
            result += `<option value = ${roles[i].id}>${roles[i].name.replace("ROLE_", "")}</option>`
        }
        document.getElementById(elementId).innerHTML = result
    })
}

fillRoles("role_select")


//Добавление нового пользователя
document.getElementById('newUserForm').addEventListener('submit', (e) => {
    e.preventDefault()
    let role = document.getElementById('role_select')
    let rolesAddUser = []
    let rolesAddUserValue = ''
    for (let i = 0; i < role.options.length; i++) {
        if (role.options[i].selected) {
            rolesAddUser.push({id: role.options[i].value, name: 'ROLE_' + role.options[i].innerHTML})
            rolesAddUserValue += role.options[i].innerHTML + ' '
        }
    }
    fetch(urlUsers, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            username: document.getElementById('firstName').value,
            lastname: document.getElementById('lastName').value,
            age: document.getElementById('age').value,
            email: document.getElementById('email').value,
            password: document.getElementById('password').value,
            roles: rolesAddUser
        })
    })
        .then(response => response.json())
        .then(user => {
            let newRow = document.createElement('tr')
            newRow.innerHTML = `<tr>
                           <td>${user.id}</td>
                           <td>${user.username}</td>
                           <td>${user.lastname}</td>
                           <td>${user.age}</td>
                           <td>${user.email}</td>
                           <td>${rolesAddUserValue}</td>
                           <td><button type="button" class="btn btn-info btn-sm text-white" id="editUserButton">Edit</button></td>
                           <td><button type="button" class="btn btn-danger btn-sm" id="deleteUserButton">Delete</button></td>
                           </tr>`
            document.getElementById("users-info-table").appendChild(newRow)
            document.getElementById('newUserForm').reset()

        })
    document.getElementById("all-users-tab").click()
})

// Удаление пользователя
let rowDelete = null
on(document, 'click', '#deleteUserButton', e => {
    rowDelete = e.parentNode.parentNode
    document.getElementById('id_delete').value = rowDelete.children[0].innerHTML
    document.getElementById('firstName_delete').value = rowDelete.children[1].innerHTML
    document.getElementById('lastName_delete').value = rowDelete.children[2].innerHTML
    document.getElementById('age_delete').value = rowDelete.children[3].innerHTML
    document.getElementById('email_delete').value = rowDelete.children[4].innerHTML

    let option = ''
    allRoles.then(roles => {
        roles.forEach(role => {
            if (rowDelete.children[5].innerHTML.includes(role.name.replace('ROLE_', ''))) {
                option += `<option value="${role.id}">${role.name.replace('ROLE_', '')}</option>`
            }
        })
        document.getElementById('role_delete').innerHTML = option
    })
    deleteUserModal.show()
})

document.getElementById('delete_user_form').addEventListener('submit', (e) => {
    e.preventDefault()
    console.log(deleteUserModal.id)
    fetch(urlUsers, {
        method: 'DELETE'
    }).then(() => {
        deleteUserModal.hide()
        rowDelete.parentNode.removeChild(rowDelete)
    })
})

