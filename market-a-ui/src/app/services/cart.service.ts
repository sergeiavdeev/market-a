import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {CartRow} from "../entities/cart.row";

@Injectable({providedIn: 'root'})
export class CartService {

    constructor() {
    }

    load(): Observable<CartRow[]> {
        return new Observable<CartRow[]>((observer) => {

            let cart = localStorage.getItem("cart");
            console.log("read cart");
            if (cart) {
                observer.next(JSON.parse(cart));
            } else {
                observer.next([]);
            }
        });
    }

    save(cart: CartRow[]): Observable<any> {
        return new Observable<any>((observer) => {
            if (cart.length == 0) {
                localStorage.removeItem("cart");
                console.log("remove cart")
            } else {
                localStorage.setItem("cart", JSON.stringify(cart));
                console.log("write cart");
            }
            observer.next(cart);
        });
    }
}