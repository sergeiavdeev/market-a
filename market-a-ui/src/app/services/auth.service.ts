import {environment} from "../../environments/environment";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Credentials, User} from "../entities/user";

const apiPath = environment.apiPath + "auth/";

@Injectable({providedIn: 'root'})
export class AuthService {
    constructor(private http: HttpClient) {
    }

    auth(credentials: Credentials): Observable<User> {
        return this.http.post<User>(apiPath, credentials)
            .pipe(map((user) => {
                return user;
            }));
    }
}