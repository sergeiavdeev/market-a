import {Product} from "../../entities/product";

export interface IProductsState {
    productPage: Product[];
    selectedProduct: Product;
    error: Object;
    pending: boolean;
    pageNumber: number,
    pageSize: number,
    pageCount: number,
    loadedPages: number,
    productList: Product[]
}

export const initialProductsState: IProductsState = {
    productPage: [],
    selectedProduct: new Product(),
    error: "",
    pending: false,
    pageNumber: 1,
    pageSize: 6,
    pageCount: 0,
    loadedPages: 0,
    productList: []
}
