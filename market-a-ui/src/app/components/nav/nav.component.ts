import {Component} from '@angular/core';
import {IAppState} from "../../store/state/app.state";
import {Store} from "@ngrx/store";
import {EUserActions} from "../../store/actions/user.actions";
import {selectUser} from "../../store/selectors/user.selector";
import {User} from "../../entities/user";
import {Router} from "@angular/router";
import {CartRow} from "../../entities/cart.row";
import {selectCart} from "../../store/selectors/cart.selector";

@Component({
    selector: 'nav-component',
    templateUrl: './nav.component.html'
})
export class NavComponent {
    title = 'web-app';
    user: User = new User();
    cart: CartRow[] = [];

    user$ = this.store.select(selectUser)
        .subscribe((u) => {
            this.user = u;
        });

    cart$ = this.store.select(selectCart).subscribe(v => {
        this.cart = v;
    })

    constructor(private store: Store<IAppState>, private router: Router) {
    }

    logout(): boolean {
        this.store.dispatch({type: EUserActions.Logout});
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        return false;
    }
}
