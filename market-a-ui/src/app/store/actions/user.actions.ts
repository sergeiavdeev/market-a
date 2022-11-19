import {Action} from "@ngrx/store";
import {Credentials} from "../../entities/user";
import {HttpResponse} from "@angular/common/http";
import {IUserState} from "../state/user.state";

export enum EUserActions {

    Auth = "[User] auth",
    AuthSuccess = "[User] auth success",
    AuthError = "[User] auth error",
    Logout = "[User] logout"
}

export class Auth implements Action {
    public readonly type = EUserActions.Auth;

    constructor(public payload: Credentials) {
    }
}

export class AuthSuccess implements Action {
    public readonly type = EUserActions.AuthSuccess;

    constructor(public payload: IUserState) {
    }
}

export class AuthError implements Action {
    public readonly type = EUserActions.AuthError;

    constructor(public payload: HttpResponse<any>) {
    }
}

export class Logout implements Action {
    public readonly type = EUserActions.Logout;
}


export type UserActions = Auth | AuthSuccess | AuthError | Logout;