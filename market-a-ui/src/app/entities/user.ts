export class User {
    username: String = "";
    email: String = "";
    firstName: String = "";
    lastName: String = "";
    patronymic: String = "";
    dateOfBirthday: String = "";
    isBlocked: boolean = false;
    roles: String[] = [];
    token: String = "";
}

export class Credentials {
    username: String = "";
    password: String = "";
}