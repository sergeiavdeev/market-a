import {IAppState} from "../state/app.state";
import {createSelector} from "@ngrx/store";
import {IUserState} from "../state/user.state";
import {environment} from "../../../environments/environment";

const userSelector = (state: IAppState) => state.user;

export const selectUser = createSelector(
    userSelector,
    (state: IUserState) => state.user
);

export const selectUserIsAdmin = createSelector(
    userSelector,
    (state: IUserState) => {
        return state.user.roles.findIndex((role) => role == environment.ROLE_ADMIN) != -1;
    }
)