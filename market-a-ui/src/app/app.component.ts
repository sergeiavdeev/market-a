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

    cart$ = this.store.select(selectCart).subscribe(cart => {
        this.store.dispatch({type: ECartActions.SaveToStorage, payload: cart});
    });

    constructor(private store: Store<IAppState>) {
    }

    ngOnInit() {
        console.log('App init');
        let user = localStorage.getItem("user");
        let token = localStorage.getItem("token");
        if (user != null) {
            user = JSON.parse(user);
            this.store.dispatch({type: EUserActions.AuthSuccess, payload: {token: token, user: user}});
            this.store.dispatch({type: ECartActions.LoadFromApi});
        }
    }
}
