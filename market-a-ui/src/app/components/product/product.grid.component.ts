import {Component} from "@angular/core";
import {Store} from "@ngrx/store";
import {IAppState} from "../../store/state/app.state";
import {selectLoadedPages, selectPages, selectProductList} from "../../store/selectors/products.selector";
import {Product} from "../../entities/product";
import {EProductsActions} from "../../store/actions/products.actions";

@Component({
    selector: 'product-grid',
    templateUrl: './product.grid.component.html'
})
export class ProductGridComponent {

    productList: Product[] = [];
    loadedPages: number = 0;
    pageCount: number = 0;

    productList$ = this.store.select(selectProductList).subscribe((list) => {
        this.productList = list;
    });

    loadedPages$ = this.store.select(selectLoadedPages).subscribe((lp) => {
        this.loadedPages = lp;
    });

    pageCount$ = this.store.select(selectPages).subscribe((p) => {
        this.pageCount = p;
    });

    constructor(private store: Store<IAppState>) {
    }

    ngOnInit() {
        if (this.productList.length == 0) {
            this.store.dispatch({type: EProductsActions.AddProducts, payload: {page: 1, size: 50}});
        }
    }

    moreClick() {
        if (this.loadedPages < this.pageCount) {
            this.store.dispatch({type: EProductsActions.AddProducts, payload: {page: this.loadedPages + 1, size: 8}});
        }
        ``
    }
}