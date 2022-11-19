import {Component, Input} from "@angular/core";
import {Product} from "../../entities/product";
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";
import {Store} from "@ngrx/store";
import {IAppState} from "../../store/state/app.state";
import {ECartActions} from "../../store/actions/cart.actions";

@Component({
    selector: 'product-sm-card',
    templateUrl: './product.small.card.component.html'
})
export class ProductSmallCardComponent {

    @Input()
    product: Product = new Product();

    filePath: string = environment.fileStoragePath + "product/";

    constructor(private router: Router, private store: Store<IAppState>) {

    }

    showDetails(id: String) {
        this.router.navigate(['products', id])
            .then()
            .catch((e) => console.log(e));
    }

    addToCart() {
        this.store.dispatch({type: ECartActions.Add, payload: this.product});
    }
}