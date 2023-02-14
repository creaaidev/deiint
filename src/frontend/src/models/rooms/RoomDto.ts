export default class InterviewDto {
  id?: number;
  name?: string;
  building?: string;
  floor?: number;
  available?: boolean;

  constructor(jsonObj: Partial<InterviewDto>) {
    Object.assign(this, jsonObj);
  }
}