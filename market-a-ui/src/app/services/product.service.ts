import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {PageResponse, Product} from "../entities/product";
import {environment} from "../../environments/environment";
import {ExtFile} from "../entities/ext.file";

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

    deleteFile(productId: String, fileId: String): Observable<any> {
        return this.http.delete(apiPath + productId + "/file/" + fileId)
            .pipe(map((response) => response));
    }

    uploadFile(file: ExtFile): Observable<any> {
        if (!file.file) return new Observable<any>();
        const formData = new FormData();
        formData.append('file', file.file);
        return this.http.post(apiPath + file.ownerId + '/file', formData)
            .pipe(map(response => response));
    }
}
