import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {catchError, map, mergeMap, of} from "rxjs";
import {Auth, EUserActions} from "../actions/user.actions";
import {AuthService} from "../../services/auth.service";

@Injectable()
export class AuthEffects {

    auth$ = createEffect(() => this.actions$.pipe(
            ofType(EUserActions.Auth),
            mergeMap((action: Auth) => this.authService.auth(action.payload)
                .pipe(
                    map((user) => ({type: EUserActions.AuthSuccess, payload: user})),
                    catchError((error) => of({type: EUserActions.AuthError, payload: error}))
                ))
        )
    );

    constructor(private authService: AuthService, private actions$: Actions) {
    }

}