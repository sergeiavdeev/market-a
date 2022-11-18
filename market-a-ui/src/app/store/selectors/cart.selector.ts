import {IAppState} from "../state/app.state";
import {createSelector} from "@ngrx/store";
import {ICartState} from "../state/cart.state";

const cartSelector = (state: IAppState) => state.cart;

export const selectCart = createSelector(
    cartSelector,
    (state: ICartState) => state.cartRows
);

export const selectCartCount = createSelector(
    cartSelector,
    (state: ICartState) => {
        let count = 0;
        state.cartRows.map(el => count = count + el.count);
        return count;
    }
)

export const selectCartSum = createSelector(
    cartSelector,
    (state: ICartState) => {
        let sum = 0;
        state.cartRows.map(el => sum = sum + el.count * el.product.price);
        return sum;
    }
)