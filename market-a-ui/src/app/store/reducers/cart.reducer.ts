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
            tmpRow = cartRows.findIndex(el => el.productId == action.payload);
            if (tmpRow == -1) {
                cartRows.push({productId: action.payload, count: 1});
            } else {
                cartRows[tmpRow] = {productId: action.payload, count: cartRows[tmpRow].count + 1};
            }
            return {
                ...state,
                cartRows: cartRows
            }
        case ECartActions.Delete:
            cartRows = state.cartRows.slice(0);
            cartRows.map((el, index) => {
                if (el.productId == action.payload) {
                    delete cartRows[index];
                    return;
                }
                return el;
            });
            return {
                ...state,
                cartRows
            }
        case ECartActions.SetCount:
            cartRows = state.cartRows.slice(0);
            tmpRow = cartRows.findIndex(el => el.productId == action.payload.productId);
            if (tmpRow == -1) {
                cartRows.push({productId: action.payload.productId, count: action.payload.count});
            } else {
                cartRows[tmpRow] = {productId: action.payload.productId, count: action.payload.count};
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