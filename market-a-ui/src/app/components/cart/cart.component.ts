import {Component} from '@angular/core';
import {IAppState} from "../../store/state/app.state";
import {Store} from "@ngrx/store";
import {selectCart, selectCartCount, selectCartSum} from "../../store/selectors/cart.selector";
import {environment} from "../../../environments/environment";
import {ECartActions} from "../../store/actions/cart.actions";
import {Product} from "../../entities/product";

@Component({
    selector: 'cart-component',
    templateUrl: './cart.component.html'
})
export class CartComponent {
    title = 'web-app';

    filePath: string = environment.fileStoragePath + "product/";

    cart$ = this.store.select(selectCart);
    cartCount$ = this.store.select(selectCartCount);
    cartSum$ = this.store.select(selectCartSum);

    constructor(private store: Store<IAppState>) {
    }

    deleteFromCart(id: String) {
        this.store.dispatch({type: ECartActions.Delete, payload: id});
    }

    countChange(value: string, product: Product) {
        this.store.dispatch({type: ECartActions.SetCount, payload: {count: Number(value), product: product}});
    }
}
