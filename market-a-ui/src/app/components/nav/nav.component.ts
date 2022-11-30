import {Component} from '@angular/core';
import {IAppState} from "../../store/state/app.state";
import {Store} from "@ngrx/store";
import {EUserActions} from "../../store/actions/user.actions";
import {selectUser, selectUserIsAdmin} from "../../store/selectors/user.selector";
import {selectCart} from "../../store/selectors/cart.selector";
import {ICartState, initialCartState} from "../../store/state/cart.state";

@Component({
    selector: 'nav-component',
    templateUrl: './nav.component.html'
})
export class NavComponent {
    title = 'web-app';
    cart: ICartState = initialCartState;

    user$ = this.store.select(selectUser);
    isAdmin$ = this.store.select(selectUserIsAdmin);

    cart$ = this.store.select(selectCart).subscribe(value => {
        this.cart = value;
    })

    constructor(private store: Store<IAppState>) {
    }

    logout(): boolean {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        this.store.dispatch({type: EUserActions.Logout});
        return false;
    }
}
