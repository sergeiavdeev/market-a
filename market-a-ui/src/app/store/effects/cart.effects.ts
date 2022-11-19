import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {map, mergeMap} from "rxjs";
import {CartService} from "../../services/cart.service";
import {ECartActions, LoadFromStorage, SaveToStorage} from "../actions/cart.actions";

@Injectable()
export class CartEffects {

    load$ = createEffect(() => this.actions$.pipe(
            ofType(ECartActions.LoadFromStorage),
            mergeMap((action: LoadFromStorage) => this.cartService.load()
                .pipe(
                    map((cart) => ({type: ECartActions.LoadFromStorageSuccess, payload: cart}))
                ))
        )
    );

    save$ = createEffect(() => this.actions$.pipe(
        ofType(ECartActions.SaveToStorage),
        mergeMap((action: SaveToStorage) => this.cartService.save(action.payload)
            .pipe(
                map((v => ({type: ECartActions.SaveToStorageSuccess})))
            ))
    ));

    constructor(private cartService: CartService, private actions$: Actions) {
    }

}