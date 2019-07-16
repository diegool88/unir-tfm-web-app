export interface IStateProvince {
  id?: number;
  name?: string;
  countryId?: number;
}

export class StateProvince implements IStateProvince {
  constructor(public id?: number, public name?: string, public countryId?: number) {}
}
