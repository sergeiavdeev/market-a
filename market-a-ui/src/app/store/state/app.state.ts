import {RouterReducerState} from "@ngrx/router-store";
import {initialProductsState, IProductsState} from "./products.state";
import {initialUserState, IUserState} from "./user.state";
import {ICartState, initialCartState} from "./cart.state";

export interface IAppState {
    router?: RouterReducerState;
    products: IProductsState;
    user: IUserState;
    cart: ICartState;
}

export const initialAppState: IAppState = {
    products: initialProductsState,
    user: initialUserState,
    cart: initialCartState
}

export function getInitialState(): IAppState {
    return initialAppState;
}
