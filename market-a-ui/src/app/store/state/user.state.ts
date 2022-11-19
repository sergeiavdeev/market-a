import {User} from "../../entities/user";
import {HttpResponse} from "@angular/common/http";

export interface IUserState {
    user: User;
    token: String;
    pending: boolean;
    error: HttpResponse<any>
}

export const initialUserState: IUserState = {
    user: new User(),
    token: "",
    pending: false,
    error: new HttpResponse()
}