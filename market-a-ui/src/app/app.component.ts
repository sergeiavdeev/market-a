import {Component} from '@angular/core';
import {Store} from "@ngrx/store";
import {IAppState} from "./store/state/app.state";
import {EUserActions} from "./store/actions/user.actions";
import {selectCart} from "./store/selectors/cart.selector";
import {ECartActions} from "./store/actions/cart.actions";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html'
})
export class AppComponent {
    title = 'web-app';
    //cart: CartRow[] = [];

    cart$ = this.store.select(selectCart).subscribe(value => {
        if (value.length != 0) {
            localStorage.setItem("cart", JSON.stringify(value));
        }
    })

    constructor(private store: Store<IAppState>) {
    }

    ngOnInit() {
        console.log('App init');
        let user = localStorage.getItem("user");
        if (user != null) {
            user = JSON.parse(user);
            this.store.dispatch({type: EUserActions.AuthSuccess, payload: user});
        }
        let cart = localStorage.getItem("cart");
        if (cart != null) {
            cart = JSON.parse(cart);
            this.store.dispatch({type: ECartActions.LoadFromStorage, payload: cart});
        }
    }
}
