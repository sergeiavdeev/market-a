import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {PageResponse, Product} from "../entities/product";
import {environment} from "../../environments/environment";

const apiPath = environment.apiPath + "product/";

@Injectable({providedIn: 'root'})
export class ProductService {
    constructor(private http: HttpClient) {
    }
    
    getPage(page: number, size: number): Observable<PageResponse> {
        return this.http.get<PageResponse>(apiPath,
            {
                params: {
                    page: page,
                    size: size,
                    sort: 'title'
                }
            })
            .pipe(map(response => {
                if (!response) {
                    return new PageResponse();
                }
                return response;
            }));
    }

    add(product: Product): Observable<Product> {
        return this.http.post<Product>(apiPath, product)
            .pipe(map((product) => {
                return product;
            }));
    }

    getById(id: String): Observable<Product> {
        return this.http.get<Product>(apiPath + id)
            .pipe(map((product) => {
                return product;
            }))
    }

    update(product: Product): Observable<Product> {
        return this.http.put<Product>(apiPath, product)
            .pipe(map((product) => {
                return product;
            }));
    }

    delete(product: Product): Observable<any> {
        return this.http.delete(apiPath + product.id)
            .pipe(map((response) => {
                return response;
            }));
    }
}
