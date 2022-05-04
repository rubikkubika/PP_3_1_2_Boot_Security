const url = '/api/user'
const urlHead = '/api/header'
const header = document.getElementById('head')
const tBody = document.querySelector('tbody')

function getAuthenticationForUserPage() {
    fetch(urlHead)
        .then(res => res.json())
        .then(user => {
            let role = user.username + ' с ролями: '
            role += user.roles.map(r=>r.role.replace('ROLE_', ' '))
            header.innerHTML = role
        })
}
getAuthenticationForUserPage()


let res = ''
const filling = (user) => {
    res += `<tr>
        <td>${user.id}</td>   
        <td>${user.name}</td>
        <td>${user.surname}</td>
        <td>${user.username}</td>
        <td>${user.age}</td>
        <td>${user.roles.map(r=>r.role.replace('ROLE_', ' '))}</td>
        </tr>`
    tBody.innerHTML = res
}

fetch(url)
    .then(response => response.json())
    .then(data => filling(data))
    .catch(error => console.log(error))

