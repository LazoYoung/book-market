let button_cart;

window.addEventListener('load', () => {
    button_cart = document.getElementById('cart-btn');
    button_cart.addEventListener('click', addCartItem);
});

function addCartItem() {
    const params = new Proxy(new URLSearchParams(window.location.search), {
        get: (searchParams, prop) => searchParams.get(prop)
    });
    let query = new URLSearchParams({id: params.id}).toString();

    fetch('/cart/add?' + query, {method: 'POST', credentials: 'include'})
        .catch(() => {
            window.alert('Error! Failed to add item.');
        })
        .then(() => {
            window.location.assign('/books')
        });
}