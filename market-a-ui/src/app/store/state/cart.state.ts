import {CartRow} from "../../entities/cart.row";

export interface ICartState {
    cartRows: CartRow[];
}

export const initialCartState: ICartState = {
    cartRows: []
}

