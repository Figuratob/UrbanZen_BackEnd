export class Account {
  constructor(
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public phone: string,
    public firstName: string,
    public langKey: string,
    public lastName: string,
    public login: string,
    public imageUrl: string
  ) {}
}
