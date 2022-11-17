import {IAppState} from "../state/app.state";
import {createSelector} from "@ngrx/store";
import {ICartState} from "../state/cart.state";

const cartSelector = (state: IAppState) => state.cart;

export const selectCart = createSelector(
    cartSelector,
    (state: ICartState) => state.cartRows
);