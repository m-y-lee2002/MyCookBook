export const loginAuth = (email, password) => {
    return fetch(`http://localhost:8080/api/account/login/${email}/${password}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'include',
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .catch(error => {
        console.error('Error fetching data:', error);
        return;
    });
};