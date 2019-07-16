export interface ICity {
  id?: number;
  name?: string;
  stateId?: number;
}

export class City implements ICity {
  constructor(public id?: number, public name?: string, public stateId?: number) {}
}
