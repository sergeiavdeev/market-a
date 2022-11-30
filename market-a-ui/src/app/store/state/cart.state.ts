import {CartItem} from "../../entities/cart.item";

export interface ICartState {
    createdAt: Date;
    total: number;
    items: CartItem[];
}

export const initialCartState: ICartState = iniCart()


function iniCart(): ICartState {

    let cart = localStorage.getItem("cart");
    console.log("read cart");
    if (cart) {
        return JSON.parse(cart);
    }

    return {
        createdAt: new Date(),
        total: 0,
        items: []
    }
}
