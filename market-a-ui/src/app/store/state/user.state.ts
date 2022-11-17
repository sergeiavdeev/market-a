import {User} from "../../entities/user";
import {HttpResponse} from "@angular/common/http";

export interface IUserState {
    user: User;
    pending: boolean;
    error: HttpResponse<any>
}

export const initialUserState: IUserState = {
    user: new User(),
    pending: false,
    error: new HttpResponse()
}