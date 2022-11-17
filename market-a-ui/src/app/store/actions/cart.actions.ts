import {Action} from "@ngrx/store";
import {CartRow} from "../../entities/cart.row";

export enum ECartActions {
    Add = "[Cart] add",
    Delete = "[Cart] delete",
    SetCount = "[Cart] set count",
    LoadFromStorage = "[Cart] load from storage"
}

export class Add implements Action {
    public readonly type = ECartActions.Add;

    constructor(public payload: string) {
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

    constructor(public payload: CartRow[]) {
    }
}


export type CartActions = Add | Delete | SetCount | LoadFromStorage;



