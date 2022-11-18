import {ICartState, initialCartState} from "../state/cart.state";
import {CartActions, ECartActions} from "../actions/cart.actions";
import {CartRow} from "../../entities/cart.row";

export const cartReducer = (
    state = initialCartState,
    action: CartActions
): ICartState => {

    let cartRows: CartRow[];
    let tmpRow;
    switch (action.type) {
        case ECartActions.Add:
            cartRows = state.cartRows.slice(0);
            tmpRow = cartRows.findIndex(el => el.product.id == action.payload.id);
            if (tmpRow == -1) {
                cartRows.push({count: 1, product: action.payload});
            } else {
                cartRows[tmpRow] = {
                    count: cartRows[tmpRow].count + 1,
                    product: action.payload
                };
            }
            return {
                ...state,
                cartRows: cartRows
            }
        case ECartActions.Delete:
            cartRows = state.cartRows.slice(0);
            tmpRow = cartRows.findIndex(el => el.product.id == action.payload);
            if (tmpRow != -1) cartRows.splice(tmpRow, 1);
            return {
                ...state,
                cartRows
            }
        case ECartActions.SetCount:
            cartRows = state.cartRows.slice(0);
            tmpRow = cartRows.findIndex(el => el.product.id == action.payload.product.id);
            if (tmpRow == -1) {
                cartRows.push({
                    count: action.payload.count,
                    product: action.payload.product
                });
            } else {
                cartRows[tmpRow] = {
                    count: action.payload.count,
                    product: action.payload.product
                };
            }
            return {
                ...state,
                cartRows
            }
        case ECartActions.LoadFromStorage:
            cartRows = action.payload;
            return {
                ...state,
                cartRows
            }
        default:
            return state;
    }
}