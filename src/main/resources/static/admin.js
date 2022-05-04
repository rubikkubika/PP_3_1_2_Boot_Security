const url = '/api/admin'
const urlHead = '/api/header'
const header = document.getElementById('head')
const usersTab = document.getElementById('alluserstab')
const alluserstab = bootstrap.Tab.getOrCreateInstance('usersTab')
const tBody = document.querySelector('tbody')
const newUser = document.getElementById('newuser')

const firstName = document.getElementById('firstName')
const lastName = document.getElementById('lastName')
const age = document.getElementById('age')
const username = document.getElementById('username')
const password = document.getElementById('password')
const roles = document.getElementById('roles')

function getAuthentication() {
    fetch(urlHead)
        .then(res => res.json())
        .then(user => {
            let role = user.username + ' с ролями: '
            role += user.roles.map(r => r.role.replace('ROLE_', ' '))
            header.innerHTML = role
        })
}

getAuthentication()

const fillingUserTable = () => {
    let res = ''
    fetch(url)
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                res += `<tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.surname}</td>
                            <td>${user.username}</td>
                            <td>${user.age}</td>
                            <td>${user.roles.map(r => r.role.replace('ROLE_', ' '))}</td>
                            <td class="text-center"><button type="submit" class="btn btn-info active" 
                                data-bs-toggle="modal" data-bs-target="#editModal">Edit</button></td>
                            <td class="text-center"><button type="submit" class="btn btn-danger" 
                                data-bs-toggle="modal" data-bs-target="#deleteModal">Delete</button></td>
                        </tr>`
            })
            tBody.innerHTML = res
        })

}

fillingUserTable()

function getAllRoles(target) {
    fetch(`${url}/allroles`)
        .then(res => res.json())
        .then(roles => {
            let roleList = ''
            roles.forEach(role => {
                roleList += `<option value='${role.id}'>${role.role.replace('ROLE_', ' ')}</option>`
            })
            target.innerHTML = roleList
        })
}

getAllRoles(roles)

let headers = new Headers();
headers.append('Content-Type', 'application/json; charset=utf-8');

let roleList = (options) => {
    let array = []
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            let role = {id: options[i].value}
            array.push(role)
        }
    }
    return array;
}

newUser.addEventListener('submit', (e) => {
    e.preventDefault()
    let options = document.querySelector('#roles');
    let setRoles = roleList(options)
    fetch(`${url}`, {
        method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({
            name: firstName.value,
            surname: lastName.value,
            age: age.value,
            username: username.value,
            password: password.value,
            roles: setRoles
        })
    })
        .then(data => fillingUserTable(data))
        .catch(error => console.log(error))
    alluserstab.show()
    firstName.value = ''
    lastName.value = ''
    age.value = ''
    username.value = ''
    password.value = ''
})
