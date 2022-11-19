import {CartRow} from "../../entities/cart.row";

export interface ICartState {
    cartRows: CartRow[];
}

export const initialCartState: ICartState = {
    cartRows: function () {
        let cart = localStorage.getItem("cart");
        console.log("Initial cart state");
        if (cart) {
            return JSON.parse(cart);
        }
        return [];
    }()
}

