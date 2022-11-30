import {Component} from '@angular/core';
import {IAppState} from "../../store/state/app.state";
import {Store} from "@ngrx/store";
import {selectCart, selectCartCount, selectCartItems, selectCartSum} from "../../store/selectors/cart.selector";
import {environment} from "../../../environments/environment";
import {ECartActions} from "../../store/actions/cart.actions";
import {ProductService} from "../../services/product.service";
import {ICartState} from "../../store/state/cart.state";
import {Product} from "../../entities/product";

@Component({
    selector: 'cart-component',
    templateUrl: './cart.component.html'
})
export class CartComponent {
    title = 'web-app';

    products = new Map();
    filePath: string = environment.fileStoragePath + "product/";
    cart?: ICartState;
    cart$ = this.store.select(selectCart).subscribe(
        (v) => {
            this.cart = v;

            this.cart.items.map(el => {
                this.service.getById(el.productId).subscribe((p) => {
                    this.products.set(el.productId, p);
                })
            });
        }
    );
    cartItems$ = this.store.select(selectCartItems);
    cartCount$ = this.store.select(selectCartCount);
    cartSum$ = this.store.select(selectCartSum);

    constructor(private store: Store<IAppState>, private service: ProductService) {
    }

    deleteFromCart(id: String) {
        this.store.dispatch({type: ECartActions.Delete, payload: id});
    }

    countChange(value: string, productId: String, price: number) {
        this.store.dispatch({
            type: ECartActions.SetCount,
            payload: {quantity: Number(value), productId: productId, price: price}
        });
    }

    getProduct(id: String): Product {

        let p = this.products.get(id);
        if (p == undefined) {
            return new Product();
        }
        return p;
    }
}
