import {Injectable} from "@angular/core";
import {map, Observable} from "rxjs";
import {ICartState, initialCartState} from "../store/state/cart.state";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

const apiPath = environment.apiPath + "cart/";

@Injectable({providedIn: 'root'})
export class CartService {

    constructor(private http: HttpClient) {
    }

    load(): Observable<ICartState> {
        return new Observable<ICartState>((observer) => {

            let cart = localStorage.getItem("cart");
            console.log("read cart");
            if (cart) {
                observer.next(JSON.parse(cart));
            } else {
                observer.next(initialCartState);
            }
        });
    }

    save(cart: ICartState): Observable<any> {
        return new Observable<any>((observer) => {
            if (cart.items.length == 0) {
                localStorage.removeItem("cart");
                console.log("remove cart")
            } else {
                localStorage.setItem("cart", JSON.stringify(cart));
                console.log("write cart");
            }
            observer.next(cart);
        });
    }

    get(): Observable<ICartState> {
        return this.http.get<ICartState>(apiPath)
            .pipe(map(response => {
                if (!response) {
                    return {
                        createdAt: new Date('1970-01-01'),
                        total: 0,
                        items: []
                    };
                }
                return response;
            }));
    }
}