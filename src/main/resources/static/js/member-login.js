let form;
let input_id;
let input_pwd;
let button_submit;

window.addEventListener('load', () => {
    form = document.getElementById('login-form');
    input_id = document.getElementById('id');
    input_pwd = document.getElementById('pwd');
    button_submit = document.getElementById('login-btn');
    input_pwd.addEventListener('keydown', (e) => {
        if (e.key === 'Enter') {
            onLogin().then();
        }
    });
    button_submit.addEventListener('click', () => onLogin());
});

async function onLogin() {
    let id_valid = input_id.value;
    let pwd_valid = input_pwd.value;

    if (id_valid && pwd_valid) {
        let id_match = await fetch_account(input_id);
        let pwd_match = await fetch_account(input_pwd);

        if (id_match && pwd_match) {
            form.submit();
        } else {
            input_id.value = null;
            input_pwd.value = null;
            updateValidity(input_id, true);
            updateValidity(input_pwd, true);
            window.alert('Wrong id or password!');
        }
    } else {
        updateValidity(input_id, id_valid);
        updateValidity(input_pwd, pwd_valid);
    }
}

/**
 * Requests account validation to server
 * @param input Input element (e.g. input_id)
 * @returns {Promise<boolean>} Promise returns true if credential were matched correctly
 */
async function fetch_account(input) {
    let param = {};
    let url;

    switch (input) {
        case input_id:
            url = '/member/register/query-id?';
            param.id = input.value;
            break;
        case input_pwd:
            url = '/member/register/query-pwd?';
            param.id = input_id.value;
            param.password = input.value;
            break;
        default:
            throw new Error('Unknown type of input!');
    }

    let query = new URLSearchParams(param);
    let fetch_url = url + query.toString();
    let response = await fetch(fetch_url);

    if (response.ok) {
        let text = await response.text();
        return (text === 'true');
    } else {
        throw new Error(`HTTP Error: ${response.status}`);
    }
}

/**
 * Updates bootstrap validation styling
 * @param input Input element
 * @param valid Boolean value to determine if entry is valid or not
 */
function updateValidity(input, valid) {
    if (valid) {
        input.classList.remove('is-invalid');
    } else {
        input.classList.add('is-invalid');
    }
}
