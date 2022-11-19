import {initialUserState, IUserState} from "../state/user.state";
import {EUserActions, UserActions} from "../actions/user.actions";
import {User} from "../../entities/user";

export const userReducer = (
    state = initialUserState,
    action: UserActions
): IUserState => {

    switch (action.type) {
        case EUserActions.Auth:
            return {
                ...state,
                pending: true
            }
        case EUserActions.AuthSuccess:
            return {
                ...state,
                user: action.payload.user,
                token: action.payload.token,
                pending: false
            }
        case EUserActions.AuthError:
            return {
                ...state,
                user: new User(),
                pending: false,
                error: action.payload
            }
        case EUserActions.Logout:
            return {
                ...state,
                user: new User()
            }
        default:
            return state;
    }
}