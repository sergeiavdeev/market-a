import {Action} from "@ngrx/store";
import {PageRequest, PageResponse, Product} from "../../entities/product";
import {HttpResponse} from "@angular/common/http";
import {ExtFile} from "../../entities/ext.file";

export enum EProductsActions {
    GetPage = '[Products] get page',
    GetPageSuccess = '[Products] get page success',
    GetPageError = '[Products] get page error',
    GetById = '[Product] get by id',
    GetByIdSuccess = '[Product] get by id success',
    GetByIdError = '[Product] get by id error',
    Add = '[Product] add new',
    AddSuccess = '[Product] add success',
    AddError = '[Product] add error',
    Delete = '[Product] delete',
    DeleteSuccess = '[Product] delete success',
    DeleteError = '[Product] delete error',
    Update = '[Product] update',
    UpdateSuccess = '[Product] update success',
    UpdateError = '[Product] update error',
    SetPageParams = '[Products] set page number',
    AddProducts = '[Products] add products',
    AddProductsSuccess = '[Products] add products success',
    DeleteFile = "[Products] delete file",
    DeleteFileSuccess = "[Products] delete file success",
    AddFile = "[Products] add file",
    AddFileSuccess = "[Products] add file success"
}

export class GetPage implements Action {
    public readonly type = EProductsActions.GetPage;

    constructor(public payload: PageRequest) {
    }
}

export class GetPageSuccess implements Action {
    public readonly type = EProductsActions.GetPageSuccess;

    constructor(public payload: PageResponse) {
    }
}

export class GetPageError implements Action {
    public readonly type = EProductsActions.GetPageError;

    constructor(public payload: String) {
    }
}

export class GetById implements Action {
    public readonly type = EProductsActions.GetById;

    constructor(public payload: String) {
    }
}

export class GetByIdSuccess implements Action {
    public readonly type = EProductsActions.GetByIdSuccess;

    constructor(public payload: Product) {
    }
}

export class GetByIdError implements Action {
    public readonly type = EProductsActions.GetByIdError;

    constructor(public payload: String) {
    }
}

export class Add implements Action {
    public readonly type = EProductsActions.Add;

    constructor(public payload: Product) {
    }
}

export class AddSuccess implements Action {
    public readonly type = EProductsActions.AddSuccess;

    constructor(public payload: Product) {
    }
}

export class AddError implements Action {
    public readonly type = EProductsActions.AddError;

    constructor(public payload: HttpResponse<any>) {
    }
}

export class Update implements Action {
    public readonly type = EProductsActions.Update;

    constructor(public payload: Product) {
    }
}

export class UpdateSuccess implements Action {
    public readonly type = EProductsActions.UpdateSuccess;

    constructor(public payload: Product) {
    }
}

export class UpdateError implements Action {
    public readonly type = EProductsActions.UpdateError;

    constructor(public payload: HttpResponse<any>) {
    }
}

export class Delete implements Action {
    public readonly type = EProductsActions.Delete;

    constructor(public payload: Product) {
    }
}

export class DeleteSuccess implements Action {
    public readonly type = EProductsActions.DeleteSuccess;

    constructor(public payload: any) {
    }
}

export class DeleteError implements Action {
    public readonly type = EProductsActions.DeleteError;

    constructor(public payload: HttpResponse<any>) {
    }
}

export class SetPageParams implements Action {
    public readonly type = EProductsActions.SetPageParams;

    constructor(public payload: PageRequest) {
    }
}

export class AddProducts implements Action {
    public readonly type = EProductsActions.AddProducts;

    constructor(public payload: PageRequest) {
    }
}

export class AddProductsSuccess implements Action {
    public readonly type = EProductsActions.AddProductsSuccess;

    constructor(public payload: PageResponse) {
    }
}

export class DeleteFile implements Action {
    public readonly type = EProductsActions.DeleteFile;

    constructor(public payload: ExtFile) {
    }
}

export class DeleteFileSuccess implements Action {
    public readonly type = EProductsActions.DeleteFileSuccess;

    constructor(public payload: ExtFile) {
    }
}

export class AddFile implements Action {
    public readonly type = EProductsActions.AddFile;

    constructor(public payload: ExtFile) {
    }
}

export class AddFileSuccess implements Action {
    public readonly type = EProductsActions.AddFileSuccess;

    constructor(public payload: ExtFile) {
    }
}

export type ProductsActions =
    GetPage | GetPageSuccess | GetPageError
    | GetById | GetByIdSuccess | GetByIdError
    | Add | AddSuccess | AddError
    | Update | UpdateSuccess | UpdateError
    | Delete | DeleteSuccess | DeleteError
    | SetPageParams | AddProducts | AddProductsSuccess
    | DeleteFile | DeleteFileSuccess
    | AddFile | AddFileSuccess;
