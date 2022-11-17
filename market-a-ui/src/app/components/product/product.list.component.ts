import {ChangeDetectorRef, Component} from '@angular/core';
import {Store} from "@ngrx/store";
import {
    selectPage,
    selectPages,
    selectPageSize,
    selectProduct,
    selectProductPage
} from "../../store/selectors/products.selector";
import {IAppState} from "../../store/state/app.state";
import {EProductsActions} from "../../store/actions/products.actions";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Product} from "../../entities/product";

@Component({
    selector: 'product-list',
    templateUrl: './product.list.component.html'
})
export class ProductListComponent {

    selectedProduct$ = this.store.select(selectProduct);
    page = 1;
    size = 0;
    productList: Product[] = [];
    pagesCount = 0;
    modalVisible = false;
    form = new FormGroup({
        title: new FormControl({value: '', disabled: true}, Validators.required),
        price: new FormControl({
            value: 0.00,
            disabled: true
        }, [Validators.required, Validators.pattern('^[0-9]*[.]?[0-9]+$')]),
        id: new FormControl('')
    });
    isNewProduct = false;
    isDelete = false;

    page$ = this.store.select(selectPage).subscribe((p) => {
        this.page = p;
    });
    size$ = this.store.select(selectPageSize).subscribe((s) => {
        this.size = s;
    });

    pages$ = this.store.select(selectPages).subscribe((s) => {
        this.pagesCount = s;
    });

    productList$ = this.store.select(selectProductPage).subscribe((list) => {
        this.productList = list;
    });

    constructor(private store: Store<IAppState>,
                private changeDetector: ChangeDetectorRef) {
    }

    ngOnInit() {
        if (this.productList.length == 0) {
            this.store.dispatch({type: EProductsActions.SetPageParams, payload: {page: 1, size: 8}});
        }
    }

    ngAfterContentChecked(): void {
        this.changeDetector.detectChanges();
    }

    setPage(page: number) {
        this.store.dispatch({type: EProductsActions.SetPageParams, payload: {page: page, size: this.size}});
        return false;
    }

    editClick(id: String) {
        this.form.controls.price.enable();
        this.form.controls.title.enable();
        this.modalVisible = true;
        this.store.dispatch({type: EProductsActions.GetById, payload: id});
    }

    deleteClick(id: String) {
        this.isDelete = true;
        this.form.controls.price.disable();
        this.form.controls.title.disable();
        this.modalVisible = true;
        this.store.dispatch({type: EProductsActions.GetById, payload: id});
    }

    addClick() {
        this.form.controls.title.setValue('');
        this.form.controls.price.setValue(0);
        this.form.controls.price.enable();
        this.form.controls.title.enable();
        this.isNewProduct = true;
        this.modalVisible = true;
    }

    saveProduct() {

        if (this.isNewProduct) {
            this.store.dispatch({
                type: '[Product] add new',
                payload: {title: this.form.controls.title.value, price: this.form.controls.price.value}
            });
        } else if (this.isDelete) {
            this.store.dispatch({
                type: '[Product] delete',
                payload: {id: this.form.controls.id.value}
            });
        } else {
            this.store.dispatch({
                type: '[Product] update',
                payload: {
                    title: this.form.controls.title.value,
                    price: this.form.controls.price.value,
                    id: this.form.controls.id.value
                }
            });
        }
        this.closeModal();
    }

    closeModal() {
        this.isNewProduct = false;
        this.isDelete = false;
        this.modalVisible = false;
    }

    previousPage() {
        return this.setPage(this.page - 1);
    }

    nextPage() {
        return this.setPage(this.page + 1);
    }
}
