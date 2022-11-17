import {Component} from "@angular/core";
import {Store} from "@ngrx/store";
import {IAppState} from "../../store/state/app.state";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {EUserActions} from "../../store/actions/user.actions";
import {selectUser} from "../../store/selectors/user.selector";
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

    user$ = this.store.select(selectUser).subscribe((user) => {
        if (user.token.length > 0) {
            localStorage.setItem('token', user.token.toString());
            localStorage.setItem("user", JSON.stringify(user));
            this.router.navigate(['/products']);
        }
    });

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