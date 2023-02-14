export default class InterviewDto {
  id?: number;
  name?: string;
  building?: string;
  floor?: string;
  available?: boolean;

  constructor(jsonObj: Partial<InterviewDto>) {
    Object.assign(this, jsonObj);
  }
}