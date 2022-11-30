import {Action} from "@ngrx/store";
import {CartItem} from "../../entities/cart.item";
import {ICartState} from "../state/cart.state";
import {HttpResponse} from "@angular/common/http";

export enum ECartActions {
    Add = "[Cart] add",
    Delete = "[Cart] delete",
    SetCount = "[Cart] set count",
    LoadFromStorage = "[Cart] load from storage",
    LoadFromStorageSuccess = "[Cart] load from storage success",
    SaveToStorage = "[Cart] save to storage",
    SaveToStorageSuccess = "[Cart] save to storage success",
    LoadFromApi = "[Cart] load from API",
    LoadFromApiSucces = "[Cart] load from API success",
    LoadFromApiError = "[Cart] load from API error"
}

export class Add implements Action {
    public readonly type = ECartActions.Add;

    constructor(public payload: CartItem) {
    }
}

export class Delete implements Action {
    public readonly type = ECartActions.Delete;

    constructor(public payload: string) {
    }
}

export class SetCount implements Action {
    public readonly type = ECartActions.SetCount;

    constructor(public payload: CartItem) {
    }
}

export class LoadFromStorage implements Action {
    public readonly type = ECartActions.LoadFromStorage;

    constructor() {
    }
}

export class LoadFromStorageSuccess implements Action {
    public readonly type = ECartActions.LoadFromStorageSuccess;

    constructor(public payload: ICartState) {
    }
}

export class SaveToStorage implements Action {
    public readonly type = ECartActions.SaveToStorage;

    constructor(public payload: ICartState) {
    }
}

export class SaveToStorageSuccess implements Action {
    public readonly type = ECartActions.SaveToStorageSuccess;

    constructor() {
    }
}

export class LoadFromApi implements Action {
    public readonly type = ECartActions.LoadFromApi;

    constructor() {
    }
}

export class LoadFromApiSuccess implements Action {
    public readonly type = ECartActions.LoadFromApiSucces;

    constructor(payload: ICartState) {
    }
}

export class LoadFromApiError implements Action {
    public readonly type = ECartActions.LoadFromApiError;

    constructor(payload: HttpResponse<any>) {
    }
}


export type CartActions = Add | Delete | SetCount | LoadFromStorage | LoadFromStorageSuccess
    | SaveToStorage | SaveToStorageSuccess
    | LoadFromApi | LoadFromApiSuccess | LoadFromApiError;



