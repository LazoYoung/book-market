class PageInfo extends HTMLElement {
    constructor() {
        super();

        let name = this.getAttribute('name').toLowerCase();
        const a = document.getElementById('link-' + name);

        if (a) {
            a.classList.add('active');
            a.setAttribute('aria-current', 'page');
        }
    }
}

window.addEventListener('load', () => {
    let logout_form = document.getElementById('logout-form');
    let logout_button = document.getElementById('logout-btn');

    if (logout_button) {
        logout_button.addEventListener('click', (e) => {
            e.preventDefault();
            logout_form.submit();
        });
    }
    customElements.define('page-info', PageInfo);
});
