import {ActionReducerMap, MetaReducer} from "@ngrx/store";
import {IAppState} from "../state/app.state";
import {routerReducer} from "@ngrx/router-store";
import {productsReducer} from "./products.reducer";
import {environment} from "../../../environments/environment.prod";
import {userReducer} from "./user.reducer";
import {cartReducer} from "./cart.reducer";

export const appReducer: ActionReducerMap<IAppState, any> = {
    router: routerReducer,
    products: productsReducer,
    user: userReducer,
    cart: cartReducer
}

export const metaReducers: MetaReducer<IAppState>[] = !environment.production ? [] : [];
