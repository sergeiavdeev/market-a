import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {ProductService} from "../../services/product.service";
import {catchError, map, mergeMap, of} from "rxjs";
import {Add, Delete, EProductsActions, GetById, GetPage, SetPageParams, Update} from "../actions/products.actions";
import {Product} from "../../entities/product";
import {Router} from "@angular/router";

@Injectable()
export class ProductEffects {

    getPage$ = createEffect(() => this.actions$.pipe(
            ofType(EProductsActions.GetPage),
            mergeMap((action: GetPage) => this.productService.getPage(action.payload.page, action.payload.size)
                .pipe(
                    map((products) => ({type: EProductsActions.GetPageSuccess, payload: products})),
                    catchError((error) => of({type: EProductsActions.GetPageError, payload: error.message}))
                ))
        )
    );

    add$ = createEffect(() => this.actions$.pipe(
            ofType('[Product] add new'),
            mergeMap((action: Add) => this.productService.add(action.payload)
                .pipe(
                    map((product) => ({type: EProductsActions.AddSuccess, payload: product})),
                    catchError((error) => of({type: EProductsActions.AddError, payload: error}))
                ))
        )
    );

    getById$ = createEffect(() => this.actions$.pipe(
            ofType(EProductsActions.GetById),
            mergeMap((action: GetById) => this.productService.getById(action.payload)
                .pipe(
                    map((product: Product) => ({type: EProductsActions.GetByIdSuccess, payload: product})),
                    catchError((error) => of({type: EProductsActions.GetByIdError, payload: error.message}))
                ))
        )
    );

    update$ = createEffect(() => this.actions$.pipe(
            ofType('[Product] update'),
            mergeMap((action: Update) => this.productService.update(action.payload)
                .pipe(
                    map((product) => ({type: EProductsActions.UpdateSuccess, payload: product})),
                    catchError((error) => of({type: EProductsActions.UpdateError, payload: error}))
                ))
        )
    );

    delete$ = createEffect(() => this.actions$.pipe(
            ofType('[Product] delete'),
            mergeMap((action: Delete) => this.productService.delete(action.payload)
                .pipe(
                    map((product) => ({type: EProductsActions.DeleteSuccess, payload: product})),
                    catchError((error) => of({type: EProductsActions.DeleteError, payload: error})
                    )
                ))
        )
    );

    setPageParams$ = createEffect(() => this.actions$.pipe(
            ofType(EProductsActions.SetPageParams),
            map((action: SetPageParams) => ({type: EProductsActions.GetPage, payload: action.payload}))
        )
    );

    addProducts$ = createEffect(() => this.actions$.pipe(
        ofType(EProductsActions.AddProducts),
        mergeMap((action: GetPage) => this.productService.getPage(action.payload.page, action.payload.size)
            .pipe(
                map((products) => ({type: EProductsActions.AddProductsSuccess, payload: products})),
                catchError((error) => of({type: EProductsActions.GetPageError, payload: error.message}))
            ))
    ));

    authErrorHandler$ = createEffect(() => this.actions$.pipe(
        ofType(
            EProductsActions.AddError,
            EProductsActions.DeleteError,
            EProductsActions.UpdateError),
        map((action: any) => {
            if (action.payload.status == 401 || action.payload.status == 403) {
                this.router.navigate(['/auth'])
                    .catch((e) => console.log(e));
            }
        })
    ), {dispatch: false});

    constructor(
        private actions$: Actions,
        private productService: ProductService,
        private router: Router) {
    }
}
