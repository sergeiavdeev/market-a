import {Action} from "@ngrx/store";
import {CartRow} from "../../entities/cart.row";
import {Product} from "../../entities/product";

export enum ECartActions {
    Add = "[Cart] add",
    Delete = "[Cart] delete",
    SetCount = "[Cart] set count",
    LoadFromStorage = "[Cart] load from storage",
    LoadFromStorageSuccess = "[Cart] load from storage success",
    SaveToStorage = "[Cart] save to storage",
    SaveToStorageSuccess = "[Cart] save to storage success"
}

export class Add implements Action {
    public readonly type = ECartActions.Add;

    constructor(public payload: Product) {
    }
}

export class Delete implements Action {
    public readonly type = ECartActions.Delete;

    constructor(public payload: string) {
    }
}

export class SetCount implements Action {
    public readonly type = ECartActions.SetCount;

    constructor(public payload: CartRow) {
    }
}

export class LoadFromStorage implements Action {
    public readonly type = ECartActions.LoadFromStorage;

    constructor() {
    }
}

export class LoadFromStorageSuccess implements Action {
    public readonly type = ECartActions.LoadFromStorageSuccess;

    constructor(public payload: CartRow[]) {
    }
}

export class SaveToStorage implements Action {
    public readonly type = ECartActions.SaveToStorage;

    constructor(public payload: CartRow[]) {
    }
}

export class SaveToStorageSuccess implements Action {
    public readonly type = ECartActions.SaveToStorageSuccess;

    constructor() {
    }
}


export type CartActions = Add | Delete | SetCount | LoadFromStorage | LoadFromStorageSuccess
    | SaveToStorage | SaveToStorageSuccess;



