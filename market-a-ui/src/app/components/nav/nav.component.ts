import {Component} from '@angular/core';
import {IAppState} from "../../store/state/app.state";
import {Store} from "@ngrx/store";
import {EUserActions} from "../../store/actions/user.actions";
import {selectUser, selectUserIsAdmin} from "../../store/selectors/user.selector";
import {CartRow} from "../../entities/cart.row";
import {selectCart} from "../../store/selectors/cart.selector";

@Component({
    selector: 'nav-component',
    templateUrl: './nav.component.html'
})
export class NavComponent {
    title = 'web-app';
    cart: CartRow[] = [];

    user$ = this.store.select(selectUser);
    isAdmin$ = this.store.select(selectUserIsAdmin);

    cart$ = this.store.select(selectCart).subscribe(value => {
        this.cart = value;
    })

    constructor(private store: Store<IAppState>) {
    }

    logout(): boolean {
        this.store.dispatch({type: EUserActions.Logout});
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        return false;
    }
}
