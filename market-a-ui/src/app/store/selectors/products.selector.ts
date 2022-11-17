import {IAppState} from "../state/app.state";
import {createSelector} from "@ngrx/store";
import {IProductsState} from "../state/products.state";

const productsSelector = (state: IAppState) => state.products;

export const selectProductPage = createSelector(
  productsSelector,
  (state: IProductsState) => state.productPage
);

export const selectProduct = createSelector(
  productsSelector,
  (state: IProductsState) => state.selectedProduct
);

export const selectPage = createSelector(
  productsSelector,
  (state: IProductsState) => state.pageNumber
)

export const selectPages = createSelector(
  productsSelector,
  (state: IProductsState) => state.pageCount
)

export const selectPageSize = createSelector(
  productsSelector,
  (state: IProductsState) => state.pageSize
)

export const selectProductList = createSelector(
    productsSelector,
    (state: IProductsState) => state.productList
)

export const selectLoadedPages = createSelector(
    productsSelector,
    (state: IProductsState) => state.loadedPages
)
