import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {StoreModule} from '@ngrx/store';
import {StoreRouterConnectingModule} from '@ngrx/router-store';
import {EffectsModule} from '@ngrx/effects';
import {StoreDevtoolsModule} from '@ngrx/store-devtools';
import {environment} from '../environments/environment';
import {appReducer, metaReducers} from "./store/reducers/app.reducer";
import {NavComponent} from "./components/nav/nav.component";
import {HomeComponent} from "./components/home/home.component";
import {ProductListComponent} from "./components/product/product.list.component";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {CartComponent} from "./components/cart/cart.component";
import {ProductEffects} from "./store/effects/product.effects";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SetValueDirective} from "./services/set-value.directive";
import {AuthComponent} from "./components/auth/auth.component";
import {ProductGridComponent} from "./components/product/product.grid.component";
import {ProductSmallCardComponent} from "./components/product/product.small.card.component";
import {AuthEffects} from "./store/effects/auth.effects";
import {AuthInterceptor} from "./services/auth.interceptor";
import {ProductDetailComponent} from "./components/product/product.detail.component";

@NgModule({
    declarations: [
        AppComponent,
        NavComponent,
        HomeComponent,
        ProductListComponent,
        CartComponent,
        SetValueDirective,
        AuthComponent,
        ProductGridComponent,
        ProductSmallCardComponent,
        ProductDetailComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        StoreModule.forRoot(appReducer, {
            metaReducers,
            runtimeChecks: {
                strictStateImmutability: true,
                strictActionImmutability: true
            }
        }),
        StoreRouterConnectingModule.forRoot(),
        EffectsModule.forRoot([ProductEffects, AuthEffects]),
        StoreDevtoolsModule.instrument({maxAge: 25, logOnly: environment.production}),
        NgbModule
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
