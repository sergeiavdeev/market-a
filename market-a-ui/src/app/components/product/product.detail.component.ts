import {Component} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Product} from "../../entities/product";
import {Store} from "@ngrx/store";
import {IAppState} from "../../store/state/app.state";
import {selectProduct} from "../../store/selectors/products.selector";
import {EProductsActions} from "../../store/actions/products.actions";
import {environment} from "../../../environments/environment";
import {ExtFile} from "../../entities/ext.file";

@Component({
    selector: 'product-detail',
    templateUrl: './product.detail.component.html'
})
export class ProductDetailComponent {

    product: Product = new Product();
    filePath: string = environment.fileStoragePath + "product/";

    product$ = this.store.select(selectProduct)
        .subscribe(value => this.product = value);

    constructor(private router: ActivatedRoute, private store: Store<IAppState>) {
    }

    ngOnInit() {
        this.store.dispatch({type: EProductsActions.GetById, payload: this.router.snapshot.params["id"]});
    }

    deleteFile(file: ExtFile) {
        this.store.dispatch({type: EProductsActions.DeleteFile, payload: file});
    }

    uploadFile(event: any) {
        this.store.dispatch({
            type: EProductsActions.AddFile,
            payload: {ownerId: this.product.id, file: event.target.files[0]}
        })
    }
}