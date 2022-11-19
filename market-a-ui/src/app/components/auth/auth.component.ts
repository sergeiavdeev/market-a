import {Component} from "@angular/core";
import {Store} from "@ngrx/store";
import {IAppState} from "../../store/state/app.state";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {EUserActions} from "../../store/actions/user.actions";
import {selectToken, selectUser} from "../../store/selectors/user.selector";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
    selector: 'auth-form',
    templateUrl: './auth.component.html'
})
export class AuthComponent {
    form = new FormGroup({
        username: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required)
    });

    token$ = this.store.select(selectToken).subscribe((token) => {

        if (token.length > 0) {
            localStorage.setItem('token', token.toString());
            this.router.navigate(['/products'])
                .catch((e) => console.log(e));
        }
    });

    user$ = this.store.select(selectUser).subscribe(user => {
        localStorage.setItem('user', JSON.stringify(user));
    })

    constructor(private store: Store<IAppState>,
                private http: HttpClient,
                private router: Router) {
    }

    auth() {
        this.store.dispatch({
            type: EUserActions.Auth,
            payload: {username: this.form.controls.username.value, password: this.form.controls.password.value}
        })
    }
}