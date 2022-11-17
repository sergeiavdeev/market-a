import {initialProductsState, IProductsState} from "../state/products.state";
import {EProductsActions, ProductsActions} from "../actions/products.actions";
import {Product} from "../../entities/product";

export const productsReducer = (
    state = initialProductsState,
    action: ProductsActions
): IProductsState => {

    let pList: Product[];

    switch (action.type) {
        case EProductsActions.GetPage:
            return {
                ...state,
                pending: true
            };
        case EProductsActions.GetPageSuccess:
            return {
                ...state,
                productPage: action.payload.content,
                pageCount: action.payload.totalPages,
                pending: false
            };
        case EProductsActions.AddProducts:
            return {
                ...state,
                pending: true
            };
        case EProductsActions.AddProductsSuccess:
            const newProducts = action.payload.content;
            pList = state.productList.slice(0);
            newProducts.map((el) => pList.push(el));
            return {
                ...state,
                productList: pList,
                pageCount: action.payload.totalPages,
                loadedPages: action.payload.number,
                pending: false
            };
        case EProductsActions.GetPageError:
            return {
                ...state,
                error: action.payload,
                pending: false
            };
        case EProductsActions.GetById:
            return {
                ...state,
                pending: true,
                selectedProduct: new Product()
            };
        case EProductsActions.GetByIdSuccess:
            return {
                ...state,
                pending: false,
                selectedProduct: action.payload
            };
        case EProductsActions.GetByIdError:
            return {
                ...state,
                pending: false,
                error: action.payload,
                selectedProduct: new Product()
            };
        case EProductsActions.Add:
            return {
                ...state,
                pending: true
            };
        case EProductsActions.AddSuccess:
            return {
                ...state,
                error: "",
                selectedProduct: action.payload,
                pending: false
            };
        case EProductsActions.AddError:
            return {
                ...state,
                error: action.payload,
                pending: false
            };
        case EProductsActions.Update:
            return {
                ...state,
                pending: true
            };
        case EProductsActions.UpdateSuccess:
            pList = state.productPage.slice(0);
            for (let i = 0; i < pList.length; i++) {
                if (pList[i].id == action.payload.id) pList[i] = action.payload;
            }
            return {
                ...state,
                error: "",
                productPage: pList,
                selectedProduct: action.payload,
                pending: false
            };
        case EProductsActions.UpdateError:
            return {
                ...state,
                error: action.payload,
                pending: false
            };
        case EProductsActions.Delete:
            return {
                ...state,
                pending: true
            };
        case EProductsActions.DeleteSuccess:
            return {
                ...state,
                error: "",
                selectedProduct: new Product(),
                pending: false,
            };
        case EProductsActions.DeleteError:
            return {
                ...state,
                error: action.payload,
                pending: false
            };
        case EProductsActions.SetPageParams:
            return {
                ...state,
                pageNumber: action.payload.page,
                pageSize: action.payload.size
            };
        default:
            return state;
    }
}
