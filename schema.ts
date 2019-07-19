export interface Error {
  type: string;
  message: string;
}

export interface Response<T> {
  data: T;
  errors: Error[];
}

export interface Holiday {
  id: string;
  name: string;
  fromDate: Date;
  toDate: Date;
  bla: Bla;
  user?: User;
}

export interface HolidayInput {
  id: string;
  userId?: User;
  name?: string;
  fromDate?: Date;
  toDate?: Date;
}

export interface User {
  id: string;
  firstName: string;
  lastName: string;
}

export interface HolidayController {
  all(userId: string): Promise<Response<Holiday[]>>;
  find(id: string): Promise<Response<Holiday>>;
  create(body: HolidayInput): Promise<Response<Holiday>>;
  update(id: string, body: HolidayInput): Promise<Response<Holiday>>;
  delete(id: string): Promise<Response<string>>;
}

export type Bla = "Progress" | "Bla" | "Ta";
