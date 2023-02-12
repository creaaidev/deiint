export default class InterviewDto {
  id?: number;
  callName?: string;
  room?: string;
  candidate?: string;
  status?: string;

  constructor(jsonObj: Partial<InterviewDto>) {
    Object.assign(this, jsonObj);
  }
}