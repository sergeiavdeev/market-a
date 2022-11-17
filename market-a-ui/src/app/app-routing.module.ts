import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CartComponent} from "./components/cart/cart.component";
import {ProductGridComponent} from "./components/product/product.grid.component";
import {HomeComponent} from "./components/home/home.component";
import {AuthComponent} from "./components/auth/auth.component";
import {ProductDetailComponent} from "./components/product/product.detail.component";

const routes: Routes = [
    {
        path: 'products',
        component: ProductGridComponent
    },
    {
        path: 'products/:id',
        component: ProductDetailComponent
    },
    {
        path: 'contacts',
        component: HomeComponent
    },
    {
        path: 'cart',
        component: CartComponent
    },
    {
        path: 'auth',
        component: AuthComponent
    },
    {
        path: '**',
        redirectTo: 'products'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
