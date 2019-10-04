export interface IStudio {
  id?: number;
  about?: string;
  street?: string;
  postCode?: string;
  city?: string;
  email?: string;
  phone?: string;
}

export class Studio implements IStudio {
  constructor(
    public id?: number,
    public about?: string,
    public street?: string,
    public postCode?: string,
    public city?: string,
    public email?: string,
    public phone?: string
  ) {}
}
