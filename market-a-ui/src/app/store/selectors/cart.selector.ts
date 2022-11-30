import {IAppState} from "../state/app.state";
import {createSelector} from "@ngrx/store";
import {ICartState} from "../state/cart.state";

const cartSelector = (state: IAppState) => state.cart;

export const selectCart = createSelector(
    cartSelector,
    (state: ICartState) => state
);

export const selectCartCount = createSelector(
    cartSelector,
    (state: ICartState) => {
        let count = 0;
        state.items.map(el => count = count + el.quantity);
        return count;
    }
)

export const selectCartSum = createSelector(
    cartSelector,
    (state: ICartState) => state.total
)

export const selectCartItems = createSelector(
    cartSelector,
    (state: ICartState) => state.items
)