import {ICartState, initialCartState} from "../state/cart.state";
import {CartActions, ECartActions} from "../actions/cart.actions";
import {CartItem} from "../../entities/cart.item";

export const cartReducer = (
    state = initialCartState,
    action: CartActions
): ICartState => {

    let cartRows: CartItem[];
    let tmpRow;
    switch (action.type) {
        case ECartActions.Add:
            cartRows = state.items.slice(0);
            tmpRow = cartRows.findIndex(el => el.productId == action.payload.productId);
            if (tmpRow == -1) {
                cartRows.push({
                    quantity: 1,
                    productId: action.payload.productId,
                    price: action.payload.price,
                    total: action.payload.price
                });
            } else {
                cartRows[tmpRow] = {
                    quantity: cartRows[tmpRow].quantity + 1,
                    productId: action.payload.productId,
                    price: action.payload.price,
                    total: action.payload.price * (cartRows[tmpRow].quantity + 1)
                };
            }
            return {
                ...state,
                items: cartRows,
                total: total(cartRows)
            }
        case ECartActions.Delete:
            cartRows = state.items.slice(0);
            tmpRow = cartRows.findIndex(el => el.productId == action.payload);
            if (tmpRow != -1) cartRows.splice(tmpRow, 1);
            return {
                ...state,
                items: cartRows,
                total: total(cartRows)
            }
        case ECartActions.SetCount:
            cartRows = state.items.slice(0);
            tmpRow = cartRows.findIndex(el => el.productId == action.payload.productId);
            if (tmpRow == -1) {
                cartRows.push({
                    quantity: action.payload.quantity,
                    productId: action.payload.productId,
                    price: action.payload.price,
                    total: action.payload.price
                });
            } else {
                cartRows[tmpRow] = {
                    quantity: action.payload.quantity,
                    productId: action.payload.productId,
                    price: action.payload.price,
                    total: action.payload.price * action.payload.quantity
                };
            }
            return {
                ...state,
                items: cartRows,
                total: total(cartRows)
            }
        case ECartActions.LoadFromStorage:
            return state;
        case ECartActions.LoadFromStorageSuccess:
            return action.payload;
        default:
            return state;
    }
}

function total(items: CartItem[]): number {
    let res = 0;
    items.map(el => {
        res = res + el.total;
    });
    return res;
}